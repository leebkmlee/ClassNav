create database IM_PROJECT

create table [USER] (
UserID int,
Lname varchar (50),
Fname varchar (50),
Mname varchar (50),
Street varchar (50),
Barangay varchar (50),
City varchar (50),
Province varchar (50),
PostalCode int check (PostalCode between 1000 and 9999),
Birthday varchar (10),
Gender char(1) check (Gender in('M', 'F')),
ProgramCode varchar (10),
UserType varchar (10) check (UserType in ('Student', 'Admin', 'Faculty')))

create table STUDENT (
StudentNumber int,
YearLevel int check (YearLevel in (1,2,3,4)))

create table [ADMIN] (
AdminID int,
[Role] varchar (20))

create table FACULTY (
ProfessorID int,
ContactNumber varchar (11),
EmailAdress varchar (50),
CollegeCode varchar (10))

create table SECTION (
Section_ID varchar(20),
YearLevel int check (YearLevel in (1, 2, 3, 4)),
[Group] int check ([Group] in (1, 2)),
RoomCode varchar (10),
CourseCode varchar (10),
ProfessorID varchar (20))

create table ENROLLMENT(
EnrollmentID varchar (20),
Section_ID varchar(20),
StudentNumber varchar (20))

create table SCHEDULE(
ScheduleID int,
[Day] varchar (10),
StartTime varchar (10),
EndTime varchar (10),
SectionID varchar(20),
RoomCode varchar (10))

create table ROOM (
RoomCode varchar (10),
RoomDescription varchar(20),
FloorNumber int check (FloorNumber in (1,2,3,4,5)),
BuildingCode varchar (10))

create table BUILDING (
BuildingCode varchar(10),
BuildingName varchar (50))

create table COURSE(
CourseCode varchar(10),
CourseDescription varchar (50),
CreditUnits int,
ProgramCode varchar (10))

create table PROGRAM (
ProgramCode varchar (10),
ProgramDescription varchar (50),
CollegeCode varchar (10))

create table COLLEGE (
CollegeCode varchar (10),
CollegeDescription varchar (50))

insert into [USER] (UserID, Lname, Fname, Mname, Street, Barangay, City, Province, PostalCode, Birthday, Gender, ProgramCode, UserType)
values (101, 'Sabulao','Josh Beckamlee','Perona','1225','Zone 4','San Jose Del Monte','Bulacan', 3000 , '06-03-2007','F','BSIT','Student'),
       (102, 'Caparas', 'Kyle Andrei', 'Villafuerte', '100', 'Tiaong', 'Guiguinto', 'Bulacan', 3000, '09-17-2007', 'M', 'BSIT', 'Admin' ),
       (103, 'Rural', 'Elijah', 'Masarap', '050', 'Bulihan', 'Malolos', 'Bulacan', 3000, '04-22-2006', 'F', 'BSIT', 'Faculty'),
       (104, 'Banot','Josh Roden','Cute','1235','Zone 5','San Jose Del Monte','Bulacan', 3000 , '06-04-2007','F','BSED','Student'),
       (105, 'Cruz', 'Cris John', 'Cruz', '110', 'Tiaong', 'Guiguinto', 'Bulacan', 3000, '09-18-2007', 'M', 'BSED', 'Admin' ),
       (106, 'Mercado', 'John Mark', 'Masarap', '050', 'San Pablo', 'Malolos', 'Bulacan', 3000, '04-21-2006', 'F', 'BSED', 'Faculty'),
       (107, 'Velasco','Leo','Reyes','1325','Zone 6','San Jose Del Monte','Bulacan', 3000 , '06-09-2007','F','BSBA','Student'),
       (108, 'Honda', 'Kawasaki', 'Mitsubishi', '200', 'Plaridel', 'Guiguinto', 'Bulacan', 3000, '09-20-2007', 'M', 'BSBA', 'Admin' ),
       (109, 'Verity', 'Minecraft', 'Masarap', '070', 'Tikay', 'Malolos', 'Bulacan', 3000, '04-30-2006', 'F', 'BSBA', 'Faculty')

insert into STUDENT (StudentNumber, YearLevel)
values (101, 2),
       (104, 3),
       (107, 4)

insert into [ADMIN] (AdminID, [Role])
values (102, 'Main Character'),
       (105, 'Side Character'),
       (108, 'Model')

insert into FACULTY (ProfessorID, ContactNumber, EmailAdress, CollegeCode)
values (103, '09676767676' , 'ElijahMasarapUwU@gmail.com', 'CICT'),
       (106, '09165038628' , 'ZenStaria17@gmail.com', 'COED'),
       (109, '09564032344', 'TheManWhoCantMove@gmail.com', 'CBEA')

insert into SECTION (Section_ID, YearLevel, [Group], RoomCode, CourseCode, ProfessorID)
values ('2B', 2, 2, '101', 'IT104', 103),
       ('3A', 3, null, '203', 'ED107', 106),
       ('4C', 4, null, '401', 'BA110', 109)

insert into ENROLLMENT (EnrollmentID, Section_ID, StudentNumber)
values (201, '2B', 101),
       (302, '3A', 104),
       (403, '4C', 107)

insert into SCHEDULE (ScheduleID, [Day], StartTime, EndTime, SectionID, RoomCode)
values (1, 'Monday', '7:00AM', '10:00AM', '2B', '101'),
       (2, 'Tuesday', '10:00AM', '1:00PM', '3A', '203'),
       (3, 'Wednesday', '1:00PM', '3:00PM', '4C', '401')

insert into ROOM (RoomCode, RoomDescription, FloorNumber, BuildingCode)
values (101, 'Laboratory', 1, 'PM1'),
       (203, 'Lecture', 2, 'R1'),
       (401, 'Lecture', 4, 'CB2')

insert into BUILDING (BuildingCode, BuildingName)
values ('PM1', 'Pimentel Hall'),
       ('R1', 'Roxas Hall'),
       ('CB2', 'Bea Hall')

insert into COURSE (CourseCode, CourseDescription, CreditUnits, ProgramCode)
values ('IT104', 'Information Management', 3, 'BSIT'),
       ('ED107', 'Education Something', 3, 'BSED'),
       ('BA110', 'Business Something', 3, 'BSBA')

insert into PROGRAM (ProgramCode, ProgramDescription, CollegeCode)
values ('BSIT', 'Bachelor of Science in Information Technology', 'CICT'),
       ('BSED', 'Bachelor of Secondary Education', 'COED'),
       ('BSBA', 'Bachelor of Science in Business Administration', 'CBEA')

insert into COLLEGE (CollegeCode, CollegeDescription)
values ('CICT' , 'College of Information Communication Technology'),
       ('COED' , 'College of Education'),
       ('CBEA' , 'College of Business Economics Accountancy')


select * from [USER]
select * from STUDENT
select * from [ADMIN]
select * from FACULTY
select * from SECTION
select * from ENROLLMENT
select * from SCHEDULE
select * from ROOM
select * from BUILDING
select * from COURSE
select * from PROGRAM
select * from COLLEGE

drop table  [USER]
drop table STUDENT
drop table [ADMIN]
drop table  FACULTY
drop table  SECTION
drop table  ENROLLMENT
drop table  SCHEDULE
drop table ROOM
drop table  BUILDING
drop table COURSE
drop table PROGRAM
drop table COLLEGE
