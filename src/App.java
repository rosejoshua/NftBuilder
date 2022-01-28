import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class App {

    public static void main(String[] args) {

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
                }

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

    }

}
