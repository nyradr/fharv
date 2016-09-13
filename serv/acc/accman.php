<!DOCTYPE html>
<?php
include_once '../db/db.php';

function getAccount(){
    global $db;
    
    $sql = "SELECT id, sname AS user, status FROM Scanner WHERE ";
    if(isset($_SESSION['id'])){
        $sql .= "id = :id";
        $id = $_SESSION['id'];
    
    }else{
        $sql .= "sname = :id";
        $id = $_SESSION['user'];
    }
    
    $stmt = $db->prepare($sql);
    $stmt->bindParam(":id", $id);
    $stmt->execute();
    
    $ret = $stmt->fetch(PDO::FETCH_ASSOC);
    
    $_SESSION['id'] = $ret['id'];
    
    return $ret;
}

function getCurScans($id){
    global $db;
    
    $sql = "SELECT count(*) AS nbr FROM InScan WHERE scanner = :id";
    
    $stmt = $db->prepare($sql);
    $stmt->bindParam(":id", $id, PDO::PARAM_INT);
    $stmt->execute();
    
    $ret = $stmt->fetch(PDO::FETCH_ASSOC);
    
    return $ret['nbr'];
}

///// START

session_start();
// out if not logged
if(! $_SESSION['logged']){
    header("Location: login.php");
}

$db = coDb();

$account = getAccount();

?>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Welcome <?php echo $account['user']?></title>
    </head>
    <body>
        <header>
            <a href="logout.php">Logout</a>
        </header>
        <div>
            There is <?php echo getCurScans($account['id']);?> scan(s) running
        </div>
    </body>
</html>
