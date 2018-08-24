// main.html용 js
var indexjs = function(){
    lan_li(pageNo);
    main_lan();
    logintoggle(pageNo);
    main_event();
    lan_bnt();
}

// main 화면 언어변경
var main_lan = function() {
    $.ajax({
        url: "json/main.json"
    }).done(function(d){
        $("#s1_d h1").text(d.mainH1[lang]);
        for(var h=1; h<4; h++) {
            $("#s"+h+"_d article p").html(d.divData[h-1][lang]);
        }
        $("#s4_d1 b p").text(d.div2Data[0][lang]);
        $("#s4_d2 b p").text(d.div2Data[1][lang]);
        var fbData = d.feedback[lang];
        $("#feedback #fd_req").text(fbData.fd_request);
        $("#feedback form input").eq(0).attr("placeholder", fbData.fd_email);
        $("#feedback form input").eq(1).attr("placeholder", fbData.fd_title);
        $("#feedback form textarea").attr("placeholder", fbData.fd_main);
        $("#feedback form button").eq(0).text(fbData.fd_submit);
        $("#feedback form button").eq(1).text(fbData.fd_cancel); 
        $("#thankyou p").text(d.fd_thank[lang]);
        $("#feedback form p").eq(0).text(fbData.fd_email_er);
        $("#feedback form p").eq(1).text(fbData.fd_title_er);
        $("#feedback form p").eq(2).text(fbData.fd_main_er);
    });
}

// main 화면 이벤트
var main_event = function() {
    $.ajax({
        url: "json/main.json"
    }).done(function(d){
        for (var i=0; i<d.reco.length; i++) {
            $("#s4 #s4_d1 #top_movielist li").eq(i).children("div").css("background-image", "url(https://image.tmdb.org/t/p/original/"+d.reco[i].URL+")");
            $("#s4 #s4_d1 #top_movielist li").eq(i).children("h3").text(d.reco[i].Rank);
            $("#s4 #s4_d1 #top_movielist li").eq(i).children("p").text(d.reco[i].Title);
        }
        for (var j=0; j<d.cont.length; j++) {
            $("#s4 #s4_d2 #top_contributor li").eq(j).text(d.cont[j].contributor + " (" + d.cont[j].count + ")");
        }
    });
    main_button();
}

// main 화면 3가지 버튼 이벤트
var main_button = function() {
    $("#s1_b").on("click", function(){
        location.href="stats.html";
    });
    $("#s2_b").on("click", function(){
        location.href="review.html";
    });
    $("#s3_b").off().on("click", function(){
        $("#feedback").animate({bottom: "25px"}, 600);
        feedback();
    });
}

// feedback 전송/취소 버튼 이벤트
var feedback = function() {
    $("#feedback #can_btn").off().on("click", function(){
        feedbackCancel();
    });
    $("#feedback form").off().submit(function(e){
        e.preventDefault();
        var fd_height = 300;
        var fd_email = $("#feedback form input").eq(0).val();
        var fd_title = $("#feedback form input").eq(1).val();
        var fd_main = $("#feedback form textarea").val();
        fd_height += feedbackError(fd_email, 0);
        fd_height += feedbackError(fd_title, 1);
        fd_height += feedbackError(fd_main, 2);
        $("#feedback").css("height", fd_height);     
        if(fd_height == 300) {
            console.log(fd_email, " | ", fd_title, " | ", fd_main); // 자바로 보낼것
            feedbackCancel();
            feedbackOK();
        }
    });
}

// feedback 내용 미입력에 따른 CSS변화
var feedbackError = function(fd_text, eqNo) {
    if(fd_text == "") {
        $("#feedback form p").eq(eqNo).show();
        return 33;
    }else{
        $("#feedback form p").eq(eqNo).hide();
        return 0;
    }
}

// feedback 취소시
var feedbackCancel = function() {
    $("#feedback form p").hide();
    $("#feedback").css("height", 300);
    $("#feedback").animate({bottom: "-360px"}, 600);
    $("#feedback input").val("");
    $("#feedback textarea").val("");
}

// feedback 완료시
var feedbackOK = function() {
    var fbO = setTimeout(function(){
        $("#thankyou").animate({bottom: "25px"}, 600);
        $("#thankyou h3").off().on("click", function(){
            $("#thankyou").animate({bottom: "-140px"}, 600);
        });
    }, 600);
}