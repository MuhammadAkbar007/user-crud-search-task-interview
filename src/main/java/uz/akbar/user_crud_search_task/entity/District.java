package uz.akbar.user_crud_search_task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.akbar.user_crud_search_task.entity.template.AbsNameOrder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class District extends AbsNameOrder {

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}
