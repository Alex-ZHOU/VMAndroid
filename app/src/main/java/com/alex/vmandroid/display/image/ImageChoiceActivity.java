/*
 * Copyright 2017 Alex_ZHOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alex.vmandroid.display.image;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.utils.AppLog;
import com.alex.vmandroid.R;
import com.alex.vmandroid.base.BaseActivity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ImageChoiceActivity extends BaseActivity {

    public static final String TAG = ImageChoiceActivity.class.getName();

    private int mScreenHeight = -1;

    private RelativeLayout mBottomRelativeLayout = null;

    private TextView mFolderNameTextView = null;

    private TextView mPicNumTextView = null;

    private GridView mGridView = null;

    private ProgressDialog mProgressDialog;

    private int totalCount = 0;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFolder> mImageFloders = new ArrayList<>();

    /**
     * 所有的图片
     */
    private List<String> mImgs;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            //initListDirPopupWindw();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_choice);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;

        AppLog.debug(TAG, "ScreenHeight:" + mScreenHeight);

        mBottomRelativeLayout = (RelativeLayout) findViewById(R.id.image_choice_bottom_rl);
        mFolderNameTextView = (TextView) findViewById(R.id.image_choice_dir_tv);
        mPicNumTextView = (TextView) findViewById(R.id.image_choice_total_tv);
        mGridView = (GridView) findViewById(R.id.image_choice_pic_show_gv);

        getImageDirs();
    }


    public void getImageDirs() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }

        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = ImageChoiceActivity.this
                        .getContentResolver();

                // 只查询jpg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                if (mCursor == null) {
                    return;
                }

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    AppLog.debug(TAG, "获取图片的路径path:" + path);

                    // 拿到第一张图片的路径
                    if (firstImage == null) {
                        firstImage = path;
                    }
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null) {
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFolder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化ImageFolder
                        imageFloder = new ImageFolder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File file, String filename) {
//                            if (filename.endsWith(".jpg")
//                                    || filename.endsWith(".png")
//                                    || filename.endsWith(".jpeg")) {
//                                return true;
//                            } else {
//                                return false;
//                            }
                            return (filename.endsWith(".jpg") ||
                                    filename.endsWith(".png") ||
                                    filename.endsWith(".jpeg"));

                        }
                    }).length;

                    totalCount += picSize;

                    imageFloder.setImageNum(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize)
                    {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();
    }

    /**
     * 为View绑定数据
     */
    private void data2View()
    {
        if (mImgDir == null)
        {
            Toast.makeText(getApplicationContext(), "擦，一张图片没扫描到",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mImgs = Arrays.asList(mImgDir.list());
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
//        mAdapter = new MyAdapter(getApplicationContext(), mImgs,
//                R.layout.grid_item, mImgDir.getAbsolutePath());
//        mGirdView.setAdapter(mAdapter);
//        mImageCount.setText(totalCount + "张");
    }
}
