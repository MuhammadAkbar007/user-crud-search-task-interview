package uz.akbar.user_crud_search_task.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.UserDto;
import uz.akbar.user_crud_search_task.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserDto dto) {
        ApiResponse response = service.add(dto);
        return ResponseEntity.status(response.success() ? 201 : 400).body(response.object());
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        ApiResponse response = service.getAll();
        return ResponseEntity.status(response.success() ? 200 : 400).body(response.object());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse response = service.getById(id);
        return ResponseEntity.status(response.success() ? 200 : 400).body(response.object());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid UserDto dto) {
        ApiResponse response = service.edit(id, dto);
        return ResponseEntity.status(response.success() ? 200 : 400).body(response.object());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse response = service.delete(id);
        return ResponseEntity.status(response.success() ? 204 : 400).body(response.object());
    }
}
