import java.util.ArrayList;
import java.util.HashSet;

public class TraitPool {
    private static int traitIdCounter = 0;

    private HashSet<AssembledTraits> assembledTraits;

    private Trait[] backgroundTraits;
    private ArrayList<Trait> distributedBackgroundTraits;

    private Trait[] skinTraits;
    private ArrayList<Trait> distributedSkinTraits;

    private Trait[] baseTraits;
    private ArrayList<Trait> distributedBaseTraits;

    private Trait[] clothesTraits;
    private ArrayList<Trait> distributedClothesTraits;

    private Trait[] expressionTraits;
    private ArrayList<Trait> distributedExpressionTraits;

    private Trait[] HeadTraits;
    private ArrayList<Trait> distributedHeadTraits;

    private Trait[] ArmsTraits;
    private ArrayList<Trait> distributedArmsTraits;

}
