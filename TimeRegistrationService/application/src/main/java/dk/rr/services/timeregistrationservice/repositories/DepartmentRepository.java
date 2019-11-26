package dk.rr.services.timeregistrationservice.repositories;

import dk.rr.services.timeregistrationservice.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    public Department findDistinctByName(String name);

    public List<Department> findAll();

    public Optional<Department> findById(Long id);

    public void deleteById(Long id);
}
