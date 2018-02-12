package com.jdbc.database.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopFiveEmployersDao {
  private static final int TOP_FIVE = 5;
  private static final String NULL = "NULL";

  private ConnectionManager privateConnectionManager;
  private ConnectionManager publicConnectionManager;

  // Singleton pattern
  private static TopFiveEmployersDao instance = null;

  /**
   * Default Constructor.
   * Get setup the connection configurations for both public database
   * and private database.
   *
   * @throws SQLException when connection has something wrong.
   */
  private TopFiveEmployersDao() throws SQLException {
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
   * @return Top Five Employers Dao instance.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TopFiveEmployersDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TopFiveEmployersDao();
    }
    return instance;
  }

  /**
   * Get the top five employers from the private database.
   * This is a script that extract the information for top five
   * employers from the private database.
   *
   * @return Top five employers.
   * @throws SQLException when connection to database has something wrong.
   */
  public List<String> getTopFiveEmployersFromPrivateDatabase() throws SQLException {
    String getTopFiveEmployersFromPrivateDatabase =
            "SELECT Companies.CompanyName AS EMPLOYER, COUNT(*) AS TOTAL " +
                    "FROM WorkExperiences INNER JOIN Companies ON WorkExperiences.CompanyId = Companies.CompanyId " +
                    "GROUP BY EMPLOYER " +
                    "ORDER BY TOTAL DESC " +
                    "LIMIT 5;";
    List<String> topFiveEmployers = new ArrayList<>();
    Connection privateConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      privateConnectionManager.connect();
      privateConnection = privateConnectionManager.getConnection();
      selectStatement = privateConnection.prepareStatement(getTopFiveEmployersFromPrivateDatabase);
      results = selectStatement.executeQuery();
      while (results.next()) {
        String topEmployers = results.getString("EMPLOYER");
        topFiveEmployers.add(topEmployers);
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
    return topFiveEmployers;
  }

  /**
   * Update top five employers the public database.
   * This is a script that update the information for top five
   * employers in the public database.
   *
   * @param topFiveEmployers List of Top Five Employers (extracted from the
   *                              private database); not null, size less than or equal to 5.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTopFiveEmployersInPublicDatabase(List<String> topFiveEmployers) throws SQLException {
    String updateTopFiveEmployersInPublic =
            "UPDATE TopFiveEmployers SET Employer = ? WHERE TopFiveEmployersId = ?;";
    Connection publicConnection = null;
    PreparedStatement updateStatement = null;
    int listSize = topFiveEmployers.size();
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      for (int index = 0; index < listSize; index++) {
        updateStatement = publicConnection.prepareStatement(updateTopFiveEmployersInPublic);
        updateStatement.setString(1, topFiveEmployers.get(index));
        updateStatement.setInt(2, index + 1);
        updateStatement.executeQuery();
      }
      if (listSize < TOP_FIVE) {
        for (int index = listSize; index < TOP_FIVE; index++) {
          updateStatement = publicConnection.prepareStatement(updateTopFiveEmployersInPublic);
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
