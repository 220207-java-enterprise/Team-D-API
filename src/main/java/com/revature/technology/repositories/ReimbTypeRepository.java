package com.revature.technology.repositories;

import com.revature.technology.models.ReimbursementType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbTypeRepository extends CrudRepository<ReimbursementType, String> {

    //ReimbursementType getReimbByType_id(String type_id);

    //ReimbursementType getReimbByType(String type);
}
