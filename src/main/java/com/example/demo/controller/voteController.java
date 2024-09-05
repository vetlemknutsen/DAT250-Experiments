package com.example.demo.controller;

import com.example.demo.domains.Poll;
import com.example.demo.domains.User;
import com.example.demo.domains.Vote;
import com.example.demo.domains.VoteOption;
import com.example.demo.manager.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/vote")
public class voteController {

    @Autowired
    private PollManager pollManager;

    @PostMapping("/vote")
    public ResponseEntity<String> vote(@RequestParam String username, @RequestParam int pollId, @RequestParam int voteOption){
        voteOption++;
        User user = null;
        Poll poll = null;

        for (User u : pollManager.getHashmap().keySet()) {
            if (u.getUsername().equals(username)) {
                user = u;
                break;
            }
        }

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        for (List<Poll> polls : pollManager.getHashmap().values()) {
            for (Poll p : polls) {
                if (p.getId() == pollId) {
                    poll = p;
                    break;
                }
            }
        }

        if (poll == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }


        Vote vote = new Vote();
        vote.setVoter(user);
        VoteOption voteOption1 = poll.getOptions().get(voteOption);
        if (voteOption1.getVotes() != null){
            voteOption1.getVotes().add(vote);
        }
        else {
            List<Vote> votes = new ArrayList<>();
            votes.add(vote);
            voteOption1.setVotes(votes);
        }
        return ResponseEntity.ok("Vote registered");

    }

    @PutMapping("/change")
    public ResponseEntity<String> changeVote(@RequestParam String username, @RequestParam int pollId, @RequestParam int oldVote, @RequestParam int newVote){

        deleteVote(username, pollId, oldVote);
        vote(username, pollId, newVote);

        return ResponseEntity.ok("Vote changed");
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteVote(@RequestParam String username, @RequestParam int pollId, @RequestParam int voteOption ){

        Poll poll = null;
        for (List<Poll> polls : pollManager.getHashmap().values()) {
            for (Poll p : polls) {
                if (p.getId() == pollId) {
                    poll = p;
                    break;
                }
            }
        }

        if (poll == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        List<VoteOption> voteOptionList = poll.getOptions();
        List<Vote> optionVotes = null;
        for (VoteOption v : voteOptionList) {
            if (v.getPresentationOrder() == voteOption) {
                optionVotes = v.getVotes();
                if (optionVotes == null) {
                    optionVotes = new ArrayList<>();
                    v.setVotes(optionVotes);
                }
                break;
            }
        }

        Iterator<Vote> iterator = optionVotes.iterator();
        while (iterator.hasNext()) {
            Vote v = iterator.next();
            if (Objects.equals(v.getVoter().getUsername(), username)) {
                iterator.remove();
                break;
            }
        }

        return ResponseEntity.ok("Vote deleted");
    }


}
