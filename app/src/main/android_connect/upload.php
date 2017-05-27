 <?php

//importing dbDetails file
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
//response array

$response = array();
$fileinfo = array();
$stream = array();
 
		if($_SERVER['REQUEST_METHOD']=='POST')
		{  $flag = 0; 
			$conn=mysqli_connect($servername,$mysql_user,$mysql_pass,$db_name) or die('Unable to Connect...');
			 
			//checking the required parameters from the request
			if(isset($_POST['name']) and isset($_FILES['csv']['name']))
			{
		     
 
				//connecting to the database
				$conn=mysqli_connect($servername,$mysql_user,$mysql_pass,$db_name) or die('Unable to Connect...');
		 
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
				
				 $sql_query = "select (Initials) from teacher";
		           
		        $result=mysqli_query($conn,$sql_query);
		        if($sql_query)
		         {
			       while($row=mysqli_fetch_array($result))
			       {
				      $teacher[] = $row['Initials'];
			       }
			         
		         }
                               

				try{           

								
						          
								//in case you want to move  the file in uploads directory
								   $myname = explode('_', $name);
								   $streamname = $myname[0];
								   $semestername = $myname[2];
								   
								 	
						            $csvfile = fopen($_FILES['csv']['tmp_name'],'r');
									$theData = fgets($csvfile);
									$i = 0;
									while (!feof($csvfile))
									{
										$csv_data[] = fgets($csvfile, 1024);
										$csv_array = explode(",", $csv_data[$i]);
										$n = count($csv_array);
										
							            if($n == 10) 
							            {
										$insert_csv = array();
										$insert_csv['Day'] = $csv_array[0];
										$insert_csv['Stream'] = strtoupper($csv_array[1]);
										$insert_csv['Semester'] = $csv_array[2];
										$insert_csv['Section'] = $csv_array[3];
										$insert_csv['Period1'] = $csv_array[4];
										$insert_csv['Period2'] = $csv_array[5];
										$insert_csv['Period3'] = $csv_array[6];
										$insert_csv['Period4'] = $csv_array[7];
										$insert_csv['Period5'] = $csv_array[8];
										$insert_csv['Period6'] = $csv_array[9];
                                      
											
										if($insert_csv['Day'] == "" || $insert_csv['Stream'] == "" || $insert_csv['Semester'] == "" || $insert_csv['Section'] == ""
										|| $insert_csv['Period1'] == "" || $insert_csv['Period2'] == "" || $insert_csv['Period3'] == "" ||$insert_csv['Period4'] == ""
										|| $insert_csv['Period5'] == "" || $insert_csv['Period6'] == "")
										{
											$flag = 1;
											
										
										}
										elseif($insert_csv['Stream'] != $streamname)
										{
											$flag = 5;
										}
										elseif($insert_csv['Semester'] != $semestername)
										{
											$flag = 6;
										}
										
										elseif(substr($insert_csv['Period1'], 0, 1) === '(' || substr($insert_csv['Period2'], 0, 1) === '(' || substr($insert_csv['Period3'], 0, 1) === '(' || substr($insert_csv['Period4'], 0, 1) === '(' || substr($insert_csv['Period5'], 0, 1) === '(' || substr($insert_csv['Period6'], 0, 1) === '(' )
									   {
									   	$flag = 3;
									   }
								       elseif(strpos($insert_csv['Period1'], '(' ) === false || strpos($insert_csv['Period2'], '(' )  === false || strpos($insert_csv['Period3'], '(' )  === false || strpos($insert_csv['Period4'], '(' )  === false || strpos($insert_csv['Period5'], '(' )  === false || strpos($insert_csv['Period6'], '(' )  === false )
									   {
									     $flag = 4;
									  									
									   }
										   
									   else 
										     {   
											$confirmteacher1=0;
											$confirmteacher2=0;
											$confirmteacher3=0;
											$confirmteacher4=0;
											$confirmteacher5=0;
											$confirmteacher6=0;
											$myArray6 = explode('(', rtrim( $insert_csv['Period6'], ')' ) );
											$myArray1 = explode('(', rtrim($insert_csv['Period1'], ')'));
											$myArray2 = explode('(', rtrim($insert_csv['Period2'], ')'));
											$myArray3 = explode('(', rtrim($insert_csv['Period3'], ')'));
											$myArray4 = explode('(', rtrim($insert_csv['Period4'], ')'));
											$myArray5 = explode('(', rtrim($insert_csv['Period5'], ')'));
											$myArray61 = explode(')',$myArray6[1]);
											
											
											$l = count($teacher);
											for($p = 0; $p < $l; ++$p)
												{
												
												if($myArray1[1] == rtrim($teacher[$p]))
													$confirmteacher1 = 1;
												
											    if($myArray2[1] == rtrim($teacher[$p]))
													$confirmteacher2 = 1;
												if($myArray3[1] == rtrim($teacher[$p]))
													$confirmteacher3 = 1;
												if($myArray4[1] == rtrim($teacher[$p]))
													$confirmteacher4 = 1;
												if($myArray5[1] == rtrim($teacher[$p]))
													$confirmteacher5 = 1;
												if(ltrim($myArray61[0]) == rtrim($teacher[$p]))
													$confirmteacher6 = 1;
												}
												
								
											if($confirmteacher1 == 0 || $confirmteacher2 == 0 || $confirmteacher3 == 0 || $confirmteacher4 == 0 ||
											$confirmteacher5 == 0 || $confirmteacher6 == 0)
											{
											 $flag = 2;
											 
											}
										
										
										}
										}
										else
										{
											$flag = 10;
										}
										
			                             $i++;
			                        }    
				
								//$sql_query = "insert into upload (Stream) values('$flag')";
								//mysqli_query($conn,$sql_query);
				       
					  if($flag == 0)
					  {
					        if (is_uploaded_file($_FILES['csv']['tmp_name']))
							{   
			                     //$sql_query = "insert into upload (Stream) values('$flag')";
								//mysqli_query($conn,$sql_query);
								
					
										 if(move_uploaded_file($_FILES['csv']['tmp_name'], $file_path))
										 {
											 
										$response['yo'] = 'successfully uploaded';

										 }	
							         //readCSVFile($name,$extension);
							 	
							 }
										
					 }
					elseif($flag == 2)
					{
						$response['yo'] = 'Teacher not present in database';
						
					}
					elseif($flag == 3)
					{
						$response['yo'] = 'Subject not present in routine';
						
					}
					elseif($flag == 4)
					{
						$response['yo'] = 'Teacher code not present in routine';
						
					}
					elseif($flag == 5)
					{
						$response['yo'] = 'Stream mismatch';
						
					}
					elseif($flag == 6)
					{
						$response['yo'] = 'Semester mismatch';
						
					}
					elseif($flag == 10)
					{
						$response['yo'] = 'Columns missing or wrong csv';
						
					}
					else 
					{
											
						$response['yo'] = 'missing fields';
										
					}
										
								
								$sql_query = "INSERT INTO csvfiles(url,name) VALUES('$file_url','$name')";
								//mysqli_query($conn,$sql_query);
					//echo 'Here is some more debugging info:\n';
				   // print_r($_FILES);
					
					//$return= mysqli_query($con,$sql);
					 
					//adding the path and name to database
					if(mysqli_query($conn,$sql_query))
					{
					  		
						//filling response array with values
						//$response['error'] = false;
						//$response['url'] = $file_url;
					//$response['name'] = $name;
						
				    }
					 
					//if some error occurred
				}
				catch(Exception $e)
				{
					
					//$response['error']=true;
					//$response['message']=$e->getMessage();
				} 
				//closing the connection
				  //mysqli_close($conn);
				
		    }
		
		else
		{
				
				//$response['error']=true;
				//$response['message']='Please choose a file';
	    }
			//require 'sample.php';
			//displaying the response
			
			
		   
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
			// supply your database name
			
			define('CSV_PATH','upload/routine/');
			// path where your CSV file is located
			
			$csv_file = CSV_PATH.$name.'.'.$extension; // Name of your CSV file
			
			//echo $csv_file;
			
			$csvfile = fopen($csv_file,'r');
			
			$theData = fgets($csvfile);
			$i = 0;
			
			while (!feof($csvfile)) {
			$csv_data[] = fgets($csvfile,1024);
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
			
			$insert_csv['Period6'] = $csv_array[9];
			
			//$sql_query = "insert into upload (Stream) values('$insert_csv['Period6']')";
								//mysqli_query($conn,$sql_query);
			$query = "INSERT INTO routine(Day,Stream,Semester,Section,Period1,Period2,Period3,Period4,Period5,Period6)
			VALUES('".$insert_csv['Day']."','".$insert_csv['Stream']."','".$insert_csv['Semester']."','".$insert_csv['Section']."','".$insert_csv['Period1']."',
			'".$insert_csv['Period2']."','".$insert_csv['Period3']."','".$insert_csv['Period4']."','".$insert_csv['Period5']."','".$insert_csv['Period6']."') ON
			DUPLICATE KEY UPDATE Period1 = '".$insert_csv['Period1']."', Period2 = '".$insert_csv['Period2']."', Period3 = '".$insert_csv['Period3']."',
			Period4 = '".$insert_csv['Period4']."', Period5 '".$insert_csv['Period5']."', Period6 '".$insert_csv['Period6']."' ";
			mysqli_query($conn,$query);
			$i++;
			}
			fclose($csvfile);

			echo "File data successfully imported to database!!";
			mysqli_close($conn);
		
		}			
		   
		  
 ?>



