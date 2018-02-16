package com.jdbc.database.dal.script;

import com.jdbc.database.dal.script.TopFiveElectivesDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopFiveElectivesDaoTest {
  private List<String> topFiveElectivesTest;
  private List<String> topFiveElectivesFromPlaceholder;
  private TopFiveElectivesDao topFiveElectivesDao;

  @Before
  public void init() throws SQLException {
    topFiveElectivesDao = TopFiveElectivesDao.getInstance();

    topFiveElectivesTest = new ArrayList<>();
    topFiveElectivesTest.add("Database Management Systems");
    topFiveElectivesTest.add("Machine Learning");

    topFiveElectivesFromPlaceholder = new ArrayList<>();
    topFiveElectivesFromPlaceholder.add("Database Management Systems");
    topFiveElectivesFromPlaceholder.add("Machine Learning");
    topFiveElectivesFromPlaceholder.add("Web Development");
    topFiveElectivesFromPlaceholder.add("NULL");
    topFiveElectivesFromPlaceholder.add("NULL");
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
    topFiveElectivesTest.add("NULL");
    topFiveElectivesTest.add("NULL");
    topFiveElectivesTest.add("NULL");
    Assert.assertTrue(topFiveElectivesDao.getTopFiveElectivesFromPublicDatabase()
            .equals(topFiveElectivesTest));
    topFiveElectivesDao.updateTopFiveElectivesInPublicDatabase(
            topFiveElectivesFromPlaceholder);
    Assert.assertTrue(topFiveElectivesDao.getTopFiveElectivesFromPublicDatabase()
            .equals(topFiveElectivesFromPlaceholder));
  }
}