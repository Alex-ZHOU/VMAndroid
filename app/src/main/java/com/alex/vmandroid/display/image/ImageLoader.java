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

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 图片加载核心类
 *
 * @author 鸿洋_ 源码出处
 * @author AlexZHOU 源码修改 2017.04.26
 */
public class ImageLoader {

    /**
     * 图片缓存
     */
    private LruCache<String, Bitmap> mLruCache;

    /**
     * 线程池
     */
    private ExecutorService mThreadPool;

    /**
     * 线程池的线程数量，默认为1
     */
    private int mThreadNum = 1;

    /**
     * 队列的调度方式
     */
    private Type mType = Type.LIFO;

    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTasks;

    /**
     * 轮询的线程
     */
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;

    /**
     * 运行在UI线程的handler，用于给ImageView设置图片
     */
    private Handler mHandler;

    /**
     * 引入一个值为1的信号量，防止mPoolThreadHandler未初始化完成
     */
    private volatile Semaphore mSemaphore = new Semaphore(0);

    /**
     * 引入一个值为1的信号量，由于线程池内部也有一个阻塞线程，防止加入任务的速度过快，使LIFO效果不明显
     */
    private volatile Semaphore mPoolSemaphore;

    private static ImageLoader mInstance;


    /**
     * 队列的调度方式
     */
    private enum Type {
        FIFO, LIFO
    }

    public static ImageLoader newInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(1, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    public ImageLoader(int threadCount, Type type) {
        mThreadNum = threadCount;
        init(threadCount, type);
    }

    private void init(int threadCount, Type type) {

        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {

                        if (getTask() != null) {
                            mThreadPool.execute(getTask());
                        }

                        try {
                            mPoolSemaphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 释放一个信号量
                mSemaphore.release();
                Looper.loop();
            }
        };
        mPoolThread.start();


        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;

        mLruCache = new LruCache<String, Bitmap>(cacheSize)
        {
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                return value.getRowBytes() * value.getHeight();
            };
        };

        mThreadPool = Executors.newFixedThreadPool(mThreadNum);
        mPoolSemaphore = new Semaphore(threadCount);
        mTasks = new LinkedList<Runnable>();
        mType = type == null ? Type.LIFO : type;

    }

    /**
     * 取出一个任务
     *
     * @return
     */
    private synchronized Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTasks.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTasks.removeLast();
        }
        return null;
    }

}
