<!DOCTYPE html>
<?php

if( isset($_POST['get']) or
    isset($_POST['send'])){

    $cu = curl_init();
    
    if( isset($_POST['get'])){
        curl_setopt($cu, CURLOPT_URL, "http://localhost/fbharv/send/get.php");
    }else if (isset($_POST['send'])){
        curl_setopt($cu, CURLOPT_URL, "http://localhost/fbharv/send/send.php");
    }
    
    curl_setopt($cu, CURLOPT_POST, count($_POST));
    curl_setopt($cu, CURLOPT_POSTFIELDS, $_POST);
    curl_setopt($cu, CURLOPT_RETURNTRANSFER, 1);
    
    $res = curl_exec($cu);
    var_dump($res);
    
    curl_close($cu);
}


?>
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <form method="post">
            <div>
                <h3>Account information</h3>
                <input type="text" name="user" placeholder="user">
                <input type="password" name="pass" placeholder="password">
            </div>

            <div>
                <h3>Get scan</h3>
                <input type="submit" name="get">
            </div>

            <div>
                <h3>Post scan information</h3>
                <input type="text" name="fb">
                <input type="submit" name="send">
            </div>
        </form>
    </body>
</html>
