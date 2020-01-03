DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `del_object_by_id`(object_id_ int(5))
BEGIN 
	IF (SELECT object_id from object WHERE object_id =object_id_ ) IS NOT NULL THEN 
	DELETE FROM object WHERE object_id = object_id_; 
	ELSE SELECT 'There is no such object!'; 
	END IF; 
END$$
DELIMITER ;