public class Trait {

    private String name;
    private TraitType traitType;
    private Rarity rarity;
    private TraitFrame[] traitFrames;
    private final int traitId;
    private int numTimesUsed = 0;

    public Trait(TraitType traitType, Rarity rarity, TraitFrame[] traitFrames, int traitId) {
        this.name = name;
        this.traitType = traitType;
        this.rarity = rarity;
        this.traitFrames = traitFrames;
        this.traitId = traitId;
    }

    public int getNumTimesUsed() {
        return numTimesUsed;
    }

    public void incrementNumTimesUsed() {
        numTimesUsed++;
    }

    public int getTraitId() {
        return traitId;
    }

    public TraitType getTraitType() {
        return traitType;
    }

}
