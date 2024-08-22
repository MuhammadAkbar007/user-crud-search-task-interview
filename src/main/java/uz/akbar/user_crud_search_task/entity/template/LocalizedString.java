package uz.akbar.user_crud_search_task.entity.template;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class LocalizedString {

    private String nameUz;

    private String nameEng;

    private String nameRu;
}
