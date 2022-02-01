import jdk.nashorn.internal.objects.annotations.Constructor;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Trait {

    private TraitType traitType;
    private Rarity rarity;
    private TraitFrame[] traitFrames;
    private int layerPriority;
    private final int traitId;
    private int numTimesUsed = 0;

    public Trait(TraitType traitType, Rarity rarity, TraitFrame[] traitFrames, int layerPriority, int traitId) {
        this.traitType = traitType;
        this.rarity = rarity;
        this.traitFrames = traitFrames;
        this.layerPriority = layerPriority;
        this.traitId = traitId;
    }

    public void export(String baseFilePath) throws IOException {
        File outputDirectory = new File(baseFilePath + traitId + "\\");
        if (!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }

        for (int i = 0; i < traitFrames.length; i++) {
            ImageIO.write(traitFrames[i].getImage(), "PNG", new File(outputDirectory,traitFrames[i].getSequenceNumber() + ".png"));
        }
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

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Trait #:" + traitId);
        sb.append(" || ");
        sb.append("Trait type: " + traitType.toString());
        sb.append(" || ");
        sb.append("Number of frames: " + traitFrames.length);
        sb.append(" || ");
        sb.append("Layer priority: " + layerPriority);
        sb.append(" || ");
        sb.append("Rarity: " + rarity.toString());
        sb.append(" || ");
        sb.append("Number of times used: " + numTimesUsed);
        sb.append("\n");

        return sb.toString();
    }

}
