package uz.akbar.user_crud_search_task.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.UserDto;
import uz.akbar.user_crud_search_task.service.UserService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /* Create */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserDto dto) {
        ApiResponse response = service.add(dto);
        return ResponseEntity.status(response.success() ? 201 : 400).body(response.object());
    }

    /* Read all (pageable) */
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponse response = service.getAll(page, size);
        return ResponseEntity.status(response.success() ? 200 : 400).body(response.object());
    }

    /* Read one by id */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse response = service.getById(id);
        return ResponseEntity.status(response.success() ? 200 : 400).body(response.object());
    }

    /* Update */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid UserDto dto) {
        ApiResponse response = service.edit(id, dto);
        return ResponseEntity.status(response.success() ? 200 : 400).body(response.object());
    }

    /* Delete */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse response = service.delete(id);
        return ResponseEntity.status(response.success() ? 204 : 400).body(response.object());
    }

    /* Search == Filter */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Set<String> roleNames,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) UUID regionId,
            @RequestParam(required = false) UUID districtId,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) UUID parenDepartmentId
    ) {
        ApiResponse response = service.search(
                firstName, lastName, middleName, username, roleNames, address, regionId,
                districtId, departmentId, parenDepartmentId
        );
        return ResponseEntity.status(response.success() ? 200 : 400).body(response.object());
    }
}
