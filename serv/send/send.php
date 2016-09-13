<?php
/*  Receive account data from remote scanner
*/

include_once 'utils/sendutils.php';
include_once 'utils/accounts.php';

/**
 * Add list of friends to the person list of friends
 * @global type $db
 * @param type $id person id
 * @param type $friends list of friends
 */
function addFriends($id, $friends){
    $sql = "INSERT INTO Friend (idpers, idfrd) VALUES (:id1, :id2)";
    $stmt_frd = $GLOBALS['db']->prepare($sql);
    
    foreach($friends as $frd){
        $idfrd = idExist($frd);
        if(!$idfrd){
            $idfrd = addBasicPers($frd);
        }
        
        $id1 = 0;
        $id2 = 0;
        
        if($id < $idfrd){
            $id1 = $id;
            $id2 = $idfrd;
        }else{
            $id1 = $idfrd;
            $id2 = $id;
        }
        
        $stmt_frd->bindParam(":id1", $id1, PDO::PARAM_INT);
        $stmt_frd->bindParam(":id2", $id2, PDO::PARAM_INT);
        $stmt_frd->execute();
    }
}


///// START
if(acc_verif($_POST['user'], $_POST['pass'])){
    if( isset($_POST['fb'])){
        $json = json_decode($_POST['fb']);

        // get person id
        $id = idExist($json->id);
        // update person info or add person to database
        if($id){
            $sql = "UPDATE Pers SET rname = :name, sexe = :sexe, birth = :birth, lastscan = NOW() WHERE id = :id";
            $stmt = $db->prepare($sql);
            $stmt->bindParam(":name", $json->name, PDO::PARAM_STR);
            $stmt->bindParam(":sexe", $json->sexe, PDO::PARAM_INT);
            $stmt->bindParam(":birth", $json->birth, PDO::PARAM_STR);
            $stmt->bindParam(":id", $id, PDO::PARAM_INT);

            $stmt->execute();
            
            acc_stopscan($id);
        }else{

            $sql = "INSERT INTO Pers (rname, idstr, sexe, birth,lastscan) VALUES (:name, :id, :sexe, :birth, NOW())";
            $stmt = $db->prepare($sql);
            $stmt->bindParam(":name", $json->name, PDO::PARAM_STR);
            $stmt->bindParam(":id", $json->id, PDO::PARAM_STR);
            $stmt->bindParam(":sexe", $json->sexe, PDO::PARAM_INT);
            $stmt->bindParam(":birth", $json->birth, PDO::PARAM_STR);
            $stmt->execute();

            $id = $db->lastInsertId();
        }

        splitText("Namewpart", $id, $json->name);

        addLangs($id, $json->langs);
        addWorks($id, $json->work);
        addLive($db, $json->live);
        addFriends($id, $json->friends);
        addPhotos($id, $json->photos);
    }
}else
    echo "AUTH";