package com.petermenice.Peter.Menice.repos;

import com.petermenice.Peter.Menice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User getReferenceByUserName(String toLowerCase);

    User getReferenceByEmail(String toLowerCase);

    List<User> findAllByActive(boolean b);

    List<User> findAllByBirthMonth(Integer month);

    List<User> findAllByOrderByUserNameAsc();

}
