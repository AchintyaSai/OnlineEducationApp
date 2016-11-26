-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 26, 2016 at 07:18 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `information`
--

-- --------------------------------------------------------

--
-- Table structure for table `acc_details`
--

CREATE TABLE IF NOT EXISTS `acc_details` (
  `usr_id` varchar(10) NOT NULL,
  `sub_enr` varchar(15) NOT NULL,
  `test1` int(30) NOT NULL DEFAULT '-1',
  `test2` int(30) NOT NULL DEFAULT '-1',
  `totalques` int(100) NOT NULL,
  PRIMARY KEY (`usr_id`,`sub_enr`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `acc_details`
--

INSERT INTO `acc_details` (`usr_id`, `sub_enr`, `test1`, `test2`, `totalques`) VALUES
('13691A0555', 'dbms', -1, -1, 5),
('13691A0555', 'java', -1, -1, 0),
('13691A0555', 'SE', -1, -1, 5);

-- --------------------------------------------------------

--
-- Table structure for table `achintya`
--

CREATE TABLE IF NOT EXISTS `achintya` (
  `id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `csvtbl`
--

CREATE TABLE IF NOT EXISTS `csvtbl` (
  `ID` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `csvtbl`
--

INSERT INTO `csvtbl` (`ID`, `name`, `city`) VALUES
('3', '13691A0503', 'aravindh'),
('2', '13691A0502', 'abid'),
('3', '13691A0503', 'aravindh'),
('2', '13691A0502', 'abid'),
('3', '13691A0503', 'aravindh');

-- --------------------------------------------------------

--
-- Table structure for table `dbms`
--

CREATE TABLE IF NOT EXISTS `dbms` (
  `qno` int(5) NOT NULL AUTO_INCREMENT,
  `question` varchar(1000) NOT NULL,
  `ch1` varchar(200) NOT NULL,
  `ch2` varchar(200) NOT NULL,
  `ch3` varchar(200) NOT NULL,
  `ch4` varchar(200) NOT NULL,
  `right` varchar(200) NOT NULL,
  UNIQUE KEY `qno` (`qno`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `dbms`
--

INSERT INTO `dbms` (`qno`, `question`, `ch1`, `ch2`, `ch3`, `ch4`, `right`) VALUES
(1, 'what is this?', 'this', 'that', 'those', 'them', 'this'),
(2, 'who r u?', 'i', 'she', 'it', 'they', 'it'),
(3, 'what is the difference between you and me', 'q', 'w', 'e', 'r', 'q'),
(4, 'what is the difference between micro and macro economics', 'r', 'w', 'd', 'f', 'f'),
(5, 'what is your name', 'achintya', 'suhail', 'jeethu', 'dinesh', 'achintya');

-- --------------------------------------------------------

--
-- Table structure for table `java`
--

CREATE TABLE IF NOT EXISTS `java` (
  `qno` int(10) NOT NULL,
  `question` varchar(100) NOT NULL,
  `ch1` varchar(10) NOT NULL,
  `ch2` varchar(10) NOT NULL,
  `ch3` varchar(10) NOT NULL,
  `ch4` varchar(10) NOT NULL,
  `ch5` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lecturers_info`
--

CREATE TABLE IF NOT EXISTS `lecturers_info` (
  `lec_name` varchar(30) NOT NULL,
  `lec_id` varchar(15) NOT NULL,
  `passwd` varchar(30) NOT NULL,
  `sub_handling` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lecturers_info`
--

INSERT INTO `lecturers_info` (`lec_name`, `lec_id`, `passwd`, `sub_handling`) VALUES
('Farooq', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `se`
--

CREATE TABLE IF NOT EXISTS `se` (
  `qno` int(5) NOT NULL AUTO_INCREMENT,
  `question` varchar(1000) NOT NULL,
  `ch1` varchar(200) NOT NULL,
  `ch2` varchar(200) NOT NULL,
  `ch3` varchar(200) NOT NULL,
  `ch4` varchar(200) NOT NULL,
  `right` varchar(200) NOT NULL,
  UNIQUE KEY `qno` (`qno`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `se`
--

INSERT INTO `se` (`qno`, `question`, `ch1`, `ch2`, `ch3`, `ch4`, `right`) VALUES
(1, 'what is this?', 'this', 'that', 'those', 'them', 'this'),
(2, 'who r u?', 'i', 'she', 'it', 'they', 'it'),
(3, 'what is the difference between you and me', 'q', 'w', 'e', 'r', 'q'),
(4, 'what is the difference between micro and macro economics', 'r', 'w', 'd', 'f', 'f'),
(5, 'what is your name', 'achintya', 'suhail', 'jeethu', 'dinesh', 'achintya');

--
-- Triggers `se`
--
DROP TRIGGER IF EXISTS `delete`;
DELIMITER //
CREATE TRIGGER `delete` AFTER DELETE ON `se`
 FOR EACH ROW update acc_details set totalques=(select count(*) from se) where sub_enr='se'
//
DELIMITER ;
DROP TRIGGER IF EXISTS `se`;
DELIMITER //
CREATE TRIGGER `se` AFTER INSERT ON `se`
 FOR EACH ROW update acc_details set totalques=(select count(*) from se) where sub_enr='se'
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE IF NOT EXISTS `subjects` (
  `sub_name` varchar(15) NOT NULL,
  `sub_id` varchar(20) NOT NULL,
  `person_handling` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`sub_name`, `sub_id`, `person_handling`, `email`) VALUES
('dbms', '13A01', 'farooq', 'farooq@mits.ac.in'),
('SE', '13A02', 'Rajeshwari', 'rajeshwari@gmail.com'),
('java', '13A03', 'shabeer', 'shabeer@gmail.com'),
('ds', '1324', 'sudhakar', 'sudha@gmail.com'),
('es', '1567', 'eeswari', 'esu@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `test_conducting`
--

CREATE TABLE IF NOT EXISTS `test_conducting` (
  `lec_id` varchar(30) NOT NULL,
  `sub_id` varchar(30) NOT NULL,
  `topic` varchar(30) NOT NULL,
  `marks_con` int(10) NOT NULL,
  `pass_marks` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `test_conducting`
--

INSERT INTO `test_conducting` (`lec_id`, `sub_id`, `topic`, `marks_con`, `pass_marks`) VALUES
('', '', 'se', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `userinfo`
--

CREATE TABLE IF NOT EXISTS `userinfo` (
  `usr_name` varchar(30) NOT NULL,
  `usr_id` varchar(10) NOT NULL,
  `email` text NOT NULL,
  `passwd` varchar(30) NOT NULL,
  PRIMARY KEY (`usr_name`),
  UNIQUE KEY `usr_id` (`usr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='created for users information';

--
-- Dumping data for table `userinfo`
--

INSERT INTO `userinfo` (`usr_name`, `usr_id`, `email`, `passwd`) VALUES
('achintya', 'whxjdie', 'amazingahdhie', 'achintya'),
('suhail', '13691A0555', 'suhail@gmail.com', 'suhail'),
('test', '1234578', 'jyothibellary@gmail.com', 'test');

--
-- Triggers `userinfo`
--
DROP TRIGGER IF EXISTS `delete userinfo`;
DELIMITER //
CREATE TRIGGER `delete userinfo` BEFORE DELETE ON `userinfo`
 FOR EACH ROW DELETE FROM acc_details
WHERE usr_id=acc_details.usr_id
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `videos`
--

CREATE TABLE IF NOT EXISTS `videos` (
  `sub_name` varchar(100) NOT NULL,
  `topic` varchar(100) NOT NULL,
  `video` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `videos1`
--

CREATE TABLE IF NOT EXISTS `videos1` (
  `sub_name` varchar(30) NOT NULL,
  `topic` varchar(100) NOT NULL,
  `video_title` varchar(100) NOT NULL,
  `author` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `videos1`
--

INSERT INTO `videos1` (`sub_name`, `topic`, `video_title`, `author`) VALUES
('dbms', 'intro', 'intro', 'achi'),
('dbms', 'formulae', 'formulae', 'achi'),
('SE', 'introduction', 'first', 'achintya'),
('SE', 'second', 'second', 'suhail');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
