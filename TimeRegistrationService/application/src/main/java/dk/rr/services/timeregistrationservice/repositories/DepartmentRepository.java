package dk.rr.services.timeregistrationservice.repositories;

import dk.rr.services.timeregistrationservice.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    public void deleteById(Long id);
}
