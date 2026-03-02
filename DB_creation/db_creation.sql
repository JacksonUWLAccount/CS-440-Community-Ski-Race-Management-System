-- Drop database if exists community_ski_race_ms;
Create database if not exists community_ski_race_ms;
USE community_ski_race_ms;

-- DROP TABLE IF EXISTS Season;
-- DROP TABLE IF EXISTS Results;
-- DROP TABLE IF EXISTS Team;
-- DROP TABLE IF EXISTS Users;
-- DROP TABLE IF EXISTS Courses;
-- DROP TABLE IF EXISTS Races;


Create table Season(
	SeasonID int auto_increment primary key,
    SeasonName varchar(50),
    SeasonStart datetime,
    SeasonEnd datetime);
    
-- might want a roles table if we can classify skiers and coaches as both skiers and coaches    
 Create table Users(
	UserID int auto_increment primary key,
    Username varchar(50) unique,
    FirstName varchar(50),
    LastName varchar(50),
    Email varchar(100) unique,
    `Password` varbinary(32),
    Salt varbinary(16) unique,
    `Role` varchar(15));
 
Create table Team(
	TeamID int auto_increment primary key,
    TeamName varchar(50),
    CoachID int,
    Skier1ID int,
    Skier2ID int,
    foreign key (CoachID) References Users(UserID),
    foreign key (Skier1ID) References Users(UserID),
    foreign key (Skier2ID) References Users(UserID));
 
Create table Courses(
	CourseID int auto_increment primary key,
    CourseName varchar(50),
    CourseLocation varchar(50));

Create table Races(
	RaceID int auto_increment primary key,
	RaceName varchar(50),
	RaceStart time,
	RaceEnd time);

Create table Results(
	ResultsID int auto_increment primary key,
	RaceID int,
    UserID int,
    TeamID int,
    RaceTimes decimal(8,3),
    foreign key (RaceID) References Races(RaceID),
    foreign key (UserID) References Users(UserID),
    foreign key (TeamID) References Team(TeamID)
    );
 
 Select *
	from users;
    
