package com.tkachini.springbootcontroller;

import com.tkachini.springbootcontroller.entity.Contact;
import com.tkachini.springbootcontroller.repository.ContactRepository;
import com.tkachini.springbootcontroller.service.ContactService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ContactServiceIntegrationTest {

    @TestConfiguration
    static class ContactServiceTestContextConfiguration {

        @Bean
        public ContactService contactService() {
            return new ContactService();
        }
    }

    @Autowired
    private ContactService contactService;

    @MockBean
    private ContactRepository contactRepository;

    private PageRequest pageRequest;

    @Before
    public void setUp() {
        Contact contact = new Contact("Petrovich");
        Contact contact2 = new Contact("Albertovich");
        Contact contact3 = new Contact("raf123");

        pageRequest = PageRequest.of(0, 100);

        PageImpl<Contact> response = new PageImpl<>(Arrays.asList(contact, contact2, contact3), pageRequest, 1);
        Mockito.when(contactRepository.findAll(pageRequest))
                .thenReturn(response);
    }

    @Test
    public void whenFindAllByFilter_thenReturnFilteredPage_withPetrovich() {
        List<Contact> filteredContacts = contactService.getFilteredContactsByStream("^A.*$");

        assertThat(filteredContacts)
                .filteredOn(contact1 -> contact1.getName().equals("Petrovich"))
                .hasSize(1);
    }

    @Test
    public void whenFindAllByFilter_thenReturnFilteredPage_withFirstUppercase() {
        List<Contact> filteredContacts = contactService.getFilteredContactsByStream("^[a-z].*$");

        assertThat(filteredContacts)
                .filteredOn(contact -> contact.getName().equals("Petrovich") || contact.getName().equals("Albertovich"))
                .hasSize(2);
    }

    @Test
    public void whenFindAllByFilter_thenReturnFilteredPage_withDigits() {
        List<Contact> filteredContacts = contactService.getFilteredContactsByStream("^[a-zA-Z]+$");

        assertThat(filteredContacts)
                .filteredOn(contact -> contact.getName().equals("raf123"))
                .hasSize(1);
    }
}
