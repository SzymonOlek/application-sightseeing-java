DELIMITER //

CREATE TRIGGER `DeleteCity` AFTER DELETE ON `city`
FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT(' USUNIETO MIASTO O ID ' , OLD.city_id,' NAZWIE ',OLD.city_name));


SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;
