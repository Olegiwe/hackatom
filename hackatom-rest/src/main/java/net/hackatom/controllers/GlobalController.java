package net.hackatom.controllers;

import net.hackatom.Dto.Page;
import net.hackatom.Dto.QueryModifier;
import net.hackatom.readers.GlobalReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin
public class GlobalController {

    @Autowired
    GlobalReader reader;

    @PostMapping("get-unit-list")
    public Page<?> getUnits(@RequestBody(required = false) QueryModifier modifier) {
        return reader.getUnits(modifier);
    }

    @PostMapping("get-defect-list")
    public Page<?> getDefects(@RequestBody(required = false) QueryModifier modifier) {
        return reader.getDefects(modifier);
    }

}
