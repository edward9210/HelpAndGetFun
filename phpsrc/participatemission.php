<?php session_start(); include 'mysqlconnect.php';  ?>
<?php
  	$uname=$_POST["uname"];
    $mname=$_POST["mname"];
    include 'mysqlconnect.php';
    $sel=mysql_query("select * from cre_par where mission='$mname' and cr_or_pr=1",$id);
    $rownum=mysql_num_rows($sel);
    if($rownum>0) 
    {
    	//if ($sel["cr_or_pr"]==0) print("created"); else print("participated");
    	print("can't do this");
    }
    else
    {
  	  $query2="insert into cre_par(user,mission,cr_or_pr) value('$uname','$mname',1)";
  	  mysql_query($query2,$id);
  	  $query="update mission set missionstate=1 where missionname='$mname'";
  	  mysql_query($query,$id);
  	  print("success");
  	}
    mysql_close($id);
?>