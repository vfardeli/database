package com.jdbc.database.dal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopFiveEmployersDaoTest {
  private List<String> topFiveEmployersTest;
  private List<String> topFiveEmployersFromPlaceholder;
  private TopFiveEmployersDao topFiveEmployersDao;

  @Before
  public void init() throws SQLException {
    topFiveEmployersDao = TopFiveEmployersDao.getInstance();

    topFiveEmployersTest = new ArrayList<>();
    topFiveEmployersTest.add("Alibaba");
    topFiveEmployersTest.add("Samsung");

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
    topFiveEmployersTest.add("NULL");
    topFiveEmployersTest.add("NULL");
    topFiveEmployersTest.add("NULL");
    Assert.assertTrue(topFiveEmployersDao.getTopFiveEmployersFromPublicDatabase()
            .equals(topFiveEmployersTest));
    topFiveEmployersDao.updateTopFiveEmployersInPublicDatabase(
            topFiveEmployersFromPlaceholder);
    Assert.assertTrue(topFiveEmployersDao.getTopFiveEmployersFromPublicDatabase()
            .equals(topFiveEmployersFromPlaceholder));
  }
}