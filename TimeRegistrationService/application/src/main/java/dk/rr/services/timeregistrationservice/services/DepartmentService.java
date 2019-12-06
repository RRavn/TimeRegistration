package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.exceptions.NotFoundException;
import dk.rr.services.timeregistrationservice.models.Department;
import dk.rr.services.timeregistrationservice.repositories.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public Optional<Department> getById(Long id) {
        return departmentRepository.findById(id);
    }

    public void delete(Long id) throws NotFoundException {
        try {
            departmentRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            log.error("Id {} not found", id, e);
            throw new NotFoundException(e.getMessage());
        }
    }
}