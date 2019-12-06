package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.exceptions.NotFoundException;
import dk.rr.services.timeregistrationservice.models.CoWorker;
import dk.rr.services.timeregistrationservice.repositories.CoWorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CoWorkerService {
    private CoWorkerRepository coWorkerRepository;

    public CoWorkerService(CoWorkerRepository coWorkerRepository) {
        this.coWorkerRepository = coWorkerRepository;
    }

    public List<CoWorker> getAll() {
        return coWorkerRepository.findAll();
    }

    public CoWorker save(CoWorker coWorker) {
        return coWorkerRepository.save(coWorker);
    }

    public Optional<CoWorker> getById(Long id) {
        return coWorkerRepository.findById(id);
    }

    public Optional<CoWorker> getByName(String name) {
        return coWorkerRepository.findDistinctByName(name);
    }

    public void delete(Long id) throws NotFoundException {
        try {
            coWorkerRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            log.error("Id {} not found", id, e);
            throw new NotFoundException(e.getMessage());
        }
    }
}