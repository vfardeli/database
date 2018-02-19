package com.jdbc.database.dal.script;

import java.sql.SQLException;

public class TotalStudentsInFacebookDao {
    // Singleton pattern
    private static TotalStudentsInFacebookDao instance = null;

    /**
     * Default Constructor.
     */
    public TotalStudentsInFacebookDao() { }

    /**
     * Singleton Pattern.
     *
     * @return Total Students Who Worked In Facebook Dao.
     * @throws SQLException when connection to the DB has something wrong.
     */
    public static TotalStudentsInFacebookDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new TotalStudentsInFacebookDao();
        }

        return instance;
    }

    /**
     * Get total number of students who worked in Facebook
     * from private database.
     *
     * @return Total number of students who worked in Facebook
     * @throws Exception
     */
    public int getTotalStudentsFromPrivateDB() throws Exception {
        String query = "SELECT COUNT(*) AS TOTAL_FACEBOOK_STUDENT FROM ( " +
                "SELECT S.NeuId, COUNT(*) " +
                "FROM AlignPrivate.Students AS S " +
                "INNER JOIN AlignPrivate.WorkExperiences AS E ON S.NeuId = E.NeuId " +
                "INNER JOIN AlignPrivate.Companies AS C ON E.CompanyId = C.CompanyId " +
                "WHERE C.CompanyName = \"Facebook\" " +
                "GROUP BY S.NeuId) AS Student_Facebook;";

        PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
        return privateDatabaseEtlQuery.getSingleValueQuery(
                query,
                "TOTAL_FACEBOOK_STUDENT");
    }

    /**
     * Update the number of students who worked in Facebook in public database.
     *
     * @param totalStudentsInFacebook
     * @throws Exception
     */
    public void updateTotalStudentsInFacebook(int totalStudentsInFacebook) throws Exception {
        String updateStudentsInFacebook =
                "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE " +
                        "DataKey = \"TotalStudentsWhoWorkInFacebook\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateStudentsInFacebook, totalStudentsInFacebook);
    }

    /**
     * Get the number of students who worked in Facebook from public database.
     *
     * @return the number of students who worked in Facebook.
     * @throws Exception
     */
    public int getTotalStudentsInFacebook() throws Exception {
        String updateStudentsInFacebook =
                "SELECT DataValue FROM SingleValueAggregatedData " +
                        "WHERE DataKey = \"TotalStudentsWhoWorkInFacebook\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        return publicPublicDatabaseEtlQuery.getSingleValueQuery(updateStudentsInFacebook);
    }
}
