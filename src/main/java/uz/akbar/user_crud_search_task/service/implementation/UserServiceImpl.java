package uz.akbar.user_crud_search_task.service.implementation;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.akbar.user_crud_search_task.entity.Address;
import uz.akbar.user_crud_search_task.entity.Department;
import uz.akbar.user_crud_search_task.entity.Role;
import uz.akbar.user_crud_search_task.entity.User;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.UserDependenciesDto;
import uz.akbar.user_crud_search_task.payload.UserDto;
import uz.akbar.user_crud_search_task.payload.UserSavingDto;
import uz.akbar.user_crud_search_task.repository.AddressRepository;
import uz.akbar.user_crud_search_task.repository.DepartmentRepository;
import uz.akbar.user_crud_search_task.repository.RoleRepository;
import uz.akbar.user_crud_search_task.repository.UserRepository;
import uz.akbar.user_crud_search_task.repository.specifications.UserSpecifications;
import uz.akbar.user_crud_search_task.service.UserService;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository repository;

    final AddressRepository addressRepository;

    final DepartmentRepository departmentRepository;

    final RoleRepository roleRepository;

    final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, AddressRepository addressRepository, DepartmentRepository departmentRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResponse add(UserDto dto) {
        ApiResponse responseFromValidation = validateDependencies(dto);
        if (!responseFromValidation.success()) return responseFromValidation;

        UserDependenciesDto dependenciesDto = (UserDependenciesDto) responseFromValidation.object();

        UserSavingDto userSavingDto = new UserSavingDto(
                new User(),
                dto.firstName(),
                dto.lastName(),
                dto.middleName(),
                dto.username(),
                passwordEncoder.encode(dto.password()),
                dependenciesDto.address(),
                dependenciesDto.department(),
                dependenciesDto.roles()
        );

        return prepareAndSaveUser(userSavingDto);
    }

    @Override
    public ApiResponse getAll(int page, int size) {
        try {
            return new ApiResponse(true, repository.findAll(PageRequest.of(page - 1, size)));
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

        ApiResponse responseFromValidation = validateDependencies(dto);
        if (!responseFromValidation.success()) return responseFromValidation;

        UserDependenciesDto dependenciesDto = (UserDependenciesDto) responseFromValidation.object();

        UserSavingDto userSavingDto = new UserSavingDto(
                optional.get(),
                dto.firstName(),
                dto.lastName(),
                dto.middleName(),
                dto.username(),
                passwordEncoder.encode(dto.password()),
                dependenciesDto.address(),
                dependenciesDto.department(),
                dependenciesDto.roles()
        );

        return prepareAndSaveUser(userSavingDto);
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

    private ApiResponse validateDependencies(UserDto dto) {
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

        UserDependenciesDto dependenciesDto = new UserDependenciesDto(
                optionalAddress.get(),
                optionalDepartment.get(),
                roles
        );

        return new ApiResponse(true, dependenciesDto);
    }

    private ApiResponse prepareAndSaveUser(UserSavingDto dto) {
        User user = dto.user();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setMiddleName(dto.middleName());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setAddress(dto.address());
        user.setDepartment(dto.department());
        user.setRoles(dto.roles());

        try {
            User saved = repository.save(user);
            return new ApiResponse(true, saved);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }
}