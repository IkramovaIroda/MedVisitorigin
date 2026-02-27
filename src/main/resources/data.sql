-- Seed initial clinic
INSERT INTO clinics (name, address, phone, email, website, is_active) VALUES
('MedVisit Clinic', 'Tashkent, Zdorovya str. 10', '+998901234567', 'info@medvisit.com', 'www.medvisit.com', true);

-- Seed initial users (password is BCrypt encoded for '123456')
INSERT INTO users (username, password, role, active) VALUES
('admin', '$2b$10$Pi9ptrlmY2QTK3Fmhxqumu3wTYlVvv74ZpnvISoPSsnq7d7VEQgEq', 'ADMIN', true),
('doctor1', '$2b$10$Pi9ptrlmY2QTK3Fmhxqumu3wTYlVvv74ZpnvISoPSsnq7d7VEQgEq', 'EMPLOYEE', true),
('doctor2', '$2b$10$Pi9ptrlmY2QTK3Fmhxqumu3wTYlVvv74ZpnvISoPSsnq7d7VEQgEq', 'EMPLOYEE', true),
('patient1', '$2b$10$Pi9ptrlmY2QTK3Fmhxqumu3wTYlVvv74ZpnvISoPSsnq7d7VEQgEq', 'CLIENT', true);

-- Seed employees (doctors)
INSERT INTO employees (full_name, position, specialization, phone, email, clinic_id) VALUES
('Ivanov S.P.', 'Doctor', 'Therapist', '+998901111111', 'ivanov@medvisit.com', 1),
('Petrova A.I.', 'Doctor', 'Cardiologist', '+998902222222', 'petrova@medvisit.com', 1),
('Sidorov A.V.', 'Doctor', 'Neurologist', '+998903333333', 'sidorov@medvisit.com', 1),
('Kuznetsova O.S.', 'Doctor', 'Pediatrician', '+998904444444', 'kuznetsova@medvisit.com', 1);

-- Seed services
INSERT INTO services (name, description, price, clinic_id) VALUES
('Consultation', 'General medical consultation', 150000, 1),
('Diagnostics', 'Full diagnostic examination', 350000, 1),
('ECG', 'Electrocardiogram', 200000, 1),
('Ultrasound', 'Ultrasound examination', 250000, 1),
('Vaccination', 'Vaccination for children and adults', 100000, 1);

-- Seed clients
INSERT INTO clients (first_name, last_name, patronymic, date_of_birth, gender, phone, email, address, passport_number, insurance_number, is_active, clinic_id) VALUES
('Anna', 'Smirnova', 'Ivanovna', '1990-05-15', 'F', '+998905551111', 'anna@mail.com', 'Tashkent', 'AB1234567', 'INS001', true, 1);

-- Seed schedules
INSERT INTO schedules (date, start_time, end_time, employee_id) VALUES
('2026-03-01', '09:00', '13:00', 1),
('2026-03-01', '14:00', '18:00', 2),
('2026-03-02', '09:00', '13:00', 3),
('2026-03-02', '14:00', '18:00', 4),
('2026-03-03', '09:00', '17:00', 1),
('2026-03-03', '09:00', '17:00', 2);
