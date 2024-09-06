package com.example.demo.controller;

import com.example.demo.manager.PollManager;
import com.example.demo.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class userController {

    @Autowired
    private PollManager pollManager;

    @PostMapping("/create")
    public String createUser(@RequestParam String username){
        pollManager.getHashmap().put(new User(username),null);

        return "User was created";
    }

    @GetMapping("/getAll")
    public List<User> getAll(){
        return pollManager.getHashmap().keySet().stream().toList();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String username){
        Iterator<User> iterator = pollManager.getHashmap().keySet().iterator();

        while (iterator.hasNext()) {
            User u = iterator.next();

            if (u.getUsername().equals(username)) {
                iterator.remove();
                return ResponseEntity.ok("User deleted");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/change")
    public ResponseEntity<String> changeUser(@RequestParam String oldUsername, @RequestParam String newUsername){

        for (User u : pollManager.getHashmap().keySet()) {
            if (u.getUsername().equals(oldUsername)) {
                u.setUsername(newUsername);
                return ResponseEntity.ok("User changed");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }


}
