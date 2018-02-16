package com.jdbc.database.servlet;

import com.jdbc.database.dal.script.TotalStudentsInSiliconValleyDao;

import java.sql.SQLException;

public class TotalStudentsInSiliconValleyExtractor implements PrivateToPublicExtractor<Integer> {
  private TotalStudentsInSiliconValleyDao totalStudentsInSiliconValleyDao;

  public TotalStudentsInSiliconValleyExtractor() {
    totalStudentsInSiliconValleyDao = new TotalStudentsInSiliconValleyDao();
  }

  @Override
  public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
    int totalStudents = totalStudentsInSiliconValleyDao.getTotalStudentsInSiliconValleyFromPrivateDatabase();
    totalStudentsInSiliconValleyDao.updateTotalStudentsInSiliconValleyInPublicDatabase(totalStudents);
    return totalStudentsInSiliconValleyDao.getTotalStudentsInSiliconValleyFromPublicDatabase();
  }
}
