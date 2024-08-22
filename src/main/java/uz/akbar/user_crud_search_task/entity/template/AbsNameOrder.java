package uz.akbar.user_crud_search_task.entity.template;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbsNameOrder extends AbsEntity {

    @Column(nullable = false, unique = true)
    @Embedded
    private LocalizedString name;

    @Column(name = "\"order\"", unique = true, nullable = false)
    private Integer order;
}
