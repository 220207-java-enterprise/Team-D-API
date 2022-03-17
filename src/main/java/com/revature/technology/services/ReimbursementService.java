package com.revature.technology.services;

import com.revature.technology.dtos.requests.NewReimbursementRequest;
import com.revature.technology.dtos.requests.UpdatePendingReimbursementRequest;
import com.revature.technology.dtos.responses.Principal;
import com.revature.technology.dtos.responses.ReimbursementResponse;
import com.revature.technology.models.Reimbursement;
import com.revature.technology.models.ReimbursementStatus;
import com.revature.technology.models.ReimbursementType;
import com.revature.technology.models.User;
import com.revature.technology.repositories.ReimbRepository;
import com.revature.technology.repositories.ReimbStatusRepository;
import com.revature.technology.repositories.ReimbTypeRepository;
import com.revature.technology.repositories.UserRepository;
import com.revature.technology.util.exceptions.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReimbursementService {
    ReimbRepository reimbRepository;
    UserRepository userRepository;
    ReimbTypeRepository reimbTypeRepository;
    ReimbStatusRepository reimbStatusRepository;

    @Autowired
    public ReimbursementService(ReimbRepository reimbRepository, UserRepository userRepository,ReimbTypeRepository reimbTypeRepository, ReimbStatusRepository reimbStatusRepository) {
        this.reimbRepository = reimbRepository;
        this.userRepository = userRepository;
        this.reimbTypeRepository = reimbTypeRepository;
        this.reimbStatusRepository = reimbStatusRepository;
    }

    //For Employee ---------------------------------------------------------------------------------------
    public List<ReimbursementResponse> findAllPendingReimbursementsByEmployee(Principal requester){
        User currentUser = userRepository.getUserById(requester.getId());
        List<Reimbursement> reimbursementList = reimbRepository.getAllPendingByAuthorUser(currentUser, "PENDING");

        List<ReimbursementResponse> reimbursementResponseList = ConvertReimbListToReimbResponseList(reimbursementList);
        return reimbursementResponseList;
    }

    public  String updatePendingReimbursementByEmployee(UpdatePendingReimbursementRequest updatePendingReimbursementRequest, Principal requester){
        Reimbursement reimbursement = reimbRepository.getReimbByReimbId(updatePendingReimbursementRequest.getReimbursementId());

        if(reimbursement == null){
            throw new InvalidRequestException("reimbursement does not exist");
        }

        if(updatePendingReimbursementRequest.getAmount()>0 && updatePendingReimbursementRequest.getAmount()<10000){
            reimbursement.setAmount(updatePendingReimbursementRequest.getAmount());
        }

        if(updatePendingReimbursementRequest.getDescription() != null){
            reimbursement.setDescription(updatePendingReimbursementRequest.getDescription());
        }

        if(updatePendingReimbursementRequest.getReceipt() != null){
            reimbursement.setReceipt(updatePendingReimbursementRequest.getReceipt());
        }

       if(updatePendingReimbursementRequest.getReimbursementType().equals("LODGING") ||
            updatePendingReimbursementRequest.getReimbursementType().equals("TRAVEL") ||
            updatePendingReimbursementRequest.getReimbursementType().equals("FOOD") ||
            updatePendingReimbursementRequest.getReimbursementType().equals("OTHER")
        ){
           reimbursement.setType(reimbTypeRepository.getReimbByType(updatePendingReimbursementRequest.getReimbursementType()));
        }else if (updatePendingReimbursementRequest.getReimbursementType() != null){
            throw new InvalidRequestException("reimbursement type does not exist");
        }

        reimbRepository.save(reimbursement);

        return "successful update your reimbursement.";
    }

    public List<ReimbursementResponse> findAllReimbursementsByEmployee(Principal requester){
        User currentUser = userRepository.getUserById(requester.getId());
        List<Reimbursement> reimbursementList = reimbRepository.getAllByAuthorUser(currentUser);

        List<ReimbursementResponse> reimbursementResponseList = ConvertReimbListToReimbResponseList(reimbursementList);
        return reimbursementResponseList;
    }

    public String submitNewReimbursementRequestByEmployee (NewReimbursementRequest newReimbursementRequest, Principal requester){
        User currentUser = userRepository.getUserById(requester.getId());
        ReimbursementType reimbursementType = reimbTypeRepository.getReimbByType(newReimbursementRequest.getReimbursementType());
        ReimbursementStatus reimbursementStatus = reimbStatusRepository.getReimbByStatus("PENDING");
        String newReimbursementId = UUID.randomUUID().toString();

        Reimbursement newReimbursement = new Reimbursement();
        newReimbursement.setReimbId(newReimbursementId);
        newReimbursement.setAmount(newReimbursementRequest.getAmount());
        newReimbursement.setSubmitted(LocalDateTime.now());
        newReimbursement.setDescription(newReimbursementRequest.getDescription());
        newReimbursement.setReceipt(newReimbursementRequest.getReceipt());
        newReimbursement.setAuthorUser(currentUser);
        newReimbursement.setType(reimbursementType);
        newReimbursement.setStatus(reimbursementStatus);

        reimbRepository.save(newReimbursement);
        return newReimbursementId;
    }
    //---------------------------------------------------------------------------------------













    //Helper function: use to convert List<Reimbursement> ---> List<ReimbursementResponse>
    private List<ReimbursementResponse> ConvertReimbListToReimbResponseList (List<Reimbursement> reimbursementList){
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
