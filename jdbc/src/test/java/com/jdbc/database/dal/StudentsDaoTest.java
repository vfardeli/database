package com.jdbc.database.dal;

import com.jdbc.database.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StudentsDaoTest {
    public static final int COUNT_OF_MALE = 5;
    private static final int COUNT_OF_FEMALE = 5;

    private StudentsDao studentdao;

    @Before
    public void init() throws SQLException {
        studentdao = studentdao.getInstance();
    }

    @Test
    public void getMaleStudentCountTest() throws Exception {
        Assert.assertTrue(studentdao.getMaleStudentCount() == COUNT_OF_MALE);
    }

    @Test
    public void getFemaleStudentCountTest() throws Exception {
        Assert.assertTrue(studentdao.getFemaleStudentCount() == COUNT_OF_FEMALE);
    }

    @Test
    public void searchStudentByIdTest() throws Exception {
        Students student = studentdao.searchStudentById("111234544");
        Assert.assertTrue(student.getFirstName().equals("James"));
        Assert.assertTrue(student.getLastName().equals("Marks"));
        Students student2 = studentdao.searchStudentById("1");
        Assert.assertTrue(student2 == null);
    }

    @Test
    public void getAllStudentsTest() throws Exception {
        List<Students> students = studentdao.getAllStudents();
        Assert.assertTrue(students.size() == 10);
    }

    @Test
    public void addStudentTest() throws Exception {
        Students student = studentdao.searchStudentById("0000000");
        Assert.assertTrue(student == null);

        Students newStudent = new Students("0000000", "tomcat@gmail.com", "Tom", "",
                "Cat", Gender.M, true, true, 22, "1111111111",
                "401 Terry Ave", "WA", "98109", EnrollmentStatus.FULL_TIME, Campus.SEATTLE,
                DegreeCandidacy.MASTERS, null);

        boolean insert = studentdao.addStudent(newStudent);
        Assert.assertTrue(insert);

        student = studentdao.searchStudentById("0000000");
        Assert.assertTrue(student.getFirstName().equals("Tom"));

        studentdao.deleteStudent(newStudent);
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Students newStudent = new Students("0000000", "tomcat@gmail.com", "Tom", "",
                "Cat", Gender.M, true, true, 22, "1111111111",
                "401 Terry Ave", "WA", "98109", EnrollmentStatus.FULL_TIME, Campus.SEATTLE,
                DegreeCandidacy.MASTERS, null);

        studentdao.addStudent(newStudent);
        boolean delete = studentdao.deleteStudent(newStudent);
        Assert.assertTrue(delete);
        newStudent = studentdao.searchStudentById("0000000");
        Assert.assertTrue(newStudent==null);
    }

    @Test
    public void searchStudentByFirstNameTest() throws Exception {
        List<Students> students = studentdao.searchStudentByFirstName("James");
        for (Students student : students) {
            Assert.assertTrue(student.getFirstName().equals("James"));
        }
    }

    @Test
    public void updateStudentAddressTest() throws Exception {
        Students newStudent = new Students("000000", "tomcat@gmail.com", "Tom", "",
                "Cat", Gender.M, true, true, 22, "1111111111",
                "401 Terry Ave", "WA", "98109", EnrollmentStatus.FULL_TIME, Campus.SEATTLE,
                DegreeCandidacy.MASTERS, null);

        studentdao.addStudent(newStudent);

        newStudent.setAddress("402 Terry Ave");
        studentdao.updateStudentAddress(newStudent);

        Assert.assertTrue(studentdao.searchStudentById("000000").getAddress().equals("402 Terry Ave"));
        studentdao.deleteStudent(newStudent);
    }

    @Test
    public void searchSimilarStudents() throws Exception {
        List<Students> students = studentdao.searchSimilarStudents(DegreeCandidacy.MASTERS);
        for (Students student : students) {
            Assert.assertTrue(student.getDegree().name().equals("MASTERS"));
        }
    }
}
