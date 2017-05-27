<?php

		require "connection.php";
		$sql_query = "select distinct(Stream) from upload";
		
		$result=mysqli_query($conn,$sql_query);
		if($sql_query)
		{
			while($row=mysqli_fetch_assoc($result))
			{
				$stream[] = $row;
			}
			print (json_encode($stream));
		}
		else
			print(json_encode(array("Cant read data from database")));

         
?>