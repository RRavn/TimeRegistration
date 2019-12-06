package dk.rr.services.timeregistrationservice.models;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @NotNull
    @Column(name = "Arrivetime")
    private Timestamp arriveTime;
    @NotNull
    @Column(name = "Leavetime")
    private Timestamp leaveTime;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "Timecategory_id")
    private TimeCategory timeCategory;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "Coworker_id")
    private CoWorker coworker;

    Registration(Timestamp arriveTime, Timestamp leaveTime, TimeCategory timeCategory, CoWorker coWorker) {
        this.arriveTime = arriveTime;
        this.leaveTime = leaveTime;
        this.timeCategory = timeCategory;
        this.coworker = coWorker;
    }
}