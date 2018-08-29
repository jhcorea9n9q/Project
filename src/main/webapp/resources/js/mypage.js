// mypage.html 용 js
var myjs = function() {
    pageNo = 5;
    lan_li(pageNo);
    my_lan();
    logintoggle(pageNo);
    my_event();
    lan_bnt();
}

// mypage 화면 언어변경
var my_lan = function() {
    $.ajax({
       url: "json/mypage.json" 
    }).done(function(d){
        var my_ul = d.ul_li[lang];
        $("#menu_users p").text(my_ul.titles);
        $.each(my_ul.li_nm, function(i,v){
            $("#menu_users li").eq(i).text(v);
            $("#menu" + (i+1) + " h2").text(v);
        });
        var menu_text = d.menu_text[lang];
        for(var m=1; m<7; m++){
            var mn = "menu" + m;
            $.each(menu_text[mn], function(ind, val) {
                $("#" + mn + " p").eq(ind).html(val);
            });
            if(m==3||m==4){
                $("#" + mn + " button").text(menu_text.menu34btn);
            }else if(m==5){
                $("#" + mn + " button").eq(0).text(menu_text.menu5btn[0]);
                $("#" + mn + " button").eq(1).text(menu_text.menu5btn[1]);
            }else if(m==6){
                $("#" + mn + " button").text(menu_text.menu6btn);
            }
        }
        var alerts = d.mypage_alert[lang];
        suc = alerts.suc;
        really_out = alerts.really_out;
        get_out = alerts.get_out;
        not_4 = alerts.not_4;
        not_matching = alerts.not_matching;
        same_pass = alerts.same_pass;
        fail_a = alerts.fail_a;
    });
}

var suc = "";
var really_out = "";
var get_out = "";
var not_4 = "";
var not_matching = "";
var same_pass = "";
var fail_a = "";

// mypage 화면 이벤트
var my_event = function() {
    $("#menu_users li").off().on("click", function(){
        li_event(this, $(this).index() );
    });
    $("#menu_admin li").off().on("click", function(){
        li_event(this, $(this).index()+6 );
        $("#menu" + ($(this).index()+6) + " h2").text( $(this).text() );
        if( ($(this).index()+6)==7 ){
            makeList(1, "U");
            paging("/userCount", "#menu7page");
        }else if( ($(this).index()+6)==8 ){
            makeList(1, "F");
            paging("/FBCount", "#menu8page");
        }
    });
    updateID();
    updatePass();
    changeGetMail();
    deleteMe();
}

// list 공통이벤트
var li_event = function(t, i) {
    $("#menu_body > div").hide();
    $("#menu_body #menu" + i).show();
    $("#my_menu ul li").css("background-color", "");
    $("#my_menu ul li").css("color", "#005818");
    $(t).css("background-color", "#005818");
    $(t).css("color", "white");
}

// 로그인된 유저에 따른 변경
var myPageCss = function(userData){
    var userNo = userData.userNo;
    if(userNo==1){
        $("#menu_users").hide();
        $("#menu_admin").show();
        li_event( $("#menu_admin > li").eq(0) , 7);
        $("#menu7 h2").text( $("#menu_admin > li").eq(0).text() );
        makeList(1, "U");
        paging("/userCount", "#menu7page");
    }else{
        li_event( $("#menu_users > li").eq(0) , 1);
        $("#menu1 p").eq(0).children("span").text(userData.userEmail);
        $("#menu1 p").eq(1).children("span").text(userData.nickName);
        var dateData = userData.signDate.split(" ");
        $("#menu1 p").eq(2).children("span").text(dateData[0]+"-"+dateData[1]+"-"+dateData[2]);
        $("#menu2 p").eq(0).children("span").text(userData.revCount);
        $("#menu2 p").eq(1).children("span").text(userData.commCount);
        $("#menu3 input").eq(0).attr("value", userData.userEmail);
        $("#menu3 input").eq(1).attr("value", userData.nickName);
        if(userData.getMail=="Y"){
            btnCssToggle("#yesbtn", "#nobtn");
        }else if(userData.getMail=="N"){
            btnCssToggle("#nobtn", "#yesbtn");
        }
    }
}

// 이메일 발송 버튼 토글
var btnCssToggle = function(push, pull){
    $(push).css("background-color", "lightgrey");
    $(pull).css("background-color", "white");
    $(pull).hover(function(){
        $(this).css("background-color", "#e2e2e2")
    }, function(){
        $(this).css("background-color", "white")
    });
    $(push).css("box-shadow", "inset 0 3px 5px rgba(0,0,0,.125)");
    $(pull).css("box-shadow", "0");
}

// 유저 정보 업데이트
var updateID= function() {
    $("#menu3 button").off().on("click",function(){
        var newEmail = $("#menu3 input").eq(0).val();
        var newNick = $("#menu3 input").eq(1).val();
        $.ajax({
            type:"post",
            url:"/chID",
            data:{"email":newEmail, "nick":newNick}
        }).done(function(data){
            var d = JSON.parse(data);
            if(d.status==0){
                alert(fail_a);
            }else if(d.status==1){
                alert(suc);
                $("#menu1 p").eq(0).children("span").text(newEmail);
                $("#menu1 p").eq(1).children("span").text(newNick);
            }
        }).fail(function(){
            alert(fail_a);
        });
    });
}

// 유저 비밀번호 업데이트
var updatePass = function() {
    $("#menu4 button").off().on("click",function(){
        var newPass = $("#menu4 input").eq(0).val();
        var newPassCheck = $("#menu4 input").eq(1).val();
        if(newPass.length < 4) {
            alert(not_4);
            document.getElementById('newpwd').focus();
        }else if(newPass.length >= 4) {
            if(newPass!=newPassCheck){
                alert(not_matching);
                document.getElementById('newpwdCheck').focus();
            }else if(newPass==newPassCheck){
                $.ajax({
                    type:"post",
                    url:"/chPWD",
                    data:{"pwd":newPass}
                }).done(function(data){
                    var d = JSON.parse(data);
                    if(d.status==0){
                        alert(fail_a);
                    }else if(d.status==1){
                        alert(suc);
                    }else if(d.status==2){
                        alert(same_pass);
                        document.getElementById('newpwd').focus();
                    }
                }).fail(function(){
                    alert(fail_a);
                });
            }
        }
    });
}

// 알람 메일 발송설정 변경
var changeGetMail = function() {
    $("#menu5 button").off().on("click",function(){
        var pushBtn = $(this).attr('id');
        var isgetMail;
        if(pushBtn=="yesbtn"){
            isgetMail="Y";
        }else if(pushBtn=="nobtn"){
            isgetMail="N";
        }
        $.ajax({
            type:"post",
            url:"/chGetMail",
            data:{"getMail":isgetMail}
        }).done(function(data){
            var d = JSON.parse(data);
            if(d.status==0){
                alert(fail_a);
            }else if(d.status==1){
                if(isgetMail=="Y"){
                    btnCssToggle("#yesbtn","#nobtn");
                }else if(isgetMail=="N"){
                    btnCssToggle("#nobtn","#yesbtn");
                }
            }
        }).fail(function(){
            alert(fail_a);
        }).always(function(){
            changeGetMail();
        });
    });
}

// 유저 삭제
var deleteMe = function(){
    $("#menu6 button").off().on("click",function(){
        var tryOut = confirm(really_out);
        if(tryOut==true){
            $.ajax({
                type:"post",
                url:"/delUser"
            }).done(function(data){
                var d = JSON.parse(data);
                if(d.status==0){
                    alert(fail_a);
                }else if(d.status==1){
                    alert(get_out);
                    location.href="main.html";
                }
            }).fail(function(){
                alert(fail_a);
            });
        }
    })
}

// 유저 리스트 또는 피드백 리스트 불러오기
var makeList = function(pgNo, type){
    if(type=="U"){
        var target_url = "/userList";
        var target = "#menu7 .admin_list";
        var tableCol = "<tr><th>회원번호</th><th width='290'>메일 주소</th><th width='190'>닉네임</th><th>리뷰 수</th><th>가입일</th><th></th></tr>";
    }else if(type=="F"){
        var target_url = "/FBList";
        var target = "#menu8 .admin_list";
        var tableCol = "<tr><th width='40'>번호</th><th width='230'>메일 주소</th><th width='350'>타이틀</th><th>발송일</th><th>체크</th></tr>";
    }
    $.ajax({
        type:"post",
        url:target_url,
        data:{"limit": ((pgNo - 1) * 8) }
    }).done(function(data){
        var d = JSON.parse(data);
        $(target).empty();
        $(target).append(tableCol);
        for(var l=0; l<d.list.length; l++){
            var listData = d.list[l];
            var tableData = "<tr>";
            if(type=="U"){
                var dateData = listData.signDate.split(" ");
                tableData += "<td>" + listData.userNo + "</td>"
                tableData += "<td>" + listData.userEmail + "</td>"
                tableData += "<td>" + listData.nickName + "</td>"
                tableData += "<td>" + listData.revCount + "</td>"
                tableData += "<td>" + dateData[0]+"-"+dateData[1]+"-"+dateData[2] + "</td>"
                tableData += "<td><button type='button' id='userBtn" + listData.userNo + "' class='userOut'>탈퇴</button></td></tr>"
                $(target).append(tableData);
            }else if(type=="F"){
                var dateData = listData.fdDate.split(" ");
                tableData += "<td>" + listData.fdNo + "</td>"
                tableData += "<td>" + listData.fdEmail + "</td>"
                tableData += "<td id='fdNo" + listData.fdNo + "' class='fdTitle'>" + listData.fdTitle + "</td>"
                tableData += "<td>" + dateData[0]+"-"+dateData[1]+"-"+dateData[2] + "</td>"
                tableData += "<td><button type='button' id='fdBtn" + listData.fdNo + "' class='fdCheck'>확인</button></td></tr>"
                $(target).append(tableData);
                fdview( $("#fdNo" + listData.fdNo) , listData.fdMain );
            }
        }
        adminFunction();
    }).fail(function(){
        alert("ajax에 실패했습니다.");
    });
}

// 피드백 내용 hover로 보여주기
var fdview = function(fdNo, fdData) {
    var csstop = $(fdNo).offset().top + 56;
    var cssleft = $(fdNo).offset().left;
    var cssWidth = $(fdNo).width();
    $(fdNo).hover(function(){
        $("#menu8 > div #fdDetail").css("top", csstop);
        $("#menu8 > div #fdDetail").css("left", cssleft);
        $("#menu8 > div #fdDetail").width(cssWidth);
        $("#menu8 > div #fdDetail").text(fdData);
        $("#menu8 > div #fdDetail").show();
    }, function(){
        $("#menu8 > div #fdDetail").hide();
    });
}

// 페이징 기능
var paging = function(target_url, target) {
    $.ajax({
        type:"post",
        url:target_url
    }).done(function(data){
        var d = JSON.parse(data);
        if(d.count!=0){
            var count_calc = parseInt( (d.count - 1) / 8 ) + 1;
            var pageCount;
            if ( count_calc > 10 ) {
                pageCount = 10;
            } else {
                pageCount = count_calc;
            } 
            $(target).empty();
            for (var p=1; p<=pageCount; p++){
                $(target).append("<td>[" + p + "]</td>");
            }
            if ( count_calc > 10 ) {
                $(target).append("<td>[다음]</td>");
            }
        }
        page_click(target, count_calc);
    }).fail(function(){
        alert("ajax에 실패했습니다.");
    });
}

// 페이지 클릭
var page_click = function(target, count_calc){
    $(target + " td").off().on("click",function(){  // 이벤트 발생
        var pageList = $(target + " td"); // 대상
        page_text = $(this).text().replace("[", "").replace("]","");  // 클릭한 곳의 text
        first_text = pageList.eq(0).text().replace("[", "").replace("]","");  // 첫text
        last_text = pageList.eq( pageList.length-1 ).text().replace("[", "").replace("]",""); // 마지막text    
        if(page_text=="다음" || page_text=="이전"){ // 페이지버튼
            if(page_text=="다음"){      // 다음화면
                if(first_text=="이전"){   // 이전 페이지 있을 때
                    var right=1;
                    var page_length = 10;
                }else{                  // 이전 페이지 없을 때
                    var right=0;
                    var page_length = 9;
                    $(target).prepend("<td>[이전]</td>");
                }
                for(var r=right; r<=page_length; r++){
                    var page_Number = Number( pageList.eq(r).text().replace("[", "").replace("]","") );
                    page_Number=page_Number+10;
                    if(page_Number > count_calc){   // 보여줄 페이지 수가 적어질 경우 '다음' 삭제
                        pageList.eq(r).remove();
                        pageList.eq( pageList.length-1 ).remove();
                    }else{                          // 페이지 수 10개 충분할 경우
                        pageList.eq(r).text("[" + page_Number + "]");
                    }
                }
            }else if(page_text=="이전"){  // 이전화면
                var page_length = pageList.length-1;
                if(last_text=="다음") {   // 다음 페이지 있을 때
                    page_length = page_length-1;
                }
                for(var l=1; l<=page_length; l++){
                    var page_Number = Number( pageList.eq(l).text().replace("[", "").replace("]","") );
                    page_Number=page_Number-10;
                    pageList.eq(l).text("[" + page_Number + "]");
                    if(page_Number==1){     // 1페이지 도달 시 '이전' 삭제
                        pageList.eq(0).remove();
                    }
                }
                if(last_text!="다음") {  // 다음 페이지 없을 때
                    for(var a=1; a<=(10-page_length); a++){
                        $(target).append("<td>[" + (page_Number + a) + "]</td>");
                    }
                    $(target).append("<td>[다음]</td>");
                }
            }
        }else{
            if(target=="#menu7page"){
                makeList(page_text, "U");
            }else if(target=="#menu8page"){
                makeList(page_text, "F");
            }
        }
        page_click(target, count_calc);
    });
}

// 버튼 기능
var adminFunction = function(){
    $(".userOut").off().on("click",function(){
        adminBtnEvent(this, {"userNo":$(this).attr("id").replace("userBtn", "")},"/adminDelUser");
    });
    $(".fdCheck").off().on("click",function(){
        adminBtnEvent(this, {"feedbackNo":$(this).attr("id").replace("fdBtn", "")},"/FBCheck");
    });
}

var adminBtnEvent = function(target, targetData, target_url){
        $.ajax({
            type:"post",
            url:target_url,
            data:targetData
        }).done(function(data){
            var d = JSON.parse(data);
            if(d.status==0){
                alert("ajax에 실패했습니다.");
            }else if(d.status==1){
                alert("요청하신 처리가 완료되었습니다.");
                $(target).prop("disabled", true);
            }
        }).fail(function(){
            alert("ajax에 실패했습니다.");
        });
}