package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.models.CoWorker;
import dk.rr.services.timeregistrationservice.repositories.CoWorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CoWorkerService {
    private CoWorkerRepository coWorkerRepository;

    public CoWorkerService(CoWorkerRepository coWorkerRepository) {
        this.coWorkerRepository = coWorkerRepository;
    }

    public List<CoWorker> getCoWorkers() {
        return coWorkerRepository.findAll();
    }

    public CoWorker saveCoWorker(CoWorker coWorker) {
        return coWorkerRepository.save(coWorker);
    }

    public Optional<CoWorker> getCoWorkerById(Long id) {
        return coWorkerRepository.findById(id);
    }

    public Optional<CoWorker> getCoWorkerByName(String name) {
        return coWorkerRepository.findDistinctByName(name);
    }

    public void deleteCoWorkerById(Long id) {
        coWorkerRepository.deleteById(id);
    }
}