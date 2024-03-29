package ru.someboy.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.someboy.springcourse.models.Book;
import ru.someboy.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author Slipets Artem
 */
@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int personId) {
        return jdbcTemplate
                .query("SELECT * FROM Person WHERE person_id=?", new BeanPropertyRowMapper<>(Person.class), personId)
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth) VALUES( ?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int personId, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=? WHERE person_id=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), personId);
    }

    public void delete(int personId) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", personId);
    }

    // Для валидации уникальности ФИО
    public Optional<Person> getPersonByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?",
                new BeanPropertyRowMapper<>(Person.class), fullName).stream().findAny();
    }

    // Здесь Join не нужен. И так уже получили человека с помощью отдельного метода
    public List<Book> getBooksByPersonId(int personId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?",
                new BeanPropertyRowMapper<>(Book.class), personId);
    }
}
