package uz.akbar.user_crud_search_task.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import uz.akbar.user_crud_search_task.entity.User;

import java.util.UUID;

public class UserSpecifications {

    public static Specification<User> hasUsername(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username);
    }

    public static Specification<User> isAddressAssigned(UUID addressId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("address").get("id"), addressId);
    }

    public static Specification<User> isAddressAssignedToOtherUser(UUID addressId, UUID userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("address").get("id"), addressId),
                criteriaBuilder.notEqual(root.get("id"), userId)
        );
    }
}
