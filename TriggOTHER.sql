DELIMITER //

CREATE TRIGGER `AddUser` AFTER INSERT ON `sysuser`
FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('DODANO UZYTKOWNIKA O ID ' , NEW.user_id ,' IMIENIU ',NEW.f_name,' NAZWISKU ' ,NEW.l_name , ' Z EMAILEM : ' , NEW.email));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;


DELIMITER //

CREATE TRIGGER `DelUser` BEFORE DELETE ON `sysuser`
FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('USUNIETO UZYTKOWNIKA O ID ' , OLD.user_id ,' IMIENIU ',OLD.f_name,' NAZWISKU ' ,OLD.l_name , ' Z EMAILEM : ' , OLD.email));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;


DELIMITER //

CREATE TRIGGER `DelObject` BEFORE DELETE ON `object`
FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('USUNIETO OBJEKT O ID ' , OLD.object_id ,' LOKALIZACJI ',OLD.localization,' NAZWIE ' ,OLD.object_name));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;

DELIMITER //

CREATE TRIGGER `AddObject` AFTER INSERT ON `object`
FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('DODANO OBJEKT O ID ' , new.object_id ,' LOKALIZACJI ',new.localization,' NAZWIE ' ,new.object_name));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;


DELIMITER //

CREATE TRIGGER `AddComent` AFTER INSERT ON `commentary`
FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('DODANO KOMENTARZ O ID ' , NEW.comment_id,' PRZEZ UZYTKOWNIKA O ID ',NEW.user_id,' DOTYCZACY OBIEKTU O ID ' ,NEW.object_id));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;


DELIMITER //

CREATE TRIGGER `DelComent` BEFORE DELETE ON `commentary`
FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('USUNIETO KOMENTARZ O ID ' , OLD.comment_id,' PRZEZ UZYTKOWNIKA O ID ',OLD.user_id,' DOTYCZACY OBIEKTU O ID ' ,OLD.object_id));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END //
DELIMITER ;





