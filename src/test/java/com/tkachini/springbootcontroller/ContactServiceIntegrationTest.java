package com.tkachini.springbootcontroller;

import com.tkachini.springbootcontroller.entity.Contact;
import com.tkachini.springbootcontroller.repository.ContactRepository;
import com.tkachini.springbootcontroller.service.ContactService;
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

import java.util.Collections;
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

    @Test
    public void whenFindAllByFilter_thenReturnFilteredPage() {

        Contact contact = new Contact("Petrovich");

		pageRequest = PageRequest.of(0, 100);

		PageImpl<Contact> response = new PageImpl<>(Collections.singletonList(contact), pageRequest, 1);
		Mockito.when(contactRepository.findAll(pageRequest))
				.thenReturn(response);

        List<Contact> filteredContacts = contactService.getFilteredContactsByStream("^A.*$");

        Contact[] expectedArray = {contact};

        assertThat(response).containsOnly(contact);
    }
}
