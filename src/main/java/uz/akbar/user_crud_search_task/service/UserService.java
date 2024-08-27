package uz.akbar.user_crud_search_task.service;

import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.UserDto;

import java.util.UUID;

public interface UserService {

    ApiResponse add(UserDto dto);

    ApiResponse getAll(int page, int size);

    ApiResponse getById(UUID id);

    ApiResponse edit(UUID id, UserDto dto);

    ApiResponse delete(UUID id);
}
