package com.scm.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String>{

    // Find the contact by user
    //custom finder method
    Page<Contact> findByUser(User user,Pageable pageable); 

     // custom query method
     @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
     List<Contact> findByUserId(@Param("userId") String userId);

     // Custom finder method
    Page<Contact> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phonekeyword, Pageable pageable);

    // Find recent contacts (for example, contacts created in the last 30 days)
    List<Contact> findByUserAndCreatedDateAfter(User user, LocalDateTime dateTime);

    // Get total number of contacts for a user
    long countByUser(User user);

    // Find favorite contacts by user
    @Query("SELECT COUNT(c) FROM Contact c WHERE c.user = :user AND c.favorite = true")
    long findByUserAndFavoriteTrue(User user);

}
