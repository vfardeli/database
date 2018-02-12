package com.jdbc.database.dal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalStudentsInBostonDaoTest {
  public static final int TOTAL_STUDENTS_IN_BOSTON_TEST = 36;
  public static final int TOTAL_STUDENTS_IN_BOSTON_FROM_PLACEHOLDER = 1;

  TotalStudentsInBostonDao totalStudentsInBostonDao;

  private int getTotalStudentsInBostonFromPublicDatabase() throws SQLException {
    Connection publicConnection = null;
    ConnectionManager publicConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPublic");

    String getTotalStudentsInBostonFromPublicDatabase =
            "SELECT TotalStudents FROM TotalStudentsInBoston;";
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      selectStatement = publicConnection.prepareStatement(getTotalStudentsInBostonFromPublicDatabase);
      results = selectStatement.executeQuery();
      if (results.next()) {
        int totalStudentsInBoston = results.getInt("TotalStudents");
        return totalStudentsInBoston;
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw exception;
    } finally {
      if (publicConnection != null) {
        publicConnection.close();
      }
      if (selectStatement != null) {
        selectStatement.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return -1;
  }

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
    Assert.assertTrue(getTotalStudentsInBostonFromPublicDatabase()
            == TOTAL_STUDENTS_IN_BOSTON_TEST);
    totalStudentsInBostonDao.updateTotalStudentsInBostonInPublicDatabase(
            TOTAL_STUDENTS_IN_BOSTON_FROM_PLACEHOLDER);
    Assert.assertTrue(getTotalStudentsInBostonFromPublicDatabase() ==
            TOTAL_STUDENTS_IN_BOSTON_FROM_PLACEHOLDER);
  }

}