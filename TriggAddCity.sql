DELIMITER //

CREATE TRIGGER `AddCity` BEFORE INSERT ON `city`
FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT(' DODANO MIASTO O ID ' , new.city_id,' NAZWIE ',new.city_name));


SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;
