<?php 


	// header("Content-Type: application/json");
	
	if (session_status() == PHP_SESSION_NONE) {
    session_start();
	}
	
	$dbhost = "localhost";
	$dbname = "android";
	$dbuser = "root";
	$dbpass = "";
	$db = "";

	try {

		$db = new PDO("mysql:host=$dbhost;dbname=$dbname;", $dbuser, $dbpass);

	} catch (PDOException $e) {
		echo $e;
	}
	



 ?>