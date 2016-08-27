<@Page>
	<script src="${rc.contextPath}/resources/js/Chart.min.js"></script>
	<style>
		#div {width:50%}
		#canvas {height:450px;width:600px;}
	</style>
	<@Tab model=[["图书借阅次数","showbookborrowtimes.do"],["借阅总量","showbookborrowtimespastmonth.do"]] 
		pageIndex=1 />
	<div id="div" >
		<canvas id="canvas" ></canvas>
	</div>
	<script>
		var labels = [];
		var datas = [];
		
		<#list data as item >
			labels.push("${item[0]}");
			datas.push(${item[1]});
		</#list>

		var barChartData = { labels : labels,
							 datasets : [ {  fillColor : "rgba(151,187,205,0.5)",
											strokeColor : "rgba(151,187,205,0.8)",
											highlightFill : "rgba(151,187,205,0.75)",
											highlightStroke : "rgba(151,187,205,1)",
											data : datas 
									     } ]
						   }
		
		window.onload = function(){
			var ctx = document.getElementById("canvas").getContext("2d");
			window.myBar = new Chart(ctx).Bar(barChartData, {
				responsive : true
			});
		}

	</script>
</@Page>