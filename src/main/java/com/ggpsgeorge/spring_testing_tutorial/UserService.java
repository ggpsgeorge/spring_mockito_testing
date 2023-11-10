package com.ggpsgeorge.spring_testing_tutorial;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> findUser(Long id){
        return userRepository.findById(id);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User updateUser(Long id, User user){

        try {
            User persistedUser = findUser(id).orElseThrow();

            persistedUser.setFirstName(user.getFirstName());
            persistedUser.setLastName(user.getLastName());
            persistedUser.setEmail(user.getEmail());
            persistedUser.setPassword(user.getPassword());
            User updatedUser = saveUser(persistedUser);

            return updatedUser;
        
        } catch (NoSuchElementException e){
            return null;
        }
    }

    public boolean deleteUser(Long id){
        Optional<User> user = findUser(id);

        if(user.isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        
        return false;
    }

}
