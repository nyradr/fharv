<?php
/*
 * Account functions
 * 
 * $GLOBALS['db'] must be defined
 */

/**
 * Verify account
 * If the account is verified $GLOBALS['acc'] is set to account id
 * @param type $name user name
 * @param type $pass user password
 * @return type
 */
function acc_verif($name, $pass){
    $verified = false;
    $sql = "SELECT * FROM Scanner WHERE sname = :name";
    
    $stmt = $GLOBALS['db']->prepare($sql);
    $stmt->bindParam(":name", $name, PDO::PARAM_STR);
    $stmt->execute();
    
    $res = $stmt->fetch(PDO::FETCH_ASSOC);
    
    if($res){
        if(password_verify($pass, $res['pass'])){
            $verified = ($res['status'] > 0);
            if($verified){
                $GLOBALS['acc'] = $res['id'];
            }
            
            
            if(password_needs_rehash($res['pass'], PASSWORD_DEFAULT)){
                $sql = "UPDATE Scanner SET pass = :pass WHERE id = :id";
                $stmti = $GLOBALS['db']->prepare($sql);
                
                $stmti->bindParam(":pass", password_hash($pass, PASSWORD_DEFAULT));
                $stmti->bindParam(":id", $res['id']);
                $stmti->execute();
            }
        }
    }
    
    return $verified;
}

function acc_stopscan($id){
    $sql = "DELETE FROM InScan WHERE acc = :id AND scanner = :acc";
    
    $stmt = $GLOBALS['db']->prepare($sql);
    $stmt->bindParam(":id", $id, PDO::PARAM_INT);
    $stmt->bindParam(":acc", $GLOBALS['acc']);
    $stmt->execute();
}