<?php include("connection.php");
	
	$get_user = $_POST["get_user"];
	$query = $_POST["query"];
	if($con && $get_user == "true"){
		
		$users = array();
		$sel = mysqli_query($con,$query);
		if(mysqli_num_rows($sel) != 0){
			
			while($res = mysqli_fetch_array($sel)){
				array_push($users,array("name"=>$res["name"],"mobile"=>$res["mobile"],"date"=>$res["date"],"image"=>$res["image"]));
			}
			echo json_encode($users);
		}
	}
	
?>