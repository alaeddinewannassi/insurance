package org.esprit.repository;

import org.esprit.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class VehicleDaoImpl implements VehicleDao {

    private SessionFactory sessionFactory;

    public VehicleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean saveVehicle(Vehicle vehicle) {
        try {
            Session hibernateSession = sessionFactory.openSession();
            Transaction hibernateTransaction = hibernateSession.beginTransaction();
            hibernateSession.save(vehicle);
            hibernateTransaction.commit();
            hibernateSession.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
