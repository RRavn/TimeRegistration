package dk.rr.services.timeregistrationservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Coworker")
public class CoWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @NotNull
    @Column(name = "Name")
    private String name;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "Department_id")
    private Department department;

    CoWorker(Long id) {
        this.id = id;
    }
    
    CoWorker(String name, Department department) {
        this.name = name;
        this.department = department;
    }
}