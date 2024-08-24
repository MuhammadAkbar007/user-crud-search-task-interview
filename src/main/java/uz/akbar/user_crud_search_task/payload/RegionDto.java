package uz.akbar.user_crud_search_task.payload;

import jakarta.validation.constraints.NotBlank;

public record RegionDto(
        @NotBlank(message = "Uzbek name is required") String nameUz,

        @NotBlank(message = "English name is required") String nameEng,

        @NotBlank(message = "Russian name is required") String nameRu
        ) {
}