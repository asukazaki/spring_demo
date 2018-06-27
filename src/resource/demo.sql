-- phpMyAdmin SQL Dump
-- version 4.7.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: 2018 年 6 月 27 日 15:10
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

CREATE TABLE `jobs` (
  `id` int(50) NOT NULL,
  `date` varchar(10) NOT NULL,
  `start_time` varchar(15) DEFAULT NULL,
  `end_time` varchar(15) DEFAULT NULL,
  `rest_start_time` varchar(15) DEFAULT NULL,
  `rest_end_time` varchar(15) DEFAULT NULL,
  `job_state_code` int(10) DEFAULT NULL,
  `job_state_name` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `jobs`
--

INSERT INTO `jobs` (`id`, `date`, `start_time`, `end_time`, `rest_start_time`, `rest_end_time`, `job_state_code`, `job_state_name`) VALUES
(1, '2018/06/01', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 1, '出勤'),
(1, '2018/06/04', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 1, '出勤'),
(1, '2018/06/05', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 1, '出勤'),
(1, '2018/06/06', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 1, '出勤'),
(1, '2018/06/07', '10:00:00', '20:00:00', '12:00:00', '13:00:00', 1, '出勤'),
(1, '2018/06/08', '12:00:00', '18:00:00', '', '', 2, '半休'),
(1, '2018/06/11', '09:00:00', '18:00:00', '12:00:00', '13:00:00', 1, '出勤'),
(1, '2018/06/27', NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- テーブルの構造 `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- テーブルのデータのダンプ `user`
--

INSERT INTO `user` (`id`, `user_name`) VALUES
(1, 'ITI太郎'),
(2, 'ITI二郎'),
(3, 'ITI三郎'),
(4, 'ITItest');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jobs`
--
ALTER TABLE `jobs`
  ADD PRIMARY KEY (`id`,`date`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
