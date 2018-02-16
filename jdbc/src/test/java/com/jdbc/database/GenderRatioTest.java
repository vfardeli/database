//package com.jdbc.database;
//
//import com.jdbc.database.dal.StudentsDao;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//public class GenderRatioTest {
//
//  @Mock
//  private StudentsDao studentsDaoMock;
//
//  @Before
//  public void init() {
//    studentsDaoMock = Mockito.mock(StudentsDao.class);
//  }
//
//  private String getGenderRatio(StudentsDao studentsDao) {
//    int maleCount = studentsDao.getMaleStudentCount();
//    int femaleCount = studentsDao.getFemaleStudentCount();
//    if (maleCount == femaleCount) {
//      return ("1 : 1");
//    } else if (maleCount > femaleCount) {
//      return String.valueOf(maleCount / femaleCount) + " : 1";
//    } else {
//      return "1 : " + String.valueOf(femaleCount / maleCount);
//    }
//  }
//
//  @Test
//  public void testGetGenderRatio() {
//    Mockito.when(studentsDaoMock.getMaleStudentCount()).thenReturn(1);
//    Mockito.when(studentsDaoMock.getFemaleStudentCount()).thenReturn(1);
//    String genderRatio = getGenderRatio(studentsDaoMock);
//    Assert.assertTrue(genderRatio.equals("1 : 1"));
//    Mockito.when(studentsDaoMock.getMaleStudentCount()).thenReturn(5);
//    Mockito.when(studentsDaoMock.getFemaleStudentCount()).thenReturn(2);
//    genderRatio = getGenderRatio(studentsDaoMock);
//    Assert.assertTrue(genderRatio.equals("2 : 1"));
//    Mockito.when(studentsDaoMock.getMaleStudentCount()).thenReturn(11);
//    Mockito.when(studentsDaoMock.getFemaleStudentCount()).thenReturn(35);
//    genderRatio = getGenderRatio(studentsDaoMock);
//    Assert.assertTrue(genderRatio.equals("1 : 3"));
//  }
//
//}
