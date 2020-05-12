package edache.joe.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping(ContactController.BASE_URL)
@RequiredArgsConstructor
public class ContactController {

    public static final String BASE_URL = "/contacts";
    public static final String IMAGE_UPLOAD_URL = "upload";

    private final ContactService service;

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

    @PostMapping(IMAGE_UPLOAD_URL)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void uploadContactImage(
            @RequestParam String username,
            @RequestPart("file") MultipartFile file
    ) {
        service.storeImage(file, username);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new ContactValidator());
    }
}
