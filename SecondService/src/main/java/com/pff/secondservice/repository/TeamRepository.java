package com.pff.secondservice.repository;

import com.pff.secondservice.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
