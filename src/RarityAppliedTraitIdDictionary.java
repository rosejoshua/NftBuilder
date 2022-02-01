public class RarityAppliedTraitIdDictionary {

    private final TraitType traitType;
    private RarityAppliedTrait[] rarityAppliedTraits;
    int nextAvailableSlot = 0;
    int rangeMultiplier = 0;

    public RarityAppliedTraitIdDictionary(TraitType traitType, int numberOfTraits) {
        this.traitType = traitType;
        rarityAppliedTraits = new RarityAppliedTrait[numberOfTraits];
    }

    public void addTrait (int traitId, int min, int max){
        rarityAppliedTraits[nextAvailableSlot] = new RarityAppliedTrait(min, max, traitId);
        nextAvailableSlot++;
    }

    public void setRangeMultiplier(int rangeMultiplier) {
        this.rangeMultiplier = rangeMultiplier;
    }

    public int retrieveRandomTrait() {
        System.out.println("Retrieving random trait");
        if(rangeMultiplier == 0){
            throw new IllegalStateException("Range multiplier not set!");
        }
        else {

            int randomInt = (int) (rangeMultiplier * Math.random());
            System.out.println("Random int between 0 and " + rangeMultiplier + ": " + randomInt);
            for (int i=0; i< rarityAppliedTraits.length; i++) {
                if (randomInt <= rarityAppliedTraits[i].getMax()) {
                    return rarityAppliedTraits[i].getTraitId();
                }
            }
            return -1;
        }
    }
}
