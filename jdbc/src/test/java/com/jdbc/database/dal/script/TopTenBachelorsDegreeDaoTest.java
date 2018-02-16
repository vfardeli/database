package com.jdbc.database.dal.script;

import com.jdbc.database.dal.script.TopTenBachelorsDegreeDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopTenBachelorsDegreeDaoTest {
  private List<String> topTenBachelorsDegreeTest;
  private List<String> topTenBachelorsDegreeFromPlaceholder;
  private TopTenBachelorsDegreeDao topTenBachelorsDegreeDao;

  @Before
  public void init() throws SQLException {
    topTenBachelorsDegreeDao = TopTenBachelorsDegreeDao.getInstance();

    topTenBachelorsDegreeTest = new ArrayList<>();
    topTenBachelorsDegreeTest.add("Mechanical Engineering");
    topTenBachelorsDegreeTest.add("Industrial Engineering");

    topTenBachelorsDegreeFromPlaceholder = new ArrayList<>();
    topTenBachelorsDegreeFromPlaceholder.add("Computer Science");
    topTenBachelorsDegreeFromPlaceholder.add("Accounting");
    topTenBachelorsDegreeFromPlaceholder.add("English");
    topTenBachelorsDegreeFromPlaceholder.add("NULL");
    topTenBachelorsDegreeFromPlaceholder.add("NULL");
    topTenBachelorsDegreeFromPlaceholder.add("NULL");
    topTenBachelorsDegreeFromPlaceholder.add("NULL");
    topTenBachelorsDegreeFromPlaceholder.add("NULL");
    topTenBachelorsDegreeFromPlaceholder.add("NULL");
    topTenBachelorsDegreeFromPlaceholder.add("NULL");
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
    topTenBachelorsDegreeTest.add("NULL");
    topTenBachelorsDegreeTest.add("NULL");
    topTenBachelorsDegreeTest.add("NULL");
    topTenBachelorsDegreeTest.add("NULL");
    topTenBachelorsDegreeTest.add("NULL");
    topTenBachelorsDegreeTest.add("NULL");
    topTenBachelorsDegreeTest.add("NULL");
    topTenBachelorsDegreeTest.add("NULL");
    Assert.assertTrue(topTenBachelorsDegreeDao.getTopTenBachelorsDegreeFromPublicDatabase()
            .equals(topTenBachelorsDegreeTest));
    topTenBachelorsDegreeDao.updateTopTenBachelorsDegreeInPublicDatabase(
            topTenBachelorsDegreeFromPlaceholder);
    Assert.assertTrue(topTenBachelorsDegreeDao.getTopTenBachelorsDegreeFromPublicDatabase()
            .equals(topTenBachelorsDegreeFromPlaceholder));
  }
}