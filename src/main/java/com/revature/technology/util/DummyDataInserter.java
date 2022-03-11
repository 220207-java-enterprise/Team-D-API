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
        UserRole userRole1 = new UserRole();
        userRole1.setRole(UUID.randomUUID().toString());
        userRole1.setRoleId(UUID.randomUUID().toString());

        UserRole userRole2 = new UserRole();
        userRole2.setRole(UUID.randomUUID().toString());
        userRole2.setRoleId(UUID.randomUUID().toString());

        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setUsername(UUID.randomUUID().toString());
        user1.setEmail(UUID.randomUUID().toString());
        user1.setPassword(UUID.randomUUID().toString());
        user1.setGivenName(UUID.randomUUID().toString());
        user1.setSurname(UUID.randomUUID().toString());
        user1.setIsActive(false);
        user1.setRole(userRole1);

        User user2 = new User();
        user2.setUserId(UUID.randomUUID().toString());
        user2.setUsername(UUID.randomUUID().toString());
        user2.setEmail(UUID.randomUUID().toString());
        user2.setPassword(UUID.randomUUID().toString());
        user2.setGivenName(UUID.randomUUID().toString());
        user2.setSurname(UUID.randomUUID().toString());
        user2.setIsActive(false);
        user2.setRole(userRole2);

        ReimbursementStatus reimbursementStatus = new ReimbursementStatus();
        reimbursementStatus.setStatus(UUID.randomUUID().toString());
        reimbursementStatus.setStatusId(UUID.randomUUID().toString());

        ReimbursementType reimbursementType = new ReimbursementType();
        reimbursementType.setType(UUID.randomUUID().toString());
        reimbursementType.setTypeId(UUID.randomUUID().toString());

        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setReimbId(UUID.randomUUID().toString());
        Random random = new Random();
        reimbursement.setAmount(random.nextDouble());
        reimbursement.setSubmitted(LocalDateTime.now());
        reimbursement.setResolved(LocalDateTime.now());
        reimbursement.setDescription(UUID.randomUUID().toString());
        byte[] byteArray = new byte[10];
        random.nextBytes(byteArray);
        reimbursement.setReceipt(byteArray);
        reimbursement.setPaymentId(UUID.randomUUID().toString());
        reimbursement.setAuthorUser(user1);
        reimbursement.setResolverUser(user2);
        reimbursement.setStatus(reimbursementStatus);
        reimbursement.setType(reimbursementType);

        UserRole userRoleAdmin = new UserRole();
        userRoleAdmin.setRole("ADMIN");
        userRoleAdmin.setRoleId("ADMIN");

        UserRole userRoleFinance = new UserRole();
        userRoleFinance.setRole("FINANCE MANAGER");
        userRoleFinance.setRoleId("FINANCE MANAGER");

        UserRole userRoleEmployee = new UserRole();
        userRoleEmployee.setRole("EMPLOYEE");
        userRoleEmployee.setRoleId("EMPLOYEE");

        userRoleRepository.save(userRoleAdmin);
        userRoleRepository.save(userRoleFinance);
        userRoleRepository.save(userRoleEmployee);


        userRoleRepository.save(userRole1);
        userRoleRepository.save(userRole2);

        userRepository.save(user1);
        userRepository.save(user2);

        reimbursementStatusRepository.save(reimbursementStatus);
        reimbursementTypeRepository.save(reimbursementType);

        reimbursementRepository.save(reimbursement);
    }
}
