package uz.akbar.user_crud_search_task.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.akbar.user_crud_search_task.entity.Address;
import uz.akbar.user_crud_search_task.entity.Department;
import uz.akbar.user_crud_search_task.entity.Role;
import uz.akbar.user_crud_search_task.entity.User;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.UserDto;
import uz.akbar.user_crud_search_task.repository.AddressRepository;
import uz.akbar.user_crud_search_task.repository.DepartmentRepository;
import uz.akbar.user_crud_search_task.repository.RoleRepository;
import uz.akbar.user_crud_search_task.repository.UserRepository;
import uz.akbar.user_crud_search_task.repository.specifications.UserSpecifications;
import uz.akbar.user_crud_search_task.service.UserService;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse add(UserDto dto) {
        ApiResponse responseFromChecker = checkUsername(dto.username());
        if (!responseFromChecker.success()) return responseFromChecker;

        Optional<Address> optionalAddress = addressRepository.findById(dto.addressId());
        if (optionalAddress.isEmpty()) return new ApiResponse(false, "Address not found");

        Optional<Department> optionalDepartment = departmentRepository.findById(dto.departmentId());
        if (optionalDepartment.isEmpty()) return new ApiResponse(false, "Department not found");

        Set<Role> roles = getRoles(dto.roleIds());
        if (roles.isEmpty()) return new ApiResponse(false, "Roles not found");

        if (repository.exists(UserSpecifications.isAddressAssigned(dto.addressId())))
            return new ApiResponse(false, "Address is already assigned");

        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setMiddleName(dto.middleName());
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setAddress(optionalAddress.get());
        user.setDepartment(optionalDepartment.get());
        user.setRoles(roles);

        try {
            User saved = repository.save(user);
            return new ApiResponse(true, saved);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Override
    public ApiResponse getAll() {
        try {
            return new ApiResponse(true, repository.findAll());
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Override
    public ApiResponse getById(UUID id) {
        return repository.findById(id)
                .map(user -> new ApiResponse(true, user))
                .orElseGet(() -> new ApiResponse(false, "User not found"));
    }

    @Override
    public ApiResponse edit(UUID id, UserDto dto) {
        Optional<User> optional = repository.findById(id);
        if (optional.isEmpty()) return new ApiResponse(false, "User not found");

        ApiResponse responseFromChecker = checkUsername(dto.username());
        if (!responseFromChecker.success()) return responseFromChecker;

        Optional<Address> optionalAddress = addressRepository.findById(dto.addressId());
        if (optionalAddress.isEmpty()) return new ApiResponse(false, "Address not found");

        Optional<Department> optionalDepartment = departmentRepository.findById(dto.departmentId());
        if (optionalDepartment.isEmpty()) return new ApiResponse(false, "Department not found");

        Set<Role> roles = getRoles(dto.roleIds());
        if (roles.isEmpty()) return new ApiResponse(false, "Roles not found");

        if (repository.exists(UserSpecifications.isAddressAssignedToOtherUser(dto.addressId(), id)))
            return new ApiResponse(false, "Address is assigned to other user");

        User user = optional.get();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setMiddleName(dto.middleName());
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(roles);
        user.setAddress(optionalAddress.get());
        user.setDepartment(optionalDepartment.get());

        try {
            User saved = repository.save(user);
            return new ApiResponse(true, saved);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Override
    public ApiResponse delete(UUID id) {
        try {
            repository.deleteById(id);
            return new ApiResponse(true, "User deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    private ApiResponse checkUsername(String username) {
        if (repository.exists(UserSpecifications.hasUsername(username)))
            return new ApiResponse(false, "Username already exists");
        return new ApiResponse(true, "ok");
    }

    private Set<Role> getRoles(Set<UUID> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (UUID id : roleIds) {
            Optional<Role> optional = roleRepository.findById(id);
            optional.ifPresent(roles::add);
        }
        return roles;
    }
}
