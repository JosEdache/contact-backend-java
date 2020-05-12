package edache.joe.contact;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ContactValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        boolean value = Contact.class.equals(clazz);
        System.out.println(value);
        return  value;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contact contact = (Contact) target;
        ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "phoneNumber.isEmpty", "No phone number provided");
        if (contact.getFirstName() == null && contact.getLastName() == null) {
            errors.rejectValue("firstName", "name.isEmpty", "No name provided");
        }
    }
}
