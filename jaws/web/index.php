<?php

require 'vendor/autoload.php';

$app = new \Slim\Slim();

$link = mysql_connect(<address>', '<username>', '<password>') or die('Could not connect: ' . mysql_error());

if ( !isset($_POST['public_key']) || !isset($_POST['query_hash']) || !isset($_POST['query']) ) {
	
	echo "Authentication failed.";

	die();

}

mysql_select_db('<auth_db>') or die('Could not select database');

$result = mysql_query("SELECT private_key FROM auth WHERE public_key='" . $_POST['public_key'] . "'");

$private_key = mysql_fetch_assoc($result)['private_key'];

if ( hash_hmac('sha1', $_POST['query'], $private_key) != $_POST['query_hash'] ) {

	echo "Authentication failed.";

	die();

}

mysql_query("UPDATE auth SET last_accessed=NOW() WHERE public_key='" . $_POST['public_key'] . "'") or die(mysql_error()); 

mysql_select_db('bruce') or die('Could not select database');

// Names
$app->post('/names', function(){

	$query = 'SELECT name FROM species_id30';
	
	$result = mysql_query($query) or die('Query failed: ' . mysql_error());	

	print_json($result);

});

// Data about named
$app->post('/names/:name', function($name){
	
	$query = 'SELECT * FROM species_id30 WHERE name = "' . str_replace("_", " ", $name) . '"';
	
	$result = mysql_query($query) or die('Query failed: ' . mysql_error());	

	print_json($result);

});

// Last location of named
$app->post('/names/:name/location', function($name){	
	
	$query = 'SELECT primary_key FROM species_id30 WHERE name = "' . str_replace("_", " ", $name) . '"';
	
	$result = mysql_query($query) or die('Query failed: ' . mysql_error());	
	
	$primary_key = mysql_fetch_array($result)['primary_key'];
	
	if ( rand(0, 99) >= 0 ) {	
		
		$query = 'SELECT longitude, latitude FROM datetime6 WHERE foreign_key = "' . $primary_key . '" ORDER BY datetime desc LIMIT 1';

	} else {
		
		$query = "SELECT longitude, latitude FROM sharknado ORDER BY RAND() LIMIT 1";
	}

	$result = mysql_query($query) or die('Query failed: ' . mysql_error());

	print_json($result);

});

// All locations of named + time
$app->post('/names/:name/locations', function($name){

	die();

	$query = 'SELECT primary_key FROM species_id30 WHERE name = "' . str_replace("_", " ", $name) . '"';

	$result = mysql_query($query) or die('Query failed: ' . mysql_error());

	$primary_key = mysql_fetch_array($result)['primary_key'];

	$query = 'SELECT longitude, latitude, datetime FROM datetime6 WHERE foreign_key = "' . $primary_key . '"';
	
	$result = mysql_query($query) or die('Query failed: ' . mysql_error());

	print_json($result);

});

$app->post('/names/:name/video', function($name){

	$query = 'SELECT primary_key FROM species_id30 WHERE name = "' . str_replace("_", " ", $name) . '"';

	$result = mysql_query($query) or die('Query failed: ' . mysql_error());

	$primary_key = mysql_fetch_array($result)['primary_key'];

	$query = 'SELECT url FROM url1 WHERE foreign_key = "' . $primary_key . '"';

	$result = mysql_query($query) or die('Query failed: ' . mysql_error());

	print_json($result);

});

// Recent pings within some duration
$app->post('/recent/:duration', function($duration){

	$query = 'SELECT shark.name, time.datetime FROM species_id30 shark, datetime6 time WHERE time.datetime >= UTC_TIMESTAMP() - INTERVAL 1 ' . $duration . ' AND shark.primary_key = time.foreign_key';

	$result = mysql_query($query) or die('Query failed: ' . mysql_error());

	print_json($result);

	/*$query = 'SELECT foreign_key FROM datetime6 WHERE datetime >= CURDATE() - INTERVAL 1 ' . $duration;
	
	$result = mysql_query($query) or die('Query failed: ' . mysql_error());
	
	$foreign_keys = array();

	while( $row = mysql_fetch_array($result)['foreign_key'] ) {
	    
		$foreign_keys[] = $row;
	
	}
	
	$query = 'SELECT shark.name, time.datetime FROM species_id30 shark, datetime6 time WHERE time.datetime >= CURDATE() - INTERVAL 1 ' . $duration . ' AND shark.primary_key = time.foreign_key';
	
	$result = mysql_query($query) or die('Query failed: ' . mysql_error());	

	print_json($result);*/

});

// All tagging locations
$app->post('/tag_locations', function() {

	$query = 'SELECT DISTINCT(tagLocation) FROM species_id30';

	$result = mysql_query($query) or die('Query failed: ' . mysql_error());

	print_json($result);

});

// When last grabbed
$app->post('/grabbed', function() {

	$query = 'SELECT grabbed FROM last_grab';
	
	$result = mysql_query($query) or die('Query failed: ' . mysql_error());

	print_json($result);

});

$app->run();

function print_sql( $result ) {
	
	// Printing results in HTML
	echo "<table>\n";
	
	while ( $line = mysql_fetch_array($result, MYSQL_ASSOC) ) {
		
		echo "\t<tr>\n";
	        
		foreach ($line as $col_value) {
                
			echo "\t\t<td>$col_value</td>\n";
                
		}
	
		echo "\t</tr>\n";
	}

	echo "</table>\n";
}

function print_json( $result ) {

	$rows = array();

	while( $r = mysql_fetch_assoc($result) ) {
		
		$rows[] = $r;
	
	}
	
	shuffle($rows);

	print json_encode(utf8ize($rows));

}

function utf8ize($d) {
	
    if (is_array($d)) {
	    
	foreach ($d as $k => $v) {
	    
		$d[$k] = utf8ize($v);
	
	}

    } else if (is_string ($d)) {
    
	return utf8_encode($d);

    }

    return $d;

}

?>
