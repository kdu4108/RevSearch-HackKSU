<?php 
$input = $_POST['data'];
// // Replace quotations with escape keys
//$input = str_replace("\"", "\\\"", $input);
#echo $input;

$pieces = explode('934812739', $input);

$output = shell_exec('cd java; java Main "'.$pieces[0].'" "'.$pieces[1].'"'); 
#echo nl2br($output);
echo $output;
?>