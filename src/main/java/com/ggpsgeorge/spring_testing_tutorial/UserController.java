package com.ggpsgeorge.spring_testing_tutorial;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public String greet(){
        return "Sup";
    }
    
    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody @Valid User user){
        User persistedUser = userService.saveUser(user);

        URI uri = URI.create("/api/v1/users/" + persistedUser.getId());
        
        return ResponseEntity.created(uri).body(entityToDTO(persistedUser));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        try{
            User persistedUser = userService.findUser(id).orElseThrow();

            return ResponseEntity.ok().body(entityToDTO(persistedUser));

        }catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
            
        }
        
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> users = userService.findAllUsers();

        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(entityListToDTO(users));
    }

    private UserDTO entityToDTO(User entity){
        return modelMapper.map(entity, UserDTO.class);
    }

    private List<UserDTO> entityListToDTO(List<User> entities){
        List<UserDTO> transformedEntities =  new ArrayList<UserDTO>(entities.size());
        
        for(User entity : entities){
            transformedEntities.add(entityToDTO(entity));
        }
        
        return transformedEntities;
    }
}
