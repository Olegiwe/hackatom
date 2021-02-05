package net.hackatom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "schema", cascade = CascadeType.ALL)
    private Set<Region> regions;

    @OneToOne(mappedBy = "schema_id")
    private Attachment attachment;
}
