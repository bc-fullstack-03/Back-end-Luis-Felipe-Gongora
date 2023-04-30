package com.sysmap.showus.services.user;

import com.sysmap.showus.data.IUserRepository;
import com.sysmap.showus.data.UserDTO;
import com.sysmap.showus.domain.User;
import com.sysmap.showus.services.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private IUserRepository repo;

    public List<User> findAll(){
        return repo.findAll();
    }

    public User findById(UUID id){
        Optional<User> user = repo.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
    }

    public User createUser(UserRequest request){
        var user = new User(request.getName(), request.getEmail(), request.getPassword());
        return repo.save(user);
    }

    public void delete(UUID id){
        findById(id);
        repo.deleteById(id);
    }

    public User update (UUID id, UserRequest request){
        User newUser = findById(id);
        updateData(newUser, request);
        return repo.save(newUser);

    }

    private void updateData(User newUser, UserRequest user) {
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
    }
}
