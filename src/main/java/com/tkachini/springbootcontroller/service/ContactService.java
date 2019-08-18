package com.tkachini.springbootcontroller.service;

import com.tkachini.springbootcontroller.entity.Contact;
import com.tkachini.springbootcontroller.repository.ContactRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContactService
{
    private static final int WORD_LENGTH = 10;
    private static final int BATCH_SIZE = 100;

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getFilteredContactsByStream(final String filter)
    {
        List<Contact> result = new ArrayList<>();
        Pageable pageRequest = PageRequest.of(0, BATCH_SIZE);
        int totalPages = 0;

        do {
            Page<Contact> contactPage = contactRepository.findAll(pageRequest);
            List<Contact> newFilteredBatch = contactPage.getContent().stream()
                    .filter(contact -> !contact.getName().matches(filter))
                    .collect(Collectors.toList());
            result.addAll(newFilteredBatch);

            pageRequest = pageRequest.next();
            if(totalPages == 0)
            {
                totalPages = contactPage.getTotalPages();
            }
        } while(pageRequest.getPageNumber() < totalPages);

        return result;
    }

    public List<Contact> getFilteredContacts(final String filter)
    {
        List<Contact> result = new ArrayList<>();
        Pageable pageRequest = PageRequest.of(0, BATCH_SIZE);
        int totalPages = 0;

        do {
            Page<Contact> contactPage = contactRepository.findAll(isNotMatchFilter(filter), pageRequest);
            List<Contact> newFilteredBatch = contactPage.getContent();
            result.addAll(newFilteredBatch);

            pageRequest = pageRequest.next();
            if(totalPages == 0)
            {
                totalPages = contactPage.getTotalPages();
            }
        } while(pageRequest.getPageNumber() < totalPages);

        return result;
    }

     private Specification<Contact> isNotMatchFilter(final String filter) {
         return (Specification<Contact>) (root, criteriaQuery, cb) -> {
             Pattern regexPattern = Pattern.compile(filter);
             Expression<String> patternExpression = cb.<String>literal(regexPattern.pattern());

             return cb.isTrue(cb.function("not_regexp", Boolean.class, root.get("name"), patternExpression));
         };
     }

    public void generateContacts(final int count) {
        ArrayList<Contact> contacts = new ArrayList<>(count);
        for(int i = 0; i < count; i++) {
            contacts.add(new Contact(RandomStringUtils.random(WORD_LENGTH, true, true)));
        }
        contactRepository.saveAll(contacts);
    }
}
