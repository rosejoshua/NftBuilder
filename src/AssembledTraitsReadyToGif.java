public class AssembledTraitsReadyToGif {
    private final String hashId;

    private Trait backgroundTrait;
    private Trait skinTrait;
    private Trait baseTrait;
    private Trait clothesTrait;
    private Trait eyesTrait;
    private Trait mouthTrait;
    private Trait HeadTrait;
    private Trait ArmsTrait;

    public String getHashId() {
        return hashId;
    }

    public AssembledTraitsReadyToGif(Trait backgroundTrait,
                                     Trait skinTrait, Trait baseTrait, Trait clothesTrait,
                                     Trait eyesTrait, Trait mouthTrait, Trait headTrait,
                                     Trait armsTrait) {

        this.backgroundTrait = backgroundTrait;
        this.skinTrait = skinTrait;
        this.baseTrait = baseTrait;
        this.clothesTrait = clothesTrait;
        this.eyesTrait = eyesTrait;
        this.mouthTrait = mouthTrait;
        this.HeadTrait = headTrait;
        this.ArmsTrait = armsTrait;

        hashId = "" + backgroundTrait.getTraitId() + "-" + skinTrait.getTraitId() + "-" + baseTrait.getTraitId() + "-" +
        clothesTrait.getTraitId() + "-" + eyesTrait.getTraitId() + mouthTrait.getTraitId() + "-" + headTrait.getTraitId() + ":" + armsTrait.getTraitId();
    }

    // FIXME: 2/2/2022 delete after testing complete
    public AssembledTraitsReadyToGif(Trait backgroundTrait, Trait baseTrait, Trait eyesTrait, Trait armsTrait) {

        this.backgroundTrait = backgroundTrait;
        this.baseTrait = baseTrait;
        this.eyesTrait = eyesTrait;
        this.ArmsTrait = armsTrait;

        hashId = "" + backgroundTrait.getTraitId() + "-" + baseTrait.getTraitId() + "-" + eyesTrait.getTraitId() +
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
