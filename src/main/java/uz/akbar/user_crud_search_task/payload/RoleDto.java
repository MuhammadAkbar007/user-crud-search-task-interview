package uz.akbar.user_crud_search_task.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleDto(

        @NotNull(message = "Order is required") Integer order,

        @NotBlank(message = "Uzbek name is required") String nameUz,

        @NotBlank(message = "English name is required") String nameEng,

        @NotBlank(message = "Russian name is required") String nameRu) {
}
