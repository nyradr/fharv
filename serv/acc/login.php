<!DOCTYPE html>
<?php
include_once "../db/db.php";
include_once 'accutils.php';

$err = false;

session_start();
if(!isset($_SESSION['logged']))
    $_SESSION['logged'] = false;

if( isset($_POST['user']) and
    isset($_POST['pass'])){
    
    $db = coDb();
    
    $user = htmlspecialchars($_POST['user']);
    $pass = htmlspecialchars($_POST['pass']);
  
    $_SESSION['logged'] = acc_verif($user, $pass);
    $_SESSION['user'] = $user;
    
    $err = ! $_SESSION['logged'];
}

if($_SESSION['logged']){
    if(!isset($_SESSION['loginret'])){
        $_SESSION['loginret'] = 'accman.php';
    }
    
    header("Location: " . $_SESSION['loginret']);
    unset($_SESSION['loginret']);
}

?>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form method="post">
            <input type="text" name="user" placeholder="User name"><br>
            <input type="password" name="pass" placeholder="password"><br>
            <?php
            if($err)
                echo "<p>Login failed</p>";
            ?>
            <input type="submit" name="sub" value="Login"><br>
            <a href="signin.php">No account, create one</a>
        </form>
    </body>
</html>
