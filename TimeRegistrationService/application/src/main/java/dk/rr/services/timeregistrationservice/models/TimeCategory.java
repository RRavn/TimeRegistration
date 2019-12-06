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
@Table(name = "Timecategory")
public class TimeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @NotNull
    @Column(name = "Name")
    private String name;

    TimeCategory(Long id) {
        this.id = id;
    }

    TimeCategory(String name) {
        this.name = name;
    }
}