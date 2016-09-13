<?php
/*  Get some account to scan
*/

include_once '../db/db.php';
include_once 'utils/accounts.php';

$db = coDb();

/**
 * Abort scan if the scan took too much time
 * 
 * Nb : 1 day is aleready very very long
 * @global type $db
 */
function cleanup(){
    global $db;
    
    $sql = "DELETE FROM InScan WHERE DATEDIFF(NOW(), start) >= 1";
    $stmt = $db->prepare($sql);
    $stmt->execute();
}

/**
 * Get a non scanned person
 * @global type $db
 * @return type non scanned person or false
 */
function getNonScanned(){
    global $db;
    
    $sql = "SELECT id, idstr FROM Pers WHERE lastscan IS NULL ".
            "AND id NOT IN (SELECT acc FROM InScan) LIMIT 1";
    $stmt = $db->prepare($sql);
    $stmt->execute();
    
    return $stmt->fetch(PDO::FETCH_ASSOC);
}

/**
 * Get the oldest person scanned for refresh
 * @global type $db
 * @return type person id to scan or false
 */
function getToRefresh(){
    global $db;
    
    $sql = "SELECT id, idstr FROM Pers WHERE id NOT IN (SELECT acc FROM InScan) ".
            "ORDER BY lastscan DESCR LIMIT 1";
    $stmt = $db->prepare($sql);
    $stmt->execute();
    
    return $stmt->fetch(PDO::FETCH_ASSOC);
}

//////  START

if(acc_verif($_POST['user'], $_POST['pass'])){
    cleanup();
    $ret = getNonScanned();
    if(! $ret){
        $ret = getToRefresh();
    }

    if($ret){
        $sql = "INSERT INTO InScan (acc, start, scanner) VALUES (:id, NOW(), :scan)";
        $stmt = $db->prepare($sql);
        $stmt->bindParam(":id", $ret['id']);
        $stmt->bindParam(":scan", $GLOBALS['acc']);
        $stmt->execute();

        echo $ret['idstr'];
    }
}else
    echo "AUTH";