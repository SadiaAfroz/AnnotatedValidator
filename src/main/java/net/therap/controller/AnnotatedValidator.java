package net.therap.controller;

import net.therap.model.Person;
import net.therap.model.Size;
import net.therap.model.ValidationError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sadia.afroz
 * @since 3/24/21
 */
public class AnnotatedValidator {
    public static Pattern Annotation_Attribute_Match = Pattern.compile("\\{\\w+\\}");

    public static void validate(Person p, List<ValidationError> errors) {
        Class c = p.getClass();
        for (Field field : c.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Size.class)) {
                Annotation annotation = field.getAnnotation(Size.class);
                Size size = (Size) annotation;
                String original = size.message();
                Matcher matcher = Annotation_Attribute_Match.matcher(original);

                if (field.getType().equals(Integer.TYPE)) {
                    if (p.getAge() < size.min()) {

                        int lastIndex = 0;
                        StringBuilder output = new StringBuilder();
                        while (matcher.find()) {
                            output.append(original, lastIndex, matcher.start()).append(size.min());
                            lastIndex = matcher.end();
                        }
                        if (lastIndex < original.length()) {
                            output.append(original, lastIndex, original.length());
                        }

                        ValidationError validationError = new ValidationError(field.getName(), (String) field.getType().toString(), output.toString());
                        errors.add(validationError);
                    }
                }
                if (field.getType() == (String.class)) {
                    if (p.getName().length() > size.max()) {

                        int lastIndex = 0, count = 0;
                        StringBuilder output = new StringBuilder();
                        while (matcher.find()) {
                            if (count == 0) {
                                output.append(original, lastIndex, matcher.start())
                                        .append(size.min());
                            } else {
                                output.append(original, lastIndex, matcher.start())
                                        .append(size.max());
                            }
                            lastIndex = matcher.end();
                            count++;
                        }
                        if (lastIndex < original.length()) {
                            output.append(original, lastIndex, original.length());
                        }

                        ValidationError validationError = new ValidationError(field.getName(), (String) field.getType().getSimpleName(), output.toString());
                        errors.add(validationError);
                    }
                }
            }
        }
    }

    public static void print(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            System.out.println(error.getName() + "(" + error.getType() + ") : " + error.getErrorMessage());
        }
    }
}
