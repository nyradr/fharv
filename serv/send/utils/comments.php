<?php
/*
 * Manage comments insertion into database
 * 
 * $GLOBALS['db'] must be defined
 */

include_once 'likes.php';
include_once 'words.php';

/**
 * Delete all comments of an object
 * @param type $trel relation to an object (photo, ...) table
 * @param type $id object id
 */
function delComments($trel, $id){
    $sql = "DELETE FROM Comments WHERE id IN (SELECT com AS id FROM $trel WHERE id = :id)";
    
    $stmt = $GLOBALS['db']->prepare($sql);
    $stmt->bindParam(":id", $id, PDO::PARAM_INT);
    $stmt->execute();
}

/**
 * Add list of comments to database
 * @param type $trel relation to an object (photo, ...) table
 * @param type $id object id 
 * @param type $json comments list as a json
 */
function addComments($trel, $id, $json){
    $db = $GLOBALS['db'];
    
    delComments($trel, $id);
    
    $sql = "INSERT INTO Comments (postby, datep, txt) VALUES (:post, :date, :txt)";
    $stmt_com = $db->prepare($sql);
    
    $sql = "INSERT INTO $trel (id, com) VALUES (:id, :com)";
    $stmt_rel = $db->prepare($sql);
    
    foreach($json as $com){
        $pid = idExist($com->post);
        if(!$pid)
            $pid = addBasicPers ($com->post);
        
        $stmt_com->bindParam(":post", $pid, PDO::PARAM_INT);
        $stmt_com->bindParam(":date", $com->date, PDO::PARAM_STR);
        $stmt_com->bindParam(":txt", $com->val, PDO::PARAM_STR);
        
        $stmt_com->execute();
        
        $comid = $db->lastInsertId();
        
        $stmt_rel->bindParam(":id", $id, PDO::PARAM_INT);
        $stmt_rel->bindParam(":com", $comid, PDO::PARAM_INT);
        $stmt_rel->execute();
        
        com_likes($comid, $com->likes);
        
        w_compart($comid, $com->val);
    }    
}

/**
 * Add comments to a photo
 * @param type $id photo id
 * @param type $json comments list as json
 */
function ph_coms($id, $json){
    addComments("Phcoms", $id, $json);
}