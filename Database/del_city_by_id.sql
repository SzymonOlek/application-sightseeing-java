DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `del_city_by_id`(city_id_ int(5))
BEGIN 
	IF (SELECT city_id from city WHERE city_id =city_id_ ) IS NOT NULL THEN 
	DELETE FROM city WHERE city_id =city_id_; 
	ELSE SELECT 'There is no such city!'; 
	END IF; 
END$$
DELIMITER ;