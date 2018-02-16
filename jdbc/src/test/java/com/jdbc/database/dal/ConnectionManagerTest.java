package com.jdbc.database.dal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManagerTest {

  ConnectionManager publicConnectionManager;

  private void testSslConnection(ConnectionManager connectionManager) throws SQLException {
    Connection connection = null;
    try {
      connection = connectionManager.connect();
      Statement statement = connection.createStatement();

      //Test a query to make sure we're connected;
      ResultSet rs = statement.executeQuery("SELECT 1");
      rs.next();
      Assert.assertEquals(1, rs.getInt(1));
      rs.close();

      // Check whether we're using SSL
      rs = statement.executeQuery("SHOW STATUS LIKE 'ssl_cipher'");
      rs.next();
      String sslCipher = rs.getString(2);
      rs.close();
      boolean sslActual = sslCipher != null && sslCipher.length() > 0;
      Assert.assertEquals("SSL use does not match", true, sslActual);
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (Exception e) {

        }
      }
    }
  }

  /**
   * Initialize the local variable before each test.
   */
  @Before
  public void init() {
    publicConnectionManager = new ConnectionManager(
            "root",
            "AlienGUIse-93",
            "localhost",
            3306,
            "AlignPublic");
  }

  /**
   * Connect to the database with wrong password.
   * This test expects to be fail.
   *
   * @throws SQLException when something is wrong with DB Connection.
   */
  @Test(expected = SQLException.class)
  public void connectWithWrongPassword() throws SQLException {
    publicConnectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "AlignPublic");
    publicConnectionManager.connect();
  }

  /**
   * Connect using SSL and attempt to validate the server's certificate
   * against the proper pre shared certificate. Make sure to copy the server's
   * certificate to src/test/resources prior to running this. Instructions are
   * in the README file.
   * <p>
   * NOTE: This test will fail if you do not copy the newly generated server
   * certificate before you run it.
   * <p>
   * NOTE: If you're connecting to a remote server that uses a self signed
   * certificate this is how a connection should be made.
   *
   * @throws SQLException when something is wrong with SQL connection.
   * @throws IOException  when something is wrong with the input files.
   */
  @Test
  public void connectSSLWithValidationProperCert() throws SQLException,
          IOException {
    testSslConnection(publicConnectionManager);
  }

  /**
   * Testing the close connection function in Connection Manager
   *
   * @throws SQLException when something is wrong with SQL connection.
   * @throws IOException  when something is wrong with the input files.
   */
  @Test
  public void closeConnection() throws SQLException, IOException {
    Assert.assertTrue(publicConnectionManager.getConnection() == null);
    publicConnectionManager.connect();
    Assert.assertTrue(publicConnectionManager.getConnection() != null);
    publicConnectionManager.closeConnection();
    Assert.assertTrue(publicConnectionManager.getConnection() == null);
    publicConnectionManager.closeConnection();
    Assert.assertTrue(publicConnectionManager.getConnection() == null);
  }
}
