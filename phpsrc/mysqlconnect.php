<?php
  $host="SAE_MYSQL_HOST_M";
  $dbuser="SAE_MYSQL_USER";
  $dbpass="SAE_MYSQL_PASS";
  $id=mysql_connect($host,$dbuser,$dbpass);
  $ok=mysql_select_db("app_helpandgetfun",$id);
  mysql_query("SET NAMES 'gb2312'",$id);
?>