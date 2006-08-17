<html>
<head>
<title>
Buddi - Personal budget software for the rest of us
</title>
<link rel='stylesheet' href='../css/style.css' type='text/css' media='screen' />
</head>
<frameset rows="35,*">
	<frame src="http://buddi.sourceforge.net/language.php" 
noresize="noresize" frameborder=no framespacing=0>
<? 
if ($lang=='')
	$lang='en';
?>
	<frame src="http://buddi.sourceforge.net/<? echo $lang; ?>/index.php" 
name="body" noresize="noresize" frameborder=no framespacing=0>
</frameset>
</html>
