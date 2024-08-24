package uz.akbar.user_crud_search_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.akbar.user_crud_search_task.entity.Region;

import java.util.UUID;

@Repository
public interface RegionRepository extends JpaRepository<Region, UUID>, JpaSpecificationExecutor<Region> {
}
