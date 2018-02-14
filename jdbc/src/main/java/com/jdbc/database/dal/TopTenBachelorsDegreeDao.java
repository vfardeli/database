package com.jdbc.database.dal;

import com.jdbc.database.Constants;

import java.sql.SQLException;
import java.util.List;

public class TopTenBachelorsDegreeDao {

  // Singleton pattern
  private static TopTenBachelorsDegreeDao instance = null;

  /**
   * Default Constructor.
   */
  private TopTenBachelorsDegreeDao() { }

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
    PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
    return privateDatabaseEtlQuery.getMultipleValueQuery(
            getTopTenBachelorsDegreeFromPrivateDatabase, "MAJOR");
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
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    publicPublicDatabaseEtlQuery.updateMultipleValueQuery(
            updateTopTenBachelorsDegreeInPublic, topTenBachelorsDegree, Constants.TOP_TEN);
  }

  /**
   * Get the top ten Bachelors degree from the public database.
   * This is a script that gets the information for top ten
   * bachelors degree from the public database.
   *
   * @return Top ten bachelors degree (from public database)
   * @throws SQLException when connection to database has something wrong.
   */
  public List<String> getTopTenBachelorsDegreeFromPublicDatabase() throws SQLException {
    String getTopTenBachelorsDegreeFromPublicDatabase =
            "SELECT BachelorsDegree FROM TopTenBachelorsDegree;";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    return publicPublicDatabaseEtlQuery.getMultipleValueQuery(
            getTopTenBachelorsDegreeFromPublicDatabase, "BachelorsDegree", Constants.TOP_TEN);
  }
}
