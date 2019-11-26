package dk.rr.services.timeregistrationservice.repositories;

import dk.rr.services.timeregistrationservice.models.Registration;
import dk.rr.services.timeregistrationservice.models.TimeCategory;
import dk.rr.services.timeregistrationservice.models.CoWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Repository
@Transactional
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    public List<Registration> findByCoWorker(CoWorker coWorker);

    public List<Registration> findByTimeCategory(TimeCategory timeCategory);

    public List<Registration> findByArriveTimeAndLeaveTime(Date arriveTime, Date leaveTime);

    public List<Registration> findByCoWorkerAndArriveTimeAndLeaveTime(CoWorker coWorker, Date arriveTime, Date leaveTime);

    public Optional<Registration> findById(Long id);

    public void deleteById(Long id);

}
