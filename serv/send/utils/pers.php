<?php
/*
 * Persons functions
 * 
 * $GLOBALS['db'] must be defined
 */

/**
 * Test if the facebook ID is already in the database
 * If the account exist his internal id is returned, if not false is returned
 * @global type $db
 * @param type $idstr
 * @return type
 */
function idExist($idstr){
    global $db;
    
    $sql = "SELECT id FROM Pers WHERE idstr = :id";
    $stmt = $db->prepare($sql);
    $stmt->bindParam(":id", $idstr, PDO::PARAM_STR);
    $stmt->execute();
    
    $ret = $stmt->fetch();
    
    if($ret)
        $ret = $ret['id'];
    
    return $ret;
}

/**
 * Add empty person to database
 * @global type $db
 * @param type $fbid
 * @return type database person id
 */
function addBasicPers($fbid){
    global $db;
    
    $sql = "INSERT INTO Pers (idstr) VALUES (:idstr)";
    $stmt = $db->prepare($sql);
    
    $stmt->bindParam(":idstr", $fbid, PDO::PARAM_STR);
    $stmt->execute();
    
    return $db->lastInsertId();
}