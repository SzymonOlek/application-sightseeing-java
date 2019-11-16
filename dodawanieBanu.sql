DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddBannedUser`(IN `id_user` INT, IN `adminID` INT, IN `banType` ENUM('comment ban','perma ban'), IN `howManyDays` INT)
BEGIN

SET @banID = (SELECT max(ban.ban_id) from ban);

IF @banID IS NULL THEN
SET @banID=1;
END IF;

SET @EndBanData =(select DATE_ADD(CURDATE(),INTERVAL howManyDays DAY));

INSERT INTO ban values(@banID,id_user,adminID,banType,CURDATE(), @EndBanData);

END$$
DELIMITER ;