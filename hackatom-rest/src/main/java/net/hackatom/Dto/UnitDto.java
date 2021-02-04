package net.hackatom.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto {

    private Long id;
    private String name;
    private String description;
    private String stationDesignation;
    private String workshopDesignation;
    private String type;
    private SystemDto system;
    private List<DefectDto> defects;

}
