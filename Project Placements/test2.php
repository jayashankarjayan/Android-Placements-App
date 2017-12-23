<?php

    $uname = $_GET["uname"];

    require_once("./connection.php");

    $conn = mysqli_connect($host,$username,$password,$database) or die("Error connecting to server");

    $getstudentdetailsq = "SELECT * FROM $studentstable WHERE Username = '$uname';";

    $getstudentdetailsres = mysqli_query($conn,$getstudentdetailsq);

    $getstudentdetailsrow = mysqli_fetch_assoc($getstudentdetailsres);

    if($getstudentdetailsrow["rid"] != $_GET["rsid"]){
        die();
        exit();
    }


?>
<?php
	if($_SERVER["REQUEST_METHOD"] == "GET"){
		$uname = $_GET["uname"];
		$tablename = $_GET["tab_name"];



		require_once("./connection.php");

		$conn = mysqli_connect($host,$username,$password,$database) or die("Error connecting to server.");

		$getstudentsdetsq = "SELECT * FROM $studentstable WHERE Username='$uname';";

		$getstudentsdetsres = mysqli_query($conn,$getstudentsdetsq);

		$getstudentsdetsrow = mysqli_fetch_assoc($getstudentsdetsres);

		$name_of_student = $getstudentsdetsrow["Name_of_the_Student"];
		$gender = $getstudentsdetsrow["Gender"];
		$email = $getstudentsdetsrow["Email"];
		$student_image = $getstudentsdetsrow["Student_Image"];
		$roll_no = $getstudentsdetsrow["Roll_No"];
		$cv = $getstudentsdetsrow["Student_CV"];
		$nkt = $getstudentsdetsrow["Number_of_KT"];
		$contact_number = $getstudentsdetsrow["Contact_Number"];
		$college = $getstudentsdetsrow["College_ID"];
		$course = $getstudentsdetsrow["Course"];

		$getcompanydetsq = "SELECT * FROM $companiestable WHERE Table_Name='$tablename';";
		$getcompanydetsres = mysqli_query($conn,$getcompanydetsq);
		$getcompanydetsrow = mysqli_fetch_assoc($getcompanydetsres);

		$maxbacklogs = $getcompanydetsrow["Maximum_Backlogs"];


		if($cv != null && $student_image !=null){
			if($nkt <= $maxbacklogs){
				$applystudentq = "INSERT INTO $tablename VALUES('$uname','$name_of_student','$gender','$email','$student_image','$roll_no','$cv',$nkt,$contact_number,$college,$course);";
				if(mysqli_query($conn,$applystudentq)){
					success("You have applied to the placement interview.");
					mysqli_query($conn,"UPDATE $studentstable SET apply_count = apply_count + 1 WHERE Username='$uname';");

				}
				else{failure("Error in applying for placement.");}
			}
			else{
				failure("You are not eligible for this placement because of your backlogs.");
			}
		}
		else{
			failure("Please upload your profile picture and CV and try again (Go to 'Edit Profile')");				
		}

		mysqli_close($conn);
		


	}
?>

<?php require_once("./student_panel_apply_for_placement.php"); ?>