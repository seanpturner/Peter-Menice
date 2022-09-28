package com.petermenice.Peter.Menice.repos;

import com.petermenice.Peter.Menice.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
}
