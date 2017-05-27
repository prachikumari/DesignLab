<?php
require "connection.php";
$stream = $_POST['Stream'];
$semester = $_POST['Semester'];
//$semester ='2';

$table = "Semester" . $semester;
$sql_query ="UPDATE upload SET $table='1' WHERE Stream='$stream' ";
mysqli_query($conn,$sql_query);

echo $stream;
echo $semester;
//if(mysqli_query($conn,$sql_query))
//echo "<h3> Data insertion successfull</h3>";
//else
	//echo "<h3> error while insertion</h3>";
?>