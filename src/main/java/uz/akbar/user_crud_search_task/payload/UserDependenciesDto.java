package uz.akbar.user_crud_search_task.payload;

import uz.akbar.user_crud_search_task.entity.Address;
import uz.akbar.user_crud_search_task.entity.Department;
import uz.akbar.user_crud_search_task.entity.Role;

import java.util.Set;

public record UserDependenciesDto(
        Address address,
        Department department,
        Set<Role> roles
) {
}
