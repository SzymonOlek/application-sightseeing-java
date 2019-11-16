DELIMITER //

CREATE PROCEDURE add_route(cityID int, object1ID int, object2ID int, distance float)
BEGIN

    SET @route_id = (SELECT MAX(route.route_id) FROM route);
    SET @exist_route = (SELECT route.route_id from route where 
                   (route.object_1_id=object1ID AND route.object_2_id=object2ID) 
                   OR (route.object_1_id=object2ID AND route.object_2_id=object1ID) 
                   AND route.city_id = cityID);
    SET @exist_obj1 = (SELECT object.object_id FROM object 
                       WHERE object.object_id = object1ID 
                       AND object.city_id = cityID);
    SET @exist_obj2 = (SELECT object.object_id FROM object 
                       WHERE object.object_id = object2ID 
                       AND object.city_id = cityID);
    
    IF (@exist_route IS null) AND (@exist_obj1 is NOT null) AND (@exist_obj2 is NOT null) THEN
    	
    	IF (@route_id IS null) THEN
        	SET @route_id = 1;
        ELSE SET @route_id = @route_id + 1;
        END IF;
        
        INSERT INTO route(route_id, object_1_id, object_2_id, city_id, distance) 
        		VALUES (@route_id, object1ID, object2ID, cityID, distance);
        
    END IF;
        
    
END//