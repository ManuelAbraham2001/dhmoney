package com.dh.activities_service.Repository;

import com.dh.activities_service.Entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    @Query("SELECT a FROM Activity a WHERE a.userId = :id ORDER BY a.dated DESC")
    List<Activity> findActivitiesByUser(UUID id);
    @Query("SELECT a FROM Activity a WHERE a.userId = :id ORDER BY a.dated DESC")
    List<Activity> findLastFiveActivitiesByUser(UUID id, Pageable pageable);
    boolean existsByUserId(UUID id);

//    @Query("")
//    List<Activity> findLastUserActivities(Long id, int limit);
}
