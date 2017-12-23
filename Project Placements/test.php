<?php
	if(version_compare(phpversion(), "5.4.0") != -1){
        if (session_status() == PHP_SESSION_NONE) {
          session_start();
        }
      } else {
        if(session_id() == '') {
          session_start();
        }
      }


    if(isset($_SESSION["student_au_uname"]) == false && isset($_SESSION["student_au_pwd"]) == false && isset($_SESSION["student_au_logged_in"]) == false && $_SESSION["student_au_logged_in"] != true){
        die();
        exit();
    }
	
	require_once("./header.php");

	$message = "";


	function f_success($msg){return '<div class="alert alert-success" role="alert"><center><strong>&nbsp;&nbsp;'.$msg.'</strong></center></div>';}

	function f_failure($msg){return '<div class="alert alert-danger" role="alert"><center><strong>&nbsp;&nbsp;'.$msg.'.</strong></center></div>';}


	if(!empty($_POST["student-update-reg-name"]) && !empty($_POST["student-update-reg-roll-no"]) && !empty($_POST["student-update-reg-email"]) && $_POST["student-update-reg-gender"] != "Gender" && $_POST["student-update-reg-course"] != "Course" && !empty($_POST["student-update-username"]) && $_POST["student-update-reg-college"] != "College"){

		$uname = $_POST["student-update-username"];
		$name = $_POST["student-update-reg-name"];
		$pwd = $_POST["student-update-reg-password"];
		$c_pwd = $_POST["student-update-reg-confirm-password"];
		$rollno = $_POST["student-update-reg-roll-no"];
		$email = $_POST["student-update-reg-email"];
		$gender = $_POST["student-update-reg-gender"];
		$course = $_POST["student-update-reg-course"];
		$college = $_POST["student-update-reg-college"];
		$s_id = $_POST["student-update-id"];
		$oldpassword = $_POST["student-update-reg-old-password"];


		$strengths = str_replace("\n", "#", $_POST["student-update-reg-strengths"]); 
		$weaknesses = str_replace("\n", "#", $_POST["student-update-reg-weaknesses"]);
		$achievements = str_replace("\n", "#", $_POST["student-update-reg-acheivements"]);


		

		$ts=null;$ps=null;
		if(!empty($_POST["technical-skills"])){
			$ts = implode("#",$_POST["technical-skills"]);
		}
		if(!empty($_POST["personal-skills"])){
			$ps = implode("#",$_POST["personal-skills"]);
		}

		require_once("./connection.php");

		$conn = mysqli_connect($host,$username,$password,$database) or die("Error connecting to server");

		$getallstudentdetailsq = "SELECT * FROM $studentstable WHERE Username='$uname';";

		$getallstudentdetailsres = mysqli_query($conn,$getallstudentdetailsq);

		$getallstudentdetailsrow = mysqli_fetch_assoc($getallstudentdetailsres);

		$contact_number = $_POST["student-update-reg-contact-number"];

		$opassword = $getallstudentdetailsrow["Password"];

		if(strlen($pwd) > 0){
			if(strlen($name) > 4 && strlen($pwd) >= 6 && $pwd == $c_pwd  && preg_match('/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/', $email) && sha1($oldpassword) == $opassword){
				str_replace(".", "#", $contact_number);
				if(is_numeric($contact_number) && strlen($contact_number) == 10){
					$updatestudentdets = "UPDATE $studentstable SET Name_of_the_Student='$name',Password=sha1('$pwd'),Roll_No='$rollno',Email='$email',Gender='$gender',Course=$course,Contact_Number=$contact_number,College_ID=$college,Strengths='$strengths',Weaknesses='$weaknesses',Acheivements='$achievements',Technical_Skills='$ts',Personal_Skills='$ps' WHERE Username='$uname';";
					
					if(mysqli_query($conn,$updatestudentdets)){
						$message .= f_success("Student Profile successfully updated.");

					}
					else{
						$message .= f_failure("Student Profile could not be updated.Please check your data and try again.");
					}
				}
				else{
					$message .= f_failure("Contact Number should be numeric and valid and should be of 10 digits.");
				}

			}
			else{
				if(strlen($name) <= 4){
					$message .= f_failure("Name should be greater than 4 characters");
				}
				if(strlen($pwd) < 6){
					$message .= f_failure("Password should be greater than 6 characters");
				}
				if($pwd != $c_pwd){
					$message .= f_failure("Password and Confirm Password did not match");
				}
				if(!preg_match('/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/', $email)){
					$message .= f_failure("Email is not valid");
				}
				if(!is_numeric($contact_number) || !strlen($contact_number) == 10){
					$message .= f_failure("Contact number is not valid");
				}
				if(sha1($oldpassword) != $opassword){
					$message .= f_failure("Update not authenticated because of wrong old password input");
				}
			}
		}
		else{
			if(strlen($name) > 4 && preg_match('/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/', $email)){
				str_replace(".", "#", $contact_number);
				if(is_numeric($contact_number) && strlen($contact_number) == 10){
					$updatestudentdets = "UPDATE $studentstable SET Name_of_the_Student='$name',Roll_No='$rollno',Email='$email',Gender='$gender',Course=$course,Contact_Number=$contact_number,College_ID=$college,Strengths='$strengths',Weaknesses='$weaknesses',Acheivements='$achievements',Technical_Skills='$ts',Personal_Skills='$ps' WHERE Username='$uname';";
					
					if(mysqli_query($conn,$updatestudentdets)){
						$message .= f_success("Student Profile successfully updated.");
					}
					else{
						$message .= f_failure("Student Profile could not be updated.");
					}
				}
				else{
					$message .= f_failure("Contact Number should be numeric and valid");
				}

			}
			else{
				if(strlen($name) < 4){
					$message .= f_failure("Name should be greater than 4 characters.");
				}
				if(!preg_match('/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/', $email)){
					$message .= f_failure("Email is not valid");
				}
				if(!is_numeric($contact_number) || !strlen($contact_number) == 10){
					$message .= f_failure("Contact number is not valid");
				}
			}
		}


		$sscmarks = $_POST["student-update-reg-ssc-percent"];
		$hscmarks = $_POST["student-update-reg-hsc-percent"];
		$sem1marks = $_POST["student-update-reg-sem1-gpa"];
		$sem2marks = $_POST["student-update-reg-sem2-gpa"];
		$sem3marks = $_POST["student-update-reg-sem3-gpa"];
		$sem4marks = $_POST["student-update-reg-sem4-gpa"];
		$sem5marks = $_POST["student-update-reg-sem5-gpa"];
		$sem6marks = $_POST["student-update-reg-sem6-gpa"];

		$sscmonth = $_POST["student-update-reg-ssc-month"];
		$hscmonth = $_POST["student-update-reg-hsc-month"];
		$sem1month = $_POST["student-update-reg-sem1-month"];
		$sem2month = $_POST["student-update-reg-sem2-month"];
		$sem3month = $_POST["student-update-reg-sem3-month"];
		$sem4month = $_POST["student-update-reg-sem4-month"];
		$sem5month = $_POST["student-update-reg-sem5-month"];
		$sem6month = $_POST["student-update-reg-sem6-month"];

		$sscyear = $_POST["student-update-reg-ssc-year"];
		$hscyear = $_POST["student-update-reg-hsc-year"];
		$sem1year = $_POST["student-update-reg-sem1-year"];
		$sem2year = $_POST["student-update-reg-sem2-year"];
		$sem3year = $_POST["student-update-reg-sem3-year"];
		$sem4year = $_POST["student-update-reg-sem4-year"];
		$sem5year = $_POST["student-update-reg-sem5-year"];
		$sem6year = $_POST["student-update-reg-sem6-year"];

		$nkt = $_POST["student-update-reg-kt"];

		$total_gpa = $_POST["student-update-reg-total-gpa"];

		$marksprocess = true;

		if($sscmarks == "" || $hscmarks == "" || $sem1marks == "" || $sem2marks == "" || $sem3marks == "" || $sem4marks == "" || $sem5marks == "" || $sem6marks == ""){
			$message .= f_failure("Percent/GPA input field cannot be empty.If you havent obtained your GPA please type 'NA'(HSC,SSC Percent mandatory).");
			$marksprocess = false;
		}

		if((!is_numeric($sscmarks)) || (!is_numeric($hscmarks)) || (!is_numeric($sem1marks) && $sem1marks !="NA") || (!is_numeric($sem2marks) && $sem2marks !="NA") || (!is_numeric($sem3marks) && $sem3marks !="NA") || (!is_numeric($sem4marks) && $sem4marks !="NA") || (!is_numeric($sem5marks) && $sem5marks !="NA")  || (!is_numeric($sem6marks) && $sem6marks !="NA")  ){
			$message .= f_failure("Percent/GPA input should be numeric.If you havent obtained your GPA please type 'NA'(Not applicable to HSC,SSC percent).");
			$marksprocess = false;
		}

		if($sscmonth == "Month" || $hscmonth == "Month" || $sem1month == "Month" || $sem2month == "Month" || $sem3month == "Month" || $sem4month == "Month" || $sem5month == "Month"  || $sem6month == "Month" || $sscyear == "Year" || $hscyear == "Year" || $sem1year == "Year" || $sem2year == "Year" || $sem3year == "Year" || $sem4year == "Year" || $sem5year == "Year"  || $sem6year == "Year" ){

			$message .= f_failure("Please check your inputed Month and Year data and try again.If you have no month and year,select 'NA'.");
			$marksprocess = false;
		}

		if(!is_numeric($nkt)){
			$message .= f_failure("Number of KTs should be numeric. If you dont have any, input 0.");
			$marksprocess = false;
		}

		if (strpos($nkt, '.') !== false) {
		    $message .= f_failure("Number of KTs should be an integer.");
			$marksprocess = false;
		}
		if (strpos($nkt, '-') !== false) {
		    $message .= f_failure("Number of KTs should be an integer.");
			$marksprocess = false;
		}

		if(!is_numeric($total_gpa)){
			$message .= f_failure("Out of - GPA must be a number.");
			$marksprocess = false;
		}

		// if(!is_int($nkt)){
		// 	if($nkt != 0){
		// 		$message .= f_failure("No of KTs must be an integer value.");
		// 		$marksprocess = false;
		// 	}
		// }

		if($sscmarks < 40 || $hscmarks < 40 || $sem1marks < ($total_gpa * 0.35) || $sem2marks < ($total_gpa * 0.35) || $sem3marks < ($total_gpa * 0.35) || $sem4marks < ($total_gpa * 0.35) || $sem5marks < ($total_gpa * 0.35) || $sem6marks < ($total_gpa * 0.35) ){
			if($sem1marks != "NA" && $sem2marks != "NA" && $sem3marks != "NA" && $sem4marks != "NA" && $sem5marks != "NA" && $sem6marks != "NA"){
				$message .= f_failure("Failed students not allowed");
				$marksprocess = false;
			}
		}

		if($sscyear > $hscyear){
			$message .= f_failure("HSC year should be greater than SSC year");
			$marksprocess = false;
		}

		if( ($sem1year < $sscyear || $sem1year < $hscyear) || ($sem2year < $sscyear || $sem2year < $hscyear) || ($sem3year < $sscyear || $sem3year < $hscyear) || ($sem4year < $sscyear || $sem4year < $hscyear) || ($sem5year < $sscyear || $sem5year < $hscyear)  || ($sem6year < $sscyear || $sem6year < $hscyear) ){
			$message .= f_failure("Semester year should be greater than SSC and HSC year");
			$marksprocess = false;
		}

		if($sscmarks > 100 || $hscmarks > 100 || $sem1marks > $total_gpa || $sem2marks > $total_gpa || $sem3marks > $total_gpa || $sem4marks > $total_gpa || $sem5marks > $total_gpa || $sem6marks > $total_gpa){
			if($sem1marks != "NA" && $sem2marks != "NA" && $sem3marks != "NA" && $sem4marks != "NA" && $sem5marks != "NA"  && $sem6marks != "NA"){
				$marksprocess = false;
				$message .= f_failure("Your Percentage or GPA seem to be beyond the specified limit. Please check your inputs and try again.");
			}
		}


		if($sscmarks < 0 && $hscmarks < 0 && $sem1marks < 0 && $sem2marks < 0 && $sem3marks < 0 && $sem4marks < 0 && $sem5marks < 0 && $sem6marks < 0){
			$message .= f_failure("Your Percentage or GPA seem to be less than 0. Please check your inputs and try again.");
			$marksprocess = false;
		}


		if($sem1marks == "NA"){
			$sem1marks = 0;
		}
		if($sem2marks == "NA"){
			$sem2marks = 0;
		}	
		if($sem3marks == "NA"){
			$sem3marks = 0;
		}
		if($sem4marks == "NA"){
			$sem4marks = 0;
		}
		if($sem5marks == "NA"){
			$sem5marks = 0;
		}
		if($sem6marks == "NA"){
			$sem6marks = 0;
		}

		if($marksprocess == true){
			if($sem1marks == "NA"){
				$sem1marks = 0;
			}
			if($sem2marks == "NA"){
				$sem2marks = 0;
			}	
			if($sem3marks == "NA"){
				$sem3marks = 0;
			}
			if($sem4marks == "NA"){
				$sem4marks = 0;
			}
			if($sem5marks == "NA"){
				$sem5marks = 0;
			}
			if($sem6marks == "NA"){
				$sem6marks = 0;
			}
			$updatesscq = 'UPDATE '.$ssctable.' SET Percentage='.$sscmarks.',Month_of_Passing="'.$sscmonth.'",Year_of_Passing="'.$sscyear.'" WHERE Username="'.$uname.'";';
			$updatehscq = 'UPDATE '.$hsctable.' SET Percentage='.$hscmarks.',Month_of_Passing="'.$hscmonth.'",Year_of_Passing="'.$hscyear.'" WHERE Username="'.$uname.'";';
			$updatesem1q = 'UPDATE '.$sem1table.' SET GPA='.$sem1marks.',Month_of_Passing="'.$sem1month.'",Year_of_Passing="'.$sem1year.'",Out_of_GPA="'.$total_gpa.'" WHERE Username="'.$uname.'";';
			$updatesem2q = 'UPDATE '.$sem2table.' SET GPA='.$sem2marks.',Month_of_Passing="'.$sem2month.'",Year_of_Passing="'.$sem2year.'",Out_of_GPA="'.$total_gpa.'" WHERE Username="'.$uname.'";';
			$updatesem3q = 'UPDATE '.$sem3table.' SET GPA='.$sem3marks.',Month_of_Passing="'.$sem3month.'",Year_of_Passing="'.$sem3year.'",Out_of_GPA="'.$total_gpa.'" WHERE Username="'.$uname.'";';
			$updatesem4q = 'UPDATE '.$sem4table.' SET GPA='.$sem4marks.',Month_of_Passing="'.$sem4month.'",Year_of_Passing="'.$sem4year.'",Out_of_GPA="'.$total_gpa.'" WHERE Username="'.$uname.'";';
			$updatesem5q = 'UPDATE '.$sem5table.' SET GPA='.$sem5marks.',Month_of_Passing="'.$sem5month.'",Year_of_Passing="'.$sem5year.'",Out_of_GPA="'.$total_gpa.'" WHERE Username="'.$uname.'";';
			$updatesem6q = 'UPDATE '.$sem6table.' SET GPA='.$sem6marks.',Month_of_Passing="'.$sem6month.'",Year_of_Passing="'.$sem6year.'",Out_of_GPA="'.$total_gpa.'" WHERE Username="'.$uname.'";';

			$updatektq = 'UPDATE '.$studentstable.' SET Number_of_KT='.$nkt.' WHERE Username="'.$uname.'";';

			$markssuccesscount = 0;
			if(mysqli_query($conn,$updatesscq)){$markssuccesscount += 1;}
			if(mysqli_query($conn,$updatehscq)){$markssuccesscount += 1;}
			if(mysqli_query($conn,$updatesem1q)){$markssuccesscount += 1;}
			if(mysqli_query($conn,$updatesem2q)){$markssuccesscount += 1;}
			if(mysqli_query($conn,$updatesem3q)){$markssuccesscount += 1;}
			if(mysqli_query($conn,$updatesem4q)){$markssuccesscount += 1;}
			if(mysqli_query($conn,$updatesem5q)){$markssuccesscount += 1;}
			if(mysqli_query($conn,$updatesem6q)){$markssuccesscount += 1;}

			if(mysqli_query($conn,$updatektq)){$markssuccesscount += 1;}


			if($markssuccesscount == 9){
				$message .= f_success("Marks successfully updated.");
			}
			else{
				$message .= f_failure("Marks could not be updated.Please check your inputed data and try again.");
			}

			
		}
		else{
			$message .= f_failure("Marks could not be updated.Please check your inputed data and try again.");
		}	


		mysqli_close($conn);



		$picname = "";
		if($_FILES["student-update-reg-profile-pic"]["name"] != ""){
			$target_dir = "./student_profile_pictures/";
			$target_file = $target_dir . $getallstudentdetailsrow["Student_ID"]."_".  basename($_FILES["student-update-reg-profile-pic"]["name"]);
			$picname = $target_dir . basename($_FILES["student-update-reg-profile-pic"]["name"]);
			$uploadOk = 1;
			$imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
			// Check if image file is a actual image or fake image
			$check = getimagesize($_FILES["student-update-reg-profile-pic"]["tmp_name"]);
		    if($check !== false) {
		        $uploadOk = 1;
		    } else {
		        $message .= f_failure("File is not an image.");
		        $uploadOk = 0;
		    }
		
			// Check file size
			if ($_FILES["student-update-reg-profile-pic"]["size"] > 40000000) {
			    $message .= f_failure("Sorry, your file is too large (Profile Picture).");
			    $uploadOk = 0;
			}
			// Allow certain file formats
			if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg") {
			    $message .= f_failure("Sorry, only JPG, JPEG, PNG files are allowed for Profile Picture.");
			    $uploadOk = 0;
			}
			// Check if $uploadOk is set to 0 by an error
			if ($uploadOk == 0) {
			    $message .= f_failure("Profile Picture could not be updated");
			// if everything is ok, try to upload file
			} else {
			    if (move_uploaded_file($_FILES["student-update-reg-profile-pic"]["tmp_name"], $target_file)) {

			        $conn = mysqli_connect($host,$username,$password,$database) or die("Error connecting to server");

			        $updateprofilepicq = "UPDATE $studentstable SET Student_Image='".str_replace("'", "", $target_file)."' WHERE Username='$uname';";

			        $message .= f_success("Profile Picture successfully updated");

			        if(mysqli_query($conn,$updateprofilepicq)){}

			        $updatecvq = "INSERT INTO $fileuploadstable(file_name,uploader_id,uploader_type,uploaded_on) VALUES('".str_replace("'", "", $target_file)."',".$getallstudentdetailsrow["Student_ID"].",'student','$timestamp');";

			    	if(mysqli_query($conn,$updatecvq)){}

			        mysqli_close($conn);


			    } else {
			        $message .= f_failure("Profile Picture could not be updated");
			    }
			}
		}


		$cvname = "";
		if($_FILES["student-update-reg-cv"]["name"] != ""){
			$target_dir = "./student_cv/";
			$target_file = $target_dir .  $getallstudentdetailsrow["Student_ID"]."_". basename($_FILES["student-update-reg-cv"]["name"]);
			$cvname = $target_dir. basename($_FILES["student-update-reg-cv"]["name"]);
			$uploadOk = 1;
			$imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
			
			if ($_FILES["student-update-reg-cv"]["size"] > 40000000) {
			    $message .= f_failure("Sorry, your file is too large.(CV)");
			    $uploadOk = 0;
			}
			// Allow certain file formats
			if($imageFileType != "doc" && $imageFileType != "pdf" && $imageFileType != "docx" && $imageFileType != "odt" && $imageFileType != "ott" && $imageFileType != "odp" && $imageFileType != "ods" && $imageFileType != "odf") {
			    $message .= f_failure("Sorry, only DOC,PDF,DOCX,ODT,OTT,ODP,ODS,ODF files are allowed.(CV)");
			    $uploadOk = 0;
			}
			// Check if $uploadOk is set to 0 by an error
			if ($uploadOk == 0) {
			    $message .= f_failure("Sorry, your file was not uploaded.(CV)");
			// if everything is ok, try to upload file
			} else {
			    if (move_uploaded_file($_FILES["student-update-reg-cv"]["tmp_name"], $target_file)) {

			        $conn = mysqli_connect($host,$username,$password,$database) or die("Error connecting to server");

			        $updatecvq = "UPDATE $studentstable SET Student_CV='".str_replace("'", "", $target_file)."' WHERE Username='$uname';";

			        $message .= f_success("CV successfully updated");

			        if(mysqli_query($conn,$updatecvq)){}

			        $updatecvq = "INSERT INTO $fileuploadstable(file_name,uploader_id,uploader_type,uploaded_on) VALUES('".str_replace("'", "", $target_file)."',".$getallstudentdetailsrow["Student_ID"].",'student','$timestamp');";

			    	if(mysqli_query($conn,$updatecvq)){}

			        mysqli_close($conn);


			    } else {
			        $message .= f_failure("CV could not be updated");
			    }
			}
		}
	}
	else{
		$message .= f_failure("Some error has occured in updating. Please check your input data and try again");
	}


?>

<!-- <link rel="stylesheet" type="text/css" href="./student_login.css">

<script type="text/javascript" src="./student_login.js"></script>


<div class="container student-login-card">
    	<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row" align="center">
							<div class="col-xs-12">
								<a href="#" class="active" id="login-form-link">Login</a>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="login-form" action="./student_panel.php" method="post" role="form" style="display: block;">
									<div class="form-group">
										<input type="text" name="student-username" id="student-username" tabindex="1" class="form-control" placeholder="Username" required="true" spellcheck="false">
									</div>
									<div class="form-group">
										<input type="password" name="student-password" id="student-password" tabindex="2" class="form-control" placeholder="Password" required="true" spellcheck="false">
									</div>
									<div class="form-group text-center">
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="student-login-submit" id="login-submit" tabindex="3" class="form-control btn btn-login" value="Log In">
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												<div class="text-center">
													<a href="https://phpoll.com/recover" tabindex="5" class="forgot-password">Forgot Password?</a>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
 -->

<?php
	function redirect($url) {
	    ob_start();
	    header('Location: '.$url);
	    ob_end_flush();
	    die();
	}

	$_SESSION["student_au_update_message"] = $message;

	redirect("./student_login.php");

?>

student_update_profile.php
Displaying student_update_profile.php.