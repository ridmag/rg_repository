/**
 * resize the container
 */
function containerResizer() {
	var a = ($('.chart-container').width() / 1.2)- 25;
	$('.chart-container').height(a);
	
}

/**
 * load google charts api
 */
function loadGoogleAPI() {
	google.load('visualization', '1.0', {
		'packages' : [ 'corechart' ]
	});
}

/**
 * Draw the chart by selecting it's type.
 * @param chartType type of the chart.
 * @param id id of the div container.
 * @param data data that chats need.
 */
function drawChart(chartType, id, data) {
		selectChart(chartType, id, data);
}

/**
 * Select the chart from it's type
 * @param chartType type of the chart.
 * @param id id of the div container.
 * @param data data that chats need.
 */
function selectChart(chartType, id, data) {
	switch (chartType.toLowerCase()) {
		case 'barchart':
			google.setOnLoadCallback(function() {
				drawSelectedGoogleBarChart(id, data);
			});
			break;
		case 'piechart':
			google.setOnLoadCallback(function() {
				drawSelectedGooglePieChart(id, data);
			});
			break;
		case 'combochart':
			google.setOnLoadCallback(function() {
				drawSelectedGoogleComboChart(id, data);
			});
			break;
		case 'piechart-c3':
			//var processedData = processInputDataForC3PieChart(data);
			drawC3PieChart(id, data);
			break;
		case 'barcharthndisc-c3':
			//var processedData = processInputDataForC3PieChart(data);
			drawC3BarChartHNDISC(id, data);
			break;
		case 'barchartvst-c3':
			//var processedData = processInputDataForC3PieChart(data);
			drawC3BarChartVST(id, data);
			break;
		case 'barchartvfu-c3':
			//var processedData = processInputDataForC3PieChart(data);
			drawC3BarChartVFU(id, data);
			break;
		case 'barchartvpa-c3':
			//var processedData = processInputDataForC3PieChart(data);
			drawC3BarChartVPA(id, data);
			break;
		case 'barchartvsa-c3':
			//var processedData = processInputDataForC3PieChart(data);
			drawC3BarChartVSA(id, data);
			break;
		case 'barcharthssp-c3':
			//var processedData = processInputDataForC3PieChart(data);
			drawC3BarChartHSSP(id, data);
			break;
		case 'areachartpp-c3':
			//var processedData = processInputDataForC3PieChart(data);
			drawC3AreaChartPP(id, data);
			break;
		default : alert("Chart type not defined");
	}
}

/**
 * Draw google pie chart.
 * @param id id of the div container.
 * @param jsonPieData data that chats need.
 */
function drawSelectedGooglePieChart(id,jsonPieData) {
	var data = new google.visualization.DataTable(jsonPieData);
	var options = {
		'title' : 'Participated demogrphics',
		'legend': 'none',
		colors: ['#87A7CE', '#B4CA75', '#F6C569', '#F49E6F', '#E87C7C'],
		backgroundColor: { fill:'transparent' }
	};

	drawGooglePieChart(id, data, options);

	$(window).resize(function() {
		drawGooglePieChart(id, data, options);
	});

}


/**
 * Instantiate and draw our chart, passing in some options.
 * @param id id of the div container
 * @param data raw data to draw the chart
 * @param options chart options
 */
function drawGooglePieChart(id, data, options) {
	var chart = new google.visualization.PieChart(document.getElementById(id));
	chart.draw(data, options);
}


/**
 * Get pie chart from its name. 
 * @param inputArray input data for the chat.
 * @param pieChartName chart name.
 * @returns {Array} returns the pie chat data according to the pie chart name.
 */
function getGooglePieChartJsonData(inputArray , pieChartName){
	var jsonPieData = {};
	
	switch(pieChartName.toLowerCase()){
		case 'temppiechart':
			return googleTempPieChartData(inputArray);
			break;
		default:
			alert("No data handler is defined");
	}
}

/**
 * Temporary chart.
 * @param inputArray input data for the chat.
 * @returns json data of the chart.
 */
function googleTempPieChartData(inputArray){
	var jsonPieData = {
		"cols" : [ {
			"id" : "",
			"label" : "Topping",
			"pattern" : "",
			"type" : "string"
		}, {
			"id" : "",
			"label" : "Slices",
			"pattern" : "",
			"type" : "number"
		}],
		"rows" : [ {
			"c" : [ {
				"v" : "Mushrooms",
				"f" : null
			}, {
				"v" : 0,
				"f" : null
			}]
		}, {
			"c" : [ {
				"v" : "Onions",
				"f" : null
			}, {
				"v" : 0,
				"f" : null
			}]
		}, {
			"c" : [ {
				"v" : "Olives",
				"f" : null
			}, {
				"v" : 0,
				"f" : null
			}]
		}, {
			"c" : [ {
				"v" : "Zucchini",
				"f" : null
			}, {
				"v" : 0,
				"f" : null
			} ]
		}, {
			"c" : [ {
				"v" : "Pepperoni",
				"f" : null
			}, {
				"v" : 0,
				"f" : null
			} ]
		} ]
	}

	$.each(inputArray, function(i, val) {
		jsonPieData.rows[i].c[1].v = val;
	});

	return jsonPieData;
}

/**
 * Process the input data for temp pie chart.
 * @param data input DOM object array.
 * @returns {Array} data for pie chart.  
 */
function processInputDataForTempPieChart(data){
	var rowData = [];
	$.each(data, function( i, val ){
		rowData[i] = parseInt(val.value);
	});
	
	return rowData;
}

function drawSelectedGoogleBarChart(id, jsonBarData){
	var data = new google.visualization.DataTable(jsonBarData);
	
	 var options = {
		    'title': "NDIS claims",
		    'bar': {groupWidth: "95%"},
		    'legend': {position: 'none'},
		    dataOpacity: 0.6,
		    hAxis: {
		        viewWindow: {
		            min: 0,
		            max: 100
		        },
		        ticks: [0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100] // display labels every 25
		    },
		    backgroundColor: { fill:'transparent' }
		  };
	 
	 drawGoogleBarChart(id, data, options);

	$(window).resize(function() {
		drawGoogleBarChart(id, data, options);
	});
}

function googleTempBarChartData(inputArray){
	var jsonPieData = {
		cols : [ {
			id : 'task',
			label : 'Total values',
			type : 'string'
		}, {
			id : 'day_count',
			label : 'Day count',
			type : 'number'
		}, {
			id : 'color',
			role : 'style',
			type : 'string'
		} ],
		rows : [ {
			c : [ {
				v : 'Total billed'
			}, {
				v : 0
			}, {
				v : '#d92525'
			} ]
		}, {
			c : [ {
				v : 'Total subm.'
			}, {
				v : 0
			}, {
				v : '#f25c05'
			} ]
		}]
	}

	$.each(inputArray, function(i, val) {
		jsonPieData.rows[i].c[1].v = val;
	});

	return jsonPieData;
}

function drawGoogleBarChart(id, data, options){
	var chart = new google.visualization.BarChart(document.getElementById(id));
	chart.draw(data, options);
}

function processInputDataForTempBarChart(data){
	var rowData = [];
	$.each(data, function( i, val ){
		rowData[i] = parseInt(val.value);
	});
	
	return rowData;
}

function getGoogleBarChartJsonData(inputArray , barChartName){
	var jsonPieData = {};
	
	switch(barChartName.toLowerCase()){
		case 'tempbarchart':
			return googleTempBarChartData(inputArray);
			break;
		default:
			alert("No data handler is defined");
	}
}

function processInputDataForTempComboChart(data){
	var rowData = [];
	$.each(data, function( i, val ){
		rowData[i] = parseInt(val.value);
	});
	
	return rowData;
	
}

function getGoogleComboChartJsonData(inputArray , comboChartName){
	var jsonComboData = {};
	
	switch(comboChartName.toLowerCase()){
		case 'tempcombochart':
			return googleTempComboChartData(inputArray);
			break;
		default:
			alert("No data handler is defined");
	}
}

function googleTempComboChartData(inputArray){
	var jsonComboData = {
		"cols" : [ {
			"id" : "",
			"label" : "Name",
			"type" : "string"
		}, {
			"id" : "",
			"label" : "Planned",
			"type" : "number"
		}, {
			"id" : "",
			"label" : "Actual",
			"type" : "number"
		} ],
		"rows" : [ {
			"c" : [ {
				"v" : 'Kariay'
			}, {
				"v" : 0
			}, {
				"v" : 0
			} ]
		}, {
			"c" : [ {
				"v" : 'Gasf'
			}, {
				"v" : 9999
			}, {
				"v" : 0
			} ]
		}, {
			"c" : [ {
				"v" : 'Wyo'
			}, {
				"v" : 0
			}, {
				"v" : 0
			} ]
		} ]
	};
	
	for (var int = 0; int < inputArray.length/2; int++) {
			jsonComboData.rows[int].c[1].v = inputArray[2*int];
			jsonComboData.rows[int].c[2].v = inputArray[2*int+1];
	}

	return jsonComboData;
}

function drawSelectedGoogleComboChart(id,jsonComboData){
	    var data = new google.visualization.DataTable(jsonComboData);

	    var options = {
	    	chartArea: {left: '10%'},
	      title : 'Funding utilizer',
	      /*vAxis: {title: 'Cups'},
	      hAxis: {title: 'Month'},*/
	      seriesType: 'bars',
	      //series: {5: {type: 'line'}},
	      colors:['#87A7CE','#F6C569'],
	      backgroundColor: { fill:'transparent' }
	    };
	    
	    drawGoogleComboChart(id, data, options);

		$(window).resize(function() {
			drawGoogleComboChart(id, data, options);
		});
	    
}

function drawGoogleComboChart(id, data, options) {
	var chart = new google.visualization.ComboChart(document.getElementById(id));
	chart.draw(data, options);
}


/////////////////////////////////////////////C3 implementation/////////////////////////////////////////

function processIdForC3(id){
	return "#".concat(id);
}

function drawChartC3(id, input) {
	var newId = processIdForC3(id);
	var h = $(newId).height()-15;
	var w = $(newId).width()-15;

	input.bindto = newId;
	/*var input = {
		bindto : newId,
		data : {},
		axis: {
	        rotated: false
	    }
	}
	
	input.data = data;
	input.axis.rotated = rotation;*/
	
	var chart = c3.generate(input);

	 chart.resize({height:h, width:w});
	 
	 $(window).resize(function() {
		 var size = { height: $(newId).parent().height()-25, width:$(newId).parent().width()-15 }
		 chart.resize(size);
	 });
	 
}

function drawC3PieChart(id, data){
	
	/*var dummyData = {
		columns : [ [ "Mushroom", 0 ], [ "Onions", 0 ], [ "Olives", 0 ],
				[ "Zucchini", 0 ], [ "Pepperoni", 0 ], ],
		type : 'pie',
		colors : {
			Mushroom : '#87A7CE',
			Onions : '#B4CA75',
			Olives : '#F6C569',
			Zucchini : '#F49E6F',
			Pepperoni : '#E87C7C'
		}
	};*/
		
		var input = {
				data : {
					columns : [ [ "Mushroom", 0 ], [ "Onions", 0 ], [ "Olives", 0 ],
								[ "Zucchini", 0 ], [ "Pepperoni", 0 ], ],
						type : 'pie',
						colors : {
							Mushroom : '#87A7CE',
							Onions : '#B4CA75',
							Olives : '#F6C569',
							Zucchini : '#F49E6F',
							Pepperoni : '#E87C7C'
						}
				},
				axis: {
			        rotated: false
			    }
			}

	if(data.length > 0){
		for (var i = 0; i < data.length; i++) {
			input.data.columns[i][1] = parseInt(data[i].value);
		}
	}
	
	drawChartC3(id, input);
}

function drawC3BarChartHNDISC(id, data) {
	
	var val = {};
	val[0] = data[0].value;
	val[1] = data[1].value;

	var input = {
		color : {
			pattern : [ '#B4CA75', '#ACB6DD' ]
		},
		data : {
			x : 'x',
			columns : [ [ 'x', ' Total billed', ' Total submissions' ],
					[ 'value', 0, 0 ] ],

			type : 'bar',

			color : function(inColor, data) {
				var colors = [ '#B4CA75', '#ACB6DD' ];
				if (data.index !== undefined) {
					return colors[data.index];
				}

				return inColor;
			}
		},
		axis : {
			rotated : true,
			x : {
				type : 'category'
			},
			y : {
				//label : 'Y Label'
			}
		},
		tooltip : {
			grouped : true,
			contents : function(d, defaultTitleFormat, defaultValueFormat,
					color) {
				var colors = [ '#B4CA75', '#ACB6DD' ];
				var category = ['Total billed','Total submissions'];
				//console.log(d[0].index);

				var color_val = '';
				var cat = '';
				var i;

				if (d[0].index === 0) {
					color_val = colors[0];
					cat = category[0];
					i = val[0];
				} else if (d[0].index === 1) {
					color_val = colors[1];
					cat = category[1];
					i = val[1];
				}

				var tip = '<table class="c3-tooltip"><tbody><tr><th colspan="2">'+cat+'</th></tr><tr class="c3-tooltip-name-value"><td class="name"><span style="background-color:'
						+ color_val + '"></span>value</td><td class="value">'+i+'</td></tr></tbody></table>'

				return tip;
			}
		},
		legend : {
			show : false
		}
	}

	if (data.length > 0) {
		for (var i = 1; i <= data.length; i++) {
			input.data.columns[1][i] = parseInt(data[i - 1].value);
		}
	}

	drawChartC3(id, input);
}

//staf training
function drawC3BarChartVST(id, data) {
	
	var input = {
			color : {
				pattern : ['#87A7CE','#F49E6F']
			},
		    data: {
		        columns: [
		            ['Planned', 30, 200, 100, 400, 150, 250],
		            ['Actual', 130, 100, 140, 200, 150, 50]
		        ],
		        type: 'bar'
		    },
		    bar: {
		        width: {
		            ratio: 0.8
		        }
		    },
		    axis: {
		        x: {
			    	tick: {
			    		format: function (d) { return "week" + (d+1); }
		            }
		        }
		    }
		};
	
	drawChartC3(id, input);
}

//funding utilizal
function drawC3BarChartVFU(id, data) {
	
	var input = {
			color : {
				pattern : ['#87A7CE','#F49E6F']
			},
		    data: {
		        columns: [
		            ['Planned', 30, 200, 100, 400, 150, 250],
		            ['Actual', 130, 100, 140, 200, 150, 50]
		        ],
		        type: 'bar'
		    },
		    bar: {
		        width: {
		            ratio: 0.8
		        }
		    },
		    axis: {
		        x: {
		        	type: 'category',
		            categories: ['Gosf', 'Indiv..', 'Kari', 'OTC', 'SLF', 'Wyong']
		        }
		    }
		};
	
	drawChartC3(id, input);
}

//participants attendance
function drawC3BarChartVPA(id, data) {
	
	var input = {
			color : {
				pattern : ['#87A7CE','#B4CA75','#F6C569','#F49E6F','#E87C7C']
			},
		    data: {
		        columns: [
		            ['Planned tranceport', 30, 200, 100, 400, 150, 250],
		            ['Actual tranceport', 130, 100, 140, 200, 150, 50],
		            ['Planned program', 30, 200, 100, 400, 150, 250],
		            ['Actual program', 130, 100, 140, 200, 150, 50],
		            ['Planned individual', 30, 200, 100, 400, 150, 250],
		            ['Actual individual', 130, 100, 140, 200, 150, 50]
		        ],
		        type: 'bar'
		    },
		    bar: {
		        width: {
		            ratio: 0.8
		        }
		    },
		    axis: {
		        x: {
			    	/*tick: {
			    		format: function (d) { return "week" + (d+1); }
		            }*/
		        	type: 'category',
		            categories: ['Gosf', 'Indiv..', 'Kari', 'OTC', 'SLF', 'Wyong']
		        }
		    }
		};
	
	drawChartC3(id, input);
}

//staff attendance
function drawC3BarChartVSA(id, data) {
	
	var input = {
			color : {
				pattern : ['#87A7CE','#B4CA75','#F6C569','#F49E6F','#E87C7C']
			},
		    data: {
		        columns: [
		            ['Planned trnceport', 30, 200, 100, 400, 150, 250],
		            ['Actual trnceport', 130, 100, 140, 200, 150, 50],
		            ['Planned program', 30, 200, 90, 310, 140, 260],
		            ['Actual program', 130, 56, 112, 200, 150, 50],
		            ['Planned individual', 30, 150, 100, 5, 100, 25],
		            ['Actual individual', 113, 10, 140, 20, 15, 50]
		        ],
		        type: 'bar'
		    },
		    bar: {
		        width: {
		            ratio: 0.8
		        }
		    },
		    axis: {
		        x: {
			    	/*tick: {
			    		format: function (d) { return "week" + (d+1); }
		            }*/
		        	type: 'category',
		            categories: ['Gosf', 'Indiv..', 'Kari', 'OTC', 'SLF', 'Wyong']
		        }
		    }
		};
	
	drawChartC3(id, input);
}

//statement of support plan
function drawC3BarChartHSSP(id, data1) {
	
	var val = {};
	val[0] = data1[0].value;
	val[1] = data1[1].value;
	val[2] = data1[2].value;
	val[3] = data1[3].value;

	var input = {
		/*color : {
			pattern : [ '#B4CA75', '#ACB6DD' ]
		},*/
        padding: {
            left: 70
        },
		data : {
			x : 'x',
			columns : [ [ 'x', 'Reviews', 'Participants' ],
			            ['value', 300, 400],['value2', 200, 100]],

			type : 'bar',

			color: function(inColor, data) {
                var colors2 = [ '#f9d899', '#E87C7C' ];
                var colors1 = [ '#f2a921', '#dd3b3b' ];
                if(data.index !== undefined) {
                	if(data.id == 'value'){
                    return colors1[data.index];
                  	}else if(data.id == 'value2'){
                    return colors2[data.index];
                  	}
                }

                return inColor;
            }
		},
		axis : {
			rotated : true,
			x : {
				type : 'category'
			},
			y : {
				//label : 'Y Label'
			}
		},
		tooltip : {
			grouped : true,
			contents : function(d, defaultTitleFormat, defaultValueFormat,
					color) {
				//var colors = [ '#F6C569', '#E87C7C' ];
				var colors2 = [ '#f9d899', '#E87C7C' ];
				var colors = [ '#f2a921', '#dd3b3b' ];
				var category = ['Reviews','Participants'];

				var color_val = '';
				var cat = '';
				var i,j,v1,v2;

				if (d[0].index === 0) {
					color_val = colors[0];
					color_val2 = colors2[0];
					cat = category[0];
					i = val[0];
					j = val[1];
					v1 = 'completed'; v2 = 'pending';
				} else if (d[0].index === 1) {
					color_val = colors[1];
					color_val2 = colors2[1];
					cat = category[1];
					i = val[2];
					j = val[3];
					v1 = 'new'; v2 = 'current';
				}

				var tip = '<table class="c3-tooltip"><tbody><tr><th colspan="2">'+cat+'</th></tr><tr class="c3-tooltip-name-value"><td class="name"><span style="background-color:'
						+ color_val + '"></span>'+v1+'</td><td class="value">'+i+'</td></tr><tr class="c3-tooltip-name-value"><td class="name"><span style="background-color:'
						+ color_val2 + '"></span>'+v2+'</td><td class="value">'+j+'</td></tr></tbody></table>'

				return tip;
			}
		},
		legend : {
			show : false
		}
	}
	
	/*if (data.length > 0) {
		for (var i = 1; i <= data.length; i++) {
			input.data.columns[1][i] = parseInt(data[i - 1].value);
		}
	}*/

	drawChartC3(id, input);
}

function drawC3AreaChartPP(id, data) {
	
	/*var val = {};
	val[0] = data[0].value;
	val[1] = data[1].value;*/

	var input = {
		    data: {
		        columns: [
		            ['data1', 300, 350, 300, 0, 0, 0]
		        ],
		        types: {
		            data1: 'area',
		            data2: 'area-spline'
		        }
		    }
		}
	
	/*if (data.length > 0) {
		for (var i = 1; i <= data.length; i++) {
			input.data.columns[1][i] = parseInt(data[i - 1].value);
		}
	}*/

	drawChartC3(id, input);
}

