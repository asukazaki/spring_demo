-- phpMyAdmin SQL Dump
-- version 4.7.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: 2019 年 2 月 04 日 06:28
-- サーバのバージョン： 5.6.35
-- PHP Version: 7.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `demo`
--

-- --------------------------------------------------------

--
-- テーブルの構造 `jobs`
--

CREATE TABLE IF NOT EXISTS `jobs` (
  `id` int(50) NOT NULL,
  `date` varchar(10) NOT NULL,
  `version` timestamp(5) NOT NULL DEFAULT CURRENT_TIMESTAMP(5),
  `start_time` varchar(15) DEFAULT NULL,
  `end_time` varchar(15) DEFAULT NULL,
  `rest_start_time` varchar(15) DEFAULT NULL,
  `rest_end_time` varchar(15) DEFAULT NULL,
  `job_state_code` int(2) DEFAULT '0',
  `job_state_name` varchar(15) DEFAULT NULL,
  `tmp_shift_start_time` varchar(10) DEFAULT NULL,
  `approval_falg` int(2) DEFAULT '0',
  PRIMARY KEY (`id`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `jobs`
--

INSERT INTO `jobs` (`id`, `date`, `version`, `start_time`, `end_time`, `rest_start_time`, `rest_end_time`, `job_state_code`, `job_state_name`, `tmp_shift_start_time`, `approval_falg`) VALUES
(1, '2018/06/01', '2019-02-04 03:28:51.15439', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/04', '2019-02-04 03:28:51.15439', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/05', '2019-02-04 03:28:51.15439', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/06', '2019-02-04 03:28:51.15439', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '', NULL, 1),
(1, '2018/06/07', '2019-02-04 03:28:51.15439', '10:00:00', '20:00:00', '12:00:00', '13:00:00', -1, 'LATE', NULL, 0),
(1, '2018/06/08', '2019-02-04 03:28:51.15439', '12:00:00', '18:00:00', '', '', -1, 'LATE', NULL, 0),
(1, '2018/06/11', '2019-02-04 03:28:51.15439', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/12', '2019-02-04 03:28:51.15439', '09:00:00', '20:00:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/13', '2019-02-04 03:28:51.15439', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/14', '2019-02-04 03:28:51.15439', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/15', '2019-02-04 03:28:51.15439', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/17', '2019-02-04 03:28:51.15439', '09:00:00', '20:00:00', '12:00:00', '13:00:00', 0, NULL, NULL, 1),
(1, '2018/06/18', '2019-02-04 03:28:51.15439', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/19', '2019-02-04 03:28:51.15439', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/20', '2019-02-04 03:28:51.15439', '09:00:00', '18:30:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/21', '2019-02-04 03:28:51.15439', '09:00:00', '19:30:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/22', '2019-02-04 03:28:51.15439', '09:00:00', '19:30:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/25', '2019-02-04 03:28:51.15439', '09:00:00', '20:00:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/26', '2019-02-04 03:28:51.15439', '09:00:00', '21:30:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/27', '2019-02-04 03:28:51.15439', '09:00:00', '22:00:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/28', '2019-02-04 03:28:51.15439', '09:00:00', '21:30:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/06/29', '2019-02-04 03:28:51.15439', '09:00:00', '21:30:00', '12:00:00', '13:00:00', 0, '', NULL, 0),
(1, '2018/11/04', '2019-02-04 03:28:51.15439', '22:49:32', NULL, NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/05', '2019-02-04 03:28:51.15439', '17:14:33', '17:14:44', NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/06', '2019-02-04 03:28:51.15439', '11:41:33', '16:58:33', NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/12', '2019-02-04 03:28:51.15439', NULL, '18:57:24', NULL, NULL, -3, 'BRANK', NULL, 0),
(1, '2018/11/13', '2019-02-04 03:28:51.15439', '18:55:07', '18:55:05', NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/14', '2019-02-04 03:28:51.15439', '16:39:56', NULL, NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/16', '2019-02-04 03:28:51.15439', '17:15:58', '17:24:35', NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/17', '2019-02-04 03:28:51.15439', '13:46:17', NULL, NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/18', '2019-02-04 03:28:51.15439', '16:59:18', NULL, NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/19', '2019-02-04 03:28:51.15439', '14:13:19', NULL, NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/20', '2019-02-04 03:28:51.15439', '17:31:46', '00:20:04', NULL, NULL, -1, 'LATE', NULL, 0),
(1, '2018/11/21', '2019-02-04 03:28:51.15439', '19:18:17', '18:47:38', NULL, NULL, -1, 'LATE', NULL, 0),
(2, '2018/06/04', '2019-02-04 03:28:51.15439', '09:00:00', '21:30:00', '12:00:00', '13:00:00', 0, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- テーブルの構造 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `shift_start_time` varchar(10) NOT NULL,
  `shift_end_time` varchar(10) NOT NULL,
  `job_shift_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7bjb5n8uequ75iyoahvfetwwx` (`job_shift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `user`
--

INSERT INTO `user` (`id`, `user_name`, `shift_start_time`, `shift_end_time`, `job_shift_id`) VALUES
(1, 'ITI太郎', '09:00:00', '17:30:00', NULL),
(2, 'ITI二郎', '10:00:00', '18:30:00', NULL),
(3, 'ITI三郎', '', '', NULL),
(4, 'ITItest', '', '', NULL);

--
-- ダンプしたテーブルの制約
--

--
-- テーブルの制約 `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK7bjb5n8uequ75iyoahvfetwwx` FOREIGN KEY (`job_shift_id`) REFERENCES `job_shift` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
