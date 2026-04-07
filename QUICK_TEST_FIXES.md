# Quick Test - Bug Fixes Verification

## Test 1: Medical Questionnaire Visibility ✅

### Steps:
1. **Login as Doctor**
   - Username: (doctor credentials)
   - Go to "Medical Forms" tab
   - Select any patient from dropdown
   - Fill in questionnaire fields
   - Click "Create Medical Form" or "Save Medical Form"
   - Note the patient name

2. **Logout and Login as Patient**
   - Use the same patient's credentials
   - Wait for dashboard to load completely

3. **Check Medical Form Tab**
   - Click "Medical Form" in navigation
   - **Expected Result**: See the questionnaire created by doctor
   - **Should NOT see**: "Client profile not found" error

4. **Check Browser Console**
   - Open DevTools (F12)
   - Look for logs:
     ```
     Current user: {userId: X, username: "...", role: "CLIENT"}
     Looking for client with username: ...
     Found client: {clientId: X, firstName: "...", ...}
     ```

### Pass Criteria:
- ✅ Questionnaire displays with all sections
- ✅ No error messages
- ✅ Console shows successful client matching
- ✅ All data entered by doctor is visible

---

## Test 2: Booking via "Book Now" (Service First) ✅

### Steps:
1. **Login as Patient**
   - Use patient credentials
   - Wait for dashboard to load

2. **Navigate to Services**
   - Click "Available Services" tab
   - Find any service card (e.g., "Consultation")

3. **Start Booking**
   - Click "Book Now" button
   - Modal opens showing doctors

4. **Select Doctor**
   - Choose any available doctor
   - Click "Select Doctor"

5. **Select Date & Time**
   - Date picker appears
   - Select a date (should show available slots)
   - Click on an available time slot

6. **Confirm Booking**
   - Review confirmation details
   - Optionally add notes
   - Click "Confirm Booking"

7. **Verify Success**
   - **Expected**: Success modal appears
   - Close success modal
   - Go to "My Appointments" tab
   - **Expected**: New appointment visible in list

8. **Check Browser Console**
   - Should see:
     ```
     Booking data: {
       clientId: X,
       employeeId: X,
       serviceId: X,
       clinicId: X,
       slotId: X,
       notes: ""
     }
     ```
   - **Should NOT see**: HTTP 500 error

### Pass Criteria:
- ✅ No HTTP 500 errors
- ✅ Success modal appears
- ✅ Appointment shows in "My Appointments"
- ✅ Console shows booking data (not errors)
- ✅ Slot marked as booked (can't book again)

---

## Test 3: Booking via "Book Appointment" (Doctor First) ✅

### Steps:
1. **Login as Patient**
   - Use patient credentials

2. **Navigate to Doctors**
   - Click "Our Medical Team" tab
   - Find any doctor card

3. **Start Booking**
   - Click "Book Appointment" button
   - Modal opens showing services

4. **Select Service**
   - Choose any service
   - System should use pre-selected doctor

5. **Select Date & Time**
   - Pick available date
   - Click on available time slot

6. **Confirm Booking**
   - Review details
   - Click "Confirm Booking"

7. **Verify Success**
   - **Expected**: Success modal appears
   - Close modal
   - Check "My Appointments"
   - **Expected**: New appointment visible

8. **Check Browser Console**
   - Should see booking data logged
   - **Should NOT see**: HTTP 500 error

### Pass Criteria:
- ✅ No HTTP 500 errors
- ✅ Success modal appears
- ✅ Appointment shows in list
- ✅ Same result as Test 2

---

## Database Verification

Run these SQL queries to verify data integrity:

### Check Recent Visits
```sql
SELECT 
    v.visit_id,
    c.first_name || ' ' || c.last_name as patient,
    e.full_name as doctor,
    s.name as service,
    v.status,
    v.visit_date_time,
    v.notes
FROM visits v
JOIN clients c ON v.client_id = c.client_id
JOIN employees e ON v.employee_id = e.employee_id
JOIN services s ON v.service_id = s.service_id
ORDER BY v.visit_id DESC
LIMIT 10;
```

**Expected**: Your test bookings should appear with status "SCHEDULED"

### Check Booked Slots
```sql
SELECT 
    slot.slot_id,
    sch.date,
    slot.start_time,
    slot.end_time,
    slot.is_booked,
    v.visit_id,
    c.first_name as patient
FROM appointment_slots slot
JOIN schedules sch ON slot.schedule_id = sch.schedule_id
LEFT JOIN visits v ON slot.visit_id = v.visit_id
LEFT JOIN clients c ON v.client_id = c.client_id
WHERE slot.is_booked = true
ORDER BY slot.slot_id DESC
LIMIT 10;
```

**Expected**: Slots you booked should show `is_booked = true` and have visit_id

### Check for Orphaned Data
```sql
-- Should return 0 rows
SELECT * FROM visits WHERE client_id IS NULL;
SELECT * FROM visits WHERE employee_id IS NULL;
SELECT * FROM visits WHERE service_id IS NULL;
SELECT * FROM visits WHERE schedule_id IS NULL;
```

**Expected**: Empty result set (no orphaned visits)

---

## Common Issues & Solutions

### Issue: Still seeing "Client profile not found"
**Solution**:
1. Hard refresh browser (Ctrl+Shift+R or Cmd+Shift+R)
2. Clear browser cache completely
3. Check console logs for matching attempts
4. Verify client exists in database:
   ```sql
   SELECT * FROM clients WHERE email = 'patient@email.com';
   ```

### Issue: Still getting HTTP 500 on booking
**Solution**:
1. Check server logs for exact error message
2. Verify appointment slot exists:
   ```sql
   SELECT * FROM appointment_slots WHERE slot_id = X;
   ```
3. Verify all IDs in booking data are valid
4. Rebuild application:
   ```bash
   bash gradlew clean build -x test
   ```

### Issue: Can't see available time slots
**Solution**:
1. Check if doctor has schedule for that date:
   ```sql
   SELECT * FROM schedules 
   WHERE employee_id = X AND date = '2026-04-XX';
   ```
2. Check if slots were generated:
   ```sql
   SELECT COUNT(*) FROM appointment_slots 
   WHERE schedule_id IN (
       SELECT schedule_id FROM schedules WHERE employee_id = X
   );
   ```
3. Restart application to trigger DatabaseInitializer

### Issue: Booking succeeds but appointment doesn't show
**Solution**:
1. Check if visit was created:
   ```sql
   SELECT * FROM visits ORDER BY visit_id DESC LIMIT 1;
   ```
2. Refresh "My Appointments" page
3. Check if client ID matches:
   ```sql
   SELECT v.visit_id, v.client_id, c.client_id 
   FROM visits v 
   JOIN clients c ON v.client_id = c.client_id 
   ORDER BY v.visit_id DESC LIMIT 5;
   ```

---

## Performance Testing

### Test Multiple Bookings
1. Try booking 5 appointments in a row
2. Each should succeed without errors
3. Check database after each booking
4. Verify no duplicate bookings

### Expected Performance:
- Booking API response: < 500ms
- Page load after booking: < 1s
- No browser console warnings/errors

---

## Edge Cases to Test

### 1. Case-Insensitive Email Matching
- Client email in DB: `john@example.com`
- User logs in with: `John@Example.COM`
- **Expected**: Should match and show questionnaire

### 2. Phone Number Matching
- Client phone in DB: `+998901234567`
- User logs in with: `+998901234567`
- **Expected**: Should match correctly

### 3. Already Booked Slot
- Try to book a slot that's already booked
- **Expected**: Error message "This time slot is already booked"

### 4. Invalid Slot ID
- Manipulate booking request to use non-existent slot ID
- **Expected**: Error message "Appointment slot not found with ID: X"

### 5. Missing Required Fields
- Try booking without selecting service/doctor/slot
- **Expected**: Alert "Please complete all selections"

---

## Success Indicators

After all tests pass, you should see:

### In Browser Console:
```javascript
Current user: {userId: 5, username: "patient@test.com", role: "CLIENT"}
Looking for client with username: patient@test.com
Found client: {clientId: 3, firstName: "John", lastName: "Doe", ...}

Booking data: {
  clientId: 3,
  employeeId: 1,
  serviceId: 2,
  clinicId: 1,
  slotId: 15,
  notes: ""
}
```

### In Server Logs:
```
Created X schedules for employee Y
Hibernate: insert into visits (...) values (...)
Hibernate: update appointment_slots set is_booked=true where slot_id=?
```

### In Database:
- New visits with proper foreign keys
- Appointment slots marked as booked
- No null values in required fields

### In UI:
- Questionnaires display correctly
- Booking completes with success modal
- Appointments appear in patient dashboard
- No error messages or alerts

---

## Reporting Results

If tests pass:
- ✅ Mark all checkboxes above
- ✅ Document any minor observations
- ✅ Approve for production deployment

If tests fail:
- ❌ Document exact failure point
- ❌ Capture screenshots
- ❌ Copy console/server logs
- ❌ Report to development team with details

---

**Test Date**: _______________  
**Tester Name**: _______________  
**Result**: ☐ PASS  ☐ FAIL  
**Notes**: _______________________________________________
