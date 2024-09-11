package com.example.demo.controller;

import com.example.demo.domains.Poll;
import com.example.demo.domains.Vote;
import com.example.demo.domains.VoteOption;
import com.example.demo.manager.PollManager;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/v1/voteOption")
public class voteOptionController {

    @Autowired
    PollManager pollManager;

    @Operation(summary = "Change caption of a voting option")
    @PutMapping("/change")
    public ResponseEntity<String> changeVoteOption(@RequestParam int pollId, @RequestParam int presentationOrder, @RequestParam String newOption){
        List<VoteOption> options = findVoteOptionsByPollId(pollId);
        if (options == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        for (VoteOption v : options) {
            if (v.getPresentationOrder() == presentationOrder) {
                v.setCaption(newOption);
            }
        }
        return ResponseEntity.ok("Vote option changed");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteVoteOption(@RequestParam int pollId, @RequestParam int presentationOrder){
        List<VoteOption> options = findVoteOptionsByPollId(pollId);
        if (options == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        options.removeIf(v -> v.getPresentationOrder() == presentationOrder);
        return ResponseEntity.ok("Vote option deleted");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVoteOption(@RequestParam int pollId, @RequestBody VoteOption voteOption){
        List<VoteOption> options = findVoteOptionsByPollId(pollId);
        if (options == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        options.add(voteOption);
        return ResponseEntity.ok("Vote option added");
    }


    @GetMapping("/get")
    public VoteOption getVoteOption(@RequestParam int pollId, @RequestParam int presentationOrder) {

        for (List<Poll> polls : pollManager.getHashmap().values()) {
            for (Poll p : polls) {
                if (p.getId() == pollId) {

                    List<VoteOption> options = p.getOptions();
                    if (options != null) {
                        for (VoteOption v : options) {
                            if (v.getPresentationOrder() == presentationOrder) {
                                return v;
                            }
                        }
                    }
                    return null;
                }
            }
        }
        return null;
    }


    private List<VoteOption> findVoteOptionsByPollId(int pollId) {
        return pollManager.getHashmap().values().stream()
                .flatMap(List::stream)
                .filter(p -> p.getId() == pollId)
                .map(Poll::getOptions)
                .findFirst().orElse(null);
    }

}
