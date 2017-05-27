<?php
 $db_name="attendancesystem";
 $mysql_user="root";
 $mysql_pass="";
 $servername="localhost";
 
		//require "connection.php";
		 $conn=mysqli_connect($servername,$mysql_user,$mysql_pass,$db_name);
			
			
			$name = $_POST['Name'];
			$extension = $_POST['Extension'];
		
			define('CSV_PATH','student_cache_uploads/');
			// path where your CSV file is located
			
			$csv_file = CSV_PATH.'student_'.$name.'.'.$extension; // Name of your CSV file
			 $sql_query = "insert into upload (Stream) values('abc')";
								mysqli_query($conn,$sql_query);
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
				$insert_csv['Time'] = $csv_array[1];
				$insert_csv['Latitude'] = $csv_array[2];
				$insert_csv['Longitude'] = $csv_array[3];
				$insert_csv['Otp'] = $csv_array[4];
				$insert_csv['Student_id'] = $csv_array[5];
				$insert_csv['Student_name'] = $csv_array[6];
				$insert_csv['Semester'] = $csv_array[7];
				$insert_csv['Stream'] = $csv_array[8];
				$insert_csv['Section'] = $csv_array[9];
				$insert_csv['imei'] = $csv_array[10];
				$insert_csv['androidid'] = $csv_array[11];
				if($insert_csv['Date'] != null)
				{
				$query = "INSERT INTO student_cache(Date,Time,Latitude,Longitude,Otp,Student_id,Student_name,Semester,Stream,Section,imei,androidid)
				VALUES('".$insert_csv['Date']."','".$insert_csv['Time']."','".$insert_csv['Latitude']."','".$insert_csv['Longitude']."','".$insert_csv['Otp']."',
				'".$insert_csv['Student_id']."','".$insert_csv['Student_name']."','".$insert_csv['Semester']."','".$insert_csv['Stream']."','".$insert_csv['Section']."','".$insert_csv['imei']."','".$insert_csv['androidid']."' )";
				mysqli_query($conn,$query);
				}
				$i++;
			}
			fclose($csvfile);

			echo "File data successfully imported to database!!";
			mysqli_close($conn);
		
				
		   
		  
 ?>

