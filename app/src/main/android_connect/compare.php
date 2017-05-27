<?php
 require_once 'connection.php';
 $db_name="attendancesystem";
 $mysql_user="root";
 $mysql_pass="";
 $servername="localhost";
 
 
//this is our upload folder
$upload_path = 'upload/routine/';

 
//Getting the server ip
$server_ip = gethostbyname(gethostname());

 $path='upload/routine/';
//creating the upload url
$upload_url = 'http://'.$server_ip.'/'.$upload_path;
 $up_url= 'http://'.$server_ip.'/'.$path;

			
			$fullname= $_POST["name"];
            $conn=mysqli_connect($servername,$mysql_user,$mysql_pass,$db_name);
			
			 //supply your database name
			
			define('CSV_PATH','upload/routine/');
			// path where your CSV file is located
			
			
			$csv_file = CSV_PATH.$fullname.'.csv'; // Name of your CSV file
			//echo $fullname;
			
			$csvfile = fopen($csv_file, 'r');
			$theData = fgets($csvfile);
			$i = 0;
			while (!feof($csvfile)) {
			$csv_data[] = fgets($csvfile, 1024);
			$csv_array = explode(",", $csv_data[$i]);
			$insert_csv = array();
			$insert_csv['Day'] = $csv_array[0];
			$insert_csv['Stream'] = $csv_array[1];
			$insert_csv['Semester'] = $csv_array[2];
			$insert_csv['Section'] = $csv_array[3];
			$insert_csv['Period1'] = $csv_array[4];
			$insert_csv['Period2'] = $csv_array[5];
			$insert_csv['Period3'] = $csv_array[6];
			$insert_csv['Period4'] = $csv_array[7];
			$insert_csv['Period5'] = $csv_array[8];
			$insert_csv['Period6'] = $csv_array[9];
			$sql_query = "insert into upload (Stream) values('abc')";
		    mysqli_query($conn,$sql_query);
			//echo $insert_csv['Period1'];
			$query = "INSERT INTO routine(Day,Stream,Semester,Section,Period1,Period2,Period3,Period4,Period5,Period6)
			VALUES('".$insert_csv['Day']."','".$insert_csv['Stream']."','".$insert_csv['Semester']."','".$insert_csv['Section']."','".$insert_csv['Period1']."',
			'".$insert_csv['Period2']."','".$insert_csv['Period3']."','".$insert_csv['Period4']."','".$insert_csv['Period5']."','".$insert_csv['Period6']."')";
			mysqli_query($conn,$query);
			$i++;
			}
			fclose($csvfile);

			echo "File data successfully imported to database!!";
			mysqli_close($conn);
		
					
		?>