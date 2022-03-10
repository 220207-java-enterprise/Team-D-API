package com.revature.technology.repositories;

import com.revature.technology.models.ReimbursementStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReimbStatusRepository extends CrudRepository<ReimbursementStatus, String> {

    ReimbursementStatus getReimbByStatusId(String status_id);

    ReimbursementStatus getReimbByStatus(String status);
}
