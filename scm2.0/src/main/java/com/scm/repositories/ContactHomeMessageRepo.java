package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.entities.ContactHomeMessage;

public interface ContactHomeMessageRepo extends JpaRepository<ContactHomeMessage, Long> {

}
