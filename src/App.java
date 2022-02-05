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

        // TODO: 2/5/2022 read in text file for build specs to replace these 5
        int width = 300;
        int height = 300;
        int numTraitsFolders = 9;
        int numAnimationFramesOutput = 4;
        int maxLayerPriorities = 8;

        int numBackgroundProcessed = 0;
        int numSkinProcessed = 0;
        int numBaseProcessed = 0;
        int numClothesProcessed = 0;
        int numEyesProcessed = 0;
        int numMouthProcessed = 0;
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
                    case "EYES":
                        traitType = TraitType.EYES;
                        numEyesProcessed++;
                        break;
                    case "MOUTH":
                        traitType = TraitType.MOUTH;
                        numMouthProcessed++;
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
        System.out.println("Unique Eyes Traits Input: " + numEyesProcessed);
        System.out.println("Unique Mouth Traits Input: " + numMouthProcessed);
        System.out.println("Unique Head Traits Input: " + numHeadProcessed);
        System.out.println("Unique Arms Traits Input: " + numArmsProcessed);
        System.out.println("Errors during processing: " + processErrors);



        //exportTraitsTest(traitPool, imageDirectory);

        distributeTraitPoolByRarity(traitPool, rarityAppliedTraitIdDictionaries, platinumMultiplier, goldMultiplier, silverMultiplier, bronzeMultiplier,
                numBackgroundProcessed, numSkinProcessed, numBaseProcessed, numClothesProcessed, numEyesProcessed, numMouthProcessed, numHeadProcessed,
                numArmsProcessed);

        HashSet<String> uniqueBuildHashIds = new HashSet<>();
        String tempHolder = "";
        int numLoopsToBuildAllHashes = 0;

        while (uniqueBuildHashIds.size()<14) {

            for (int i = 0; i < rarityAppliedTraitIdDictionaries.length; i++) {
                if (rarityAppliedTraitIdDictionaries[i].rangeMultiplier!=0)
                {
                    tempHolder += rarityAppliedTraitIdDictionaries[i].retrieveRandomTrait();
                    tempHolder += "-";
                }
            }
            tempHolder=tempHolder.substring(0, tempHolder.length()-1);
            uniqueBuildHashIds.add(tempHolder);
            numLoopsToBuildAllHashes++;
            System.out.println("Hashcode: " + tempHolder);
            tempHolder="";

        }

        System.out.println("Made it out of hash building, numElements: " + uniqueBuildHashIds.size() + ", taking " +
                numLoopsToBuildAllHashes + " operations");

        //BUILDING OUTPUT HERE

        File outputDirectory = new File(imageDirectory + "output" + "\\");
        if (!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }

        Iterator itr = uniqueBuildHashIds.iterator();
        while (itr.hasNext()) {
            export(outputDirectory, traitPool, (String)itr.next(), maxLayerPriorities, numAnimationFramesOutput, width, height);
        }

        System.out.println(traitPool);

    }

    public static void export(File outputDirectory, TraitPool traitPool, String hashId,
                              int maxLayerPrioritiesFromOnetoN, int animationFrames,
                              int width, int height) throws IOException {
        File nftFolder = new File(outputDirectory + "\\" + hashId );
        nftFolder.mkdir();
        String[] traitIdsUnsorted = hashId.split("-");

        System.out.println("building nft based on hashId: " + hashId);

        BufferedImage[] imageBuffers = new BufferedImage[animationFrames];

        for (int i = 0; i < animationFrames; i++) {
            imageBuffers[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
            for (int i = 0; i < maxLayerPrioritiesFromOnetoN; i++) {
                for (String s : traitIdsUnsorted) {
                    if (traitPool.getTrait(Integer.parseInt(s)).getLayerPriority() == (i+1)) {
                        traitPool.getTrait(Integer.parseInt(s)).incrementNumTimesUsed();
                        TraitFrame[] traitFrames = traitPool.getTrait(Integer.parseInt(s)).getTraitFrames();
                        for (int j = 0; j < animationFrames;) {
                            for (TraitFrame traitFrame : traitFrames) {
                                Graphics graphics = imageBuffers[j].getGraphics();
                                BufferedImage sourceLayer = traitFrame.getImage();
                                graphics.drawImage(sourceLayer, 0, 0, null);
                                j++;
                            }
                        }
                    }
                }
            }

        for (int i = 0; i < imageBuffers.length; i++) {
            ImageIO.write(imageBuffers[i], "png",
                    new File(nftFolder.getAbsoluteFile() + "\\" + (i + 1) + ".png"));
        }
    }

    public static void distributeTraitPoolByRarity (
            TraitPool traitPool, RarityAppliedTraitIdDictionary[] rarityAppliedTraitIdDictionaries,
            int platinumMultiplier, int goldMultiplier, int silverMultiplier, int bronzeMultiplier,
            int numBackgroundTraits, int numSkinTraits, int numBaseTraits, int numClothesTraits,
            int numEyesTraits, int numMouthTraits, int numHeadTraits, int numArmsTraits
    ) throws IOException {

        RarityAppliedTraitIdDictionary backgroundDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.BACKGROUND, numBackgroundTraits);

        RarityAppliedTraitIdDictionary skinDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.SKIN, numSkinTraits);

        RarityAppliedTraitIdDictionary baseDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.BASE, numBaseTraits);

        RarityAppliedTraitIdDictionary clothesDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.CLOTHES, numClothesTraits);

        RarityAppliedTraitIdDictionary eyesDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.EYES, numEyesTraits);

        RarityAppliedTraitIdDictionary mouthDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.MOUTH, numMouthTraits);

        RarityAppliedTraitIdDictionary headDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.HEAD, numHeadTraits);

        RarityAppliedTraitIdDictionary armsDictionary =
                new RarityAppliedTraitIdDictionary(TraitType.ARMS, numArmsTraits);

        int rangeBackground = 0;
        int rangeSkin = 0;
        int rangeBase = 0;
        int rangeClothes = 0;
        int rangeEyes = 0;
        int rangeMouth = 0;
        int rangeHead = 0;
        int rangeArms = 0;

        {
            for (Trait trait : traitPool.getAllTraits()) {
                if(trait.getTraitType().equals(TraitType.BACKGROUND)){
                    System.out.println("range background: " + rangeBackground);
                    if (trait.getRarity().equals(Rarity.PLATINUM)) {
                        backgroundDictionary.addTrait(trait.getTraitId(), rangeBackground, rangeBackground += platinumMultiplier);
                    }
                    else if (trait.getRarity().equals(Rarity.GOLD)) {
                        backgroundDictionary.addTrait(trait.getTraitId(), rangeBackground, rangeBackground += goldMultiplier);
                    }
                    else if (trait.getRarity().equals(Rarity.SILVER)) {
                        backgroundDictionary.addTrait(trait.getTraitId(), rangeBackground, rangeBackground += silverMultiplier);
                    }
                    else if (trait.getRarity().equals(Rarity.BRONZE)) {
                        backgroundDictionary.addTrait(trait.getTraitId(), rangeBackground, rangeBackground += bronzeMultiplier);
                    }
                }

                else if(trait.getTraitType().equals(TraitType.SKIN)){
                    System.out.println("range skin: " + rangeSkin);
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

                else if (trait.getTraitType().equals(TraitType.BASE)){
                    System.out.println("range base " + rangeBase);
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

                else if(trait.getTraitType().equals(TraitType.CLOTHES)){
                    System.out.println("range clothes " + rangeClothes);
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

                else if (trait.getTraitType().equals(TraitType.EYES)){
                    System.out.println("range eyes " + rangeEyes);
                    if(trait.getRarity().equals(Rarity.PLATINUM)){
                        eyesDictionary.addTrait(trait.getTraitId(), rangeEyes, rangeEyes+=platinumMultiplier);
                    }
                    else if(trait.getRarity().equals(Rarity.GOLD)){
                        eyesDictionary.addTrait(trait.getTraitId(), rangeEyes, rangeEyes+=goldMultiplier);
                    }
                    else if(trait.getRarity().equals(Rarity.SILVER)){
                        eyesDictionary.addTrait(trait.getTraitId(), rangeEyes, rangeEyes+=silverMultiplier);
                    }
                    else if(trait.getRarity().equals(Rarity.BRONZE)){
                        eyesDictionary.addTrait(trait.getTraitId(), rangeEyes, rangeEyes+=bronzeMultiplier);
                    }
                }

                else if (trait.getTraitType().equals(TraitType.MOUTH)){
                    System.out.println("range mouth " + rangeMouth);
                    if(trait.getRarity().equals(Rarity.PLATINUM)){
                        mouthDictionary.addTrait(trait.getTraitId(), rangeMouth, rangeMouth+=platinumMultiplier);
                    }
                    else if(trait.getRarity().equals(Rarity.GOLD)){
                        mouthDictionary.addTrait(trait.getTraitId(), rangeMouth, rangeMouth+=goldMultiplier);
                    }
                    else if(trait.getRarity().equals(Rarity.SILVER)){
                        mouthDictionary.addTrait(trait.getTraitId(), rangeMouth, rangeMouth+=silverMultiplier);
                    }
                    else if(trait.getRarity().equals(Rarity.BRONZE)){
                        mouthDictionary.addTrait(trait.getTraitId(), rangeMouth, rangeMouth+=bronzeMultiplier);
                    }
                }

                else if (trait.getTraitType().equals(TraitType.HEAD)){
                    System.out.println("range head " + rangeHead);
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

                else if (trait.getTraitType().equals(TraitType.ARMS)){
                    System.out.println("range arms " + rangeArms);
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

            }
        }
        backgroundDictionary.setRangeMultiplier(rangeBackground);
        System.out.println("range background multiplier " + rangeBackground);

        skinDictionary.setRangeMultiplier(rangeSkin);
        System.out.println("range skin multiplier " + rangeSkin);

        baseDictionary.setRangeMultiplier(rangeBase);
        System.out.println("range base multiplier " + rangeBase);

        clothesDictionary.setRangeMultiplier(rangeClothes);
        System.out.println("range clothes multiplier " + rangeClothes);

        eyesDictionary.setRangeMultiplier(rangeEyes);
        System.out.println("range eyes multiplier " + rangeEyes);

        mouthDictionary.setRangeMultiplier(rangeMouth);
        System.out.println("range mouth multiplier " + rangeMouth);

        headDictionary.setRangeMultiplier(rangeHead);
        System.out.println("range head multiplier " + rangeHead);

        armsDictionary.setRangeMultiplier(rangeArms);
        System.out.println("range arms multiplier " + rangeArms);

        rarityAppliedTraitIdDictionaries[0] = backgroundDictionary;
        rarityAppliedTraitIdDictionaries[1] = skinDictionary;
        rarityAppliedTraitIdDictionaries[2] = baseDictionary;
        rarityAppliedTraitIdDictionaries[3] = clothesDictionary;
        rarityAppliedTraitIdDictionaries[4] = eyesDictionary;
        rarityAppliedTraitIdDictionaries[5] = mouthDictionary;
        rarityAppliedTraitIdDictionaries[6] = headDictionary;
        rarityAppliedTraitIdDictionaries[7] = armsDictionary;


    }


}
