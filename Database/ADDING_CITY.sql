DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ADDING_CITY`( new_city_name varchar(20), new_obj_quan int(10))
BEGIN
SET  @city_id = (SELECT MAX(city.city_id) FROM city);
IF @city_id IS NULL THEN SET @city_id = 1;
    ELSE SET @city_id = @city_id + 1;
    END IF;


INSERT INTO city(city_id, city_name, obj_quan) 
VALUES (@city_id, new_city_name, new_obj_quan);
END$$
DELIMITER ;

 