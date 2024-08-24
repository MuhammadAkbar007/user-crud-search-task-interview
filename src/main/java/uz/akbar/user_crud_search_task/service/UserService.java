package uz.akbar.user_crud_search_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.akbar.user_crud_search_task.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
}
