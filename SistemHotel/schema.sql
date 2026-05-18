-- ============================================================
-- Database Schema: Hotel Nusantara
-- Sistem Manajemen Hotel - Praktikum PBO IF-A 2025/2026
-- ============================================================

CREATE DATABASE IF NOT EXISTS hotel_nusantara
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE hotel_nusantara;

-- ========================
-- Table: users
-- ========================
CREATE TABLE IF NOT EXISTS users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nama_lengkap VARCHAR(100) NOT NULL,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ========================
-- Table: kamar
-- ========================
CREATE TABLE IF NOT EXISTS kamar (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nama_item       VARCHAR(100)   NOT NULL,
    harga_per_malam DECIMAL(15, 2) NOT NULL,
    tipe_kamar      VARCHAR(50)    NOT NULL,
    kapasitas_tamu  INT            NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ========================
-- Table: ruang_meeting
-- ========================
CREATE TABLE IF NOT EXISTS ruang_meeting (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nama_item       VARCHAR(100)   NOT NULL,
    harga_per_malam DECIMAL(15, 2) NOT NULL,
    kapasitas_orang INT            NOT NULL,
    fasilitas       TEXT           NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ========================
-- Table: reservasi (for Bookable interface)
-- ========================
CREATE TABLE IF NOT EXISTS reservasi (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    item_id           INT         NOT NULL,
    nama_tamu         VARCHAR(100) NOT NULL,
    tanggal_check_in  DATE        NOT NULL,
    tanggal_check_out DATE        NOT NULL,
    status            ENUM('aktif', 'batal', 'selesai') DEFAULT 'aktif',
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ========================
-- Sample Data
-- ========================

-- Default admin user (password: admin123)
INSERT INTO users (nama_lengkap, username, password) VALUES
    ('Administrator', 'admin', 'admin123'),
    ('Staff Hotel', 'staff', 'staff123');

-- Sample kamar data
INSERT INTO kamar (nama_item, harga_per_malam, tipe_kamar, kapasitas_tamu) VALUES
    ('Kamar Melati',       350000, 'Standard',          2),
    ('Kamar Anggrek',      500000, 'Deluxe',            2),
    ('Kamar Mawar',        750000, 'Junior Suite',      3),
    ('Kamar Cempaka',     1200000, 'Suite',             4),
    ('Kamar Nusantara',   2500000, 'Presidential Suite', 6);

-- Sample ruang meeting data
INSERT INTO ruang_meeting (nama_item, harga_per_malam, kapasitas_orang, fasilitas) VALUES
    ('Ruang Borobudur',  800000,  20, 'Proyektor, Whiteboard, AC, Sound System'),
    ('Ruang Prambanan',  1200000, 50, 'Proyektor HD, Whiteboard, AC, Sound System, Podium'),
    ('Ruang Majapahit',  2000000, 100, 'LED Screen, Stage, AC, Sound System Pro, Catering Area'),
    ('Ruang Sriwijaya',  500000,  10, 'TV 55", Whiteboard, AC, WiFi Premium');
