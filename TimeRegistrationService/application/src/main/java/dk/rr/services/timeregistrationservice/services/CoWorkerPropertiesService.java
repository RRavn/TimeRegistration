package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.exceptions.NotFoundException;
import dk.rr.services.timeregistrationservice.models.CoWorker;
import dk.rr.services.timeregistrationservice.models.CoWorkerProperties;
import dk.rr.services.timeregistrationservice.repositories.CoWorkerPropertiesRepository;
import dk.rr.services.timeregistrationservice.repositories.CoWorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CoWorkerPropertiesService {
    private CoWorkerRepository coWorkerRepository;
    private CoWorkerPropertiesRepository coWorkerPropertiesRepository;

    public CoWorkerPropertiesService(CoWorkerRepository coWorkerRepository, CoWorkerPropertiesRepository coWorkerPropertiesRepository) {
        this.coWorkerRepository = coWorkerRepository;
        this.coWorkerPropertiesRepository = coWorkerPropertiesRepository;
    }

    public List<CoWorkerProperties> getAll() {
        return coWorkerPropertiesRepository.findAll();
    }

    public CoWorkerProperties save(CoWorkerProperties coWorkerProperties) {
        return coWorkerPropertiesRepository.save(coWorkerProperties);
    }

    public Optional<CoWorkerProperties> getById(Long id) {
        return coWorkerPropertiesRepository.findById(id);
    }

    public Optional<CoWorkerProperties> getByCoWorkerId(Long id) {
        CoWorker coWorker;
        try {
            coWorker = coWorkerRepository.findById(id).get();
        } catch(NoSuchElementException e) {
            log.error("CoWorker id {} not found", id, e);
            throw new NotFoundException(e.getMessage());
        }
        if (coWorker == null) {
            log.error("CoWorker id {} not found", id);
            throw new NotFoundException("CoWorker id does not exist!");
        }
        return coWorkerPropertiesRepository.findByCoworker(coWorker);
    }

    public void delete(Long id) throws NotFoundException {
        try {
            coWorkerPropertiesRepository.deleteByCoworker(coWorkerRepository.findById(id).get());
        } catch(EmptyResultDataAccessException e) {
            log.error("CoWorker id {} not found", id, e);
            throw new NotFoundException(e.getMessage());
        } catch(NoSuchElementException ne) {
            log.error("CoWorker id {} not found", id, ne);
            throw new NotFoundException(ne.getMessage());
        }
    }
}