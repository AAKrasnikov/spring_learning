package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository<T> implements ProjectRepository<Book>, ApplicationContextAware {
    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;


    //Понять для чгео данный метод, ведь в параметрах и так создается список...
    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("store new book: " + book);
        repo.add(book);
    }
    //При организации удаления по 1 полю и наличии всех текущих кнопок, можно попробовать сокраить кол-во методов удаления.
    @Override
    public boolean removeItemById(String bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book by id completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
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
