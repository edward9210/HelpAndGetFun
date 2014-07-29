<?php session_start(); include 'mysqlconnect.php';  ?>
<?php
  	$uname=$_POST["uname"];
    $mname=$_POST["mname"];
    include 'mysqlconnect.php';
    $sel=mysql_query("select * from cre_par where mission='$mname'",$id);
    $rownum=mysql_num_rows($sel);
    if($rownum != 2) 
    {
    	//if ($sel["cr_or_pr"]==0) print("created"); else print("participated");
    	print("can't do this");
    }
    else
    {
  	  $query="update mission set missionstate=2 where missionname='$mname'";
  	  mysql_query($query,$id);
  	  print("success");
  	}
    mysql_close($id);
?>
