package uz.akbar.user_crud_search_task.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.akbar.user_crud_search_task.entity.template.AbsNameOrder;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Role extends AbsNameOrder {
}