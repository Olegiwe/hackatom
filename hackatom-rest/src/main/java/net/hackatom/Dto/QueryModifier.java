package net.hackatom.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryModifier {

    private Long limit;
    private Long offset;
    private int sortOrder;
    private String sortField;
    private List<PageFilter> filters;

}
