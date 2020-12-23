package org.blonding.mpg.repository;

import org.blonding.mpg.model.db.GrandSlam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrandSlamRepository extends JpaRepository<GrandSlam, Long> {

}
