package uz.akbar.user_crud_search_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.akbar.user_crud_search_task.entity.District;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.DistrictDto;
import uz.akbar.user_crud_search_task.repository.DistrictRepository;

import java.time.LocalDateTime;

@Service
public class DistrictService {

    @Autowired
    DistrictRepository repository;

    public ApiResponse add(DistrictDto dto) {
        District district = new District();
        return null;
    }
}
