package ru.someboy.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.someboy.springcourse.dao.PersonDAO;
import ru.someboy.springcourse.models.Person;
import ru.someboy.springcourse.util.PersonValidator;

import javax.validation.Valid;

/**
 * @author Slipets Artem
 */
@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    //Получим всех людей из DAO и передадим на отображение в представление
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{personId}")
    //Получим одного человека по id из DAO и передадим на отображение в предаставление
    //Получим список книг этого человека по его id из DAO и передадим на отображение в представление
    public String show(Model model, @PathVariable("personId") int personId) {
        model.addAttribute("person", personDAO.show(personId));
        model.addAttribute("books", personDAO.getBooksByPersonId(personId));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String edit(Model model, @PathVariable("personId") int personId) {
        model.addAttribute("person", personDAO.show(personId));
        return "people/edit";
    }

    @PatchMapping("/{personId}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("personId") int personId) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(personId, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{personId}")
    public String delete(@PathVariable("personId") int personId) {
        personDAO.delete(personId);
        return "redirect:/people";
    }
}
