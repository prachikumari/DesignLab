<?php
 $db_name="attendancesystem";
 $mysql_user="root";
 $mysql_pass="";
 $servername="localhost";
 
		//require "connection.php";
		 $conn=mysqli_connect($servername,$mysql_user,$mysql_pass,$db_name);
			
			
			$name = $_POST['Name'];
			$extension = $_POST['Extension'];
		
			define('CSV_PATH','teacher_cache_uploads/');
			// path where your CSV file is located
			
			$csv_file = CSV_PATH.'teacher_'.$name.'.'.$extension; // Name of your CSV file
			 
			//echo $csv_file;
			
			$csvfile = fopen($csv_file,'r');
			//$csv_array = array();
			$theData = fgets($csvfile);
			$i = 0;
			
			while (!feof($csvfile)) {
				$csv_data[] = fgets($csvfile,1024);
				$csv_array = explode(",",$csv_data[$i]);
				$insert_csv = array();
				$insert_csv['Date'] = $csv_array[0];
				$insert_csv['Otp'] = $csv_array[1];
				$insert_csv['Latitude'] = $csv_array[2];
				$insert_csv['Longitude'] = $csv_array[3];
				$insert_csv['Start_time'] = $csv_array[4];
				$insert_csv['End_Time'] = $csv_array[5];
				$insert_csv['Stream'] = $csv_array[6];
				$insert_csv['Semester'] = $csv_array[7];
				$insert_csv['Section'] = $csv_array[8];
				$insert_csv['Period'] = $csv_array[9];
				//$sql_query = "insert into upload (Stream) values('$csv_array[4]')";
								//	mysqli_query($conn,$sql_query);
				if($insert_csv['Date'] != null){
				$query = "INSERT INTO teacher_cache(Date,Otp,Latitude,Longitude,Start_time,End_Time,Stream,Semester,Section,Period)
				VALUES('".$insert_csv['Date']."','".$insert_csv['Otp']."','".$insert_csv['Latitude']."','".$insert_csv['Longitude']."','".$insert_csv['Start_time']."',
				'".$insert_csv['End_Time']."','".$insert_csv['Stream']."','".$insert_csv['Semester']."','".$insert_csv['Section']."','".$insert_csv['Period']."')";
				mysqli_query($conn,$query);
				}
				$i++;
			}
			fclose($csvfile);

			echo "File data successfully imported to database!!";
			mysqli_close($conn);
		
				
		   
		  
 ?>

