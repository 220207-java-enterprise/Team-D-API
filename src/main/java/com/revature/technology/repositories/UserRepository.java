package com.revature.technology.repositories;


import com.revature.technology.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User getUserByUserId(String id);

    @Query("from ers_users u where u.username = ?1")
    List<User> getUsersByUsername(String username);

    @Query(
            value = "SELECT * from ers_users where email = ?1",
            nativeQuery = true
    )
    List<User> getUsersByEmail(String email);

    User getUserByUsernameAndPassword(String username, String password);


    List<User> getAllByIsActive(Boolean active);

}
