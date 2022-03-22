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

        UserRole manager = new UserRole();
        manager.setRoleId("2");
        manager.setRole("FINANCE MANAGER");

        UserRole employee = new UserRole();
        employee.setRoleId("3");
        employee.setRole("EMPLOYEE");


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
        User ersUserAdmin = new User();
        ersUserAdmin.setUserId("1");
        ersUserAdmin.setGivenName("Super");
        ersUserAdmin.setSurname("Admin");
        ersUserAdmin.setEmail("admin@revature.com");
        ersUserAdmin.setUsername("superadmin");
        ersUserAdmin.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        ersUserAdmin.setIsActive(true);
        ersUserAdmin.setRole(admin);
        userRepository.save(ersUserAdmin);

        User ersUserFinanceManager = new User();
        ersUserFinanceManager.setUserId(UUID.randomUUID().toString());
        ersUserFinanceManager.setGivenName("Wezley");
        ersUserFinanceManager.setSurname("Singleton");
        ersUserFinanceManager.setEmail("wezleysingleton@revature.com");
        ersUserFinanceManager.setUsername("wezleysingleton");
        ersUserFinanceManager.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        ersUserFinanceManager.setIsActive(true);
        ersUserFinanceManager.setRole(manager);
        userRepository.save(ersUserFinanceManager);

        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setGivenName("Ryan");
        user1.setSurname("Odeneal");
        user1.setEmail("ryanodeneal@revature.net");
        user1.setUsername("ryanodeneal");
        user1.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user1.setIsActive(true);
        user1.setRole(employee);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUserId(UUID.randomUUID().toString());
        user2.setGivenName("Zhenying");
        user2.setSurname("Chen");
        user2.setEmail("zhenyingchen@revature.net");
        user2.setUsername("zhenyingchen");
        user2.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user2.setIsActive(true);
        user2.setRole(employee);
        userRepository.save(user2);

        User user3 = new User();
        user3.setUserId(UUID.randomUUID().toString());
        user3.setGivenName("Adam");
        user3.setSurname("Lyn");
        user3.setEmail("adamlyn@revature.net");
        user3.setUsername("adamlyn");
        user3.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user3.setIsActive(true);
        user3.setRole(employee);
        userRepository.save(user3);

        User user4 = new User();
        user4.setUserId(UUID.randomUUID().toString());
        user4.setGivenName("Abhilekh");
        user4.setSurname("Adhikari");
        user4.setEmail("abhilekhadhikari@revature.net");
        user4.setUsername("abhilekhadhikari");
        user4.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user4.setIsActive(true);
        user4.setRole(employee);
        userRepository.save(user4);

        User user5 = new User();
        user5.setUserId(UUID.randomUUID().toString());
        user5.setGivenName("Aidan");
        user5.setSurname("Amato");
        user5.setEmail("aidanamato@revature.net");
        user5.setUsername("aidanamato");
        user5.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user5.setIsActive(true);
        user5.setRole(employee);
        userRepository.save(user5);

        User user6 = new User();
        user6.setUserId(UUID.randomUUID().toString());
        user6.setGivenName("Aidan");
        user6.setSurname("Arnold");
        user6.setEmail("aidanarnold@revature.net");
        user6.setUsername("aidanarnold");
        user6.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user6.setIsActive(true);
        user6.setRole(employee);
        userRepository.save(user6);

        User user7 = new User();
        user7.setUserId(UUID.randomUUID().toString());
        user7.setGivenName("Aiza");
        user7.setSurname("Weber");
        user7.setEmail("aizaweber@revature.net");
        user7.setUsername("aizaweber");
        user7.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user7.setIsActive(true);
        user7.setRole(employee);
        userRepository.save(user7);

        User user8 = new User();
        user8.setUserId(UUID.randomUUID().toString());
        user8.setGivenName("Ajitesh");
        user8.setSurname("Vedula");
        user8.setEmail("ajiteshvedula@revature.net");
        user8.setUsername("ajiteshvedula");
        user8.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user8.setIsActive(true);
        user8.setRole(employee);
        userRepository.save(user8);

        User user9 = new User();
        user9.setUserId(UUID.randomUUID().toString());
        user9.setGivenName("Amelia");
        user9.setSurname("DePew");
        user9.setEmail("ameliadepew@revature.net");
        user9.setUsername("ameliadepew");
        user9.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user9.setIsActive(true);
        user9.setRole(employee);
        userRepository.save(user9);

        User user10 = new User();
        user10.setUserId(UUID.randomUUID().toString());
        user10.setGivenName("Andrew");
        user10.setSurname("Saenz");
        user10.setEmail("andrewsaenz@revature.net");
        user10.setUsername("andrewsaenz");
        user10.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user10.setIsActive(true);
        user10.setRole(employee);
        userRepository.save(user10);

        User user11 = new User();
        user11.setUserId(UUID.randomUUID().toString());
        user11.setGivenName("Arthur");
        user11.setSurname("Davidson");
        user11.setEmail("arthurdavidson@revature.net");
        user11.setUsername("arthurdavidson");
        user11.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user11.setIsActive(true);
        user11.setRole(employee);
        userRepository.save(user11);

        User user12 = new User();
        user12.setUserId(UUID.randomUUID().toString());
        user12.setGivenName("Cameron");
        user12.setSurname("Lintz");
        user12.setEmail("cameronlintz@revature.net");
        user12.setUsername("cameronlintz");
        user12.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user12.setIsActive(true);
        user12.setRole(employee);
        userRepository.save(user12);

        User user13 = new User();
        user13.setUserId(UUID.randomUUID().toString());
        user13.setGivenName("Carlos");
        user13.setSurname("Iniguez");
        user13.setEmail("carlosiniguez@revature.net");
        user13.setUsername("carlosiniguez");
        user13.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user13.setIsActive(true);
        user13.setRole(employee);
        userRepository.save(user13);

        User user14 = new User();
        user14.setUserId(UUID.randomUUID().toString());
        user14.setGivenName("Ebenezer");
        user14.setSurname("Belay");
        user14.setEmail("ebenezerbelay@revature.net");
        user14.setUsername("ebenezerbelay");
        user14.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user14.setIsActive(true);
        user14.setRole(employee);
        userRepository.save(user14);

        User user15 = new User();
        user15.setUserId(UUID.randomUUID().toString());
        user15.setGivenName("Khari");
        user15.setSurname("Thompson");
        user15.setEmail("kharithompson@revature.net");
        user15.setUsername("kharithompson");
        user15.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user15.setIsActive(true);
        user15.setRole(employee);
        userRepository.save(user15);

        User user16 = new User();
        user16.setUserId(UUID.randomUUID().toString());
        user16.setGivenName("Trevor");
        user16.setSurname("Thomas");
        user16.setEmail("trevorthomas@revature.net");
        user16.setUsername("trevorthomas");
        user16.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user16.setIsActive(true);
        user16.setRole(employee);
        userRepository.save(user16);

    }
}