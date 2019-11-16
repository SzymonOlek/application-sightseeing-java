DELIMITER //

CREATE PROCEDURE add_user(f_name varchar(20), l_name varchar(20), avatar_path varchar(30), email varchar(20), passwd varchar(20), login varchar(20))
BEGIN

    SET @user_id = (SELECT MAX(sysuser.user_id) FROM sysuser);
    
    IF @user_id IS NULL THEN SET @user_id = 1;
    ELSE SET @user_id = @user_id + 1;
    END IF;
    
    
    
	INSERT INTO sysuser(user_id, f_name, l_name, login, passwd, email, avatar_path, comment_num) 
    VALUES (@user_id, f_name, l_name, login, passwd, email, avatar_path, 0);
    
END//