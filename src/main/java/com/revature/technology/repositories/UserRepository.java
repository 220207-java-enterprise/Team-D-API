package com.revature.technology.repositories;


import com.revature.technology.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User getUserByUserId(String id);

    @Query("from User u where u.username = ?1")
    Optional<User> getUserByUsername(String username);

    @Query(
            value = "SELECT * from technology_project.ers_users where email = ?1",
            nativeQuery = true
    )
    User getUserByEmail(String email);

    @Query(
            value = "SELECT * from technology_project.ers_users where user_id = ?1",
            nativeQuery = true
    )
    User getUserById(String id);

    Optional<User> getUserByUsernameAndPassword(String username, String password);


    List<User> getAllByIsActive(Boolean active);

}
