-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 16 Gru 2019, 13:47
-- Wersja serwera: 10.4.8-MariaDB
-- Wersja PHP: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `sightseeing`
--

DELIMITER $$
--
-- Procedury
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddBannedUser` (IN `id_user` INT, IN `adminID` INT, IN `banType` ENUM('comment ban','perma ban'), IN `howManyDays` INT)  BEGIN

SET @banID = (SELECT max(ban.ban_id) from ban);

IF @banID IS NULL THEN
SET @banID=1;
END IF;

SET @EndBanData = (select DATE_ADD(CURDATE(),INTERVAL howManyDays DAY));

INSERT INTO ban(ban_id,user_id,admin_id,ban_type,date_since,date_by) values(@banID,id_user,adminID,banType,CURDATE(), @EndBanData);

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ADDING_CITY` (`new_city_name` VARCHAR(20), `new_obj_quan` INT(10))  BEGIN
SET  @city_id = (SELECT MAX(city.city_id) FROM city);
IF @city_id IS NULL THEN SET @city_id = 1;
    ELSE SET @city_id = @city_id + 1;
    END IF;


INSERT INTO city(city_id, city_name, obj_quan) 
VALUES (@city_id, new_city_name, new_obj_quan);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ADDING_COMMENTARY` (IN `nc_user_id` INT(50), IN `nc_object_id` INT(10), IN `nc_contents` TEXT, IN `nc_rate` ENUM('1','2','3','4','5'))  BEGIN
	SET  @comment_id = (SELECT MAX(comment_id) FROM commentary);
	IF @comment_id IS NULL THEN SET @comment_id = 0;
    	ELSE SET @comment_id = @comment_id + 1;
   	END IF;

	SET @comment_date = (SELECT CURRENT_DATE() );
	INSERT INTO commentary(comment_id, user_id, object_id, contents, comment_date, rate) 
	VALUES (@comment_id, nc_user_id, nc_object_id, nc_contents, @comment_date, nc_rate);
 

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ADDING_OBJECT` (`new_localization` VARCHAR(30), `new_name` VARCHAR(20), `new_city_id` INT(5), `new_description` TEXT)  BEGIN
SET  @object_id = (SELECT MAX(object.object_id) FROM object);
IF @object_id IS NULL THEN SET @object_id = 1;
    ELSE SET @object_id = @object_id + 1;
    END IF;


INSERT INTO object(object_id, localization, object_name, city_id, description) 
VALUES (@object_id, new_localization , new_name, new_city_id , new_description );
 

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_route` (IN `cityID` INT, IN `object1ID` INT, IN `object2ID` INT, IN `distance` FLOAT)  BEGIN

    SET @route_id = (SELECT MAX(route.route_id) FROM route);
    SET @exist_route = (SELECT route.route_id from route where 
                   (route.object_1_id=object1ID AND route.object_2_id=object2ID) 
                   OR (route.object_1_id=object2ID AND route.object_2_id=object1ID) 
                   AND route.city_id = cityID);
    SET @exist_obj1 = (SELECT object.object_id FROM object 
                       WHERE object.object_id = object1ID 
                       AND object.city_id = cityID);
    SET @exist_obj2 = (SELECT object.object_id FROM object 
                       WHERE object.object_id = object2ID 
                       AND object.city_id = cityID);
    
    IF (@exist_route IS null) AND (@exist_obj1 is NOT null) AND (@exist_obj2 is NOT null) THEN
    	
    	IF (@route_id IS null) THEN
        	set @route_id = 1;
        ELSE SET @route_id = @route_id + 1;
        END IF;
        
        INSERT INTO route(route_id, object_1_id, object_2_id, city_id, distance) 
        		VALUES (@route_id, object1ID, object2ID, cityID, distance);
        ELSE SELECT 'route exists!';
    END IF;
        
    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_user` (`f_name` VARCHAR(20), `l_name` VARCHAR(20), `avatar_path` VARCHAR(30), `email` VARCHAR(20), `passwd` VARCHAR(20), `login` VARCHAR(20))  BEGIN
	
    SET @user_id = (SELECT MAX(sysuser.user_id) FROM sysuser);
    
    IF @user_id IS NULL THEN SET @user_id = 0;
    ELSE SET @user_id = @user_id + 1;
    END IF;
    
    
    
	INSERT INTO sysuser(user_id, f_name, l_name, login, passwd, email, avatar_path, comment_num) 
    VALUES (@user_id, f_name, l_name, login, passwd, email, avatar_path, 0);
    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delBan` (`UserID` INT, `BanType` ENUM('comment ban','perma ban'))  BEGIN

DELETE from ban where ban.user_id=UserID and ban.ban_type=BanType;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delBanByBanID` (`BanID` INT)  BEGIN

DELETE from ban where ban.ban_id=BanID;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `del_city_by_id` (`city_id_` INT(5))  BEGIN 
	IF (SELECT city_id from city WHERE city_id =city_id_ ) IS NOT NULL THEN 
	DELETE FROM city WHERE city_id =city_id_; 
	ELSE SELECT 'There is no such city!'; 
	END IF; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `del_commentary_by_id` (`com_id` INT(100))  BEGIN 
	IF (SELECT comment_id from commentary WHERE comment_id = com_id ) IS NOT NULL THEN 
	DELETE FROM commentary WHERE comment_id = com_id; 
	ELSE SELECT 'There is no such commentary!'; 
	END IF; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `del_object_by_id` (`object_id_` INT(5))  BEGIN 
	IF (SELECT object_id from object WHERE object_id =object_id_ ) IS NOT NULL THEN 
	DELETE FROM object WHERE object_id = object_id_; 
	ELSE SELECT 'There is no such object!'; 
	END IF; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `del_user_by_id` (IN `id` INT)  BEGIN
    
    IF (SELECT user_id from sysuser WHERE user_id = id ) IS NOT NULL THEN 
    DELETE FROM sysuser WHERE user_id = id;
    ELSE SELECT 'There is no such user!';
    END IF;
    
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(10) NOT NULL,
  `f_name` varchar(20) NOT NULL,
  `l_name` varchar(20) NOT NULL,
  `login` varchar(20) NOT NULL,
  `passwd` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `avatar_path` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `admin`
--

INSERT INTO `admin` (`admin_id`, `f_name`, `l_name`, `login`, `passwd`, `email`, `avatar_path`) VALUES
(1, 'Adam', 'Kowalski', 'login1', 'psw', 'Admin1EMAIL', 'awatar_Path1'),
(2, 'Tomek', 'Kotlet', 'login2', 'psw2', 'Kotlet2EMAIL', 'awatar_Path2');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ban`
--

CREATE TABLE `ban` (
  `ban_id` int(50) NOT NULL,
  `user_id` int(50) NOT NULL,
  `admin_id` int(10) NOT NULL,
  `ban_type` enum('comment_ban','perma_ban') DEFAULT NULL,
  `date_since` date NOT NULL,
  `date_by` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `ban`
--

INSERT INTO `ban` (`ban_id`, `user_id`, `admin_id`, `ban_type`, `date_since`, `date_by`) VALUES
(1, 1, 1, 'perma_ban', '2019-11-16', '2019-11-26');

--
-- Wyzwalacze `ban`
--
DELIMITER $$
CREATE TRIGGER `AddBan` AFTER INSERT ON `ban` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('DODANO BAN TYPU ', new.ban_type ,' O ID ' , new.ban_id, ' NA UZYTKOWNIKA O ID ',new.user_id,' OD ',new.date_since ,' DO ' ,new.date_by, ' PRZEZ ADMINA O ID ' , new.admin_id));


SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `DeleteBan` AFTER DELETE ON `ban` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('USUNIETO BAN TYPU ', OLD.ban_type ,' O ID ' , OLD.ban_id, ' Z UZYTKOWNIKA O ID ',OLD.user_id,' ZACZYNAJACY SIE ',old.date_since ,' KONCZACY SIE ' ,old.date_by, ' NAŁOŻONY PRZEZ ADMINA O ID ' , old.admin_id));


SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `city`
--

CREATE TABLE `city` (
  `city_id` int(5) NOT NULL,
  `city_name` varchar(20) NOT NULL,
  `obj_quan` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `city`
--

INSERT INTO `city` (`city_id`, `city_name`, `obj_quan`) VALUES
(1, 'Warszawa', 10),
(2, 'Kraków', 5);

--
-- Wyzwalacze `city`
--
DELIMITER $$
CREATE TRIGGER `AddCity` BEFORE INSERT ON `city` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT(' DODANO MIASTO O ID ' , new.city_id,' NAZWIE ',new.city_name));


SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `DeleteCity` AFTER DELETE ON `city` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT(' USUNIETO MIASTO O ID ' , OLD.city_id,' NAZWIE ',OLD.city_name));


SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `commentary`
--

CREATE TABLE `commentary` (
  `comment_id` int(100) NOT NULL,
  `user_id` int(50) NOT NULL,
  `object_id` int(10) NOT NULL,
  `contents` text NOT NULL,
  `comment_date` date NOT NULL,
  `rate` enum('one','two','three','four','five') DEFAULT NULL,
  `commentary_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Wyzwalacze `commentary`
--
DELIMITER $$
CREATE TRIGGER `AddComent` AFTER INSERT ON `commentary` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('DODANO KOMENTARZ O ID ' , NEW.comment_id,' PRZEZ UZYTKOWNIKA O ID ',NEW.user_id,' DOTYCZACY OBIEKTU O ID ' ,NEW.object_id));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `DelComent` BEFORE DELETE ON `commentary` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('USUNIETO KOMENTARZ O ID ' , OLD.comment_id,' PRZEZ UZYTKOWNIKA O ID ',OLD.user_id,' DOTYCZACY OBIEKTU O ID ' ,OLD.object_id));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1),
(1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `logs`
--

CREATE TABLE `logs` (
  `log_id` int(255) NOT NULL,
  `log_text` text NOT NULL,
  `log_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `object`
--

CREATE TABLE `object` (
  `object_id` int(10) NOT NULL,
  `localization` varchar(30) NOT NULL,
  `object_name` varchar(20) NOT NULL,
  `city_id` int(5) NOT NULL,
  `decription` text NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `object`
--

INSERT INTO `object` (`object_id`, `localization`, `object_name`, `city_id`, `decription`, `description`) VALUES
(1, 'dupa', 'zamek', 1, 'sadrhahdh', NULL),
(2, 'dupa', 'zamek2', 1, 'sadrhahdh', NULL);

--
-- Wyzwalacze `object`
--
DELIMITER $$
CREATE TRIGGER `AddObject` AFTER INSERT ON `object` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('DODANO OBJEKT O ID ' , new.object_id ,' LOKALIZACJI ',new.localization,' NAZWIE ' ,new.object_name));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `DelObject` BEFORE DELETE ON `object` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('USUNIETO OBJEKT O ID ' , OLD.object_id ,' LOKALIZACJI ',OLD.localization,' NAZWIE ' ,OLD.object_name));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `photo`
--

CREATE TABLE `photo` (
  `photo_id` int(10) NOT NULL,
  `object_id` int(10) NOT NULL,
  `photo_path` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `route`
--

CREATE TABLE `route` (
  `route_id` int(50) NOT NULL,
  `object_1_id` int(10) NOT NULL,
  `object_2_id` int(10) NOT NULL,
  `distance` float(5,3) NOT NULL,
  `city_id` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `route`
--

INSERT INTO `route` (`route_id`, `object_1_id`, `object_2_id`, `distance`, `city_id`) VALUES
(1, 1, 2, 99.999, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sysuser`
--

CREATE TABLE `sysuser` (
  `user_id` int(50) NOT NULL,
  `f_name` varchar(20) NOT NULL,
  `l_name` varchar(20) NOT NULL,
  `login` varchar(20) NOT NULL,
  `passwd` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `avatar_path` varchar(30) DEFAULT NULL,
  `comment_num` int(10) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `sysuser`
--

INSERT INTO `sysuser` (`user_id`, `f_name`, `l_name`, `login`, `passwd`, `email`, `avatar_path`, `comment_num`, `id`) VALUES
(1, 'Tomek', 'NAziwksoTomka', 'TomekLogin', 'psw', 'TomekEmail', 'TOmekAwatar', 0, 0),
(2, 'Adam', 'NAziwksAdam', 'AdamLogin', 'psw', 'AdamEmail', 'AdamAwatar', 0, 0),
(3, 'Kamil', 'NAziwksoKamil', 'KamilLogin', 'psw', 'KamilEmail', 'KamilkAwatar', 0, 0);

--
-- Wyzwalacze `sysuser`
--
DELIMITER $$
CREATE TRIGGER `AddUser` AFTER INSERT ON `sysuser` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('DODANO UZYTKOWNIKA O ID ' , NEW.user_id ,' IMIENIU ',NEW.f_name,' NAZWISKU ' ,NEW.l_name , ' Z EMAILEM : ' , NEW.email));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `DelUser` BEFORE DELETE ON `sysuser` FOR EACH ROW BEGIN

SET @text = (SELECT CONCAT('USUNIETO UZYTKOWNIKA O ID ' , OLD.user_id ,' IMIENIU ',OLD.f_name,' NAZWISKU ' ,OLD.l_name , ' Z EMAILEM : ' , OLD.email));

SET @ID = (SELECT max(logs.log_id)+1 from logs);

IF @ID IS NULL THEN 
SET @ID=1;
END IF;

INSERT INTO logs values(@ID,@text,CURRENT_DATE());

END
$$
DELIMITER ;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indeksy dla tabeli `ban`
--
ALTER TABLE `ban`
  ADD PRIMARY KEY (`ban_id`),
  ADD KEY `user_fk` (`user_id`),
  ADD KEY `admin_fk` (`admin_id`);

--
-- Indeksy dla tabeli `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`city_id`);

--
-- Indeksy dla tabeli `commentary`
--
ALTER TABLE `commentary`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `user_comment_fk` (`user_id`),
  ADD KEY `object_comment_fk` (`object_id`);

--
-- Indeksy dla tabeli `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`log_id`);

--
-- Indeksy dla tabeli `object`
--
ALTER TABLE `object`
  ADD PRIMARY KEY (`object_id`),
  ADD KEY `city_fk` (`city_id`);

--
-- Indeksy dla tabeli `photo`
--
ALTER TABLE `photo`
  ADD PRIMARY KEY (`photo_id`),
  ADD KEY `object_fk` (`object_id`);

--
-- Indeksy dla tabeli `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`route_id`),
  ADD KEY `object_1_fk` (`object_1_id`),
  ADD KEY `object_2_fk` (`object_2_id`),
  ADD KEY `city_route_fk` (`city_id`);

--
-- Indeksy dla tabeli `sysuser`
--
ALTER TABLE `sysuser`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `logs`
--
ALTER TABLE `logs`
  MODIFY `log_id` int(255) NOT NULL AUTO_INCREMENT;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `ban`
--
ALTER TABLE `ban`
  ADD CONSTRAINT `admin_fk` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`),
  ADD CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `sysuser` (`user_id`);

--
-- Ograniczenia dla tabeli `commentary`
--
ALTER TABLE `commentary`
  ADD CONSTRAINT `object_comment_fk` FOREIGN KEY (`object_id`) REFERENCES `object` (`object_id`),
  ADD CONSTRAINT `user_comment_fk` FOREIGN KEY (`user_id`) REFERENCES `sysuser` (`user_id`);

--
-- Ograniczenia dla tabeli `object`
--
ALTER TABLE `object`
  ADD CONSTRAINT `city_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`);

--
-- Ograniczenia dla tabeli `photo`
--
ALTER TABLE `photo`
  ADD CONSTRAINT `object_fk` FOREIGN KEY (`object_id`) REFERENCES `object` (`object_id`);

--
-- Ograniczenia dla tabeli `route`
--
ALTER TABLE `route`
  ADD CONSTRAINT `city_route_fk` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`),
  ADD CONSTRAINT `object_1_fk` FOREIGN KEY (`object_1_id`) REFERENCES `object` (`object_id`),
  ADD CONSTRAINT `object_2_fk` FOREIGN KEY (`object_2_id`) REFERENCES `object` (`object_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
