var charts = [],
	defaultOptions, options;
Highcharts.tableToHighChart = function(table, opt,index) {
	defaultOptions = {
		chart: {
			defaultSeriesType: 'column'
		},
		credits: {
			enabled: 0
		},
		xAxis: {},
		yAxis: {
			title: {
				text: 'Unit'
			}
		},
		tooltip: {
			formatter: function() {
				return '<b>' + this.series.name + '</b><br/>' + this.y + ' ' + this.x.toLowerCase();
			}
		},
		exporting: {
			enabled: true,
			buttons: {
				contextButton: {
					menuItems: [{
						text: 'Print Chart',
						onclick: function() {
							this.print();
						}
					}]
				}
			}
		}
	};
	var options = Highcharts.merge(defaultOptions, opt);
	options.xAxis.categories = [];
	var tableHead=$(table).find('.chart_header');
	options.series = [];

	var dataRow= $('tbody tr.highlight', table);
	/*if(dataRow.length==0){
	 dataRow= $('tbody tr', table);
	 }*/
	$('th:not(.exclude_chart)', tableHead).each(function(i) {
		if(i!=index){
			options.xAxis.categories.push($(this).text());
			dataRow.each(function(j) {
				var tr = $(this);
				if(options.series[j]==undefined){
					options.series[j] = {
						name: tr.find('td:nth-child('+(index+1)+')').text(),
						data: []
					};
				}
				options.series[j].data.push(parseFloat(tr.find('td:nth-child('+(i+1)+')').text().trim().replace(/[^0-9\.]+/g,"")))
			});
		}
	});
	charts[charts.length] = new Highcharts.Chart(options);
};
//options_metric = {
//	chart: {
//		renderTo: 'container',
//		defaultSeriesType: 'line',
//		width: 600
//	},
//	title: {
//		text: 'Trending Of ${metric}'
//	},
//	yAxis: {
//		title: {
//			text: 'Unit',
//			margin: 50
//		},
//		plotLines: [{
//			value: 0,
//			width: 1,
//			color: '#808080'
//		}]
//	}
//};
//
//
//$(document).ready(function() {
//	Highcharts.tableToHighChart($('table#trend_metric'), options_metric,0);
//
//});