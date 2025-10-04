package com.sanmartinvapor.repository;

import com.sanmartinvapor.model.PersonalItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalItemRepository extends JpaRepository<PersonalItem, Long> {
}
