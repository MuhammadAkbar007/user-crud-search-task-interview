package uz.akbar.user_crud_search_task.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.akbar.user_crud_search_task.entity.District;
import uz.akbar.user_crud_search_task.entity.Region;
import uz.akbar.user_crud_search_task.entity.template.LocalizedString;
import uz.akbar.user_crud_search_task.repository.DistrictRepository;
import uz.akbar.user_crud_search_task.repository.RegionRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner runner(RegionRepository regionRepository, DistrictRepository districtRepository) {
        return args -> {
            if (regionRepository.count() == 0) {
                /* Create default data for Region */
                Region region = new Region();
                region.setName(new LocalizedString("Namangan viloyati", "Namangan region", "Наманганская область"));
                regionRepository.save(region);

                /* Create default data for Region */
                District district = new District();
                district.setOrder(1);
                district.setName(new LocalizedString("Uychi tumani", "Uychi district", "Уйчинский район"));
                district.setRegion(region);
                districtRepository.save(district);
            }
        };
    }
}
