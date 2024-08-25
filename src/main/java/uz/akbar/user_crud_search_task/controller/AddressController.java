package uz.akbar.user_crud_search_task.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.akbar.user_crud_search_task.payload.AddressDto;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.service.AddressService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    AddressService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AddressDto dto) {
        ApiResponse response = service.add(dto);
        return ResponseEntity.status(response.success() ? 201 : 400).body(response.object());
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        ApiResponse response = service.getAll();
        return ResponseEntity.status(response.success() ? 200 : 404).body(response.object());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse response = service.getById(id);
        return ResponseEntity.status(response.success() ? 200 : 400).body(response.object());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody AddressDto dto) {
        ApiResponse response = service.edit(id, dto);
        return ResponseEntity.status(response.success() ? 200 : 400).body(response.object());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse response = service.delete(id);
        return ResponseEntity.status(response.success() ? 204 : 400).body(response.object());
    }
}
