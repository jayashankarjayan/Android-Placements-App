<?php 
/**
	fetch data from the specified table

	$param array
*/

$tables = array(
			'students',
			'companies_placements',
			'colleges',
			'notices',
			'personal_skills',
			'technical_skills',
			'courses',
			'discussion_threads',
			'invitations',
			'ssc_details',
			'messages',
			'hsc_details',
			'sem_1_details',
			'sem_2_details',
			'sem_3_details',
			'sem_4_details',
			'sem_5_details',
			'sem_6_details'
		);



function addTableToList($tableName='')
{
	global $tables;

	if(!in_array($tableName, $tables))
	{
		if($tableName != '')
		{
			array_push($tables, $tableName);
			return true;
		}
	}
}


function fetchData($parameters)
{
	global $db;
	global $tables;

	$table = isset($parameters['table']) ? $parameters['table'] : "";
	$condition = isset($parameters['condition']) ? $parameters['condition'] : "";

	if(!empty($table) && in_array($table, $tables))
	{
		$sql = "SELECT * FROM $table $condition";
		
		// echo $sql;
		$result_set = $db->query($sql);
		
		if($result_set->rowCount())
		{
			return $result_set->fetchAll(PDO::FETCH_ASSOC);
		}
		else
		{

			return false;
		}
	}
	else
	{
		return false;
	}
	
}

function fetchDistinctData($parameters,$column)
{
	global $db;
	global $tables;

	$table = isset($parameters['table']) ? $parameters['table'] : "";
	$condition = isset($parameters['condition']) ? $parameters['condition'] : "";

	if(!empty($table) && in_array($table, $tables))
	{
		$sql = "SELECT distinct($column) FROM $table $condition";
		
		 // echo $sql;
		$result_set = $db->query($sql);
		
		if($result_set->rowCount())
		{
			return $result_set->fetchAll(PDO::FETCH_ASSOC);
		}
		else
		{

			return false;
		}
	}
	else
	{
		return false;
	}
	
}


function fetchSingleColumnData($parameters,$column)
{
	global $db;
	global $tables;

	$table = isset($parameters['table']) ? $parameters['table'] : "";
	$condition = isset($parameters['condition']) ? $parameters['condition'] : "";

	if(!empty($table) && in_array($table, $tables))
	{
		$sql = "SELECT $column FROM $table $condition";
		
		 // echo $sql;
		$result_set = $db->query($sql);
		
		if($result_set->rowCount())
		{
			return $result_set->fetchAll(PDO::FETCH_ASSOC);
		}
		else
		{

			return false;
		}
	}
	else
	{
		return false;
	}
	
}
?>

