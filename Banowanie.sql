DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delBan`(UserID INT,BanType enum('comment ban', 'perma ban'))
BEGIN

DELETE from ban where ban.user_id=UserID and ban.ban_type=BanType;

END$$
DELIMITER ;