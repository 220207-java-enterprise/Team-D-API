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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Component
public class DummyDataInserter implements CommandLineRunner {

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
        String payeeId1 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user1);
        user1.setPayeeId(payeeId1);
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
        String payeeId2 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user2);
        user2.setPayeeId(payeeId2);
        userRepository.save(user2);

        User user3 = new User();
        user3.setUserId(UUID.randomUUID().toString());
        user3.setGivenName("Adam");
        user3.setSurname("Lyn");
        user3.setEmail("adamlyn@revature.net");
        user3.setUsername("adamlyn1");
        user3.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user3.setIsActive(true);
        user3.setRole(employee);
        String payeeId3 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user3);
        user3.setPayeeId(payeeId3);
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
        String payeeId4 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user4);
        user4.setPayeeId(payeeId4);
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
        String payeeId5 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user5);
        user5.setPayeeId(payeeId5);
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
        String payeeId6 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user6);
        user6.setPayeeId(payeeId6);
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
        String payeeId7 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user7);
        user7.setPayeeId(payeeId7);
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
        String payeeId8 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user8);
        user8.setPayeeId(payeeId8);
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
        String payeeId9 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user9);
        user9.setPayeeId(payeeId9);
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
        String payeeId10 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user10);
        user10.setPayeeId(payeeId10);
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
        String payeeId11 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user11);
        user11.setPayeeId(payeeId11);
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
        String payeeId12 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user12);
        user12.setPayeeId(payeeId12);
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
        String payeeId13 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user13);
        user13.setPayeeId(payeeId13);
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
        String payeeId14 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user14);
        user14.setPayeeId(payeeId14);
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
        String payeeId15 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user15);
        user15.setPayeeId(payeeId15);
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
        String payeeId16 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user16);
        user16.setPayeeId(payeeId16);
        userRepository.save(user16);

        User user17 = new User();
        user17.setUserId(UUID.randomUUID().toString());
        user17.setGivenName("Claire");
        user17.setSurname("Abel");
        user17.setEmail("claireabel@revature.net");
        user17.setUsername("claireabel");
        user17.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user17.setIsActive(false);
        user17.setRole(employee);
        String payeeId17 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user17);
        user17.setPayeeId(payeeId17);
        userRepository.save(user17);

        User user18 = new User();
        user18.setUserId(UUID.randomUUID().toString());
        user18.setGivenName("Janay");
        user18.setSurname("Seymour");
        user18.setEmail("janaysemour@revature.net");
        user18.setUsername("janaysemour");
        user18.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user18.setIsActive(false);
        user18.setRole(employee);
        String payeeId18 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user18);
        user18.setPayeeId(payeeId18);
        userRepository.save(user18);

        User user19 = new User();
        user19.setUserId(UUID.randomUUID().toString());
        user19.setGivenName("Bao");
        user19.setSurname("Duong");
        user19.setEmail("baoduong@revature.net");
        user19.setUsername("baoduong");
        user19.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user19.setIsActive(false);
        user19.setRole(manager);

        User user20 = new User();
        user20.setUserId(UUID.randomUUID().toString());
        user20.setGivenName("Azhya");
        user20.setSurname("Knox");
        user20.setEmail("azhyaknox@revature.net");
        user20.setUsername("azhyaknox");
        user20.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user20.setIsActive(false);
        user20.setRole(manager);;


        User user21 = new User();
        user21.setUserId("2");
        user21.setGivenName("Guy");
        user21.setSurname("Dood");
        user21.setEmail("guydood@gmail.com");
        user21.setUsername("HandsomeDevil");
        user21.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user21.setIsActive(true);
        user21.setRole(new UserRole("2", "FINANCE MANAGER"));

        User user22 = new User();
        user22.setUserId("3");
        user22.setGivenName("Lady");
        user22.setSurname("Gal");
        user22.setEmail("ladygal@gmail.com");
        user22.setUsername("WonderWoman");
        user22.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        user22.setIsActive(true);
        user22.setRole(new UserRole("3", "EMPLOYEE"));
        String payeeId22 = prismClient.registerNewEmployeeUsingPrism(getAuthOrg(), user22);
        user22.setPayeeId(payeeId22);


        User myadmin = new User();
        myadmin.setUserId("4");
        myadmin.setGivenName("Hugh");
        myadmin.setSurname("Jackman");
        myadmin.setEmail("wolverine@gmail.com");
        myadmin.setUsername("iamwolverine");
        myadmin.setPassword("$2a$10$Z/xu6Ujt7T5.lzZfBUkNA.WVvB3llWOaumUT7VI2.TfmybZbrjq0G");
        myadmin.setIsActive(true);
        myadmin.setRole(new UserRole("1", "ADMIN"));

        userRepository.save(user21);
        userRepository.save(user22);
        userRepository.save(myadmin);

        // registerNewEmployeeUsingPrism(Authorg)

        //-------Reimbursement Dummy Data-------------------------------
        List<Reimbursement> dumReimb = new ArrayList<Reimbursement>();
        System.out.println("WE OUT HERE-----------------------------------------------");
        for(int i = 1; i < 100; i++){
            Reimbursement myReimb = new Reimbursement();
            myReimb.setReimbId(String.valueOf(i));
            myReimb.setAmount((Math.random()*(9999+1)));
            myReimb.setSubmitted(LocalDateTime.now());
            myReimb.setDescription("Dummy Dum Data");
            myReimb.setAuthorUser(userRepository.getRandomEmp());
            int myType = (int) Math.floor(Math.random()*(3)+1);
            myReimb.setType(reimbursementTypeRepository
                    .getReimbByTypeId(String.valueOf(myType)));
            int myStatus = (int) (Math.floor(Math.random()*(3)+1));
            myReimb.setStatus(reimbursementStatusRepository.
                    getReimbByStatusId(String.valueOf(myStatus)));
            if (myStatus != 1) {
                myReimb.setResolved(LocalDateTime.now());
                myReimb.setResolverUser(userRepository.getRandomFM());
                String paymentId = prismClient.postPaymentUsingPrism(getAuthOrg(),
                        myReimb.getAuthorUser(), myReimb);
                myReimb.setPaymentId(paymentId);
            }

            reimbursementRepository.save(myReimb);
        }

        Reimbursement myReimb1 = new Reimbursement();
        myReimb1.setReimbId(String.valueOf(199));
        myReimb1.setAmount((Math.random()*(9999+1)));
        myReimb1.setSubmitted(LocalDateTime.now());
        myReimb1.setDescription("Dummy Dum Data Nonviolatile");
        myReimb1.setAuthorUser(user16);
        int myType = 1;
        myReimb1.setType(reimbursementTypeRepository
                .getReimbByTypeId(String.valueOf(myType)));
        int myStatus = 1;
        myReimb1.setStatus(reimbursementStatusRepository.
                getReimbByStatusId(String.valueOf(myStatus)));
        if (myStatus != 1) {
            myReimb1.setResolved(LocalDateTime.now());
            myReimb1.setResolverUser(userRepository.getRandomFM());
            String paymentId = prismClient.postPaymentUsingPrism(getAuthOrg(),
                    myReimb1.getAuthorUser(), myReimb1);
            myReimb1.setPaymentId(paymentId);
        }

        reimbursementRepository.save(myReimb1);

        Reimbursement myReimb2 = new Reimbursement();
        myReimb2.setReimbId(String.valueOf(200));
        myReimb2.setAmount((Math.random()*(9999+1)));
        myReimb2.setSubmitted(LocalDateTime.now());
        myReimb2.setDescription("Dummy Dum Data Nonviolatile");
        myReimb2.setAuthorUser(user16);
        myType = 1;
        myReimb2.setType(reimbursementTypeRepository
                .getReimbByTypeId(String.valueOf(myType)));
        myStatus = 2;
        myReimb2.setStatus(reimbursementStatusRepository.
                getReimbByStatusId(String.valueOf(myStatus)));
        if (myStatus != 1) {
            myReimb2.setResolved(LocalDateTime.now());
            myReimb2.setResolverUser(userRepository.getRandomFM());
            String paymentId = prismClient.postPaymentUsingPrism(getAuthOrg(),
                    myReimb2.getAuthorUser(), myReimb2);
            myReimb2.setPaymentId(paymentId);
        }

        reimbursementRepository.save(myReimb2);
    }
}