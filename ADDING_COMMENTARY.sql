DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ADDING_COMMENTARY`( nc_user_id int(50), nc_object_id int(10), nc_contents text,  nc_rate enum('1','2','3', '4','5'))
BEGIN
SET  @comment_id = (SELECT MAX(commentary.comment_id) FROM commentary);
IF @comment_id IS NULL THEN SET @comment_id = 1;
    ELSE SET @comment_id = @comment_id + 1;
    END IF;

SET @comment_date = (SELECT CURRENT_DATE() );
INSERT INTO commentary(comment_id, user_id, object_id, contents, comment_date, rate) VALUES (@comment_id, nc_user_id, nc_object_id, nc_contents, @comment_date, nc_rate);
 

END$$
DELIMITER ;
