package com.jdbc.database.dal.script;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TotalStudentsInGoogleDaoTest {
    public static final int TOTAL_GOOGLE_STUDENTS_TEST = 10;

    TotalStudentsInGoogleDao totalGoogleStudentsDao;

    @Before
    public void init() throws Exception {
        totalGoogleStudentsDao = TotalStudentsInGoogleDao.getInstance();
    }

    @Test
    public void getTotalStudentsFromPrivateDB() throws Exception {
        int totalStudents = totalGoogleStudentsDao.getTotalStudentsFromPrivateDB();
        Assert.assertTrue(totalStudents == 2);
    }

    @Test
    public void updateTotalExperiencedStudents() throws Exception {
        int totalGoogleStudents = totalGoogleStudentsDao.getTotalStudentsInGoogle();

        totalGoogleStudentsDao.updateTotalStudentsInGoogle(TOTAL_GOOGLE_STUDENTS_TEST);
        Assert.assertTrue(totalGoogleStudentsDao.getTotalStudentsInGoogle() ==
                TOTAL_GOOGLE_STUDENTS_TEST);

        totalGoogleStudentsDao.updateTotalStudentsInGoogle(totalGoogleStudents);
        Assert.assertTrue(totalGoogleStudentsDao.getTotalStudentsInGoogle() ==
                totalGoogleStudents);
    }
}
