// signup.html 용 js
var signjs = function() {
    pageNo = 3;
    lan_li(pageNo);
    sign_lan();
    logintoggle(pageNo);
    sign_event();
    lan_bnt();
}

// signup 화면 언어변경
var sign_lan = function() {
    $.ajax({
        url: "json/signup.json"
    }).done(function(d){
        var signData = d.agree[lang];
        $("#wel_head h2").text(signData.titles);
        $("#wel_main > p").html(signData.introduce);
        $("#wel_main > div").eq(0).html(signData.terms1);
        $("#wel_main label").eq(0).text(signData.agree1);
        $("#wel_main > div").eq(1).html(signData.terms2);
        $("#wel_main label").eq(1).text(signData.agree2);
        $("#wel_btn button").eq(0).text(signData.btn1);
        $("#wel_btn button").eq(1).text(signData.btn2);
        var alert_n_confirm = d.alert_n_confirm[lang];
        confirm_message = alert_n_confirm.confirm_message;
        alert_message = alert_n_confirm.alert_message;
        var sign = d.sign[lang];
        $.each(sign.sign_text,function(i,v){
            $("#sign_mainsec #sign_input p").eq(i).text(v);
        });
        $("#sign_input label").text(sign.email_agree);
        $("#sign_input div button").eq(0).text(sign.btn1);
        $("#sign_input div button").eq(1).text(sign.btn2);
        var sign_alert = d.sign_alert[lang];
        already_email = sign_alert.already_email;
        already_nick = sign_alert.already_nick;
        not_4 = sign_alert.not_4;
        not_matching = sign_alert.not_matching;
        fail_to_sign = sign_alert.fail_to_sign;
        succeed = sign_alert.success;
    });
}

var alert_message = "";
var confirm_message = "";
var fail_to_sign = "";
var already_email = "";
var already_nick = "";
var not_4 = "";
var not_matching = "";
var succeed = "";

// signup 화면 이벤트
var sign_event = function() {
	agreeToService("#wel_btn .sign_ok");
	noSign(".sign_no");
	trySignUp();
}

// 약관동의 이벤트
var agreeToService = function(btn) {
	$(btn).off().on("click", function(){
        if ( !$("#agree1").is(":checked") ) {       // 1번 체크 안됨
            alert(alert_message);
            $("html, body").animate({scrollTop:$("#terms1").offset().top}, 0);
        }else{
            if( !$("#agree2").is(":checked") ) {    // 2번 체크 안됨
                alert(alert_message);
            } else{
                $("#sign_mainsec").show();
                $("#welcome_goodee .wel_nothead").hide();
                $(window).scrollTop(0);
            }
        }
    });
}

// 가입취소 이벤트
var noSign = function(btn) {
	$(btn).on("click", function(){
	    var really_cancel = confirm(confirm_message);
	    if(really_cancel){
	        location.href = "main.html";
	    }
	});
}

// 회원가입 이벤트
var trySignUp = function() {
	$("#sign_input").off().submit(function(e){
        e.preventDefault();
        var email = $("#sign_input input").eq(0).val();
        var nick = $("#sign_input input").eq(1).val();
        var pwd = $("#sign_input input").eq(2).val();
        var pwd_check = $("#sign_input input").eq(3).val();
        var email_agree = $("#sign_input #email_agree").is(":checked");
        var getMail;
        if(email_agree){
            getMail="Y";
        }else{
            getMail="N";
        }
        $.ajax({
            type:"post",
            url:"/gdmovie/sign_up",
            data:{"email":email,"nick":nick,"pwd":pwd,"pwd2":pwd_check,"getMail":getMail}
        }).done(function(data){
            var d = JSON.parse(data);
            if(d.status==0){            // 회원가입 실패
                alert(fail_to_sign);
                document.getElementById('email').focus();
            }else if(d.status==2){      // 이메일 중복
                alert(already_email);
                document.getElementById('email').focus();
            }else if(d.status==3){      // 닉네임 중복
                alert(already_nick);
                document.getElementById('nick').focus();
            }else if(d.status==4){      // 비밀번호 4자리 미만
                alert(not_4);
                document.getElementById('pwd').focus();
            }else if(d.status==5){      // 재확인 비밀번호 불일치
                alert(not_matching);
                document.getElementById('pwd_check').focus();
            }else if(d.status==1){      // 회원가입 설공
                alert(nick + succeed);
                location.href = "main.html";
            }
        }).fail(function(){
        	alert(fail_to_sign);
            document.getElementById('email').focus();
        }); 
    });
}