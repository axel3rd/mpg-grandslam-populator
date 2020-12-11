package org.blonding.mpg.repository;

import java.util.List;

import org.blonding.mpg.model.db.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

    List<League> findByGrandSlamId(Long grandSlamId);
}
