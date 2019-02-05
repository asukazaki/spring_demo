-- phpMyAdmin SQL Dump
-- version 4.7.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: 2019 年 2 月 05 日 07:31
-- サーバのバージョン： 5.6.35
-- PHP Version: 7.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jobkan`
--

-- --------------------------------------------------------

--
-- テーブルの構造 `jobs`
--

CREATE TABLE IF NOT EXISTS `jobs` (
  `id` int(50) NOT NULL,
  `date` date NOT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `rest_start_time` time DEFAULT NULL,
  `rest_end_time` time DEFAULT NULL,
  `job_state_code` int(2) DEFAULT '0',
  `job_state_name` varchar(10) DEFAULT NULL,
  `tmp_shift_start_time` time DEFAULT NULL,
  `approval_flag` int(2) DEFAULT NULL,
  `version` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `jobs`
--

INSERT INTO `jobs` (`id`, `date`, `start_time`, `end_time`, `rest_start_time`, `rest_end_time`, `job_state_code`, `job_state_name`, `tmp_shift_start_time`, `approval_flag`, `version`) VALUES
(1, '2018-11-12', '13:00:00', '19:00:00', '00:00:00', '00:00:00', -1, 'LATE', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-13', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-14', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-15', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-16', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-19', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-20', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-21', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-22', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-23', '09:00:00', '19:00:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-27', '09:00:00', '19:00:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-28', '09:00:00', '19:00:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-11-29', '09:00:00', '19:00:00', '12:00:00', '13:00:00', 0, 'NORMAL', '00:00:00', 0, '2019-02-04 18:41:04'),
(1, '2018-12-10', '13:54:05', NULL, NULL, NULL, -1, 'LATE', NULL, 0, '2019-02-04 18:41:04'),
(1, '2019-01-04', '09:00:00', '18:30:00', NULL, NULL, 0, 'NORMAL', NULL, 0, '2019-02-05 12:40:25'),
(1, '2019-01-07', '09:00:00', '18:30:00', NULL, NULL, 0, 'NORMAL', NULL, 0, '2019-02-05 11:30:23'),
(1, '2019-01-08', '08:20:00', '19:00:00', NULL, NULL, 0, 'NORMAL', NULL, 0, '2019-02-05 12:03:28'),
(1, '2019-01-09', '09:00:00', '18:00:00', NULL, NULL, 0, 'NORMAL', NULL, 0, '2019-02-05 12:04:00'),
(1, '2019-01-10', '09:00:00', '19:00:00', NULL, NULL, 0, 'NORMAL', NULL, 0, '2019-02-05 12:16:40'),
(1, '2019-01-11', '09:00:00', '18:30:00', NULL, NULL, 0, 'NORMAL', NULL, 0, '2019-02-05 12:16:28'),
(1, '2019-01-15', '12:00:00', NULL, NULL, NULL, -1, 'LATE', NULL, 0, '2019-02-05 12:40:54'),
(1, '2019-01-17', '09:00:00', '19:00:00', NULL, NULL, 0, 'NORMAL', NULL, 0, '2019-02-05 12:40:54'),
(1, '2019-02-04', '18:41:03', NULL, NULL, NULL, -1, 'LATE', NULL, 0, '2019-02-04 18:41:04');

-- --------------------------------------------------------

--
-- テーブルの構造 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `shift_start_time` time NOT NULL,
  `shift_end_time` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `user`
--

INSERT INTO `user` (`id`, `user_name`, `shift_start_time`, `shift_end_time`) VALUES
(1, 'ITI太郎', '09:00:00', '17:30:00'),
(2, 'ITI二郎', '10:00:00', '18:30:00'),
(3, 'ITI三郎', '08:00:00', '16:30:00');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
