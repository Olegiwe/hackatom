package net.hackatom.readers;


import com.querydsl.jpa.impl.JPAQueryFactory;
import net.hackatom.Dto.BookDto;
import net.hackatom.entity.QAuthor;
import net.hackatom.entity.QBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TestReader {

    @Autowired
    ReaderHelper readerHelper;

    @Autowired
    EntityManager entityManager;

    private static final QBook book = QBook.book;
    private static final QAuthor author = QAuthor.author;
    private static final QAuthor coauthor = new QAuthor("coauthor");

    public List<BookDto> test() {

        JPAQueryFactory factory = new JPAQueryFactory(entityManager);

        return readerHelper.selectDto(BookDto.class, factory.from(book)
                .leftJoin(author).on(book.authorId.eq(author.id))
                .leftJoin(coauthor).on(book.coauthorId.eq(coauthor.id)));
    }

}
