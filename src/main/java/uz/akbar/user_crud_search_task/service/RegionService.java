package uz.akbar.user_crud_search_task.service;

import jakarta.validation.Valid;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.RegionDto;

import java.util.UUID;

public interface RegionService {

    ApiResponse add(RegionDto dto);

    ApiResponse getAll();

    ApiResponse getById(UUID id);

    ApiResponse update(UUID id, RegionDto dto);

    ApiResponse delete(UUID id);
}
