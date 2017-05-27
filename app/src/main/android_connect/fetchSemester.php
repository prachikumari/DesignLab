<?php

		require "connection.php";
		$stream = $_POST["Stream"];
		$sql_query = "select * from Upload where Stream='$stream'";
		$result=mysqli_query($conn,$sql_query);
		if($sql_query)
		{
			while($row=mysqli_fetch_assoc($result))
			{
				$semester[] = $row;
			}
			print (json_encode($semester));
		}
		else
			print(json_encode(array("Cant read data from database")));

         
?>