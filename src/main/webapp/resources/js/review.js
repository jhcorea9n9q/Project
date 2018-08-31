// review.html 용 js
var revjs = function() {
    pageNo = 2;
    lan_li(pageNo);
    rev_lan();
    logintoggle(pageNo);
    lan_bnt();
}

var rev_lan = function() {
    $.ajax({
        url: "json/review.json"
    }).done(function(d){
        $("h1").html(d.reviews[lang]);
    });
}