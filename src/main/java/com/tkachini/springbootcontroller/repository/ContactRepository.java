package com.tkachini.springbootcontroller.repository;

import com.tkachini.springbootcontroller.entity.Contact;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer> {
}
