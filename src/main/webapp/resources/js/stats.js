// stats.html 용 js
var stajs = function() {
    pageNo = 1;
    lan_li(pageNo);
    sta_lan();
    logintoggle(pageNo);
    stajs_event();
    lan_bnt();
}

var resultData = "";

// stats 화면 언어변경
var sta_lan = function() {
    $.ajax({
        url: "json/stats.json"
    }).done(function(d){
        $("#nowLoading div p").text(d.nowLoading[lang]);
        var staText = d.staText[lang];
        $("#statText h2").text(staText.title);
        $("#selsec button").eq(0).text(staText.btn1);
        $("#selsec button").eq(1).text(staText.btn2);
        $("#selsec button").eq(2).text(staText.btn3);
        $("#chart_body").html(staText.whats);
    });
}

// stats 화면 이벤트
var stajs_event = function() {
    hadoop_btn();
}

// 버튼 선택 이벤트
var hadoop_btn = function(){
    $("#selsec button").off().on("click",function(){
        var btnindex = $(this).index();
        var operation;
        var op_nm = $(this).text();
        if(btnindex==1){
            operation="G"
        }else if(btnindex==2){
            operation="C"
        }else if(btnindex==3){
            operation="Y"
        }
        $('#chart_body').empty();
        $("#nowLoading").show();
        $.ajax({
            type:"post",
            url:"/hadoop",
            data : {"operation" : operation}
        }).done(function(data){
            var d = JSON.parse(data);
            var path = d.path;
            $("#nowLoading").hide();
            checkData(path, op_nm);
        });
    });
}

// 완료된 데이터 가져오기.
var checkData = function(path, op_nm) {
    $.ajax({
        type:"post",
        url:"/callData",
        data:{"path":path}
    }).done(function(data){
        var d = JSON.parse(data);
        resultData = d.result;
        if(resultData.length > 0) {
            ChartMaker(op_nm);
        }
    });
}

// 차트 그리기
var ChartMaker = function(op_nm){
    google.charts.load('current', {'packages':['corechart']});
		google.charts.setOnLoadCallback(draw);
		
		function draw() {
	    	var chartData = new google.visualization.DataTable();
	    	chartData.addColumn("string", "데이터");
			chartData.addColumn("number", "숫자");
			$.each(resultData, function(index, value) {
				var row = [];
				for(var i = 0; i < 2; i++){
					row[i] = (i != 0) ? Number(value[i]) : value[i];
				}
				chartData.addRows([ row ]);
			});
			
			var option = {
	   			    chartType: 'PieChart',
	   			 	dataTable: chartData,
	   			    options: {title: op_nm, pieHole: 0.3},
	   			    containerId : 'chart_body',
                    backgroundColor : '#f1f1f1'
	   			  };
			var wrapper = new google.visualization.ChartWrapper(option);
			wrapper.draw();
        }
    
}