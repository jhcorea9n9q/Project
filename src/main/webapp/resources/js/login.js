// login.html 용 js
var loginjs = function() {
    pageNo = 4;
    lan_li(pageNo);
    login_lan();
    login_event();
    lan_bnt();
}

// login 화면 언어변경
var login_lan = function() {
    $.ajax({
        url: "json/login.json"
    }).done(function(d){
        var login_text = d.login_text[lang];
        $("section h2").text(login_text.titles);
        $("section > p").html(login_text.sign);
        $("section form p").eq(0).text(login_text.login_id);
        $("section form p").eq(1).text(login_text.login_pwd);
        $("section form button").text(login_text.titles);
        var login_alert = d.login_alert[lang];
        alert_id = login_alert.no_id;
        alert_pwd = login_alert.not_pass;
        alert_5 = login_alert.alert5;
    });
}

var alert_id = "";
var alert_pwd = "";
var alert_5 = "";

// login 화면 이벤트
var login_event = function() {
    var fail_stack = 0;
    $("section form").off().submit(function(e){
        e.preventDefault();
        var id = $("section form input").eq(0).val();
        var pwd = $("section form input").eq(1).val();
        fail_stack += 1;
        console.log(id,pwd);
        if(fail_stack > 4) {
            alert(id + alert_5);
        } 
    });
}