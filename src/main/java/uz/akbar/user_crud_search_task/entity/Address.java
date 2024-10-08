package uz.akbar.user_crud_search_task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.akbar.user_crud_search_task.entity.template.AbsEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address extends AbsEntity {

    private String address; // street name and house number

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private User user;
}
