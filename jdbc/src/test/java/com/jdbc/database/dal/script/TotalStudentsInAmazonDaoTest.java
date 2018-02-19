package com.jdbc.database.dal.script;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TotalStudentsInAmazonDaoTest {

    public static final int TOTAL_AMAZON_STUDENTS_TEST = 10;

    TotalStudentsInAmazonDao totalAmazonStudentsDao;

    @Before
    public void init() throws Exception {
        totalAmazonStudentsDao = TotalStudentsInAmazonDao.getInstance();
    }

    @Test
    public void getTotalStudentsFromPrivateDB() throws Exception {
        int totalStudents = totalAmazonStudentsDao.getTotalStudentsFromPrivateDB();
        Assert.assertTrue(totalStudents == 1);
    }

    @Test
    public void updateTotalExperiencedStudents() throws Exception {
        int totalAmazonStudents = totalAmazonStudentsDao.getTotalStudentsInAmazon();

        totalAmazonStudentsDao.updateTotalStudentsInAmazon(TOTAL_AMAZON_STUDENTS_TEST);
        Assert.assertTrue(totalAmazonStudentsDao.getTotalStudentsInAmazon() ==
                TOTAL_AMAZON_STUDENTS_TEST);

        totalAmazonStudentsDao.updateTotalStudentsInAmazon(totalAmazonStudents);
        Assert.assertTrue(totalAmazonStudentsDao.getTotalStudentsInAmazon() ==
                totalAmazonStudents);
    }
}