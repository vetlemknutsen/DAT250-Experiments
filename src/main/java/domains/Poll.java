package domains;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
public class Poll {

    private String question;
    private Instant publishedAt;
    private Instant validUntil;

    public Poll(){

    }

}
