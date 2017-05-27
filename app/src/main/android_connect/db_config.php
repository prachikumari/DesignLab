<?php
/**
All database connection variables here
 **/
 define('DB_USER',"root");
 define('DB_PASSWORD',"");
 define('DB_DATABASE',"ATTENDANCESYSTEM");
 define('DB_SERVER',"localhost");
 
 $db_name="ATTENDANCESYSTEM";
 $mysql_user="root";
 $mysql_pass="";
 $servername="localhost";
 
 $con=mysqli_connect($servername,$mysql_user,$mysql_pass,$db_name);
 if(!$conn)
 {
	 echo "Connection error".mysqli_connect_error();
 }
 else
 {
	 echo "<h3> Connection Successfull</h3>";
 }
 
?>