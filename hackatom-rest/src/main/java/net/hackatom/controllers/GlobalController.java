package net.hackatom.controllers;

import net.hackatom.Dto.QueryModifier;
import net.hackatom.Dto.UnitDto;
import net.hackatom.readers.GlobalReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin
public class GlobalController {

    @Autowired
    GlobalReader reader;

    @PostMapping("get-unit-list")
    public List<UnitDto> getUnits(@RequestBody(required = false) QueryModifier modifier) {
        return reader.getUnits(modifier);
    }

}
