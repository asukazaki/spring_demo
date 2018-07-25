-- phpMyAdmin SQL Dump
-- version 4.7.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: 2018 年 7 月 25 日 14:02
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
-- テーブルの構造 `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(3);

-- --------------------------------------------------------

--
-- テーブルの構造 `holiday`
--

CREATE TABLE `holiday` (
  `id` int(5) NOT NULL,
  `month` varchar(2) NOT NULL,
  `day` varchar(2) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `holiday`
--

INSERT INTO `holiday` (`id`, `month`, `day`, `name`) VALUES
(1, '06', '06', '特別休暇'),
(2, '07', '16', '海の日');

-- --------------------------------------------------------

--
-- テーブルの構造 `jobs`
--

CREATE TABLE `jobs` (
  `id` int(50) NOT NULL,
  `date` varchar(10) NOT NULL,
  `start_time` varchar(15) DEFAULT NULL,
  `end_time` varchar(15) DEFAULT NULL,
  `rest_start_time` varchar(15) DEFAULT NULL,
  `rest_end_time` varchar(15) DEFAULT NULL,
  `job_state_code` int(2) DEFAULT '0',
  `job_state_name` varchar(15) DEFAULT NULL,
  `approval_falg` int(2) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `jobs`
--

INSERT INTO `jobs` (`id`, `date`, `start_time`, `end_time`, `rest_start_time`, `rest_end_time`, `job_state_code`, `job_state_name`, `approval_falg`) VALUES
(1, '2018/06/01', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '出勤', 0),
(1, '2018/06/04', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '出勤', 0),
(1, '2018/06/05', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '出勤', 0),
(1, '2018/06/06', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '出勤', 0),
(1, '2018/06/07', '10:00:00', '20:00:00', '12:00:00', '13:00:00', 0, '出勤', 0),
(1, '2018/06/08', '12:00:00', '18:00:00', '', '', 0, '半休', 0),
(1, '2018/06/11', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 0, '出勤', 0),
(1, '2018/06/27', NULL, NULL, NULL, NULL, 0, NULL, 0),
(1, '2018/06/28', NULL, NULL, NULL, NULL, 0, NULL, 0);

-- --------------------------------------------------------

--
-- テーブルの構造 `job_shift`
--

CREATE TABLE `job_shift` (
  `id` int(10) NOT NULL,
  `shift_start_time` varchar(10) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `job_shift`
--

INSERT INTO `job_shift` (`id`, `shift_start_time`, `user_id`) VALUES
(1, '09:00:00', NULL),
(2, '10:00:00', NULL);

-- --------------------------------------------------------

--
-- テーブルの構造 `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `job_shift_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `user`
--

INSERT INTO `user` (`id`, `user_name`, `job_shift_id`) VALUES
(1, 'ITI太郎', NULL),
(2, 'ITI二郎', NULL),
(3, 'ITI三郎', NULL),
(4, 'ITItest', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `holiday`
--
ALTER TABLE `holiday`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobs`
--
ALTER TABLE `jobs`
  ADD PRIMARY KEY (`id`,`date`);

--
-- Indexes for table `job_shift`
--
ALTER TABLE `job_shift`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgd5j3uo99ki87rpwxglh3jfb8` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7bjb5n8uequ75iyoahvfetwwx` (`job_shift_id`);

--
-- ダンプしたテーブルの制約
--

--
-- テーブルの制約 `job_shift`
--
ALTER TABLE `job_shift`
  ADD CONSTRAINT `FKgd5j3uo99ki87rpwxglh3jfb8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- テーブルの制約 `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK7bjb5n8uequ75iyoahvfetwwx` FOREIGN KEY (`job_shift_id`) REFERENCES `job_shift` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
