DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `del_commentary_by_id`(com_id int(100))
BEGIN 
	IF (SELECT comment_id from commentary WHERE comment_id = com_id ) IS NOT NULL THEN 
	DELETE FROM commentary WHERE comment_id = com_id; 
	ELSE SELECT 'There is no such commentary!'; 
	END IF; 
END$$
DELIMITER ;