# Before running, create the schema named AlignPrivate.
# Or just create schema if necessary.
CREATE SCHEMA IF NOT EXISTS AlignPublic;
USE AlignPublic;

DROP TABLE IF EXISTS TotalStudentsInBoston;
DROP TABLE IF EXISTS TotalStudentsInCharlotte;
DROP TABLE IF EXISTS TotalStudentsInSiliconValley;
DROP TABLE IF EXISTS TopTenBachelorsDegree;
DROP TABLE IF EXISTS TopFiveEmployers;
DROP TABLE IF EXISTS TopFiveElectives;

CREATE TABLE TotalStudentsInBoston (
	TotalStudentsInBostonId INT AUTO_INCREMENT,
    TotalStudents INT DEFAULT 0,
    CONSTRAINT pk_TotalStudentsInBoston_TotalStudentsInBostonId
		PRIMARY KEY (TotalStudentsInBostonId)
);

INSERT INTO TotalStudentsInBoston() VALUES ();

CREATE TABLE TotalStudentsInCharlotte (
	TotalStudentsInCharlotteId INT AUTO_INCREMENT,
    TotalStudents INT DEFAULT 0,
    CONSTRAINT pk_TotalStudentsInCharlotte_TotalStudentsInCharlotteId
		PRIMARY KEY (TotalStudentsInCharlotteId)
);

INSERT INTO TotalStudentsInCharlotte() VALUES ();

CREATE TABLE TotalStudentsInSiliconValley (
	TotalStudentsInSiliconValleyId INT AUTO_INCREMENT,
    TotalStudents INT DEFAULT 0,
    CONSTRAINT pk_TotalStudentsInSiliconValley_TotalStudentsInSiliconValleyId
		PRIMARY KEY (TotalStudentsInSiliconValleyId)
);

INSERT INTO TotalStudentsInSiliconValley() VALUES ();

CREATE TABLE TopTenBachelorsDegree (
	TopTenBachelorsDegreeId INT AUTO_INCREMENT,
    BachelorsDegree VARCHAR(255) DEFAULT NULL,
    CONSTRAINT pk_TopTenBachelorsDegree_TopTenBachelorsDegreeId
		PRIMARY KEY (TopTenBachelorsDegreeId)
);

INSERT INTO TopTenBachelorsDegree() VALUES ();
INSERT INTO TopTenBachelorsDegree() VALUES ();
INSERT INTO TopTenBachelorsDegree() VALUES ();
INSERT INTO TopTenBachelorsDegree() VALUES ();
INSERT INTO TopTenBachelorsDegree() VALUES ();
INSERT INTO TopTenBachelorsDegree() VALUES ();
INSERT INTO TopTenBachelorsDegree() VALUES ();
INSERT INTO TopTenBachelorsDegree() VALUES ();
INSERT INTO TopTenBachelorsDegree() VALUES ();
INSERT INTO TopTenBachelorsDegree() VALUES ();

CREATE TABLE TopFiveEmployers (
	TopFiveEmployersId INT AUTO_INCREMENT,
    Employer VARCHAR(255) DEFAULT NULL,
    CONSTRAINT pk_TopFiveEmployers_TopFiveEmployersId
		PRIMARY KEY (TopFiveEmployersId)
);

INSERT INTO TopFiveEmployers() VALUES ();
INSERT INTO TopFiveEmployers() VALUES ();
INSERT INTO TopFiveEmployers() VALUES ();
INSERT INTO TopFiveEmployers() VALUES ();
INSERT INTO TopFiveEmployers() VALUES ();

CREATE TABLE TopFiveElectives (
	TopFiveElectivesId INT AUTO_INCREMENT,
    CourseName VARCHAR(255) DEFAULT NULL,
    CONSTRAINT pk_TopFiveElectives_TopFiveElectivesId
		PRIMARY KEY (TopFiveElectivesId)
);

INSERT INTO TopFiveElectives() VALUES ();
INSERT INTO TopFiveElectives() VALUES ();
INSERT INTO TopFiveElectives() VALUES ();
INSERT INTO TopFiveElectives() VALUES ();
INSERT INTO TopFiveElectives() VALUES ();
