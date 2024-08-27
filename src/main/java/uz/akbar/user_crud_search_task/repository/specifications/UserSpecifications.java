package uz.akbar.user_crud_search_task.repository.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.SetJoin;
import org.springframework.data.jpa.domain.Specification;
import uz.akbar.user_crud_search_task.entity.*;

import java.util.Set;
import java.util.UUID;

public class UserSpecifications {

    public static Specification<User> isAddressAssigned(UUID addressId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("address").get("id"), addressId);
    }

    public static Specification<User> isAddressAssignedToOtherUser(UUID addressId, UUID userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("address").get("id"), addressId),
                criteriaBuilder.notEqual(root.get("id"), userId)
        );
    }

    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("firstName"), firstName);
    }

    public static Specification<User> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("lastName"), lastName);
    }

    public static Specification<User> hasMiddleName(String middleName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("middleName"), middleName);
    }

    public static Specification<User> hasUsername(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username);
    }

    public static Specification<User> hasRole(Set<String> rolenames) {
        return (root, query, criteriaBuilder) -> {
            SetJoin<User, Role> roles = root.joinSet("roles");

            Predicate nameUzPredicate = roles.get("name").get("nameUz").in(rolenames);
            Predicate nameEngPredicate = roles.get("name").get("nameEng").in(rolenames);
            Predicate nameRuPredicate = roles.get("name").get("nameRu").in(rolenames);

            return criteriaBuilder.or(nameUzPredicate, nameEngPredicate, nameRuPredicate);
        };
    }

    public static Specification<User> hasAddressContaining(String address) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("address").get("address"), "%" + address + "%");
    }

    public static Specification<User> hasRegion(UUID regionId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("address").get("region").get("id"), regionId);
    }

    public static Specification<User> hasDistrict(UUID districtId) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Address> addressJoin = root.join("address");
            Join<Address, Region> regionJoin = addressJoin.join("region");
            Join<Region, District> districtJoin = regionJoin.join("districts");
            return criteriaBuilder.equal(districtJoin.get("id"), districtId);
        };
    }

    public static Specification<User> belongsToDepartment(UUID departmentId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("department").get("id"), departmentId));
    }

    public static Specification<User> belongsToParentDepartment(UUID parentDepartmentId) {
        return ((root, query, criteriaBuilder) -> {
            Join<User, Department> departmentJoin = root.join("department", JoinType.INNER);
            return criteriaBuilder.equal(departmentJoin.get("parentDepartment").get("id"), parentDepartmentId);
        });
    }
}