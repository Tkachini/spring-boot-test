package com.tkachini.springbootcontroller;

import com.tkachini.springbootcontroller.entity.Contact;
import com.tkachini.springbootcontroller.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactsRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContactService contactService;

    @Test
    public void givenContacts_whenGetContactsByFilter_thenReturnJsonArray() throws Exception {

        Contact contact = new Contact("Petrovich");
        Contact contact2 = new Contact("contact2");

        List<Contact> allContacts = Arrays.asList(contact, contact2);

        given(contactService.getFilteredContacts("asd")).willReturn(allContacts);

        mvc.perform(get("/hello/contacts?nameFilter=asd")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(contact.getName())));
    }

    @Test
    public void givenContacts_whenGetContactsByFilterStream_thenReturnJsonArray() throws Exception {

        Contact contact = new Contact("1");
        Contact contact2 = new Contact("2");
        Contact contact3 = new Contact("3");

        List<Contact> allContacts = Arrays.asList(contact, contact2, contact3);

        given(contactService.getFilteredContactsByStream("test2")).willReturn(allContacts);

        mvc.perform(get("/hello/contacts/stream?nameFilter=test2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is(contact3.getName())));
    }
}
