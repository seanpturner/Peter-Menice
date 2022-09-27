package com.petermenice.Peter.Menice.repos;

import com.petermenice.Peter.Menice.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Long> {
}
