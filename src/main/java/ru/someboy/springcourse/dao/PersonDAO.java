package ru.someboy.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.someboy.springcourse.models.Person;

import java.util.List;

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
                .query("SELECT * FROM Person WHERE person_id=?", new Object[]{personId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, year_of_birth) VALUES( ?, ?)",
                person.getName(), person.getYearOfBirth());
    }

    public void update(int personId, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, year_of_birth=? WHERE person_id=?",
                updatedPerson.getName(), updatedPerson.getYearOfBirth(), personId);
    }

    public void delete(int personId) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", personId);
    }
}
