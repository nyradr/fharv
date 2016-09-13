<?php
/*
 * Language and place database insertion
 * 
 * $GLOBALS['db'] must be defined
 */

/**
 * Get language id from language name
 * @global type $db
 * @param type $lang language name
 * @return type language id or false
 */
function getIdLang($lang){
    
    $sql = "SELECT id FROM Lang WHERE lname = :name";
    $stmt = $GLOBALS['db']->prepare($sql);
    $stmt->bindParam(":name", $lang, PDO::PARAM_STR);
    $stmt->execute();
    
    $ret = $stmt->fetch(PDO::FETCH_ASSOC);
    if($ret)
        $ret = $ret['id'];
    
    return $ret;
}

/**
 * Add list of language to the spoken language list of a person
 * @global type $db
 * @param type $id person id
 * @param type $langs languages list
 */
function addLangs($id, $langs){
    $db = $GLOBALS['db'];
    
    $sql = "INSERT INTO Speak (idpers, idlang) VALUES (:idp, :idl)";
    $stmt_sp = $db->prepare($sql);
    
    $sql = "INSERT INTO Lang (lname) VALUES (:lname)";
    $stmt_lang = $db->prepare($sql);
    
    foreach($langs as $lang){
        $idlang = getIdLang($lang);
        if(!$idlang){
            $stmt_lang->bindParam(":lname", $lang, PDO::PARAM_STR);
            $stmt_lang->execute();
            
            $idlang = $db->lastInsertId();
        }
        
        $stmt_sp->bindParam(":idp", $id, PDO::PARAM_INT);
        $stmt_sp->bindParam(":idl", $idlang, PDO::PARAM_INT);
        $stmt_sp->execute();
    }
}

/**
 * Get id of a place
 * @param type $place place name
 * @return type place id or false if the place isn't in the database
 */
function getPlaceId($place){
    $sql = "SELECT id FROM Place WHERE pname = :name";
    
    $stmt = $GLOBALS['db']->prepare($sql);
    $stmt->bindParam(":name", $place, PDO::PARAM_STR);
    $stmt->execute();
    
    $ret = $stmt->fetch(PDO::FETCH_ASSOC);
    
    if($ret)
        $ret = $ret['id'];
    
    return $ret;
}

/**
 * Add places into related table
 * @param type $id person id
 * @param type $places list of places names
 * @param type $table relation table name
 */
function addPlaces($id, $places, $table){
    $db = $GLOBALS['db'];
    
    $sql = "INSERT INTO $table (idpers, idplace) VALUES(:pers, :place)";
    $stmt_rel = $db->prepare($sql);
    
    $sql = "INSERT INTO Place (pname) VALUES (:name)";
    $stmt_pl = $db->prepare($sql);
    
    foreach ($places as $place){
        $plid = getPlaceId($place);
        if(!$plid){
            $stmt_pl->bindParam(":name", $place, PDO::PARAM_STR);
            $stmt_pl->execute();
            
            $plid = $db->lastInsertId();
        }
        
        $stmt_rel->bindParam(":pers", $id, PDO::PARAM_INT);
        $stmt_rel->bindParam(":place", $plid, PDO::PARAM_INT);
    }
    
}

/**
 * Add workplaces to person works
 * @param type $id person id
 * @param type $places list of places names
 */
function addWorks($id, $places){
    addPlaces($id, $places, "Workat");
}

/**
 * Add places to person references liveplaces
 * @param type $id person id
 * @param type $places list of places names
 */
function addLive($id, $places){
    addPlaces($id, $places, "Live");
}