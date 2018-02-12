package com.jdbc.database.dal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalStudentsInCharlotteDaoTest {
  public static final int TOTAL_STUDENTS_IN_CHARLOTTE_TEST = 38;
  public static final int TOTAL_STUDENTS_IN_CHARLOTTE_FROM_PLACEHOLDER = 2;

  TotalStudentsInCharlotteDao totalStudentsInCharlotteDao;

  private int getTotalStudentsInCharlotteFromPublicDatabase() throws SQLException {
    Connection publicConnection = null;
    ConnectionManager publicConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPublic");

    String getTotalStudentsInCharlotteFromPublicDatabase =
            "SELECT TotalStudents FROM TotalStudentsInCharlotte;";
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      selectStatement = publicConnection.prepareStatement(getTotalStudentsInCharlotteFromPublicDatabase);
      results = selectStatement.executeQuery();
      if (results.next()) {
        int totalStudentsInCharlotte = results.getInt("TotalStudents");
        return totalStudentsInCharlotte;
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
    Assert.assertTrue(getTotalStudentsInCharlotteFromPublicDatabase()
            == TOTAL_STUDENTS_IN_CHARLOTTE_TEST);
    totalStudentsInCharlotteDao.updateTotalStudentsInCharlotteInPublicDatabase(
            TOTAL_STUDENTS_IN_CHARLOTTE_FROM_PLACEHOLDER);
    Assert.assertTrue(getTotalStudentsInCharlotteFromPublicDatabase() ==
            TOTAL_STUDENTS_IN_CHARLOTTE_FROM_PLACEHOLDER);
  }
}