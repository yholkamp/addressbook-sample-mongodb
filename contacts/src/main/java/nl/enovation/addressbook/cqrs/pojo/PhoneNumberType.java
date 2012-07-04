package nl.enovation.addressbook.cqrs.pojo;

/**
 * Enum to represent the different types of PhoneNumber entries we identify in our system.
 */
public enum PhoneNumberType {
    HOME("Home"), WORK("Work"), FAX("Fax"), OTHER("Other");

    private String description;

    private PhoneNumberType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return name();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(String value) {
    }
}
