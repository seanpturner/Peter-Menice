package com.petermenice.Peter.Menice.repos;

import com.petermenice.Peter.Menice.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByOrderByNameAsc();

    List<Subscription> findAllByActive(boolean a);
}
