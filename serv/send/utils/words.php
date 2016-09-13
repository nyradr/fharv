<?php
/*
 * Manage words insertion
 * 
 * $GLOBALS['db'] must befined as pdo instance
 */

/**
 * Add word to the word list
 * If the word is already in the database the id will be retuned
 * @global type $db
 * @param type $word word
 * @return type word id
 */
function addWord($word){
    $db = $GLOBALS['db'];
    
    $word = strtolower($word);
    
    $sql = "SELECT id FROM Word WHERE word = :word";
    $stmt_f = $db->prepare($sql);
    $stmt_f->bindParam(":word", $word);
    $stmt_f->execute();
    
    $id = $stmt_f->fetch();
    
    if(!$id){
        $sql = "INSERT INTO Word (word) VALUES (:word)";
        $stmt = $db->prepare($sql);
        $stmt->bindParam(":word", $word);
    
        $stmt->execute();
        
        $id = $db->lastInsertId();
    } else {
      $id = $id['id'];  
    }
    
    return $id;
}

/**
 * Split text into list of worlds and insert them into the database
 * @global type $db
 * @param type $table table name
 * @param type $id element id
 * @param type $text text to split
 */
function splitText($table, $id, $text){
    $db = $GLOBALS['db'];
    
    // clean old values
    $sql = "DELETE FROM $table WHERE id = :id";
    $stmt_c = $db->prepare($sql);
    $stmt_c->bindParam(":id", $id);
    $stmt_c->execute();
    
    $sql = "INSERT INTO $table (id, word, place) VALUES (:id, :word, :place)";
    $stmt = $db->prepare($sql);
    
    $toks = str_word_count($text, 1);

    $i = 0;
    foreach($toks as $tok){
        $idw = addWord($tok);
        
        $stmt->bindParam(":id", $id);
        $stmt->bindParam(":word", $idw);
        $stmt->bindParam(":place", $i);
        $stmt->execute();
        
        $i++;
    }
}

/**
 * Insert person name into words database
 * @param type $id
 * @param type $name
 */
function w_namepart($id, $name){
    splitText("Namewpart", $id, $name);
}

/**
 * Insert photo description into words database
 * @param type $id
 * @param type $text
 */
function w_phpart($id, $text){
    splitText("Phwpart", $id, $text);
}

/**
 * Insert comment value into words database
 * @param type $id
 * @param type $text
 */
function w_compart($id, $text){
    splitText("Comwpart", $id, $text);
}