package dk.rr.services.timeregistrationservice.repositories;

import dk.rr.services.timeregistrationservice.models.TimeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TimeCategoryRepository extends JpaRepository<TimeCategory, Long> {
    public Optional<TimeCategory> findDistinctByName(String name);

    public List<TimeCategory> findAllByName(String name);

    public Optional<TimeCategory> findById(Long id);

    public void deleteById(Long id);
}
