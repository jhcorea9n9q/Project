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
    });
}

// 언어변경 이벤트주기
var lan_bnt = function(){
    $("#cg_lan").off().on("click", function(){
        (lang==0)?lang=1:lang=0;
        sessionStorage.setItem("gd_lang", lang);
        lan_li(pageNo);
        if(pageNo==0){
            main_lan();  // 메인화면 언어변경 펑션(main.js참조)
        }else if(pageNo==3){
            sign_lan();  // 회원가입 화면 언어변경 펑션
        }else if(pageNo==4){
            login_lan(); // 로그인 화면 언어변경 펑션
        }else if(pageNo==5){
            my_lan();    // 마이페이지 화면 언어변경 펑션
        }
    });
}

var islogin = false;

// 로그인 세션 체크 & 세션에 따른 메뉴리스트 변화
var logintoggle = function(){
    if(islogin){
        $("#page3").hide();
        $("#page4").hide();
        $("#page5").show();
    }else{
        $("#page3").show();
        $("#page4").show();
        $("#page5").hide();
    }
}