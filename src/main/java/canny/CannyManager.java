package canny;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import org.omg.PortableServer.ServantRetentionPolicy;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;

/**
 * Created by bdanglot on 08/06/16.
 */
public class CannyManager extends ManagerImpl<BufferedImage, BufferedImage> {

    private static final String PATH_TO_DATASET = "resources/canny/t10k-images.idx3-ubyte";

    private static final int MAGIC_NUMBER = 2051;

    private int nbImage;

    private int nbRow;

    private int nbCol;

    private int sizeUsed;

    private DataInputStream reader;

    public CannyManager(int nbTask, int sizeTask) {
        this(nbTask, sizeTask, 23);
    }

    public CannyManager(int nbTask, int sizeTask, int seed) {
        super(seed);
        super.CUP = CannyEdgeDetectorInstr.class;
        super.initialize(nbTask, sizeTask);
    }

    @Override
    protected BufferedImage generateOneTask() {
        try {
            if (this.reader == null) {
                this.reader = new DataInputStream(new FileInputStream(
                        PATH_TO_DATASET));

                if (this.reader.readInt() != MAGIC_NUMBER) {
                    System.err.println("MAGIC NUMBER DOES NOT MATCH");
                    System.exit(-1);
                }

                this.nbImage = this.reader.readInt();
                this.nbRow = this.reader.readInt();
                this.nbCol = this.reader.readInt();

                this.sizeUsed = (int) (this.nbRow * 0.75);
            }

            BufferedImage img = new BufferedImage(this.nbCol, this.nbRow, BufferedImage.TYPE_INT_RGB);

            for (int j = 0; j < this.nbCol; j++)
                for (int i = 0; i < this.nbRow; i++) {
                    int b = this.reader.readUnsignedByte();
                    img.setRGB(i, j, (b << 16) | (b << 8) | b);
                }

            return scale(img, 0.75d);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CallableImpl<BufferedImage, BufferedImage> getCallable(BufferedImage input) {
        return new CallableImpl<BufferedImage, BufferedImage>(input) {
            public BufferedImage call() {
                CannyEdgeDetectorInstr canny = new CannyEdgeDetectorInstr();
                canny.setSourceImage(this.input);
                canny.process();
                return canny.getEdgesImage();
            }
        };
    }

    @Override
    public Oracle<BufferedImage, BufferedImage> getOracle() {
        return (input, output) -> {
            CannyEdgeDetector canny = new CannyEdgeDetector();
            canny.setSourceImage(input);
            canny.process();
            BufferedImage edges = canny.getEdgesImage();
            for (int x = 0; x < this.sizeUsed; x++) {
                for (int y = 0; y < this.sizeUsed; y++) {
                    if (edges.getRGB(x, y) != output.getRGB(x, y))
                        return false;
                }
            }
            return true;
        };
    }

    @Override
    public String getName() {
        return "canny";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " image of " + this.sizeUsed + "x" + this.sizeUsed + " pixels\n" +
                "Images come from dataset nist, reduce by 75% (28px to 21px)\n" +
                super.locations.size() + " perturbations points\n";
    }

    @Override
    public BufferedImage getTask(int indexOfTask) {
        if (indexOfTask >= super.tasks.size())
            super.getTask(indexOfTask);
        return super.tasks.get(indexOfTask);
    }

    public static void main(String[] args) {
        try {
            DataInputStream reader = new DataInputStream(new FileInputStream(
                    PATH_TO_DATASET));

            if (reader.readInt() != MAGIC_NUMBER) {
                System.err.println("MAGIC NUMBER DOES NOT MATCH");
                System.exit(-1);
            }

            CannyEdgeDetector c = new CannyEdgeDetector();

            int nbImage = reader.readInt();
            int nbRow = reader.readInt();
            int nbCol = reader.readInt();

            for (int x = 0; x < 1; x++) {
                BufferedImage img = new BufferedImage(nbRow, nbCol, BufferedImage.TYPE_INT_RGB);

                for (int j = 0; j < nbRow; j++) {
                    for (int i = 0; i < nbCol; i++) {
                        int b = reader.readUnsignedByte();
                        System.out.print(b + " ");
                        img.setRGB(i, j, (b << 16) | (b << 8) | b);
                    }
                    System.out.println();
                }

                File f = new File("MyFile" + x + ".png");

                img = scale(img, 1.0);

                ImageIO.write(img, "PNG", f);

                c.setSourceImage(img);

                c.process();

                f = new File("MyFile-canny" + x + ".png");

                ImageIO.write(c.getEdgesImage(), "PNG", f);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage scale(BufferedImage bi, double scaleValue) {
        AffineTransform tx = new AffineTransform();
        tx.scale(scaleValue, scaleValue);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage biNew = new BufferedImage((int) (bi.getWidth() * scaleValue),
                (int) (bi.getHeight() * scaleValue),
                bi.getType());
        return op.filter(bi, biNew);
    }

}