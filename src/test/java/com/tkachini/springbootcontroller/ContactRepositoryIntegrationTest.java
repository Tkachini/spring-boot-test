package com.tkachini.springbootcontroller;

import com.tkachini.springbootcontroller.entity.Contact;
import com.tkachini.springbootcontroller.repository.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class ContactRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        Contact contact = new Contact("Petrovich");
        entityManager.persist(contact);
        entityManager.flush();

        Iterable<Contact> founded = contactRepository.findAll();

        assertThat(founded)
                .containsOnly(contact);
    }
}
