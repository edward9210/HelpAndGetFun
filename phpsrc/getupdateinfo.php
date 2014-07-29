<?php session_start(); include 'mysqlconnect.php';  ?>
<?php
  	$uname=$_POST["uname"];
    include 'mysqlconnect.php';
    $sel=mysql_query("select * from info where receiver='$uname' and state=0",$id);
    $rownum=mysql_num_rows($sel);
    if($rownum>0) 
    {
  	  print("success");
  	}
    mysql_close($id);
?>