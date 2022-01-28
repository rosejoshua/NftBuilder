import java.util.ArrayList;

public class TraitPool {

    private ArrayList<Trait> backgroundTraits = new ArrayList<>();
    private ArrayList<Trait> skinTraits = new ArrayList<>();
    private ArrayList<Trait> baseTraits = new ArrayList<>();
    private ArrayList<Trait> clothesTraits = new ArrayList<>();
    private ArrayList<Trait> expressionTraits = new ArrayList<>();
    private ArrayList<Trait> headTraits = new ArrayList<>();
    private ArrayList<Trait> armsTraits = new ArrayList<>();

    public void addTrait(Trait trait) {
        switch (trait.getTraitType()){
            case BACKGROUND: backgroundTraits.add(trait);
                break;

            case SKIN: skinTraits.add(trait);
                break;

            case BASE: baseTraits.add(trait);
                break;

            case CLOTHES: clothesTraits.add(trait);
                break;

            case EXPRESSION: expressionTraits.add(trait);
                break;

            case HEAD: headTraits.add(trait);
                break;

            case ARMS: armsTraits.add(trait);
                break;
        }
    }

    public Trait getBackgroundTrait(int index){
        return backgroundTraits.get(index);
    }
    public Trait getSkinTrait(int index){
        return skinTraits.get(index);
    }
    public Trait getBaseTrait(int index){
        return baseTraits.get(index);
    }
    public Trait getClothesTrait(int index){
        return clothesTraits.get(index);
    }
    public Trait getExpressionTrait(int index){
        return expressionTraits.get(index);
    }
    public Trait getHeadTrait(int index){
        return headTraits.get(index);
    }
    public Trait getArmsTrait(int index){
        return armsTraits.get(index);
    }

    public int getNumBackgroundTraits() {
        return backgroundTraits.size();
    }
    public int getNumSkinTraits() {
        return skinTraits.size();
    }
    public int getNumBaseTraits() {
        return baseTraits.size();
    }
    public int getNumClothesTraits() {
        return clothesTraits.size();
    }
    public int getNumExpressionTraits() {
        return expressionTraits.size();
    }
    public int getNumHeadTraits() {
        return headTraits.size();
    }
    public int getNumArmsTraits() {
        return armsTraits.size();
    }
    public TraitPool() {
    }
}
