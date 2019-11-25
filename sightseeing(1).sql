-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 16 Lis 2019, 21:16
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
CREATE DATABASE IF NOT EXISTS `sightseeing` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `sightseeing`;

DELIMITER $$
--
-- Procedury
--
DROP PROCEDURE IF EXISTS `AddBannedUser`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddBannedUser` (IN `id_user` INT, IN `adminID` INT, IN `banType` ENUM('comment ban','perma ban'), IN `howManyDays` INT)  BEGIN

SET @banID = (SELECT max(ban.ban_id) from ban);

IF @banID IS NULL THEN
SET @banID=1;
END IF;

SET @EndBanData = (select DATE_ADD(CURDATE(),INTERVAL howManyDays DAY));

INSERT INTO ban(ban_id,user_id,admin_id,ban_type,date_since,date_by) values(@banID,id_user,adminID,banType,CURDATE(), @EndBanData);

END$$

DROP PROCEDURE IF EXISTS `ADDING_COMMENTARY`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ADDING_COMMENTARY` (IN `nc_user_id` INT(50), IN `nc_object_id` INT(10), IN `nc_contents` TEXT, IN `nc_rate` ENUM('1','2','3','4','5'))  BEGIN
	SET  @comment_id = (SELECT MAX(comment_id) FROM commentary);
	IF @comment_id IS NULL THEN SET @comment_id = 0;
    	ELSE SET @comment_id = @comment_id + 1;
   	END IF;

	SET @comment_date = (SELECT CURRENT_DATE() );
	INSERT INTO commentary(comment_id, user_id, object_id, contents, comment_date, rate) 
	VALUES (@comment_id, nc_user_id, nc_object_id, nc_contents, @comment_date, nc_rate);
 

END$$

DROP PROCEDURE IF EXISTS `add_route`$$
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

DROP PROCEDURE IF EXISTS `add_user`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_user` (`f_name` VARCHAR(20), `l_name` VARCHAR(20), `avatar_path` VARCHAR(30), `email` VARCHAR(20), `passwd` VARCHAR(20), `login` VARCHAR(20))  BEGIN
	
    SET @user_id = (SELECT MAX(sysuser.user_id) FROM sysuser);
    
    IF @user_id IS NULL THEN SET @user_id = 0;
    ELSE SET @user_id = @user_id + 1;
    END IF;
    
    
    
	INSERT INTO sysuser(user_id, f_name, l_name, login, passwd, email, avatar_path, comment_num) 
    VALUES (@user_id, f_name, l_name, login, passwd, email, avatar_path, 0);
    
END$$

DROP PROCEDURE IF EXISTS `del_user_by_id`$$
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

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `admin_id` int(10) NOT NULL AUTO_INCREMENT,
  `f_name` varchar(20) NOT NULL,
  `l_name` varchar(20) NOT NULL,
  `login` varchar(20) NOT NULL,
  `passwd` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `avatar_path` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Tabela Truncate przed wstawieniem `admin`
--

TRUNCATE TABLE `admin`;
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

DROP TABLE IF EXISTS `ban`;
CREATE TABLE IF NOT EXISTS `ban` (
  `ban_id` int(50) NOT NULL,
  `user_id` int(50) NOT NULL,
  `admin_id` int(10) NOT NULL,
  `ban_type` enum('comment ban','perma ban') DEFAULT NULL,
  `date_since` date NOT NULL,
  `date_by` date NOT NULL,
  PRIMARY KEY (`ban_id`),
  KEY `user_fk` (`user_id`),
  KEY `admin_fk` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabela Truncate przed wstawieniem `ban`
--

TRUNCATE TABLE `ban`;
--
-- Zrzut danych tabeli `ban`
--

INSERT INTO `ban` (`ban_id`, `user_id`, `admin_id`, `ban_type`, `date_since`, `date_by`) VALUES
(1, 1, 1, 'comment ban', '2019-11-16', '2019-11-26');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `city`
--

DROP TABLE IF EXISTS `city`;
CREATE TABLE IF NOT EXISTS `city` (
  `city_id` int(5) NOT NULL,
  `city_name` varchar(20) NOT NULL,
  `obj_quan` int(10) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabela Truncate przed wstawieniem `city`
--

TRUNCATE TABLE `city`;
--
-- Zrzut danych tabeli `city`
--

INSERT INTO `city` (`city_id`, `city_name`, `obj_quan`) VALUES
(1, 'Warszawa', 10),
(2, 'Kraków', 5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `commentary`
--

DROP TABLE IF EXISTS `commentary`;
CREATE TABLE IF NOT EXISTS `commentary` (
  `comment_id` int(100) NOT NULL,
  `user_id` int(50) NOT NULL,
  `object_id` int(10) NOT NULL,
  `contents` text NOT NULL,
  `comment_date` date NOT NULL,
  `rate` enum('1','2','3','4','5') DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `user_comment_fk` (`user_id`),
  KEY `object_comment_fk` (`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabela Truncate przed wstawieniem `commentary`
--

TRUNCATE TABLE `commentary`;
-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `logs`
--

DROP TABLE IF EXISTS `logs`;
CREATE TABLE IF NOT EXISTS `logs` (
  `log_id` int(255) NOT NULL AUTO_INCREMENT,
  `log_text` text NOT NULL,
  `log_date` date NOT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabela Truncate przed wstawieniem `logs`
--

TRUNCATE TABLE `logs`;
-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `object`
--

DROP TABLE IF EXISTS `object`;
CREATE TABLE IF NOT EXISTS `object` (
  `object_id` int(10) NOT NULL,
  `localization` varchar(30) NOT NULL,
  `object_name` varchar(20) NOT NULL,
  `city_id` int(5) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`object_id`),
  KEY `city_fk` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabela Truncate przed wstawieniem `object`
--

TRUNCATE TABLE `object`;
--
-- Zrzut danych tabeli `object`
--

INSERT INTO `object` (`object_id`, `localization`, `object_name`, `city_id`, `decription`) VALUES
(1, 'dupa', 'zamek', 1, 'sadrhahdh'),
(2, 'dupa', 'zamek2', 1, 'sadrhahdh');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `photo`
--

DROP TABLE IF EXISTS `photo`;
CREATE TABLE IF NOT EXISTS `photo` (
  `photo_id` int(10) NOT NULL,
  `object_id` int(10) NOT NULL,
  `photo_path` varchar(30) NOT NULL,
  PRIMARY KEY (`photo_id`),
  KEY `object_fk` (`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabela Truncate przed wstawieniem `photo`
--

TRUNCATE TABLE `photo`;
-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `route`
--

DROP TABLE IF EXISTS `route`;
CREATE TABLE IF NOT EXISTS `route` (
  `route_id` int(50) NOT NULL,
  `object_1_id` int(10) NOT NULL,
  `object_2_id` int(10) NOT NULL,
  `distance` float(5,3) NOT NULL,
  `city_id` int(5) NOT NULL,
  PRIMARY KEY (`route_id`),
  KEY `object_1_fk` (`object_1_id`),
  KEY `object_2_fk` (`object_2_id`),
  KEY `city_route_fk` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabela Truncate przed wstawieniem `route`
--

TRUNCATE TABLE `route`;
--
-- Zrzut danych tabeli `route`
--

INSERT INTO `route` (`route_id`, `object_1_id`, `object_2_id`, `distance`, `city_id`) VALUES
(1, 1, 2, 99.999, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sysuser`
--

DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE IF NOT EXISTS `sysuser` (
  `user_id` int(50) NOT NULL,
  `f_name` varchar(20) NOT NULL,
  `l_name` varchar(20) NOT NULL,
  `login` varchar(20) NOT NULL,
  `passwd` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `avatar_path` varchar(30) DEFAULT NULL,
  `comment_num` int(10) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE sysuser MODIFY user_id INTEGER NOT NULL AUTO_INCREMENT;

--
-- Tabela Truncate przed wstawieniem `sysuser`
--

TRUNCATE TABLE `sysuser`;
--
-- Zrzut danych tabeli `sysuser`
--

INSERT INTO `sysuser` (`user_id`, `f_name`, `l_name`, `login`, `passwd`, `email`, `avatar_path`, `comment_num`) VALUES
(1, 'Tomek', 'NAziwksoTomka', 'TomekLogin', 'psw', 'TomekEmail', 'TOmekAwatar', 0),
(2, 'Adam', 'NAziwksAdam', 'AdamLogin', 'psw', 'AdamEmail', 'AdamAwatar', 0),
(3, 'Kamil', 'NAziwksoKamil', 'KamilLogin', 'psw', 'KamilEmail', 'KamilkAwatar', 0);

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
