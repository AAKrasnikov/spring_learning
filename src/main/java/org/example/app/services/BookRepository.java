package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository<T> implements ProjectRepository<Book>, ApplicationContextAware {
    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;
    private final NamedParameterJdbcTemplate jdbcTemplete;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplete) {
        this.jdbcTemplete = jdbcTemplete;
    }

    //Понять для чгео данный метод, ведь в параметрах и так создается список...
    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplete.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplete.update("INSERT INTO books(author, title, size) VALUES(:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);
    }
    //При организации удаления по 1 полю и наличии всех текущих кнопок, можно попробовать сокраить кол-во методов удаления.
    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        jdbcTemplete.update("DELETE FROM books WHERE id = :id", parameterSource);
        logger.info("remove book by id completed");
        return true;
    }

    @Override
    public boolean removeByAuthor(String bookAuthorToRemove) {
        int count = 0;
        for (Book book : retreiveAll()) {
            if (book.getAuthor().matches(bookAuthorToRemove)) {
                repo.remove(book);
                count ++;
            }
        }
        if (count > 0) {
            logger.info("remove book by author completed: count = " + count);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeByTitle(String bookTitleToRemove) {
        int count = 0;
        for (Book book : retreiveAll()) {
            if (book.getTitle().matches(bookTitleToRemove)) {
                repo.remove(book);
                count++;
            }
        }
        if (count > 0) {
            logger.info("remove book by author completed: count = " + count);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeBySize(Integer bookSizeToRemove) {
        int count = 0;
        for (Book book : retreiveAll()) {
            if (book.getSize().equals(bookSizeToRemove)) {
                repo.remove(book);
                count++;
            }
        }
        if (count > 0) {
            logger.info("remove book by author completed: count = " + count);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void defaultInit() {
        logger.info("default INIT in book repo bean");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in book repo bean");
    }
}
