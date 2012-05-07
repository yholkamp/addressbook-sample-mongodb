package org.axonframework.samples.trader.contacts.api;

import org.springframework.util.Assert;

public abstract class AbstractContactCrudCommand extends AbstractContactCommand {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String street;
    private String city;
    private String zipCode;
    private String department;

    /**
     * Set the name for the new Contact. An exception is thrown when the provided name is empty
     *
     * @param firstName String containing the name for the new contact
     */
    public void setFirstName(String firstName) {
        Assert.hasText(firstName, "Name for new contact must contain text");
        this.firstName = firstName;
    }

    /**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

    /**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    /**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

    /**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    /**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

    /**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

    /**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

    /**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

    /**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

    /**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
}
