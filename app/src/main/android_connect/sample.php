
<?php
require "connection.php";
$connect = mysql_connect('localhost','root','');
if (!$connect) {
die('Could not connect to MySQL: ' . mysql_error());
}

$cid =mysql_select_db('attendancesystem',$connect);
// supply your database name
session_start(); 
define('CSV_PATH','upload/routine/');
// path where your CSV file is located
$fullname = $_SESSION['sessionVar'];
//$csv_file = CSV_PATH.$name.$extension; // Name of your CSV file
$csv_file = CSV_PATH.$fullname;
echo $csv_file;
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
echo $insert_csv['Period1'];
$query = "INSERT INTO routine(Day,Stream,Semester,Section,Period1,Period2,Period3,Period4,Period5,Period6)
VALUES('".$insert_csv['Day']."','".$insert_csv['Stream']."','".$insert_csv['Semester']."','".$insert_csv['Section']."','".$insert_csv['Period1']."',
'".$insert_csv['Period2']."','".$insert_csv['Period3']."','".$insert_csv['Period4']."','".$insert_csv['Period5']."','".$insert_csv['Period6']."')";
$n=mysql_query($query, $connect);
$i++;
}
fclose($csvfile);

echo "File data successfully imported to database!!";
mysql_close($connect);
?> 