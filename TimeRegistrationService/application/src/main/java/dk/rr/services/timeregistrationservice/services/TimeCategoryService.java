package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.exceptions.NotFoundException;
import dk.rr.services.timeregistrationservice.models.TimeCategory;
import dk.rr.services.timeregistrationservice.repositories.TimeCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class TimeCategoryService {
    private TimeCategoryRepository timeCategoryRepository;

    public TimeCategoryService(TimeCategoryRepository timeCategoryRepository) {
        this.timeCategoryRepository = timeCategoryRepository;
    }

    public List<TimeCategory> getAll() {
        return timeCategoryRepository.findAll();
    }

    public TimeCategory save(TimeCategory timeCategory) {
        return timeCategoryRepository.save(timeCategory);
    }

    public Optional<TimeCategory> getById(Long id) {
        return timeCategoryRepository.findById(id);
    }

    public void delete(Long id) throws NotFoundException {
        try {
            timeCategoryRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            log.error("Id {} not found", id, e);
            throw new NotFoundException(e.getMessage());
        }
    }
}