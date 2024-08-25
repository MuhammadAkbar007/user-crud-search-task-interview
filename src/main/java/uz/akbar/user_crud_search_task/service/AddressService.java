package uz.akbar.user_crud_search_task.service;

import jakarta.validation.Valid;
import uz.akbar.user_crud_search_task.payload.AddressDto;
import uz.akbar.user_crud_search_task.payload.ApiResponse;

import java.util.UUID;

public interface AddressService {
    ApiResponse add(AddressDto dto);

    ApiResponse getAll();

    ApiResponse getById(UUID id);

    ApiResponse edit(UUID id, AddressDto dto);

    ApiResponse delete(UUID id);
}
