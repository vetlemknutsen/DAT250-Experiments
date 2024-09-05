package domain.manager;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import domains.User;
import domains.Poll;
@Component
@Getter
@Setter
public class PollManager {

    private HashMap<Poll, User> hashmap = new HashMap<>();
    Poll poll = new Poll();

    public PollManager(){

    }


}
