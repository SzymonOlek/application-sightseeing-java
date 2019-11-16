-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 16 Lis 2019, 16:58
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

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ban`
--

CREATE TABLE `ban` (
  `ban_id` int(50) NOT NULL,
  `user_id` int(50) NOT NULL,
  `admin_id` int(10) NOT NULL,
  `ban_type` enum('comment ban','perma ban') DEFAULT NULL,
  `date_since` date NOT NULL,
  `date_by` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `city`
--

CREATE TABLE `city` (
  `city_id` int(5) NOT NULL,
  `city_name` varchar(20) NOT NULL,
  `obj_quan` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `rate` enum('1','2','3','4','5') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `decription` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `comment_num` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  MODIFY `admin_id` int(10) NOT NULL AUTO_INCREMENT;

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
