public class AssembledTraitsReadyToGif {
    private final String hashId;

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

    public AssembledTraitsReadyToGif(Trait backgroundTrait,
                                     Trait skinTrait, Trait baseTrait, Trait clothesTrait,
                                     Trait expressionTrait, Trait headTrait,
                                     Trait armsTrait) {

        this.backgroundTrait = backgroundTrait;
        this.skinTrait = skinTrait;
        this.baseTrait = baseTrait;
        this.clothesTrait = clothesTrait;
        this.expressionTrait = expressionTrait;
        this.HeadTrait = headTrait;
        this.ArmsTrait = armsTrait;

        hashId = "" + backgroundTrait.getTraitId() + "-" + skinTrait.getTraitId() + "-" + baseTrait.getTraitId() + "-" +
        clothesTrait.getTraitId() + "-" + expressionTrait.getTraitId() + "-" + headTrait.getTraitId() + ":" + armsTrait.getTraitId();
    }

    public AssembledTraitsReadyToGif(Trait backgroundTrait, Trait baseTrait, Trait expressionTrait, Trait armsTrait) {

        this.backgroundTrait = backgroundTrait;
        this.baseTrait = baseTrait;
        this.expressionTrait = expressionTrait;
        this.ArmsTrait = armsTrait;

        hashId = "" + backgroundTrait.getTraitId() + "-" + baseTrait.getTraitId() + "-" + expressionTrait.getTraitId() +
                "-"  + armsTrait.getTraitId();
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            if (o instanceof AssembledTraitsReadyToGif) {
                return ((AssembledTraitsReadyToGif) o).getHashId().equals(this.hashId);
            }
        }
        return false;
    }
}
