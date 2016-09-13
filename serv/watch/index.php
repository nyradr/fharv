<!DOCTYPE html>
<?php
include_once '../acc/accutils.php';
session_start();
if(!isLogged()){
    $_SESSION['loginret'] = $_SERVER['PHP_SELF'];
    header("Location: ../acc/login.php");
}


?>
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        // put your code here
        ?>
    </body>
</html>
