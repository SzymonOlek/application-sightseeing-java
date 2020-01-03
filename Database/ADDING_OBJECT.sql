DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ADDING_OBJECT`( new_localization varchar(30), new_name varchar(20), new_city_id int(5), new_description text)
BEGIN
SET  @object_id = (SELECT MAX(object.object_id) FROM object);
IF @object_id IS NULL THEN SET @object_id = 1;
    ELSE SET @object_id = @object_id + 1;
    END IF;


INSERT INTO object(object_id, localization, object_name, city_id, description) 
VALUES (@object_id, new_localization , new_name, new_city_id , new_description );
 

END$$
DELIMITER ;