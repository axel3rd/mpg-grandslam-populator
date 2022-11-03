package org.blonding.mpg.repository;

import java.util.List;

import org.blonding.mpg.model.db.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByActive(boolean active);
}
