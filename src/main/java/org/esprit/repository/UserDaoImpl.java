package org.esprit.repository;

import org.esprit.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean saveUser(User user) {
        try {
            Session hibernateSession = sessionFactory.openSession();
            Transaction hibernateTransaction = hibernateSession.beginTransaction();
            hibernateSession.save(user);
            hibernateTransaction.commit();
            hibernateSession.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public User getUser(String userName, String password) {
        try {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from User where userName='" + userName + "' and password='" + password + "'");
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


}
