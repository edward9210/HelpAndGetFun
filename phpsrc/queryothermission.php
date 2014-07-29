<?php error_reporting(NULL); session_start(); include 'mysqlconnect.php';  ?>
<?php
      $name=$_POST["name"];
      include 'mysqlconnect.php';
      $sel=mysql_query("select tmp.*,user.headimg from ((select * from cre_par,mission where cre_par.mission=mission.missionname and cre_par.cr_or_pr=0 and missionname in (select mission from cre_par where cr_or_pr=0 and user<>'$name' and user not in (select user2 from is_friend where user1='$name')) order by missiontime desc)as tmp, user) where user.username=tmp.user",$id);
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
