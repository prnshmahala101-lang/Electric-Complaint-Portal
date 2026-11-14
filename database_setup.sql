-- ============================================================
-- Electricity Complaint Portal - Database Setup Script
-- Database Name: electricity_db
-- ============================================================

-- Step 1: Create Database
CREATE DATABASE IF NOT EXISTS electricity_db;
USE electricity_db;

-- Step 2: Create 'users' Table (For User Authentication / Management)
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(100) NOT NULL PRIMARY KEY
);

-- Step 3: Create 'complaints' Table (For Electricity Complaints)
CREATE TABLE IF NOT EXISTS complaints (
    complaint_id VARCHAR(50) NOT NULL PRIMARY KEY,
    consumer_number VARCHAR(50) NOT NULL,
    category VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'Pending',
    complaint_date VARCHAR(50) NOT NULL,
    address VARCHAR(255),
    description TEXT
);

-- Step 4: Insert Initial Sample Data for Testing
INSERT INTO users (username) VALUES 
    ('admin'),
    ('priyanshu'),
    ('consumer_user')
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO complaints (complaint_id, consumer_number, category, status, complaint_date, address, description) VALUES
    ('CMP1001', '1234567890', 'Power Failure', 'Pending', '02-11-2026', 'Sector 12, Malviya Nagar, Jaipur', 'Frequent power cuts during evening hours.'),
    ('CMP1002', '1234567890', 'Voltage Issue', 'Resolved', '01-11-2026', 'Plot 45, Vaishali Nagar, Jaipur', 'High voltage fluctuations damaging appliances.'),
    ('CMP1003', '9876543210', 'Meter Problem', 'Pending', '02-11-2026', 'Civil Lines, Jaipur', 'Meter display is blank and not recording units.')
ON DUPLICATE KEY UPDATE complaint_id=complaint_id;

-- Step 5: Verify Data
SELECT * FROM users;
SELECT * FROM complaints;
