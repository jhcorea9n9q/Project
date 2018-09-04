// review.html 용 js
var revjs = function() {
    pageNo = 2;
    lan_li(pageNo);
    rev_lan();
    logintoggle(pageNo);
    rev_event();
    lan_bnt();
}


// 리뷰화면 언어처리(미완성)
var rev_lan = function() {
    $.ajax({
        url: "/page/json/review.json"
    }).done(function(d){
        alert(d.reviews[lang]);
    });
}
var limit = 0;

// 리뷰화면 이벤트
var rev_event = function() {
    listMaker(limit);
    $("#pageList td").off().on("click",function(){
       if( $(this).text()=="▶" ){
            limit=limit+1;
            listMaker(limit);
       } else if( $(this).text()=="◀" ) {
            limit=limit-1;
            listMaker(limit);
       }
    });
}

var listMaker = function(limit) {
    $.ajax({
        type:"post",
        url:"/boardList",
        data:{"limit": (limit * 10) }
    }).done(function(data){
        var d = JSON.parse(data);
        var target = "#revList table";
        $(target).empty();
        for(var l=0; l<d.list.length; l++){
            var listData = d.list[l];
            var tableData = "<tr>";
            var dateData = listData.revDate.split(" ");
            tableData += "<td>" + listData.boardNo + "</td>";
            tableData += "<td>" + listData.movieTitle + "</td>";
            tableData += "<td>" + listData.revTitle + "</td>";
            tableData += "<td>" + listData.nickName + "</td>";
            tableData += "<td>" + dateData[1]+"-"+dateData[2] + "</td>";
            tableData += "<td>" + listData.visitCount + "</td>";
            tableData += "<td><button type='button' id='recomBtn" + listData.userNo + "' class='recommend'>추천</button></td></tr>";
            $(target).prepend(tableData);
        }
        $(target).prepend("<tr><th>번호</th><th>영화명</th><th>제목</th><th>닉네임</th><th>등록일</th><th>조회수</th><th>추천!</th></tr>");
    }).fail(function(){
        alert("ajax에 실패했습니다.");
    });
}