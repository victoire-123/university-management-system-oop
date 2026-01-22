group 8

1. Mpano Victoire, 30663/2025.    
2. Shema Deo Anthere, 29786/2025.
3. Eliwantchoni, 23734/2024
4. Nasir Roup, 29373/2025
5. Elsir Omer ,26082/2024
6. IRUMVA Paul, 29729/2025
7. Shehababdo, 30239/2025





University Course Management System
📚 Project Overview
A comprehensive console-based Java application for managing university courses, users, enrollments, and grades. This system demonstrates all four pillars of Object-Oriented Programming (OOP) through practical implementation.

🎯 Project Goals
Implement a fully functional course management system

Demonstrate mastery of OOP principles: Inheritance, Polymorphism, Encapsulation, Abstraction

Develop team collaboration skills through component-based development

Create a robust, error-handled application with data persistence

👥 Team Members & Responsibilities
Component	Assigned To	Responsibilities
1. User Management	[Name 1], [Name 2]	Student, Instructor, Admin classes
2. Course Management	[Name 3]	Course CRUD operations
3. Enrollment System	[Name 4]	Enrollment logic with validations
4. Grade Management	[Name 5]	Multiple grading calculators
5. Notification System	[Name 6]	Email, SMS, InApp notifications
6. Reporting System	[Name 7]	Various report types
7. Authentication	[Name 8]	Login and access control
8. Data Persistence	[Name 9]	File-based storage
🚀 Features
👤 User Management
Three user types with inheritance hierarchy: User → Student, Instructor, Admin

Unique attributes and behaviors for each user type

Role-based access control

📋 Course Management
Create, update, and delete courses

Track course capacity and enrolled students

Prerequisite checking and validation

Department and schedule management

📝 Enrollment System
Students can enroll in available courses

Automated validation: capacity, prerequisites, credit limits

Course drop functionality

Real-time enrollment status tracking

📊 Grade Management
Multiple grading schemes: Letter, Pass/Fail, Numerical

Polymorphic grade calculation

GPA computation

Grade assignment by instructors

🔔 Notification System
Multiple channels: Email, SMS, In-App

Events: enrollment confirmation, grade posting, course drop

Polymorphic notification service

📈 Reporting System
Student transcripts

Course rosters

Instructor teaching loads

Department summaries

Inheritance-based report generation

🔐 Authentication & Authorization
Secure login system

Session management

Role-based feature access

💾 Data Persistence
File-based storage (text files/serialization)

Data persists between program runs

All entities: users, courses, enrollments, grades

🛠️ Technical Stack
Language: Java SE 8+

Interface: Console-based (no GUI)

Storage: File-based (no databases)

Packages: com.university.{component}

Dependencies: Pure Java (no external frameworks)

📦 Project Structure
text
src/
├── com/
│   └── university/
│       ├── user/           # Component 1
│       ├── course/         # Component 2
│       ├── enrollment/     # Component 3
│       ├── grade/          # Component 4
│       ├── notification/   # Component 5
│       ├── report/         # Component 6
│       ├── auth/           # Component 7
│       ├── persistence/    # Component 8
│       └── Main.java       # Application entry point
⚙️ Setup & Installation
Prerequisites
Java Development Kit (JDK) 8 or higher

Git (for version control)

Compilation
bash
# Windows
compile.bat

# Mac/Linux
./compile.sh
Running the Application
bash
# Windows
run.bat

# Mac/Linux
./run.sh
🏗️ OOP Principles Demonstrated
1. Inheritance
java
// User hierarchy example
public abstract class User { }
public class Student extends User { }
public class Instructor extends User { }
public class Admin extends User { }
2. Polymorphism
java
// Notification system example
interface NotificationService {
    void sendNotification(String message);
}

class EmailNotification implements NotificationService { }
class SMSNotification implements NotificationService { }
3. Encapsulation
java
// Proper data hiding
public class Student {
    private String studentId;
    private double gpa;
    
    public double getGPA() { return this.gpa; }
    public void setGPA(double gpa) { 
        if(gpa >= 0.0 && gpa <= 4.0) this.gpa = gpa;
    }
}
4. Abstraction
java
// Abstract classes and interfaces
public abstract class GradeCalculator {
    public abstract Grade calculateGrade(Score score);
}

public interface DataRepository {
    void save(Object data);
    Object load(String id);
}
📋 Sample Workflow
Admin Workflow
Login as Admin

Create new courses

Manage user accounts

View system reports

Send announcements

Instructor Workflow
Login as Instructor

View assigned courses

Access course rosters

Assign grades to students

Generate teaching load reports

Student Workflow
Login as Student

Browse available courses

Enroll in courses (with validation)

View current enrollments

Check grades and transcript

Drop courses if needed

🧪 Testing
The application includes comprehensive error handling:

Input validation for all user inputs

Exception handling for file operations

Graceful error messages

Data integrity checks

📊 Sample Data
The system comes with pre-loaded sample data:

3 Admin users

5 Instructors with assigned courses

20 Students with various enrollments

15 Courses with prerequisites

Multiple departments and programs

🔗 Integration Points
All components integrate through:

Shared interfaces

Common data models

Centralized Main class

Consistent file formats

Unified exception handling

📝 Development Notes
Git Collaboration
Each team member must have at least 3 meaningful commits

Use descriptive commit messages

Pull before pushing to avoid conflicts

Follow the branching strategy outlined in SETUP.md

Code Quality Standards
Meaningful variable and method names

Comments for complex logic

Consistent indentation (4 spaces)

No code duplication

All fields must be private with getters/setters

Error Handling
Validate all user inputs

Handle file I/O exceptions

Provide helpful error messages

Maintain data consistency

🚨 Common Issues & Solutions
Compilation Issues
Class not found: Ensure you're in the correct directory

Java version mismatch: Verify JDK 8+ is installed

Permission denied (Mac/Linux): Run chmod +x *.sh

Runtime Issues
File not found: Check data files exist in correct location

Invalid input: Follow the exact format shown in prompts

Null pointer: Restart application with ./run.sh

📚 Learning Outcomes
By completing this project, you will:

Master OOP principles through practical application

Develop skills in component-based software design

Learn team collaboration in software development

Understand file-based data persistence

Practice exception handling and input validation

🤝 Collaboration Guidelines
Daily Communication: Use team chat for updates

Regular Commits: Small, frequent commits preferred

Code Reviews: Review each other's pull requests

Testing: Test components individually before integration

Documentation: Update documentation as you progress

📅 Project Timeline
Friday: Setup and planning

Saturday: Core component development

Sunday: Integration and testing

Monday: Final touches and submission

📄 Submission Requirements
Working application that compiles and runs

Complete GitHub repository with all team contributions

This README.md file with team information

All components properly integrated

Data persistence working correctly

🆘 Getting Help
Check the documentation in the starter code

Review example implementations

Discuss with team members

Consult the TASKS.md file

Ask in class forums during designated hours

⚠️ Academic Integrity
Write your own code for assigned components

You may discuss concepts with other groups

Do not share code between groups

Cite any external resources used

AI tools may be used for understanding concepts only

🎉 Success Tips
Start early - Don't wait until Monday

Communicate - Daily updates prevent integration issues

Test often - Test each component as you complete it

Ask for help - Don't struggle alone

Document - Keep notes of important decisions
