
DELIMITER //

CREATE PROCEDURE `del_user_by_id`(IN `id` INT)
BEGIN 
	IF (SELECT user_id from sysuser WHERE user_id = id ) IS NOT NULL THEN 
	DELETE FROM sysuser WHERE user_id = id; 
	ELSE SELECT 'There is no such user!'; 
	END IF; 
END//