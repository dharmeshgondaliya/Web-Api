<?php include("connection.php");

	$name = $_POST["name"];
	$mobile = $_POST["mobile"];
	$password = $_POST["password"];
	
	
	$response = array();
	
	if($con && $name != "" && $mobile != "" && $password != ""){
		$insert = mysqli_query($con,"insert into users(name,mobile,password,image) values('".$name."','".$mobile."','".$password."','demo.jpeg')");
	}
	if($insert != 0){
		array_push($response,array("register"=>"success"));
	}
	else{
		array_push($response,array("register"=>"fail"));
	}
	echo json_encode($response);
?>