// login.html 용 js
var loginjs = function() {
    pageNo = 4;
    lan_li(pageNo);
    login_lan();
    logintoggle(pageNo);
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
        alert_wel = login_alert.welcome;
        alert_not4 = login_alert.not_4;
        alert_5 = login_alert.alert5;
        alert_fail = login_alert.login_fail;
    });
}

var alert_id = "";
var alert_pwd = "";
var alert_5 = "";
var alert_not4 = "";
var alert_wel = "";
var alert_fail = "";

// login 화면 이벤트
var login_event = function() {
    tryLogin();
}

var tryLogin = function() {
    $("section form").off().submit(function(e){
        e.preventDefault();
        var id = $("section form input").eq(0).val();
        var pwd = $("section form input").eq(1).val();
        if(pwd.length < 4){
            alert(alert_not4);
            document.getElementById('pwd').focus();
        }else{
            $.ajax({
                type:"post",
                url:"/gdmovie/tryLogin",
                data:{"id":id,"pwd":pwd}
            }).done(function(data){
                var d = JSON.parse(data);
                if(d.status==0){        // 이메일&닉네임 없음
                    alert(alert_id);
                    document.getElementById('id').focus();
                }else if(d.status==3){  // 비밀번호 틀림
                    alert(alert_pwd + "(" + d.failStack + ")");
                    document.getElementById('pwd').focus();
                }else if(d.status==2){  // 관리자 로그인
                    alert("관리 모드로 들어갑니다.");
                    location.href = "main.html";
                }else if(d.status==1){  // 일반유저 로그인
                    if(d.failStack >= 5){
                        alert(d.nick + alert_wel);
                        alert(d.nick + alert_5);
                    }else{
                        alert(d.nick + alert_wel);
                    }
                    location.href = "main.html";
                }
            }).fail(function(){
                alert(alert_fail);
            });
        }
    });
}