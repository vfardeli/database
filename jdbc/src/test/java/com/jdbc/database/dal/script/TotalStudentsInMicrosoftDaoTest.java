package com.jdbc.database.dal.script;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TotalStudentsInMicrosoftDaoTest {
    public static final int TOTAL_MICROSOFT_STUDENTS_TEST = 10;

    TotalStudentsInMicrosoftDao totalMicrosoftStudentsDao;

    @Before
    public void init() throws Exception {
        totalMicrosoftStudentsDao = TotalStudentsInMicrosoftDao.getInstance();
    }

    @Test
    public void getTotalStudentsFromPrivateDB() throws Exception {
        int totalStudents = totalMicrosoftStudentsDao.getTotalStudentsFromPrivateDB();
        Assert.assertTrue(totalStudents == 1);
    }

    @Test
    public void updateTotalExperiencedStudents() throws Exception {
        int totalMicrosoftStudents = totalMicrosoftStudentsDao.getTotalStudentsInMicrosoft();

        totalMicrosoftStudentsDao.updateTotalStudentsInMicrosoft(TOTAL_MICROSOFT_STUDENTS_TEST);
        Assert.assertTrue(totalMicrosoftStudentsDao.getTotalStudentsInMicrosoft() ==
                TOTAL_MICROSOFT_STUDENTS_TEST);

        totalMicrosoftStudentsDao.updateTotalStudentsInMicrosoft(totalMicrosoftStudents);
        Assert.assertTrue(totalMicrosoftStudentsDao.getTotalStudentsInMicrosoft() ==
                totalMicrosoftStudents);
    }
}
