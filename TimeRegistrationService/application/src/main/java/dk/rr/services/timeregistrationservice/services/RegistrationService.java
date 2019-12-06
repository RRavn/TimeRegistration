package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.exceptions.NotFoundException;
import dk.rr.services.timeregistrationservice.models.Registration;
import dk.rr.services.timeregistrationservice.repositories.CoWorkerRepository;
import dk.rr.services.timeregistrationservice.repositories.RegistrationRepository;
import dk.rr.services.timeregistrationservice.repositories.TimeCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class RegistrationService {
    private TimeCategoryRepository timeCategoryRepository;
    private RegistrationRepository registrationRepository;
    private CoWorkerRepository coWorkerRepository;

    public RegistrationService(TimeCategoryRepository timeCategoryRepository, RegistrationRepository registrationRepository, CoWorkerRepository coWorkerRepository) {
        this.timeCategoryRepository = timeCategoryRepository;
        this.registrationRepository = registrationRepository;
        this.coWorkerRepository = coWorkerRepository;
    }

    public List<Registration> getAll() {
        return registrationRepository.findAll();
    }

    public List<Registration> getByCoWorkerId(Long id) {
        try {
            return registrationRepository.findByCoworker(coWorkerRepository.findById(id).get());
        } catch(NoSuchElementException ne) {
            log.error("CoWorkerId {} not found",id, ne);
            throw new NotFoundException(ne.getMessage());
        }
    }
    public List<Registration> getByTimeCategory(Long timeCategoryId) {
        try {
            return registrationRepository.findByTimeCategory(timeCategoryRepository.findById(timeCategoryId).get());
        } catch(NoSuchElementException ne) {
            log.error("TimeCategoryId {} not found",timeCategoryId, ne);
            throw new NotFoundException(ne.getMessage());
        }
    }

    public List<Registration> getByTimeInterval(Timestamp startTime, Timestamp endTime) {
        return registrationRepository.findAllByArriveTimeBetweenAndLeaveTimeBetween(startTime, endTime, startTime, endTime);
    }

    public List<Registration> getByCoworkerAndTimeInterval(Long coworkerId, Timestamp startTime, Timestamp endTime) {
        return registrationRepository.findByCoworkerAndArriveTimeBetweenAndLeaveTimeBetween(coWorkerRepository.findById(coworkerId).get(), startTime, endTime, startTime, endTime);
    }

    public Registration save(Registration registration) {
        return registrationRepository.save(registration);
    }

    public Optional<Registration> getById(Long id) {
        return registrationRepository.findById(id);
    }

    public void delete(Long id) throws NotFoundException {
        try {
            registrationRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            log.error("Id {} not found",id, e);
            throw new NotFoundException(e.getMessage());
        }
    }
}