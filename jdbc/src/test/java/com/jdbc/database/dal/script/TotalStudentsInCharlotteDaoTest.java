package com.jdbc.database.dal.script;

import com.jdbc.database.dal.script.TotalStudentsInCharlotteDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class TotalStudentsInCharlotteDaoTest {
  public static final int TOTAL_STUDENTS_IN_CHARLOTTE_TEST = 38;
  public static final int TOTAL_STUDENTS_IN_CHARLOTTE_FROM_PLACEHOLDER = 2;

  TotalStudentsInCharlotteDao totalStudentsInCharlotteDao;

  @Before
  public void init() throws SQLException {
    totalStudentsInCharlotteDao = TotalStudentsInCharlotteDao.getInstance();
  }

  @Test
  public void getTotalStudentsInCharlotteFromPrivateDatabaseTest() throws SQLException {
    Assert.assertTrue(totalStudentsInCharlotteDao.getTotalStudentsInCharlotteFromPrivateDatabase()
            == TOTAL_STUDENTS_IN_CHARLOTTE_FROM_PLACEHOLDER);
  }

  @Test
  public void updateTotalStudentsInCharlotteInPublicDatabaseTest() throws SQLException {
    totalStudentsInCharlotteDao.updateTotalStudentsInCharlotteInPublicDatabase(
            TOTAL_STUDENTS_IN_CHARLOTTE_TEST);
    Assert.assertTrue(totalStudentsInCharlotteDao.getTotalStudentsInCharlotteFromPublicDatabase()
            == TOTAL_STUDENTS_IN_CHARLOTTE_TEST);
    totalStudentsInCharlotteDao.updateTotalStudentsInCharlotteInPublicDatabase(
            TOTAL_STUDENTS_IN_CHARLOTTE_FROM_PLACEHOLDER);
    Assert.assertTrue(totalStudentsInCharlotteDao.getTotalStudentsInCharlotteFromPublicDatabase() ==
            TOTAL_STUDENTS_IN_CHARLOTTE_FROM_PLACEHOLDER);
  }
}