
# TAbby Dabby (AB3)
[![Java CI](https://github.com/AY2425S2-CS2103T-T12-1/tp/actions/workflows/gradle.yml/badge.svg)](https://github.com/AY2425S2-CS2103T-T12-1/tp/actions/workflows/gradle.yml)

![Ui](docs/images/Ui.png)

This is a sample project for Software Engineering (SE) students.

Example usages:
- as a starting point of a course project (as opposed to writing everything from scratch)
- as a case study

The project simulates an ongoing software project for a desktop application (called TAbby Dabby) used for managing contact details.

It is written in OOP fashion. It provides a reasonably well-written code base bigger (around 6 KLoC) than what students usually write in beginner-level SE modules, without being overwhelmingly big.

It comes with a reasonable level of user and developer documentation.

It is named TAbby Dabby because it rolls off the tongue and is designed to assist TAs (i.e., Teaching Assistants) with their administrative work.

For the detailed documentation of this project, see the [TAbby Dabby Product Website](https://se-education.org/addressbook-level3/).

This project is a part of the [se-education.org](https://se-education.org) initiative. If you would like to contribute code to this project, see [se-education.org](https://se-education.org) for more info.

## Features

### User Stories

As a teaching assistant, I want to:

- Search for a contact by name, email, or role, so that I can quickly find the person I need to reach out to. **
- Filter contacts by student, professor, or TA, so that I can organize my address book more efficiently. **
- Import contacts from a CSV file or my university's system, so that I don't have to manually enter each contact. *
- Update a contact's details, such as their email or phone number, so that I always have up-to-date information.
- Add notes to a contact, such as "Needs extra help with assignments" or "Final project team leader", so that I can remember important details about them. **
- Mark certain contacts as "priority", so that I can quickly access the most important people (e.g., professors, head TAs). **
- Log interactions with students (e.g., meeting notes, emails sent), so that I can track who I've assisted and follow up if needed. *
- Know how my students look, so that I can remember faces and names better. *
- Export my contacts to a CSV or other format, so that I can back up or share my contact list if needed. *
- Track my students' homework submission status, so that I can mark their work for grading purposes. *
- Set reminders for specific contacts (e.g., "Follow up with John on project progress"), so that I don't forget important tasks. *
- Track whether I marked my student's homework, so that I ensure that I do not leave work unmarked. *
- Track my students' grades, so that I can assess my own teaching ability (or the learning abilities of my students). *
- Give feedback to my students on their assignments and homework submissions, so that they can learn from their mistakes. *
- See the dates of upcoming tutorials, so that I can mentally prepare myself for my demise. *
- See exam dates, so that I can remind my students to prepare for them. *
- Track whether my teaching materials have been completed, so that I can complete them in time for the tutorial. *
- Plot a whisker plot of the current grades of my students, so that I can track their learning progress and identify struggling students and outstanding students. *
- Mark the attendance count of students, so that I can keep track of the attendance counts for grading. **

## Minimum Viable Product (MVP)

### Core Features

#### Add a new contact (e.g., student, professor, colleague) with details such as name, email, phone number, and role, so that I can quickly access their information when needed.

**Feature**: Create new contact

**Purpose**: Add a new contact to the list of contacts

**Command Format**: `Add [Contact name] [Email] [Phone Number] [Role]`

**Example Command**: `Add John Doe John@gmail.com 81234567 TA`

**Parameters**:

- **[Contact name]**:
  - Acceptable values: Any String without special characters or numbers (e.g., +*-_/?!<>1234)
  - Error Message: "Invalid Format. Please do not enter any special characters or numbers"
  - Rationale: No usage of special characters to prevent the possibility of malicious code injection.

- **[Email]**:
  - Acceptable values: Follows the regex – `/^((?!\.)[\w\-_.]*[^.])(@\w+)(\.\w+(\.\w+)?[^.\W])$/gm`
  - Error Message: "Invalid Email Format"
  - Rationale: Accept only valid email formats – cannot start or end with a dot. Allows double domain names and should not contain special characters or spaces in the email.

- **[Phone Number]**:
  - Acceptable values: String containing integer digits, with optional "+" in front for country codes
  - Error Message: "Invalid phone number"
  - Rationale: Accept only valid phone numbers – optionally starting with "+" followed by a string of digits.

- **[Role]**:
  - Acceptable values: Any String without special characters or numbers (e.g., +*-_/?!<>1234)
  - Error Message: "Invalid Role. Please ensure that the role exists"
  - Rationale: Accept only valid roles. No usage of special characters to prevent the possibility of malicious code injection.

**Outputs**: If success, return an acknowledgment message. Otherwise, specify the error and appropriate format.

**Duplicate Handling**: Allow duplicates

**Possible Errors**: Invalid parsing of commands.

#### Create and manage groups (e.g., "CS2103 Students", "CS2103 TAs", "Professors"), so that I can organize contacts based on my different responsibilities.

**Feature**: Add to group

**Purpose**: Manage contacts clearly by compartmentalizing into groups

**Command Format**: `Group [Contact id] [Group]`

**Example Command**: `Group 42 G13`

**Parameters**:

- **[Contact id]**:
  - Acceptable values: Positive integer between 1 and the size of the current student list. Must reference a valid student in the displayed list. No leading zeros allowed (e.g., '01' is invalid). No spaces allowed before or after the number.
  - Error Message: "Invalid student index. Please provide a valid number from the displayed list."
  - Rationale: Using index numbers ensures unambiguous student selection, especially when there are students with similar names.

- **[Group]**:
  - Acceptable values: Any String without special characters (e.g., +*-_/?!<>)
  - Error Message: "Error, group not found. Please ensure that you are adding contact to existing groups"
  - Rationale: No usage of special characters to prevent the possibility of malicious code injection.

#### Mark the attendance count of students, so that I can keep track of the attendance counts for grading.

**Feature**: Mark Attendance

**Purpose**: Allows teaching assistants to record student attendance for each class session and maintain a running count of attendance for grading purposes.

**Command Format**: `mark-attendance INDEX [w/WEEK] [d/DAY] [s/STATUS]`

**Parameters**:

- **[Student Index (INDEX)]**:
  - Acceptable values: Positive integer between 1 and the size of the current student list. Must reference a valid student in the displayed list. No leading zeros allowed (e.g., '01' is invalid). No spaces allowed before or after the number.
  - Error Message: "Invalid student index. Please provide a valid number from the displayed list."
  - Rationale: Using index numbers ensures unambiguous student selection, especially when there are students with similar names.

- **[Week Number (w/WEEK)]**:
  - Acceptable values: Positive integer between 1 and 13 (representing academic weeks). No leading zeros. No spaces between 'w/' and the number. Default: Current academic week if not specified.
  - Error Message: "Invalid week number. Please enter a number between 1 and 13."
  - Rationale: 13-week semester standard in most universities.

- **[Day (d/DAY)]**:
  - Acceptable values: MON, TUE, WED, THU, FRI. Case insensitive. No spaces between 'd/' and the day. Default: Current day if not specified.
  - Error Message: "Invalid day. Please use MON, TUE, WED, THU, or FRI."
  - Rationale: Weekdays only as classes are not typically held on weekends.

- **[Attendance Status (s/STATUS)]**:
  - Acceptable values: PRESENT, ABSENT, EXCUSED. Case insensitive. No spaces between 's/' and the status. Default: PRESENT if not specified.
  - Error Message: "Invalid attendance status. Please use PRESENT, ABSENT, or EXCUSED."
  - Rationale: Three states cover all common attendance scenarios while keeping tracking simple.

**Example Commands**:
- `mark-attendance 1 w/3 d/MON s/PRESENT`
- `mark-attendance 5 w/2 s/ABSENT`
- `mark-attendance 3 d/WED`
- `mark-attendance 2`

#### Delete a contact, to remove students who have dropped the class or transferred classes.

**Feature**: Delete contact

**Purpose**: To remove unnecessary students from the class list, i.e., if the student dropped the class or transferred classes.

**Command Format**: `contact delete <contact-id>`

**Example Command**: `contact delete 123`

**Parameters**:

- **[Contact ID]**:
  - Acceptable values: Natural numbers.
  - Error Message: "Contact does not exist."

**Outputs**: Successfully deleted / Contact does not exist.

**Possible Errors**: Contact does not exist.

#### Update a contact's details, such as their email or phone number, so that I always have up-to-date information.

**Feature**: Update a contact's details

**Purpose**: To ensure contact is the most up-to-date in the event of changes in one contact detail, without the need to delete all other contact details which could lead to data loss.

**Command Format**: `update INDEX [n/NAME] [e/EMAIL] [p/PHONE] [r/ROLE]`

**Example Command**: `update 123 n/Mallory e/CLEAR p/62353535 r/`

**Parameters**:

- **[NAME, EMAIL, PHONE, ROLE]**: No changes will be made if arguments are left empty. If the argument is "CLEAR", the contact detail represented by the param will be reset to an empty value. If the argument is not empty, the contact detail will be updated to the provided argument.

**Outputs**: Successfully updated / Contact does not exist / Form validation error - (insert details).

**Possible Errors**: Non-existent contact, email address does not contain "@", phone number contains chars other than + and digits, name/role cannot be CLEAR.

## Contributing

This project is a part of the [se-education.org](https://se-education.org) initiative. If you would like to contribute code to this project, see [se-education.org](https://se-education.org) for more info.