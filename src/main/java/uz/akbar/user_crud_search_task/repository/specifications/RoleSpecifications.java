package uz.akbar.user_crud_search_task.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import uz.akbar.user_crud_search_task.entity.Role;

public class RoleSpecifications {
    public static Specification<Role> hasAnyName(String nameUz, String nameEng, String nameRu) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.equal(root.get("name").get("nameUz"), nameUz),
                criteriaBuilder.equal(root.get("name").get("nameEng"), nameEng),
                criteriaBuilder.equal(root.get("name").get("nameRu"), nameRu)
        );
    }

    public static Specification<Role> hasOrder(Integer order) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("order"), order);
    }

}
