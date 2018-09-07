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
    if(lang==1){
        alert("すみません，日本版はまだ完成されていません。");
    }
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

// 게시판 리스트 만들기
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
            tableData += "<td class='clickDetail' id='D_" + listData.boardNo + "'>" + listData.revTitle + "</td>";
            tableData += "<td>" + listData.nickName + "</td>";
            tableData += "<td>" + dateData[1]+"-"+dateData[2] + "</td>";
            tableData += "<td>" + listData.visitCount + "</td>";
            tableData += "<td><button type='button' id='recomBtn" + listData.userNo + "' class='recommend'>추천</button></td></tr>";
            $(target).append(tableData);
        }
        $(target).prepend("<tr><th>번호</th><th>영화명</th><th>제목</th><th>닉네임</th><th>등록일</th><th>조회수</th><th>추천!</th></tr>");
        seeDt();
    }).fail(function(){
        alert("ajax에 실패했습니다.");
    });
}

// 글쓰기 화면 생성
var wantInsert = function(isLogin, userData) {
    $("#insertBtn button").off().on("click", function(){
        if(isLogin==0){
            alert("우선 로그인을 해주십시오.");
            location.href="/page/login.html";
        }else if(isLogin==1){
            $("html, body").animate({scrollTop:0}, 'fast');
            $("#revInsert").show();
            var userNo = userData.userNo;
            insertBtn(userNo);
        }
    });
}

// 입력 시 기능
var insertBtn = function(userNo) {
    $("#insertForm").off().submit(function(event){
        event.preventDefault();
        var revTitle = $("#revTitle").val();
        var movieTitle = $("#movieTitle").val();
        var movieGrade = $("#movieGrade").val();
        var revContents = $("#revContents").val();
        $.ajax({
            type:"post",
            url:"/boardIn",
            data:{
                  "userNo":userNo,
                  "revTitle":revTitle,
                  "movieTitle":movieTitle,
                  "movieGrade":movieGrade,
                  "revContents": revContents
                 }
        }).done(function(data){
            var d = JSON.parse(data);
            if(d.status==0){
                alert("글쓰기에 실패하였습니다");
            }else if(d.status==1){
                alert("글쓰기 완료!");
                $("#revInsert").hide();
            }
        }).fail(function(){
            alert("글쓰기에 실패하였습니다");
        });
    });
}

// 디테일 화면
var seeDt = function() {
    $(".clickDetail").off().on("click", function(){
        var getID = $(this).attr('id').split("_");
        $.ajax({
            type:"post",
            url:"/boardDetail/" + getID[1]
        }).done(function(data){
            var d = JSON.parse(data);
            if(d.result!=null){
                $("#revDetails").show();
                var boardData = d.result;
                $("#revHeader h2").text(boardData.movieTitle);
                var mG = "";
                if(boardData.movieGrade==1){
                    mG = "★☆☆☆☆";
                }else if(boardData.movieGrade==2){
                    mG = "★★☆☆☆";
                }else if(boardData.movieGrade==3){
                    mG = "★★★☆☆";
                }else if(boardData.movieGrade==4){
                    mG = "★★★★☆";
                }else if(boardData.movieGrade==5){
                    mG = "★★★★★";
                }
                $("#revHeader span").text(mG);
                $("#revHeader h3").text(boardData.revTitle);
                $("#revInfo p").eq(0).children("span").text(boardData.nickName);
                $("#revInfo p").eq(1).children("span").text(boardData.visitCount);
                $("#revInfo p").eq(2).children("span").text(boardData.recoCount);
                var dateData = boardData.revDate.split(" ");
                $("#revInfo p").eq(3).children("span").text(dateData[0]+"-"+dateData[1]+"-"+dateData[2]);
                $("#revMain").text(boardData.revContents);
            }
        }).fail(function(){
            alert("글읽기에 실패하였습니다");
        });
    });
}