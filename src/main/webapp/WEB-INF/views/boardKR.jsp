<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<link rel="stylesheet" href="/page/css/common.css">
<link rel="stylesheet" href="/page/css/review.css">
<link rel='shortcut icon' href="/page/img/icon.png">
<link href="https://fonts.googleapis.com/css?family=Kosugi+Maru|Sunflower:300" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="/page/js/common.js"></script>
<script src="/page/js/review.js"></script>
<script>
$(document).ready(function(){
    revjs();
});
</script>
</head>
<body>
<header id="mainhead" class="bc_w dp_i f_l give_pad">
    <a id="headlogo" class="dp_i" href="/page/main.html"></a>
</header>
<ul id="mainli" class="dp_i f_l give_pad">
    <li id="page1" class="f_l"><a href="/page/stats.html"></a></li>
    <li id="page2" class="f_l"><a href="/reviewBoard"></a></li>
    <li id="page3" class="f_r outPage"><a href="/page/signup.html"></a></li>
    <li id="page4" class="f_r outPage"><a href="/page/login.html"></a></li>
    <li id="page6" class="f_r inPage"><a href="/logout"></a></li>
    <li id="page5" class="f_r inPage"><a href="/page/mypage.html"></a></li>
    <span id="cg_lan" class="bc_w f_r"><p></p></span>
</ul>
<section class="dp_i f_l give_pad">
    <h2 class="dp_i f_l">리뷰 게시판</h2>
        <div id="revInsert" class="dp_i f_l give_pad">
            <form id='insertForm'>
                <input type='text' name='revTitle' id='revTitle' class='inClass' placeholder='제목을 입력해 주십시오.' maxlength="30" required><br>
                <div id='movieSector'>
                    <input type='text' name='movieTitle' id='movieTitle' placeholder='영화 제목을 입력해 주십시오.' maxlength="30" class="dp_i f_l" required>
                    <select name='movieGrade' placeholder='평점을 매겨주십시오.' id='movieGrade' class="dp_i f_l">
                        <option value="1">★☆☆☆☆</option>
                        <option value="2">★★☆☆☆</option>
                        <option value="3">★★★☆☆</option>
                        <option value="4">★★★★☆</option>
                        <option value="5">★★★★★</option>
                    </select><br>
                </div>
                <textarea name='revContents' id='revContents' class='inClass' placeholder="리뷰 내용을 입력해 주십시오." maxlength="500"></textarea><br>
                <button type="submit">완료!</button>
            </form>
        </div>
        <hr>
    <div id="revDetails" class="dp_i f_l give_pad">
        <div id="revHeader">
            <h2></h2>
            <span></span>
            <h3></h3>
        </div>
        <hr>
        <div id="revInfo">
            <p>작성자 : <span></span></p>
            <p>조회수 : <span></span></p>
            <p>추천수 : <span></span></p>
            <p>작성일 : <span></span></p>
        </div>
        <hr>
        <div id="revMain"></div>
    </div>
    <hr>
    <div id="revList" class="dp_i f_l">
        <table></table>
    </div>
    <hr>
    <div id="pageList" class="dp_i f_l">
        <table>
            <tr>
                <td>◀</td>
<!--                <td>1</td>
                <td>2</td>
                <td>3</td>
                <td>4</td>
                <td>5</td>
                <td>6</td>
                <td>7</td>
                <td>8</td>
                <td>9</td>
                <td>10</td>-->
                <td>▶</td>
            </tr>
        </table>
    </div>
    <hr>
    <div id="searchDiv" class="dp_i f_l">
        <select id='find' name='find'>
            <option value="find_title">제목</option>
            <option value="find_contents">내용</option>
            <option value="find_nick">닉네임</option>
            <option value="find_movie">영화명</option>
        </select>
        <input type="text" name="search" maxlength="25">
        <button type="button" disabled>검색!</button>
    </div>
    <div id="insertBtn" class="dp_i f_l">
        <button type="button" class="f_r">글쓰기</button>
    </div>
</section>
</body>
</html>
