import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TraitPool {

    private HashMap<Integer, Trait> allTraits =new HashMap<>();
//    private HashMap<Integer, Trait> backgroundTraits =new HashMap<>();
//    private HashMap<Integer, Trait> skinTraits = new HashMap<>();
//    private HashMap<Integer, Trait> baseTraits = new HashMap<>();
//    private HashMap<Integer, Trait> clothesTraits = new HashMap<>();
//    private HashMap<Integer, Trait> eyesTraits = new HashMap<>();
//    private HashMap<Integer, Trait> mouthTraits = new HashMap<>();
//    private HashMap<Integer, Trait> headTraits = new HashMap<>();
//    private HashMap<Integer, Trait> armsTraits = new HashMap<>();

    public void addTrait(Integer i, Trait trait) {

        allTraits.put(i, trait);
//        switch (trait.getTraitType()){
//            case BACKGROUND: backgroundTraits.put(i, trait);
//                break;
//
//            case SKIN: skinTraits.put(i, trait);
//                break;
//
//            case BASE: baseTraits.put(i, trait);
//                break;
//
//            case CLOTHES: clothesTraits.put(i, trait);
//                break;
//
//            case EYES: eyesTraits.put(i, trait);
//                break;
//
//            case MOUTH: mouthTraits.put(i, trait);
//                break;
//
//            case HEAD: headTraits.put(i, trait);
//                break;
//
//            case ARMS: armsTraits.put(i, trait);
//                break;
//        }
    }

    public Collection<Trait> getAllTraits() {
        return allTraits.values();
    }
//    public Collection<Trait> getBackgroundTraits() {
//        return backgroundTraits.values();
//    }
//
//    public Collection<Trait> getSkinTraits() {
//        return skinTraits.values();
//    }
//
//    public Collection<Trait> getBaseTraits() {
//        return baseTraits.values();
//    }
//
//    public Collection<Trait> getClothesTraits() {
//        return clothesTraits.values();
//    }
//
//    public Collection<Trait> getEyesTraits() {
//        return eyesTraits.values();
//    }
//
//    public Collection<Trait> getMouthTraits() {
//        return mouthTraits.values();
//    }
//
//    public Collection<Trait> getHeadTraits() {
//        return headTraits.values();
//    }
//
//    public Collection<Trait> getArmsTraits() {
//        return armsTraits.values();
//    }

    public Trait getTrait(int index){
        return allTraits.get(index);
    }
//    public Trait getBackgroundTrait(int index){
//        return backgroundTraits.get(index);
//    }
//    public Trait getSkinTrait(int index){
//        return skinTraits.get(index);
//    }
//    public Trait getBaseTrait(int index){
//        return baseTraits.get(index);
//    }
//    public Trait getClothesTrait(int index){
//        return clothesTraits.get(index);
//    }
//    public Trait getEyesTrait(int index){
//        return eyesTraits.get(index);
//    }
//    public Trait getMouthTrait(int index){
//        return mouthTraits.get(index);
//    }
//    public Trait getHeadTrait(int index){
//        return headTraits.get(index);
//    }
//    public Trait getArmsTrait(int index){
//        return armsTraits.get(index);
//    }

//    public int getNumBackgroundTraits() {
//        return backgroundTraits.size();
//    }
//    public int getNumSkinTraits() {
//        return skinTraits.size();
//    }
//    public int getNumBaseTraits() {
//        return baseTraits.size();
//    }
//    public int getNumClothesTraits() {
//        return clothesTraits.size();
//    }
//    public int getNumEyesTraits() {
//        return eyesTraits.size();
//    }
//    public int getNumMouthTraits() {
//        return mouthTraits.size();
//    }
//    public int getNumHeadTraits() {
//        return headTraits.size();
//    }
//    public int getNumArmsTraits() {
//        return armsTraits.size();
//    }
    public TraitPool() {
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Trait> trait : allTraits.entrySet()) {
           sb.append(trait.toString());
        }
//        for (Map.Entry<Integer, Trait> trait : backgroundTraits.entrySet()) {
//           sb.append(trait.toString());
//        }
//        for (Map.Entry<Integer, Trait> trait : skinTraits.entrySet()) {
//            sb.append(trait.toString());
//        }
//        for (Map.Entry<Integer, Trait> trait : baseTraits.entrySet()) {
//            sb.append(trait.toString());
//        }
//        for (Map.Entry<Integer, Trait> trait : clothesTraits.entrySet()) {
//            sb.append(trait.toString());
//        }
//        for (Map.Entry<Integer, Trait> trait : eyesTraits.entrySet()) {
//            sb.append(trait.toString());
//        }
//        for (Map.Entry<Integer, Trait> trait : mouthTraits.entrySet()) {
//            sb.append(trait.toString());
//        }
//        for (Map.Entry<Integer, Trait> trait : headTraits.entrySet()) {
//            sb.append(trait.toString());
//        }
//        for (Map.Entry<Integer, Trait> trait : armsTraits.entrySet()) {
//            sb.append(trait.toString());
//        }
        return sb.toString();
    }

}
