package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.models.TimeCategory;
import dk.rr.services.timeregistrationservice.repositories.TimeCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class TimeCategoryService {
    private TimeCategoryRepository timeCategoryRepository;

    public TimeCategoryService(TimeCategoryRepository timeCategoryRepository) {
        this.timeCategoryRepository = timeCategoryRepository;
    }

    public List<TimeCategory> getTimeCategorys() {
        return timeCategoryRepository.findAll();
    }

    public TimeCategory saveTimeCategory(TimeCategory timeCategory) {
        return timeCategoryRepository.save(timeCategory);
    }

    public Optional<TimeCategory> getTimeCategoryById(Long id) {
        return timeCategoryRepository.findById(id);
    }

    public void deleteTimeCategoryById(Long id) {
        timeCategoryRepository.deleteById(id);
    }
}