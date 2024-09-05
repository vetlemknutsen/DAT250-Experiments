package com.example.demo.controller;

import com.example.demo.manager.PollManager;
import com.example.demo.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
