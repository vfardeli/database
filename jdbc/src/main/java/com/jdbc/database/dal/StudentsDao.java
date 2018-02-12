package com.jdbc.database.dal;

import com.jdbc.database.model.Students;

public class StudentsDao {
  private ConnectionManager connectionManager;

  // Singleton pattern
  private static StudentsDao instance = null;
  private StudentsDao() {
    connectionManager = new ConnectionManager(
            "root",
            "password",
            "localhost",
            3306,
            "align_public");
  }
  public static StudentsDao getInstance() {
    if (instance == null) {
      instance = new StudentsDao();
    }
    return instance;
  }

  public int getMaleStudentCount() {
    return 0;
  }

  public int getFemaleStudentCount() {
    return 0;
  }
}
