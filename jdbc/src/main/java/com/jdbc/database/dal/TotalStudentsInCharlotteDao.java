package com.jdbc.database.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalStudentsInCharlotteDao {
  private ConnectionManager privateConnectionManager;
  private ConnectionManager publicConnectionManager;

  // Singleton pattern
  private static TotalStudentsInCharlotteDao instance = null;

  /**
   * Default Constructor.
   * Get setup the connection configurations for both public database
   * and private database.
   *
   * @throws SQLException when connection has something wrong.
   */
  private TotalStudentsInCharlotteDao() throws SQLException {
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
   * @return Total Students In Charlotte Dao.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TotalStudentsInCharlotteDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TotalStudentsInCharlotteDao();
    }
    return instance;
  }

  /**
   * Get total students in Charlotte from the private database.
   * This is a script that extract the information for total students
   * in Charlotte from the private database.
   *
   * @return Total Students In Charlotte
   * @throws SQLException when connection to database has something wrong.
   */
  public int getTotalStudentsInCharlotteFromPrivateDatabase() throws SQLException {
    String getTotalStudentsFromPrivateDatabase =
            "SELECT COUNT(*) AS TOTAL_CHARLOTTE_STUDENTS " +
                    "FROM Students " +
                    "WHERE Campus = \"CHARLOTTE\";";
    Connection privateConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      privateConnectionManager.connect();
      privateConnection = privateConnectionManager.getConnection();
      selectStatement = privateConnection.prepareStatement(getTotalStudentsFromPrivateDatabase);
      results = selectStatement.executeQuery();
      if (results.next()) {
        int totalStudentsInCharlotte = results.getInt("TOTAL_CHARLOTTE_STUDENTS");
        return totalStudentsInCharlotte;
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
   * Update total students in Charlotte in the public database.
   * This is a script that update the information for total students
   * in Charlotte in the public database.
   *
   * @param totalStudents Total Students in Charlotte (extracted from the
   *                      private database); not null.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTotalStudentsInCharlotteInPublicDatabase(int totalStudents) throws SQLException {
    String updateTotalStudentsInCharlotteInPublic =
            "UPDATE TotalStudentsInCharlotte SET TotalStudents = ? WHERE TotalStudentsInCharlotteId = 1;";
    Connection publicConnection = null;
    PreparedStatement updateStatement = null;
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      updateStatement = publicConnection.prepareStatement(updateTotalStudentsInCharlotteInPublic);
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
