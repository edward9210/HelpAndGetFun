<?php error_reporting(NULL); session_start(); include 'mysqlconnect.php';  ?>
<?php

  $name=$_POST["name"];
  if ($name!=""){
  	$password=$_POST["password"];
    include 'mysqlconnect.php';
    $query="select * from user where username='$name'";
    $result=mysql_query($query,$id);
    if (mysql_num_rows($result)<1){
    	print ("no user");
    }
    else{
    	$info=mysql_fetch_array($result);
    	if ($info['password']!=$password){
    		print ("password error");
    	}
    	else{
    		print ("success");
    		/*session_start();
    		$_SESSION["login"]="yes";
    		$_SESSION["uname"]=$name;
    		exit;*/
    	}
    }
    mysql_close($id);
  }
?>