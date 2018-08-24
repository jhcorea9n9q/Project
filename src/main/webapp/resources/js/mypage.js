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
        
    });
}

// mypage 화면 이벤트
var my_event = function() {
    $("#menu_users li").off().on("click", function(){
        li_event(this, $(this).index() );
    });
    $("#menu_admin li").off().on("click", function(){
        li_event(this, $(this).index()+6 );
        $("#menu" + ($(this).index()+6) + " h2").text( $(this).text() );
    });
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