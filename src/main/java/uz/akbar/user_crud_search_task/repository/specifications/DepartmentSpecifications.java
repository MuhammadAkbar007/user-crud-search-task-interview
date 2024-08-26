package uz.akbar.user_crud_search_task.repository.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.akbar.user_crud_search_task.entity.Department;

import java.util.UUID;

public class DepartmentSpecifications {

    public static Specification<Department> hasOrder(Integer order) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("order"), order);
    }

    public static Specification<Department> hasAnyNameWithParent(String nameUz, String nameEng, String nameRu, UUID parentDepartmentId) {
        return (root, query, criteriaBuilder) -> {
            Predicate nameUzPredicate = criteriaBuilder.equal(root.get("name").get("nameUz"), nameUz);
            Predicate nameEngPredicate = criteriaBuilder.equal(root.get("name").get("nameEng"), nameEng);
            Predicate nameRuPredicate = criteriaBuilder.equal(root.get("name").get("nameRu"), nameRu);

            Predicate sameParentDepartment = parentDepartmentId != null
                    ? criteriaBuilder.equal(root.get("parentDepartment").get("id"), parentDepartmentId)
                    : criteriaBuilder.isNull(root.get("parentDepartment"));

            return criteriaBuilder.and(
                    criteriaBuilder.or(nameUzPredicate, nameEngPredicate, nameRuPredicate),
                    sameParentDepartment
            );
        };
    }
}
