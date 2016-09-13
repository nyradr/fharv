<?php
/*
 * Add likes to database
 * Persons references act likes likes
 * 
 * $GLOBALS['db'] must be defined
 */

include_once 'pers.php';

/**
 * Add likes to a like table
 * @global type $db
 * @param type $table like table
 * @param type $id photo ID
 * @param type $likes persons who likes 
 */
function addLikes($table, $id, $likes){
    global $db;
    
    $sql = "INSERT INTO $table(id, pers) VALUES (:id, :pers)";
    $stmt_lk = $db->prepare($sql);
    
    foreach($likes as $lk){
        $fbid = idExist($lk);
        if(!$fbid)
            $fbid = addBasicPers ($lk);
        
        $stmt_lk->bindParam(":id", $id, PDO::PARAM_INT);
        $stmt_lk->bindParam(":pers", $fbid, PDO::PARAM_INT);
        $stmt_lk->execute();
    }
}

/**
 * Add likes to a photo
 * @param type $id photo id
 * @param type $likes list of fbid
 */
function ph_likes($id, $likes){
    addLikes("Phlike", $id, $likes);
}

/**
 * Add references person to a photo
 * References is defferents from likes
 * @param type $id photo id
 * @param type $refs list of fbid
 */
function ph_refs($id, $refs){
    addLikes("Phrefs", $id, $refs);
}

/**
 * Add likes to a comment
 * @param type $id comment id
 * @param type $likes list of fbid
 */
function com_likes($id, $likes){
    addLikes("Comlike", $id, $likes);
}