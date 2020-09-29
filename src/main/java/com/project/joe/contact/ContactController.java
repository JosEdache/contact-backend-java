package com.project.joe.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("contact")
@CrossOrigin
public class ContactController {

    @Autowired
    ContactService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveContact(
            @Validated @RequestBody Contact contact,
            BindingResult bindingResult
    ) {
        service.storeContact(contact, bindingResult);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Contact> getAllContacts(Pageable pageable, PagedResourcesAssembler assembler) {
        return service.getContacts(pageable, assembler);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteContact(int uid) {
        service.deleteContact(uid);
    }

    @PostMapping("upload")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void uploadContactImage(
            @RequestParam String username,
            @RequestPart("file") MultipartFile file
    ) {
        service.storeImage(file, username);
    }

}
