package com.winsigns.investment.measure.data;

//TODO: 需要改写。其中需要包含更多信息
public class MeasureData {
	public int version;
	public double value;
	
	private static MeasureData defaultData = new MeasureData();
	public static MeasureData defaultData(){
		return defaultData;
	}
}
