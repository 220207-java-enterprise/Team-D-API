package com.revature.technology.services;

import com.revature.technology.dtos.requests.ApproveOrDenyReimbursementRequest;
import com.revature.technology.dtos.responses.Principal;
import com.revature.technology.dtos.responses.ReimbursementResponse;
import com.revature.technology.models.Reimbursement;
import com.revature.technology.models.ReimbursementStatus;
import com.revature.technology.models.User;
import com.revature.technology.repositories.ReimbRepository;
import com.revature.technology.repositories.UserRepository;
import com.revature.technology.util.exceptions.ForbiddenException;
import com.revature.technology.util.exceptions.NotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<ReimbursementResponse> findAllPendingReimbursement(Principal requester){

        if (requester == null){
            throw new NotLoggedInException();
        }

        if (!requester.getRole().equals("FINANCE MANAGER")){
            throw new ForbiddenException();
        }

        List<Reimbursement> pendingReimbursementList  = reimbRepository.getAllByStatus(new ReimbursementStatus("1",
                "PENDING"));

        List<ReimbursementResponse> reimbursementResponseList = new ArrayList<>();
        for(Reimbursement reimbursement : pendingReimbursementList){
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

    public List<ReimbursementResponse> findAllReimbursementsByManager(Principal requester){

        if (requester == null){
            throw new NotLoggedInException();
        }

        User currentUser = userRepository.getUserById(requester.getId());

        if (requester == null){
            throw new NotLoggedInException();
        }

        if (!requester.getRole().equals("FINANCE MANAGER")){
            throw new ForbiddenException();
        }

        List<Reimbursement> resolverReimbursementList  = reimbRepository.getAllByResolverUser(currentUser);

        List<ReimbursementResponse> reimbursementResponseList = new ArrayList<>();
        for(Reimbursement reimbursement : resolverReimbursementList){
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

    public ReimbursementResponse resolveReimbursement(String reimbId, Principal requester,
                                                      ApproveOrDenyReimbursementRequest approveOrDenyReimbursementRequest){

        if (requester == null){
            throw new NotLoggedInException();
        }

        if (!requester.getRole().equals("FINANCE MANAGER")){
            throw new ForbiddenException();
        }

        Reimbursement resolvedReimbursement = new Reimbursement();
        User resolver = userRepository.getUserById(requester.getId());

        if (approveOrDenyReimbursementRequest.getApprove()){
            resolvedReimbursement = reimbRepository.getReimbByReimbId(reimbId);
            resolvedReimbursement.setResolved(LocalDateTime.now());
            resolvedReimbursement.setResolverUser(resolver);
            resolvedReimbursement.setStatus(new ReimbursementStatus("2","APPROVED"));
            reimbRepository.save(resolvedReimbursement);

            ReimbursementResponse reimbursementResponse = new ReimbursementResponse(
                    resolvedReimbursement.getReimbId(),
                    resolvedReimbursement.getAmount(),
                    resolvedReimbursement.getSubmitted(),
                    resolvedReimbursement.getResolved(),
                    resolvedReimbursement.getDescription(),
                    resolvedReimbursement.getReceipt(),
                    resolvedReimbursement.getPaymentId(),
                    resolvedReimbursement.getAuthorUser().getGivenName() + " " + resolvedReimbursement.getAuthorUser().getSurname(),
                    resolvedReimbursement.getResolverUser().getGivenName()+" "+resolvedReimbursement.getResolverUser().getSurname(),
                    resolvedReimbursement.getStatus().getStatus(),
                    resolvedReimbursement.getType().getType()
            );
            return reimbursementResponse;

        } else {
            resolvedReimbursement = reimbRepository.getReimbByReimbId(reimbId);
            resolvedReimbursement.setResolved(LocalDateTime.now());
            resolvedReimbursement.setResolverUser(resolver);
            resolvedReimbursement.setStatus(new ReimbursementStatus("3","DENIED"));
            reimbRepository.save(resolvedReimbursement);

            ReimbursementResponse reimbursementResponse = new ReimbursementResponse(
                    resolvedReimbursement.getReimbId(),
                    resolvedReimbursement.getAmount(),
                    resolvedReimbursement.getSubmitted(),
                    resolvedReimbursement.getResolved(),
                    resolvedReimbursement.getDescription(),
                    resolvedReimbursement.getReceipt(),
                    resolvedReimbursement.getPaymentId(),
                    resolvedReimbursement.getAuthorUser().getGivenName() + " " + resolvedReimbursement.getAuthorUser().getSurname(),
                    resolvedReimbursement.getResolverUser().getGivenName()+" "+resolvedReimbursement.getResolverUser().getSurname(),
                    resolvedReimbursement.getStatus().getStatus(),
                    resolvedReimbursement.getType().getType()
            );

            return reimbursementResponse;
        }

    }
}
