package net.therap.controller;

import net.therap.model.Size;
import net.therap.model.ValidationError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author sadia.afroz
 * @since 3/24/21
 */
public class AnnotatedValidator {

    public static void validate(Object object, List<ValidationError> errors) {
        Class c = object.getClass();
        for (Field field : c.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Size.class)) {
                Annotation annotation = field.getAnnotation(Size.class);
                Size size = (Size) annotation;
                Object value = null;
                try {
                    value = field.get(object);
                    int checkValue = 0;
                    if (value.getClass().equals(Integer.class)) {
                        checkValue = (Integer) value;
                    } else if (value.getClass().equals(String.class)) {
                        checkValue = ((String) value).length();
                    }
                    String outputMessage = size.message().replaceAll("\\{min\\}", Integer.toString(size.min())).replaceAll("\\{max\\}",
                            Integer.toString(size.max()));
                    if (checkValue <= size.min() || checkValue >= size.max()) {
                        ValidationError validationError = new ValidationError(field.getName(), field.getType().getSimpleName(), outputMessage);
                        errors.add(validationError);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
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
