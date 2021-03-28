package net.therap;

import net.therap.controller.AnnotatedValidator;
import net.therap.model.Person;
import net.therap.model.Summary;
import net.therap.model.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sadia.afroz
 * @since 3/24/21
 */
public class Validator {
    public static void main(String[] args) {
        Person p = new Person("Abc Efghjkl", 9);
        Summary summary = new Summary(2, p);
        List<ValidationError> errors = new ArrayList<>();
        AnnotatedValidator.validate(summary, errors);
        AnnotatedValidator.print(errors);

    }
}
