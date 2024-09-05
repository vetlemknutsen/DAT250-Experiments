package com.example.demo.controller;

import com.example.demo.domains.Poll;
import com.example.demo.domains.User;
import com.example.demo.manager.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/v1/poll")
public class pollController {

    @Autowired
    private PollManager pollManager;

    @PostMapping("/create")
    public ResponseEntity<String> createPoll(@RequestParam String username, @RequestBody Poll poll){
        poll.setId(pollManager.getNextId());

        User existingUser = null;
        for (User user : pollManager.getHashmap().keySet()) {
            if (user.getUsername().equals(username)) {
                existingUser = user;
                break;
            }
        }

        if (existingUser != null) {
            List<Poll> polls = pollManager.getHashmap().get(existingUser);

            if (polls == null) {
                polls = new ArrayList<>();
            }

            polls.add(poll);
            pollManager.getHashmap().put(existingUser, polls);

            return ResponseEntity.ok("Poll was created");
        }

        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/getAll")
    public Collection<List<Poll>> getPolls(){
        return pollManager.getHashmap().values();
    }





}
