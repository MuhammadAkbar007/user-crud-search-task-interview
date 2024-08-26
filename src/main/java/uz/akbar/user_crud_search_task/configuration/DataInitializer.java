package uz.akbar.user_crud_search_task.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.akbar.user_crud_search_task.entity.*;
import uz.akbar.user_crud_search_task.entity.template.LocalizedString;
import uz.akbar.user_crud_search_task.repository.*;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner runner(
            RegionRepository regionRepository,
            DistrictRepository districtRepository,
            AddressRepository addressRepository,
            RoleRepository roleRepository,
            DepartmentRepository departmentRepository
    ) {
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

                /* Create default data for Address */
                Address address = new Address();
                address.setAddress("Do'stlik ko'cha 3-uy");
                address.setRegion(region);
                addressRepository.save(address);

                /* Create default data for Role */
                Role role = new Role();
                role.setOrder(1);
                role.setName(new LocalizedString("Dasturchi", "Programmist", "Программист"));
                roleRepository.save(role);

                /* Create default data for Role */
                Department department = new Department();
                department.setOrder(1);
                department.setName(
                        new LocalizedString("" +
                                "Axborot Texnologiyalari",
                                "Information Technologies",
                                "Информационные Технологии"
                        ));
                departmentRepository.save(department);
            }
        };
    }
}
