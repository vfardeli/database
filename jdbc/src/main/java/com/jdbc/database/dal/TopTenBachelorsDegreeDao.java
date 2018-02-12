package com.jdbc.database.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopTenBachelorsDegreeDao {
  private static final int TOP_TEN = 10;
  private static final String NULL = "NULL";

  private ConnectionManager privateConnectionManager;
  private ConnectionManager publicConnectionManager;

  // Singleton pattern
  private static TopTenBachelorsDegreeDao instance = null;

  /**
   * Default Constructor.
   * Get setup the connection configurations for both public database
   * and private database.
   *
   * @throws SQLException when connection has something wrong.
   */
  private TopTenBachelorsDegreeDao() throws SQLException {
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
   * @return Top Ten Bachelors Degree Dao instance.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TopTenBachelorsDegreeDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TopTenBachelorsDegreeDao();
    }
    return instance;
  }

  /**
   * Get the top ten Bachelors degree from the private database.
   * This is a script that extract the information for top ten
   * bachelors degree from the private database.
   *
   * @return Top ten bachelors degree
   * @throws SQLException when connection to database has something wrong.
   */
  public List<String> getTopTenBachelorsDegreeFromPrivateDatabase() throws SQLException {
    String getTopTenBachelorsDegreeFromPrivateDatabase =
            "SELECT Majors.Major AS MAJOR, COUNT(*) AS TOTAL " +
                    "FROM PriorEducations INNER JOIN Majors ON PriorEducations.MajorId = Majors.MajorId " +
                    "WHERE PriorEducations.DegreeCandidacy = \"BACHELORS\" " +
                    "GROUP BY MAJOR " +
                    "ORDER BY TOTAL DESC " +
                    "LIMIT 10;";
    List<String> topTenBachelorsDegree = new ArrayList<>();
    Connection privateConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      privateConnectionManager.connect();
      privateConnection = privateConnectionManager.getConnection();
      selectStatement = privateConnection.prepareStatement(getTopTenBachelorsDegreeFromPrivateDatabase);
      results = selectStatement.executeQuery();
      while (results.next()) {
        String TopBachelorsDegree = results.getString("MAJOR");
        topTenBachelorsDegree.add(TopBachelorsDegree);
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
    return topTenBachelorsDegree;
  }

  /**
   * Update top ten bachelors degree the public database.
   * This is a script that update the information for top ten
   * bachelors degree in the public database.
   *
   * @param topTenBachelorsDegree List of Top Ten Bachelors Degree (extracted from the
   *                      private database); not null, size less than or equal to 10.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTopTenBachelorsDegreeInPublicDatabase(List<String> topTenBachelorsDegree) throws SQLException {
    String updateTopTenBachelorsDegreeInPublic =
            "UPDATE TopTenBachelorsDegree SET BachelorsDegree = ? WHERE TopTenBachelorsDegreeId = ?;";
    Connection publicConnection = null;
    PreparedStatement updateStatement = null;
    int listSize = topTenBachelorsDegree.size();
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      for (int index = 0; index < listSize; index++) {
        updateStatement = publicConnection.prepareStatement(updateTopTenBachelorsDegreeInPublic);
        updateStatement.setString(1, topTenBachelorsDegree.get(index));
        updateStatement.setInt(2, index + 1);
        updateStatement.executeQuery();
      }
      if (listSize < TOP_TEN) {
        for (int index = listSize; index < TOP_TEN; index++) {
          updateStatement = publicConnection.prepareStatement(updateTopTenBachelorsDegreeInPublic);
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
