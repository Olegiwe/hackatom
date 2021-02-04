package net.hackatom.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Defect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unitId", insertable = false, updatable = false)
    Unit unit;

    private String number;
    private LocalDate date;
    private String status;
    private String regPerson;
    private String responsible;
    private String description;
    private String result;



}
