<?php include("connection.php");

	$mobile = $_POST["mobile"];
	$password = $_POST["password"];
	$response = array();
	
	if($con && $mobile != "" && $password != ""){
		$sel = mysqli_query($con,"select * from users where mobile='$mobile' and password='$password'");
		if($res = mysqli_fetch_array($sel)){
			array_push($response,array("image"=>$res["image"]));
		}
		echo json_encode($response);
	}
?>