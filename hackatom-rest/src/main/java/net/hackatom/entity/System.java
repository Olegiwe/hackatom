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
public class System {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer block;

    @OneToMany(mappedBy = "system", cascade = CascadeType.ALL)
    List<Unit> units;


}
