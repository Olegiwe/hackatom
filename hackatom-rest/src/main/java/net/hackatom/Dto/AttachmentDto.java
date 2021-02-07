package net.hackatom.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hackatom.entity.Unit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {

    private Long id;
    private Long systemId;
    private Unit unit;
    private Long defectId;
    private String name;

}
