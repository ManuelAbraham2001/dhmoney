package com.dh.activities_service.Service;

import com.dh.activities_service.Entity.Activity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IActivityService {
    Activity create(Activity activity, UUID userId, String token);
    List<Activity> getActivitiesByUser(UUID id);
    List<Activity> getLastFiveActivitiesByUser(UUID id, int limit);
    Activity getActivityById(UUID id);
}
