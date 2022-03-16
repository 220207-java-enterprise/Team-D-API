package com.revature.technology.services;

import com.revature.technology.dtos.responses.Principal;
import com.revature.technology.dtos.responses.ReimbursementResponse;
import com.revature.technology.models.Reimbursement;
import com.revature.technology.models.User;
import com.revature.technology.repositories.ReimbRepository;
import com.revature.technology.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReimbursementService {
    ReimbRepository reimbRepository;
    UserRepository userRepository;

    @Autowired
    public ReimbursementService(ReimbRepository reimbRepository, UserRepository userRepository) {
        this.reimbRepository = reimbRepository;
        this.userRepository = userRepository;
    }

    public List<ReimbursementResponse> findAllReimbursementsByEmployee(Principal requester){
        User currentUser = userRepository.getUserById(requester.getId());
        List<Reimbursement> reimbursementList = reimbRepository.getAllByAuthorUser(currentUser);

        List<ReimbursementResponse> reimbursementResponseList = new ArrayList<>();
        for(Reimbursement reimbursement : reimbursementList){
            ReimbursementResponse reimbursementResponse = new ReimbursementResponse(
                 reimbursement.getReimbId(),
                 reimbursement.getAmount(),
                 reimbursement.getSubmitted(),
                 reimbursement.getResolved(),
                 reimbursement.getDescription(),
                 reimbursement.getReceipt(),
                 reimbursement.getPaymentId(),
                 reimbursement.getAuthorUser().getGivenName() + " " + reimbursement.getAuthorUser().getSurname(),
                 reimbursement.getStatus().getStatus(),
                 reimbursement.getType().getType()
            );
            if(reimbursement.getResolverUser() != null){
                reimbursementResponse.setResolver(reimbursement.getResolverUser().getGivenName() + " " + reimbursement.getResolverUser().getSurname());
            }

            reimbursementResponseList.add(reimbursementResponse);
        }
        return reimbursementResponseList;
    }
}
