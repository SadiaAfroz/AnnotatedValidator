package net.therap.controller;

import net.therap.model.PersonValidator;
import net.therap.model.Size;
import net.therap.model.ValidationError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
                    if (field.getType().isPrimitive()) {
                        printClass(field.getAnnotation(Size.class), value, field, errors);
                    } else {
                        Object[] containedValues = null;
                        if (value instanceof Collection) {
                            containedValues = ((Collection) value).toArray();
                        } else if (value instanceof Map) {
                            containedValues = ((Map) value).values().toArray();
                        } else if (value instanceof Object[]) {
                            containedValues = (Object[]) value;
                        }

                        if (containedValues != null) {
                            // /only primitives
                            Object[] fs = (Object[]) containedValues;
                            for (Object fi : fs) {
                                printClass(field.getAnnotation(Size.class), fi, field, errors);
                            }
                        }
                        // only custom obejct
                        else {
                            printClass(field.getAnnotation(Size.class), value, field, errors);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void printClass(Annotation annotation, Object value, Field parent, List<ValidationError> errors) {
        Class c = value.getClass();
        Size size = (Size) annotation;

        int checkValue = 0;
        if (c.equals(Integer.class)) {
            checkValue = (Integer) value;
        } else if (c.equals(String.class)) {
            checkValue = ((String) value).length();
        } else if (!c.isPrimitive()) {
            try {
                Method sizeValueValidator = size.validatorClass().getMethod("getSizeValue", c);
                PersonValidator instance = new PersonValidator();
                checkValue = (Integer) sizeValueValidator.invoke(instance, value);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        String outputMessage = size.message().replaceAll("\\{min\\}", Integer.toString(size.min())).replaceAll("\\{max\\}",
                Integer.toString(size.max()));
        if (checkValue < size.min() || checkValue > size.max()) {
            ValidationError validationError = new ValidationError(parent.getName(), parent.getType().getSimpleName(), outputMessage);
            errors.add(validationError);
        }
    }

    public static void print(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            System.out.println(error.getName() + "(" + error.getType() + ") : " + error.getErrorMessage());
        }
    }
}

