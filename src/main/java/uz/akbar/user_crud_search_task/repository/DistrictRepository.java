package uz.akbar.user_crud_search_task.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.akbar.user_crud_search_task.entity.District;

import java.util.UUID;

@Repository
public interface DistrictRepository extends JpaRepository<District, UUID>, JpaSpecificationExecutor<District> {
}
