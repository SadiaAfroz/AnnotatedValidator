package net.therap.model;

/**
 * @author sadia.afroz
 * @since 3/28/21
 */
public class PersonValidator implements SizeAnnotationClassValue<Person> {
    @Override
    public int getSizeValue(Person person) {
        return person.getAge();
    }
}
