package com.jdbc.database.dal.script;

import com.jdbc.database.dal.script.TotalStudentsInSiliconValleyDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class TotalStudentsInSiliconValleyDaoTest {
  public static final int TOTAL_STUDENTS_IN_SILICON_VALLEY_TEST = 70;
  public static final int TOTAL_STUDENTS_IN_SILICON_VALLEY_FROM_PLACEHOLDER = 3;

  TotalStudentsInSiliconValleyDao totalStudentsInSiliconValleyDao;

  @Before
  public void init() throws SQLException {
    totalStudentsInSiliconValleyDao = TotalStudentsInSiliconValleyDao.getInstance();
  }

  @Test
  public void getTotalStudentsInSiliconValleyFromPrivateDatabaseTest() throws SQLException {
    Assert.assertTrue(totalStudentsInSiliconValleyDao.getTotalStudentsInSiliconValleyFromPrivateDatabase()
            == TOTAL_STUDENTS_IN_SILICON_VALLEY_FROM_PLACEHOLDER);
  }

  @Test
  public void updateTotalStudentsInSiliconValleyInPublicDatabaseTest() throws SQLException {
    totalStudentsInSiliconValleyDao.updateTotalStudentsInSiliconValleyInPublicDatabase(
            TOTAL_STUDENTS_IN_SILICON_VALLEY_TEST);
    Assert.assertTrue(totalStudentsInSiliconValleyDao.getTotalStudentsInSiliconValleyFromPublicDatabase()
            == TOTAL_STUDENTS_IN_SILICON_VALLEY_TEST);
    totalStudentsInSiliconValleyDao.updateTotalStudentsInSiliconValleyInPublicDatabase(
            TOTAL_STUDENTS_IN_SILICON_VALLEY_FROM_PLACEHOLDER);
    Assert.assertTrue(totalStudentsInSiliconValleyDao.getTotalStudentsInSiliconValleyFromPublicDatabase() ==
            TOTAL_STUDENTS_IN_SILICON_VALLEY_FROM_PLACEHOLDER);
  }
}