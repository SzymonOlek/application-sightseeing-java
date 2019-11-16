DELIMITER //

CREATE TRIGGER `DodawanieBanu` AFTER INSERT ON `ban`
 FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('DODANO BAN TYPU ', new.ban_type ,' O ID ' , new.ban_id, ' NA UZYTKOWNIKA O ID ',new.user_id,' OD ',new.date_since ,' DO ' ,new.date_by, ' PRZEZ ADMINA O ID ' , new.admin_id));


SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;







