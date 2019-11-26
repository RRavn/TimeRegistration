package dk.rr.services.timeregistrationservice.models;

import java.util.Date;

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
    private Date arriveTime;
    @NotNull
    @Column(name = "Leavetime")
    private Date leaveTime;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "Timecategory_id")
    private TimeCategory timeCategory;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "Coworker_id")
    private CoWorker coWorker;

    Registration(Date arriveTime, Date leaveTime, TimeCategory timeCategory, CoWorker coWorker) {
        this.arriveTime = arriveTime;
        this.leaveTime = leaveTime;
        this.timeCategory = timeCategory;
        this.coWorker = coWorker;
    }
}
