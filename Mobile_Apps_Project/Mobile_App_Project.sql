-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 20, 2022 at 05:17 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Mobile_App_Project`
--

-- --------------------------------------------------------

--
-- Table structure for table `parking`
--

CREATE TABLE `parking` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `current_count` int(11) NOT NULL DEFAULT 0,
  `total_count` int(11) NOT NULL DEFAULT 0,
  `status` varchar(15) NOT NULL,
  `poll_count` double DEFAULT NULL,
  `poll_total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `parking`
--

INSERT INTO `parking` (`id`, `name`, `current_count`, `total_count`, `status`, `poll_count`, `poll_total`) VALUES
(1, 'Pepsi Gate', 100, 200, 'Halfway Filled', 0, 0),
(2, 'Gardens', 1, 200, 'Empty', 0, 0),
(3, 'Bus Gate', 6, 100, 'Empty', 0, 0),
(4, 'Omar Mohsen', 100, 100, 'No Parking', 0, 0),
(5, 'Watson', 12, 100, 'Almost Empty', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE `user_info` (
  `student_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `phone_num` varchar(11) NOT NULL,
  `points` int(11) NOT NULL,
  `parking_space_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`student_id`, `name`, `email`, `phone_num`, `points`, `parking_space_id`) VALUES
(900162227, 'Feras Awaga', 'feras@aucegypt.edu', '01068899306', 759, NULL),
(900172333, 'Seif Eldin Hani', 'seifhani2016@aucegypt.edu', '01140140105', 900, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

CREATE TABLE `vendor` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `vendor`
--

INSERT INTO `vendor` (`id`, `name`) VALUES
(1, 'Tarwee2a'),
(2, 'Tabali'),
(3, 'Cilantro'),
(4, 'Subway');

-- --------------------------------------------------------

--
-- Table structure for table `vendor_offers`
--

CREATE TABLE `vendor_offers` (
  `vendor_id` int(11) NOT NULL,
  `offer_description` varchar(50) NOT NULL,
  `offer_id` int(11) NOT NULL,
  `points` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `vendor_offers`
--

INSERT INTO `vendor_offers` (`vendor_id`, `offer_description`, `offer_id`, `points`) VALUES
(1, 'Get 50% off', 1, 50),
(2, 'Get 30% off', 2, 30),
(3, 'Get 40% off', 3, 100),
(4, 'Get 20% off', 4, 30);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `parking`
--
ALTER TABLE `parking`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `parking_space_id` (`parking_space_id`);

--
-- Indexes for table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendor_offers`
--
ALTER TABLE `vendor_offers`
  ADD PRIMARY KEY (`offer_id`),
  ADD KEY `vendor_id` (`vendor_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `parking`
--
ALTER TABLE `parking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `vendor`
--
ALTER TABLE `vendor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `vendor_offers`
--
ALTER TABLE `vendor_offers`
  MODIFY `offer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_info`
--
ALTER TABLE `user_info`
  ADD CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`parking_space_id`) REFERENCES `parking` (`id`);

--
-- Constraints for table `vendor_offers`
--
ALTER TABLE `vendor_offers`
  ADD CONSTRAINT `vendor_offers_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
