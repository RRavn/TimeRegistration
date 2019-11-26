package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.models.Registration;
import dk.rr.services.timeregistrationservice.models.TimeCategory;
import dk.rr.services.timeregistrationservice.repositories.RegistrationRepository;
import dk.rr.services.timeregistrationservice.repositories.TimeCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class RegistrationService {
    private RegistrationRepository registrationRepository;

    public RegistrationService(TimeCategoryRepository timeCategoryRepository, RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public List<Registration> getRegistrations() {
        return registrationRepository.findAll();
    }

    public List<Registration> getRegistrationsByTimeCatecory(TimeCategory timeCategory) {
        return registrationRepository.findByTimeCategory(timeCategory);
    }

    public Registration saveRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    public Optional<Registration> getRegistrationById(Long id) {
        return registrationRepository.findById(id);
    }

    public void deleteRegistrationById(Long id) {
        registrationRepository.deleteById(id);
    }
}