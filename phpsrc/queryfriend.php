<?php error_reporting(NULL); session_start(); include 'mysqlconnect.php';  ?>
<?php
      $uname=$_POST["uname"];
      include 'mysqlconnect.php';
      $sel=mysql_query("select tmp.*,user.headimg from (select user2 from is_friend where user1='$uname')as tmp, user where tmp.user2=user.username",$id);
      $rownum=mysql_num_rows($sel);
      $count=0;
      for($i=0;$i< $rownum;$i++){
      	$res[$count]=mysql_fetch_array($sel,MYSQL_ASSOC);
      	$count++;
      }
      foreach ($res as $a=>$b){
      	foreach ($b as $key=>$value){
      		$res[$a][$key]=urlencode($value);//iconv('gb2312','utf-8',$value)
      	}
      }
      
      //print_r($res);
      $result=json_encode($res);//,JSON_UNESCAPED_UNICODE
      $result2=urldecode($result);
      print($result2);
      mysql_close($id);
?>