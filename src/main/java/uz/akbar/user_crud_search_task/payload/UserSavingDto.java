package uz.akbar.user_crud_search_task.payload;

import uz.akbar.user_crud_search_task.entity.Address;
import uz.akbar.user_crud_search_task.entity.Department;
import uz.akbar.user_crud_search_task.entity.Role;
import uz.akbar.user_crud_search_task.entity.User;

import java.util.Set;

public record UserSavingDto(
        User user,

        String firstName,

        String lastName,

        String middleName,

        String username,

        String password,

        Address address,

        Department department,

        Set<Role> roles
) {
}
