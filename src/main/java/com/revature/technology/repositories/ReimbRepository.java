package com.revature.technology.repositories;

import com.revature.technology.models.Reimbursement;
import com.revature.technology.models.ReimbursementStatus;
import com.revature.technology.models.ReimbursementType;
import com.revature.technology.models.User;
import org.hibernate.usertype.UserType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbRepository extends CrudRepository <Reimbursement, String> {

    Reimbursement getReimbByReimbId(String id);

    List<Reimbursement> getAllByStatus(ReimbursementStatus reimbursementStatus);

    List<Reimbursement> getAllByType(ReimbursementType reimbursementType);

    List<Reimbursement> getAllByAuthorUser(User authorUser);

    List<Reimbursement> getAllByResolverUser(User resolverUser);




}
