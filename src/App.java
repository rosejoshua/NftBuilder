import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class App {

    public static void main(String[] args) throws IOException {

        BufferedReader reader;
        File file;
        String imageDirectory = "C:\\Users\\rosej\\IdeaProjects\\NftBuilder\\test\\";

        TraitPool traitPool = new TraitPool();

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
                        new Trait(traitType, rarity, tempTraitFrames, layerPriority, i+1)
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

        //Test output of frames
//        public Trait getBackgroundTrait(int index){
//            return backgroundTraits.get(index);
//        }
//        public Trait getSkinTrait(int index){
//            return skinTraits.get(index);
//        }
//        public Trait getBaseTrait(int index){
//            return baseTraits.get(index);
//        }
//        public Trait getClothesTrait(int index){
//            return clothesTraits.get(index);
//        }
//        public Trait getExpressionTrait(int index){
//            return expressionTraits.get(index);
//        }
//        public Trait getHeadTrait(int index){
//            return headTraits.get(index);
//        }
//        public Trait getArmsTrait(int index){
//            return armsTraits.get(index);
//        }

        for (Trait trait :
                traitPool.getBackgroundTraits()) {
            trait.export(imageDirectory + "output\\");
        }
        for (Trait trait :
                        traitPool.getSkinTraits()) {
                    trait.export(imageDirectory + "output\\");
                }
        for (Trait trait :
                        traitPool.getBaseTraits()) {
                    trait.export(imageDirectory + "output\\");
                }
        for (Trait trait :
                        traitPool.getClothesTraits()) {
                    trait.export(imageDirectory + "output\\");
                }
        for (Trait trait :
                        traitPool.getExpressionTraits()) {
                    trait.export(imageDirectory + "output\\");
                }
        for (Trait trait :
                        traitPool.getHeadTraits()) {
                    trait.export(imageDirectory + "output\\");
                }
        for (Trait trait :
                        traitPool.getArmsTraits()) {
                    trait.export(imageDirectory + "output\\");
                }

        System.out.println(traitPool);

    }

}
