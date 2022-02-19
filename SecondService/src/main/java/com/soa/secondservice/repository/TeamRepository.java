package com.soa.secondservice.repository;

import com.soa.secondservice.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
