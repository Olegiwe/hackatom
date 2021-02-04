package net.hackatom.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {

    private Long id;
    private Long systemId;
    private Long unitId;
    private Long defectId;
    private String name;

}
