package ru.someboy.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Slipets Artem
 */
public class Person {
    private int personId;

    @NotEmpty(message = "Поле 'ФИО' не должно быть пустым")
    @Size(min = 2, max = 100, message = "Поле 'ФИО' должно быть от 2 до 50 символов длиной")
    private String fullName;

    @Min(value = 1900, message = "Поле 'Дата рождения' не должно быть меньше 1900 года")
    private int yearOfBirth;

    //Пустой конструктор нужен для Spring, аннотация ModelAttribute
    public Person() {

    }

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
