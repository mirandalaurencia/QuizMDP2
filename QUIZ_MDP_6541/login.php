<?php
	$response = array();
	if (isset($_GET["username"]) && isset($_GET["password"])) {
		$username = $_GET["username"];
		$password = $_GET["password"];
		if ($username == $password) {
			$response["code"] = 1;
			$response["message"] = "Welcome, " . $username . "!";
		} else {
			$response["code"] = -1;
			$response["message"] = "Login Failed!";
		}
	} else {
		$response["code"] = -2;
		$response["message"] = "Invalid Data!";
	}
	echo json_encode($response);
?>