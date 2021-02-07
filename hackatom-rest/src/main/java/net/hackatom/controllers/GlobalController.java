package net.hackatom.controllers;

import net.hackatom.Dto.Page;
import net.hackatom.Dto.QueryModifier;
import net.hackatom.Dto.SchemaDto;
import net.hackatom.readers.GlobalReader;
import net.hackatom.readers.SchemaReader;
import net.hackatom.services.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("api")
@CrossOrigin
public class GlobalController {

    @Autowired
    GlobalReader reader;

    @Autowired
    private SchemaService schemaService;

    @Autowired
    private SchemaReader schemaReader;

    @PostMapping("get-unit-list")
    public Page<?> getUnits(@RequestBody(required = false) QueryModifier modifier) {
        return reader.getUnits(modifier);
    }

    @PostMapping("get-defect-list")
    public Page<?> getDefects(@RequestBody(required = false) QueryModifier modifier) {
        return reader.getDefects(modifier);
    }

    @GetMapping(value = "schemas/{id}", produces = "text/plain")
    public byte[] getSchemaAsImage(@PathVariable Long id) {
        return schemaService.getImage(id);
    }

    @GetMapping("schema/{id}")
    public SchemaDto getSchema(@PathVariable Long id) {
        return this.schemaReader.getSchema(id);
    }

}
