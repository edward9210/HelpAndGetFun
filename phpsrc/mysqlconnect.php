<?php
  $host="localhost";
  $dbuser="root";
  $dbpass="";
  $id=mysql_connect($host,$dbuser,$dbpass);
  $ok=mysql_select_db("help_and_fun",$id);
  mysql_query("SET NAMES 'gb2312'",$id);
?>