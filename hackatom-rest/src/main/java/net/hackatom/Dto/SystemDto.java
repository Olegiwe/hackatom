package net.hackatom.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemDto {

    private Long id;
    private String name;
    private Integer block;
    List<UnitDto> units;
}
