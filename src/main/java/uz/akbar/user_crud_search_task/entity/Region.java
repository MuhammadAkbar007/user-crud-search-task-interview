package uz.akbar.user_crud_search_task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.akbar.user_crud_search_task.entity.template.AbsEntity;
import uz.akbar.user_crud_search_task.entity.template.LocalizedString;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Region extends AbsEntity {

    @Embedded
    @Column(unique = true, nullable = false)
    private LocalizedString name;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<District> districts;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;
}
