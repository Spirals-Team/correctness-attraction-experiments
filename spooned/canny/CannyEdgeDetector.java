

package canny;


public class CannyEdgeDetector {
    private static final float GAUSSIAN_CUT_OFF = 0.005F;

    private static final float MAGNITUDE_SCALE = 100.0F;

    private static final float MAGNITUDE_LIMIT = 1000.0F;

    private static final int MAGNITUDE_MAX = ((int) ((canny.CannyEdgeDetector.MAGNITUDE_SCALE) * (canny.CannyEdgeDetector.MAGNITUDE_LIMIT)));

    private int height;

    private int width;

    private int picsize;

    private int[] data;

    private int[] magnitude;

    private java.awt.image.BufferedImage sourceImage;

    private java.awt.image.BufferedImage edgesImage;

    private float gaussianKernelRadius;

    private float lowThreshold;

    private float highThreshold;

    private int gaussianKernelWidth;

    private boolean contrastNormalized;

    private float[] xConv;

    private float[] yConv;

    private float[] xGradient;

    private float[] yGradient;

    public CannyEdgeDetector() {
        lowThreshold = 2.5F;
        highThreshold = 7.5F;
        gaussianKernelRadius = 2.0F;
        gaussianKernelWidth = 16;
        contrastNormalized = false;
    }

    public java.awt.image.BufferedImage getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(java.awt.image.BufferedImage image) {
        sourceImage = image;
    }

    public java.awt.image.BufferedImage getEdgesImage() {
        return edgesImage;
    }

    public void setEdgesImage(java.awt.image.BufferedImage edgesImage) {
        canny.CannyEdgeDetector.this.edgesImage = edgesImage;
    }

    public float getLowThreshold() {
        return lowThreshold;
    }

    public void setLowThreshold(float threshold) {
        if (threshold < 0)
            throw new java.lang.IllegalArgumentException();
        
        lowThreshold = threshold;
    }

    public float getHighThreshold() {
        return highThreshold;
    }

    public void setHighThreshold(float threshold) {
        if (threshold < 0)
            throw new java.lang.IllegalArgumentException();
        
        highThreshold = threshold;
    }

    public int getGaussianKernelWidth() {
        return gaussianKernelWidth;
    }

    public void setGaussianKernelWidth(int gaussianKernelWidth) {
        if (gaussianKernelWidth < 2)
            throw new java.lang.IllegalArgumentException();
        
        canny.CannyEdgeDetector.this.gaussianKernelWidth = gaussianKernelWidth;
    }

    public float getGaussianKernelRadius() {
        return gaussianKernelRadius;
    }

    public void setGaussianKernelRadius(float gaussianKernelRadius) {
        if (gaussianKernelRadius < 0.1F)
            throw new java.lang.IllegalArgumentException();
        
        canny.CannyEdgeDetector.this.gaussianKernelRadius = gaussianKernelRadius;
    }

    public boolean isContrastNormalized() {
        return contrastNormalized;
    }

    public void setContrastNormalized(boolean contrastNormalized) {
        canny.CannyEdgeDetector.this.contrastNormalized = contrastNormalized;
    }

    public void process() {
        width = sourceImage.getWidth();
        height = sourceImage.getHeight();
        picsize = (width) * (height);
        initArrays();
        readLuminance();
        if (contrastNormalized)
            normalizeContrast();
        
        computeGradients(gaussianKernelRadius, gaussianKernelWidth);
        int low = java.lang.Math.round(((lowThreshold) * (canny.CannyEdgeDetector.MAGNITUDE_SCALE)));
        int high = java.lang.Math.round(((highThreshold) * (canny.CannyEdgeDetector.MAGNITUDE_SCALE)));
        performHysteresis(low, high);
        thresholdEdges();
        writeEdges(data);
    }

    private void initArrays() {
        if (((data) == null) || ((picsize) != (data.length))) {
            data = new int[picsize];
            magnitude = new int[picsize];
            xConv = new float[picsize];
            yConv = new float[picsize];
            xGradient = new float[picsize];
            yGradient = new float[picsize];
        } 
    }

    private void computeGradients(float kernelRadius, int kernelWidth) {
        float[] kernel = new float[kernelWidth];
        float[] diffKernel = new float[kernelWidth];
        int kwidth;
        for (kwidth = 0; kwidth < kernelWidth; kwidth++) {
            float g1 = gaussian(kwidth, kernelRadius);
            if ((g1 <= (canny.CannyEdgeDetector.GAUSSIAN_CUT_OFF)) && (kwidth >= 2))
                break;
            
            float g2 = gaussian((kwidth - 0.5F), kernelRadius);
            float g3 = gaussian((kwidth + 0.5F), kernelRadius);
            kernel[kwidth] = (((g1 + g2) + g3) / 3.0F) / (((2.0F * ((float) (java.lang.Math.PI))) * kernelRadius) * kernelRadius);
            diffKernel[kwidth] = g3 - g2;
        }
        int initX = kwidth - 1;
        int maxX = (width) - (kwidth - 1);
        int initY = (width) * (kwidth - 1);
        int maxY = (width) * ((height) - (kwidth - 1));
        for (int x = initX; x < maxX; x++) {
            for (int y = initY; y < maxY; y += width) {
                int index = x + y;
                float sumX = (data[index]) * (kernel[0]);
                float sumY = sumX;
                int xOffset = 1;
                int yOffset = width;
                for (; xOffset < kwidth;) {
                    sumY += (kernel[xOffset]) * ((data[(index - yOffset)]) + (data[(index + yOffset)]));
                    sumX += (kernel[xOffset]) * ((data[(index - xOffset)]) + (data[(index + xOffset)]));
                    yOffset += width;
                    xOffset++;
                }
                yConv[index] = sumY;
                xConv[index] = sumX;
            }
        }
        for (int x = initX; x < maxX; x++) {
            for (int y = initY; y < maxY; y += width) {
                float sum = 0.0F;
                int index = x + y;
                for (int i = 1; i < kwidth; i++)
                    sum += (diffKernel[i]) * ((yConv[(index - i)]) - (yConv[(index + i)]));
                xGradient[index] = sum;
            }
        }
        for (int x = kwidth; x < ((width) - kwidth); x++) {
            for (int y = initY; y < maxY; y += width) {
                float sum = 0.0F;
                int index = x + y;
                int yOffset = width;
                for (int i = 1; i < kwidth; i++) {
                    sum += (diffKernel[i]) * ((xConv[(index - yOffset)]) - (xConv[(index + yOffset)]));
                    yOffset += width;
                }
                yGradient[index] = sum;
            }
        }
        initX = kwidth;
        maxX = (width) - kwidth;
        initY = (width) * kwidth;
        maxY = (width) * ((height) - kwidth);
        for (int x = initX; x < maxX; x++) {
            for (int y = initY; y < maxY; y += width) {
                int index = x + y;
                int indexN = index - (width);
                int indexS = index + (width);
                int indexW = index - 1;
                int indexE = index + 1;
                int indexNW = indexN - 1;
                int indexNE = indexN + 1;
                int indexSW = indexS - 1;
                int indexSE = indexS + 1;
                float xGrad = xGradient[index];
                float yGrad = yGradient[index];
                float gradMag = hypot(xGrad, yGrad);
                float nMag = hypot(xGradient[indexN], yGradient[indexN]);
                float sMag = hypot(xGradient[indexS], yGradient[indexS]);
                float wMag = hypot(xGradient[indexW], yGradient[indexW]);
                float eMag = hypot(xGradient[indexE], yGradient[indexE]);
                float neMag = hypot(xGradient[indexNE], yGradient[indexNE]);
                float seMag = hypot(xGradient[indexSE], yGradient[indexSE]);
                float swMag = hypot(xGradient[indexSW], yGradient[indexSW]);
                float nwMag = hypot(xGradient[indexNW], yGradient[indexNW]);
                float tmp;
                if ((xGrad * yGrad) <= ((float) (0)) ? (java.lang.Math.abs(xGrad)) >= (java.lang.Math.abs(yGrad)) ? ((tmp = java.lang.Math.abs((xGrad * gradMag))) >= (java.lang.Math.abs(((yGrad * neMag) - ((xGrad + yGrad) * eMag))))) && (tmp > (java.lang.Math.abs(((yGrad * swMag) - ((xGrad + yGrad) * wMag))))) : ((tmp = java.lang.Math.abs((yGrad * gradMag))) >= (java.lang.Math.abs(((xGrad * neMag) - ((yGrad + xGrad) * nMag))))) && (tmp > (java.lang.Math.abs(((xGrad * swMag) - ((yGrad + xGrad) * sMag))))) : (java.lang.Math.abs(xGrad)) >= (java.lang.Math.abs(yGrad)) ? ((tmp = java.lang.Math.abs((xGrad * gradMag))) >= (java.lang.Math.abs(((yGrad * seMag) + ((xGrad - yGrad) * eMag))))) && (tmp > (java.lang.Math.abs(((yGrad * nwMag) + ((xGrad - yGrad) * wMag))))) : ((tmp = java.lang.Math.abs((yGrad * gradMag))) >= (java.lang.Math.abs(((xGrad * seMag) + ((yGrad - xGrad) * sMag))))) && (tmp > (java.lang.Math.abs(((xGrad * nwMag) + ((yGrad - xGrad) * nMag)))))) {
                    magnitude[index] = (gradMag >= (canny.CannyEdgeDetector.MAGNITUDE_LIMIT)) ? canny.CannyEdgeDetector.MAGNITUDE_MAX : ((int) ((canny.CannyEdgeDetector.MAGNITUDE_SCALE) * gradMag));
                } else {
                    magnitude[index] = 0;
                }
            }
        }
    }

    private float hypot(float x, float y) {
        return ((float) (java.lang.Math.hypot(x, y)));
    }

    private float gaussian(float x, float sigma) {
        return ((float) (java.lang.Math.exp(((-(x * x)) / ((2.0F * sigma) * sigma)))));
    }

    private void performHysteresis(int low, int high) {
        java.util.Arrays.fill(data, 0);
        int offset = 0;
        for (int y = 0; y < (height); y++) {
            for (int x = 0; x < (width); x++) {
                if (((data[offset]) == 0) && ((magnitude[offset]) >= high)) {
                    follow(x, y, offset, low);
                } 
                offset++;
            }
        }
    }

    private void follow(int x1, int y1, int i1, int threshold) {
        int x0 = x1 == 0 ? x1 : x1 - 1;
        int x2 = x1 == ((width) - 1) ? x1 : x1 + 1;
        int y0 = y1 == 0 ? y1 : y1 - 1;
        int y2 = y1 == ((height) - 1) ? y1 : y1 + 1;
        data[i1] = magnitude[i1];
        for (int x = x0; x <= x2; x++) {
            for (int y = y0; y <= y2; y++) {
                int i2 = x + (y * (width));
                if ((((y != y1) || (x != x1)) && ((data[i2]) == 0)) && ((magnitude[i2]) >= threshold)) {
                    follow(x, y, i2, threshold);
                    return ;
                } 
            }
        }
    }

    private void thresholdEdges() {
        for (int i = 0; i < (picsize); i++) {
            data[i] = ((data[i]) > 0) ? -1 : -16777216;
        }
    }

    private int luminance(float r, float g, float b) {
        return java.lang.Math.round((((0.299F * r) + (0.587F * g)) + (0.114F * b)));
    }

    private void readLuminance() {
        int type = sourceImage.getType();
        if ((type == (java.awt.image.BufferedImage.TYPE_INT_RGB)) || (type == (java.awt.image.BufferedImage.TYPE_INT_ARGB))) {
            int[] pixels = ((int[]) (sourceImage.getData().getDataElements(0, 0, width, height, null)));
            for (int i = 0; i < (picsize); i++) {
                int p = pixels[i];
                int r = (p & 16711680) >> 16;
                int g = (p & 65280) >> 8;
                int b = p & 255;
                data[i] = luminance(r, g, b);
            }
        } else if (type == (java.awt.image.BufferedImage.TYPE_BYTE_GRAY)) {
            byte[] pixels = ((byte[]) (sourceImage.getData().getDataElements(0, 0, width, height, null)));
            for (int i = 0; i < (picsize); i++) {
                data[i] = (pixels[i]) & 255;
            }
        } else if (type == (java.awt.image.BufferedImage.TYPE_USHORT_GRAY)) {
            short[] pixels = ((short[]) (sourceImage.getData().getDataElements(0, 0, width, height, null)));
            for (int i = 0; i < (picsize); i++) {
                data[i] = ((pixels[i]) & 65535) / 256;
            }
        } else if (type == (java.awt.image.BufferedImage.TYPE_3BYTE_BGR)) {
            byte[] pixels = ((byte[]) (sourceImage.getData().getDataElements(0, 0, width, height, null)));
            int offset = 0;
            for (int i = 0; i < (picsize); i++) {
                int b = (pixels[(offset++)]) & 255;
                int g = (pixels[(offset++)]) & 255;
                int r = (pixels[(offset++)]) & 255;
                data[i] = luminance(r, g, b);
            }
        } else {
            throw new java.lang.IllegalArgumentException(("Unsupported image type: " + type));
        }
    }

    private void normalizeContrast() {
        int[] histogram = new int[256];
        for (int i = 0; i < (data.length); i++) {
            (histogram[data[i]])++;
        }
        int[] remap = new int[256];
        int sum = 0;
        int j = 0;
        for (int i = 0; i < (histogram.length); i++) {
            sum += histogram[i];
            int target = (sum * 255) / (picsize);
            for (int k = j + 1; k <= target; k++) {
                remap[k] = i;
            }
            j = target;
        }
        for (int i = 0; i < (data.length); i++) {
            data[i] = remap[data[i]];
        }
    }

    private void writeEdges(int[] pixels) {
        if ((edgesImage) == null) {
            edgesImage = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        } 
        edgesImage.getWritableTile(0, 0).setDataElements(0, 0, width, height, pixels);
    }
}

