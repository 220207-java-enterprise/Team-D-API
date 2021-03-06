package com.revature.technology.repositories;


import com.revature.technology.models.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, String> {

    UserRole getUserRoleByRoleId(String id);

    UserRole getUserRoleByRole(String name);

//    @Query("from UserRole ur where ur.role_id = ?1")
//    List<UserRole> getUserRoleByID(String id);

}
