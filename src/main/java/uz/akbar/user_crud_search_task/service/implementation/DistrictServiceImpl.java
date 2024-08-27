package uz.akbar.user_crud_search_task.service.implementation;

import org.springframework.stereotype.Service;
import uz.akbar.user_crud_search_task.entity.District;
import uz.akbar.user_crud_search_task.entity.Region;
import uz.akbar.user_crud_search_task.entity.template.LocalizedString;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.DistrictDto;
import uz.akbar.user_crud_search_task.repository.DistrictRepository;
import uz.akbar.user_crud_search_task.repository.RegionRepository;
import uz.akbar.user_crud_search_task.repository.specifications.DistrictSpecifications;
import uz.akbar.user_crud_search_task.service.DistrictService;

import java.util.Optional;
import java.util.UUID;

@Service
public class DistrictServiceImpl implements DistrictService {

    final DistrictRepository repository;
    final RegionRepository regionRepository;

    public DistrictServiceImpl(DistrictRepository repository, RegionRepository regionRepository) {
        this.repository = repository;
        this.regionRepository = regionRepository;
    }

    /* Create */
    @Override
    public ApiResponse add(DistrictDto dto) {
        ApiResponse responseFromExists = checkByFields(dto.nameUz(), dto.nameEng(), dto.nameRu(), dto.order());
        if (!responseFromExists.success())
            return responseFromExists;

        Optional<Region> optionalRegion = regionRepository.findById(dto.regionId());
        if (optionalRegion.isEmpty())
            return new ApiResponse(false, "Region not found");

        District district = new District();
        district.setOrder(dto.order());
        district.setName(new LocalizedString(dto.nameUz(), dto.nameEng(), dto.nameRu()));
        district.setRegion(optionalRegion.get());

        try {
            District saved = repository.save(district);
            return new ApiResponse(true, saved);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    /* Read all */
    @Override
    public ApiResponse getAll() {
        try {
            return new ApiResponse(true, repository.findAll());
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    /* Read one by id */
    @Override
    public ApiResponse getById(UUID id) {
        Optional<District> optional = repository.findById(id);
        return optional.map(district -> new ApiResponse(true, district))
                .orElseGet(() -> new ApiResponse(false, "District not found"));
    }

    /* Update */
    @Override
    public ApiResponse edit(UUID id, DistrictDto dto) {
        Optional<District> optional = repository.findById(id);
        if (optional.isEmpty())
            return new ApiResponse(false, "District not found");

        ApiResponse responseFromExists = checkByFields(dto.nameUz(), dto.nameEng(), dto.nameRu(), dto.order());
        if (!responseFromExists.success())
            return responseFromExists;

        Optional<Region> optionalRegion = regionRepository.findById(dto.regionId());
        if (optionalRegion.isEmpty())
            return new ApiResponse(false, "Region not found");

        District district = optional.get();
        district.setName(new LocalizedString(dto.nameUz(), dto.nameEng(), dto.nameRu()));
        district.setOrder(dto.order());
        district.setRegion(optionalRegion.get());
        try {
            repository.save(district);
            return new ApiResponse(true, district);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    /* Delete */
    @Override
    public ApiResponse delete(UUID id) {
        try {
            repository.deleteById(id);
            return new ApiResponse(true, "District deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    private ApiResponse checkByFields(String nameUz, String nameEng, String nameRu, Integer order) {
        if (repository.exists(DistrictSpecifications.hasOrder(order)))
            return new ApiResponse(false, "Order already exists");
        if (repository.exists(DistrictSpecifications.hasAnyName(nameUz, nameEng, nameRu)))
            return new ApiResponse(false, "Name already exists");
        return new ApiResponse(true, "OK");
    }
}
