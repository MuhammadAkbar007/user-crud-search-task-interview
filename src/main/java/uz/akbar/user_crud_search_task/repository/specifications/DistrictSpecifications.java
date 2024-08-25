package uz.akbar.user_crud_search_task.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import uz.akbar.user_crud_search_task.entity.District;

public class DistrictSpecifications {

    public static Specification<District> hasAnyName(String nameUz, String nameEng, String nameRu) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.equal(root.get("name").get("nameUz"), nameUz),
                criteriaBuilder.equal(root.get("name").get("nameEng"), nameEng),
                criteriaBuilder.equal(root.get("name").get("nameRu"), nameRu)
        );
    }

    public static Specification<District> hasOrder(Integer order) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("order"), order);
    }
}
