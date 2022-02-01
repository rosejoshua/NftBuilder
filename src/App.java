import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

public class App {

    public static void main(String[] args) throws IOException {

        BufferedReader reader;
        File file;
        String imageDirectory = "C:\\Users\\rosej\\IdeaProjects\\NftBuilder\\test\\";
        int numTraitCategories = TraitType.getNumberOfTraitTypes();

        int platinumMultiplier = 1;
        int goldMultiplier = 10;
        int silverMultiplier = 25;
        int bronzeMultiplier = 60;

        TraitPool traitPool = new TraitPool();
        RarityAppliedTraitIdDictionary[] rarityAppliedTraitIdDictionaries =
                new RarityAppliedTraitIdDictionary[numTraitCategories];

        int width = 300;
        int height = 300;
        int numTraitsFolders = 9;

        int numBackgroundProcessed = 0;
        int numSkinProcessed = 0;
        int numBaseProcessed = 0;
        int numClothesProcessed = 0;
        int numExpressionProcessed = 0;
        int numHeadProcessed = 0;
        int numArmsProcessed = 0;

        int processErrors = 0;

        for (int i=0; i<numTraitsFolders; i++){

            try {
                int numPhotos;
                String traitTypeString;
                String rarityString;
                int layerPriority;
                int numAnimationFrames;
                TraitFrame[] tempTraitFrames;

                file = new File( imageDirectory + (i+1) + "\\specs.txt");
                reader = new BufferedReader(new FileReader(file));

                numPhotos = Integer.parseInt(reader.readLine());
                traitTypeString = reader.readLine();
                TraitType traitType;
                rarityString = reader.readLine();
                Rarity rarity;
                layerPriority = Integer.parseInt(reader.readLine());
                numAnimationFrames = Integer.parseInt(reader.readLine());

                switch (traitTypeString){
                    case "BACKGROUND":
                        traitType = TraitType.BACKGROUND;
                        numBackgroundProcessed++;
                        break;
                    case "SKIN":
                        traitType = TraitType.SKIN;
                        numSkinProcessed++;
                        break;
                    case "BASE":
                        traitType = TraitType.BASE;
                        numBaseProcessed++;
                        break;
                    case "CLOTHES":
                        traitType = TraitType.CLOTHES;
                        numClothesProcessed++;
                        break;
                    case "EXPRESSION":
                        traitType = TraitType.EXPRESSION;
                        numExpressionProcessed++;
                        break;
                    case "HEAD":
                        traitType = TraitType.HEAD;
                        numHeadProcessed++;
                        break;
                    case "ARMS":
                        traitType = TraitType.ARMS;
                        numArmsProcessed++;
                        break;
                    default:
                        processErrors++;
                        System.out.println("ERROR: unmatched trait String " + traitTypeString);
                        traitType = TraitType.ERROR;
                }
                switch (rarityString){
                    case "PLATINUM":
                        rarity = Rarity.PLATINUM;
                        break;
                    case "GOLD":
                        rarity = Rarity.GOLD;
                        break;
                    case "SILVER":
                        rarity = Rarity.SILVER;
                        break;
                    case "BRONZE":
                        rarity = Rarity.BRONZE;
                        break;
                    default:
                        processErrors++;
                        System.out.println("ERROR: unmatched rarity String " + rarityString);
                        rarity = Rarity.ERROR;
                }

                if (numPhotos % numAnimationFrames > 0 ){
                    System.out.println("ERROR: Number of photos in folder #" + i+1 + " does not divide" +
                            "evenly by number of frames: " + numAnimationFrames);
                    processErrors++;
                }
                else if (numAnimationFrames > numPhotos ){
                    System.out.println("ERROR: Number of animation frames in in folder #" + (i+1) + " > number of photos") ;
                    processErrors++;
                }

                tempTraitFrames = new TraitFrame[numAnimationFrames];

                for (int j = 0; j < numAnimationFrames; j++) {

                    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    Graphics graphics = outputImage.getGraphics();

                    for (int k = 0; k < numPhotos/numAnimationFrames; k++) {

                        try {
                            BufferedImage sourceLayer = ImageIO.read(new File(imageDirectory + (i+1) + "\\" +
                                    ((j+1)+(k*numAnimationFrames)) +".png"));

                            graphics.drawImage(sourceLayer, 0, 0, null);

                        } catch (Exception e) {
                            processErrors++;
                            System.out.println("ERROR: Exception trying to draw image: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    TraitFrame tempTraitFrame = new TraitFrame(outputImage, (j+1));
                    tempTraitFrames[j] = tempTraitFrame;
                    graphics.dispose();
                }
                traitPool.addTrait(
                        i+1, new Trait(traitType, rarity, tempTraitFrames, layerPriority, i+1)
                );

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        System.out.println("STATS:");
        System.out.println("Unique Background Traits Input: " + numBackgroundProcessed);
        System.out.println("Unique Skin Traits Input: " + numSkinProcessed);
        System.out.println("Unique Base Traits Input: " + numBaseProcessed);
        System.out.println("Unique Clothes Traits Input: " + numClothesProcessed);
        System.out.println("Unique Expression Traits Input: " + numExpressionProcessed);
        System.out.println("Unique Head Traits Input: " + numHeadProcessed);
        System.out.println("Unique Arms Traits Input: " + numArmsProcessed);
        System.out.println("Errors during processing: " + processErrors);

        //exportTraitsTest(traitPool, imageDirectory);

        distributeTraitPoolByRarity(traitPool, rarityAppliedTraitIdDictionaries, platinumMultiplier, goldMultiplier, silverMultiplier, bronzeMultiplier);

        HashSet<String> uniqueBuildHashIds = new HashSet<>();
        String tempHolder = "";

        while (uniqueBuildHashIds.size()<10) {

            for (int i = 0; i < rarityAppliedTraitIdDictionaries.length; i++) {
                if (rarityAppliedTraitIdDictionaries[i].rangeMultiplier!=0)
                {
                    tempHolder += rarityAppliedTraitIdDictionaries[i].retrieveRandomTrait();
                    tempHolder += "-";
                }
            }
            tempHolder=tempHolder.substring(0, tempHolder.length()-1);
            uniqueBuildHashIds.add(tempHolder);
            System.out.println("Hashcode: " + tempHolder);
            tempHolder="";

        }

        System.out.println("Made it out of hash building, numElements: " + uniqueBuildHashIds.size());

        Iterator itr = uniqueBuildHashIds.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println(traitPool);



    }

    public static void distributeTraitPoolByRarity (
            TraitPool traitPool, RarityAppliedTraitIdDictionary[] rarityAppliedTraitIdDictionaries,
            int platinumMultiplier, int goldMultiplier, int silverMultiplier, int bronzeMultiplier
    ) throws IOException {

        RarityAppliedTraitIdDictionary backgroundDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.BACKGROUND, traitPool.getNumBackgroundTraits());

        RarityAppliedTraitIdDictionary skinDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.SKIN, traitPool.getNumSkinTraits());

        RarityAppliedTraitIdDictionary baseDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.BASE, traitPool.getNumBaseTraits());

        RarityAppliedTraitIdDictionary clothesDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.CLOTHES, traitPool.getNumClothesTraits());

        RarityAppliedTraitIdDictionary expressionDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.EXPRESSION, traitPool.getNumExpressionTraits());

        RarityAppliedTraitIdDictionary headDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.HEAD, traitPool.getNumHeadTraits());

        RarityAppliedTraitIdDictionary armsDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.ARMS, traitPool.getNumArmsTraits());

        int rangeBackground = 0;
        int rangeSkin = 0;
        int rangeBase = 0;
        int rangeClothes = 0;
        int rangeExpression = 0;
        int rangeHead = 0;
        int rangeArms = 0;

        for (Trait trait : traitPool.getBackgroundTraits()) {
            System.out.println("range background: " + rangeBackground);
            if(trait.getRarity().equals(Rarity.PLATINUM)){
                backgroundDictionary.addTrait(trait.getTraitId(), rangeBackground, rangeBackground+=platinumMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.GOLD)){
                backgroundDictionary.addTrait(trait.getTraitId(), rangeBackground, rangeBackground+=goldMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.SILVER)){
                backgroundDictionary.addTrait(trait.getTraitId(), rangeBackground, rangeBackground+=silverMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.BRONZE)){
                backgroundDictionary.addTrait(trait.getTraitId(), rangeBackground, rangeBackground+=bronzeMultiplier);
            }
        }
        backgroundDictionary.setRangeMultiplier(rangeBackground);
        System.out.println("range background multiplier " + rangeBackground);

        for (Trait trait : traitPool.getSkinTraits()) {
            if(trait.getRarity().equals(Rarity.PLATINUM)){
                skinDictionary.addTrait(trait.getTraitId(), rangeSkin, rangeSkin+=platinumMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.GOLD)){
                skinDictionary.addTrait(trait.getTraitId(), rangeSkin, rangeSkin+=goldMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.SILVER)){
                skinDictionary.addTrait(trait.getTraitId(), rangeSkin, rangeSkin+=silverMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.BRONZE)){
                skinDictionary.addTrait(trait.getTraitId(), rangeSkin, rangeSkin+=bronzeMultiplier);
            }
        }
        skinDictionary.setRangeMultiplier(rangeSkin);
        System.out.println("range skin multiplier " + rangeSkin);

        for (Trait trait : traitPool.getBaseTraits()) {
            if(trait.getRarity().equals(Rarity.PLATINUM)){
                baseDictionary.addTrait(trait.getTraitId(), rangeBase, rangeBase+=platinumMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.GOLD)){
                baseDictionary.addTrait(trait.getTraitId(), rangeBase, rangeBase+=goldMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.SILVER)){
                baseDictionary.addTrait(trait.getTraitId(), rangeBase, rangeBase+=silverMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.BRONZE)){
                baseDictionary.addTrait(trait.getTraitId(), rangeBase, rangeBase+=bronzeMultiplier);
            }
        }
        baseDictionary.setRangeMultiplier(rangeBase);

        for (Trait trait : traitPool.getClothesTraits()) {
            if(trait.getRarity().equals(Rarity.PLATINUM)){
                clothesDictionary.addTrait(trait.getTraitId(), rangeClothes, rangeClothes+=platinumMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.GOLD)){
                clothesDictionary.addTrait(trait.getTraitId(), rangeClothes, rangeClothes+=goldMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.SILVER)){
                clothesDictionary.addTrait(trait.getTraitId(), rangeClothes, rangeClothes+=silverMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.BRONZE)){
                clothesDictionary.addTrait(trait.getTraitId(), rangeClothes, rangeClothes+=bronzeMultiplier);
            }
        }
        clothesDictionary.setRangeMultiplier(rangeClothes);

        for (Trait trait : traitPool.getExpressionTraits()) {
            if(trait.getRarity().equals(Rarity.PLATINUM)){
                expressionDictionary.addTrait(trait.getTraitId(), rangeExpression, rangeExpression+=platinumMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.GOLD)){
                expressionDictionary.addTrait(trait.getTraitId(), rangeExpression, rangeExpression+=goldMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.SILVER)){
                expressionDictionary.addTrait(trait.getTraitId(), rangeExpression, rangeExpression+=silverMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.BRONZE)){
                expressionDictionary.addTrait(trait.getTraitId(), rangeExpression, rangeExpression+=bronzeMultiplier);
            }
        }
        expressionDictionary.setRangeMultiplier(rangeExpression);

        for (Trait trait : traitPool.getHeadTraits()) {
            if(trait.getRarity().equals(Rarity.PLATINUM)){
                headDictionary.addTrait(trait.getTraitId(), rangeHead, rangeHead+=platinumMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.GOLD)){
                headDictionary.addTrait(trait.getTraitId(), rangeHead, rangeHead+=goldMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.SILVER)){
                headDictionary.addTrait(trait.getTraitId(), rangeHead, rangeHead+=silverMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.BRONZE)){
                headDictionary.addTrait(trait.getTraitId(), rangeHead, rangeHead+=bronzeMultiplier);
            }
        }
        expressionDictionary.setRangeMultiplier(rangeHead);

        for (Trait trait : traitPool.getArmsTraits()) {
            if(trait.getRarity().equals(Rarity.PLATINUM)){
                armsDictionary.addTrait(trait.getTraitId(), rangeArms, rangeArms+=platinumMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.GOLD)){
                armsDictionary.addTrait(trait.getTraitId(), rangeArms, rangeArms+=goldMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.SILVER)){
                armsDictionary.addTrait(trait.getTraitId(), rangeArms, rangeArms+=silverMultiplier);
            }
            else if(trait.getRarity().equals(Rarity.BRONZE)){
                armsDictionary.addTrait(trait.getTraitId(), rangeArms, rangeArms+=bronzeMultiplier);
            }
        }
        expressionDictionary.setRangeMultiplier(rangeArms);

        rarityAppliedTraitIdDictionaries[0] = backgroundDictionary;
        rarityAppliedTraitIdDictionaries[1] = skinDictionary;
        rarityAppliedTraitIdDictionaries[2] = baseDictionary;
        rarityAppliedTraitIdDictionaries[3] = clothesDictionary;
        rarityAppliedTraitIdDictionaries[4] = expressionDictionary;
        rarityAppliedTraitIdDictionaries[5] = headDictionary;
        rarityAppliedTraitIdDictionaries[6] = armsDictionary;


//        for (Trait trait :
//                traitPool.getArmsTraits()) {
//            trait.export(imageDirectory + "output\\");
//        }
    }


}
