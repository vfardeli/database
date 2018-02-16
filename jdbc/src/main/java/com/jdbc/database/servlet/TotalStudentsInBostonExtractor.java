package com.jdbc.database.servlet;

import com.jdbc.database.dal.script.TotalStudentsInBostonDao;

import java.sql.SQLException;

public class TotalStudentsInBostonExtractor implements PrivateToPublicExtractor<Integer> {
  private TotalStudentsInBostonDao totalStudentsInBostonDao;

  public TotalStudentsInBostonExtractor() {
    totalStudentsInBostonDao = new TotalStudentsInBostonDao();
  }

  @Override
  public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
    int totalStudents = totalStudentsInBostonDao.getTotalStudentsInBostonFromPrivateDatabase();
    totalStudentsInBostonDao.updateTotalStudentsInBostonInPublicDatabase(totalStudents);
    return totalStudentsInBostonDao.getTotalStudentsInBostonFromPublicDatabase();
  }
}
