# Quick Testing Guide - New Features

## Test Scenario 1: Medical Questionnaire (Patient View)

### Steps:
1. Login as a patient
2. Click "Medical Form" tab in navigation
3. **Expected**: See organized medical form sections with icons
4. If no form exists: See placeholder message "No medical form available yet"
5. Verify sections display:
   - Personal Information ✓
   - Emergency Contact ✓
   - Medical History ✓
   - Lifestyle ✓
   - Current Health Status ✓
   - Insurance Information ✓
   - Additional Notes ✓

---

## Test Scenario 2: Medical Questionnaire (Doctor Edit)

### Steps:
1. Login as a doctor
2. Click "Medical Forms" tab
3. Select a patient from dropdown
4. **Expected**: 
   - If patient has questionnaire: See editable form with current data
   - If patient has NO questionnaire: See empty form with "Create" button
5. Fill in or edit fields:
   - Enter full name, DOB, gender
   - Add emergency contact info
   - Add medical history details
   - Check/uncheck lifestyle checkboxes
   - Enter height, weight, blood type
   - Add insurance info
   - Add notes
6. Click "Save Medical Form" or "Create Medical Form"
7. **Expected**: Success alert appears
8. Refresh page and select same patient again
9. **Expected**: All saved data displays correctly

---

## Test Scenario 3: Bulk Monthly Schedule Creation

### Steps:
1. Login as a doctor
2. Go to "My Schedule" tab
3. Click "Create Monthly Schedule" button
4. **Expected**: Modal opens with pre-filled dates
5. Configure schedule:
   - Start Date: 2026-04-01
   - End Date: 2026-04-30
   - Start Time: 09:00
   - End Time: 17:00
   - Days of Week: Check Mon, Tue, Wed, Thu, Fri
6. Click "Create Schedules"
7. **Expected**: Alert shows "Successfully created X schedule(s)!"
8. **Expected**: Modal closes automatically
9. **Expected**: Schedule list refreshes showing new entries
10. Scroll through April dates
11. **Expected**: Only weekdays have schedules (no weekends)

### Test Variations:
- **All days**: Leave all day checkboxes unchecked → Creates schedule for every day
- **Weekends only**: Check Sat, Sun → Creates weekend schedules only
- **Custom range**: Set range spanning 2-3 months → Works across months
- **Duplicate prevention**: Run same creation twice → Second time creates 0 schedules

---

## Test Scenario 4: Book Appointment Button (From Doctor Card)

### Steps:
1. Login as a patient
2. Go to "Find Doctors" tab
3. Find any doctor card
4. Click "Book Appointment" button on the card
5. **Expected**: Modal opens showing "Select Service" step
6. Choose a service (e.g., "Consultation")
7. **Expected**: Moves to "Select Doctor" step
8. **Expected**: The doctor you originally clicked is pre-selected/visible
9. Click "Select Doctor" on that doctor
10. **Expected**: Shows date picker and time slots
11. Select an available date
12. **Expected**: Available time slots appear
13. Click on a time slot
14. **Expected**: Shows confirmation screen with all details
15. Click "Confirm Booking"
16. **Expected**: Success modal appears
17. Close success modal
18. Go to "My Appointments" tab
19. **Expected**: New appointment appears in list

---

## Test Scenario 5: Book Now Button (From Service Card)

### Steps:
1. Login as a patient
2. Go to "Available Services" tab
3. Click "Book Now" on any service
4. **Expected**: Modal opens showing doctors for that service
5. Select a doctor
6. Select date and time slot
7. Confirm booking
8. **Expected**: Booking completes successfully
9. Verify appointment appears in "My Appointments"

---

## Common Issues & Solutions

### Issue: Medical Form tab not showing
**Check**: 
- Browser console for JavaScript errors
- Verify patient.html loaded completely
- Check if jQuery is working: Type `jQuery.fn.jquery` in console

### Issue: Patient dropdown empty in doctor questionnaire
**Check**:
- Network tab: Is `/api/clients` returning data?
- Are there any clients in database?
- Any CORS errors?

### Issue: Bulk schedule creation fails
**Check**:
- Server logs for error messages
- Verify employee profile loaded (check console)
- Ensure date range is valid (start < end)
- Check if times are in HH:MM format

### Issue: "Book Appointment" opens wrong step
**Check**:
- Clear browser cache (Ctrl+Shift+R)
- Verify `window.preSelectedDoctorId` is set in console
- Check `showStep('services')` is being called

### Issue: Time slots not loading
**Check**:
- Does doctor have schedules for selected date?
- Check Network tab: `/api/visits/doctor/{id}/available-slots?date={date}`
- Verify DatabaseInitializer ran on startup (check server logs)
- Look for "Generated slots for schedule..." messages

---

## API Testing with cURL

### Test Questionnaire Creation
```bash
curl -X POST http://localhost:8080/api/questionnaires \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": 1,
    "fullName": "John Doe",
    "dateOfBirth": "1990-01-15",
    "gender": "Male",
    "emergencyContactName": "Jane Doe",
    "emergencyContactPhone": "+998901234567",
    "emergencyContactRelation": "Spouse",
    "chronicDiseases": "None",
    "smokes": false,
    "drinksAlcohol": false,
    "exercises": true,
    "height": 175.5,
    "weight": 70.0,
    "bloodType": "A+"
  }'
```

### Test Bulk Schedule Creation
```bash
curl -X POST http://localhost:8080/api/schedules/bulk-monthly \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 1,
    "startDate": "2026-04-01",
    "endDate": "2026-04-30",
    "startTime": "09:00",
    "endTime": "17:00",
    "daysOfWeek": ["MONDAY", "WEDNESDAY", "FRIDAY"]
  }'
```

### Test Get Questionnaire by Client
```bash
curl http://localhost:8080/api/questionnaires/client/1
```

---

## Database Verification Queries

### Check Questionnaires
```sql
-- Count total questionnaires
SELECT COUNT(*) FROM medical_questionnaires;

-- View recent questionnaires
SELECT questionnaire_id, client_id, full_name, created_at 
FROM medical_questionnaires 
ORDER BY created_at DESC 
LIMIT 10;

-- Check specific patient's questionnaire
SELECT * FROM medical_questionnaires WHERE client_id = 1;
```

### Check Bulk Schedules
```sql
-- Count schedules for a specific doctor in April 2026
SELECT COUNT(*) 
FROM schedules 
WHERE employee_id = 1 
  AND date >= '2026-04-01' 
  AND date <= '2026-04-30';

-- View schedules by day of week for April 2026
SELECT 
  EXTRACT(DOW FROM date) as day_of_week,
  TO_CHAR(date, 'Day') as day_name,
  COUNT(*) as schedule_count
FROM schedules 
WHERE employee_id = 1 
  AND date >= '2026-04-01' 
  AND date <= '2026-04-30'
GROUP BY EXTRACT(DOW FROM date), TO_CHAR(date, 'Day')
ORDER BY EXTRACT(DOW FROM date);

-- Check for duplicate schedules (should return 0)
SELECT employee_id, date, COUNT(*)
FROM schedules
GROUP BY employee_id, date
HAVING COUNT(*) > 1;
```

### Check Bookings
```sql
-- Recent appointments
SELECT v.visit_id, c.full_name as patient, e.full_name as doctor, 
       v.visit_date_time, v.status
FROM visits v
JOIN clients c ON v.client_id = c.client_id
JOIN employees e ON v.employee_id = e.employee_id
ORDER BY v.visit_date_time DESC
LIMIT 10;

-- Check appointments with slots
SELECT v.visit_id, s.start_time, s.end_time, v.status
FROM visits v
JOIN appointment_slots s ON v.slot_id = s.slot_id
WHERE v.visit_id IN (
  SELECT visit_id FROM visits 
  ORDER BY visit_id DESC LIMIT 5
);
```

---

## Performance Testing

### Load Test: Bulk Schedule Creation
Test creating schedules for large date ranges:
- 3 months (90 days) → Should complete in < 2 seconds
- 6 months (180 days) → Should complete in < 5 seconds
- 1 year (365 days) → Should complete in < 10 seconds

### Monitor:
- Server CPU usage during bulk creation
- Database query execution time
- Memory usage
- Response time in browser Network tab

---

## Success Criteria

All features pass when:
- ✅ No JavaScript errors in browser console
- ✅ No server-side errors in application logs
- ✅ All API endpoints return 200 OK status
- ✅ Data persists correctly in database
- ✅ UI updates reflect backend changes immediately
- ✅ Modals open and close smoothly
- ✅ Form validations work correctly
- ✅ Success/error messages display appropriately
- ✅ Page refresh maintains data integrity

---

## Reporting Bugs

When reporting issues, include:
1. **Steps to reproduce** (exact sequence of actions)
2. **Expected behavior** (what should happen)
3. **Actual behavior** (what actually happened)
4. **Browser console errors** (screenshot or copy-paste)
5. **Network tab response** (for API failures)
6. **Server logs** (error stack traces)
7. **Browser version** (Chrome/Firefox/Safari + version)
8. **Screenshots** (if visual issue)

Example:
```
Bug: Questionnaire save fails
Steps:
1. Login as doctor
2. Go to Medical Forms tab
3. Select patient "John Doe"
4. Fill in all fields
5. Click "Save Medical Form"

Expected: Success message and form updates
Actual: Red error banner with "Internal Server Error"

Console: POST /api/questionnaires 500 (Internal Server Error)
Server Log: org.postgresql.util.PSQLException: ERROR: null value in column "client_id"
Browser: Chrome 120.0.6099.109
Screenshot: [attached]
```

---

Happy Testing! 🚀
