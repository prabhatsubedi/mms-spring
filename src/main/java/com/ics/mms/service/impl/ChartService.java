package com.ics.mms.service.impl;

import com.ics.mms.service.IChartService;

public class ChartService implements IChartService{

	@Override
	public String getLineChart(String chartName, String chartTitle, String yAxisTitle, String categories, String jsonData) {
		String chartScript = "{chart: {renderTo: 'container_"+chartName+"',height: 400}, title: {text: '"+chartTitle+"',x: -20}," +
				"xAxis: {categories: "+categories+"}, yAxis: {title: {text: '"+yAxisTitle+"'}},credits: {enabled: false}," +
				"exporting: { enabled: true, buttons: { contextButton: { menuItems: [{ text: 'Print Chart', onclick: function(){ this.print(); }}]}}}," +
				"credits: {enabled: false},series: "+jsonData+"}";
		
		return chartScript;
	}

	@Override
	public String getPieChart(String chartName, String chartTitle, String yAxisTitle, String categories, String jsonData) {
		String chartScript = "{chart: {renderTo: 'container_"+chartName+"',height: 400, type: 'pie'}, title: {text: '"+chartTitle+"',x: -20}," +
				"colors:['rgb(47, 126, 216)','rgb(13, 35, 58)','rgb(139, 188, 33)','rgb(145, 0, 0)','rgb(26, 173, 206)','rgb(73, 41, 112)','rgb(242, 143, 67)','rgb(119, 161, 229)','rgb(196, 37, 37)','rgb(166, 201, 106)','rgb(237, 86, 27)','rgb(146, 168, 205)','rgb(181, 202, 146)','rgb(144, 137, 232)','rgb(164, 125, 124)','rgb(96, 188, 179)','rgb(86, 97, 54)','rgb(210, 189, 123)','rgb(120, 67, 181)','rgb(184,0,46)'],"+
				"tooltip: {formatter: function() { return this.point.name +' : <b>'+ Highcharts.numberFormat(this.y, 0) +' </b> qty'; }},"+
				"plotOptions: {pie: {allowPointSelect: true,cursor: 'pointer',"+
                "dataLabels: {enabled: true, format: '<b>{point.name}</b>: {point.percentage:.1f} %',"+
                "style: {color: 'BLACK',fontSize:'13px'}}}}," +
				"credits: {enabled: false}, series: [{type: 'pie',name: 'Chart Report',"+jsonData+"}]}";
		
		return chartScript;
	}
	

}
