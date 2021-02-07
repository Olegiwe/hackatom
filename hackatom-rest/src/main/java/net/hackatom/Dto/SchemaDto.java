package net.hackatom.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SchemaDto {
    private Long id;
    private Set<RegionDto> region;
    private AttachmentDto attachment;
    private AttachmentDto anotherAttachment;
}
