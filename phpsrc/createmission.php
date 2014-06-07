<?php error_reporting(NULL); session_start(); include 'mysqlconnect.php';  ?>
<?php
  	$uname=$_POST["uname"];
    $mname=$_POST["mname"];
    $start=$_POST["start"];
    $end=$_POST["end"];
    $place=$_POST["place"];
    $ps=$_POST["ps"];
    include 'mysqlconnect.php';
    $sel=mysql_query("select * from mission where missionname='$mname'",$id);
    $rownum=mysql_num_rows($sel);
    if($rownum>0) print("mission already created");
    else{
  	$query="insert into mission(missionname,missiontime,missiondeadline,missionplace,missionps) value('$mname','$start','$end','$place','$ps')";
  	mysql_query($query,$id);
  	$query2="insert into cre_par(user,mission,cr_or_pr) value('$uname','$mname',0)";
  	mysql_query($query2,$id);
  	print("success");
  	  //$error=mysql_error();
  	  //echo $error."<br>";
    }
    mysql_close($id);
?>