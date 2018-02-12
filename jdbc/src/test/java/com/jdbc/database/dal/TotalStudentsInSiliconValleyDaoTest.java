package com.jdbc.database.dal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalStudentsInSiliconValleyDaoTest {
  public static final int TOTAL_STUDENTS_IN_SILICON_VALLEY_TEST = 70;
  public static final int TOTAL_STUDENTS_IN_SILICON_VALLEY_FROM_PLACEHOLDER = 3;

  TotalStudentsInSiliconValleyDao totalStudentsInSiliconValleyDao;

  private int getTotalStudentsInSiliconValleyFromPublicDatabase() throws SQLException {
    Connection publicConnection = null;
    ConnectionManager publicConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPublic");

    String getTotalStudentsInSiliconValleyFromPublicDatabase =
            "SELECT TotalStudents FROM TotalStudentsInSiliconValley;";
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      selectStatement = publicConnection.prepareStatement(getTotalStudentsInSiliconValleyFromPublicDatabase);
      results = selectStatement.executeQuery();
      if (results.next()) {
        int totalStudentsInSiliconValley = results.getInt("TotalStudents");
        return totalStudentsInSiliconValley;
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
    Assert.assertTrue(getTotalStudentsInSiliconValleyFromPublicDatabase()
            == TOTAL_STUDENTS_IN_SILICON_VALLEY_TEST);
    totalStudentsInSiliconValleyDao.updateTotalStudentsInSiliconValleyInPublicDatabase(
            TOTAL_STUDENTS_IN_SILICON_VALLEY_FROM_PLACEHOLDER);
    Assert.assertTrue(getTotalStudentsInSiliconValleyFromPublicDatabase() ==
            TOTAL_STUDENTS_IN_SILICON_VALLEY_FROM_PLACEHOLDER);
  }
}