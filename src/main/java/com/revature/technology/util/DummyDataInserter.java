package com.revature.technology.util;

import com.revature.technology.models.Reimbursement;
import com.revature.technology.models.ReimbursementStatus;
import com.revature.technology.models.ReimbursementType;
import com.revature.technology.models.User;
import com.revature.technology.models.UserRole;

import com.revature.technology.repositories.ReimbRepository;
import com.revature.technology.repositories.ReimbStatusRepository;
import com.revature.technology.repositories.ReimbTypeRepository;
import com.revature.technology.repositories.UserRepository;
import com.revature.technology.repositories.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
public class DummyDataInserter implements CommandLineRunner{

    private final ReimbRepository reimbursementRepository;
    private final ReimbStatusRepository reimbursementStatusRepository;
    private final ReimbTypeRepository reimbursementTypeRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public DummyDataInserter(ReimbRepository reimbursementRepository, ReimbStatusRepository reimbursementStatusRepository, ReimbTypeRepository reimbursementTypeRepository, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.reimbursementRepository = reimbursementRepository;
        this.reimbursementStatusRepository = reimbursementStatusRepository;
        this.reimbursementTypeRepository = reimbursementTypeRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //Create a table for Reimbursement Status
        ReimbursementStatus ersReimbStatus_PENDING = new ReimbursementStatus();
        ersReimbStatus_PENDING.setStatusId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9h");
        ersReimbStatus_PENDING.setStatus("PENDING");

        ReimbursementStatus ersReimbStatus_APPROVED = new ReimbursementStatus();
        ersReimbStatus_APPROVED.setStatusId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9i");
        ersReimbStatus_APPROVED.setStatus("APPROVED");

        ReimbursementStatus ersReimbStatus_DENIED = new ReimbursementStatus();
        ersReimbStatus_DENIED.setStatusId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9j");
        ersReimbStatus_DENIED.setStatus("DENIED");

        //Create a table for Reimbursement Type
        ReimbursementType ersReimbType_LODGING = new ReimbursementType();
        ersReimbType_LODGING.setTypeId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9d");
        ersReimbType_LODGING.setType("LODGING");

        ReimbursementType ersReimbType_TRAVEL = new ReimbursementType();
        //ersReimbType_TRAVEL.setTypeId(UUID.randomUUID().toString());
        ersReimbType_TRAVEL.setTypeId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9e");
        ersReimbType_TRAVEL.setType("TRAVEL");

        ReimbursementType ersReimbType_FOOD = new ReimbursementType();
        ersReimbType_FOOD.setTypeId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9f");
        ersReimbType_FOOD.setType("FOOD");

        ReimbursementType ersReimbType_OTHER = new ReimbursementType();
        ersReimbType_OTHER.setTypeId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9g");
        ersReimbType_OTHER.setType("OTHER");


        //Create a table for User Role
        UserRole ersUserRole_ADMIN = new UserRole();
        ersUserRole_ADMIN.setRoleId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9a");
        ersUserRole_ADMIN.setRole("ADMIN");

        UserRole ersUserRole_FINANCE_MANAGER = new UserRole();
        ersUserRole_FINANCE_MANAGER.setRoleId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9b");
        ersUserRole_FINANCE_MANAGER.setRole("FINANCE MANAGER");

        UserRole ersUserRole_EMPLOYEE = new UserRole();
        ersUserRole_EMPLOYEE.setRoleId("7c3521f5-ff75-4e8a-9913-01d15ee4dc9c");
        ersUserRole_EMPLOYEE.setRole("EMPLOYEE");


        reimbursementStatusRepository.save(ersReimbStatus_PENDING);
        reimbursementStatusRepository.save(ersReimbStatus_APPROVED);
        reimbursementStatusRepository.save(ersReimbStatus_DENIED);

        reimbursementTypeRepository.save(ersReimbType_LODGING);
        reimbursementTypeRepository.save(ersReimbType_TRAVEL);
        reimbursementTypeRepository.save(ersReimbType_FOOD);
        reimbursementTypeRepository.save(ersReimbType_OTHER);

        userRoleRepository.save(ersUserRole_ADMIN);
        userRoleRepository.save(ersUserRole_FINANCE_MANAGER);
        userRoleRepository.save(ersUserRole_EMPLOYEE);


        //Create a user as ADMIN
        User ersUserADMIN = new User();
        ersUserADMIN.setUserId(UUID.randomUUID().toString());
        ersUserADMIN.setGivenName("ADMIN");
        ersUserADMIN.setSurname("admin");
        ersUserADMIN.setEmail("admin@gmail.com");
        ersUserADMIN.setUsername("adminadmin");
        ersUserADMIN.setPassword("Revature99?");
        ersUserADMIN.setIsActive(true);
        ersUserADMIN.setRole(ersUserRole_ADMIN);

        userRepository.save(ersUserADMIN);
    }
}
