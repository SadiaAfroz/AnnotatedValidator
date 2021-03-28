package net.therap.model;

/**
 * @author sadia.afroz
 * @since 3/28/21
 */
public class PersonValidator implements SizeAnnotationClassVale<Person> {
    @Override
    public int getSizeValue(Person person) {
        return person.getAge();
    }
}
