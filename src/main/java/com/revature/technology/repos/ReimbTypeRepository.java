package com.revature.technology.repos;

import com.revature.technology.models.ReimbursementStatus;
import com.revature.technology.models.ReimbursementType;
import org.springframework.data.repository.CrudRepository;

public interface ReimbTypeRepository extends CrudRepository {

    ReimbursementType getReimbByType_id(String type_id);

    ReimbursementType getReimbByType(String type);
}
