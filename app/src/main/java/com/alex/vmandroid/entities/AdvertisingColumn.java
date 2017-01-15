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

package com.alex.vmandroid.entities;

public class AdvertisingColumn{
	/**
	 * 广告的Id号
	 */
	private int Advertisement_Id;
	
	/**
	 * 广告对应图片的Id号
	 */
	private int ImageId;

	public int getAdvertisement_Id() {
		return Advertisement_Id;
	}

	public void setAdvertisement_Id(int advertisement_Id) {
		Advertisement_Id = advertisement_Id;
	}

	public int getImageId() {
		return ImageId;
	}

	public void setImageId(int imageId) {
		ImageId = imageId;
	}
}
