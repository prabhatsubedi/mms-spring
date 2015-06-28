package com.ics.mms.service;

public interface IChartService extends IBaseService {
	String getLineChart(String chartName, String chartTitle, String yAxisTitle, String categories, String jsonData);
	String getPieChart(String chartName, String chartTitle, String yAxisTitle, String categories, String jsonData);
}
