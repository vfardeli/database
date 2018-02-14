package com.jdbc.database.dal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class TotalStudentsInBostonDaoTest {
  public static final int TOTAL_STUDENTS_IN_BOSTON_TEST = 36;
  public static final int TOTAL_STUDENTS_IN_BOSTON_FROM_PLACEHOLDER = 1;

  TotalStudentsInBostonDao totalStudentsInBostonDao;

  @Before
  public void init() throws SQLException {
    totalStudentsInBostonDao = TotalStudentsInBostonDao.getInstance();
  }

  @Test
  public void getTotalStudentsInBostonFromPrivateDatabaseTest() throws SQLException {
    Assert.assertTrue(totalStudentsInBostonDao.getTotalStudentsInBostonFromPrivateDatabase()
            == TOTAL_STUDENTS_IN_BOSTON_FROM_PLACEHOLDER);
  }

  @Test
  public void updateTotalStudentsInBostonInPublicDatabaseTest() throws SQLException {
    totalStudentsInBostonDao.updateTotalStudentsInBostonInPublicDatabase(
            TOTAL_STUDENTS_IN_BOSTON_TEST);
    Assert.assertTrue(totalStudentsInBostonDao.getTotalStudentsInBostonFromPublicDatabase()
            == TOTAL_STUDENTS_IN_BOSTON_TEST);
    totalStudentsInBostonDao.updateTotalStudentsInBostonInPublicDatabase(
            TOTAL_STUDENTS_IN_BOSTON_FROM_PLACEHOLDER);
    Assert.assertTrue(totalStudentsInBostonDao.getTotalStudentsInBostonFromPublicDatabase() ==
            TOTAL_STUDENTS_IN_BOSTON_FROM_PLACEHOLDER);
  }

}