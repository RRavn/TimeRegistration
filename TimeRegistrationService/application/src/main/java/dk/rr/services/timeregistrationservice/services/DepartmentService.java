package dk.rr.services.timeregistrationservice.services;

import dk.rr.services.timeregistrationservice.models.Department;
import dk.rr.services.timeregistrationservice.repositories.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public void deleteDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }
}