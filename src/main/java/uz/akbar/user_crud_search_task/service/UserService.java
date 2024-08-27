package uz.akbar.user_crud_search_task.service;

import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.UserDto;

import java.util.Set;
import java.util.UUID;

public interface UserService {

    ApiResponse add(UserDto dto);

    ApiResponse getAll(int page, int size);

    ApiResponse getById(UUID id);

    ApiResponse edit(UUID id, UserDto dto);

    ApiResponse delete(UUID id);

    ApiResponse search(
            String firstName, String lastName, String middleName, String username, Set<String> roleNames,
            String address, UUID regionId, UUID districtId, UUID departmentId, UUID parenDepartmentId
    );
}
