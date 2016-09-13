<!DOCTYPE html>
<?php

include_once '../db/db.php';

$error = false;

if( isset($_POST['user']) and
    isset($_POST['pass'])){
    
    $db = coDb();
    
    $user = htmlspecialchars($_POST['user']);
    $pass = htmlspecialchars($_POST['pass']);
    $pass = password_hash($pass, PASSWORD_DEFAULT);
    
    $sql = "INSERT INTO Scanner (sname, pass, status) VALUES (:name, :pass, 1)";
    $stmt = $db->prepare($sql);
    $stmt->bindParam(":name", $user, PDO::PARAM_STR);
    $stmt->bindParam(":pass", $pass, PDO::PARAM_STR);
    $stmt->execute();
    
    if($stmt->errorCode() != '00000')
        $error = $stmt->errorInfo ()[2];
}

?>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Account creation</title>
    </head>
    <body>
        <form method="post">
            <input type="text" name="user" placeholder="user name">
            <input type="password" name="pass" placeholder="password">
            <input type="submit" name="sub" value="Create">
            <?php
            if($error)
                echo $error;
            ?>
        </form>
    </body>
</html>
