package com.project.joe.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
public class ContactService {

    @Autowired
    MessageSource messageSource;
    @Autowired
    ContactRepository repository;
    @Autowired
    ServletRequest servletRequest;

    void storeContact(Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                throw new ContactException(
                        messageSource
                                .getMessage(
                                        fieldError.getCode(), null, null));
            }
        }
        repository.save(contact);
    }

    Page<Contact> getContacts(Pageable pageable, PagedResourcesAssembler assembler) {
        return repository.findAll(pageable);
    }


    void deleteContact(int uid) {
        try {
            repository.deleteById(uid);
        } catch (IllegalArgumentException ex) {
            throw new ContactException("Contact update failed");
        }
    }

    void storeImage(MultipartFile file, String username) {
        if (file.isEmpty()) {
            throw new MultipartException(
                    messageSource.getMessage("file.isEmpty", null, null));
        }

        try {
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf('.'));
            String storageLocation =
                    servletRequest
                            .getServletContext()
                            .getRealPath("/images/")
                            .concat(username)
                            .concat(extension);

            file.transferTo(Paths.get(storageLocation));
        } catch (IOException | IndexOutOfBoundsException ex) {
            if (ex instanceof IndexOutOfBoundsException) {
                throw new ContactException("invalid file extension");
            } else {
                throw new MultipartException("Server error");
            }
        }
    }

//    void resolveError(BindingResult bindingResult) {
////        Map<String, List<String>> errors;
//        if (bindingResult.hasErrors()) {
//            boolean isFieldError = bindingResult.hasFieldErrors();
//            boolean isGlobalError = bindingResult.hasGlobalErrors();
//
//            if (isFieldError && bindingResult.getFieldErrorCount() > 1) {
//
//            } else if (isFieldError) {
//
//            }
//
//            if (isGlobalError && bindingResult.getGlobalErrorCount() > 1) {
//            }
//        }
//    }

    Map<String, List<String>> mapFieldWithErrorCodes(List<ObjectError> errors) {
//        BiConsumer<Map<String, List<String>>, FieldError> addErrorCodesOfField =
//                        (errorAccumulator, fieldError) ->
//                                errorAccumulator.put(
//                                        fieldError.getField(),
//                                        Arrays.asList())
//          );
//        errors = bindingResult
//                        .getFieldErrors()
//                        .stream()
//                        .collect(HashMap::new, addErrorCodesOfField, null);
        return null;
    }
}
