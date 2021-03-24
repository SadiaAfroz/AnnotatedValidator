package net.therap.model;

/**
 * @author sadia.afroz
 * @since 3/24/21
 */
public class ValidationError {
    private String name;
    private String type;
    private String errorMessage;

    public ValidationError(String name, String type, String errorMessage) {
        this.name = name;
        this.type = type;
        this.errorMessage = errorMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
