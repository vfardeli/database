package com.jdbc.database.dal;

import com.jdbc.database.Constants;
import com.jdbc.database.dal.script.PrivateDatabaseEtlQuery;
import com.jdbc.database.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class StudentsDao {
    private static SessionFactory factory;
    private static Session session;

    /**
     * Default Constructor.
     */
    public StudentsDao() {
        try {
            // it will check the hibernate.cfg.xml file and load it
            // next it goes to all table files in the hibernate file and loads them
            factory = new Configuration().configure().buildSessionFactory();
            session = factory.openSession();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);

        }
    }

    /**
     * This is the function to add a student into database.
     *
     * @param student
     * @return true if insert successfully. Otherwise throws exception.
     */
    public Students addStudent(Students student) {
        Transaction tx = null;

        if(ifNuidExists(student.getNeuId())){
            System.out.println("student already exists!");
        }else{
            System.out.println("saving student in addStudentRecord");
            try {
                tx = session.beginTransaction();
                session.save(student);
                tx.commit();
            } catch (HibernateException e) {
                System.out.println("HibernateException: " + e);
                if (tx!=null) tx.rollback();
            } finally {
//                    session.close();
            }
        }
        return student;
    }

    /**
     * Search a single student record using neu id.
     *
     * @param neuId
     * @return a student object
     */
    public Students getStudentRecord(String neuId) {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE NeuId = :studentNuid ");
        query.setParameter("studentNuid", neuId);
        List list = query.list();
        if(list.size()==1){
            return (Students) list.get(0);
        }else{
            System.out.println("The list should contain only one student with a given nuid");
            return null;
        }
    }

    /**
     * Update a student record.
     *
     * @param student which contains the latest information.
     * @return Updated student object if successful. Otherwise, null.
     */
    public Students updateStudentRecord(Students student) {
        Transaction tx = null;
        String neuId = student.getNeuId();
        if(ifNuidExists(neuId)){
            try{
                Session session = factory.openSession();
                tx = session.beginTransaction();
                String address = student.getAddress();
                String email = student.getEmail();
                String phone = student.getPhoneNum();

                String hql = "UPDATE Students set Address = :address, "  +
                        "Email = :email, " +
                        "Phone = :phone " +
                        "WHERE NeuId = :neuId";
                org.hibernate.query.Query query = session.createQuery(hql);
                query.setParameter("address", address);
                query.setParameter("email", email);
                query.setParameter("phone", phone);
                query.setParameter("neuId", neuId);
                int result = query.executeUpdate();
                System.out.println("Rows affected: " + result);
                tx.commit();
                return student;
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            }
        }else{
            System.out.println("student id doesn't exists..");
        }

        return null;
    }

    /**
     * Delete a student record from database.
     *
     * @param neuId
     * @return true if delete succesfully. Otherwise, false.
     */
    public boolean deleteStudent(String neuId){
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("DELETE FROM Students WHERE NeuId = :studentNuid ");
            query.setParameter("studentNuid", neuId);
            System.out.println("Deleting student for nuid = " + neuId);
            query.executeUpdate();
            tx.commit();
            if(ifNuidExists(neuId)){
                return false;
            }else{
                return true;
            }
        } catch (HibernateException e) {
            System.out.println("exceptionnnnnn");
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
//            session.close();
        }

        return true;
    }

    /**
     *  Get a list of students who have the same first name.
     *
     * @param firstName
     * @return A list of students
     */
    public List<Students> searchStudentRecord(String firstName) {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE FirstName = :studentfirstName ");
        query.setParameter("studentfirstName", firstName);
        List<Students> list = query.list();
        return list;
    }

    /**
     * Get all the students records from database.
     *
     * @return A list of students
     */
    public List<Students> getAllStudents(){
        org.hibernate.query.Query query = session.createQuery("FROM Students");
        List<Students> list = query.list();
        return list;
    }

    /**
     * Check if a specific student existed in database based on neu id.
     *
     * @param neuId
     * @return true if existed, false if not.
     */
    public boolean ifNuidExists(String neuId){
        try{
            System.out.println("Checking if an entered neuId exists or not.......");
            org.hibernate.query.Query query = session.createQuery("FROM Students WHERE NeuId = :studentNeuId");
            query.setParameter("studentNeuId", neuId);
            List list = query.list();
            if(list.size() == 1){
                return true;
            }
        }catch (HibernateException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get the total number of male students in database.
     *
     * @return the number of male students.
     */
    public int countMaleStudents() {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE Gender = 'M'");
        List<Students> list = query.list();
        return list.size();
    }

    /**
     * Get the total number of female students in database.
     *
     * @return the number of female students.
     */
    public int countFemaleStudents() {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE Gender = 'F'");
        List<Students> list = query.list();
        return list.size();
    }

    /**
     * Get a list of similar students.
     *
     * @param degree
     * @return a list of students with the same degree.
     */
    public List<Students> searchSimilarStudents(DegreeCandidacy degree) {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE degreeCandidacy = :degree");
        query.setParameter("degree", degree.name());
        List<Students> list = query.list();
        return list;
    }
}
