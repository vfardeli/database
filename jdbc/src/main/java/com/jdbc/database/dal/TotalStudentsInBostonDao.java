package com.jdbc.database.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalStudentsInBostonDao {
  private ConnectionManager privateConnectionManager;
  private ConnectionManager publicConnectionManager;

  // Singleton pattern
  private static TotalStudentsInBostonDao instance = null;

  /**
   * Default Constructor.
   * Get setup the connection configurations for both public database
   * and private database.
   *
   * @throws SQLException when connection has something wrong.
   */
  private TotalStudentsInBostonDao() throws SQLException {
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
   * @return Total Students In Boston Dao.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TotalStudentsInBostonDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TotalStudentsInBostonDao();
    }
    return instance;
  }

  /**
   * Get total students in Boston from the private database.
   * This is a script that extract the information for total students
   * in Boston from the private database.
   *
   * @return Total Students In Boston
   * @throws SQLException when connection to database has something wrong.
   */
  public int getTotalStudentsInBostonFromPrivateDatabase() throws SQLException {
    String getTotalStudentsFromPrivateDatabase =
            "SELECT COUNT(*) AS TOTAL_BOSTON_STUDENTS " +
                    "FROM Students " +
                    "WHERE Campus = \"BOSTON\";";
    Connection privateConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      privateConnectionManager.connect();
      privateConnection = privateConnectionManager.getConnection();
      selectStatement = privateConnection.prepareStatement(getTotalStudentsFromPrivateDatabase);
      results = selectStatement.executeQuery();
      if (results.next()) {
        int totalStudentsInBoston = results.getInt("TOTAL_BOSTON_STUDENTS");
        return totalStudentsInBoston;
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
   * Update total students in Boston in the public database.
   * This is a script that update the information for total students
   * in Boston in the public database.
   *
   * @param totalStudents Total Students in Boston (extracted from the
   *                      private database); not null.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTotalStudentsInBostonInPublicDatabase(int totalStudents) throws SQLException {
    String updateTotalStudentsInBostonInPublic =
            "UPDATE TotalStudentsInBoston SET TotalStudents = ? WHERE TotalStudentsInBostonId = 1;";
    Connection publicConnection = null;
    PreparedStatement updateStatement = null;
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      updateStatement = publicConnection.prepareStatement(updateTotalStudentsInBostonInPublic);
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
