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

package com.alex.utils;

/**
 * 平均计算算法
 */
public class AverageAlgorithm {

	private int MiddleNum = 50;

	public void setMiddleNum(int num) {
		MiddleNum = num;
	}

	public double getAverage(double historyAverage,int historyNum,int[] a){
		double historyAmplitude = (historyAverage-MiddleNum)*historyNum;
		
		double thisAmplitude = (getAverage(a)-MiddleNum)*a.length;
		
		return MiddleNum + ((historyAmplitude+thisAmplitude) / (historyNum+a.length));
	}
	
	public double getAverage(int[] a) {

		int amplitude = 0;

		for (int anA : a) {
			amplitude = (anA - MiddleNum) + amplitude;
		}

		return MiddleNum + (amplitude / a.length);
	}

}
