-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 08, 2018 at 04:24 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `android`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrators`
--

CREATE TABLE `administrators` (
  `Admin_ID` int(11) NOT NULL,
  `Name_of_the_Admin` varchar(200) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `last_logged_in` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `prev_last_logged_in` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Cred_Mail_Sent` int(11) NOT NULL DEFAULT '0',
  `rid` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `archive_colleges`
--

CREATE TABLE `archive_colleges` (
  `archive_id` int(11) NOT NULL,
  `College_Name` varchar(900) NOT NULL,
  `Location` varchar(900) NOT NULL,
  `approved` int(11) NOT NULL,
  `deleted_by` varchar(100) NOT NULL,
  `deleted_on` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `archive_companies`
--

CREATE TABLE `archive_companies` (
  `archive_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `Company_Name` varchar(200) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `approved` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `archive_fileuploads`
--

CREATE TABLE `archive_fileuploads` (
  `archive_id` int(11) NOT NULL,
  `uploader_id` int(11) NOT NULL,
  `uploader_type` varchar(40) NOT NULL,
  `uploaded_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_by` varchar(100) NOT NULL,
  `deleter_type` varchar(40) NOT NULL,
  `deleted_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `file_name` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `archive_messages`
--

CREATE TABLE `archive_messages` (
  `archive_id` int(11) NOT NULL,
  `Title` varchar(900) NOT NULL,
  `Text` varchar(9000) NOT NULL,
  `Sent_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Sent_to` varchar(100) NOT NULL,
  `Sent_by` varchar(100) NOT NULL,
  `Sender_Type` varchar(90) NOT NULL,
  `Receiver_Type` varchar(90) NOT NULL,
  `Seen` int(11) NOT NULL,
  `deleted_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_by` varchar(100) NOT NULL,
  `deleter_type` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `archive_notices`
--

CREATE TABLE `archive_notices` (
  `archive_id` int(11) NOT NULL,
  `Title` varchar(900) NOT NULL,
  `Text` varchar(9000) NOT NULL,
  `Added_by` varchar(100) NOT NULL,
  `Added_on` datetime NOT NULL,
  `For_Course` int(11) NOT NULL,
  `Adder_Type` varchar(40) NOT NULL,
  `College_ID` int(11) NOT NULL,
  `deleted_by` varchar(108) NOT NULL,
  `deleter_type` varchar(40) NOT NULL,
  `deleted_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `archive_placements`
--

CREATE TABLE `archive_placements` (
  `archive_id` int(11) NOT NULL,
  `Company_Name` varchar(900) NOT NULL,
  `Date_of_Selection` date NOT NULL,
  `For_Course` int(11) NOT NULL,
  `Maximum_Backlogs` int(11) NOT NULL,
  `Vacancies_for` varchar(1000) NOT NULL,
  `Criteria_for_Selection` varchar(1000) NOT NULL,
  `Added_by` varchar(100) NOT NULL,
  `Added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `College_ID` int(11) NOT NULL,
  `Interview_Venue` varchar(900) NOT NULL,
  `Additional_Information` varchar(1000) NOT NULL,
  `For_Company_Account` int(11) NOT NULL,
  `approved` int(11) NOT NULL,
  `closed` int(11) NOT NULL,
  `cancelled` int(11) NOT NULL,
  `selection_done` int(11) NOT NULL,
  `approved_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `adder_type` varchar(40) NOT NULL,
  `approved_by` varchar(100) NOT NULL,
  `approver_type` varchar(40) NOT NULL,
  `deleted_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_by` varchar(100) NOT NULL,
  `deleter_type` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `archive_positions`
--

CREATE TABLE `archive_positions` (
  `archive_id` int(11) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  `position` varchar(40) DEFAULT NULL,
  `deleted_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `approved_status` int(11) NOT NULL,
  `deleted_by` varchar(100) NOT NULL,
  `deleter_type` varchar(40) NOT NULL,
  `college` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `archive_students`
--

CREATE TABLE `archive_students` (
  `archive_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `Name_of_the_Student` varchar(200) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Course` varchar(500) NOT NULL,
  `Roll_No` varchar(50) NOT NULL,
  `registered_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `College` varchar(500) NOT NULL,
  `deleted_on` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `colleges`
--

CREATE TABLE `colleges` (
  `College_ID` int(11) NOT NULL,
  `College_Name` varchar(900) NOT NULL,
  `Location` varchar(900) NOT NULL,
  `approved` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `colleges`
--

INSERT INTO `colleges` (`College_ID`, `College_Name`, `Location`, `approved`) VALUES
(54, 'SIES College of Arts,Science and Commerce', 'Sion West', 1),
(300, 'GNVS Institute of Management', 'Sion East', 1);

-- --------------------------------------------------------

--
-- Table structure for table `companies_accounts`
--

CREATE TABLE `companies_accounts` (
  `Company_ID` int(11) NOT NULL,
  `Company_Name` varchar(200) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `rid` varchar(100) NOT NULL,
  `approved` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `companies_placements`
--

CREATE TABLE `companies_placements` (
  `Company_Name` varchar(200) NOT NULL,
  `Date_of_Selection` date NOT NULL,
  `For_Course` int(11) NOT NULL,
  `Maximum_Backlogs` int(11) NOT NULL,
  `Vacancies_for` varchar(1000) NOT NULL,
  `Criteria_for_Selection` varchar(1000) NOT NULL,
  `Table_Name` varchar(90) NOT NULL,
  `Added_by` varchar(100) NOT NULL,
  `Added_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `College_ID` int(11) NOT NULL,
  `Interview_Venue` varchar(900) DEFAULT NULL,
  `Additional_Information` varchar(1000) NOT NULL,
  `For_Company_Account` int(11) NOT NULL,
  `approved` int(11) NOT NULL,
  `closed` int(11) NOT NULL DEFAULT '0',
  `cancelled` int(11) NOT NULL DEFAULT '0',
  `selection_done` int(11) DEFAULT '0',
  `id` int(11) NOT NULL,
  `approved_on` datetime DEFAULT '0000-00-00 00:00:00',
  `adder_type` varchar(40) NOT NULL,
  `approved_by` varchar(100) DEFAULT NULL,
  `approver_type` varchar(40) DEFAULT NULL,
  `seen` int(11) DEFAULT '0',
  `min_percentage` int(11) NOT NULL DEFAULT '45',
  `min_gpa_percentage` int(11) NOT NULL DEFAULT '45',
  `reason_cancel` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `ID` int(11) NOT NULL,
  `Courses` varchar(90) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`ID`, `Courses`) VALUES
(1, 'Bachelor in Hotel Management'),
(2, 'Bachelor of Architecture'),
(3, 'Bachelor of Architecture Interior Design'),
(4, 'Bachelor of Arts & Bachelor of Law'),
(5, 'Bachelor of Arts (General)'),
(6, 'Bachelor of Arts Acient Indian Culture'),
(7, 'Bachelor of Arts Arabic (Honors)'),
(8, 'Bachelor of Arts Bengali (Honors)'),
(9, 'Bachelor of Arts Business Economics (Honors)'),
(10, 'Bachelor of Arts Economics'),
(11, 'Bachelor of Arts Economics (Honors)'),
(12, 'Bachelor of Arts Education'),
(13, 'Bachelor of Arts English'),
(14, 'Bachelor of Arts English (Honors)'),
(15, 'Bachelor of Arts Folklore'),
(16, 'Bachelor of Arts Geography'),
(17, 'Bachelor of Arts Geography (Honors)'),
(18, 'Bachelor of Arts Gujarati'),
(19, 'Bachelor of Arts Hindi'),
(20, 'Bachelor of Arts Hindi (Honors)'),
(21, 'Bachelor of Arts Hindi Patrakarita Evam Jansanchar (Honors)'),
(22, 'Bachelor of Arts History'),
(23, 'Bachelor of Arts History (Honors)'),
(24, 'Bachelor of Arts Home Science (Honors)'),
(25, 'Bachelor of Arts International Hospitality Administration'),
(26, 'Bachelor of Arts Journalism'),
(27, 'Bachelor of Arts Marathi'),
(28, 'Bachelor of Arts Mathematics (Honors)'),
(29, 'Bachelor of Arts Music (Honors)'),
(30, 'Bachelor of Arts Persian (Honors)'),
(31, 'Bachelor of Arts Philosophy'),
(32, 'Bachelor of Arts Philosophy (Honors)'),
(33, 'Bachelor of Arts Political Science (Honors)'),
(34, 'Bachelor of Arts Politics'),
(35, 'Bachelor of Arts Prayojan Mulak Hindi'),
(36, 'Bachelor of Arts Psychology'),
(37, 'Bachelor of Arts Psychology (Honors)'),
(38, 'Bachelor of Arts Public Service'),
(39, 'Bachelor of Arts Punjabi (Honors)'),
(40, 'Bachelor of Arts Sanskrit'),
(41, 'Bachelor of Arts Sanskrit (Honors)'),
(42, 'Bachelor of Arts Social Work (Honors)'),
(43, 'Bachelor of Arts Sociology'),
(44, 'Bachelor of Arts Sociology (Honors)'),
(45, 'Bachelor of Arts Tamil'),
(46, 'Bachelor of Arts Urdu (Honors)'),
(47, 'Bachelor of Arts Vocational Studies'),
(48, 'Bachelor of Arts Vyavaharik and Upyojit Marathi'),
(49, 'Bachelor of Audiology and Speech Language Pathology'),
(50, 'Bachelor of Ayurvedic Medicine & Surgery'),
(51, 'Bachelor of Business Administration (Foreign Trade)'),
(52, 'Bachelor of Business Administration (Hospitality Managment)'),
(53, 'Bachelor of Business Administration (Hospitality Managment/Tourist & Travel Management)'),
(54, 'Bachelor of Business Administration (Information Systems Management)'),
(55, 'Bachelor of Business Administration (Information Technology Management)'),
(56, 'Bachelor of Business Administration (Shipping)'),
(57, 'Bachelor of Business Administration Hotel & Tourism Management'),
(58, 'Bachelor of Business Administration Insurance & Banking'),
(59, 'Bachelor of Business Adminsitration'),
(60, 'Bachelor of Business Management'),
(61, 'Bachelor of Business Studies'),
(62, 'Bachelor of Commerce (General)'),
(63, 'Bachelor of Commerce (Honors)'),
(64, 'Bachelor of Commerce (Professional)'),
(65, 'Bachelor of Commerce Accountancy'),
(66, 'Bachelor of Commerce Accounting and Finance'),
(67, 'Bachelor of Commerce Banking & Insurance'),
(68, 'Bachelor of Commerce Banking and Finance'),
(69, 'Bachelor of Commerce Business Economics'),
(70, 'Bachelor of Commerce Corporate Law'),
(71, 'Bachelor of Commerce Economics'),
(72, 'Bachelor of Commerce Environmental Science'),
(73, 'Bachelor of Commerce Financial Markets'),
(74, 'Bachelor of Commerce Vocational'),
(75, 'Bachelor of Computer Application'),
(76, 'Bachelor of Dental Surgery'),
(77, 'Bachelor of Education'),
(78, 'Bachelor of Education (English)'),
(79, 'Bachelor of Education Hearing Impairment'),
(80, 'Bachelor of Education Vacational'),
(81, 'Bachelor of Elementary Education'),
(82, 'Bachelor of Engieering Petroleum Engineering and Offshore Engineering'),
(83, 'Bachelor of Engineering Aeronautical Engineering'),
(84, 'Bachelor of Engineering Automobile Engineering'),
(85, 'Bachelor of Engineering Biomedical Engineering'),
(86, 'Bachelor of Engineering Biotechnology'),
(87, 'Bachelor of Engineering Chemical Engineering'),
(88, 'Bachelor of Engineering Civil Engineering'),
(89, 'Bachelor of Engineering Computer Engineering'),
(90, 'Bachelor of Engineering Electrical & Electronics Engineering'),
(91, 'Bachelor of Engineering Electrical & Electronics Engineering (Marine)'),
(92, 'Bachelor of Engineering Electrical Engineering'),
(93, 'Bachelor of Engineering Electronics & Instrumentation Engineering'),
(94, 'Bachelor of Engineering Electronics and Telecommunication Engineering'),
(95, 'Bachelor of Engineering Electronics Engineering'),
(96, 'Bachelor of Engineering Harbour & Ocean Engineering'),
(97, 'Bachelor of Engineering Humanities and Applied Sciences'),
(98, 'Bachelor of Engineering Industrial Engineering'),
(99, 'Bachelor of Engineering Information Technology'),
(100, 'Bachelor of Engineering Instrumentation & Control Engineering'),
(101, 'Bachelor of Engineering Instrumentation Engineering'),
(102, 'Bachelor of Engineering Manufacturing Processes & Automation Engineering'),
(103, 'Bachelor of Engineering Marine Engineering'),
(104, 'Bachelor of Engineering Mechanical Engineering'),
(105, 'Bachelor of Engineering Naval Architecture and Offshore Engineering'),
(106, 'Bachelor of Engineering Production Engineering'),
(107, 'Bachelor of Financial & Investment Analysis'),
(108, 'Bachelor of Fine Art (Applied Art)'),
(109, 'Bachelor of Fine Art (Art History)'),
(110, 'Bachelor of Fine Art (General)'),
(111, 'Bachelor of Fine Art (Painting)'),
(112, 'Bachelor of Fine Art (Print Making)'),
(113, 'Bachelor of Fine Art (Sculpture)'),
(114, 'Bachelor of Fine Art (Visual Communication)'),
(115, 'Bachelor of Home Science'),
(116, 'Bachelor of Home Science Apparel Production & Management'),
(117, 'Bachelor of Home Science Journalism & Mass Communication'),
(118, 'Bachelor of Home Science Organization & Management of Early Childhood Programmes'),
(119, 'Bachelor of Homoeopathic Medicine & Surgery'),
(120, 'Bachelor of Journalism & Mass Communication (Honors)'),
(121, 'Bachelor of Law'),
(122, 'Bachelor of Law & Master of Law'),
(123, 'Bachelor of Law (General)'),
(124, 'Bachelor of Law (Special))'),
(125, 'Bachelor of Legal Science and Bachelor of Law'),
(126, 'Bachelor of Library Science'),
(127, 'Bachelor of Management Studies'),
(128, 'Bachelor of Management Studies (Honors)'),
(129, 'Bachelor of Mass Media'),
(130, 'Bachelor of Medicine & Bachelor of Surgery'),
(131, 'Bachelor of Optometry'),
(132, 'Bachelor of Performing Arts (Dance)'),
(133, 'Bachelor of Performing Arts (Music)'),
(134, 'Bachelor of Pharmacy'),
(135, 'Bachelor of Physical Education'),
(136, 'Bachelor of Physiotherapy'),
(137, 'Bachelor of Science (Agriculture Biotechnology)'),
(138, 'Bachelor of Science (General)'),
(139, 'Bachelor of Science (Medical Technology) Radiology'),
(140, 'Bachelor of Science Agriculture'),
(141, 'Bachelor of Science Agrilcultural Marketing & Business Management'),
(142, 'Bachelor of Science Anthropology (Honors)'),
(143, 'Bachelor of Science Aviation'),
(144, 'Bachelor of Science Biochemistry'),
(145, 'Bachelor of Science Biochemistry (Honors)'),
(146, 'Bachelor of Science Biological Sciences (Honors)'),
(147, 'Bachelor of Science Biomedical Sciences (Honors)'),
(148, 'Bachelor of Science Biotechnology'),
(149, 'Bachelor of Science Botany'),
(150, 'Bachelor of Science Botany (Honors)'),
(151, 'Bachelor of Science Chemistry'),
(152, 'Bachelor of Science Chemistry (Honors)'),
(153, 'Bachelor of Science Commercial Agriculture & Business Management'),
(154, 'Bachelor of Science Computer Science'),
(155, 'Bachelor of Science Enviormental Sciences'),
(156, 'Bachelor of Science Fashion & Apparel Designing'),
(157, 'Bachelor of Science Fashion Design'),
(158, 'Bachelor of Science Fashion Technology'),
(159, 'Bachelor of Science Fisheries'),
(160, 'Bachelor of Science Food Technology'),
(161, 'Bachelor of Science Forensic Science'),
(162, 'Bachelor of Science Forestry'),
(163, 'Bachelor of Science Genetics'),
(164, 'Bachelor of Science Geology (Honors)'),
(165, 'Bachelor of Science Home Science'),
(166, 'Bachelor of Science Home Science Interior Design'),
(167, 'Bachelor of Science Home Science Nutrition & Dietetics'),
(168, 'Bachelor of Science Horticulture'),
(169, 'Bachelor of Science Hotel Management and Catering Technology'),
(170, 'Bachelor of Science Hotel Management and Tourism'),
(171, 'Bachelor of Science Information Technology'),
(172, 'Bachelor of Science Interior Design'),
(173, 'Bachelor of Science Life Science'),
(174, 'Bachelor of Science Mathematics'),
(175, 'Bachelor of Science Mathematics (Honors)'),
(176, 'Bachelor of Science Medical Laboratory Technology'),
(177, 'Bachelor of Science Medical Technology'),
(178, 'Bachelor of Science Microbiology'),
(179, 'Bachelor of Science Microbiology (Honors)'),
(180, 'Bachelor of Science Nautical Science'),
(181, 'Bachelor of Science Nursing (Honors)'),
(182, 'Bachelor of Science Nursing (Post Basic)'),
(183, 'Bachelor of Science Nursing (Post Basic) (Distance Education)'),
(184, 'Bachelor of Science Occupational Therapy'),
(185, 'Bachelor of Science Physcial Sciences (Honors)'),
(186, 'Bachelor of Science Physical Education, Health Education and Sports (Hons)'),
(187, 'Bachelor of Science Physics'),
(188, 'Bachelor of Science Physics (Honors)'),
(189, 'Bachelor of Science Plant Biotechnology'),
(190, 'Bachelor of Science Statistics'),
(191, 'Bachelor of Science Statistics (Honors)'),
(192, 'Bachelor of Science Visual Communication'),
(193, 'Bachelor of Science Zoology'),
(194, 'Bachelor of Science Zoology (Honors)'),
(195, 'Bachelor of Social Work'),
(196, 'Bachelor of Socio Legal Sciences'),
(197, 'Bachelor of Technology (Food Science)'),
(198, 'Bachelor of Technology Agriculture Engineering'),
(199, 'Bachelor of Technology Chemical Engineering'),
(200, 'Bachelor of Technology Civil Engineering'),
(201, 'Bachelor of Technology Computer Science'),
(202, 'Bachelor of Technology Computer Science and Engineering'),
(203, 'Bachelor of Technology Computer Science Engineering'),
(204, 'Bachelor of Technology Electrical and Electronics Engineering'),
(205, 'Bachelor of Technology Electronics'),
(206, 'Bachelor of Technology Electronics & Communication Engineering'),
(207, 'Bachelor of Technology Food Technology'),
(208, 'Bachelor of Technology Industrial Biotechnology'),
(209, 'Bachelor of Technology Information & Communication Technology'),
(210, 'Bachelor of Technology Information Technology'),
(211, 'Bachelor of Technology Instrumentation'),
(212, 'Bachelor of Technology Manufacturing Technology'),
(213, 'Bachelor of Technology Marine Engineering'),
(214, 'Bachelor of Technology Mechanical Engineering'),
(215, 'Bachelor of Technology Petroleum Engineering'),
(216, 'Bachelor of Technology PlasticsTechnology'),
(217, 'Bachelor of Technology Polymer Science'),
(218, 'Bachelor of Technology Psychological Science'),
(219, 'Bachelor of Unani Medicine and Surgery'),
(220, 'Bachelor of Veterinary Science and Animal Husbandry'),
(221, 'Bachelor of Visual Arts (Applied Art)'),
(222, 'Bachelor of Visual Arts (Painting)'),
(223, 'Bachelor of Visual Arts (Sculpture)'),
(224, 'Entrepreneurial Master of Business Administration'),
(225, 'Integrated Bachelor of Arts and Bachelor of Law'),
(226, 'Integrated Master of Business Administration'),
(227, 'Integrated Master of Philosophy and Doctor of Philosophy English'),
(228, 'Integrated Master of Philosophy and Doctor of Philosophy Management'),
(229, 'Integrated Master of Tourism Administration'),
(230, 'Integrated Masters in Life Science'),
(231, 'Master of Agricultural Business Management'),
(232, 'Master of Architecture (By Research)'),
(233, 'Master of Architecture (General)'),
(234, 'Master of Architecture Architectural and Urban Conservation'),
(235, 'Master of Architecture Construction Management'),
(236, 'Master of Architecture Environmental Architecture'),
(237, 'Master of Architecture Real Estate Development'),
(238, 'Master of Architecture Urban and Regional Planning'),
(239, 'Master of Architecture Urban Design'),
(240, 'Master of Arts (Diplomacy, Law & Business)'),
(241, 'Master of Arts Ancient History & Archaeology'),
(242, 'Master of Arts Arabic'),
(243, 'Master of Arts Communication & Media Studies'),
(244, 'Master of Arts Communication and Journalism'),
(245, 'Master of Arts Convergent Journalism'),
(246, 'Master of Arts Disability Communication and Deaf Studies'),
(247, 'Master of Arts Economics'),
(248, 'Master of Arts Education'),
(249, 'Master of Arts English'),
(250, 'Master of Arts English (English Language Training)'),
(251, 'Master of Arts Geography'),
(252, 'Master of Arts Gujarati'),
(253, 'Master of Arts Hindi'),
(254, 'Master of Arts History'),
(255, 'Master of Arts Mahayana Buddhist Studies'),
(256, 'Master of Arts Mathematics'),
(257, 'Master of Arts Music'),
(258, 'Master of Arts Persian'),
(259, 'Master of Arts Philosophy'),
(260, 'Master of Arts Political Science'),
(261, 'Master of Arts Psychology'),
(262, 'Master of Arts Public Administration'),
(263, 'Master of Arts Public Policy'),
(264, 'Master of Arts Public Relations'),
(265, 'Master of Arts Punjabi'),
(266, 'Master of Arts Rural Development'),
(267, 'Master of Arts Sanskrit'),
(268, 'Master of Arts Sociology'),
(269, 'Master of Arts Telugu'),
(270, 'Master of Arts Tourism Management'),
(271, 'Master of Arts Urdu'),
(272, 'Master of Business Administration'),
(273, 'Master of Business Administration (Business Economics)'),
(274, 'Master of Business Administration Hospital Administration'),
(275, 'Master of Business Administration Hospitality Management'),
(276, 'Master of Business Administration Human Resource Management'),
(277, 'Master of Business Administration Insurance & Banking'),
(278, 'Master of Business Administration International Business'),
(279, 'Master of Business Administration Tourism and Travel Management'),
(280, 'Master of Business Administration Tourism Management'),
(281, 'Master of Business Application Agriculture Management'),
(282, 'Master of Business Management (Marine Human Resource Management)'),
(283, 'Master of Business Management (Shipping & Logistics Management)'),
(284, 'Master of Business Management (Shipping Finance)'),
(285, 'Master of Commerce (Accounting & Financial Management)'),
(286, 'Master of Commerce (General)'),
(287, 'Master of Commerce (Human Resource Management)'),
(288, 'Master of Commerce (International Banking & Accounting)'),
(289, 'Master of Commerce Accontancy'),
(290, 'Master of Commerce Accounting and Auditing'),
(291, 'Master of Commerce Advance Accounting'),
(292, 'Master of Commerce Banking and Finance'),
(293, 'Master of Commerce Business Finance'),
(294, 'Master of Commerce Industrial Economics'),
(295, 'Master of Commerce Management'),
(296, 'Master of Computer Application'),
(297, 'Master of Education'),
(298, 'Master of Education Hearing Impairment'),
(299, 'Master of Engineering Communication Systems'),
(300, 'Master of Engineering Computer Aided Design / Computer Aided Machining'),
(301, 'Master of Engineering Computer Engineering'),
(302, 'Master of Engineering Electrical Engineering'),
(303, 'Master of Engineering Electronics & Telecommunication'),
(304, 'Master of Engineering Electronics Engineering'),
(305, 'Master of Engineering Information Technology (Information Security)'),
(306, 'Master of Engineering Instrumentation & Control'),
(307, 'Master of Engineering Mechanical Engineering'),
(308, 'Master of Engineering Mechanical Engineering (CAD / CAM)'),
(309, 'Master of Engineering Mechanical Engineering (Energy Engineering)'),
(310, 'Master of Engineering Power Electronics & Drives'),
(311, 'Master of Engineering VLSI Design'),
(312, 'Master of Financial Management'),
(313, 'Master of Fine Art Applied Art'),
(314, 'Master of Fine Art Painting'),
(315, 'Master of Fine Art Print Making'),
(316, 'Master of Fine Art Sclupture'),
(317, 'Master of Fine Art Visual Communication'),
(318, 'Master of Fine Arts Illustration'),
(319, 'Master of Fine Arts Typography'),
(320, 'Master of Human Resource Development'),
(321, 'Master of Human Resource Management'),
(322, 'Master of Information Management'),
(323, 'Master of Labour Welfare'),
(324, 'Master of Laws'),
(325, 'Master of Library Science'),
(326, 'Master of Management Studies'),
(327, 'Master of Marketing Management'),
(328, 'Master of Occupational Therapy'),
(329, 'Master of Optometry'),
(330, 'Master of Performing Arts (Dance)'),
(331, 'Master of Pharmacy'),
(332, 'Master of Pharmacy  Pharmaceutical  Analysis'),
(333, 'Master of Pharmacy Clinical Research'),
(334, 'Master of Pharmacy Herbal Drug Technology'),
(335, 'Master of Pharmacy Hospital Pharmacy'),
(336, 'Master of Pharmacy Pharmaceutical Chemistry'),
(337, 'Master of Pharmacy Pharmaceutical Management'),
(338, 'Master of Pharmacy Pharmaceutics'),
(339, 'Master of Pharmacy Pharmacognosy'),
(340, 'Master of Pharmacy Pharmacology'),
(341, 'Master of Pharmacy Quality Assurance'),
(342, 'Master of Philosophy (Natural Resource Mangement)'),
(343, 'Master of Philosophy Biomedical Sciences'),
(344, 'Master of Philosophy Marine Biotechnology'),
(345, 'Master of Philosophy Marine Microbiology'),
(346, 'Master of Philosophy Nursing'),
(347, 'Master of Physical Education'),
(348, 'Master of Physiotherapy'),
(349, 'Master of Science (Horticulture)'),
(350, 'Master of Science (Medical Imaging Technology) Radiology'),
(351, 'Master of Science (Post Harvest Management)'),
(352, 'Master of Science Agriculture'),
(353, 'Master of Science Analytical Chemistry'),
(354, 'Master of Science Analytical Chemistry (Research)'),
(355, 'Master of Science Anatomy'),
(356, 'Master of Science and Doctor of Philosophy Biomedical Sciences'),
(357, 'Master of Science Anthropology'),
(358, 'Master of Science Applied Genetics'),
(359, 'Master of Science Applied Mathematics'),
(360, 'Master of Science Aquaculture'),
(361, 'Master of Science Biochemistry'),
(362, 'Master of Science Biochemistry'),
(363, 'Master of Science Bioinformatics'),
(364, 'Master of Science Biomedical Sciences'),
(365, 'Master of Science Biotechnology'),
(366, 'Master of Science Botany'),
(367, 'Master of Science Chemistry'),
(368, 'Master of Science Chemistry (Research)'),
(369, 'Master of Science Computer Science'),
(370, 'Master of Science Defence Science'),
(371, 'Master of Science Earth Science'),
(372, 'Master of Science Electronics'),
(373, 'Master of Science Electronics & Communication'),
(374, 'Master of Science Electronics & Instrumentation Technology'),
(375, 'Master of Science Environment Science Technology'),
(376, 'Master of Science Environmental Sciences'),
(377, 'Master of Science Fabric & Apparel Science'),
(378, 'Master of Science Fisheries'),
(379, 'Master of Science Foods & Nutrition'),
(380, 'Master of Science Forensic Science'),
(381, 'Master of Science Forestry'),
(382, 'Master of Science Genetics'),
(383, 'Master of Science Geology'),
(384, 'Master of Science Home Science'),
(385, 'Master of Science Home Science (Family Resource Management)'),
(386, 'Master of Science Home Science (Food Biotechnology)'),
(387, 'Master of Science Home Science (Food Nutrition and Dietetics)'),
(388, 'Master of Science Home Science (Food Processing and Preservation)'),
(389, 'Master of Science Home Science (Human Development)'),
(390, 'Master of Science Home Science (Sports Nutrition)'),
(391, 'Master of Science Home Science (Textile & Clothing)'),
(392, 'Master of Science Home Science (Textile and Fashion Technology)'),
(393, 'Master of Science Hotel & Tourism Studies'),
(394, 'Master of Science Industrial BioTechnology'),
(395, 'Master of Science Industrial Polymer Chemistry'),
(396, 'Master of Science Informatics'),
(397, 'Master of Science Information Technology'),
(398, 'Master of Science Inorganic Chemistry'),
(399, 'Master of Science Inorganic Chemistry (Research)'),
(400, 'Master of Science Instrumentation'),
(401, 'Master of Science Marine Biotechnology'),
(402, 'Master of Science Material Science & Technology'),
(403, 'Master of Science Mathematics'),
(404, 'Master of Science Medical Laboratory Technology'),
(405, 'Master of Science Microbiology'),
(406, 'Master of Science Microbiology'),
(407, 'Master of Science Microbiology (Research)'),
(408, 'Master of Science Nano-Biotechnology'),
(409, 'Master of Science Nano-Technology'),
(410, 'Master of Science NanoScience & NanoTechnology'),
(411, 'Master of Science Nursing'),
(412, 'Master of Science Oceanography Fisheries and Aquaculture'),
(413, 'Master of Science Oils, Fats & Waxes'),
(414, 'Master of Science Operational Research'),
(415, 'Master of Science Organic Chemistry'),
(416, 'Master of Science Organic Chemistry (Research)'),
(417, 'Master of Science Pharmaceutical Chemistry'),
(418, 'Master of Science Pharmacology'),
(419, 'Master of Science Physical Chemistry'),
(420, 'Master of Science Physics'),
(421, 'Master of Science Physics (Electronics)'),
(422, 'Master of Science Physics (Material Science)'),
(423, 'Master of Science Physiology'),
(424, 'Master of Science Polymer Science & Technology'),
(425, 'Master of Science Renewable Energy (Energy Management)'),
(426, 'Master of Science Renewable Energy (Environment Modeling)'),
(427, 'Master of Science Renewable Energy (System Technology)'),
(428, 'Master of Science Statistics'),
(429, 'Master of Science Statistics (Quality & Productivity Management)'),
(430, 'Master of Science Statistics (Quality Reliability & Operations Research)'),
(431, 'Master of Science Surface Coating Technology'),
(432, 'Master of Science Taxonomy'),
(433, 'Master of Science Zoology'),
(434, 'Master of Science Zoology (Research)'),
(435, 'Master of Social Work'),
(436, 'Master of Surgery Anatomy'),
(437, 'Master of Surgery Ear Nose & Throat'),
(438, 'Master of Surgery General Surgery'),
(439, 'Master of Surgery Ophthalmology'),
(440, 'Master of Surgery Orthopaedics'),
(441, 'Master of Surgery Otorhinolaryngology'),
(442, 'Master of Surgery Surgery'),
(443, 'Master of Technology Agricultural Process & Food Engineering'),
(444, 'Master of Technology Agriculture Engineering'),
(445, 'Master of Technology Biotechnology'),
(446, 'Master of Technology Computer Engineering (Information Systems)'),
(447, 'Master of Technology Computer Science & Technology'),
(448, 'Master of Technology Electronics & Communication Engineering'),
(449, 'Master of Technology Electronics & Communication Engineering ( Signal Processing)'),
(450, 'Master of Technology Energy Management'),
(451, 'Master of Technology in Farm Machinery & Power'),
(452, 'Master of Technology Information & Communication Technology'),
(453, 'Master of Technology Instrumentation & Control Engineering (Process Control)'),
(454, 'Master of Technology Manufacturing & Automation'),
(455, 'Master of Technology Marine Engineering Management'),
(456, 'Master of Technology Petroleum Technology'),
(457, 'Master of Technology Plastics Technology'),
(458, 'Master of Technology Soil & Water Engineering'),
(459, 'Master of Tourism Administration'),
(460, 'Master of Tourism and Travel Management'),
(461, 'Master of Urban Planning'),
(462, 'Master of Veterinary Science'),
(463, 'Masters in Heritage Management'),
(464, 'Masters in Life Sciences');

-- --------------------------------------------------------

--
-- Table structure for table `discussion_threads`
--

CREATE TABLE `discussion_threads` (
  `Thread_ID` int(11) NOT NULL,
  `Thread_Text` varchar(900) NOT NULL,
  `Threader_ID` int(11) NOT NULL,
  `Threader_Type` varchar(40) DEFAULT NULL,
  `Sent_on` datetime NOT NULL,
  `Replying_to` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `dt_likes`
--

CREATE TABLE `dt_likes` (
  `Liker_ID` int(11) DEFAULT NULL,
  `Liker_Type` varchar(40) DEFAULT NULL,
  `Liked_Thread` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `file_uploads`
--

CREATE TABLE `file_uploads` (
  `ID` int(11) NOT NULL,
  `file_name` varchar(500) NOT NULL,
  `uploader_id` int(11) NOT NULL,
  `uploader_type` varchar(40) NOT NULL,
  `uploaded_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `link_generated` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `hsc_details`
--

CREATE TABLE `hsc_details` (
  `Username` varchar(100) NOT NULL,
  `Percentage` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `invitations`
--

CREATE TABLE `invitations` (
  `Company_Table_Name` varchar(90) NOT NULL,
  `To_Student_ID` int(11) NOT NULL,
  `From_Company_ID` int(11) NOT NULL,
  `approved` int(11) NOT NULL,
  `accepted` int(11) NOT NULL,
  `College_ID` int(11) NOT NULL,
  `ID` int(11) NOT NULL,
  `reason_decline` varchar(1000) DEFAULT NULL,
  `invited_on` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `Message_ID` int(11) NOT NULL,
  `Message_Title` varchar(900) NOT NULL,
  `Message_Text` varchar(9000) NOT NULL,
  `Sent_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Sent_to` varchar(100) NOT NULL,
  `Sent_by` varchar(100) NOT NULL,
  `Sender_Type` varchar(90) NOT NULL,
  `Receiver_Type` varchar(90) NOT NULL,
  `Seen` int(11) DEFAULT '0',
  `Deleted_R` int(11) DEFAULT '0',
  `Deleted_S` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `moderators`
--

CREATE TABLE `moderators` (
  `Moderator_ID` int(11) NOT NULL,
  `Name_of_the_Moderator` varchar(200) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `last_logged_in` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `prev_last_logged_in` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `College_ID` int(11) NOT NULL,
  `Cred_Mail_Sent` int(11) NOT NULL DEFAULT '0',
  `approved` int(11) NOT NULL DEFAULT '0',
  `rid` varchar(100) NOT NULL,
  `promoted` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `msc_sem_1_details`
--

CREATE TABLE `msc_sem_1_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `msc_sem_2_details`
--

CREATE TABLE `msc_sem_2_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `msc_sem_3_details`
--

CREATE TABLE `msc_sem_3_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `msc_sem_4_details`
--

CREATE TABLE `msc_sem_4_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `notices`
--

CREATE TABLE `notices` (
  `Notice_ID` int(11) NOT NULL,
  `Notice_Title` varchar(900) NOT NULL,
  `Notice_Text` varchar(9000) NOT NULL,
  `Added_by` varchar(100) NOT NULL,
  `Added_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `For_Course` int(11) NOT NULL,
  `Adder_Type` varchar(40) NOT NULL,
  `College_ID` int(11) NOT NULL,
  `Adder_Name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `personal_skills`
--

CREATE TABLE `personal_skills` (
  `ID` int(11) NOT NULL,
  `Skill_Name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `personal_skills`
--

INSERT INTO `personal_skills` (`ID`, `Skill_Name`) VALUES
(2, 'Adaptable'),
(3, 'Administrative'),
(4, 'Advising'),
(5, 'Analysis'),
(6, 'Analytical'),
(9, 'Being Thorough'),
(11, 'Business Storytelling'),
(12, 'Calculations'),
(13, 'Challenging Employees'),
(14, 'Classifying Records'),
(15, 'Coaching Individuals'),
(16, 'Collaboration'),
(17, 'Communication'),
(18, 'Compiling Statistics'),
(19, 'Computer'),
(20, 'Conducting Meetings'),
(21, 'Conflict Resolution'),
(23, 'Construction'),
(24, 'Consultation'),
(25, 'Counseling'),
(26, 'Creating Ideas'),
(27, 'Creating Innovation'),
(29, 'Creating New Procedures'),
(28, 'Creating New Solutions'),
(30, 'Creative Thinking'),
(31, 'Critical Thinking'),
(32, 'Customer Service'),
(33, 'Decision Making'),
(34, 'Defining Performance Standards'),
(35, 'Defining Problems'),
(36, 'Demonstrations'),
(37, 'Detail Management'),
(38, 'Dispensing Information'),
(39, 'Displaying Ideas'),
(42, 'Encouragement'),
(43, 'Entertainment'),
(44, 'Equipment Operation'),
(45, 'Evaluating'),
(46, 'Expression of Feelings'),
(47, 'Financial Report Auditing'),
(48, 'Fundraising'),
(49, 'Goal Setting'),
(50, 'Handling Complaints'),
(51, 'Human Resources'),
(52, 'Independent Action'),
(53, 'Information Search'),
(54, 'Innovation'),
(55, 'Interpersonal'),
(56, 'Interviews'),
(57, 'Inventing New Ideas'),
(58, 'Investigation'),
(59, 'Involvement'),
(60, 'Knowledge of Current Governmental Affairs'),
(61, 'Language Translation'),
(62, 'Leadership'),
(63, 'Learning'),
(65, 'Locating Missing Documents/Information'),
(66, 'Logical Thinking'),
(67, 'Maintaining High Levels of Activity'),
(68, 'Maintenance'),
(69, 'Management'),
(70, 'Managing Finances'),
(71, 'Measuring Boundaries'),
(72, 'Medical Assistance'),
(73, 'Meeting Deadlines'),
(74, 'Microsoft Office'),
(75, 'Monetary Collection'),
(76, 'Motivation'),
(77, 'Multitasking'),
(78, 'Negotiation'),
(79, 'Networking'),
(80, 'Nonverbal Communication'),
(81, 'Numerical Analysis'),
(82, 'Oration'),
(83, 'Organizational'),
(84, 'Organizational Management'),
(85, 'Organizational Tasks'),
(86, 'Overseeing Meetings'),
(87, 'Overseeing Operation'),
(88, 'Personal Interaction'),
(89, 'Plan Development'),
(90, 'Planning'),
(91, 'Prediction'),
(93, 'Principal Concept Knowledge'),
(94, 'Prioritizing'),
(95, 'Problem Solving'),
(96, 'Promotions'),
(98, 'Proposal Writing'),
(97, 'Proposals'),
(100, 'Public Relations'),
(101, 'Public Speaking'),
(99, 'Publications'),
(102, 'Questioning Others'),
(104, 'Reasoning'),
(105, 'Recommendations'),
(106, 'Regulating Rules'),
(107, 'Rehabilitating Others'),
(108, 'Remembering Facts'),
(110, 'Report Writing'),
(109, 'Reporting'),
(111, 'Responsibility'),
(113, 'Scheduling'),
(114, 'Screening Calls'),
(112, 'Service'),
(115, 'Sketching'),
(116, 'Supervision'),
(118, 'Team Building'),
(119, 'Teamwork'),
(120, 'Technical'),
(117, 'Technical Support'),
(121, 'Technology'),
(122, 'Time Management'),
(123, 'Toleration'),
(124, 'Training'),
(125, 'Transferable'),
(126, 'Updating Files');

-- --------------------------------------------------------

--
-- Table structure for table `selected_students`
--

CREATE TABLE `selected_students` (
  `Student_ID` int(11) NOT NULL,
  `Company_Placement_ID` int(11) NOT NULL,
  `mail_sent` int(11) NOT NULL DEFAULT '0',
  `msg_sent` int(11) NOT NULL DEFAULT '0',
  `extra_info_for_student` varchar(1500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sem_1_details`
--

CREATE TABLE `sem_1_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sem_2_details`
--

CREATE TABLE `sem_2_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sem_3_details`
--

CREATE TABLE `sem_3_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sem_4_details`
--

CREATE TABLE `sem_4_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sem_5_details`
--

CREATE TABLE `sem_5_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sem_6_details`
--

CREATE TABLE `sem_6_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sem_7_details`
--

CREATE TABLE `sem_7_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sem_8_details`
--

CREATE TABLE `sem_8_details` (
  `Username` varchar(100) NOT NULL,
  `GPA` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL,
  `Out_of_GPA` int(11) NOT NULL DEFAULT '7'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `ssc_details`
--

CREATE TABLE `ssc_details` (
  `Username` varchar(100) NOT NULL,
  `Percentage` float(4,2) DEFAULT NULL,
  `Month_of_Passing` varchar(20) DEFAULT NULL,
  `Year_of_Passing` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `Student_ID` int(11) NOT NULL,
  `Name_of_the_Student` varchar(200) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Course` int(11) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `registered_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_logged_in` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Student_Image` varchar(500) NOT NULL,
  `Roll_No` varchar(50) NOT NULL,
  `Student_CV` varchar(500) DEFAULT NULL,
  `Number_of_KT` int(11) DEFAULT NULL,
  `Contact_Number` bigint(20) DEFAULT NULL,
  `prev_last_logged_in` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `College_ID` int(11) NOT NULL,
  `Strengths` varchar(5000) DEFAULT NULL,
  `Weaknesses` varchar(5000) DEFAULT NULL,
  `Acheivements` varchar(5000) DEFAULT NULL,
  `Personal_Skills` varchar(400) DEFAULT NULL,
  `Technical_Skills` varchar(400) DEFAULT NULL,
  `rid` varchar(100) NOT NULL,
  `apply_count` int(11) NOT NULL DEFAULT '0',
  `success_count` int(11) NOT NULL DEFAULT '0',
  `m_token` varchar(50) DEFAULT NULL,
  `course_type` int(11) NOT NULL DEFAULT '0',
  `verification_code` bigint(20) NOT NULL DEFAULT '6598321476',
  `verified` int(11) NOT NULL DEFAULT '0',
  `delete_trigger` int(11) DEFAULT '0',
  `academic_year` varchar(20) NOT NULL,
  `final_year` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `technical_skills`
--

CREATE TABLE `technical_skills` (
  `ID` int(11) NOT NULL,
  `Skill_Name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `technical_skills`
--

INSERT INTO `technical_skills` (`ID`, `Skill_Name`) VALUES
(43, 'Adobe Illustrator'),
(6, 'Android'),
(28, 'Applescript'),
(12, 'ASP.NET'),
(14, 'Assembly'),
(44, 'C'),
(15, 'C#'),
(3, 'C++'),
(16, 'COBOL'),
(39, 'Content Creation'),
(35, 'Data Structures and Algorithms'),
(36, 'Excel'),
(8, 'HTML , CSS'),
(1, 'Java'),
(7, 'Javascript'),
(17, 'Kotlin'),
(18, 'LaTEX'),
(11, 'MySQL or other DBMS'),
(19, 'Node.js'),
(20, 'Objective-C'),
(9, 'Photoshop'),
(21, 'PHP'),
(5, 'PL/SQL'),
(37, 'Powerpoint'),
(27, 'Powershell'),
(2, 'Python'),
(10, 'R Programming'),
(22, 'Ruby'),
(23, 'Scala'),
(24, 'Swift'),
(29, 'VBScript'),
(42, 'VFX'),
(33, 'Video Editing'),
(25, 'Visual Basic'),
(26, 'Visual Basic .NET'),
(31, 'Windows Powershell'),
(38, 'Word');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrators`
--
ALTER TABLE `administrators`
  ADD PRIMARY KEY (`Admin_ID`),
  ADD UNIQUE KEY `unique_admins_username` (`Username`);

--
-- Indexes for table `archive_colleges`
--
ALTER TABLE `archive_colleges`
  ADD PRIMARY KEY (`archive_id`);

--
-- Indexes for table `archive_companies`
--
ALTER TABLE `archive_companies`
  ADD PRIMARY KEY (`archive_id`);

--
-- Indexes for table `archive_fileuploads`
--
ALTER TABLE `archive_fileuploads`
  ADD PRIMARY KEY (`archive_id`);

--
-- Indexes for table `archive_messages`
--
ALTER TABLE `archive_messages`
  ADD PRIMARY KEY (`archive_id`);

--
-- Indexes for table `archive_notices`
--
ALTER TABLE `archive_notices`
  ADD PRIMARY KEY (`archive_id`);

--
-- Indexes for table `archive_placements`
--
ALTER TABLE `archive_placements`
  ADD PRIMARY KEY (`archive_id`);

--
-- Indexes for table `archive_positions`
--
ALTER TABLE `archive_positions`
  ADD PRIMARY KEY (`archive_id`);

--
-- Indexes for table `archive_students`
--
ALTER TABLE `archive_students`
  ADD PRIMARY KEY (`archive_id`);

--
-- Indexes for table `colleges`
--
ALTER TABLE `colleges`
  ADD PRIMARY KEY (`College_ID`);

--
-- Indexes for table `companies_accounts`
--
ALTER TABLE `companies_accounts`
  ADD PRIMARY KEY (`Company_ID`),
  ADD UNIQUE KEY `uk_companiesaccount_username` (`Username`);

--
-- Indexes for table `companies_placements`
--
ALTER TABLE `companies_placements`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_table_name` (`Table_Name`),
  ADD KEY `fk_comapnies_course` (`For_Course`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `discussion_threads`
--
ALTER TABLE `discussion_threads`
  ADD PRIMARY KEY (`Thread_ID`);

--
-- Indexes for table `file_uploads`
--
ALTER TABLE `file_uploads`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `hsc_details`
--
ALTER TABLE `hsc_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `invitations`
--
ALTER TABLE `invitations`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`Message_ID`);

--
-- Indexes for table `moderators`
--
ALTER TABLE `moderators`
  ADD PRIMARY KEY (`Moderator_ID`),
  ADD UNIQUE KEY `unique_moderators_username` (`Username`);

--
-- Indexes for table `msc_sem_1_details`
--
ALTER TABLE `msc_sem_1_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `msc_sem_2_details`
--
ALTER TABLE `msc_sem_2_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `msc_sem_3_details`
--
ALTER TABLE `msc_sem_3_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `msc_sem_4_details`
--
ALTER TABLE `msc_sem_4_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `notices`
--
ALTER TABLE `notices`
  ADD PRIMARY KEY (`Notice_ID`),
  ADD KEY `fk_notices_collegeid` (`College_ID`);

--
-- Indexes for table `personal_skills`
--
ALTER TABLE `personal_skills`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Skill_Name` (`Skill_Name`);

--
-- Indexes for table `selected_students`
--
ALTER TABLE `selected_students`
  ADD KEY `Student_ID` (`Student_ID`),
  ADD KEY `Company_Placement_ID` (`Company_Placement_ID`);

--
-- Indexes for table `sem_1_details`
--
ALTER TABLE `sem_1_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `sem_2_details`
--
ALTER TABLE `sem_2_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `sem_3_details`
--
ALTER TABLE `sem_3_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `sem_4_details`
--
ALTER TABLE `sem_4_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `sem_5_details`
--
ALTER TABLE `sem_5_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `sem_6_details`
--
ALTER TABLE `sem_6_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `sem_7_details`
--
ALTER TABLE `sem_7_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `sem_8_details`
--
ALTER TABLE `sem_8_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `ssc_details`
--
ALTER TABLE `ssc_details`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`Student_ID`),
  ADD UNIQUE KEY `unique_students_username` (`Username`),
  ADD UNIQUE KEY `Username` (`Username`),
  ADD KEY `fk_students_course` (`Course`);

--
-- Indexes for table `technical_skills`
--
ALTER TABLE `technical_skills`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Skill_Name` (`Skill_Name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `administrators`
--
ALTER TABLE `administrators`
  MODIFY `Admin_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `archive_colleges`
--
ALTER TABLE `archive_colleges`
  MODIFY `archive_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `archive_companies`
--
ALTER TABLE `archive_companies`
  MODIFY `archive_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `archive_fileuploads`
--
ALTER TABLE `archive_fileuploads`
  MODIFY `archive_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `archive_messages`
--
ALTER TABLE `archive_messages`
  MODIFY `archive_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;
--
-- AUTO_INCREMENT for table `archive_notices`
--
ALTER TABLE `archive_notices`
  MODIFY `archive_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `archive_placements`
--
ALTER TABLE `archive_placements`
  MODIFY `archive_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `archive_positions`
--
ALTER TABLE `archive_positions`
  MODIFY `archive_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `archive_students`
--
ALTER TABLE `archive_students`
  MODIFY `archive_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `colleges`
--
ALTER TABLE `colleges`
  MODIFY `College_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=301;
--
-- AUTO_INCREMENT for table `companies_accounts`
--
ALTER TABLE `companies_accounts`
  MODIFY `Company_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `companies_placements`
--
ALTER TABLE `companies_placements`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=465;
--
-- AUTO_INCREMENT for table `discussion_threads`
--
ALTER TABLE `discussion_threads`
  MODIFY `Thread_ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `file_uploads`
--
ALTER TABLE `file_uploads`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `invitations`
--
ALTER TABLE `invitations`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `Message_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;
--
-- AUTO_INCREMENT for table `moderators`
--
ALTER TABLE `moderators`
  MODIFY `Moderator_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `notices`
--
ALTER TABLE `notices`
  MODIFY `Notice_ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `personal_skills`
--
ALTER TABLE `personal_skills`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=127;
--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `Student_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;
--
-- AUTO_INCREMENT for table `technical_skills`
--
ALTER TABLE `technical_skills`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `hsc_details`
--
ALTER TABLE `hsc_details`
  ADD CONSTRAINT `fk_hsc_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `msc_sem_1_details`
--
ALTER TABLE `msc_sem_1_details`
  ADD CONSTRAINT `fk_msem1_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `msc_sem_2_details`
--
ALTER TABLE `msc_sem_2_details`
  ADD CONSTRAINT `fk_msem2_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `msc_sem_3_details`
--
ALTER TABLE `msc_sem_3_details`
  ADD CONSTRAINT `fk_msem3_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `msc_sem_4_details`
--
ALTER TABLE `msc_sem_4_details`
  ADD CONSTRAINT `fk_msem4_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `selected_students`
--
ALTER TABLE `selected_students`
  ADD CONSTRAINT `selected_students_ibfk_1` FOREIGN KEY (`Student_ID`) REFERENCES `students` (`Student_ID`),
  ADD CONSTRAINT `selected_students_ibfk_2` FOREIGN KEY (`Company_Placement_ID`) REFERENCES `companies_placements` (`id`);

--
-- Constraints for table `sem_1_details`
--
ALTER TABLE `sem_1_details`
  ADD CONSTRAINT `fk_sem1_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `sem_2_details`
--
ALTER TABLE `sem_2_details`
  ADD CONSTRAINT `fk_sem2_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `sem_3_details`
--
ALTER TABLE `sem_3_details`
  ADD CONSTRAINT `fk_sem3_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `sem_4_details`
--
ALTER TABLE `sem_4_details`
  ADD CONSTRAINT `fk_sem4_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `sem_5_details`
--
ALTER TABLE `sem_5_details`
  ADD CONSTRAINT `fk_sem5_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `sem_6_details`
--
ALTER TABLE `sem_6_details`
  ADD CONSTRAINT `fk_sem6_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `ssc_details`
--
ALTER TABLE `ssc_details`
  ADD CONSTRAINT `fk_ssc_details_username` FOREIGN KEY (`Username`) REFERENCES `students` (`Username`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `fk_students_course` FOREIGN KEY (`Course`) REFERENCES `courses` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
