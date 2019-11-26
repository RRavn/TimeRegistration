package dk.rr.services.timeregistrationservice.models;

import java.sql.Time;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Coworkerproperties")
public class CoWorkerProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @NotNull
    @Column(name = "Fixedarrivetime")
    private Time fixedArriveTime;
    @NotNull
    @Column(name = "Fixedleavetime")
    private Time fixedLeaveTime;
    @NotNull
    @Column(name = "Fixedtimemin")
    private int flexTimeMin;
    @NotNull
    @Column(name = "Fixedtimemax")
    private int flexTimeMax;
    @NotNull
    @Column(name = "Overtimerate")
    private int overTimeRate;
    @NotNull
    @OneToOne
    @JoinColumn(name = "Coworker_id")
    private CoWorker coworker;

    CoWorkerProperties(Time fixedArriveTime, Time fixedLeaveTime, int flexTimeMin, int flexTimeMax, int overTimeRate, CoWorker coWorker) {
        this.fixedArriveTime = fixedArriveTime;
        this.fixedLeaveTime = fixedLeaveTime;
        this.flexTimeMin = flexTimeMin;
        this.flexTimeMax = flexTimeMax;
        this.overTimeRate = overTimeRate;
        this.coworker = coWorker;
    }
}
