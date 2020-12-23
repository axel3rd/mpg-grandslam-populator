package org.blonding.mpg.repository;

import org.blonding.mpg.model.db.GrandSlamDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrandSlamDayRepository extends JpaRepository<GrandSlamDay, Long> {

    GrandSlamDay findOneByGrandSlamIdAndDay(int grandSlamId, int day);

}
