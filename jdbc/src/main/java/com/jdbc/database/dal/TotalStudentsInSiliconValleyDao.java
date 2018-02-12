package com.jdbc.database.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalStudentsInSiliconValleyDao {
  private ConnectionManager privateConnectionManager;
  private ConnectionManager publicConnectionManager;

  // Singleton pattern
  private static TotalStudentsInSiliconValleyDao instance = null;

  /**
   * Default Constructor.
   * Get setup the connection configurations for both public database
   * and private database.
   *
   * @throws SQLException when connection has something wrong.
   */
  private TotalStudentsInSiliconValleyDao() throws SQLException {
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
   * @return Total Students In SiliconValley Dao.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TotalStudentsInSiliconValleyDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TotalStudentsInSiliconValleyDao();
    }
    return instance;
  }

  /**
   * Get total students in Silicon Valley from the private database.
   * This is a script that extract the information for total students
   * in Silicon Valley from the private database.
   *
   * @return Total Students In Silicon Valley
   * @throws SQLException when connection to database has something wrong.
   */
  public int getTotalStudentsInSiliconValleyFromPrivateDatabase() throws SQLException {
    String getTotalStudentsFromPrivateDatabase =
            "SELECT COUNT(*) AS TOTAL_SILICON_VALLEY_STUDENTS " +
                    "FROM Students " +
                    "WHERE Campus = \"SILICON_VALLEY\";";
    Connection privateConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      privateConnectionManager.connect();
      privateConnection = privateConnectionManager.getConnection();
      selectStatement = privateConnection.prepareStatement(getTotalStudentsFromPrivateDatabase);
      results = selectStatement.executeQuery();
      if (results.next()) {
        int totalStudentsInSiliconValley = results.getInt("TOTAL_SILICON_VALLEY_STUDENTS");
        return totalStudentsInSiliconValley;
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
    return -1;
  }

  /**
   * Update total students in Silicon Valley in the public database.
   * This is a script that update the information for total students
   * in Silicon Valley in the public database.
   *
   * @param totalStudents Total Students in Silicon Valley (extracted from the
   *                      private database); not null.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTotalStudentsInSiliconValleyInPublicDatabase(int totalStudents) throws SQLException {
    String updateTotalStudentsInSiliconValleyInPublic =
            "UPDATE TotalStudentsInSiliconValley SET TotalStudents = ? WHERE TotalStudentsInSiliconValleyId = 1;";
    Connection publicConnection = null;
    PreparedStatement updateStatement = null;
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      updateStatement = publicConnection.prepareStatement(updateTotalStudentsInSiliconValleyInPublic);
      updateStatement.setInt(1, totalStudents);
      updateStatement.executeQuery();
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
