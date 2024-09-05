package domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteOption {

    private String caption;
    private int presentationOrder;

    public VoteOption(){

    }

}
