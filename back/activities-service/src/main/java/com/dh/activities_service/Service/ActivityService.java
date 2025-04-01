package com.dh.activities_service.Service;

import com.dh.activities_service.DTO.ActivityDTO;
import com.dh.activities_service.Entity.Activity;
import com.dh.activities_service.Exception.ActivityNotFoundException;
import com.dh.activities_service.Exception.TransferException;
import com.dh.activities_service.Exception.UserNotFoundException;
import com.dh.activities_service.Repository.AccountFeignRepository;
import com.dh.activities_service.Repository.ActivityRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ActivityService implements IActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AccountFeignRepository accountFeignRepository;

    @Override
    public Activity create(Activity activity, UUID userId, String token) {
        validateActivity(activity);
        activity.setUserId(userId);
        activity.setDated(LocalDateTime.now());
        ActivityDTO dto = new ActivityDTO(
                activity.getAmount(),
                activity.getName(),
                activity.getDated(),
                activity.getType(),
                activity.getOrigin(),
                activity.getDestination()
        );

        try {
            ResponseEntity<?> response;
            if ("Transfer".equalsIgnoreCase(activity.getType())) {
                response = accountFeignRepository.transfer(userId, dto, token);
            } else if ("Deposit".equalsIgnoreCase(activity.getType())) {
                response = accountFeignRepository.deposit(userId, dto, token);
            } else {
                throw new TransferException("Tipo de actividad no válido: " + activity.getType());
            }

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new TransferException("Error al procesar la operación con el servicio de cuentas: " + response.getStatusCode());
            }
        } catch (FeignException e) {
            throw e;
        }

        return activityRepository.save(activity);
    }

    private void validateActivity(Activity activity) {
        String type = activity.getType();
        String origin = activity.getOrigin();
        String destination = activity.getDestination();

        if ("Transfer".equalsIgnoreCase(type)) {
            if (origin == null || origin.isEmpty()) {
                throw new TransferException("La cuenta de origen es obligatoria.");
            }
            if (destination == null || destination.isEmpty()) {
                throw new TransferException("La cuenta de destino es obligatoria.");
            }
            if (origin.equals(destination)) {
                throw new TransferException("No es posible hacer transferencias a una misma cuenta.");
            }
        }
    }

    @Override
    public List<Activity> getActivitiesByUser(UUID id) {

        if(!activityRepository.existsByUserId(id)){
            throw new UserNotFoundException(id);
        }

        return activityRepository.findActivitiesByUser(id);
    }

    @Override
    public List<Activity> getLastFiveActivitiesByUser(UUID id, int limit) {
        if(!activityRepository.existsByUserId(id)){
            throw new UserNotFoundException(id);
        }

        return activityRepository.findLastFiveActivitiesByUser(id, PageRequest.of(0, limit));
    }

    @Override
    public Activity getActivityById(UUID id) {
        return activityRepository.findById(id).orElseThrow(() -> new ActivityNotFoundException(id));
    }
}
