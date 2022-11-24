package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfControllers {
    private Logger logger = Logger.getLogger(BookShelfControllers.class);
    private BookService bookService;

    @Autowired
    public BookShelfControllers(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    //Придумать как добавить уведомление для пользователя о не корректном заполнении полей
    //Если ввести буквы в поле size выдает ошибку и переводит на пустую страницу - устранить
    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            if (book.getAuthor().equals("") && book.getTitle().equals("") && book.getSize() == null) {
                logger.info("cannot add book");
                return "redirect:/books/shelf";
            } else {
                bookService.saveBook(book);
                logger.info("current repository size: " + bookService.getAllBooks().size());
                return "redirect:/books/shelf";
            }
        }
    }

    //Придумать как добавить уведомление для пользователя о не корректном заполнении полей
    @PostMapping("/removeByID")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByAuthor")
    public String removeBookAuthor(@RequestParam(value = "bookAuthorToRemove") String bookAuthorToRemove) {
        if (bookService.removeBookByAuthor(bookAuthorToRemove)) {
            logger.info("remove book");
            return "redirect:/books/shelf";
        } else {
            logger.info("cannot remove book");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByTitle")
    public String removeBookTitle(@RequestParam(value = "bookTitleToRemove") String bookTitleToRemove) {
        if (bookService.removeBookByTitle(bookTitleToRemove)) {
            logger.info("remove book");
            return "redirect:/books/shelf";
        } else {
            logger.info("cannot remove book");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeBySize")
    public String removeBookSize(@RequestParam(value = "bookSizeToRemove") Integer bookSizeToRemove) {
        if (bookService.removeBookBySize(bookSizeToRemove)) {
            logger.info("remove book");
            return "redirect:/books/shelf";
        } else {
            logger.info("cannot remove book");
            return "redirect:/books/shelf";
        }
    }
    //Можно реализовать ввод данных для удаления в одно поле, но оставить все имеющиеся кнопки для удаления.
}
