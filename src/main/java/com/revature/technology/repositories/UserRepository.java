package com.revature.technology.repositories;


import com.revature.technology.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository {

    User getUserById(String id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByUsernameAndPassword(String username, String password);

    List<User> getAll();

    List<User> getAllByActive(Boolean active);

    //TODO correct command??
    void update(User updatedObject);


}