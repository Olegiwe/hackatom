package net.hackatom.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefectDto {

    private Long id;
    private UnitDto unit;
    private String number;
    private LocalDate date;
    private String status;
    private String regPerson;
    private String responsible;
    private String description;
    private String result;
}
