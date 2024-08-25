package uz.akbar.user_crud_search_task.service;

import jakarta.validation.Valid;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.DistrictDto;

import java.util.UUID;

public interface DistrictService {

    ApiResponse add(DistrictDto dto);

    ApiResponse getAll();

    ApiResponse getById(UUID id);

    ApiResponse edit(UUID id, DistrictDto dto);

    ApiResponse delete(UUID id);
}
