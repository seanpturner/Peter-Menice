package com.petermenice.Peter.Menice.repos;

import com.petermenice.Peter.Menice.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepo extends JpaRepository<Content, Long> {
    Content getReferenceByPageName(String page);
}
