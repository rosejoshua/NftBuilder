public class RarityAppliedTrait {

    private final int min;
    private final int max;
    private final int traitId;

    public RarityAppliedTrait(int min, int max, int traitId) {
        this.min = min;
        this.max = max;
        this.traitId = traitId;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getTraitId() {
        return traitId;
    }
}
