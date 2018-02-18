# Before running, create the schema named AlignPrivate.
# Or just create schema if necessary.
CREATE SCHEMA IF NOT EXISTS AlignPrivate;
USE AlignPrivate;

DROP TABLE IF EXISTS Privacies;
DROP TABLE IF EXISTS PriorEducations;
DROP TABLE IF EXISTS WorkExperiences;
DROP TABLE IF EXISTS EntryTerms;
DROP TABLE IF EXISTS ExpectedLastTerms;
DROP TABLE IF EXISTS CurrentMajors;
DROP TABLE IF EXISTS AdministratorNotes;
DROP TABLE IF EXISTS Electives;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Terms;
DROP TABLE IF EXISTS Institutions;
DROP TABLE IF EXISTS Companies;
DROP TABLE IF EXISTS Majors;
DROP TABLE IF EXISTS Administrators;
DROP TABLE IF EXISTS Experiences;
DROP TABLE IF EXISTS Students;

CREATE TABLE Students (
	NeuId VARCHAR(16) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    FirstName VARCHAR(25) NOT NULL,
    MiddleName VARCHAR(25),
    LastName VARCHAR(25) NOT NULL,
    Gender ENUM ('M', 'F') NOT NULL,
    Scholarship BOOLEAN DEFAULT FALSE,
    F1Visa BOOLEAN,
    Age INT,
    Phone VARCHAR(25) NOT NULL,
	Address VARCHAR(255) NOT NULL,
    State VARCHAR(2) NOT NULL,
    Zip VARCHAR(5) NOT NULL,
    EnrollmentStatus ENUM('FULL_TIME', 'PART_TIME', 'GRADUATED', 'INACTIVE', 'DROPPED_OUT') NOT NULL,
    Campus ENUM('BOSTON', 'CHARLOTTE', 'SEATTLE', 'SILICON_VALLEY') NOT NULL,
    DegreeCandidacy ENUM('ASSOCIATE', 'BACHELORS', 'MASTERS', 'PHD') NOT NULL,
    Photo BLOB,
    CONSTRAINT pk_Students_NeuId
		PRIMARY KEY (NeuId),
	CONSTRAINT uq_Students_Email
		UNIQUE (Email)
);

CREATE TABLE Institutions (
	InstitutionId INT AUTO_INCREMENT,
    InstitutionName VARCHAR(255),
    CONSTRAINT pk_Institutions_InstitutionId
		PRIMARY KEY (InstitutionId),
	CONSTRAINT uq_Institutions_Institution
		UNIQUE (InstitutionName)
);

CREATE TABLE Majors (
	MajorId INT AUTO_INCREMENT,
    Major VARCHAR(255),
    CONSTRAINT pk_Majors_MajorId
		PRIMARY KEY (MajorId),
	CONSTRAINT uq_Majors_Major
		UNIQUE (Major)
);

CREATE TABLE CurrentMajors (
	CurrentMajorId INT AUTO_INCREMENT,
    NeuId VARCHAR(16),
    MajorId INT,
    CONSTRAINT pk_CurrentMajors_CurrentMajorId
		PRIMARY KEY (CurrentMajorId),
	CONSTRAINT uq_CurrentMajors_CurrentMajor
		UNIQUE (NeuId, MajorId),
	CONSTRAINT fk_CurrentMajors_NeuId
		FOREIGN KEY (NeuId)
        REFERENCES Students(NeuId)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_CurrentMajors_MajorId
		FOREIGN KEY (MajorId)
        REFERENCES Majors(MajorId)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE PriorEducations (
	PriorEducationId INT AUTO_INCREMENT,
    NeuId VARCHAR(16),
    InstitutionId INT,
    MajorId INT,
    GraduationDate DATE NOT NULL,
	Gpa FLOAT(4, 2) DEFAULT 0.00,
    DegreeCandidacy ENUM('ASSOCIATE', 'BACHELORS', 'MASTERS', 'PHD') NOT NULL,
    CONSTRAINT pk_PriorEducations_PriorEducationId
		PRIMARY KEY (PriorEducationId),
	CONSTRAINT uq_PriorEducations_PriorEducation
		UNIQUE (NeuId, InstitutionId, MajorId),
	CONSTRAINT fk_PriorEducations_NeuId
		FOREIGN KEY (NeuId)
		REFERENCES Students(NeuId)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_PriorEducations_InstitutionId
		FOREIGN KEY (InstitutionId)
		REFERENCES Institutions(InstitutionId)
        ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT fk_PriorEducations_MajorId
		FOREIGN KEY (MajorId)
		REFERENCES Majors(MajorId)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Courses (
	CourseId VARCHAR(6) NOT NULL,
    CourseName VARCHAR(1250),
    Description VARCHAR(1250),
    CONSTRAINT pk_Courses_CourseId
		PRIMARY KEY (CourseId)
);

CREATE TABLE Terms (
	TermId INT AUTO_INCREMENT,
    Term ENUM ('WINTER', 'SPRING', 'SUMMER', 'FALL') NOT NULL,
    TermYear INT NOT NULL,
    TermType ENUM('QUARTER', 'SEMESTER') NOT NULL,
    CONSTRAINT pk_Terms_TermId
		PRIMARY KEY (TermId),
	CONSTRAINT uq_Terms_Term
		UNIQUE (Term, TermYear, TermType)
);

CREATE TABLE EntryTerms (
	EntryTermId INT AUTO_INCREMENT,
    NeuId VARCHAR(16),
    TermId INT,
    CONSTRAINT pk_EntryTerms_EntryTermId
		PRIMARY KEY (EntryTermId),
	CONSTRAINT uq_EntryTerms_EntryTerm
		UNIQUE (NeuId, TermId),
	CONSTRAINT fk_EntryTerms_NeuId
		FOREIGN KEY (NeuId)
        REFERENCES Students(NeuId)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_EntryTerms_TermId
		FOREIGN KEY (TermId)
        REFERENCES Terms(TermId)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE ExpectedLastTerms (
	ExpectedLastTermId INT AUTO_INCREMENT,
    NeuId VARCHAR(16),
    TermId INT,
    CONSTRAINT pk_ExpectedLastTerms_ExpectedLastTermId
		PRIMARY KEY (ExpectedLastTermId),
	CONSTRAINT uq_ExpectedLastTerms_ExpectedLastTerm
		UNIQUE (NeuId, TermId),
	CONSTRAINT fk_ExpectedLastTerms_NeuId
		FOREIGN KEY (NeuId)
        REFERENCES Students(NeuId)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_ExpectedLastTerms_TermId
		FOREIGN KEY (TermId)
        REFERENCES Terms(TermId)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Electives (
	ElectiveId INT AUTO_INCREMENT,
    NeuId VARCHAR(16),
    CourseId VARCHAR(6),
    TermId INT,
    Retake BOOLEAN DEFAULT FALSE,
	Gpa FLOAT(6, 4) DEFAULT 0.0000,
    Plagiarism BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_Electives_ElectiveId
		PRIMARY KEY (ElectiveId),
	CONSTRAINT uq_Electives_Elective
		UNIQUE (NeuId, CourseId, TermId),
	CONSTRAINT fk_Electives_NeuId
		FOREIGN KEY (NeuId)
		REFERENCES Students(NeuId)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_Electives_CourseId
		FOREIGN KEY (CourseId)
		REFERENCES Courses(CourseId)
        ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT fk_Electives_TermId
		FOREIGN KEY (TermId)
		REFERENCES Terms(TermId)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Experiences (
	ExperienceId INT AUTO_INCREMENT,
    NeuId VARCHAR(16),
    Title VARCHAR(255),
    Description VARCHAR(1250),
    CONSTRAINT pk_Experiences_ExperienceId
		PRIMARY KEY (ExperienceId),
	CONSTRAINT fk_Experiences_NeuId
		FOREIGN KEY (NeuId)
        REFERENCES Students(NeuId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Companies (
	CompanyId INT AUTO_INCREMENT,
    CompanyName VARCHAR(50),
    CONSTRAINT pk_Companies_CompanyId
		PRIMARY KEY (CompanyId),
	CONSTRAINT uq_Companies_Company
		UNIQUE (CompanyName)
);

CREATE TABLE WorkExperiences (
	WorkExperienceId INT AUTO_INCREMENT,
    NeuId VARCHAR(16),
    CompanyId INT,
    StartDate DATE NOT NULL,
    EndDate DATE,
    CurrentJob BOOLEAN,
    Title VARCHAR(255) NOT NULL,
    Description VARCHAR(1250),
    CONSTRAINT pk_WorkExperiences_WorkExperienceId
		PRIMARY KEY (WorkExperienceId),
	CONSTRAINT uq_WorkExperiences_WorkExperience
		UNIQUE (NeuId, CompanyId, StartDate),
	CONSTRAINT fk_WorkExperiences_NeuId
		FOREIGN KEY (NeuId)
        REFERENCES Students(NeuId)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_WorkExperiences_CompanyId
		FOREIGN KEY (CompanyId)
        REFERENCES Companies(CompanyId)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Administrators (
	AdministratorNeuId VARCHAR(16),
    Email VARCHAR(255) NOT NULL,
    FirstName VARCHAR(25) NOT NULL,
    MiddleName VARCHAR(25),
    LastName VARCHAR(25) NOT NULL,
    CONSTRAINT pk_Administrators_AdministratorNeuId
		PRIMARY KEY (AdministratorNeuId),
	CONSTRAINT uq_Administrators_AdministratorEmail
		UNIQUE (Email)
);

CREATE TABLE AdministratorNotes (
	AdministratorNoteId INT AUTO_INCREMENT,
    NeuId VARCHAR(16),
    AdministratorNeuId VARCHAR(16),
    Title VARCHAR(255),
    Description VARCHAR(255),
    CONSTRAINT pk_AdministratorNotes_AdministratorNoteId
		PRIMARY KEY (AdministratorNoteId),
	CONSTRAINT fk_AdministratorNotes_NeuId
		FOREIGN KEY (NeuId)
        REFERENCES Students(NeuId)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_AdministratorNotes_AdministratorNeuId
		FOREIGN KEY (AdministratorNeuId)
        REFERENCES Administrators(AdministratorNeuId)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Privacies (
	PrivacyId INT AUTO_INCREMENT,
    NeuId VARCHAR(16),
    AgeShown BOOLEAN DEFAULT FALSE,
    NeuIdShown BOOLEAN DEFAULT TRUE,
    PhoneShown BOOLEAN DEFAULT FALSE,
    AddressShown BOOLEAN DEFAULT FALSE,
    StateShown BOOLEAN DEFAULT TRUE,
    ZipShown BOOLEAN DEFAULT FALSE,
    EmailShown BOOLEAN DEFAULT TRUE,
    EnrollmentStatusShown BOOLEAN DEFAULT TRUE,
    PriorEducationShown BOOLEAN DEFAULT TRUE,
    ExperienceShown BOOLEAN DEFAULT TRUE,
    WorkExperienceShown BOOLEAN DEFAULT TRUE,
    EntryTermShown BOOLEAN DEFAULT TRUE,
    ExpectedLastTermShown BOOLEAN DEFAULT TRUE,
    ScholarshipShown BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_Privacies_PrivacyId
		PRIMARY KEY (PrivacyId),
	CONSTRAINT uq_Privacies_Privacy
		UNIQUE (NeuId),
	CONSTRAINT fk_Privacies_NeuId
		FOREIGN KEY (NeuId)
        REFERENCES Students(NeuId)
        ON UPDATE CASCADE ON DELETE CASCADE
);
