<?php error_reporting(NULL); session_start(); include 'mysqlconnect.php';  ?>
<?php
  $name1=$_POST["name1"];
  $name2=$_POST["name2"];
  
  if ($name1!="" && $name2!=""){
    include 'mysqlconnect.php';
    $query="insert into is_friend(user1,user2) value('$name1','$name2')";
    $result=mysql_query($query,$id);
    /*$query2="insert into is_friend(user1,user2) value('$name2','$name1')";
    $result=mysql_query($query2,$id);*/
    print("success");
    mysql_close($id);
  }
?>