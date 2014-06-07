<?php /*error_reporting(NULL);*/ session_start(); include 'mysqlconnect.php';  ?>
<?php
    $uname=$_POST["name"];
    $name=$_POST["realname"];
    $password=$_POST["password"];
    $phone=$_POST["phone"];

		include 'mysqlconnect.php'; 
		$query="select * from user where username='$uname'";
    $result=mysql_query($query,$id);
    if (mysql_num_rows($result)>0){
    	print ("username used");
    }
    else
    {
      $query2="insert into user(username,realname,password,phone) value('$uname','$name','$password','$phone')";
      mysql_query($query2,$id);
      print ("success");
    }
    mysql_close($id);
    
?>