package com.revature.technology.repositories;

import com.revature.technology.models.Reimbursement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReimbRepository extends CrudRepository <Reimbursement, String> {

    Reimbursement getReimbById(String id);

    Reimbursement getReimbByAuthor_Id(String id);

    List<Reimbursement> getAll();

    List<Reimbursement> getAllByAuthor_id(String author_user);

    List<Reimbursement> getAllByStatus(String status);

    List<Reimbursement> getAllByType(String type);

    List<Reimbursement> getAllByResolver_id(String resolver_user);



}
