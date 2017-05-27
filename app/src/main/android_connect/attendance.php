<?php
require "connection.php";
$sql_query = "select * from teacher_cache";
		
		$result=mysqli_query($conn,$sql_query);
$flag =1;
while ($row = mysqli_fetch_array($result)) {
	$android = array();
	$i =0;
	$startt = $row["Start_time"];
	$endt = $row["End_Time"];
	$starttime = new DateTime($startt);
	$endtime = new DateTime($endt);
	$latitude = $row["Latitude"];
	$longitude = $row["Longitude"];
	$latitudei = (int)$latitude;
	$latitudei = intval($latitudei);
	$longitudei = (int)$longitude;
	$longitudei = intval($longitudei);
	$sql_query1 = "select * from student_cache where Date = '".$row["Date"]."' and Stream = '".$row["Stream"]."' and Semester =
	'".$row["Semester"]."' and Section = '".$row["Section"]."' ";
    $result2=mysqli_query($conn , $sql_query1);
	while($row2 = mysqli_fetch_array($result2)){
		$currenttime = $row2["Time"];
		$checka = 0;
		foreach($android as $value)
		{
			if($value == $row2["androidid"])
			{
			      $checka = 1;
			}
		}
		if($checka == 0){
			$i = $i + 1;
			$android[$i] = $row2["androidid"];
		$studenttime = new DateTime($currenttime);
		$studlat = $row2["Latitude"];
		$studlati = (int)$studlat;
		$studlati = intval($studlati);
		$studlong = $row2["Longitude"];
		$studlongi = $studlong;
		$studlongi = intval($studlongi);
		if($row["Otp"] == $row2["Otp"] && $studenttime >= $starttime && $studenttime <= $endtime && $latitudei == $studlati && $longitudei == $studlongi)	
		{    $sql_query4 = "SELECT * FROM attendance where Enrolment_id = '".$row2["Student_id"]."' ";
	         $result3 = mysqli_query($conn,$sql_query4);
           	
             if (mysqli_num_rows($result3)==0){			
			$sql_query3 = "insert into attendance(Enrolment_id,Total_count) VALUES('".$row2["Student_id"]."','".$flag."') ";
            mysqli_query($conn,$sql_query3);
			 }
			 else 
			 {
				 $sql_query5 = "UPDATE attendance SET Total_count = Total_count + 1 where Enrolment_id = '".$row2["Student_id"]."' ";
				mysqli_query($conn,$sql_query5); 
			 }
		}
		}
	}
}
//$sql_query = "insert into upload (Stream) values('$flag')";
 //mysqli_query($conn,$sql_query);
?>