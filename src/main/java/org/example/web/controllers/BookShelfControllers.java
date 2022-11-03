package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/books")
public class BookShelfControllers {
    private Logger logger = Logger.getLogger(BookShelfControllers.class);
    private BookService bookService;

    @Autowired
    public BookShelfControllers(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    //Придумать как добавить уведомление для пользователя о не корректном заполнении полей
    //необходимо поменять логику на: дать возможность сохранять записи даже по 1 заполненому полю
    @PostMapping("/save")
    public String saveBook(Book book) {
        if (book.getAuthor().equals("") || book.getTitle().equals("") || book.getSize() == null) {
            logger.info("cannot add book");
            return "redirect:/books/shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    //Придумать как добавить уведомление для пользователя о не корректном заполнении полей
    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
        if (bookService.removeBookById(bookIdToRemove)) {
            logger.info("remove book");
            return "redirect:/books/shelf";
        } else {
            logger.info("cannot remove book");
            return "redirect:/books/shelf";
        }
    }

}
