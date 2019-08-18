package com.tkachini.springbootcontroller;

import com.tkachini.springbootcontroller.entity.Contact;
import com.tkachini.springbootcontroller.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("hello")
public class ContactController
{
    private ContactService contactService;

    public ContactController(final ContactService contactService)
    {
        this.contactService = contactService;
    }

    @ResponseBody
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getContactsByFilter(@RequestParam final String nameFilter)
    {
        List<Contact> filteredContacts = contactService.getFilteredContacts(nameFilter);
        return new ResponseEntity<>(filteredContacts, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/contacts/stream")
    public ResponseEntity<List<Contact>> getContactsByStreamFilter(@RequestParam final String nameFilter)
    {
        List<Contact> filteredContacts = contactService.getFilteredContactsByStream(nameFilter);
        return new ResponseEntity<>(filteredContacts, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/generate")
    public ResponseEntity<?> generateContacts(@RequestParam final int count)
    {
        contactService.generateContacts(count);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
