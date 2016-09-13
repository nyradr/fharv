<?php
/*
 * Add photo to database
 * 
 * $GLOBALS['db'] must be defined
 */

include_once 'comments.php';
include_once 'likes.php';
include_once 'words.php';

function getPhotoId($url, $poster){
    $sql = "SELECT id FROM Photo WHERE img = :url AND postby = :post";
    
    $stmt = $GLOBALS['db']->prepare($sql);
    $stmt->bindParam(":url", $url, PDO::PARAM_STR);
    $stmt->bindParam(":post", $poster, PDO::PARAM_INT);
    $stmt->execute();
    
    $ret = $stmt->fetch(PDO::FETCH_ASSOC);
    if($ret)
        $ret = $ret['id'];
    
    return $ret;
}

/**
 * Add photos list to database
 * @param type $id person id
 * @param type $json list of photos as json
 */
function addPhotos($id, $json){
    $sql = "INSERT INTO Photo (postby, datep, img, descr) "
            . "VALUES (:post, :date, :img, :descr)";
    
    $stmt = $GLOBALS['db']->prepare($sql);
    
    foreach($json as $photo){
        $stmt->bindParam(":post", $id, PDO::PARAM_INT);
        $stmt->bindParam(":date", $photo->post, PDO::PARAM_STR);
        $stmt->bindParam(":img", $photo->url);
        $stmt->bindParam(":descr", $photo->descr);
        $stmt->execute();
        
        if($stmt->errorCode() == '00000')
            $phid = $GLOBALS['db']->lastInsertId();
        else
            $phid = getPhotoId ($photo->url, $id);
        
        w_phpart($phid, $photo->descr);
        ph_likes($phid, $photo->likes);
        ph_refs($phid, $photo->refs);
        ph_coms($phid, $photo->coms);
    }
}