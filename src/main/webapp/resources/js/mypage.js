// mypage.html 용 js
var myjs = function() {
    pageNo = 5;
    lan_li(pageNo);
    my_lan();
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
                $("#" + mn + " p").eq(ind).text(val);
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
    li_event( $("#menu_users > li").eq(0) , 1);
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