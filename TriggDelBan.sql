DELIMITER //

CREATE TRIGGER `UsuwanieBanu` AFTER DELETE ON `ban`
 FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('USUNIETO BAN TYPU ', OLD.ban_type ,' O ID ' , OLD.ban_id, ' Z UZYTKOWNIKA O ID ',OLD.user_id,' ZACZYNAJACY SIE ',old.date_since ,' KONCZACY SIE ' ,old.date_by, ' NAŁOŻONY PRZEZ ADMINA O ID ' , old.admin_id));


SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;







