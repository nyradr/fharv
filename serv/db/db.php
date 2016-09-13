<?php

define("HOST", "localhost");
define("DB", "fbharv");

define("PDOCST", "mysql:host=" .HOST. ";dbname=" .DB);

/**
 * Connect to database and get PDO object
 * @return \PDO
 */
function coDb(){
    $user = "fbharv";
    $pass = "f";
    
    if(!isset($GLOBALS['db'])){
        $GLOBALS['db'] = new PDO(PDOCST, $user, $pass)
                or die("DB connection failed");
    }
    
    return $GLOBALS['db'];
}

function coWatch(){
    $user = "fbuse";
    $pass = "f";
    
    if(!isset($GLOBALS['wdb'])){
        $GLOBALS['wdb'] = new PDO(PDOCST, $user, $pass)
                or die("Db connection failed");
    }
    
    return $GLOBALS['wdb'];
}