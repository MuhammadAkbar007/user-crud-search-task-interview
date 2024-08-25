package uz.akbar.user_crud_search_task.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddressDto(
        @NotBlank(message = "Address is required") String address,

        @NotNull(message = "Region ID is required") UUID regionId
) {
}
