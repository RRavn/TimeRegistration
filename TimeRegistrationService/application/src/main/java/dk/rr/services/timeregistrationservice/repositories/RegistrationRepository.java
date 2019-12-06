package dk.rr.services.timeregistrationservice.repositories;

import dk.rr.services.timeregistrationservice.models.Registration;
import dk.rr.services.timeregistrationservice.models.TimeCategory;
import dk.rr.services.timeregistrationservice.models.CoWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    public List<Registration> findByCoworker(CoWorker coworker);

    public List<Registration> findByTimeCategory(TimeCategory timeCategory);

    public List<Registration> findAllByArriveTimeBetweenAndLeaveTimeBetween(Timestamp arriveStartTime, Timestamp arriveEndTime, Timestamp leaveStartTime, Timestamp leaveEndTime);

    public List<Registration> findByCoworkerAndArriveTimeBetweenAndLeaveTimeBetween(CoWorker coworker, Timestamp arriveStartTime, Timestamp arriveEndTime, Timestamp leaveStartTime, Timestamp leaveEndTime);

    public void deleteById(Long id);

}
