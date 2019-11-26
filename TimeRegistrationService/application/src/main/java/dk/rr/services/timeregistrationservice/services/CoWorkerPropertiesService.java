package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.models.CoWorkerProperties;
import dk.rr.services.timeregistrationservice.repositories.CoWorkerPropertiesRepository;
import dk.rr.services.timeregistrationservice.repositories.CoWorkerRepository;
import lombok.extern.slf4j.Slf4j;
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

    public CoWorkerProperties saveCoWorkerProperties(CoWorkerProperties coWorkerProperties) {
        return coWorkerPropertiesRepository.save(coWorkerProperties);
    }

    public Optional<CoWorkerProperties> getCoWorkerPropertiesByCoWorkerId(Long id) {
        return coWorkerPropertiesRepository.findByCoworker(coWorkerRepository.findById(id).get());
    }
    public void deleteCoWorkerPropertiesByCoWorkerId(Long id) {
        coWorkerPropertiesRepository.deleteByCoworker(coWorkerRepository.findById(id).get());
    }
}