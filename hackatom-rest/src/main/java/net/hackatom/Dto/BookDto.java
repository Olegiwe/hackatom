package net.hackatom.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hackatom.enums.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    Long id;
    String name;
    AuthorDto author;
    AuthorDto coauthor;
    Type type;
}
