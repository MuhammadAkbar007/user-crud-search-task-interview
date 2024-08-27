package uz.akbar.user_crud_search_task.service.implementation;

import org.springframework.stereotype.Service;
import uz.akbar.user_crud_search_task.entity.Address;
import uz.akbar.user_crud_search_task.entity.Region;
import uz.akbar.user_crud_search_task.payload.AddressDto;
import uz.akbar.user_crud_search_task.payload.ApiResponse;
import uz.akbar.user_crud_search_task.repository.AddressRepository;
import uz.akbar.user_crud_search_task.repository.RegionRepository;
import uz.akbar.user_crud_search_task.service.AddressService;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    final AddressRepository repository;

    final RegionRepository regionRepository;

    public AddressServiceImpl(AddressRepository repository, RegionRepository regionRepository) {
        this.repository = repository;
        this.regionRepository = regionRepository;
    }

    /* Create */
    @Override
    public ApiResponse add(AddressDto dto) {
        Optional<Region> optionalRegion = regionRepository.findById(dto.regionId());
        if (optionalRegion.isEmpty()) return new ApiResponse(false, "Region not found");

        Address address = new Address();
        address.setAddress(dto.address());
        address.setRegion(optionalRegion.get());
        try {
            Address saved = repository.save(address);
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
        return repository.findById(id)
                .map(address -> new ApiResponse(true, address))
                .orElseGet(() -> new ApiResponse(false, "Address not found"));
    }

    /* Update */
    @Override
    public ApiResponse edit(UUID id, AddressDto dto) {
        Optional<Address> optional = repository.findById(id);
        if (optional.isEmpty()) return new ApiResponse(false, "Address not found");

        Optional<Region> optionalRegion = regionRepository.findById(dto.regionId());
        if (optionalRegion.isEmpty()) return new ApiResponse(false, "Region not found");

        Address address = optional.get();
        address.setAddress(dto.address());
        address.setRegion(optionalRegion.get());
        try {
            Address saved = repository.save(address);
            return new ApiResponse(true, saved);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }

    /* Delete */
    @Override
    public ApiResponse delete(UUID id) {
        try {
            repository.deleteById(id);
            return new ApiResponse(true, "Address deleted successfully");
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }
}
