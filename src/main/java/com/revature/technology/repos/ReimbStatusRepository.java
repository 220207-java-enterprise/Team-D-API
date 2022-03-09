package com.revature.technology.repos;

import com.revature.technology.models.ReimbursementStatus;
import org.springframework.data.repository.CrudRepository;

public interface ReimbStatusRepository extends CrudRepository {

    ReimbursementStatus getReimbByStatus_id(String status_id);

    ReimbursementStatus getReimbByStatus(String status);
}
