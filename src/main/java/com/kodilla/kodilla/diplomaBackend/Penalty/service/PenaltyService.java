package com.kodilla.kodilla.diplomaBackend.Penalty.service;

import com.kodilla.kodilla.diplomaBackend.Penalty.domain.Penalty;
import com.kodilla.kodilla.diplomaBackend.Penalty.repository.PenaltyRepository;
import com.kodilla.kodilla.diplomaBackend.Rent.service.RentService;
import com.kodilla.kodilla.diplomaBackend.LogHistory.service.LogHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PenaltyService {

    @Autowired
    private PenaltyRepository penaltyRepository;

    @Autowired
    private RentService rentService;

    @Autowired
    private LogHistoryService logHistoryService;

    public Penalty chargePenalty(Penalty penalty){
        rentService.applyPenalty(penalty);
        logHistoryService.saveLog(penalty.getRent().getUser(), "Penalty for: " + penalty.getReason());
        return penaltyRepository.save(penalty);
    }

    public void cancelPenalty(long id){
        Penalty penalty = penaltyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        rentService.recallPenalty(penalty);
        logHistoryService.saveLog(penalty.getRent().getUser(), "Penalty cancellation - id: " + id);
        penaltyRepository.deleteById(id);
    }

}
