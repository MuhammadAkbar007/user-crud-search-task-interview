package uz.akbar.user_crud_search_task.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.DistrictDto;
import uz.akbar.user_crud_search_task.service.DistrictService;

@RestController
@RequestMapping("/api/v1/district")
public class DistrictController {

    @Autowired
    DistrictService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DistrictDto dto) {
        ApiResponse response = service.add(dto);
        return null;
    }

}
