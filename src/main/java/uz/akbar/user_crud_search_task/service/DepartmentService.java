package uz.akbar.user_crud_search_task.service;

import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.DepartmentDto;

import java.util.UUID;

public interface DepartmentService {
    ApiResponse add(DepartmentDto dto);

    ApiResponse getAll();

    ApiResponse getById(UUID id);

    ApiResponse edit(UUID id, DepartmentDto dto);

    ApiResponse delete(UUID id);
}
