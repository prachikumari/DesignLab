<?php
require "connection.php";

$stream = $_POST['Stream'];
$sql_query = "insert into upload (Stream) values('$stream')";
 mysqli_query($conn,$sql_query);
//if(mysqli_query($conn,$sql_query))
//echo
// "<h3> Data insertion successfull</h3>";
//else
	//echo "<h3> error while insertion</h3>";
?>