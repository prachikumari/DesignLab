 <?php

//importing dbDetails file
 require_once 'connection.php';
 $db_name="attendancesystem";
 $mysql_user="root";
 $mysql_pass="";
 $servername="localhost";
 
 
//this is our upload folder
$upload_path = 'upload/teacher/';

 
//Getting the server ip
$server_ip = gethostbyname(gethostname());

 $path='upload/teacher/';
//creating the upload url
$upload_url = 'http://'.$server_ip.'/'.$upload_path;
 $up_url= 'http://'.$server_ip.'/'.$path;
//response array
session_start(); 
$response = array();
$fileinfo = array();
$con=mysqli_connect($servername,$mysql_user,$mysql_pass,$db_name) or die('Unable to Connect...');
//$name = $_POST['name'];
//$extension =$fileinfo['extension'];
//$full=$name.$extension;
  //$_SESSION['sessionVar'] = $full;
  
 
		if($_SERVER['REQUEST_METHOD']=='POST')
		{   $flag = 0;
			 
			//checking the required parameters from the request
			if(isset($_POST['name']) and isset($_FILES['csv']['name'])){
		 
				//connecting to the database
				//$con=mysqli_connect($servername,$mysql_user,$mysql_pass,$db_name) or die('Unable to Connect...');
		 
				//getting name from the request
				$name = $_POST['name'];
				
		        // echo $name;
				//getting file info from the request
				$fileinfo = pathinfo($_FILES['csv']['name']);
				  
				//getting the file extension
				$extension =$fileinfo['extension'];
				
				//file url to store in the database
				//$getfileNum=getFileNum();
				
			   // $file_url = $up_url.getFileName().'.'.$extension;
				$file_url = $up_url.$name.'.'.$extension;
				
				
				//file path to upload in the server
				$file_path = $upload_path.$name.'.'.$extension;
				
				  
				//trying to save the file in the directory
				try{
							
						    $csvfile = fopen($_FILES['csv']['tmp_name'], 'r');
							$theData = fgets($csvfile);
							$i = 0;
							while (!feof($csvfile))
							{
							$csv_data[] = fgets($csvfile, 1024);
							$csv_array = explode(",", $csv_data[$i]);
							$n = count($csv_array);
							if($n == 5) {
							$insert_csv = array();
							$insert_csv[0] = $csv_array[0];
							$insert_csv[1] = $csv_array[1];
							$insert_csv[2] = $csv_array[2];
							$insert_csv[3] = $csv_array[3];
							$insert_csv[4] = $csv_array[4];
							 
							 // $sql_query = "insert into upload (Stream) values('$insert_csv[3]')";
								//mysqli_query($con,$sql_query);
							if (!filter_var($insert_csv[3], FILTER_VALIDATE_EMAIL) )
							{
								$flag = 1;
							}
							else
							{
								if(preg_match('/^\d{10}$/' , $insert_csv[4]) === false || ctype_digit(trim($insert_csv[4])) === false ) 
								{
									$flag = 2;
								}
							}
							}
							else{$flag = 3;
							}
								
			                             $i++;
			                }    
				      //$sql_query = "insert into upload (Stream) values('$flag')";
								//mysqli_query($con,$sql_query);
					 if($flag == 0)
					 {
					  if (is_uploaded_file($_FILES['csv']['tmp_name']))
							{     
								//in case you want to move  the file in uploads directory
								
								 if(move_uploaded_file($_FILES['csv']['tmp_name'], $file_path))
								 {//$sql_query = "insert into upload (Stream) values('$file_path')";
								// mysqli_query($conn,$sql_query);
								 $sql_query = "insert into upload (Stream) values('hj$flag')";
								mysqli_query($con,$sql_query);
								 }	
								  $response['yo'] = "Successfully Uploaded";
							}
					 }
					 elseif($flag == 2)
					 {
						 $response['yo'] = "Not valid contact number";
					 }
					 elseif($flag == 3)
					 {
						 $response['yo'] = "Missing Column or wrong csv";
					 }
					 else
					 {
					    $response['yo'] = "Not valid email id present";	 
					 }
								
								$sql = "INSERT INTO csvfiles(url,name) VALUES('$file_url','$name')";
					//echo 'Here is some more debugging info:\n';
				   // print_r($_FILES);
					
					//$return= mysqli_query($con,$sql);
					 
					//adding the path and name to database
					if(mysqli_query($con,$sql)){
							
						//filling response array with values
						/*$response['error'] = false;
						$response['url'] = $file_url;
						$response['name'] = $name;*/
						
					}
					 
					//if some error occurred
				}catch(Exception $e)
				{
					/*$response['error']=true;
					$response['message']=$e->getMessage();*/
				} 
				//closing the connection
				  //mysqli_close($conn);
				
			 }
			else{
				//$response['error']=true;
				//$response['message']='Please choose a file';
			    }
			//require 'sample.php';
			//displaying the response
		   // echo json_encode($response);
		     mysqli_close($conn);
			 print (json_encode($response));
			 if($flag == 0)
				 readCSVFile($name,$extension);
		 }
		// require_once 'connection.php';
         
		 function readCSVFile($name,$extension)
		{
			require "connection.php";
			//$connect = mysql_connect('localhost','root','');
			//if (!$connect) {
			//die('Could not connect to MySQL: ' . mysql_error());
			//}
            $conn=mysqli_connect($servername,$mysql_user,$mysql_pass,$db_name);
			//$cid =mysql_select_db('attendancesystem',$connect);
			 //supply your database name
			$sql_query = "insert into upload (Stream) values('teacher')";
		    mysqli_query($conn,$sql_query);
			define('CSV_PATH','upload/teacher/');
			// path where your CSV file is located
			
			
			$csv_file = CSV_PATH.$name.'.'.$extension; // Name of your CSV file
			$csvfile = fopen($csv_file, 'r');
			$theData = fgets($csvfile);
			$i = 0;
			while (!feof($csvfile)) {
			$csv_data[] = fgets($csvfile, 1024);
			$csv_array = explode(",", $csv_data[$i]);
			$insert_csv = array();
			$insert_csv['Employee_id'] = $csv_array[0];
			$insert_csv['Name'] = $csv_array[1];
			$insert_csv['Initials'] = $csv_array[2];
			$insert_csv['Email_id'] = $csv_array[3];
			$insert_csv['Contact_info'] = $csv_array[4];
			//echo $insert_csv['Period1'];
			$query = "INSERT INTO teacher(Employee_id,Name,Initials,Email_id,Contact_info)
			VALUES('".$insert_csv['Employee_id']."','".$insert_csv['Name']."','".$insert_csv['Initials']."',
			'".$insert_csv['Email_id']."','".$insert_csv['Contact_info']."') ON
			DUPLICATE KEY UPDATE Name = '".$insert_csv['Name']."', Initials = '".$insert_csv['Initials']."', Email_id = '".$insert_csv['Email_id']."',
			Contact_info = '".$insert_csv['Contact_info']."' ";
			mysqli_query($conn,$query);
			$i++;
			}
			fclose($csvfile);

			echo "File data successfully imported to database!!";
			mysqli_close($conn);
		
		}			
		
		  
 ?>



