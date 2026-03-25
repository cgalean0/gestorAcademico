# Professor Dashboard Specification

## Purpose

Define the complete functional requirements and behaviors for the Professor Dashboard in the Gestor Académico FullStack application.

## Requirements

### Requirement: Route Access Control

The system SHALL restrict access to the `/professor` route only to authenticated users with the PROFESSOR role.

#### Scenario: Authorized professor accesses dashboard

- GIVEN a user is logged in with PROFESSOR role
- WHEN they navigate to `/professor`
- THEN the dashboard component renders successfully

#### Scenario: Unauthenticated user attempts access

- GIVEN a user is NOT logged in
- WHEN they navigate to `/professor`
- THEN they are redirected to the login page

#### Scenario: Non-professor user attempts access

- GIVEN a user is logged in with STUDENT or ADMIN role
- WHEN they navigate to `/professor`
- THEN they are redirected to their respective dashboard or shown a 403 error

---

### Requirement: Dashboard Layout

The system SHALL display a consistent layout with sidebar, header, and main content areas.

#### Scenario: Professor views dashboard layout

- GIVEN a professor is logged in and on the dashboard
- THEN they see a sidebar on the left with 4 navigation items
- AND a header at the top with user name and logout button
- AND main content area on the right that changes based on selected section

#### Scenario: Professor switches navigation section

- GIVEN a professor is on the Mis Cursos section
- WHEN they click on "Asistencia" in the sidebar
- THEN the main content updates to show the Asistencia section
- AND the sidebar highlights the active item

---

### Requirement: Mis Cursos Section

The system SHALL display a list of courses assigned to the logged-in professor with enrolled student information.

#### Scenario: Professor views their courses

- GIVEN a professor is logged in
- WHEN they access the Mis Cursos section
- THEN they see a list of all courses they teach
- AND each course shows: course name, code, enrolled students count
- AND clicking on a course expands to show enrolled students list

#### Scenario: Professor views enrolled students

- GIVEN a professor is on Mis Cursos section
- WHEN they click on a course card
- THEN a list of enrolled students appears
- AND each student shows: name, email, enrollment date

---

### Requirement: Asistencia Section

The system SHALL allow professors to take and manage attendance for their courses by date.

#### Scenario: Professor selects course for attendance

- GIVEN a professor is on the Asistencia section
- WHEN they select a course from the dropdown
- THEN the system loads the course's enrolled students
- AND displays a date picker

#### Scenario: Professor takes attendance

- GIVEN a professor has selected a course and date
- WHEN they mark students as present/absent/tardy
- AND they click "Guardar Asistencia"
- THEN the system saves the attendance record
- AND shows a success message

#### Scenario: Professor views past attendance

- GIVEN a professor is on the Asistencia section
- WHEN they select a past date
- THEN the system shows the previously recorded attendance
- AND allows editing

---

### Requirement: Calificaciones Section

The system SHALL allow professors to enter and manage grades for students by evaluation type.

#### Scenario: Professor selects course for grading

- GIVEN a professor is on the Calificaciones section
- WHEN they select a course from the dropdown
- THEN the system loads the course's evaluation types and enrolled students

#### Scenario: Professor enters grades

- GIVEN a professor has selected a course
- WHEN they select an evaluation type (e.g., "Parcial 1", "Tarea 1")
- THEN they see a table with students and grade input fields
- AND they can enter numeric grades (0-10 scale)
- AND clicking "Guardar Calificaciones" saves all grades

#### Scenario: Professor views grade summary

- GIVEN a professor has saved grades for a course
- WHEN they select a student
- THEN they see all the student's grades across all evaluations
- AND the calculated average

---

### Requirement: Mi Perfil Section

The system SHALL display and allow editing of the professor's personal data and password.

#### Scenario: Professor views their profile

- GIVEN a professor is logged in
- WHEN they access the Mi Perfil section
- THEN they see their personal data: name, email, department, hire date
- AND a password change form

#### Scenario: Professor changes password

- GIVEN a professor is on Mi Perfil section
- WHEN they fill in current password, new password, and confirm new password
- AND all validations pass
- AND they click "Cambiar Contraseña"
- THEN the system updates the password
- AND shows a success message

#### Scenario: Password validation fails

- GIVEN a professor is on Mi Perfil section
- WHEN they try to change password with mismatched new passwords
- THEN the system shows an error: "Las contraseñas no coinciden"
- AND does not submit

---

### Requirement: Responsive Design

The system SHALL provide a usable experience across desktop, tablet, and mobile devices.

#### Scenario: Professor views dashboard on mobile

- GIVEN a professor accesses the dashboard on a mobile device
- WHEN the viewport width is < 768px
- THEN the sidebar collapses to a hamburger menu
- AND the content area takes full width

#### Scenario: Professor views dashboard on tablet

- GIVEN a professor accesses the dashboard on a tablet device
- WHEN the viewport width is 768px - 1024px
- THEN the sidebar shows as icons only
- AND expands on hover

---

### Requirement: Authentication Persistence

The system SHALL maintain session state and handle token expiration gracefully.

#### Scenario: Session is still valid

- GIVEN a professor has an active JWT token
- WHEN they navigate between sections
- THEN all API calls succeed without re-authentication

#### Scenario: Token expires during use

- GIVEN a professor's JWT token has expired
- WHEN they attempt an API call
- THEN the system shows a session expired message
- AND redirects to login page
