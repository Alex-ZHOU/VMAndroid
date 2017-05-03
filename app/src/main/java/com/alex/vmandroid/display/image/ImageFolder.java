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


/**
 * 源：用来存储当前文件夹的路径，当前文件夹包含多少张图片，以及第一张图片路径用于做文件夹的图标
 * 改：用最后一张图片作文文件夹的图标
 *
 * @author 鸿洋_ 源码出处
 * @author AlexZHOU 源码修改 2017.04.25
 */
public class ImageFolder {

    /**
     * 图片的文件夹路径
     */
    private String dir;

    /**
     * 第一张图片的路径
     */
    private String firstImagePath;

    /**
     * 最后一张图片的路径
     */
    private String lastImagePath;

    /**
     * 文件夹的名称
     */
    private String folderName;

    /**
     * 图片的数量
     */
    private int imageNum;

    public String getDir() {
        return dir;
    }

    /**
     * 在set文件夹的路径的同时，顺便提取文件夹的名称
     *
     * @param dir 图片的文件夹路径
     */
    public void setDir(String dir) {
        this.dir = dir;
        // 最后一个"／"的位置
        int lastIndexOf = this.dir.lastIndexOf("/");
        // 设置文件名，格式如"/image"
        setFolderName(this.dir.substring(lastIndexOf));
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getLastImagePath() {
        return lastImagePath;
    }

    public void setLastImagePath(String lastImagePath) {
        this.lastImagePath = lastImagePath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }
}
