<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Styles -->
<style>
#chartdiv {
  width: 100%;
  height: 500px;
}					
</style>

<!-- Resources -->
<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script src="https://www.amcharts.com/lib/3/serial.js"></script>
<script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" />
<script src="https://www.amcharts.com/lib/3/themes/light.js"></script>

<!-- Chart code -->
<script>
var chart = AmCharts.makeChart( "chartdiv1", {
	  "type": "serial",
	  "theme": "light",
	  "autoMargins": false,
	  "marginTop": 30,
	  "marginLeft": 80,
	  "marginBottom": 30,
	  "marginRight": 50,
	  "dataProvider": [ {
	    "category": "미세먼지",
	    "good": 30,
	    "average": 50,
	    "poor": 70,
	    "worst": 100,
	    "full": 200,
	    "bullet": 65
	  } ],
	  
	  "valueAxes": [ {
	    "maximum": 450,
	    "stackType": "regular",
	    "gridAlpha": 0
	  } ],
	  "startDuration": 1,
	  "graphs": [ {
	    "fillAlphas": 0.8,
	    "lineColor": "#0054FF",
	    "showBalloon": false,
	    "type": "column",
	    "valueField": "good"
	  }, {
	    "fillAlphas": 0.8,
	    "lineColor": "#19d228",
	    "showBalloon": false,
	    "type": "column",
	    "valueField": "average"
	  }, {
	    "fillAlphas": 0.8,
	    "lineColor": "#f6d32b",
	    "showBalloon": false,
	    "type": "column",
	    "valueField": "poor"
	  }, {
	    "fillAlphas": 0.8,
	    "lineColor": "#FF2424",
	    "showBalloon": false,
	    "type": "column",
	    "valueField": "worst"
	  }, {
		    "fillAlphas": 0.8,
		    "lineColor": "#00F",
		    "showBalloon": false,
		    "type": "column",
		    "valueField": "full"
	}, {
	    "clustered": false,
	    "columnWidth": 0.3,
	    "fillAlphas": 1,
	    "lineColor": "#000000",
	    "stackable": false,
	    "type": "column",
	    "valueField": "bullet"
	  }],
	  "rotate": true,
	  "columnWidth": 1,
	  "categoryField": "category",
	  "categoryAxis": {
		"autoGridCount": false,
	    "gridAlpha": 0,
	    "position": "left"
	  }
	} );






var chart = AmCharts.makeChart( "chartdiv2", {
  "type": "serial",
  "rotate": true,
  "theme": "light",
  "autoMargins": false,
  "marginTop": 30,
  "marginLeft": 80,
  "marginBottom": 30,
  "marginRight": 50,
  "dataProvider": [ {
    "category": "초미세먼지",
    "good": 15,
    "average": 35,
    "poor": 50,
    "worst": 50,
    "limit": 78,
    "full": 150,
    "bullet": 65
  } ],
  "valueAxes": [ {
    "maximum": 150,
    "stackType": "regular",
    "gridAlpha": 0
  } ],
  "startDuration": 1,
  "graphs": [ {
    "valueField": "full",
    "showBalloon": false,
    "type": "column",
    "lineAlpha": 0,
    "fillAlphas": 0.8,
    "fillColors": [ "#0054FF", "#19d228", "#f6d32b","#FF2424" ],
    "gradientOrientation": "horizontal",
  }, {
    "clustered": false,
    "columnWidth": 0.3,
    "fillAlphas": 1,
    "lineColor": "#000000",
    "stackable": false,
    "type": "column",
    "valueField": "bullet"
  }, {
    "columnWidth": 0.5,
    "lineColor": "#000000",
    "lineThickness": 3,
    "noStepRisers": true,
    "stackable": false,
    "type": "step",
    "valueField": "limit"
  } ],
  "columnWidth": 1,
  "categoryField": "category",
  "categoryAxis": {
    "gridAlpha": 0,
    "position": "left"
  }
} );
</script>

<div class="chart-block">
   <div id="chartdiv1" style="width:100%; height:150px;"></div>
  <div id="chartdiv2" style="width:100%; height:150px;"></div>
</div>
