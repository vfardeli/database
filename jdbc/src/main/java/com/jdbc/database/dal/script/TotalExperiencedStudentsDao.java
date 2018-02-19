package com.jdbc.database.dal.script;

import java.sql.SQLException;

public class TotalExperiencedStudentsDao {
    // Singleton pattern
    private static TotalExperiencedStudentsDao instance = null;

    /**
     * Default Constructor.
     */
    public TotalExperiencedStudentsDao() { }

    /**
     * Singleton Pattern.
     *
     * @return Total Students With Work Experience Dao.
     * @throws SQLException when connection to the DB has something wrong.
     */
    public static TotalExperiencedStudentsDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new TotalExperiencedStudentsDao();
        }

        return instance;
    }

    /**
     * Get total number of students who have working experience
     * from private database.
     *
     * @return Total number of experienced students
     * @throws Exception
     */
    public int getTotalStudentsFromPrivateDB() throws Exception {
        String query = "SELECT COUNT(*) AS TOTAL_EXPERIENCED_STUDENT FROM ( " +
                "SELECT S.NeuId, COUNT(*) " +
                "FROM AlignPrivate.Students AS S " +
                "INNER JOIN AlignPrivate.WorkExperiences AS E ON S.NeuId = E.NeuId " +
                "GROUP BY S.NeuId) AS Count_NeuId;";

        PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
        return privateDatabaseEtlQuery.getSingleValueQuery(
                query,
                "TOTAL_EXPERIENCED_STUDENT");
    }

    /**
     * Update the number of experienced students in public database.
     *
     * @param totalExperiencedStudents
     * @throws Exception
     */
    public void updateTotalExperiencedStudents(int totalExperiencedStudents) throws Exception {
        String updateTotalExperiencedStudents =
                "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE " +
                        "DataKey = \"TotalStudentsWithWorkExperience\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateTotalExperiencedStudents, totalExperiencedStudents);
    }

    /**
     * Get the number of experienced students from public database.
     *
     * @return the number of experienced students.
     * @throws Exception
     */
    public int getTotalExperiencedStudents() throws Exception {
        String updateTotalExperiencedStudents =
                "SELECT DataValue FROM SingleValueAggregatedData " +
                        "WHERE DataKey = \"TotalStudentsWithWorkExperience\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        return publicPublicDatabaseEtlQuery.getSingleValueQuery(updateTotalExperiencedStudents);
    }
}
