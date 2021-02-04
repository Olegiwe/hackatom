package net.hackatom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long systemId;
    private Long unitId;
    private Long defectId;
    private String name;
    private byte[] file;






}
