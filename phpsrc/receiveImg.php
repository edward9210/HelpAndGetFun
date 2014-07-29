<?php  
    $target_path  = SAE_TMP_PATH;//接收文件目录  
    $target_path = $target_path . basename( $_FILES['uploadedfile']['name']);  
    if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) {  
       print ("The file ".  basename( $_FILES['uploadedfile']['name']). " has been uploaded");  
    }  else{  
       print ("There was an error uploading the file, please try again!" . $_FILES['uploadedfile']['error']);  
    }  
?>  