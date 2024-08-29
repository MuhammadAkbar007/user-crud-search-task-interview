package uz.akbar.user_crud_search_task.service.implementation;

import org.springframework.stereotype.Service;
import uz.akbar.user_crud_search_task.entity.Role;
import uz.akbar.user_crud_search_task.entity.template.LocalizedString;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.RoleDto;
import uz.akbar.user_crud_search_task.repository.RoleRepository;
import uz.akbar.user_crud_search_task.repository.specifications.RoleSpecifications;
import uz.akbar.user_crud_search_task.service.RoleService;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    final
    RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ApiResponse add(RoleDto dto) {
        ApiResponse responseFromExists = checkByFields(dto.order(), dto.nameUz(), dto.nameEng(), dto.nameRu());
        if (!responseFromExists.success()) return responseFromExists;

        Role role = new Role();
        role.setOrder(dto.order());
        role.setName(new LocalizedString(dto.nameUz(), dto.nameEng(), dto.nameRu()));

        try {
            Role saved = repository.save(role);
            return new ApiResponse(true, saved);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Override
    public ApiResponse getAll() {
        try {
            return new ApiResponse(true, repository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Override
    public ApiResponse getById(UUID id) {
        return repository.findById(id)
                .map(role -> new ApiResponse(true, role))
                .orElseGet(() -> new ApiResponse(false, "Role not found"));
    }

    @Override
    public ApiResponse edit(UUID id, RoleDto dto) {
        Optional<Role> optional = repository.findById(id);
        if (optional.isEmpty()) return new ApiResponse(false, "Role not found");

        Role role = optional.get();
        role.setOrder(dto.order());
        role.setName(new LocalizedString(dto.nameUz(), dto.nameEng(), dto.nameRu()));

        try {
            Role saved = repository.save(role);
            return new ApiResponse(true, saved);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Override
    public ApiResponse delete(UUID id) {
        try {
            repository.deleteById(id);
            return new ApiResponse(true, "Role deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, e.getMessage());
        }
    }

    private ApiResponse checkByFields(Integer order, String nameUz, String nameEng, String nameRu) {
        if (repository.exists(RoleSpecifications.hasOrder(order)))
            return new ApiResponse(false, "Order already exists");

        if (repository.exists(RoleSpecifications.hasAnyName(nameUz, nameEng, nameRu)))
            return new ApiResponse(false, "Name already exists");

        return new ApiResponse(true, "OK");
    }
}
