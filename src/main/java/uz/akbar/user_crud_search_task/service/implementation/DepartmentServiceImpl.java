package uz.akbar.user_crud_search_task.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.akbar.user_crud_search_task.entity.Department;
import uz.akbar.user_crud_search_task.entity.template.LocalizedString;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.DepartmentDto;
import uz.akbar.user_crud_search_task.repository.DepartmentRepository;
import uz.akbar.user_crud_search_task.repository.specifications.DepartmentSpecifications;
import uz.akbar.user_crud_search_task.service.DepartmentService;

import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository repository;

    @Override
    public ApiResponse add(DepartmentDto dto) {
        ApiResponse responseFromChecker = checkUniqueness(dto.order(), dto.nameUz(), dto.nameEng(), dto.nameRu(), dto.parentDepartmentId());
        if (!responseFromChecker.success()) return responseFromChecker;

        Department parentDepartment = null;

        if (dto.parentDepartmentId() != null) {
            Optional<Department> optional = repository.findById(dto.parentDepartmentId());
            if (optional.isEmpty()) return new ApiResponse(false, "Parent department not found");
            parentDepartment = optional.get();
        }

        Department department = new Department();
        department.setOrder(dto.order());
        department.setName(new LocalizedString(dto.nameUz(), dto.nameEng(), dto.nameRu()));
        department.setParentDepartment(parentDepartment);

        try {
            Department saved = repository.save(department);
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
                .map(department -> new ApiResponse(true, department))
                .orElseGet(() -> new ApiResponse(false, "Department not found"));
    }

    @Override
    public ApiResponse edit(UUID id, DepartmentDto dto) {
        Optional<Department> optional = repository.findById(id);
        if (optional.isEmpty()) return new ApiResponse(false, "Department not found");

        ApiResponse responseFromChecker = checkUniqueness(dto.order(), dto.nameUz(), dto.nameEng(), dto.nameRu(), dto.parentDepartmentId());
        if (!responseFromChecker.success()) return responseFromChecker;

        Optional<Department> optionalParent = repository.findById(dto.parentDepartmentId());
        if (optionalParent.isEmpty()) return new ApiResponse(false, "Parent department not found");

        Department department = optional.get();
        department.setOrder(dto.order());
        department.setName(new LocalizedString(dto.nameUz(), dto.nameEng(), dto.nameRu()));
        department.setParentDepartment(optionalParent.get());
        try {
            Department saved = repository.save(department);
            return new ApiResponse(true, saved);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    @Override
    public ApiResponse delete(UUID id) {
        try {
            repository.deleteById(id);
            return new ApiResponse(true, "Department deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    private ApiResponse checkUniqueness(Integer order, String nameUz, String nameEng, String nameRu, UUID parentDepartmentId) {
        if (repository.exists(DepartmentSpecifications.hasOrder(order)))
            return new ApiResponse(false, "Order already exists");

        if (repository.exists(DepartmentSpecifications.hasAnyNameWithParent(nameUz, nameEng, nameRu, parentDepartmentId)))
            return new ApiResponse(false, "Name already exists");

        return new ApiResponse(true, "ok");
    }
}
