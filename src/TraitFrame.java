import java.awt.image.BufferedImage;

public class TraitFrame {
    private final BufferedImage image;
    private final int sequenceNumber;

    public BufferedImage getImage() {
        return image;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public TraitFrame(BufferedImage image, int sequenceNumber) {
        this.image = image;
        this.sequenceNumber = sequenceNumber;
    }
}
