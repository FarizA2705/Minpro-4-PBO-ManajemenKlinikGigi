-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 15, 2026 at 07:05 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `klinik_gigi_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `dokter_gigi`
--

CREATE TABLE `dokter_gigi` (
  `id_dokter` varchar(10) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `spesialisasi` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `dokter_gigi`
--

INSERT INTO `dokter_gigi` (`id_dokter`, `nama`, `spesialisasi`) VALUES
('D001', 'Budi Santoso', 'Orthodontis'),
('D002', 'drg. Siti Rahma', 'Konservasi Gigi');

-- --------------------------------------------------------

--
-- Table structure for table `pasien_gigi`
--

CREATE TABLE `pasien_gigi` (
  `id_pasien` varchar(10) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `id_dokter` varchar(10) DEFAULT NULL,
  `id_rekam_medis` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pasien_gigi`
--

INSERT INTO `pasien_gigi` (`id_pasien`, `nama`, `id_dokter`, `id_rekam_medis`) VALUES
('P001', 'Andi Pratama', 'D001', 'RM001'),
('P002', 'Rivalio', 'D002', 'RM002');

-- --------------------------------------------------------

--
-- Table structure for table `rekam_medis`
--

CREATE TABLE `rekam_medis` (
  `id_rekam_medis` varchar(10) NOT NULL,
  `diagnosa` text NOT NULL,
  `catatan_tindakan` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `rekam_medis`
--

INSERT INTO `rekam_medis` (`id_rekam_medis`, `diagnosa`, `catatan_tindakan`) VALUES
('RM001', 'Karies Gigi Pre-Molar', 'Penambalan'),
('RM002', 'Impaksi Gigi Bungsu', 'Pencabutan');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dokter_gigi`
--
ALTER TABLE `dokter_gigi`
  ADD PRIMARY KEY (`id_dokter`);

--
-- Indexes for table `pasien_gigi`
--
ALTER TABLE `pasien_gigi`
  ADD PRIMARY KEY (`id_pasien`),
  ADD KEY `id_dokter` (`id_dokter`),
  ADD KEY `id_rekam_medis` (`id_rekam_medis`);

--
-- Indexes for table `rekam_medis`
--
ALTER TABLE `rekam_medis`
  ADD PRIMARY KEY (`id_rekam_medis`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `pasien_gigi`
--
ALTER TABLE `pasien_gigi`
  ADD CONSTRAINT `pasien_gigi_ibfk_1` FOREIGN KEY (`id_dokter`) REFERENCES `dokter_gigi` (`id_dokter`) ON DELETE SET NULL,
  ADD CONSTRAINT `pasien_gigi_ibfk_2` FOREIGN KEY (`id_rekam_medis`) REFERENCES `rekam_medis` (`id_rekam_medis`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
