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

public class TopTenBachelorsDegreeDaoTest {
  private List<String> topTenBachelorsDegreeTest;
  private List<String> topTenBachelorsDegreeFromPlaceholder;
  private TopTenBachelorsDegreeDao topTenBachelorsDegreeDao;

  private List<String> getTopTenBachelorsDegreeFromPublicDatabase() throws SQLException {
    Connection publicConnection = null;
    ConnectionManager publicConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPublic");

    String getTopTenBachelorsDegreeFromPublicDatabase =
            "SELECT BachelorsDegree FROM TopTenBachelorsDegree;";
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    List<String> topTenBachelorsDegree = new ArrayList<>();
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      selectStatement = publicConnection.prepareStatement(getTopTenBachelorsDegreeFromPublicDatabase);
      results = selectStatement.executeQuery();
      while (results.next()) {
        String topBachelorsDegree = results.getString("BachelorsDegree");
        topTenBachelorsDegree.add(topBachelorsDegree);
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
    return topTenBachelorsDegree;
  }

  @Before
  public void init() throws SQLException {
    topTenBachelorsDegreeDao = TopTenBachelorsDegreeDao.getInstance();

    topTenBachelorsDegreeTest = new ArrayList<>();
    topTenBachelorsDegreeTest.add("Mechanical Engineering");
    topTenBachelorsDegreeTest.add("Industrial Engineering");
    topTenBachelorsDegreeTest.add(null);
    topTenBachelorsDegreeTest.add(null);
    topTenBachelorsDegreeTest.add(null);
    topTenBachelorsDegreeTest.add(null);
    topTenBachelorsDegreeTest.add(null);
    topTenBachelorsDegreeTest.add(null);
    topTenBachelorsDegreeTest.add(null);
    topTenBachelorsDegreeTest.add(null);

    topTenBachelorsDegreeFromPlaceholder = new ArrayList<>();
    topTenBachelorsDegreeFromPlaceholder.add("Computer Science");
    topTenBachelorsDegreeFromPlaceholder.add("Accounting");
    topTenBachelorsDegreeFromPlaceholder.add("English");
    topTenBachelorsDegreeFromPlaceholder.add(null);
    topTenBachelorsDegreeFromPlaceholder.add(null);
    topTenBachelorsDegreeFromPlaceholder.add(null);
    topTenBachelorsDegreeFromPlaceholder.add(null);
    topTenBachelorsDegreeFromPlaceholder.add(null);
    topTenBachelorsDegreeFromPlaceholder.add(null);
    topTenBachelorsDegreeFromPlaceholder.add(null);
  }

  @Test
  public void getTopTenBachelorsDegreeFromPrivateDatabaseTest() throws SQLException {
    List<String> topTenBachelorsDegree =
            topTenBachelorsDegreeDao.getTopTenBachelorsDegreeFromPrivateDatabase();
    Assert.assertTrue(topTenBachelorsDegree.size() <= 10);
    Assert.assertTrue(topTenBachelorsDegree.get(0).equals("Computer Science"));
    Assert.assertTrue(topTenBachelorsDegree.get(1).equals("Accounting"));
    Assert.assertTrue(topTenBachelorsDegree.get(2).equals("English"));
  }

  @Test
  public void updateTopTenBachelorsDegreeInPublicDatabaseTest() throws SQLException {
    topTenBachelorsDegreeDao.updateTopTenBachelorsDegreeInPublicDatabase(
            topTenBachelorsDegreeTest);
    Assert.assertTrue(getTopTenBachelorsDegreeFromPublicDatabase()
            .equals(topTenBachelorsDegreeTest));
    topTenBachelorsDegreeDao.updateTopTenBachelorsDegreeInPublicDatabase(
            topTenBachelorsDegreeFromPlaceholder);
    Assert.assertTrue(getTopTenBachelorsDegreeFromPublicDatabase()
            .equals(topTenBachelorsDegreeFromPlaceholder));
  }
}