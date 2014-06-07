<?php session_start(); include 'mysqlconnect.php';  ?>
<?php

  $name=$_POST["name"];
  if ($name!=""){	
    include 'mysqlconnect.php';
    $query="select * from user where username='$name'";
    $sel=mysql_query($query,$id);
    $rownum=mysql_num_rows($sel);
    $Count=0;
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
  }
?>