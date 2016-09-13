<!DOCTYPE html>
<?php
include_once '../acc/accutils.php';
include_once '../db/db.php';

session_start();
if(!isLogged()){
    $_SESSION['loginret'] = $_SERVER['PHP_SELF'];
    header("Location: ../acc/login.php");
}

if(isset($_POST["sql"])){
    $db = coWatch();
    
    
}

?>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Execute SQL query</title>
    </head>
    <body>
        <h1>Execute a SELECT sql query on persons database</h1>
        <form method="post">
            <input type="text" name="sql" placeholder="SQL query">
            <input type="submit" name="sub" value="Execute">
        </form>
        
    </body>
</html>
