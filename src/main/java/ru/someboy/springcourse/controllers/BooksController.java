package ru.someboy.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.someboy.springcourse.dao.BookDAO;
import ru.someboy.springcourse.dao.PersonDAO;
import ru.someboy.springcourse.models.Book;
import ru.someboy.springcourse.models.Person;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Slipets Artem
 */
@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    //Получим всех людей из DAO и передадим на отображение в представление
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{bookId}")
    public String show(@PathVariable("bookId") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());

        return "books/show";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int bookId) {
        model.addAttribute("book", bookDAO.show(bookId));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int bookId) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int bookId) {
        bookDAO.delete(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int bookId) {
        bookDAO.release(bookId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int bookId, @ModelAttribute("person")Person selectedPerson) {
        bookDAO.assign(bookId, selectedPerson);
        return "redirect:/books/" +bookId;
    }
}
