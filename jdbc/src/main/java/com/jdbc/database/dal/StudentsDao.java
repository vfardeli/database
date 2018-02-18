package com.jdbc.database.dal;

import com.jdbc.database.Constants;
import com.jdbc.database.dal.script.PrivateDatabaseEtlQuery;
import com.jdbc.database.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentsDao {
    private ConnectionManager connectionManager;

    // Singleton pattern
    private static StudentsDao instance = null;

    public static StudentsDao getInstance() {
        if (instance == null) {
            instance = new StudentsDao();
        }

        return instance;
    }

    private StudentsDao() {
        connectionManager = new ConnectionManager(
                "root",
                "",
                "localhost",
                3306,
                "AlignPrivate");
    }

    /**
     * Get total number of male students.
     * This is a function to extract the total number of male students.
     *
     * @return Total Number of Male Students
     * @throws SQLException
     */
    public int getMaleStudentCount() throws SQLException {
        String getTotalMaleStudents =
                "SELECT COUNT(*) AS COUNT_OF_MALE " +
                        "FROM Students " +
                        "WHERE Gender = \"M\";";
        PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
        return privateDatabaseEtlQuery.getSingleValueQuery(
                getTotalMaleStudents,
                "COUNT_OF_MALE");
    }

    /**
     * Get total number of female students.
     * This is a function to extract the total number of male students.
     *
     * @return Total Number of Female Students
     * @throws SQLException
     */
    public int getFemaleStudentCount() throws SQLException {
        String getTotalFemaleStudents =
                "SELECT COUNT(*) AS COUNT_OF_FEMALE " +
                        "FROM Students " +
                        "WHERE Gender = \"F\";";
        PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
        return privateDatabaseEtlQuery.getSingleValueQuery(
                getTotalFemaleStudents,
                "COUNT_OF_FEMALE");
    }

    /**
     * This is the function to extract a specific student by neu id.
     *
     * @param neuId
     * @return a specific student. Return null if not exist.
     * @throws SQLException
     */
    public Students searchStudentById(String neuId) throws SQLException {
        String getSingleStudent =
                "SELECT * FROM Students WHERE NeuId = ?";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connectionManager.connect();
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(getSingleStudent);
            selectStmt.setString(1, neuId);

            results = selectStmt.executeQuery();

            if(results.next()) {
                String email = results.getString("Email");
                String firstName = results.getString("FirstName");
                String middleName = results.getString("MiddleName");
                String lastName = results.getString("LastName");
                Gender gender = Gender.valueOf(results.getString("Gender"));
                boolean scholarship = results.getBoolean("Scholarship");
                boolean f1Visa = results.getBoolean("F1Visa");
                int age = results.getInt("Age");
                String phoneNum = results.getString("Phone");
                String address = results.getString("Address");
                String state = results.getString("State");
                String zip = results.getString("Zip");
                EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(
                        results.getString("EnrollmentStatus"));
                Campus campus = Campus.valueOf(results.getString("Campus"));
                DegreeCandidacy degree = DegreeCandidacy.valueOf(results.getString("DegreeCandidacy"));
                Blob photo = results.getBlob("Photo");

                Students student = new Students(neuId, email, firstName, middleName, lastName, gender, scholarship,
                        f1Visa, age, phoneNum, address, state, zip, enrollmentStatus, campus, degree, photo);

                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return null;
    }

    /**
     * This is the function to return all students as a list.
     *
     * @return A list of all students.
     * @throws SQLException
     */
    public List<Students> getAllStudents() throws SQLException {
        List<Students> students = new ArrayList<>();
        String getAllStudent =
                "SELECT * FROM Students;";

        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connectionManager.connect();
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(getAllStudent);

            results = selectStmt.executeQuery();

            while(results.next()) {
                String neuId = results.getString("NeuId");
                String email = results.getString("Email");
                String firstName = results.getString("FirstName");
                String middleName = results.getString("MiddleName");
                String lastName = results.getString("LastName");
                Gender gender = Gender.valueOf(results.getString("Gender"));
                boolean scholarship = results.getBoolean("Scholarship");
                boolean f1Visa = results.getBoolean("F1Visa");
                int age = results.getInt("Age");
                String phoneNum = results.getString("Phone");
                String address = results.getString("Address");
                String state = results.getString("State");
                String zip = results.getString("Zip");
                EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(
                        results.getString("EnrollmentStatus"));
                Campus campus = Campus.valueOf(results.getString("Campus"));
                DegreeCandidacy degree = DegreeCandidacy.valueOf(results.getString("DegreeCandidacy"));
                Blob photo = results.getBlob("Photo");

                Students student = new Students(neuId, email, firstName, middleName, lastName, gender, scholarship,
                        f1Visa, age, phoneNum, address, state, zip, enrollmentStatus, campus, degree, photo);

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }

        return students;
    }

    /**
     * This is the function to add a student into database.
     * @param student
     * @return true if insert successfully. Otherwise throws exception.
     * @throws Exception
     */
    public boolean addStudent(Students student) throws SQLException {
        String insertStudent =
                "INSERT INTO Students(NeuId,Email,FirstName,MiddleName,LastName,Gender,Scholarship,F1Visa,Age,Phone,"
                        + "Address,State,Zip,EnrollmentStatus,Campus,DegreeCandidacy,Photo)"
                        +" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connectionManager.connect();
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertStudent);
            insertStmt.setString(1, student.getNeuId());
            insertStmt.setString(2, student.getEmail());
            insertStmt.setString(3, student.getFirstName());
            insertStmt.setString(4, student.getMiddleName());
            insertStmt.setString(5, student.getLastName());
            insertStmt.setString(6, student.getGender().name());
            insertStmt.setBoolean(7, student.isScholarship());
            insertStmt.setBoolean(8, student.isF1Visa());
            insertStmt.setInt(9, student.getAge());
            insertStmt.setString(10, student.getPhoneNum());
            insertStmt.setString(11, student.getAddress());
            insertStmt.setString(12, student.getState());
            insertStmt.setString(13, student.getZip());
            insertStmt.setString(14, student.getEnrollmentStatus().name());
            insertStmt.setString(15, student.getCampus().name());
            insertStmt.setString(16, student.getDegree().name());
            insertStmt.setBlob(17, student.getPhoto());
            insertStmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
        }
    }

    /**
     * Delete a student from database.
     * @param student
     * @return true if delete succefully. Otherwise throws exception.
     * @throws SQLException
     */
    public boolean deleteStudent(Students student) throws SQLException {
        String deleteStudent = "DELETE FROM Students WHERE NeuId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connectionManager.connect();
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteStudent);
            deleteStmt.setString(1, student.getNeuId());
            deleteStmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }

    /**
     * This is the function to search a list of students by the first name.
     *
     * @param firstName
     * @return A list of students with the same first name.
     * @throws Exception
     */
    public List<Students> searchStudentByFirstName(String firstName) throws Exception {
        List<Students> students = new ArrayList<>();
        String searchStudent =
                "SELECT * FROM Students WHERE FirstName = ?;";

        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connectionManager.connect();
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(searchStudent);
            selectStmt.setString(1, firstName);

            results = selectStmt.executeQuery();

            while(results.next()) {
                String neuId = results.getString("NeuId");
                String email = results.getString("Email");
                String middleName = results.getString("MiddleName");
                String lastName = results.getString("LastName");
                Gender gender = Gender.valueOf(results.getString("Gender"));
                boolean scholarship = results.getBoolean("Scholarship");
                boolean f1Visa = results.getBoolean("F1Visa");
                int age = results.getInt("Age");
                String phoneNum = results.getString("Phone");
                String address = results.getString("Address");
                String state = results.getString("State");
                String zip = results.getString("Zip");
                EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(
                        results.getString("EnrollmentStatus"));
                Campus campus = Campus.valueOf(results.getString("Campus"));
                DegreeCandidacy degree = DegreeCandidacy.valueOf(results.getString("DegreeCandidacy"));
                Blob photo = results.getBlob("Photo");

                Students student = new Students(neuId, email, firstName, middleName, lastName, gender, scholarship,
                        f1Visa, age, phoneNum, address, state, zip, enrollmentStatus, campus, degree, photo);

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }

        return students;
    }

//    public List<Students> getSimilarStudents() throws SQLException {
//
//    }
}
