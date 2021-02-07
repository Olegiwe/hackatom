package net.hackatom.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hackatom.enums.Type;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue
    Long id;
    String name;
    Long authorId;
    Long coauthorId;
    @Enumerated(EnumType.STRING)
    Type type;

}
