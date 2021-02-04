package net.hackatom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String stationDesignation;
    private String workshopDesignation;
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "systemId", insertable = false, updatable = false)
    private System system;

    private Long systemId;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    List<Defect> defects;


}
