DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delBanByBanID`(BanID INT)
BEGIN

DELETE from ban where ban.ban_id=BanID;

END$$
DELIMITER ;