package uz.akbar.user_crud_search_task.service.implementation;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.akbar.user_crud_search_task.entity.Region;
import uz.akbar.user_crud_search_task.entity.template.LocalizedString;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.payload.RegionDto;
import uz.akbar.user_crud_search_task.repository.RegionRepository;
import uz.akbar.user_crud_search_task.repository.specifications.RegionSpecifications;
import uz.akbar.user_crud_search_task.service.RegionService;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegionServiceImpl implements RegionService {

	final RegionRepository repository;

	public RegionServiceImpl(RegionRepository repository) {
		this.repository = repository;
	}

	/* Create */
	public ApiResponse add(RegionDto dto) {
		if (existsByAnyName(dto.nameUz(), dto.nameEng(), dto.nameRu()))
			return new ApiResponse(false, "This name already exists");

		Region region = new Region();
		region.setName(new LocalizedString(dto.nameUz(), dto.nameEng(), dto.nameRu()));

		try {
			Region saved = repository.save(region);
			return new ApiResponse(true, saved);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(false, e.getMessage());
		}
	}

	/* Read all */
	@Override
	public ApiResponse getAll() {
		try {
			return new ApiResponse(true, repository.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(false, e.getMessage());
		}
	}

	/* Read one by id */
	@Override
	public ApiResponse getById(UUID id) {
		Optional<Region> optional = repository.findById(id);
		return optional.map(region -> new ApiResponse(true, region))
				.orElseGet(() -> new ApiResponse(false, "Region not found"));
	}

	/* Update */
	@Override
	public ApiResponse edit(UUID id, RegionDto dto) {
		if (existsByAnyName(dto.nameUz(), dto.nameEng(), dto.nameRu()))
			return new ApiResponse(false, "This name already exists");

		Optional<Region> optional = repository.findById(id);
		if (optional.isEmpty())
			return new ApiResponse(false, "Region not found");
		Region region = optional.get();
		region.setName(new LocalizedString(dto.nameUz(), dto.nameEng(), dto.nameRu()));
		try {
			Region saved = repository.save(region);
			return new ApiResponse(true, saved);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(false, e.getMessage());
		}
	}

	/* Delete */
	@Override
	public ApiResponse delete(UUID id) {
		try {
			repository.deleteById(id);
			return new ApiResponse(true, "Region deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(false, e.getMessage());
		}
	}

	// Checker function if any name exists in database
	private boolean existsByAnyName(String nameUz, String nameEng, String nameRu) {
		Specification<Region> exist = RegionSpecifications.hasAnyName(nameUz, nameEng, nameRu);
		return repository.exists(exist);
	}
}
