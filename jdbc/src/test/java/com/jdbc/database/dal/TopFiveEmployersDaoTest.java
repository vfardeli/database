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

public class TopFiveEmployersDaoTest {
  private List<String> topFiveEmployersTest;
  private List<String> topFiveEmployersFromPlaceholder;
  private TopFiveEmployersDao topFiveEmployersDao;

  private List<String> getTopFiveEmployersFromPublicDatabase() throws SQLException {
    Connection publicConnection = null;
    ConnectionManager publicConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPublic");

    String getTopFiveEmployersFromPublicDatabase =
            "SELECT Employer FROM TopFiveEmployers;";
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    List<String> topFiveEmployers = new ArrayList<>();
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      selectStatement = publicConnection.prepareStatement(getTopFiveEmployersFromPublicDatabase);
      results = selectStatement.executeQuery();
      while (results.next()) {
        String topEmployers = results.getString("Employer");
        topFiveEmployers.add(topEmployers);
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
    return topFiveEmployers;
  }

  @Before
  public void init() throws SQLException {
    topFiveEmployersDao = TopFiveEmployersDao.getInstance();

    topFiveEmployersTest = new ArrayList<>();
    topFiveEmployersTest.add("Alibaba");
    topFiveEmployersTest.add("Samsung");
    topFiveEmployersTest.add(null);
    topFiveEmployersTest.add(null);
    topFiveEmployersTest.add(null);

    topFiveEmployersFromPlaceholder = new ArrayList<>();
    topFiveEmployersFromPlaceholder.add("Google");
    topFiveEmployersFromPlaceholder.add("Amazon");
    topFiveEmployersFromPlaceholder.add("Facebook");
    topFiveEmployersFromPlaceholder.add("Microsoft");
    topFiveEmployersFromPlaceholder.add("Apple");
  }

  @Test
  public void getTopFiveEmployersFromPrivateDatabaseTest() throws SQLException {
    List<String> topFiveEmployers =
            topFiveEmployersDao.getTopFiveEmployersFromPrivateDatabase();
    Assert.assertTrue(topFiveEmployers.size() <= 5);
    Assert.assertTrue(topFiveEmployers.get(0).equals("Google"));
    Assert.assertTrue(topFiveEmployers.get(1).equals("Amazon"));
    Assert.assertTrue(topFiveEmployers.get(2).equals("Facebook"));
    Assert.assertTrue(topFiveEmployers.get(3).equals("Microsoft"));
    Assert.assertTrue(topFiveEmployers.get(4).equals("Apple"));
  }

  @Test
  public void updateTopFiveEmployersInPublicDatabaseTest() throws SQLException {
    topFiveEmployersDao.updateTopFiveEmployersInPublicDatabase(
            topFiveEmployersTest);
    Assert.assertTrue(getTopFiveEmployersFromPublicDatabase()
            .equals(topFiveEmployersTest));
    topFiveEmployersDao.updateTopFiveEmployersInPublicDatabase(
            topFiveEmployersFromPlaceholder);
    Assert.assertTrue(getTopFiveEmployersFromPublicDatabase()
            .equals(topFiveEmployersFromPlaceholder));
  }
}