package com.revature.technology.repositories;

import com.revature.technology.models.Reimbursement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbRepository extends CrudRepository <Reimbursement, String> {

    Reimbursement getReimbByReimbId(String id);

    List<Reimbursement> getAllByStatusId(String status);

    List<Reimbursement> getAllByTypeId(String type);

    List<Reimbursement> getAllByAuthorUser(String author_user);

    List<Reimbursement> getAllByResolverUser(String resolver_user);




}
