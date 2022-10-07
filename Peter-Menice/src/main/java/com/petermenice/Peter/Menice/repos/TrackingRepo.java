package com.petermenice.Peter.Menice.repos;

import com.petermenice.Peter.Menice.entities.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;


public interface TrackingRepo extends JpaRepository<Tracking, Long> {
}
