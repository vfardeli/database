# Before running, create the schema named AlignPrivate.
# Or just create schema if necessary.
CREATE SCHEMA IF NOT EXISTS AlignPublic;
USE AlignPublic;

DROP TABLE IF EXISTS SingleValueAggregatedData;
DROP TABLE IF EXISTS TopTenBachelorsDegree;
DROP TABLE IF EXISTS TopFiveEmployers;
DROP TABLE IF EXISTS TopFiveElectives;

CREATE TABLE SingleValueAggregatedData (
	DataKey VARCHAR(255),
    DataValue INT DEFAULT -1,
    CONSTRAINT pk_SingleValueAggregatedData_DataKey
		PRIMARY KEY (DataKey)
);

INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalMaleStudents");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalFemaleStudents");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsWithWorkExperience");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsWhoWorkInFacebook");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsWhoWorkInGoogle");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsWhoWorkInAmazon");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsWhoWorkInMicrosoft");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsWithScholarship");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsInSeattle");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsInBoston");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsInCharlotte");
INSERT INTO SingleValueAggregatedData(DataKey)
	VALUES("TotalStudentsInSiliconValley");

CREATE TABLE TopTenBachelorsDegree (
	TopTenBachelorsDegreeId INT,
    BachelorsDegree VARCHAR(255) DEFAULT NULL,
    CONSTRAINT pk_TopTenBachelorsDegree_TopTenBachelorsDegreeId
		PRIMARY KEY (TopTenBachelorsDegreeId)
);

INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (1);
INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (2);
INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (3);
INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (4);
INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (5);
INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (6);
INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (7);
INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (8);
INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (9);
INSERT INTO TopTenBachelorsDegree(TopTenBachelorsDegreeId) VALUES (10);

CREATE TABLE TopFiveEmployers (
	TopFiveEmployersId INT,
    Employer VARCHAR(255) DEFAULT NULL,
    CONSTRAINT pk_TopFiveEmployers_TopFiveEmployersId
		PRIMARY KEY (TopFiveEmployersId)
);

INSERT INTO TopFiveEmployers(TopFiveEmployersId) VALUES (1);
INSERT INTO TopFiveEmployers(TopFiveEmployersId) VALUES (2);
INSERT INTO TopFiveEmployers(TopFiveEmployersId) VALUES (3);
INSERT INTO TopFiveEmployers(TopFiveEmployersId) VALUES (4);
INSERT INTO TopFiveEmployers(TopFiveEmployersId) VALUES (5);

CREATE TABLE TopFiveElectives (
	TopFiveElectivesId INT,
    CourseName VARCHAR(255) DEFAULT NULL,
    CONSTRAINT pk_TopFiveElectives_TopFiveElectivesId
		PRIMARY KEY (TopFiveElectivesId)
);

INSERT INTO TopFiveElectives(TopFiveElectivesId) VALUES (1);
INSERT INTO TopFiveElectives(TopFiveElectivesId) VALUES (2);
INSERT INTO TopFiveElectives(TopFiveElectivesId) VALUES (3);
INSERT INTO TopFiveElectives(TopFiveElectivesId) VALUES (4);
INSERT INTO TopFiveElectives(TopFiveElectivesId) VALUES (5);
