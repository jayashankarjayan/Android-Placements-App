<?php
/**
* Process parameters
*/
// require_once($_SERVER['DOCUMENT_ROOT'].'/includes/init.php');

class ProcessParams
{
	function login($data)
	{
		if(isset($data['username']) && isset($data['password']))
		{
			if ($students = fetchData(array('table'=>'students','condition'=>' WHERE Username = "'.$data['username'].'" AND Password = "'.sha1($data['password']).'"')))
			{
				//success
				$response['success']  = 1;
				$response = array('success'=>1,'login'=>$students);
				
				// array_push($response['product'], $students);

				echo json_encode($response);
			}
			else
			{
				$response['success'] = 0;
				$response['message'] = "No Records Found";

				echo json_encode($response);
			}
		}
	}

	function getUserData($data)
	{
		if(isset($data['user_id']))
		{
			$p_skills = array();
			$t_skills = array();

			if ($students = fetchData(array('table'=>'students','condition'=>' WHERE Student_ID = '.$data['user_id'])))
			{
				foreach($students as $student)
				{
					if ($colleges = fetchData(array('table'=>'colleges','condition'=>' WHERE College_ID = "'.$student['College_ID'].'"')))
					{
						$college = $colleges;
					}

					$p_skill = str_split($student['Personal_Skills']);
					$t_skill = str_split($student['Technical_Skills']);
					
					for($i=0;$i<sizeof($p_skill);$i++)
					{
						if($p_skill[$i] != "#")
						{
							
							if ($personal_skills = fetchData(array('table'=>'personal_skills','condition'=>' WHERE ID = "'.$p_skill[$i].'"')))
							{
									// $z = $z + 2;
								foreach ($personal_skills as $personal_skill)
								{
									array_push($p_skills, $personal_skill['Skill_Name']);
								}
							}
						}
					}

					for($j=0;$j<sizeof($t_skill);$j++)
					{
						if($t_skill[$j] != "#")
						{
							
							if ($technical_skills = fetchData(array('table'=>'technical_skills','condition'=>' WHERE ID = "'.$t_skill[$j].'"')))
							{
								foreach ($technical_skills as $technical_skill)
								{
									array_push($t_skills, $technical_skill['Skill_Name']);
								}
							}
						}
					}
				}

				//success
				$response['success']  = 1;
				$response = array('success'=>1,'students'=>$students,'college'=>$college,'p_skills'=>$p_skills,'t_skills'=>$t_skills);
				
				// array_push($response['product'], $students);

				echo json_encode($response);
			}
			else
			{
				$response['success'] = 0;
				$response['message'] = "No Records Found";

				echo json_encode($response);
			}
		}
	}

	function getNameOfUser($data)
	{
		if(isset($data))
		{
			if ($names_of_students = fetchSingleColumnData(array('table'=>'students','condition'=>' WHERE Username = "'.$data.'"'),'Name_of_the_Student'))
			{
				foreach ($names_of_students as $name_of_student)
				{
					$name = $name_of_student['Name_of_the_Student'];
				}
				return $name;
			}
			else
			{
				$error = 'Anonymous';
				return $error;
			}
		}
		else
		{
			$error = 'Looks like something is wrong. Please log out and try again';
			return $error;
		}
	}

	function getImageOfUser($data)
	{
		if(isset($data))
		{
			if ($images_of_students = fetchSingleColumnData(array('table'=>'students','condition'=>' WHERE Username = "'.$data.'"'),'Student_Image'))
			{
				foreach ($images_of_students as $image_of_student)
				{
					$image = $image_of_student['Student_Image'];
				}
				return $image;
			}
			else
			{
				$error = 'Anonymous';
				return $error;
			}
		}
		else
		{
			$error = 'Looks like something is wrong. Please log out and try again';
			return $error;
		}
	}

	function settingsActivity($data)
	{
		if(isset($data['username']))
		{
			$username = $data['username'];
			$name = $this->getNameOfUser($username);
			$image = $this->getImageOfUser($username);

			$response = array('success'=>1,'name'=>$name,'image'=>$image);
			echo json_encode($response);
		}
		else
		{
			$response = array('success'=>0,'message'=>'Looks like something is wrong. Please log out and try again');
			echo(json_encode($response));
		}
	}

	function getCompanyInvitations($data)
	{
		if(isset($data['user_id']))
		{
			if($invites = fetchData(array('table'=>'invitations','condition'=>' WHERE (To_Student_ID = '.$data["user_id"].') AND approved = 1')))
			{
				if($studentDetails = fetchData(array('table'=>'students','condition'=>' WHERE Student_ID = '.$data["user_id"])))
				{
					foreach($invites as $invite)
					{
						if($companyDetails = fetchData(array('table'=>'companies_placements','condition'=>' WHERE Table_Name = "'.$invite['Company_Table_Name'].'"')))
						{
							$response = array('success'=>1,'invites'=>$invites,'studentDetails'=>$studentDetails,'companyDetails'=>$companyDetails);
							
							echo json_encode($response);
						}
					}
				}
			}
			else
			{
				$response['success'] = 0;
				$response['message'] = "No Records Found";

				echo json_encode($response);
			}					
		}
	}

	function getCompanies($data)
	{
		if((isset($data['username'])) && (isset($data['college_id'])) && (isset($data['course_id'])))
		{
			$date = date('d-m-Y');


			if($companies = fetchData(array('table'=>'companies_placements','condition'=>' WHERE (For_Course='.$data['course_id'].' OR For_Course=0) AND (College_ID = '.$data["college_id"].' OR College_ID=0) AND approved=1 AND cancelled=0 AND date(Date_of_Selection) > '.$date.';')))
			{
				if($sscDetails = fetchData(array('table'=>'ssc_details','condition'=>' WHERE Username = "'.$data["username"].'" ')))
				{
					if($hscDetails = fetchData(array('table'=>'hsc_details','condition'=>' WHERE Username = "'.$data["username"].'" ')))
					{
						if($sem1Details = fetchData(array('table'=>'sem_1_details','condition'=>' WHERE Username = "'.$data["username"].'" ')))
						{
							if($sem2Details = fetchData(array('table'=>'sem_2_details','condition'=>' WHERE Username = "'.$data["username"].'" ')))
							{
								if($sem3Details = fetchData(array('table'=>'sem_3_details','condition'=>' WHERE Username = "'.$data["username"].'" ')))
								{
									if($sem4Details = fetchData(array('table'=>'sem_4_details','condition'=>' WHERE Username = "'.$data["username"].'" ')))
									{
										if($sem5Details = fetchData(array('table'=>'sem_5_details','condition'=>' WHERE Username = "'.$data["username"].'" ')))
										{
											foreach ($sscDetails as $sscDetail)
											{
												foreach ($hscDetails as $hscDetail)
												{
													foreach ($sem1Details as $sem1Detail)
													{
														foreach ($sem2Details as $sem2Detail)
														{
															foreach ($sem3Details as $sem3Detail)
															{
																foreach ($sem4Details as $sem4Detail)
																{
																	foreach ($sem5Details as $sem5Detail)
																	{
																		$total_gpa = $sem1Detail["Out_of_GPA"];

																		$total_percent = 100;

																		$marks_into_percent = array();

																		$ssc_marks = $sscDetail["Percentage"];
																		$hsc_marks = $hscDetail["Percentage"];

																		$marks_into_percent[] = ($sem1Detail["GPA"]/$total_gpa)*100;
																		$marks_into_percent[] = ($sem2Detail["GPA"]/$total_gpa)* 100;
																		$marks_into_percent[] = ($sem3Detail["GPA"]/$total_gpa)*100;
																		$marks_into_percent[] = ($sem4Detail["GPA"]/$total_gpa)*100;
																		$marks_into_percent[] = ($sem5Detail["GPA"]/$total_gpa)*100;

																		foreach($companies as $company)
																		{
																			$isApplicable = true;
																			$count = 0;
																			for($i=0;$i<=count($marks_into_percent)-1;$i++)
																			{
																				if($marks_into_percent[$i] >= $company["min_gpa_percentage"])
																				{
																					$count++;
																				}
																			}

																			if($ssc_marks >= $company["min_percentage"] && $hsc_marks >= $company["min_percentage"])
																			{
																				$isApplicable = true;
																			}
																			else
																			{
																				$isApplicable = false;
																			}

																			$tableName = strtolower($company['Table_Name']);
																			if(addTableToList($tableName))
																			{
																				$foundUser = false;
																				if($newTables = fetchData(array('table'=>''.$tableName,'condition'=>' WHERE Username = "'.$data['username'].'"')))
																				{

																					foreach ($newTables as $newTable)
																					{
																						if($newTable["Username"] == $data['username'])
																						{
																							$foundUser = true;
																							break;
																						}
																					}
																				}
																				else
																				{
																					$response = array('success'=>0,'message'=>'Something seems to be wrong. Please try again later');
																					exit;
																					echo json_encode($response);
																				}
																				
																				if($isApplicable)
																				{
																					if($date < $company['Date_of_Selection'])
																					{
																						if($company["closed"] == 0 && $company["cancelled"] == 0 && $company["selection_done"] == 0)
																						{
																							if($foundUser == true)
																							{
																								// $response = array('success'=>1,'companies'=>$companies, 'applyButtonName'=>'unapply');
																								$initArray = array('success'=>1, 'company_name'=>$company['Company_Name'], 'vacancy'=>$company['Vacancies_for'],'applyButtonName'=>'unapply', 'table_name'=>$company['Table_Name']);
																								$response = array('data'=>array($initArray));
																								echo json_encode($response);
																							}
																							else if($foundUser == false)
																							{
																								// $response = array('success'=>1,'companies'=>$companies, 'applyButtonName'=>'apply');
																								$initArray = array('success'=>1, 'company_name'=>$company['Company_Name'], 'vacancy'=>$company['Vacancies_for'],'applyButtonName'=>'apply', 'table_name'=>$company['Table_Name']);
																								$response = array('data'=>array($initArray));
																								echo json_encode($response);
																							}
																						}
																					}																			
																					else
																					{
																						// $response = array('success'=>1,'companies'=>$companies, 'applyButtonName'=>'not applicable');
																						$initArray = array('success'=>1, 'company_name'=>$company['Company_Name'], 'vacancy'=>$company['Vacancies_for'],'applyButtonName'=>'not applicable', 'table_name'=>$company['Table_Name']);
																						$response = array('data'=>array($initArray));
																							echo json_encode($response);
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}	
												}	
											}
										}
										else
										{
											$response = array('success'=>0,'message'=>'Semester 5 details not filled');
											echo json_encode($response);
										}
									}
									else
									{
										$response = array('success'=>0,'message'=>'Semester 4 details not filled');
										echo json_encode($response);
									}
								}
								else
								{
									$response = array('success'=>0,'message'=>'Semester 3 details not filled');
									echo json_encode($response);
								}
							}
							else
							{
								$response = array('success'=>0,'message'=>'Semester 2 details not filled');
								echo json_encode($response);
							}
						}
						else
						{
							$response = array('success'=>0,'message'=>'Semester 1 details not filled');
							echo json_encode($response);
						}
					}
					else
					{
						$response = array('success'=>0,'message'=>'HSC details not filled');
						echo json_encode($response);
					}					
				}
				else
				{
					$response = array('success'=>0,'message'=>'SSC details not filled');
					echo json_encode($response);
				}
			}
			else
			{
				$response = array('success'=>0,'message'=>'No Companies Found');
				echo json_encode($response);
			}
		}
		else
		{
			$response = array('success'=>0,'message'=>'Looks like something is wrong. Please log out and try again');
			echo json_encode($response);
		}
	}


	function applyToCompany($data)
	{
		if((isset($data['username'])) && (isset($data['table_name'])))
		{
			if($studentDetails = fetchData(array('table'=>'students','condition'=>' WHERE Username = "'.$data['username'].'"')))
			{
				foreach ($studentDetails as $studentDetail)
				{
					$username = $data['username'];
					$name = $studentDetail['Name_of_the_Student'];
					$gender = $studentDetail['Gender'];
					$email = $studentDetail['Email'];
					$student_image = $studentDetail['Student_Image'];
					$roll_no = $studentDetail['Roll_No'];
					$cv = $studentDetail['Student_CV'];
					$backlogs = $studentDetail['Number_of_KT'];
					$contact = $studentDetail['Contact_Number'];				
					$college = $studentDetail['College_ID'];
					$course = $studentDetail['Course'];
				}

				if($appliableCompanies = fetchData(array('table'=>'companies_placements','condition'=>' WHERE Table_Name = "'.$data['table_name'].'"' )))
				{
					foreach ($appliableCompanies as $appliableCompany)
					{
						$maxBacklog = $appliableCompany['Maximum_Backlogs'];
					}

					if(!empty($cv) && (!empty($student_image)))
					{
						if ($backlogs <= $maxBacklog)
						{
							global $db;
							$sql = "INSERT INTO ".$data["table_name"]." VALUES(:uname, :name_of_student, :gender, :email, :student_image, :roll_no, :cv, :nkt, :contact_number, :college, :course);";

							$stmt = $db->prepare($sql);

							// $stmt->bindParam(':tablename',$data['table_name']);
							$stmt->bindParam(':uname',$username);
							$stmt->bindParam(':name_of_student',$name);
							$stmt->bindParam(':gender',$gender);
							$stmt->bindParam(':email',$email);
							$stmt->bindParam(':student_image',$student_image);
							$stmt->bindParam(':roll_no',$roll_no);
							$stmt->bindParam(':cv',$cv);
							$stmt->bindParam(':nkt',$backlogs);
							$stmt->bindParam(':contact_number',$contact);
							$stmt->bindParam(':college',$college);
							$stmt->bindParam(':course',$course);

							if($stmt->execute())
							{
								$response = array('success'=>1,'status'=>'applied');
								echo json_encode($response);
							}
							else
							{
								$response = array('success'=>0,'status'=>'not applied');
								echo json_encode($response);
							}
						}
						else
						{
							$response = array('success'=>0,'message'=>'CV or Profile Photo not uploaded');
							echo json_encode($response);
						}
					}
					else
					{
						$response = array('success'=>0,'message'=>'Too many backlogs');
						echo json_encode($response);
					}					
				}
				else
				{
					$response = array('success'=>0,'message'=>'No companies');
					echo json_encode($response);
				}
			}
			else
			{
				$response = array('success'=>0,'message'=>'Student does not exist');
				echo json_encode($response);
			}
		}
		else
		{
			$response = array('success'=>0,'message'=>'Error. Please log out and retry');
			echo json_encode($response);
		}
	}

	function getAppliedCompanies($data)
	{
		if(isset($data['username']))
		{
			if ($appliedCompanies = fetchData(array('table'=>'companies_placements','condition'=>' WHERE Added_By = "'.$data['username'].'"ORDER BY Added_on')))
			{
				foreach ($appliedCompanies as $appliedCompany)
				{
					$newTable = strtolower($appliedCompany['Table_Name']);
					if(!(empty($newTable)))
					{
						if(addTableToList($newTable))
						{
							if($addedTables = fetchData(array('table'=>''.$newTable,'condition'=>'')))
							{
								foreach ($addedTables as $addedTable)
								{
									if($data['username'] == $addedTable['Username'])
									{
										$response['success']  = 1;
										$response = array('success'=>1,'appliedCompanies'=>$appliedCompanies);

										
										echo json_encode($response);
									}
									else
									{
										$response['success'] = 0;
										$response['message'] = "Nothing Found";

										
										echo json_encode($response);
									}
								}
							}
							else
							{
								$response['success'] = 0;
								$response['message'] = "No Companies Found";

								
								echo json_encode($response);
							}
						}
						else
						{
							$response['success'] = 0;
							$response['message'] = "Error";

							
							echo json_encode($response);
						}
					}
					else
					{
						$response['success'] = 0;
						$response['message'] = "Nothing Found";

						
						echo json_encode($response);
					}	
				}
			}
			else
			{
				$response['success'] = 0;
				$response['message'] = "Nothing Found";

				echo json_encode($response);
			}
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);
		}	
	}
	function getCompanyFilter()
	{
		if ($companyFilter = fetchDistinctData(array('table'=>'companies_placements','condition'=>' ORDER BY Company_Name asc'),'Company_Name'))
		{
			//success
			$response['success']  = 1;
			$response = array('success'=>1,'companyFilter'=>$companyFilter);
			
			// array_push($response['product'], $students);

			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);
		}

	}

	function getVacancyFilter()
	{
		if ($vacancyFilter = fetchDistinctData(array('table'=>'companies_placements','condition'=>' ORDER BY Vacancies_For asc'),'Vacancies_For'))
		{
			//success
			$response['success']  = 1;
			$response = array('success'=>1,'vacancyFilter'=>$vacancyFilter);
			
			// array_push($response['product'], $students);

			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);
		}

	}

	function getVenueFilter()
	{
		if ($venueFilter = fetchDistinctData(array('table'=>'companies_placements','condition'=>' ORDER BY Interview_Venue asc'),'Interview_Venue'))
		{
			//success
			$response['success']  = 1;
			$response = array('success'=>1,'venueFilter'=>$venueFilter);
			
			// array_push($response['product'], $students);

			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);
		}

	}

	function getPostFilter()
	{

	}
	function getNotices($data)
	{
		if((isset($data['username'])) && (isset($data['college_id'])))
		{
			if ($students = fetchData(array('table'=>'students','condition'=>' WHERE Username = "'.$data['username'].'"')))
			{
				foreach($students as $student)
				{
					$course = $student['Course'];
					if ($notices = fetchData(array('table'=>'notices','condition'=>' WHERE  (For_Course='.$course.' OR For_Course=0) AND (Adder_Type <> "admin" AND Adder_Type <> "company") AND (College_ID = "'.$data["college_id"].'" OR College_ID=0) ORDER BY Added_on DESC;')))
					{
						// $notice = $notices;
						$response = array('success'=>1,'notices'=>$notice);
						
						echo json_encode($response);
					}
					else
					{
						$response['success'] = 0;
						$response['message'] = "No Notices to display";

						
						echo json_encode($response);
					}
					
				}
				//success
				
				// array_push($response['product'], $students);

				// echo json_encode($response);
			}
			else
			{
				$response['success'] = 0;
				$response['message'] = "No Records Found";

				
				echo json_encode($response);
			}
		}
	}

	function getAllColleges()
	{
		if($colleges = fetchData(array('table'=>'colleges','condition'=>' ORDER BY College_Name asc')))
		{
			$response['success']  = 1;
			$response = array('success'=>1,'colleges'=>$colleges);
			echo json_encode($response);
		}

		else
		{
			$response['success'] = 0;
				$response['message'] = "No Records Found";

				echo json_encode($response);	
		}
	}

	function getQualification($data)
	{
		if($qualifications = fetchData(array('table'=>'courses','condition'=>'')))
		{
			$response['success']  = 1;
			$response = array('success'=>1,'qualifications'=>$qualifications);
			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);	
		}
	}

	function getQualificationFilter()
	{
		if($qualificationsFilter = fetchData(array('table'=>'courses','condition'=>' ORDER BY Courses asc')))
		{
			$response['success']  = 1;
			$response = array('success'=>1,'qualificationsFilter'=>$qualificationsFilter);
			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);	
		}
	}

	function getPersonalSkills($data)
	{
		if($personal_skills = fetchData(array('table'=>'personal_skills','condition','')))
		{
			$response['success']  = 1;
			$response = array('success'=>1,'personal_skills'=>$personal_skills);
			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);	
		}
	}


	function getPersonalSkillsFilter()
	{
		if($personal_skills = fetchData(array('table'=>'personal_skills','condition',' ORDER BY Skill_Name asc')))
		{
			$response['success']  = 1;
			$response = array('success'=>1,'personal_skills_filter'=>$personal_skills);
			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);	
		}
	}

	function getTechnicalSkills($data)
	{
		if($technical_skills = fetchData(array('table'=>'technical_skills','condition','')))
		{
			$response['success']  = 1;
			$response = array('success'=>1,'technical_skills'=>$technical_skills);
			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);	
		}
	}

	function getTechnicalSkillsFilter()
	{
		if($technical_skills_filter = fetchData(array('table'=>'technical_skills','condition',' ORDER BY Skill_Name asc')))
		{
			$response['success']  = 1;
			$response = array('success'=>1,'technical_skills_filter'=>$technical_skills_filter);
			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Records Found";

			echo json_encode($response);	
		}
	}

	function getThreadMessaegs($data)
	{
		if($threads = fetchData(array('table'=>'discussion_threads','condition'=>' WHERE Replying_to IS null ORDER BY Sent_on desc')))
		{
			foreach($threads as $thread)
			{
				if($imageOfUsers = fetchData(array('table'=>'students','condition'=>'')))
				{
					$response = array('success'=>1,'messages'=>$threads,'imageOfUser'=>$imageOfUsers);
				}
			}
			echo json_encode($response);
		}
		else
		{
			$response['success'] = 0;
			$response['message'] = "No Threads To Show";

			echo json_encode($response);	
		}
	}

	function getThreadComments($data)
	{
		if(isset($data['thread_id']))
		{
			if($getThreadComments = fetchData(array('table'=>'discussion_threads','condition'=>' WHERE Thread_ID = '.$data['thread_id'].' AND Replying_to IS NOT null ')))
			{
				$response = array('success'=>1,'threadComment'=>$getThreadComments);
				echo json_encode($response);
			}
			else
			{
				$response = array('success'=>0,'message'=>'No Comments Found');
				echo json_encode($response);
			}
		}
		else
		{
			$response = array('success'=>0,'message'=>'Error. Please try again later');
			echo json_encode($response);
		}
	}

	function updatePersonalDetails($data)
	{
		global $db;

		$strengths = str_replace("\n", "#", $data["strengths"]); 
		$weaknesses = str_replace("\n", "#", $data["weaknesses"]);
		$achievements = str_replace("\n", "#", $data["achievements"]);

		/*if(!empty($data["technical_skills"])){
			$technical_skills = implode("#",$data["technical_skills"]);
		}
		if(!empty($data["personal_skills"])){
			$personal_skills = implode("#",$data["personal_skills"]);
		}*/

		$sql = "UPDATE students SET Name_of_the_Student= :name, Email = :email, Gender=:gender, Contact_Number=:contact_number,
		Strengths = :strengths, Weaknesses = :weaknesses, Acheivements =:achievements, Technical_Skills = :technical_skills, Personal_Skills = :personal_skills WHERE Username =:username;";

		$stmt = $db->prepare($sql);

	
		$stmt->bindParam(':name',$data['name']);
		$stmt->bindParam(':email',$data['email']);
		$stmt->bindParam(':gender',$data['gender']);
		$stmt->bindParam(':contact_number',$data['contact']);
		$stmt->bindParam(':strengths',$strengths);
		$stmt->bindParam(':weaknesses',$weaknesses);
		$stmt->bindParam(':achievements',$achievements);
		$stmt->bindParam(':personal_skills',$personal_skills);
		$stmt->bindParam(':technical_skills',$technical_skills);
		$stmt->bindParam(':username',$data['username']);

		if($stmt->execute())
		{
			$response['success']  = 1;
			$response = array('success'=>1,'message'=>'Personal details updated');
			echo json_encode($response);
			return true;
		}
		else
		{
			$response = array('success'=>0,'message'=>'Personal details not updated');
			echo json_encode($response);
			return true;
		}
	}

	function updatePassword($data)
	{
		global $db;

		if($data['new_password'] == $data['confirm_password'] && isset($data['username']))
		{
			if(strlen($data['new_password']) >=6 )
			{
				$sql = "UPDATE students SET Password = :password WHERE Username=:username;";

				$stmt = $db->prepare($sql);

			
				$password = sha1($data['new_password']);
				$stmt->bindParam(':password',$password);
				$stmt->bindParam(':username',$data['username']);

				if($stmt->execute())
				{
					$response['success']  = 1;
					$response = array('success'=>1,'message'=>'Password updated');
					echo json_encode($response);
					return true;
				}
				else
				{
					$response = array('success'=>0,'message'=>'Password not updated');
					echo json_encode($response);
					return false;
				}
			}
		}
		else
		{
			$response = array('success'=>0,'message'=>'Password not updated');
			echo json_encode($response);
			return false;
		}
	}

	function acceptInvitations($data)
	{
		global $db;
		if(isset($data['username']) && isset($data['invite_id']))
		{
			if($companyDetails = fetchSingleColumnData(array('table'=>'invitations','condition'=>''),'Company_Table_Name'))
			{
				if($studentDetails = fetchData(array('table'=>'students','condition'=>' WHERE Username = "'.$data["username"].'"')))
				{
					foreach ($studentDetails as $studentDetail)
					{
						$name_of_student = $studentDetail["Name_of_the_Student"];
						$gender = $studentDetail["Gender"];
						$email = $studentDetail["Email"];
						$student_image = $studentDetail["Student_Image"];
						$roll_no = $studentDetail["Roll_No"];
						$cv = $studentDetail["Student_CV"];
						$numberOfKT = $studentDetail["Number_of_KT"];
						$contact_number = $studentDetail["Contact_Number"];
						$college = $studentDetail["College_ID"];
						$course = $studentDetail["Course"];


						if($cv != null && $student_image != null)
						{
							foreach ($companyDetails as $companyDetail)
							{
								$tablename = $companyDetail['Company_Table_Name'];
								$sql = "INSERT INTO $tablename VALUES(:uname,:name_of_student, :gender, :email, :student_image, :roll_no, :cv, :numberOfKT, :contact_number, :college, :course);";

								$stmt = $db->prepare($sql);

								$stmt->bindParam(':uname', $data['username']);
								$stmt->bindParam(':name_of_student', $name_of_student);
								$stmt->bindParam(':gender',$gender);
								$stmt->bindParam(':email', $email);
								$stmt->bindParam(':student_image', $student_image);
								$stmt->bindParam(':roll_no', $roll_no);
								$stmt->bindParam(':cv', $cv);
								$stmt->bindParam(':numberOfKT', $numberOfKT);
								$stmt->bindParam(':contact_number', $contact_number);
								$stmt->bindParam(':college', $college);
								$stmt->bindParam(':course', $course);

								if($stmt->execute())
								{
									$sql = "UPDATE invitations SET accepted = 1 WHERE ID = :invite_id";

									$stmt = $db->prepare($sql);
									$stmt->bindParam(':invite_id',$data['invite_id']);

									if($stmt->execute())
									{
										$response = array('success'=>1,'message'=>'Invite accepted');
										
										echo json_encode($response);										
									}
									else
									{
										$response = array('success'=>0,'message'=>'Error. Please try again later');
										
										echo json_encode($response);
									}
								}
								else
								{
									$response = array('success'=>0,'message'=>'Error. Please try again later');
									
									echo json_encode($response);
								}
							}
						}
					}
				}
			}
		}
	}

	function declineInvitations($data)
	{
		if(isset($data['invite_id']))
		{
			global $db;

			$sql = "UPDATE invitations SET accepted=2 WHERE ID= :inviteId ;";
			$stmt = $db->prepare($sql);
			$stmt->bindParam(':inviteId',$data['invite_id']);
			if($stmt->execute())
			{
				$response = array('success'=>1,'message'=>'Invite declined');
			}
			else
			{
				$response = array('success'=>0,'message'=>'Error. Please try again later');
			}
			
			echo json_encode($response);			
		}
	}

	function uploadImage($data)
	{
		$target_dir = "student_profile_pictures/";
		$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
		$uploadOk = 1;
		$imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
		// Check if image file is a actual image or fake image
		if(isset($_POST["submit"])) {
		    $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
		    if($check !== false) {
		        // echo "File is an image - " . $check["mime"] . ".";
		        $uploadOk = 1;
		    } else {
		    	$response = array('success'=>0,'message'=>'File is not an image');
				echo json_encode($response);
		        // echo "File is not an image.";
		        $uploadOk = 0;
		    }
		}
		// Check if file already exists
		if (file_exists($target_file)) {
			$response = array('success'=>0,'message'=>'Sorry, file already exists');
			echo json_encode($response);
		    // echo "Sorry, file already exists.";
		    $uploadOk = 0;
		}
		// Check file size
		if ($_FILES["fileToUpload"]["size"] > 500000) {
			$response = array('success'=>0,'message'=>'Sorry, your file is too large');
			echo json_encode($response);
		    // echo "Sorry, your file is too large.";
		    $uploadOk = 0;
		}
		// Allow certain file formats
		if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg") {
			$response = array('success'=>0,'message'=>'Sorry, only JPG, JPEG, PNG files are allowed.');
			echo json_encode($response);
		    // echo "Sorry, only JPG, JPEG, PNG files are allowed.";
		    $uploadOk = 0;
		}
		// Check if $uploadOk is set to 0 by an error
		if ($uploadOk == 0) {
			$response = array('success'=>0,'message'=>'Sorry, your file was not uploaded');
			echo json_encode($response);
		    // echo "Sorry, your file was not uploaded";
		// if everything is ok, try to upload file
		} else {
		    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
		    	if($oldImagePaths = fetchData(array('table'=>'students','condition'=>'WHERE Student_ID = '.$data['user_id'])))
		    	{
		    		foreach($oldImagePaths as $oldImagePath)
		    		{
		    			global $db;
		    			unlink($_SERVER['DOCUMENT_ROOT'].$oldImagePath['Student_Image']);
		    			$sql = "UPDATE students SET Student_Image = :newImagePath WHERE Student_ID = :user_id";

		    			$stmt = $db->prepare($sql);

		    			$newImagePath = '/'.$target_file;
		    			$stmt->bindParam(':newImagePath',$newImagePath);
		    			$stmt->bindParam(':user_id',$data['user_id']);

		    			if($stmt->execute())
		    			{
		    				$response = array('success'=>1,'message'=>'Image uploaded successfully');
		    				echo json_encode($response);
		    				return true;
		    			}
		    			else
		    			{
		    				$response = array('success'=>0,'message'=>'Image not uploaded');
		    				echo json_encode($response);
		    				return false;
		    			}
		    		}
		    	}
		        // echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded to ".$target_file;
		    } else {
		    	$response = array('success'=>0,'message'=>'Sorry, there was an error uploading your file');
				echo json_encode($response);
		        // echo "Sorry, there was an error uploading your file.";
		    }
		}
	}

	function backoutFromAppliedCompanies($data)
	{
		if(isset($data['username']))
		{
			global $db;

			if($companyDetails = fetchSingleColumnData(array('table'=>'companies_placements','condition'=>' WHERE Added_By = "'.$data['username'].'"ORDER BY Added_on'),'Table_Name'))
			{
				foreach ($companyDetails as $companyDetail)
				{
					$tablename = $companyDetail['Table_Name'];
					$sql = "DELETE FROM $tablename WHERE Username= :uname;";
					$stmt = $db->prepare($sql);
					$stmt->bindParam(':uname',$data['username']);
					if($stmt->execute())
					{
						$sql = "UPDATE students SET apply_count = apply_count - 1 WHERE Username= :uname;";
						$stmt = $db->prepare($sql);
						$stmt->bindParam(':uname',$data['username']);
						if($stmt->execute())
						{
							$response = array('success'=>1,'message'=>'Successfully unapplied');
							
							echo json_encode($response);
						}
						else
						{
							$response = array('success'=>0,'message'=>'Error. Please try again later');
							
							echo json_encode($response);
						}
					}
					else
					{
						$response = array('success'=>0,'message'=>'Error. Please try again later');
						
						echo json_encode($response);
					}
				}
			}
		}
	}

	function deleteAccount($data)
	{
		global $db;
		if((isset($data['user_id'])) && (isset($data['username'])))
		{
			$user_id = $data['user_id'];
			$username = $data['username'];
			$deleteFromInvitations = "DELETE FROM invitations WHERE To_Student_ID = :user_id ;";
			$deleteFromInvitationsStmt = $db->prepare($deleteFromInvitations);
			$deleteFromInvitationsStmt->bindParam(':user_id',$user_id);
			if($deleteFromInvitationsStmt->execute())
			{
				if($getMessages = fetchData(array('table'=>'messages','condition'=>'WHERE Sent_by = "'.$username.'" OR Sent_to = "'.$username.'";"')))
				{
					foreach($getMessages as $getMessage)
					{
						$getMessageDetails = fetchData(array('table'=>'messages','condition'=>'WHERE Message_ID="'.$getMessage['Message_ID'].'";'));

						print_r($getMessageDetails);
						die();
					}
				}
				else
				{
					$response = array('success'=>0,'message'=>'Error');
					echo json_encode($response);
				}
			}
			else
			{
				$response = array('success'=>0,'message'=>'Error');
				echo json_encode($response);
			}
		}
		else
		{
			$response = array('success'=>0,'message'=>'Please log out and try again');
			echo json_encode($response);
		}

		
	}
}
?>