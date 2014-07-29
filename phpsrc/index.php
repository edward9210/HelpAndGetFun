<?php error_reporting(NULL); session_start(); include 'mysqlconnect.php';  ?>
<?php
    header("Content-Type: text/html; charset=gb2312");
    /*
    $_POST["uname"]="edward123";
    $_POST["label"]="queryparticipated";
    $_POST["mname"]="ÀÏÂ·";
    $_POST["realname"]="Â¬Ðñ¹â";
    $_POST["password"]="123";
    $_POST["phone"]="123";
    */
    if ($_POST["label"]=="getuser") 
    {
    	include 'getuser.php';
    }
    if ($_POST["label"]=="login") 
    {
    	include 'login.php';
    }
    if ($_POST["label"]=="reg")
    {
    	include 'success.php';
    }
    if ($_POST["label"]=="querymission")
    {
    	include 'querymission.php';
    }
    if ($_POST["label"]=="queryfriendmission")
    {
    	include 'queryfriendmission.php';
    }
    if ($_POST["label"]=="queryothermission")
    {
    	include 'queryothermission.php';
    }
    if ($_POST["label"]=="queryfriend")
    {
    	include "queryfriend.php";
    }
    if ($_POST["label"]=="querycreated")
    {
    	include "querycreated.php";
    }
    if ($_POST["label"]=="queryparticipated")
    {
    	include "queryparticipated.php";
    }
    if ($_POST["label"]=="createmission")
    {
    	include "createmission.php";
    }
    if ($_POST["label"]=="makefriends")
    {
    	include "makefriends.php";
    }
    if ($_POST["label"]=="participatemission") 
    {
    	include 'participatemission.php';
    }
    if ($_POST["label"]=="quitmission") 
    {
    	include 'quitmission.php';
    }
    if ($_POST["label"]=="deletemission") 
    {
    	include 'deletemission.php';
    }
    if ($_POST["label"]=="forgetpassword") 
    {
    	include 'forgetpassword.php';
    }
    if ($_POST["label"]=="deletefriends") 
    {
    	include 'deletefriends.php';
    }
    if ($_POST["label"]=="changeinfo") 
    {
    	include 'changeinfo.php';
    }
    if ($_POST["label"]=="getupdateinfo") 
    {
        include 'getupdateinfo.php';
    }
    if ($_POST["label"]=="getinfolist") 
    {
        include 'getinfolist.php';
    }
    if ($_POST["label"]=="sendmes") 
    {
        include 'sendmes.php';
    }
    if ($_POST["label"]=="finishmission") 
    {
        include 'finishmission.php';
    }
    //print_r($_POST);
?>