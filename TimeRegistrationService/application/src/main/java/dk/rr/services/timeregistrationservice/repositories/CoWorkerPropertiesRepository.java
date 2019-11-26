package dk.rr.services.timeregistrationservice.repositories;

import dk.rr.services.timeregistrationservice.models.CoWorker;
import dk.rr.services.timeregistrationservice.models.CoWorkerProperties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CoWorkerPropertiesRepository extends JpaRepository<CoWorkerProperties, Long> {
    public Optional<CoWorkerProperties> findByCoworker(CoWorker coworker);

    public Optional<CoWorkerProperties> findById(Long id);

    public void deleteByCoworker(CoWorker coworker);
}
