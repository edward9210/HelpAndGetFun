<?php /*error_reporting(NULL);*/ session_start(); include 'mysqlconnect.php';  ?>
<?php
    $uname=$_POST["name"];
    $name=$_POST["realname"];
    $password=$_POST["password"];
    $phone=$_POST["phone"];

		include 'mysqlconnect.php'; 
		$query="select * from user where username='$uname'";
    $result=mysql_query($query,$id);
    if (mysql_num_rows($result)==0){
    	print ("no such user");
    }
    else
    {
    	$res=mysql_fetch_array($result);
    	if ($uname==$res["username"] && $name==$res["realname"] && $phone==$res["phone"]){
    		$query="update user set password='$password' where username='$uname'";
    		mysql_query($query,$id);
    		print("success");
    	}
      else{
      	print("message error");
      }      
    }
    mysql_close($id);
    
?>