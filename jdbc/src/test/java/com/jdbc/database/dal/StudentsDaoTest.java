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

    StudentsDao studentdao;

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
        Students newStudent = new Students("0000000", "tomcat@gmail.com", "Tom", "",
                "Cat", Gender.M, true, true, 22, "1111111111",
                "401 Terry Ave", "WA", "98109", EnrollmentStatus.FULL_TIME, Campus.SEATTLE,
                DegreeCandidacy.MASTERS, null);

        boolean insert = studentdao.addStudent(newStudent);
        Assert.assertTrue(insert);

        Students student = studentdao.searchStudentById("0000000");
        Assert.assertTrue(student.getFirstName().equals("Tom"));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Students student = new Students("0000000", "tomcat@gmail.com", "Tom", "",
                "Cat", Gender.M, true, true, 22, "1111111111",
                "401 Terry Ave", "WA", "98109", EnrollmentStatus.FULL_TIME, Campus.SEATTLE,
                DegreeCandidacy.MASTERS, null);

        boolean delete = studentdao.deleteStudent(student);
        Assert.assertTrue(delete);
        student = studentdao.searchStudentById("0000000");
        Assert.assertTrue(student==null);
    }
}