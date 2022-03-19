package com.revature.technology.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.technology.dtos.prism.responses.AuthOrganizationPrincipal;
import com.revature.technology.dtos.prism.responses.OrgRegistrationResponse;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
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
    private final PrismClient prismClient;

    private final OrgRegistrationResponse principalOrg;
    private final ResponseEntity<AuthOrganizationPrincipal> authOrg;

    @Autowired
    public DummyDataInserter(ReimbRepository reimbursementRepository,
                             ReimbStatusRepository reimbursementStatusRepository,
                             ReimbTypeRepository reimbursementTypeRepository, UserRepository userRepository,
                             UserRoleRepository userRoleRepository, PrismClient prismClient) throws JsonProcessingException {
        this.reimbursementRepository = reimbursementRepository;
        this.reimbursementStatusRepository = reimbursementStatusRepository;
        this.reimbursementTypeRepository = reimbursementTypeRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.prismClient = prismClient;

        // prism setup
        this.principalOrg = prismClient.registerNewOrganizationUsingPrism();
        this.authOrg = prismClient.authenticateOrganizationUsingPrism(principalOrg);
    }

    // will be used in UserService everytime a new user is registered
    public ResponseEntity<AuthOrganizationPrincipal> getAuthOrg() {
        return authOrg;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //Create a table for Reimbursement Status
        //Create a table for Reimbursement Status
        ReimbursementStatus ersReimbStatus_PENDING = new ReimbursementStatus();
        ersReimbStatus_PENDING.setStatusId("1");
        ersReimbStatus_PENDING.setStatus("PENDING");

        ReimbursementStatus ersReimbStatus_APPROVED = new ReimbursementStatus();
        ersReimbStatus_APPROVED.setStatusId("2");
        ersReimbStatus_APPROVED.setStatus("APPROVED");

        ReimbursementStatus ersReimbStatus_DENIED = new ReimbursementStatus();
        ersReimbStatus_DENIED.setStatusId("3");
        ersReimbStatus_DENIED.setStatus("DENIED");

        //Create a table for Reimbursement Type
        ReimbursementType ersReimbType_LODGING = new ReimbursementType();
        ersReimbType_LODGING.setTypeId("1");
        ersReimbType_LODGING.setType("LODGING");

        ReimbursementType ersReimbType_TRAVEL = new ReimbursementType();
        ersReimbType_TRAVEL.setTypeId("2");
        ersReimbType_TRAVEL.setType("TRAVEL");

        ReimbursementType ersReimbType_FOOD = new ReimbursementType();
        ersReimbType_FOOD.setTypeId("3");
        ersReimbType_FOOD.setType("FOOD");

        ReimbursementType ersReimbType_OTHER = new ReimbursementType();
        ersReimbType_OTHER.setTypeId("4");
        ersReimbType_OTHER.setType("OTHER");


        //Create a table for User Role
        UserRole admin = new UserRole();
        admin.setRoleId("1");
        admin.setRole("ADMIN");

        UserRole employee = new UserRole();
        employee.setRoleId("2");
        employee.setRole("FINANCE MANAGER");

        UserRole manager = new UserRole();
        manager.setRoleId("3");
        manager.setRole("EMPLOYEE");


        reimbursementStatusRepository.save(ersReimbStatus_PENDING);
        reimbursementStatusRepository.save(ersReimbStatus_APPROVED);
        reimbursementStatusRepository.save(ersReimbStatus_DENIED);

        reimbursementTypeRepository.save(ersReimbType_LODGING);
        reimbursementTypeRepository.save(ersReimbType_TRAVEL);
        reimbursementTypeRepository.save(ersReimbType_FOOD);
        reimbursementTypeRepository.save(ersReimbType_OTHER);

        userRoleRepository.save(admin);
        userRoleRepository.save(employee);
        userRoleRepository.save(manager);


        //Create a user as ADMIN
        User ersUserADMIN = new User();
        ersUserADMIN.setUserId("1");
        ersUserADMIN.setGivenName("Abhilekh");
        ersUserADMIN.setSurname("Adhikari");
        ersUserADMIN.setEmail("4bhilekh@gmail.com");
        ersUserADMIN.setUsername("4bhilekh");
        ersUserADMIN.setPassword("p4$$WORD");
        ersUserADMIN.setIsActive(true);
        ersUserADMIN.setRole(admin);

        userRepository.save(ersUserADMIN);


        User myadmin = new User();
        myadmin.setUserId("4");
        myadmin.setGivenName("Hugh");
        myadmin.setSurname("Jackman");
        myadmin.setEmail("wolverine@gmail.com");
        myadmin.setUsername("iamwolverine");
        myadmin.setPassword("p4$$WORD");
        myadmin.setIsActive(true);
        myadmin.setRole(admin);

        User user1 = new User();
        user1.setUserId("2");
        user1.setGivenName("Guy");
        user1.setSurname("Dood");
        user1.setEmail("guydood@gmail.com");
        user1.setUsername("HandsomeDevil");
        user1.setPassword("p4$$WORD");
        user1.setIsActive(true);
        user1.setRole(new UserRole("2", "FINANCE MANAGER"));

        User user2 = new User();
        user2.setUserId("3");
        user2.setGivenName("Lady");
        user2.setSurname("Gal");
        user2.setEmail("ladygal@gmail.com");
        user2.setUsername("WonderWoman");
        user2.setPassword("p4$$WORD");
        user2.setIsActive(true);
        user2.setRole(new UserRole("3", "EMPLOYEE"));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(myadmin);
    }
}