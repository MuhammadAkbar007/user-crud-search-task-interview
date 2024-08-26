package uz.akbar.user_crud_search_task.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record UserDto(
        @NotBlank(message = "first name is required") String firstName,

        @NotBlank(message = "last name is required") String lastName,

        @NotBlank(message = "middle name is required") String middleName,

        @NotBlank(message = "username is required") String username,

        @NotBlank(message = "password is required") String password,

        @NotNull(message = "address is required") UUID addressId,

        @NotNull(message = "department is required") UUID departmentId,

        @Size(min = 1, message = "At least one role must be provided") Set<UUID> roleIds
) {
}
