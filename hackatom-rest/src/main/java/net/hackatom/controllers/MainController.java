package net.hackatom.controllers;


import net.hackatom.Dto.BookDto;
import net.hackatom.entity.Book;
import net.hackatom.enums.Type;
import net.hackatom.readers.TestReader;
import net.hackatom.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("test")
public class MainController {

    @Autowired
    TestReader testReader;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("test")
    public List<BookDto> test() {
        return testReader.test();
    }

    @GetMapping("save")
    public Book save() {
        return bookRepository.save(new Book(2L, "NAME", 1L, Type.DIGIT));
    }


}
