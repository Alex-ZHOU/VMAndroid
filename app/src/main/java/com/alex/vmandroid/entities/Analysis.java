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

/**
 * 服务器分析返回的数据
 */
public class Analysis {
	
	
	/**
	 * 平均分贝数
	 */
	private int AverageDb;
	
	/**
	 * 最小分贝数
	 */
	private int MinDb;
	
	/**
	 * 最大分贝数
	 */
	private int MaxDb;
	
	/**
	 * 记录时长
	 */
	private int RecordMinter;
	
	/**
	 * 记录的总次数
	 */
	private int Times;
	
	/**
	 * 0 －20 分贝 很静、几乎感觉不到
	 */
	private int _20Times;
	
	
	/**
	 * 20 －40 分贝 安静、犹如轻声絮语
	 */
	private int _40Times;
	
	/**
	 * 40 －60 分贝 一般、普通室内谈话
	 */
	private int _60Times;
	/**
	 * 60 －70 分贝 吵闹、有损神经；
	 */
	private int _70Times;
	
	/**
	 * 70 －90 分贝 很吵、神经细胞受到破坏
	 */
	private int _90Times;
	
	/**
	 * 90 －100 分贝 吵闹加剧、听力受损
	 */
	private int _100Times;
	
	/**
	 * 100 －120 分贝 难以忍受、呆一分钟即暂时致聋
	 */
	private int _120Times;
	
	/**
	 * 120分贝以上 极度聋或全聋
	 */
	private int _120UpTimes;
	
	private String Analysis;

	public int getAverageDb() {
		return AverageDb;
	}

	public void setAverageDb(int averageDb) {
		AverageDb = averageDb;
	}

	public int getMinDb() {
		return MinDb;
	}

	public void setMinDb(int minDb) {
		MinDb = minDb;
	}

	public int getMaxDb() {
		return MaxDb;
	}

	public void setMaxDb(int maxDb) {
		MaxDb = maxDb;
	}

	public int getRecordMinter() {
		return RecordMinter;
	}

	public void setRecordMinter(int recordMinter) {
		RecordMinter = recordMinter;
	}

	public int getTimes() {
		return Times;
	}

	public void setTimes(int times) {
		Times = times;
	}

	public int get_20Times() {
		return _20Times;
	}

	public void set_20Times(int _20Times) {
		this._20Times = _20Times;
	}

	public int get_40Times() {
		return _40Times;
	}

	public void set_40Times(int _40Times) {
		this._40Times = _40Times;
	}

	public int get_60Times() {
		return _60Times;
	}

	public void set_60Times(int _60Times) {
		this._60Times = _60Times;
	}

	public int get_70Times() {
		return _70Times;
	}

	public void set_70Times(int _70Times) {
		this._70Times = _70Times;
	}

	public int get_90Times() {
		return _90Times;
	}

	public void set_90Times(int _90Times) {
		this._90Times = _90Times;
	}

	public int get_100Times() {
		return _100Times;
	}

	public void set_100Times(int _100Times) {
		this._100Times = _100Times;
	}

	public int get_120Times() {
		return _120Times;
	}

	public void set_120Times(int _120Times) {
		this._120Times = _120Times;
	}

	public int get_120UpTimes() {
		return _120UpTimes;
	}

	public void set_120UpTimes(int _120UpTimes) {
		this._120UpTimes = _120UpTimes;
	}

	public String getAnalysis() {
		return Analysis;
	}

	public void setAnalysis(String analysis) {
		Analysis = analysis;
	}
}
