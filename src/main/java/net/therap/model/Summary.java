package net.therap.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sadia.afroz
 * @since 3/27/21
 */
public class Summary {
    @Size(min = 1000, max = 50000)
    private int salary;

    @Size(min = 3, max = 5)
    private Map<Integer, String> check;

    @Size(min = 5, max = 8, validatorClass = PersonValidator.class)
    private Person person;

    public Summary(int salary, Person person) {
        this.salary = salary;
        this.person = person;
        check = new HashMap<>();
        check.put(2, "lead");
        check.put(6, "training");
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Map<Integer, String> getCheck() {
        return check;
    }

    public void setCheck(Map<Integer, String> check) {
        this.check = check;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
