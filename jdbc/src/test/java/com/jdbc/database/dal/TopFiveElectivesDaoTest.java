package com.jdbc.database.dal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopFiveElectivesDaoTest {
  private List<String> topFiveElectivesTest;
  private List<String> topFiveElectivesFromPlaceholder;
  private TopFiveElectivesDao topFiveElectivesDao;

  private List<String> getTopFiveElectivesFromPublicDatabase() throws SQLException {
    Connection publicConnection = null;
    ConnectionManager publicConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPublic");

    String getTopFiveElectivesFromPublicDatabase =
            "SELECT CourseName FROM TopFiveElectives;";
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    List<String> topFiveElectives = new ArrayList<>();
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      selectStatement = publicConnection.prepareStatement(getTopFiveElectivesFromPublicDatabase);
      results = selectStatement.executeQuery();
      while (results.next()) {
        String topElectives = results.getString("CourseName");
        topFiveElectives.add(topElectives);
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
    return topFiveElectives;
  }

  @Before
  public void init() throws SQLException {
    topFiveElectivesDao = TopFiveElectivesDao.getInstance();

    topFiveElectivesTest = new ArrayList<>();
    topFiveElectivesTest.add("Database Management Systems");
    topFiveElectivesTest.add("Machine Learning");
    topFiveElectivesTest.add(null);
    topFiveElectivesTest.add(null);
    topFiveElectivesTest.add(null);

    topFiveElectivesFromPlaceholder = new ArrayList<>();
    topFiveElectivesFromPlaceholder.add("Database Management Systems");
    topFiveElectivesFromPlaceholder.add("Machine Learning");
    topFiveElectivesFromPlaceholder.add("Web Development");
    topFiveElectivesFromPlaceholder.add(null);
    topFiveElectivesFromPlaceholder.add(null);
  }

  @Test
  public void getTopFiveEmployersFromPrivateDatabaseTest() throws SQLException {
    List<String> topFiveElectives =
            topFiveElectivesDao.getTopFiveElectivesFromPrivateDatabase();
    Assert.assertTrue(topFiveElectives.size() <= 5);
    Assert.assertTrue(topFiveElectives.get(0).equals("Database Management Systems"));
    Assert.assertTrue(topFiveElectives.get(1).equals("Machine Learning"));
    Assert.assertTrue(topFiveElectives.get(2).equals("Web Development"));
  }

  @Test
  public void updateTopFiveEmployersInPublicDatabaseTest() throws SQLException {
    topFiveElectivesDao.updateTopFiveElectivesInPublicDatabase(
            topFiveElectivesTest);
    Assert.assertTrue(getTopFiveElectivesFromPublicDatabase()
            .equals(topFiveElectivesTest));
    topFiveElectivesDao.updateTopFiveElectivesInPublicDatabase(
            topFiveElectivesFromPlaceholder);
    Assert.assertTrue(getTopFiveElectivesFromPublicDatabase()
            .equals(topFiveElectivesFromPlaceholder));
  }
}