<?php session_start(); include 'mysqlconnect.php';  ?>
<?php
  	$uname=$_POST["uname"];
    $sendername=$_POST["sendername"];
    $content=$_POST["content"];
    include 'mysqlconnect.php';
    $query="insert into info(sender,receiver,mes) value('$uname','$sendername','$content')";
    mysql_query($query,$id);
    print("success");
    mysql_close($id);
?>