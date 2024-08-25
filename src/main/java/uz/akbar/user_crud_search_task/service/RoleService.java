package uz.akbar.user_crud_search_task.service;

import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.RoleDto;

import java.util.UUID;

public interface RoleService {
    ApiResponse add(RoleDto dto);

    ApiResponse getAll();

    ApiResponse getById(UUID id);

    ApiResponse edit(UUID id, RoleDto dto);

    ApiResponse delete(UUID id);
}
