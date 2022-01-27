import java.util.ArrayList;

public class AssembledTraits {
    private String hashId = "";

    private Trait backgroundTrait;
    private Trait skinTrait;
    private Trait baseTrait;
    private Trait clothesTrait;
    private Trait expressionTrait;
    private Trait HeadTrait;
    private Trait ArmsTrait;

    public String getHashId() {
        return hashId;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            if (o instanceof AssembledTraits) {
                return ((AssembledTraits) o).getHashId().equals(this.hashId);
            }
        }
        return false;
    }
}
