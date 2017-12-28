<?php
/**
* Retrieve parameters from url
*/
require_once($_SERVER['DOCUMENT_ROOT'].'/Project Placements/includes/init.php');

require_once($_SERVER['DOCUMENT_ROOT'].'/Project Placements/ProcessParams.php');
if(isset($_REQUEST['view']))
{
	$view = $_REQUEST['view'];
}
else
{
	$view="";
}

switch ($view) {
	case 'login':
		$processParams = new ProcessParams();
		$processParams->login($_REQUEST);
		break;
	case 'getUser':
		$processParams = new ProcessParams();
		$processParams->getUserData($_REQUEST);
		break;
	case 'getNameOfUser':
		$processParams = new ProcessParams();
		$processParams->getNameOfUser($_REQUEST);
		break;
	case 'getImageOfUser':
		$processParams = new ProcessParams();
		$processParams->getImageOfUser($_REQUEST);
		break;
	case 'settingsActivity':
		$processParams = new ProcessParams();
		$processParams->settingsActivity($_REQUEST);
		break;
	case 'getCompanyInvitations':
		$processParams = new ProcessParams();
		$processParams->getCompanyInvitations($_REQUEST);
		break;
	case 'getCompanies':
		$processParams = new ProcessParams();
		$processParams->getCompanies($_REQUEST);
		break;
	case "applyToCompany":
		$processParams = new ProcessParams();
		$processParams->applyToCompany($_REQUEST);
		break;
	case 'getCompanyFilter':
		$processParams = new ProcessParams();
		$processParams->getCompanyFilter();
		break;
	case 'getAppliedCompanies':
		$processParams = new ProcessParams();
		$processParams->getAppliedCompanies($_REQUEST);
		break;
	case 'backoutFromAppliedCompanies':
		$processParams = new ProcessParams();
		$processParams->backoutFromAppliedCompanies($_REQUEST);
		break;
	case 'getVacancyFilter':
		$processParams = new ProcessParams();
		$processParams->getVacancyFilter();
		break;
	case 'getVenueFilter':
		$processParams = new ProcessParams();
		$processParams->getVenueFilter();
		break;
	case 'getPostFilter':
		$processParams = new ProcessParams();
		$processParams->getPostFilter();
		break;
	case 'getCollege':
		$processParams = new ProcessParams();
		$processParams->getCollege($_REQUEST);
		break;
	case 'getNotices':
		$processParams = new ProcessParams();
		$processParams->getNotices($_REQUEST);
		break;
	case "getAllColleges":
		$processParams = new ProcessParams();
		$processParams->getAllColleges();
		break;
	case "getQualification":
		$processParams = new ProcessParams();
		$processParams->getQualification($_REQUEST);
		break;
	case "getQualificationFilter":
		$processParams = new ProcessParams();
		$processParams->getQualificationFilter();
		break;
	case "getPersonalSkills":
		$processParams = new ProcessParams();
		$processParams->getPersonalSkills();
		break;
	case "getPersonalSkillsFilter":
		$processParams = new ProcessParams();
		$processParams->getPersonalSkillsFilter();
		break;
	case "getTechnicalSkills":
		$processParams = new ProcessParams();
		$processParams->getTechnicalSkills();
		break;
	case "getTechnicalSkillsFilter":
		$processParams = new ProcessParams();
		$processParams->getTechnicalSkillsFilter();
		break;
	case 'getSkills':
		$processParams = new ProcessParams();
		$processParams->getSkills();
		break;
	case "getThreadMessaegs":	
		$processParams = new ProcessParams();
		$processParams->getThreadMessaegs($_REQUEST);
		break;
	case "getThreadComments":
		$processParams = new ProcessParams();
		$processParams->getThreadComments($_REQUEST);
		break;
	case "updatePersonalDetails":
		$processParams = new ProcessParams();
		$processParams->updatePersonalDetails($_REQUEST);
		break;
	case "updatePassword":
		$processParams = new ProcessParams();
		$processParams->updatePassword($_REQUEST);
		break;
	case "uploadImage":
		$processParams = new ProcessParams();
		$processParams->uploadImage($_REQUEST);
		break;
	case "acceptInvitations":
		$processParams = new ProcessParams();
		$processParams->acceptInvitations($_REQUEST);
		break;
	case "declineInvitations":
		$processParams = new ProcessParams();
		$processParams->declineInvitations($_REQUEST);
		break;
	case "deleteAccount":
		$processParams = new ProcessParams();
		$processParams->deleteAccount($_REQUEST);
		break;
	default:
		echo "Error";
		break;
}
?>
