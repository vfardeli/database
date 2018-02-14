package com.jdbc.database.dal;

import com.jdbc.database.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublicDatabaseEtlQuery {
  private ConnectionManager publicConnectionManager;

  /**
   * Default Constructors.
   *
   * @throws SQLException when connection to the DB has a problem.
   */
  public PublicDatabaseEtlQuery() throws SQLException {
    publicConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPublic");
  }

  /**
   * Update the Public DB that only has a single value
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL).
   * @param updatedData the data that will replace the one in public DB.
   * @throws SQLException when connection to the DB has a problem.
   */
  public void updateSingleValueQuery(String queryString, int updatedData) throws SQLException {
    Connection publicConnection = null;
    PreparedStatement updateStatement = null;
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      updateStatement = publicConnection.prepareStatement(queryString);
      updateStatement.setInt(1, updatedData);
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

  /**
   * Get a data for a single value query from the Public DB
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL).
   * @return Data returned from the Database.
   * @throws SQLException when connection to the DB has a problem.
   */
  public int getSingleValueQuery(String queryString) throws SQLException {
    Connection publicConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      selectStatement = publicConnection.prepareStatement(queryString);
      results = selectStatement.executeQuery();
      if (results.next()) {
        return results.getInt("DataValue");
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw exception;
    } finally {
      if (publicConnection != null) {
        publicConnection.close();
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
   * Update the Public DB that has a multiple values (rows in the table)
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL).
   * @param updatedList list of data that will replace
   *                    the datas in public DB.
   * @param totalValue size of the list of data (updatedList); should be
   *                   bigger than 0.
   * @throws SQLException when connection to the DB has a problem.
   */
  public void updateMultipleValueQuery(
          String queryString,
          List<String> updatedList,
          int totalValue) throws SQLException {
    Connection publicConnection = null;
    PreparedStatement updateStatement = null;
    int listSize = updatedList.size();
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      for (int index = 0; index < listSize; index++) {
        updateStatement = publicConnection.prepareStatement(queryString);
        updateStatement.setString(1, updatedList.get(index));
        updateStatement.setInt(2, index + 1);
        updateStatement.executeQuery();
      }
      if (listSize < totalValue) {
        for (int index = listSize; index < totalValue; index++) {
          updateStatement = publicConnection.prepareStatement(queryString);
          updateStatement.setString(1, Constants.NULL_STRING);
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

  /**
   * Get list of data for a multiple value query from the Public DB
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL).
   * @param columnLabel Column Label of the Data
   *                    that wants to be retrieved.
   * @param totalExpectedValues total number of expected data value.
   * @return List Of Data from the Public DB.
   * @throws SQLException SQLException when connection to the DB has a problem.
   */
  public List<String> getMultipleValueQuery(
          String queryString,
          String columnLabel,
          int totalExpectedValues) throws SQLException {
    Connection publicConnection = null;
    PreparedStatement selectStatement = null;
    ResultSet results = null;
    List<String> listOfData = new ArrayList<>();
    try {
      publicConnectionManager.connect();
      publicConnection = publicConnectionManager.getConnection();
      selectStatement = publicConnection.prepareStatement(queryString);
      results = selectStatement.executeQuery();
      while (results.next() && listOfData.size() < totalExpectedValues) {
        String data = results.getString(columnLabel);
        listOfData.add(data);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw exception;
    } finally {
      if (publicConnection != null) {
        publicConnection.close();
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
