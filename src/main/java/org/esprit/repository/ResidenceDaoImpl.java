package org.esprit.repository;

import org.esprit.model.Residence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ResidenceDaoImpl implements ResidenceDao {

    private SessionFactory sessionFactory;

    public ResidenceDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean saveResidence(Residence residence) {
        try {
            Session hibernateSession = sessionFactory.openSession();
            Transaction hibernateTransaction = hibernateSession.beginTransaction();
            hibernateSession.save(residence);
            hibernateTransaction.commit();
            hibernateSession.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
