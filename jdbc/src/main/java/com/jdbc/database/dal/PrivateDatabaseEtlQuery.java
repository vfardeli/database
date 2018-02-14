package com.jdbc.database.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrivateDatabaseEtlQuery {
  private ConnectionManager privateConnectionManager;

  /**
   * Default Constructors.
   *
   * @throws SQLException when connection to database has something wrong.
   */
  public PrivateDatabaseEtlQuery() throws SQLException {
    privateConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPrivate");
  }

  /**
   * Get a data for a single value query from the Private DB
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL).
   * @param columnLabel Column Label of the Data
   *                    that wants to be retrieved.
   * @return Data returned from the Database.
   * @throws SQLException when connection to the DB has a problem.
   */
  public int getSingleValueQuery(String queryString, String columnLabel) throws SQLException {
    Connection privateConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      privateConnectionManager.connect();
      privateConnection = privateConnectionManager.getConnection();
      selectStatement = privateConnection.prepareStatement(queryString);
      results = selectStatement.executeQuery();
      if (results.next()) {
        int totalStudents = results.getInt(columnLabel);
        return totalStudents;
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
   * Get list of data for a multiple value query from the Private DB
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL).
   * @param columnLabel Column Label of the Data
   *                    that wants to be retrieved.
   * @return List Of Data from the Public DB.
   * @throws SQLException when connection to the DB has a problem.
   */
  public List<String> getMultipleValueQuery(
          String queryString,
          String columnLabel) throws SQLException {
    List<String> listOfData = new ArrayList<>();
    Connection privateConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      privateConnectionManager.connect();
      privateConnection = privateConnectionManager.getConnection();
      selectStatement = privateConnection.prepareStatement(queryString);
      results = selectStatement.executeQuery();
      while (results.next()) {
        String data = results.getString(columnLabel);
        listOfData.add(data);
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
    return listOfData;
  }
}
