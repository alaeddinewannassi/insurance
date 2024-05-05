package org.esprit.repository;


import org.esprit.model.User;

public interface UserDao {
    boolean saveUser(User user);
    User getUser(String userName, String password);
}
