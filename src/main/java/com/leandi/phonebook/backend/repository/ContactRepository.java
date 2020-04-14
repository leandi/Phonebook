package com.leandi.phonebook.backend.repository;

import com.leandi.phonebook.backend.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("select c from Contact c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))" +
            "or c.phoneNumber like concat('%', :searchTerm, '%')" +
            "or c.gsmNumber like concat('%', :searchTerm, '%')")
    List<Contact> search(@Param("searchTerm") String searchTerm);
}



/*
@Query("select c from Contact c " +
        "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
        "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")

 @Query("select c from Contact c " +
          "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
          "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))" +
          "or c.phoneNumber like ('%', :searchTerm, '%')" +
            "or c.gsmNumber like ('%', :searchTerm, '%')")

*/
