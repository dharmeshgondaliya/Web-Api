<?php

	$con = mysqli_connect("localhost","root","","web_api");
	if(!$con){
		print("Connection Error");
		return;
	}
?>