package com.jdbc.database.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopFiveElectivesDao {
  private static final int TOP_FIVE = 5;
  private static final String NULL = "NULL";

  private ConnectionManager privateConnectionManager;
  private ConnectionManager publicConnectionManager;

  // Singleton pattern
  private static TopFiveElectivesDao instance = null;

  /**
   * Default Constructor.
   * Get setup the connection configurations for both public database
   * and private database.
   *
   * @throws SQLException when connection has something wrong.
   */
  private TopFiveElectivesDao() throws SQLException {
    privateConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPrivate");

    publicConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPublic");
  }

  /**
   * Singleton Pattern.
   *
   * @return Top Five Electives Dao instance.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TopFiveElectivesDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TopFiveElectivesDao();
    }
    return instance;
  }

  /**
   * Get the top five electives from the private database.
   * This is a script that extract the information for top five
   * electives from the private database.
   *
   * @return Top five electives.
   * @throws SQLException when connection to database has something wrong.
   */
  public List<String> getTopFiveElectivesFromPrivateDatabase() throws SQLException {
    String getTopFiveElectivesFromPrivateDatabase =
            "SELECT Courses.CourseName AS COURSE, COUNT(*) AS TOTAL " +
                    "FROM Electives INNER JOIN Courses ON Electives.CourseId = Courses.CourseId " +
                    "GROUP BY COURSE " +
                    "ORDER BY TOTAL DESC " +
                    "LIMIT 5;";
    List<String> topFiveElectives = new ArrayList<>();
    Connection privateConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      privateConnectionManager.connect();
      privateConnection = privateConnectionManager.getConnection();
      selectStatement = privateConnection.prepareStatement(getTopFiveElectivesFromPrivateDatabase);
      results = selectStatement.executeQuery();
      while (results.next()) {
        String topElectives = results.getString("COURSE");
        topFiveElectives.add(topElectives);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw exception;
    } finally {
      if (privateConnection != null) {
        privateConnection.close();
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

  /**
   * Update top five electives the public database.
   * This is a script that update the information for top five
   * electives in the public database.
   *
   * @param topFiveElectives List of Top Five Electives (extracted from the
   *                              private database); not null, size less than or equal to 5.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTopFiveElectivesInPublicDatabase(List<String> topFiveElectives) throws SQLException {
    String updateTopFiveElectivesInPublic =
            "UPDATE TopFiveElectives SET CourseName = ? WHERE TopFiveElectivesId = ?;";
    Connection publicConnection = null;
    PreparedStatement updateStatement = null;
    int listSize = topFiveElectives.size();
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      for (int index = 0; index < listSize; index++) {
        updateStatement = publicConnection.prepareStatement(updateTopFiveElectivesInPublic);
        updateStatement.setString(1, topFiveElectives.get(index));
        updateStatement.setInt(2, index + 1);
        updateStatement.executeQuery();
      }
      if (listSize < TOP_FIVE) {
        for (int index = listSize; index < TOP_FIVE; index++) {
          updateStatement = publicConnection.prepareStatement(updateTopFiveElectivesInPublic);
          updateStatement.setString(1, NULL);
          updateStatement.setInt(2, index + 1);
          updateStatement.executeQuery();
        }
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw exception;
    } finally {
      if (publicConnection != null) {
        publicConnection.close();
      }
      if (updateStatement != null) {
        updateStatement.close();
      }
    }

  }
}
