package dk.rr.services.timeregistrationservice.repositories;

import dk.rr.services.timeregistrationservice.models.CoWorker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CoWorkerRepository extends JpaRepository<CoWorker, Long> {
    public Optional<CoWorker> findDistinctByName(String name);

    public void deleteById(Long id);
}
