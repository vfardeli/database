package com.jdbc.database.servlet;

import com.jdbc.database.dal.script.TotalStudentsInCharlotteDao;

import java.sql.SQLException;

public class TotalStudentsInCharlotteExtractor implements PrivateToPublicExtractor<Integer> {
  private TotalStudentsInCharlotteDao totalStudentsInCharlotteDao;

  public TotalStudentsInCharlotteExtractor() {
    totalStudentsInCharlotteDao = new TotalStudentsInCharlotteDao();
  }

  @Override
  public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
    int totalStudents = totalStudentsInCharlotteDao.getTotalStudentsInCharlotteFromPrivateDatabase();
    totalStudentsInCharlotteDao.updateTotalStudentsInCharlotteInPublicDatabase(totalStudents);
    return totalStudentsInCharlotteDao.getTotalStudentsInCharlotteFromPublicDatabase();
  }
}
