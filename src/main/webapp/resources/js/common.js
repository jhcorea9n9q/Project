var pageNo = 0;

// 언어를 세션스토로지에 세팅하기
var lang_storage = function() {
    if(sessionStorage.getItem("gd_lang") == null) {
        sessionStorage.setItem("gd_lang", 0);
    }
    return sessionStorage.getItem("gd_lang");
}

var lang = lang_storage();

// 메뉴리스트 언어변경
var lan_li = function(pageNo){
    $.ajax({
        url: "json/common.json"
    }).done(function(d){
        $("title").text(d.titleData[pageNo][lang]);
        $("#cg_lan p").text(d.lanData[lang]);
        for(var i=0; i<d.HeadData[lang].length; i++) {
            $("#page" + (i+1)).children("a").text(d.HeadData[lang][i]);
        }
        var alerts = d.alerts[lang];
        a_logout = alerts.a_logout;
        a_notAllow = alerts.a_notAllow;
    });
}

var a_logout = "";
var a_notAllow = "";

// 언어변경 이벤트주기
var lan_bnt = function(){
    $("#cg_lan").off().on("click", function(){
        (lang==0)?lang=1:lang=0;
        sessionStorage.setItem("gd_lang", lang);
        if(pageNo==0){
            location.href='main.html';
        }else if(pageNo==3){
            location.href='signup.html';
        }else if(pageNo==4){
            location.href='login.html';
        }else if(pageNo==5){
            location.href='mypage.html';
        }else if(pageNo==1){
            location.href="stats.html";
        }else if(pageNo==2){
            location.href="review.html";
        }
    });
}

// 로그인 세션 체크 & 세션에 따른 메뉴리스트 변화(공용)
var logintoggle = function(pageNo){
    $("#page6").off().on("click",function(){
        alert(a_logout);
    });
    $.ajax({
        type:"post",
        url:"/sessionCheck"
    }).done(function(data){
        var d = JSON.parse(data);
        if(d.userSession==null){
            $(".outPage").show();
            $(".inPage").hide();
            if(pageNo==5){
                alert(a_notAllow);
                location.href = "main.html";
            }
        }else if(d.userSession!=null){
            $(".outPage").hide();
            $(".inPage").show();
            if(pageNo==3||pageNo==4){
                alert(a_notAllow);
                location.href = "main.html";
            }else if(pageNo==5){
                myPageCss(d.userSession);
            }
        }
    });
}