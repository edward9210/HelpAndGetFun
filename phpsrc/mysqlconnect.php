<?php
  $id=mysql_connect(SAE_MYSQL_HOST_M.':'.SAE_MYSQL_PORT,SAE_MYSQL_USER,SAE_MYSQL_PASS);
  $ok=mysql_select_db("app_helpandgetfun",$id);
  mysql_query("SET NAMES 'gb2312'",$id);
?>