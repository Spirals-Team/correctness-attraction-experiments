

package canny;

import java.util.Arrays;
import java.awt.image.BufferedImage;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

public class CannyEdgeDetectorInstr {
    static {
        CannyEdgeDetectorInstr.initPerturbationLocation0();
    }

    public static PerturbationLocation __L0;

    public static PerturbationLocation __L1;

    public static PerturbationLocation __L2;

    public static PerturbationLocation __L3;

    public static PerturbationLocation __L4;

    public static PerturbationLocation __L5;

    public static PerturbationLocation __L6;

    public static PerturbationLocation __L7;

    public static PerturbationLocation __L8;

    public static PerturbationLocation __L9;

    public static PerturbationLocation __L10;

    public static PerturbationLocation __L11;

    public static PerturbationLocation __L12;

    public static PerturbationLocation __L13;

    public static PerturbationLocation __L14;

    public static PerturbationLocation __L15;

    public static PerturbationLocation __L16;

    public static PerturbationLocation __L17;

    public static PerturbationLocation __L18;

    public static PerturbationLocation __L19;

    public static PerturbationLocation __L20;

    public static PerturbationLocation __L21;

    public static PerturbationLocation __L22;

    public static PerturbationLocation __L23;

    public static PerturbationLocation __L24;

    public static PerturbationLocation __L25;

    public static PerturbationLocation __L26;

    public static PerturbationLocation __L27;

    public static PerturbationLocation __L28;

    public static PerturbationLocation __L29;

    public static PerturbationLocation __L30;

    public static PerturbationLocation __L31;

    public static PerturbationLocation __L32;

    public static PerturbationLocation __L33;

    public static PerturbationLocation __L34;

    public static PerturbationLocation __L35;

    public static PerturbationLocation __L36;

    public static PerturbationLocation __L37;

    public static PerturbationLocation __L38;

    public static PerturbationLocation __L39;

    public static PerturbationLocation __L40;

    public static PerturbationLocation __L41;

    public static PerturbationLocation __L42;

    public static PerturbationLocation __L43;

    public static PerturbationLocation __L44;

    public static PerturbationLocation __L45;

    public static PerturbationLocation __L46;

    public static PerturbationLocation __L47;

    public static PerturbationLocation __L48;

    public static PerturbationLocation __L49;

    public static PerturbationLocation __L50;

    public static PerturbationLocation __L51;

    public static PerturbationLocation __L52;

    public static PerturbationLocation __L53;

    public static PerturbationLocation __L54;

    public static PerturbationLocation __L55;

    public static PerturbationLocation __L56;

    public static PerturbationLocation __L57;

    public static PerturbationLocation __L58;

    public static PerturbationLocation __L59;

    public static PerturbationLocation __L60;

    public static PerturbationLocation __L61;

    public static PerturbationLocation __L62;

    public static PerturbationLocation __L63;

    public static PerturbationLocation __L64;

    public static PerturbationLocation __L65;

    public static PerturbationLocation __L66;

    public static PerturbationLocation __L67;

    public static PerturbationLocation __L68;

    public static PerturbationLocation __L69;

    public static PerturbationLocation __L70;

    public static PerturbationLocation __L71;

    public static PerturbationLocation __L72;

    public static PerturbationLocation __L73;

    public static PerturbationLocation __L74;

    public static PerturbationLocation __L75;

    public static PerturbationLocation __L76;

    public static PerturbationLocation __L77;

    public static PerturbationLocation __L78;

    public static PerturbationLocation __L79;

    public static PerturbationLocation __L80;

    public static PerturbationLocation __L81;

    public static PerturbationLocation __L82;

    public static PerturbationLocation __L83;

    public static PerturbationLocation __L84;

    public static PerturbationLocation __L85;

    public static PerturbationLocation __L86;

    public static PerturbationLocation __L87;

    public static PerturbationLocation __L88;

    public static PerturbationLocation __L89;

    public static PerturbationLocation __L90;

    public static PerturbationLocation __L91;

    public static PerturbationLocation __L92;

    public static PerturbationLocation __L93;

    public static PerturbationLocation __L94;

    public static PerturbationLocation __L95;

    public static PerturbationLocation __L96;

    public static PerturbationLocation __L97;

    public static PerturbationLocation __L98;

    public static PerturbationLocation __L99;

    public static PerturbationLocation __L100;

    public static PerturbationLocation __L101;

    public static PerturbationLocation __L102;

    public static PerturbationLocation __L103;

    public static PerturbationLocation __L104;

    public static PerturbationLocation __L105;

    public static PerturbationLocation __L106;

    public static PerturbationLocation __L107;

    public static PerturbationLocation __L108;

    public static PerturbationLocation __L109;

    public static PerturbationLocation __L110;

    public static PerturbationLocation __L111;

    public static PerturbationLocation __L112;

    public static PerturbationLocation __L113;

    public static PerturbationLocation __L114;

    public static PerturbationLocation __L115;

    public static PerturbationLocation __L116;

    public static PerturbationLocation __L117;

    public static PerturbationLocation __L118;

    public static PerturbationLocation __L119;

    public static PerturbationLocation __L120;

    public static PerturbationLocation __L121;

    public static PerturbationLocation __L122;

    public static PerturbationLocation __L123;

    public static PerturbationLocation __L124;

    public static PerturbationLocation __L125;

    public static PerturbationLocation __L126;

    public static PerturbationLocation __L127;

    public static PerturbationLocation __L128;

    public static PerturbationLocation __L129;

    public static PerturbationLocation __L130;

    public static PerturbationLocation __L131;

    public static PerturbationLocation __L132;

    public static PerturbationLocation __L133;

    public static PerturbationLocation __L134;

    public static PerturbationLocation __L135;

    public static PerturbationLocation __L136;

    public static PerturbationLocation __L137;

    public static PerturbationLocation __L138;

    public static PerturbationLocation __L139;

    public static PerturbationLocation __L140;

    public static PerturbationLocation __L141;

    public static PerturbationLocation __L142;

    public static PerturbationLocation __L143;

    public static PerturbationLocation __L144;

    public static PerturbationLocation __L145;

    public static PerturbationLocation __L146;

    public static PerturbationLocation __L147;

    public static PerturbationLocation __L148;

    public static PerturbationLocation __L149;

    public static PerturbationLocation __L150;

    public static PerturbationLocation __L151;

    public static PerturbationLocation __L152;

    public static PerturbationLocation __L153;

    public static PerturbationLocation __L154;

    public static PerturbationLocation __L155;

    public static PerturbationLocation __L156;

    public static PerturbationLocation __L157;

    public static PerturbationLocation __L158;

    public static PerturbationLocation __L159;

    public static PerturbationLocation __L160;

    public static PerturbationLocation __L161;

    public static PerturbationLocation __L162;

    public static PerturbationLocation __L163;

    public static PerturbationLocation __L164;

    public static PerturbationLocation __L165;

    public static PerturbationLocation __L166;

    public static PerturbationLocation __L167;

    public static PerturbationLocation __L168;

    public static PerturbationLocation __L169;

    public static PerturbationLocation __L170;

    public static PerturbationLocation __L171;

    public static PerturbationLocation __L172;

    public static PerturbationLocation __L173;

    public static PerturbationLocation __L174;

    public static PerturbationLocation __L175;

    public static PerturbationLocation __L176;

    public static PerturbationLocation __L177;

    public static PerturbationLocation __L178;

    public static PerturbationLocation __L179;

    public static PerturbationLocation __L180;

    public static PerturbationLocation __L181;

    public static PerturbationLocation __L182;

    public static PerturbationLocation __L183;

    public static PerturbationLocation __L184;

    public static PerturbationLocation __L185;

    public static PerturbationLocation __L186;

    public static PerturbationLocation __L187;

    public static PerturbationLocation __L188;

    public static PerturbationLocation __L189;

    public static PerturbationLocation __L190;

    public static PerturbationLocation __L191;

    public static PerturbationLocation __L192;

    public static PerturbationLocation __L193;

    public static PerturbationLocation __L194;

    public static PerturbationLocation __L195;

    public static PerturbationLocation __L196;

    public static PerturbationLocation __L197;

    public static PerturbationLocation __L198;

    public static PerturbationLocation __L199;

    public static PerturbationLocation __L200;

    public static PerturbationLocation __L201;

    public static PerturbationLocation __L202;

    public static PerturbationLocation __L203;

    public static PerturbationLocation __L204;

    public static PerturbationLocation __L205;

    public static PerturbationLocation __L206;

    public static PerturbationLocation __L207;

    public static PerturbationLocation __L208;

    public static PerturbationLocation __L209;

    public static PerturbationLocation __L210;

    public static PerturbationLocation __L211;

    public static PerturbationLocation __L212;

    public static PerturbationLocation __L213;

    public static PerturbationLocation __L214;

    public static PerturbationLocation __L215;

    public static PerturbationLocation __L216;

    public static PerturbationLocation __L217;

    public static PerturbationLocation __L218;

    public static PerturbationLocation __L219;

    public static PerturbationLocation __L220;

    public static PerturbationLocation __L221;

    public static PerturbationLocation __L222;

    public static PerturbationLocation __L223;

    public static PerturbationLocation __L224;

    public static PerturbationLocation __L225;

    public static PerturbationLocation __L226;

    public static PerturbationLocation __L227;

    public static PerturbationLocation __L228;

    public static PerturbationLocation __L229;

    public static PerturbationLocation __L230;

    public static PerturbationLocation __L231;

    public static PerturbationLocation __L232;

    public static PerturbationLocation __L233;

    public static PerturbationLocation __L234;

    public static PerturbationLocation __L235;

    public static PerturbationLocation __L236;

    public static PerturbationLocation __L237;

    public static PerturbationLocation __L238;

    public static PerturbationLocation __L239;

    public static PerturbationLocation __L240;

    public static PerturbationLocation __L241;

    public static PerturbationLocation __L242;

    public static PerturbationLocation __L243;

    public static PerturbationLocation __L244;

    public static PerturbationLocation __L245;

    public static PerturbationLocation __L246;

    public static PerturbationLocation __L247;

    public static PerturbationLocation __L248;

    public static PerturbationLocation __L249;

    public static PerturbationLocation __L250;

    public static PerturbationLocation __L251;

    public static PerturbationLocation __L252;

    public static PerturbationLocation __L253;

    public static PerturbationLocation __L254;

    public static PerturbationLocation __L255;

    public static PerturbationLocation __L256;

    public static PerturbationLocation __L257;

    public static PerturbationLocation __L258;

    public static PerturbationLocation __L259;

    public static PerturbationLocation __L260;

    public static PerturbationLocation __L261;

    public static PerturbationLocation __L262;

    public static PerturbationLocation __L263;

    public static PerturbationLocation __L264;

    public static PerturbationLocation __L265;

    public static PerturbationLocation __L266;

    public static PerturbationLocation __L267;

    public static PerturbationLocation __L268;

    public static PerturbationLocation __L269;

    public static PerturbationLocation __L270;

    public static PerturbationLocation __L271;

    public static PerturbationLocation __L272;

    public static PerturbationLocation __L273;

    public static PerturbationLocation __L274;

    public static PerturbationLocation __L275;

    public static PerturbationLocation __L276;

    public static PerturbationLocation __L277;

    public static PerturbationLocation __L278;

    public static PerturbationLocation __L279;

    public static PerturbationLocation __L280;

    public static PerturbationLocation __L281;

    public static PerturbationLocation __L282;

    public static PerturbationLocation __L283;

    public static PerturbationLocation __L284;

    public static PerturbationLocation __L285;

    public static PerturbationLocation __L286;

    public static PerturbationLocation __L287;

    public static PerturbationLocation __L288;

    public static PerturbationLocation __L289;

    public static PerturbationLocation __L290;

    public static PerturbationLocation __L291;

    public static PerturbationLocation __L292;

    public static PerturbationLocation __L293;

    public static PerturbationLocation __L294;

    public static PerturbationLocation __L295;

    public static PerturbationLocation __L296;

    public static PerturbationLocation __L297;

    public static PerturbationLocation __L298;

    public static PerturbationLocation __L299;

    public static PerturbationLocation __L300;

    public static PerturbationLocation __L301;

    public static PerturbationLocation __L302;

    public static PerturbationLocation __L303;

    public static PerturbationLocation __L304;

    public static PerturbationLocation __L305;

    public static PerturbationLocation __L306;

    public static PerturbationLocation __L307;

    public static PerturbationLocation __L308;

    public static PerturbationLocation __L309;

    public static PerturbationLocation __L310;

    public static PerturbationLocation __L311;

    public static PerturbationLocation __L312;

    public static PerturbationLocation __L313;

    public static PerturbationLocation __L314;

    public static PerturbationLocation __L315;

    public static PerturbationLocation __L316;

    public static PerturbationLocation __L317;

    public static PerturbationLocation __L318;

    public static PerturbationLocation __L319;

    public static PerturbationLocation __L320;

    public static PerturbationLocation __L321;

    public static PerturbationLocation __L322;

    public static PerturbationLocation __L323;

    public static PerturbationLocation __L324;

    public static PerturbationLocation __L325;

    public static PerturbationLocation __L326;

    public static PerturbationLocation __L327;

    public static PerturbationLocation __L328;

    public static PerturbationLocation __L329;

    public static PerturbationLocation __L330;

    public static PerturbationLocation __L331;

    public static PerturbationLocation __L332;

    public static PerturbationLocation __L333;

    public static PerturbationLocation __L334;

    public static PerturbationLocation __L335;

    public static PerturbationLocation __L336;

    public static PerturbationLocation __L337;

    public static PerturbationLocation __L338;

    public static PerturbationLocation __L339;

    public static PerturbationLocation __L340;

    public static PerturbationLocation __L341;

    public static PerturbationLocation __L342;

    public static PerturbationLocation __L343;

    public static PerturbationLocation __L344;

    public static PerturbationLocation __L345;

    public static PerturbationLocation __L346;

    public static PerturbationLocation __L347;

    public static PerturbationLocation __L348;

    public static PerturbationLocation __L349;

    public static PerturbationLocation __L350;

    public static PerturbationLocation __L351;

    public static PerturbationLocation __L352;

    public static PerturbationLocation __L353;

    public static PerturbationLocation __L354;

    public static PerturbationLocation __L355;

    public static PerturbationLocation __L356;

    public static PerturbationLocation __L357;

    public static PerturbationLocation __L358;

    public static PerturbationLocation __L359;

    public static PerturbationLocation __L360;

    public static PerturbationLocation __L361;

    public static PerturbationLocation __L362;

    public static PerturbationLocation __L363;

    public static PerturbationLocation __L364;

    public static PerturbationLocation __L365;

    public static PerturbationLocation __L366;

    public static PerturbationLocation __L367;

    public static PerturbationLocation __L368;

    public static PerturbationLocation __L369;

    public static PerturbationLocation __L370;

    public static PerturbationLocation __L371;

    public static PerturbationLocation __L372;

    public static PerturbationLocation __L373;

    public static PerturbationLocation __L374;

    public static PerturbationLocation __L375;

    public static PerturbationLocation __L376;

    public static PerturbationLocation __L377;

    public static PerturbationLocation __L378;

    public static PerturbationLocation __L379;

    public static PerturbationLocation __L380;

    public static PerturbationLocation __L381;

    public static PerturbationLocation __L382;

    public static PerturbationLocation __L383;

    public static PerturbationLocation __L384;

    public static PerturbationLocation __L385;

    public static PerturbationLocation __L386;

    public static PerturbationLocation __L387;

    public static PerturbationLocation __L388;

    public static PerturbationLocation __L389;

    public static PerturbationLocation __L390;

    public static PerturbationLocation __L391;

    public static PerturbationLocation __L392;

    public static PerturbationLocation __L393;

    public static PerturbationLocation __L394;

    public static PerturbationLocation __L395;

    public static PerturbationLocation __L396;

    public static PerturbationLocation __L397;

    public static PerturbationLocation __L398;

    public static PerturbationLocation __L399;

    public static PerturbationLocation __L400;

    public static PerturbationLocation __L401;

    public static PerturbationLocation __L402;

    public static PerturbationLocation __L403;

    public static PerturbationLocation __L404;

    public static PerturbationLocation __L405;

    public static PerturbationLocation __L406;

    public static PerturbationLocation __L407;

    public static PerturbationLocation __L408;

    public static PerturbationLocation __L409;

    public static PerturbationLocation __L410;

    public static PerturbationLocation __L411;

    public static PerturbationLocation __L412;

    public static PerturbationLocation __L413;

    public static PerturbationLocation __L414;

    public static PerturbationLocation __L415;

    public static PerturbationLocation __L416;

    public static PerturbationLocation __L417;

    public static PerturbationLocation __L418;

    public static PerturbationLocation __L419;

    public static PerturbationLocation __L420;

    public static PerturbationLocation __L421;

    public static PerturbationLocation __L422;

    public static PerturbationLocation __L423;

    public static PerturbationLocation __L424;

    public static PerturbationLocation __L425;

    public static PerturbationLocation __L426;

    public static PerturbationLocation __L427;

    public static PerturbationLocation __L428;

    public static PerturbationLocation __L429;

    public static PerturbationLocation __L430;

    public static PerturbationLocation __L431;

    public static PerturbationLocation __L432;

    public static PerturbationLocation __L433;

    public static PerturbationLocation __L434;

    public static PerturbationLocation __L435;

    public static PerturbationLocation __L436;

    public static PerturbationLocation __L437;

    public static PerturbationLocation __L438;

    public static PerturbationLocation __L439;

    public static PerturbationLocation __L440;

    public static PerturbationLocation __L441;

    public static PerturbationLocation __L442;

    public static PerturbationLocation __L443;

    public static PerturbationLocation __L444;

    public static PerturbationLocation __L445;

    public static PerturbationLocation __L446;

    public static PerturbationLocation __L447;

    public static PerturbationLocation __L448;

    public static PerturbationLocation __L449;

    public static PerturbationLocation __L450;

    public static PerturbationLocation __L451;

    public static PerturbationLocation __L452;

    public static PerturbationLocation __L453;

    public static PerturbationLocation __L454;

    public static PerturbationLocation __L455;

    public static PerturbationLocation __L456;

    public static PerturbationLocation __L457;

    public static PerturbationLocation __L458;

    public static PerturbationLocation __L459;

    public static PerturbationLocation __L460;

    public static PerturbationLocation __L461;

    public static PerturbationLocation __L462;

    public static PerturbationLocation __L463;

    public static PerturbationLocation __L464;

    public static PerturbationLocation __L465;

    public static PerturbationLocation __L466;

    public static PerturbationLocation __L467;

    public static PerturbationLocation __L468;

    public static PerturbationLocation __L469;

    public static PerturbationLocation __L470;

    public static PerturbationLocation __L471;

    public static PerturbationLocation __L472;

    public static PerturbationLocation __L473;

    public static PerturbationLocation __L474;

    public static PerturbationLocation __L475;

    public static PerturbationLocation __L476;

    public static PerturbationLocation __L477;

    public static PerturbationLocation __L478;

    public static PerturbationLocation __L479;

    public static PerturbationLocation __L480;

    public static PerturbationLocation __L481;

    public static PerturbationLocation __L482;

    public static PerturbationLocation __L483;

    public static PerturbationLocation __L484;

    public static PerturbationLocation __L485;

    public static PerturbationLocation __L486;

    public static PerturbationLocation __L487;

    public static PerturbationLocation __L488;

    public static PerturbationLocation __L489;

    public static PerturbationLocation __L490;

    public static PerturbationLocation __L491;

    public static PerturbationLocation __L492;

    public static PerturbationLocation __L493;

    public static PerturbationLocation __L494;

    public static PerturbationLocation __L495;

    public static PerturbationLocation __L496;

    public static PerturbationLocation __L497;

    public static PerturbationLocation __L498;

    public static PerturbationLocation __L499;

    public static PerturbationLocation __L500;

    public static PerturbationLocation __L501;

    public static PerturbationLocation __L502;

    public static PerturbationLocation __L503;

    public static PerturbationLocation __L504;

    public static PerturbationLocation __L505;

    public static PerturbationLocation __L506;

    public static PerturbationLocation __L507;

    public static PerturbationLocation __L508;

    public static PerturbationLocation __L509;

    public static PerturbationLocation __L510;

    public static PerturbationLocation __L511;

    public static PerturbationLocation __L512;

    public static PerturbationLocation __L513;

    public static PerturbationLocation __L514;

    public static PerturbationLocation __L515;

    public static PerturbationLocation __L516;

    public static PerturbationLocation __L517;

    public static PerturbationLocation __L518;

    public static PerturbationLocation __L519;

    public static PerturbationLocation __L520;

    public static PerturbationLocation __L521;

    public static PerturbationLocation __L522;

    public static PerturbationLocation __L523;

    public static PerturbationLocation __L524;

    public static PerturbationLocation __L525;

    public static PerturbationLocation __L526;

    public static PerturbationLocation __L527;

    public static PerturbationLocation __L528;

    private static final float GAUSSIAN_CUT_OFF = 0.005F;

    private static final float MAGNITUDE_SCALE = 100.0F;

    private static final float MAGNITUDE_LIMIT = 1000.0F;

    private static final int MAGNITUDE_MAX = ((int) ((CannyEdgeDetectorInstr.MAGNITUDE_SCALE) * (CannyEdgeDetectorInstr.MAGNITUDE_LIMIT)));

    private int height;

    private int width;

    private int picsize;

    private int[] data;

    private int[] magnitude;

    private BufferedImage sourceImage;

    private BufferedImage edgesImage;

    private float gaussianKernelRadius;

    private float lowThreshold;

    private float highThreshold;

    private int gaussianKernelWidth;

    private boolean contrastNormalized;

    private float[] xConv;

    private float[] yConv;

    private float[] xGradient;

    private float[] yGradient;

    public CannyEdgeDetectorInstr() {
        lowThreshold = 2.5F;
        highThreshold = 7.5F;
        gaussianKernelRadius = 2.0F;
        gaussianKernelWidth = 16;
        contrastNormalized = false;
    }

    public BufferedImage getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(BufferedImage image) {
        sourceImage = image;
    }

    public BufferedImage getEdgesImage() {
        return edgesImage;
    }

    public void setEdgesImage(BufferedImage edgesImage) {
        CannyEdgeDetectorInstr.this.edgesImage = edgesImage;
    }

    public float getLowThreshold() {
        return lowThreshold;
    }

    public void setLowThreshold(float threshold) {
        if (PerturbationEngine.pboolean(__L504, (threshold < 0)))
            throw new IllegalArgumentException();
        
        lowThreshold = threshold;
    }

    public float getHighThreshold() {
        return highThreshold;
    }

    public void setHighThreshold(float threshold) {
        if (PerturbationEngine.pboolean(__L503, (threshold < 0)))
            throw new IllegalArgumentException();
        
        highThreshold = threshold;
    }

    public int getGaussianKernelWidth() {
        return PerturbationEngine.pint(__L2, gaussianKernelWidth);
    }

    public void setGaussianKernelWidth(int gaussianKernelWidth) {
        if (PerturbationEngine.pboolean(__L501, ((PerturbationEngine.pint(__L499, gaussianKernelWidth)) < (PerturbationEngine.pint(__L500, 2)))))
            throw new IllegalArgumentException();
        
        CannyEdgeDetectorInstr.this.gaussianKernelWidth = PerturbationEngine.pint(__L502, gaussianKernelWidth);
    }

    public float getGaussianKernelRadius() {
        return gaussianKernelRadius;
    }

    public void setGaussianKernelRadius(float gaussianKernelRadius) {
        if (PerturbationEngine.pboolean(__L498, (gaussianKernelRadius < 0.1F)))
            throw new IllegalArgumentException();
        
        CannyEdgeDetectorInstr.this.gaussianKernelRadius = gaussianKernelRadius;
    }

    public boolean isContrastNormalized() {
        return PerturbationEngine.pboolean(__L0, contrastNormalized);
    }

    public void setContrastNormalized(boolean contrastNormalized) {
        CannyEdgeDetectorInstr.this.contrastNormalized = PerturbationEngine.pboolean(__L497, contrastNormalized);
    }

    public void process() {
        width = PerturbationEngine.pint(__L383, sourceImage.getWidth());
        height = PerturbationEngine.pint(__L384, sourceImage.getHeight());
        picsize = PerturbationEngine.pint(__L387, ((PerturbationEngine.pint(__L385, width)) * (PerturbationEngine.pint(__L386, height))));
        initArrays();
        readLuminance();
        if (PerturbationEngine.pboolean(__L388, contrastNormalized))
            normalizeContrast();
        
        computeGradients(gaussianKernelRadius, PerturbationEngine.pint(__L389, gaussianKernelWidth));
        int low = PerturbationEngine.pint(__L390, Math.round(((lowThreshold) * (CannyEdgeDetectorInstr.MAGNITUDE_SCALE))));
        int high = PerturbationEngine.pint(__L391, Math.round(((highThreshold) * (CannyEdgeDetectorInstr.MAGNITUDE_SCALE))));
        performHysteresis(PerturbationEngine.pint(__L392, low), PerturbationEngine.pint(__L393, high));
        thresholdEdges();
        writeEdges(data);
    }

    private void initArrays() {
        if (PerturbationEngine.pboolean(__L307, ((PerturbationEngine.pboolean(__L303, ((data) == null))) || (PerturbationEngine.pboolean(__L306, ((PerturbationEngine.pint(__L304, picsize)) != (PerturbationEngine.pint(__L305, data.length)))))))) {
            data = new int[PerturbationEngine.pint(__L308, picsize)];
            magnitude = new int[PerturbationEngine.pint(__L309, picsize)];
            xConv = new float[PerturbationEngine.pint(__L310, picsize)];
            yConv = new float[PerturbationEngine.pint(__L311, picsize)];
            xGradient = new float[PerturbationEngine.pint(__L312, picsize)];
            yGradient = new float[PerturbationEngine.pint(__L313, picsize)];
        } 
    }

    private void computeGradients(float kernelRadius, int kernelWidth) {
        float[] kernel = new float[PerturbationEngine.pint(__L4, kernelWidth)];
        float[] diffKernel = new float[PerturbationEngine.pint(__L5, kernelWidth)];
        int kwidth;
        for (kwidth = PerturbationEngine.pint(__L6, 0); PerturbationEngine.pboolean(__L9, ((PerturbationEngine.pint(__L7, kwidth)) < (PerturbationEngine.pint(__L8, kernelWidth)))); PerturbationEngine.pint(__L10, (kwidth++))) {
            float g1 = gaussian(PerturbationEngine.pint(__L11, kwidth), kernelRadius);
            if (PerturbationEngine.pboolean(__L16, ((PerturbationEngine.pboolean(__L12, (g1 <= (CannyEdgeDetectorInstr.GAUSSIAN_CUT_OFF)))) && (PerturbationEngine.pboolean(__L15, ((PerturbationEngine.pint(__L13, kwidth)) >= (PerturbationEngine.pint(__L14, 2))))))))
                break;
            
            float g2 = gaussian((kwidth - 0.5F), kernelRadius);
            float g3 = gaussian((kwidth + 0.5F), kernelRadius);
            kernel[PerturbationEngine.pint(__L17, kwidth)] = (((g1 + g2) + g3) / 3.0F) / (((2.0F * ((float) (Math.PI))) * kernelRadius) * kernelRadius);
            diffKernel[PerturbationEngine.pint(__L18, kwidth)] = g3 - g2;
        }
        int initX = PerturbationEngine.pint(__L21, ((PerturbationEngine.pint(__L19, kwidth)) - (PerturbationEngine.pint(__L20, 1))));
        int maxX = PerturbationEngine.pint(__L26, ((PerturbationEngine.pint(__L22, width)) - (PerturbationEngine.pint(__L25, ((PerturbationEngine.pint(__L23, kwidth)) - (PerturbationEngine.pint(__L24, 1)))))));
        int initY = PerturbationEngine.pint(__L31, ((PerturbationEngine.pint(__L27, width)) * (PerturbationEngine.pint(__L30, ((PerturbationEngine.pint(__L28, kwidth)) - (PerturbationEngine.pint(__L29, 1)))))));
        int maxY = PerturbationEngine.pint(__L38, ((PerturbationEngine.pint(__L32, width)) * (PerturbationEngine.pint(__L37, ((PerturbationEngine.pint(__L33, height)) - (PerturbationEngine.pint(__L36, ((PerturbationEngine.pint(__L34, kwidth)) - (PerturbationEngine.pint(__L35, 1))))))))));
        for (int x = PerturbationEngine.pint(__L39, initX); PerturbationEngine.pboolean(__L42, ((PerturbationEngine.pint(__L40, x)) < (PerturbationEngine.pint(__L41, maxX)))); PerturbationEngine.pint(__L43, (x++))) {
            for (int y = PerturbationEngine.pint(__L44, initY); PerturbationEngine.pboolean(__L47, ((PerturbationEngine.pint(__L45, y)) < (PerturbationEngine.pint(__L46, maxY)))); y += PerturbationEngine.pint(__L48, width)) {
                int index = PerturbationEngine.pint(__L51, ((PerturbationEngine.pint(__L49, x)) + (PerturbationEngine.pint(__L50, y))));
                float sumX = (data[PerturbationEngine.pint(__L52, index)]) * (kernel[PerturbationEngine.pint(__L53, 0)]);
                float sumY = sumX;
                int xOffset = PerturbationEngine.pint(__L54, 1);
                int yOffset = PerturbationEngine.pint(__L55, width);
                for (; PerturbationEngine.pboolean(__L58, ((PerturbationEngine.pint(__L56, xOffset)) < (PerturbationEngine.pint(__L57, kwidth))));) {
                    sumY += (kernel[PerturbationEngine.pint(__L59, xOffset)]) * ((PerturbationEngine.pint(__L63, data[PerturbationEngine.pint(__L62, ((PerturbationEngine.pint(__L60, index)) - (PerturbationEngine.pint(__L61, yOffset))))])) + (PerturbationEngine.pint(__L67, data[PerturbationEngine.pint(__L66, ((PerturbationEngine.pint(__L64, index)) + (PerturbationEngine.pint(__L65, yOffset))))])));
                    sumX += (kernel[PerturbationEngine.pint(__L68, xOffset)]) * ((PerturbationEngine.pint(__L72, data[PerturbationEngine.pint(__L71, ((PerturbationEngine.pint(__L69, index)) - (PerturbationEngine.pint(__L70, xOffset))))])) + (PerturbationEngine.pint(__L76, data[PerturbationEngine.pint(__L75, ((PerturbationEngine.pint(__L73, index)) + (PerturbationEngine.pint(__L74, xOffset))))])));
                    yOffset += PerturbationEngine.pint(__L77, width);
                    PerturbationEngine.pint(__L78, (xOffset++));
                }
                yConv[PerturbationEngine.pint(__L79, index)] = sumY;
                xConv[PerturbationEngine.pint(__L80, index)] = sumX;
            }
        }
        for (int x = PerturbationEngine.pint(__L81, initX); PerturbationEngine.pboolean(__L84, ((PerturbationEngine.pint(__L82, x)) < (PerturbationEngine.pint(__L83, maxX)))); PerturbationEngine.pint(__L85, (x++))) {
            for (int y = PerturbationEngine.pint(__L86, initY); PerturbationEngine.pboolean(__L89, ((PerturbationEngine.pint(__L87, y)) < (PerturbationEngine.pint(__L88, maxY)))); y += PerturbationEngine.pint(__L90, width)) {
                float sum = 0.0F;
                int index = PerturbationEngine.pint(__L93, ((PerturbationEngine.pint(__L91, x)) + (PerturbationEngine.pint(__L92, y))));
                for (int i = PerturbationEngine.pint(__L94, 1); PerturbationEngine.pboolean(__L97, ((PerturbationEngine.pint(__L95, i)) < (PerturbationEngine.pint(__L96, kwidth)))); PerturbationEngine.pint(__L98, (i++)))
                    sum += (diffKernel[PerturbationEngine.pint(__L99, i)]) * ((yConv[PerturbationEngine.pint(__L102, ((PerturbationEngine.pint(__L100, index)) - (PerturbationEngine.pint(__L101, i))))]) - (yConv[PerturbationEngine.pint(__L105, ((PerturbationEngine.pint(__L103, index)) + (PerturbationEngine.pint(__L104, i))))]));
                xGradient[PerturbationEngine.pint(__L106, index)] = sum;
            }
        }
        for (int x = PerturbationEngine.pint(__L107, kwidth); PerturbationEngine.pboolean(__L112, ((PerturbationEngine.pint(__L108, x)) < (PerturbationEngine.pint(__L111, ((PerturbationEngine.pint(__L109, width)) - (PerturbationEngine.pint(__L110, kwidth))))))); PerturbationEngine.pint(__L113, (x++))) {
            for (int y = PerturbationEngine.pint(__L114, initY); PerturbationEngine.pboolean(__L117, ((PerturbationEngine.pint(__L115, y)) < (PerturbationEngine.pint(__L116, maxY)))); y += PerturbationEngine.pint(__L118, width)) {
                float sum = 0.0F;
                int index = PerturbationEngine.pint(__L121, ((PerturbationEngine.pint(__L119, x)) + (PerturbationEngine.pint(__L120, y))));
                int yOffset = PerturbationEngine.pint(__L122, width);
                for (int i = PerturbationEngine.pint(__L123, 1); PerturbationEngine.pboolean(__L126, ((PerturbationEngine.pint(__L124, i)) < (PerturbationEngine.pint(__L125, kwidth)))); PerturbationEngine.pint(__L127, (i++))) {
                    sum += (diffKernel[PerturbationEngine.pint(__L128, i)]) * ((xConv[PerturbationEngine.pint(__L131, ((PerturbationEngine.pint(__L129, index)) - (PerturbationEngine.pint(__L130, yOffset))))]) - (xConv[PerturbationEngine.pint(__L134, ((PerturbationEngine.pint(__L132, index)) + (PerturbationEngine.pint(__L133, yOffset))))]));
                    yOffset += PerturbationEngine.pint(__L135, width);
                }
                yGradient[PerturbationEngine.pint(__L136, index)] = sum;
            }
        }
        initX = PerturbationEngine.pint(__L137, kwidth);
        maxX = PerturbationEngine.pint(__L140, ((PerturbationEngine.pint(__L138, width)) - (PerturbationEngine.pint(__L139, kwidth))));
        initY = PerturbationEngine.pint(__L143, ((PerturbationEngine.pint(__L141, width)) * (PerturbationEngine.pint(__L142, kwidth))));
        maxY = PerturbationEngine.pint(__L148, ((PerturbationEngine.pint(__L144, width)) * (PerturbationEngine.pint(__L147, ((PerturbationEngine.pint(__L145, height)) - (PerturbationEngine.pint(__L146, kwidth)))))));
        for (int x = PerturbationEngine.pint(__L149, initX); PerturbationEngine.pboolean(__L152, ((PerturbationEngine.pint(__L150, x)) < (PerturbationEngine.pint(__L151, maxX)))); PerturbationEngine.pint(__L153, (x++))) {
            for (int y = PerturbationEngine.pint(__L154, initY); PerturbationEngine.pboolean(__L157, ((PerturbationEngine.pint(__L155, y)) < (PerturbationEngine.pint(__L156, maxY)))); y += PerturbationEngine.pint(__L158, width)) {
                int index = PerturbationEngine.pint(__L161, ((PerturbationEngine.pint(__L159, x)) + (PerturbationEngine.pint(__L160, y))));
                int indexN = PerturbationEngine.pint(__L164, ((PerturbationEngine.pint(__L162, index)) - (PerturbationEngine.pint(__L163, width))));
                int indexS = PerturbationEngine.pint(__L167, ((PerturbationEngine.pint(__L165, index)) + (PerturbationEngine.pint(__L166, width))));
                int indexW = PerturbationEngine.pint(__L170, ((PerturbationEngine.pint(__L168, index)) - (PerturbationEngine.pint(__L169, 1))));
                int indexE = PerturbationEngine.pint(__L173, ((PerturbationEngine.pint(__L171, index)) + (PerturbationEngine.pint(__L172, 1))));
                int indexNW = PerturbationEngine.pint(__L176, ((PerturbationEngine.pint(__L174, indexN)) - (PerturbationEngine.pint(__L175, 1))));
                int indexNE = PerturbationEngine.pint(__L179, ((PerturbationEngine.pint(__L177, indexN)) + (PerturbationEngine.pint(__L178, 1))));
                int indexSW = PerturbationEngine.pint(__L182, ((PerturbationEngine.pint(__L180, indexS)) - (PerturbationEngine.pint(__L181, 1))));
                int indexSE = PerturbationEngine.pint(__L185, ((PerturbationEngine.pint(__L183, indexS)) + (PerturbationEngine.pint(__L184, 1))));
                float xGrad = xGradient[PerturbationEngine.pint(__L186, index)];
                float yGrad = yGradient[PerturbationEngine.pint(__L187, index)];
                float gradMag = hypot(xGrad, yGrad);
                float nMag = hypot(xGradient[PerturbationEngine.pint(__L188, indexN)], yGradient[PerturbationEngine.pint(__L189, indexN)]);
                float sMag = hypot(xGradient[PerturbationEngine.pint(__L190, indexS)], yGradient[PerturbationEngine.pint(__L191, indexS)]);
                float wMag = hypot(xGradient[PerturbationEngine.pint(__L192, indexW)], yGradient[PerturbationEngine.pint(__L193, indexW)]);
                float eMag = hypot(xGradient[PerturbationEngine.pint(__L194, indexE)], yGradient[PerturbationEngine.pint(__L195, indexE)]);
                float neMag = hypot(xGradient[PerturbationEngine.pint(__L196, indexNE)], yGradient[PerturbationEngine.pint(__L197, indexNE)]);
                float seMag = hypot(xGradient[PerturbationEngine.pint(__L198, indexSE)], yGradient[PerturbationEngine.pint(__L199, indexSE)]);
                float swMag = hypot(xGradient[PerturbationEngine.pint(__L200, indexSW)], yGradient[PerturbationEngine.pint(__L201, indexSW)]);
                float nwMag = hypot(xGradient[PerturbationEngine.pint(__L202, indexNW)], yGradient[PerturbationEngine.pint(__L203, indexNW)]);
                float tmp;
                if (PerturbationEngine.pboolean(__L221, (PerturbationEngine.pboolean(__L204, ((xGrad * yGrad) <= ((float) (0)))) ? PerturbationEngine.pboolean(__L212, (PerturbationEngine.pboolean(__L205, ((Math.abs(xGrad)) >= (Math.abs(yGrad)))) ? PerturbationEngine.pboolean(__L208, ((PerturbationEngine.pboolean(__L206, ((tmp = Math.abs((xGrad * gradMag))) >= (Math.abs(((yGrad * neMag) - ((xGrad + yGrad) * eMag))))))) && (PerturbationEngine.pboolean(__L207, (tmp > (Math.abs(((yGrad * swMag) - ((xGrad + yGrad) * wMag))))))))) : PerturbationEngine.pboolean(__L211, ((PerturbationEngine.pboolean(__L209, ((tmp = Math.abs((yGrad * gradMag))) >= (Math.abs(((xGrad * neMag) - ((yGrad + xGrad) * nMag))))))) && (PerturbationEngine.pboolean(__L210, (tmp > (Math.abs(((xGrad * swMag) - ((yGrad + xGrad) * sMag))))))))))) : PerturbationEngine.pboolean(__L220, (PerturbationEngine.pboolean(__L213, ((Math.abs(xGrad)) >= (Math.abs(yGrad)))) ? PerturbationEngine.pboolean(__L216, ((PerturbationEngine.pboolean(__L214, ((tmp = Math.abs((xGrad * gradMag))) >= (Math.abs(((yGrad * seMag) + ((xGrad - yGrad) * eMag))))))) && (PerturbationEngine.pboolean(__L215, (tmp > (Math.abs(((yGrad * nwMag) + ((xGrad - yGrad) * wMag))))))))) : PerturbationEngine.pboolean(__L219, ((PerturbationEngine.pboolean(__L217, ((tmp = Math.abs((yGrad * gradMag))) >= (Math.abs(((xGrad * seMag) + ((yGrad - xGrad) * sMag))))))) && (PerturbationEngine.pboolean(__L218, (tmp > (Math.abs(((xGrad * nwMag) + ((yGrad - xGrad) * nMag)))))))))))))) {
                    magnitude[PerturbationEngine.pint(__L222, index)] = PerturbationEngine.pint(__L225, (PerturbationEngine.pboolean(__L223, (gradMag >= (CannyEdgeDetectorInstr.MAGNITUDE_LIMIT))) ? PerturbationEngine.pint(__L224, CannyEdgeDetectorInstr.MAGNITUDE_MAX) : ((int) ((CannyEdgeDetectorInstr.MAGNITUDE_SCALE) * gradMag))));
                } else {
                    magnitude[PerturbationEngine.pint(__L226, index)] = PerturbationEngine.pint(__L227, 0);
                }
            }
        }
    }

    private float hypot(float x, float y) {
        return ((float) (Math.hypot(x, y)));
    }

    private float gaussian(float x, float sigma) {
        return ((float) (Math.exp(((PerturbationEngine.pfloat(__L1, (-(x * x)))) / ((2.0F * sigma) * sigma)))));
    }

    private void performHysteresis(int low, int high) {
        Arrays.fill(data, PerturbationEngine.pint(__L357, 0));
        int offset = PerturbationEngine.pint(__L358, 0);
        for (int y = PerturbationEngine.pint(__L359, 0); PerturbationEngine.pboolean(__L362, ((PerturbationEngine.pint(__L360, y)) < (PerturbationEngine.pint(__L361, height)))); PerturbationEngine.pint(__L363, (y++))) {
            for (int x = PerturbationEngine.pint(__L364, 0); PerturbationEngine.pboolean(__L367, ((PerturbationEngine.pint(__L365, x)) < (PerturbationEngine.pint(__L366, width)))); PerturbationEngine.pint(__L368, (x++))) {
                if (PerturbationEngine.pboolean(__L377, ((PerturbationEngine.pboolean(__L372, ((PerturbationEngine.pint(__L370, data[PerturbationEngine.pint(__L369, offset)])) == (PerturbationEngine.pint(__L371, 0))))) && (PerturbationEngine.pboolean(__L376, ((PerturbationEngine.pint(__L374, magnitude[PerturbationEngine.pint(__L373, offset)])) >= (PerturbationEngine.pint(__L375, high)))))))) {
                    follow(PerturbationEngine.pint(__L378, x), PerturbationEngine.pint(__L379, y), PerturbationEngine.pint(__L380, offset), PerturbationEngine.pint(__L381, low));
                } 
                PerturbationEngine.pint(__L382, (offset++));
            }
        }
    }

    private void follow(int x1, int y1, int i1, int threshold) {
        int x0 = PerturbationEngine.pint(__L235, (PerturbationEngine.pboolean(__L230, ((PerturbationEngine.pint(__L228, x1)) == (PerturbationEngine.pint(__L229, 0)))) ? PerturbationEngine.pint(__L231, x1) : PerturbationEngine.pint(__L234, ((PerturbationEngine.pint(__L232, x1)) - (PerturbationEngine.pint(__L233, 1))))));
        int x2 = PerturbationEngine.pint(__L245, (PerturbationEngine.pboolean(__L240, ((PerturbationEngine.pint(__L236, x1)) == (PerturbationEngine.pint(__L239, ((PerturbationEngine.pint(__L237, width)) - (PerturbationEngine.pint(__L238, 1))))))) ? PerturbationEngine.pint(__L241, x1) : PerturbationEngine.pint(__L244, ((PerturbationEngine.pint(__L242, x1)) + (PerturbationEngine.pint(__L243, 1))))));
        int y0 = PerturbationEngine.pint(__L253, (PerturbationEngine.pboolean(__L248, ((PerturbationEngine.pint(__L246, y1)) == (PerturbationEngine.pint(__L247, 0)))) ? PerturbationEngine.pint(__L249, y1) : PerturbationEngine.pint(__L252, ((PerturbationEngine.pint(__L250, y1)) - (PerturbationEngine.pint(__L251, 1))))));
        int y2 = PerturbationEngine.pint(__L263, (PerturbationEngine.pboolean(__L258, ((PerturbationEngine.pint(__L254, y1)) == (PerturbationEngine.pint(__L257, ((PerturbationEngine.pint(__L255, height)) - (PerturbationEngine.pint(__L256, 1))))))) ? PerturbationEngine.pint(__L259, y1) : PerturbationEngine.pint(__L262, ((PerturbationEngine.pint(__L260, y1)) + (PerturbationEngine.pint(__L261, 1))))));
        data[PerturbationEngine.pint(__L264, i1)] = PerturbationEngine.pint(__L266, magnitude[PerturbationEngine.pint(__L265, i1)]);
        for (int x = PerturbationEngine.pint(__L267, x0); PerturbationEngine.pboolean(__L270, ((PerturbationEngine.pint(__L268, x)) <= (PerturbationEngine.pint(__L269, x2)))); PerturbationEngine.pint(__L271, (x++))) {
            for (int y = PerturbationEngine.pint(__L272, y0); PerturbationEngine.pboolean(__L275, ((PerturbationEngine.pint(__L273, y)) <= (PerturbationEngine.pint(__L274, y2)))); PerturbationEngine.pint(__L276, (y++))) {
                int i2 = PerturbationEngine.pint(__L281, ((PerturbationEngine.pint(__L277, x)) + (PerturbationEngine.pint(__L280, ((PerturbationEngine.pint(__L278, y)) * (PerturbationEngine.pint(__L279, width)))))));
                if (PerturbationEngine.pboolean(__L298, ((PerturbationEngine.pboolean(__L293, ((PerturbationEngine.pboolean(__L288, ((PerturbationEngine.pboolean(__L284, ((PerturbationEngine.pint(__L282, y)) != (PerturbationEngine.pint(__L283, y1))))) || (PerturbationEngine.pboolean(__L287, ((PerturbationEngine.pint(__L285, x)) != (PerturbationEngine.pint(__L286, x1)))))))) && (PerturbationEngine.pboolean(__L292, ((PerturbationEngine.pint(__L290, data[PerturbationEngine.pint(__L289, i2)])) == (PerturbationEngine.pint(__L291, 0)))))))) && (PerturbationEngine.pboolean(__L297, ((PerturbationEngine.pint(__L295, magnitude[PerturbationEngine.pint(__L294, i2)])) >= (PerturbationEngine.pint(__L296, threshold)))))))) {
                    follow(PerturbationEngine.pint(__L299, x), PerturbationEngine.pint(__L300, y), PerturbationEngine.pint(__L301, i2), PerturbationEngine.pint(__L302, threshold));
                    return ;
                } 
            }
        }
    }

    private void thresholdEdges() {
        for (int i = PerturbationEngine.pint(__L505, 0); PerturbationEngine.pboolean(__L508, ((PerturbationEngine.pint(__L506, i)) < (PerturbationEngine.pint(__L507, picsize)))); PerturbationEngine.pint(__L509, (i++))) {
            data[PerturbationEngine.pint(__L510, i)] = PerturbationEngine.pint(__L518, (PerturbationEngine.pboolean(__L514, ((PerturbationEngine.pint(__L512, data[PerturbationEngine.pint(__L511, i)])) > (PerturbationEngine.pint(__L513, 0)))) ? PerturbationEngine.pint(__L516, (-(PerturbationEngine.pint(__L515, 1)))) : PerturbationEngine.pint(__L517, -16777216)));
        }
    }

    private int luminance(float r, float g, float b) {
        return PerturbationEngine.pint(__L3, Math.round((((0.299F * r) + (0.587F * g)) + (0.114F * b))));
    }

    private void readLuminance() {
        int type = PerturbationEngine.pint(__L394, sourceImage.getType());
        if (PerturbationEngine.pboolean(__L401, ((PerturbationEngine.pboolean(__L397, ((PerturbationEngine.pint(__L395, type)) == (PerturbationEngine.pint(__L396, BufferedImage.TYPE_INT_RGB))))) || (PerturbationEngine.pboolean(__L400, ((PerturbationEngine.pint(__L398, type)) == (PerturbationEngine.pint(__L399, BufferedImage.TYPE_INT_ARGB)))))))) {
            int[] pixels = ((int[]) (sourceImage.getData().getDataElements(PerturbationEngine.pint(__L402, 0), PerturbationEngine.pint(__L403, 0), PerturbationEngine.pint(__L404, width), PerturbationEngine.pint(__L405, height), null)));
            for (int i = PerturbationEngine.pint(__L406, 0); PerturbationEngine.pboolean(__L409, ((PerturbationEngine.pint(__L407, i)) < (PerturbationEngine.pint(__L408, picsize)))); PerturbationEngine.pint(__L410, (i++))) {
                int p = PerturbationEngine.pint(__L412, pixels[PerturbationEngine.pint(__L411, i)]);
                int r = PerturbationEngine.pint(__L417, ((PerturbationEngine.pint(__L415, ((PerturbationEngine.pint(__L413, p)) & (PerturbationEngine.pint(__L414, 16711680))))) >> (PerturbationEngine.pint(__L416, 16))));
                int g = PerturbationEngine.pint(__L422, ((PerturbationEngine.pint(__L420, ((PerturbationEngine.pint(__L418, p)) & (PerturbationEngine.pint(__L419, 65280))))) >> (PerturbationEngine.pint(__L421, 8))));
                int b = PerturbationEngine.pint(__L425, ((PerturbationEngine.pint(__L423, p)) & (PerturbationEngine.pint(__L424, 255))));
                data[PerturbationEngine.pint(__L426, i)] = PerturbationEngine.pint(__L430, luminance(PerturbationEngine.pint(__L427, r), PerturbationEngine.pint(__L428, g), PerturbationEngine.pint(__L429, b)));
            }
        } else if (PerturbationEngine.pboolean(__L433, ((PerturbationEngine.pint(__L431, type)) == (PerturbationEngine.pint(__L432, BufferedImage.TYPE_BYTE_GRAY))))) {
            byte[] pixels = ((byte[]) (sourceImage.getData().getDataElements(PerturbationEngine.pint(__L434, 0), PerturbationEngine.pint(__L435, 0), PerturbationEngine.pint(__L436, width), PerturbationEngine.pint(__L437, height), null)));
            for (int i = PerturbationEngine.pint(__L438, 0); PerturbationEngine.pboolean(__L441, ((PerturbationEngine.pint(__L439, i)) < (PerturbationEngine.pint(__L440, picsize)))); PerturbationEngine.pint(__L442, (i++))) {
                data[PerturbationEngine.pint(__L443, i)] = PerturbationEngine.pint(__L447, ((PerturbationEngine.pbyte(__L445, pixels[PerturbationEngine.pint(__L444, i)])) & (PerturbationEngine.pint(__L446, 255))));
            }
        } else if (PerturbationEngine.pboolean(__L450, ((PerturbationEngine.pint(__L448, type)) == (PerturbationEngine.pint(__L449, BufferedImage.TYPE_USHORT_GRAY))))) {
            short[] pixels = ((short[]) (sourceImage.getData().getDataElements(PerturbationEngine.pint(__L451, 0), PerturbationEngine.pint(__L452, 0), PerturbationEngine.pint(__L453, width), PerturbationEngine.pint(__L454, height), null)));
            for (int i = PerturbationEngine.pint(__L455, 0); PerturbationEngine.pboolean(__L458, ((PerturbationEngine.pint(__L456, i)) < (PerturbationEngine.pint(__L457, picsize)))); PerturbationEngine.pint(__L459, (i++))) {
                data[PerturbationEngine.pint(__L460, i)] = PerturbationEngine.pint(__L466, ((PerturbationEngine.pint(__L464, ((PerturbationEngine.pshort(__L462, pixels[PerturbationEngine.pint(__L461, i)])) & (PerturbationEngine.pint(__L463, 65535))))) / (PerturbationEngine.pint(__L465, 256))));
            }
        } else if (PerturbationEngine.pboolean(__L469, ((PerturbationEngine.pint(__L467, type)) == (PerturbationEngine.pint(__L468, BufferedImage.TYPE_3BYTE_BGR))))) {
            byte[] pixels = ((byte[]) (sourceImage.getData().getDataElements(PerturbationEngine.pint(__L470, 0), PerturbationEngine.pint(__L471, 0), PerturbationEngine.pint(__L472, width), PerturbationEngine.pint(__L473, height), null)));
            int offset = PerturbationEngine.pint(__L474, 0);
            for (int i = PerturbationEngine.pint(__L475, 0); PerturbationEngine.pboolean(__L478, ((PerturbationEngine.pint(__L476, i)) < (PerturbationEngine.pint(__L477, picsize)))); PerturbationEngine.pint(__L479, (i++))) {
                int b = PerturbationEngine.pint(__L483, ((PerturbationEngine.pbyte(__L481, pixels[PerturbationEngine.pint(__L480, (offset++))])) & (PerturbationEngine.pint(__L482, 255))));
                int g = PerturbationEngine.pint(__L487, ((PerturbationEngine.pbyte(__L485, pixels[PerturbationEngine.pint(__L484, (offset++))])) & (PerturbationEngine.pint(__L486, 255))));
                int r = PerturbationEngine.pint(__L491, ((PerturbationEngine.pbyte(__L489, pixels[PerturbationEngine.pint(__L488, (offset++))])) & (PerturbationEngine.pint(__L490, 255))));
                data[PerturbationEngine.pint(__L492, i)] = PerturbationEngine.pint(__L496, luminance(PerturbationEngine.pint(__L493, r), PerturbationEngine.pint(__L494, g), PerturbationEngine.pint(__L495, b)));
            }
        } else {
            throw new IllegalArgumentException(("Unsupported image type: " + type));
        }
    }

    private void normalizeContrast() {
        int[] histogram = new int[PerturbationEngine.pint(__L314, 256)];
        for (int i = PerturbationEngine.pint(__L315, 0); PerturbationEngine.pboolean(__L318, ((PerturbationEngine.pint(__L316, i)) < (PerturbationEngine.pint(__L317, data.length)))); PerturbationEngine.pint(__L319, (i++))) {
            PerturbationEngine.pint(__L322, ((histogram[PerturbationEngine.pint(__L321, data[PerturbationEngine.pint(__L320, i)])])++));
        }
        int[] remap = new int[PerturbationEngine.pint(__L323, 256)];
        int sum = PerturbationEngine.pint(__L324, 0);
        int j = PerturbationEngine.pint(__L325, 0);
        for (int i = PerturbationEngine.pint(__L326, 0); PerturbationEngine.pboolean(__L329, ((PerturbationEngine.pint(__L327, i)) < (PerturbationEngine.pint(__L328, histogram.length)))); PerturbationEngine.pint(__L330, (i++))) {
            sum += PerturbationEngine.pint(__L332, histogram[PerturbationEngine.pint(__L331, i)]);
            int target = PerturbationEngine.pint(__L337, ((PerturbationEngine.pint(__L335, ((PerturbationEngine.pint(__L333, sum)) * (PerturbationEngine.pint(__L334, 255))))) / (PerturbationEngine.pint(__L336, picsize))));
            for (int k = PerturbationEngine.pint(__L340, ((PerturbationEngine.pint(__L338, j)) + (PerturbationEngine.pint(__L339, 1)))); PerturbationEngine.pboolean(__L343, ((PerturbationEngine.pint(__L341, k)) <= (PerturbationEngine.pint(__L342, target)))); PerturbationEngine.pint(__L344, (k++))) {
                remap[PerturbationEngine.pint(__L345, k)] = PerturbationEngine.pint(__L346, i);
            }
            j = PerturbationEngine.pint(__L347, target);
        }
        for (int i = PerturbationEngine.pint(__L348, 0); PerturbationEngine.pboolean(__L351, ((PerturbationEngine.pint(__L349, i)) < (PerturbationEngine.pint(__L350, data.length)))); PerturbationEngine.pint(__L352, (i++))) {
            data[PerturbationEngine.pint(__L353, i)] = PerturbationEngine.pint(__L356, remap[PerturbationEngine.pint(__L355, data[PerturbationEngine.pint(__L354, i)])]);
        }
    }

    private void writeEdges(int[] pixels) {
        if (PerturbationEngine.pboolean(__L519, ((edgesImage) == null))) {
            edgesImage = new BufferedImage(PerturbationEngine.pint(__L520, width), PerturbationEngine.pint(__L521, height), PerturbationEngine.pint(__L522, BufferedImage.TYPE_INT_ARGB));
        } 
        edgesImage.getWritableTile(PerturbationEngine.pint(__L523, 0), PerturbationEngine.pint(__L524, 0)).setDataElements(PerturbationEngine.pint(__L525, 0), PerturbationEngine.pint(__L526, 0), PerturbationEngine.pint(__L527, width), PerturbationEngine.pint(__L528, height), pixels);
    }

    private static void initPerturbationLocation0() {
        __L0 = new PerturbationLocationImpl("CannyEdgeDetector.java:238", 0, "Boolean");
        __L1 = new PerturbationLocationImpl("CannyEdgeDetector.java:450", 1, "Numerical");
        __L2 = new PerturbationLocationImpl("CannyEdgeDetector.java:189", 2, "Numerical");
        __L3 = new PerturbationLocationImpl("CannyEdgeDetector.java:498", 3, "Numerical");
        __L4 = new PerturbationLocationImpl("CannyEdgeDetector.java:296", 4, "Numerical");
        __L5 = new PerturbationLocationImpl("CannyEdgeDetector.java:297", 5, "Numerical");
        __L6 = new PerturbationLocationImpl("CannyEdgeDetector.java:299", 6, "Numerical");
        __L7 = new PerturbationLocationImpl("CannyEdgeDetector.java:299", 7, "Numerical");
        __L8 = new PerturbationLocationImpl("CannyEdgeDetector.java:299", 8, "Numerical");
        __L9 = new PerturbationLocationImpl("CannyEdgeDetector.java:299", 9, "Boolean");
        __L10 = new PerturbationLocationImpl("CannyEdgeDetector.java:299", 10, "Numerical");
        __L11 = new PerturbationLocationImpl("CannyEdgeDetector.java:300", 11, "Numerical");
        __L12 = new PerturbationLocationImpl("CannyEdgeDetector.java:301", 12, "Boolean");
        __L13 = new PerturbationLocationImpl("CannyEdgeDetector.java:301", 13, "Numerical");
        __L14 = new PerturbationLocationImpl("CannyEdgeDetector.java:301", 14, "Numerical");
        __L15 = new PerturbationLocationImpl("CannyEdgeDetector.java:301", 15, "Boolean");
        __L16 = new PerturbationLocationImpl("CannyEdgeDetector.java:301", 16, "Boolean");
        __L17 = new PerturbationLocationImpl("CannyEdgeDetector.java:304", 17, "Numerical");
        __L18 = new PerturbationLocationImpl("CannyEdgeDetector.java:305", 18, "Numerical");
        __L19 = new PerturbationLocationImpl("CannyEdgeDetector.java:308", 19, "Numerical");
        __L20 = new PerturbationLocationImpl("CannyEdgeDetector.java:308", 20, "Numerical");
        __L21 = new PerturbationLocationImpl("CannyEdgeDetector.java:308", 21, "Numerical");
        __L22 = new PerturbationLocationImpl("CannyEdgeDetector.java:309", 22, "Numerical");
        __L23 = new PerturbationLocationImpl("CannyEdgeDetector.java:309", 23, "Numerical");
        __L24 = new PerturbationLocationImpl("CannyEdgeDetector.java:309", 24, "Numerical");
        __L25 = new PerturbationLocationImpl("CannyEdgeDetector.java:309", 25, "Numerical");
        __L26 = new PerturbationLocationImpl("CannyEdgeDetector.java:309", 26, "Numerical");
        __L27 = new PerturbationLocationImpl("CannyEdgeDetector.java:310", 27, "Numerical");
        __L28 = new PerturbationLocationImpl("CannyEdgeDetector.java:310", 28, "Numerical");
        __L29 = new PerturbationLocationImpl("CannyEdgeDetector.java:310", 29, "Numerical");
        __L30 = new PerturbationLocationImpl("CannyEdgeDetector.java:310", 30, "Numerical");
        __L31 = new PerturbationLocationImpl("CannyEdgeDetector.java:310", 31, "Numerical");
        __L32 = new PerturbationLocationImpl("CannyEdgeDetector.java:311", 32, "Numerical");
        __L33 = new PerturbationLocationImpl("CannyEdgeDetector.java:311", 33, "Numerical");
        __L34 = new PerturbationLocationImpl("CannyEdgeDetector.java:311", 34, "Numerical");
        __L35 = new PerturbationLocationImpl("CannyEdgeDetector.java:311", 35, "Numerical");
        __L36 = new PerturbationLocationImpl("CannyEdgeDetector.java:311", 36, "Numerical");
        __L37 = new PerturbationLocationImpl("CannyEdgeDetector.java:311", 37, "Numerical");
        __L38 = new PerturbationLocationImpl("CannyEdgeDetector.java:311", 38, "Numerical");
        __L39 = new PerturbationLocationImpl("CannyEdgeDetector.java:314", 39, "Numerical");
        __L40 = new PerturbationLocationImpl("CannyEdgeDetector.java:314", 40, "Numerical");
        __L41 = new PerturbationLocationImpl("CannyEdgeDetector.java:314", 41, "Numerical");
        __L42 = new PerturbationLocationImpl("CannyEdgeDetector.java:314", 42, "Boolean");
        __L43 = new PerturbationLocationImpl("CannyEdgeDetector.java:314", 43, "Numerical");
        __L44 = new PerturbationLocationImpl("CannyEdgeDetector.java:315", 44, "Numerical");
        __L45 = new PerturbationLocationImpl("CannyEdgeDetector.java:315", 45, "Numerical");
        __L46 = new PerturbationLocationImpl("CannyEdgeDetector.java:315", 46, "Numerical");
        __L47 = new PerturbationLocationImpl("CannyEdgeDetector.java:315", 47, "Boolean");
        __L48 = new PerturbationLocationImpl("CannyEdgeDetector.java:315", 48, "Numerical");
        __L49 = new PerturbationLocationImpl("CannyEdgeDetector.java:316", 49, "Numerical");
        __L50 = new PerturbationLocationImpl("CannyEdgeDetector.java:316", 50, "Numerical");
        __L51 = new PerturbationLocationImpl("CannyEdgeDetector.java:316", 51, "Numerical");
        __L52 = new PerturbationLocationImpl("CannyEdgeDetector.java:317", 52, "Numerical");
        __L53 = new PerturbationLocationImpl("CannyEdgeDetector.java:317", 53, "Numerical");
        __L54 = new PerturbationLocationImpl("CannyEdgeDetector.java:319", 54, "Numerical");
        __L55 = new PerturbationLocationImpl("CannyEdgeDetector.java:320", 55, "Numerical");
        __L56 = new PerturbationLocationImpl("CannyEdgeDetector.java:321", 56, "Numerical");
        __L57 = new PerturbationLocationImpl("CannyEdgeDetector.java:321", 57, "Numerical");
        __L58 = new PerturbationLocationImpl("CannyEdgeDetector.java:321", 58, "Boolean");
        __L59 = new PerturbationLocationImpl("CannyEdgeDetector.java:322", 59, "Numerical");
        __L60 = new PerturbationLocationImpl("CannyEdgeDetector.java:322", 60, "Numerical");
        __L61 = new PerturbationLocationImpl("CannyEdgeDetector.java:322", 61, "Numerical");
        __L62 = new PerturbationLocationImpl("CannyEdgeDetector.java:322", 62, "Numerical");
        __L63 = new PerturbationLocationImpl("CannyEdgeDetector.java:322", 63, "Numerical");
        __L64 = new PerturbationLocationImpl("CannyEdgeDetector.java:322", 64, "Numerical");
        __L65 = new PerturbationLocationImpl("CannyEdgeDetector.java:322", 65, "Numerical");
        __L66 = new PerturbationLocationImpl("CannyEdgeDetector.java:322", 66, "Numerical");
        __L67 = new PerturbationLocationImpl("CannyEdgeDetector.java:322", 67, "Numerical");
        __L68 = new PerturbationLocationImpl("CannyEdgeDetector.java:323", 68, "Numerical");
        __L69 = new PerturbationLocationImpl("CannyEdgeDetector.java:323", 69, "Numerical");
        __L70 = new PerturbationLocationImpl("CannyEdgeDetector.java:323", 70, "Numerical");
        __L71 = new PerturbationLocationImpl("CannyEdgeDetector.java:323", 71, "Numerical");
        __L72 = new PerturbationLocationImpl("CannyEdgeDetector.java:323", 72, "Numerical");
        __L73 = new PerturbationLocationImpl("CannyEdgeDetector.java:323", 73, "Numerical");
        __L74 = new PerturbationLocationImpl("CannyEdgeDetector.java:323", 74, "Numerical");
        __L75 = new PerturbationLocationImpl("CannyEdgeDetector.java:323", 75, "Numerical");
        __L76 = new PerturbationLocationImpl("CannyEdgeDetector.java:323", 76, "Numerical");
        __L77 = new PerturbationLocationImpl("CannyEdgeDetector.java:324", 77, "Numerical");
        __L78 = new PerturbationLocationImpl("CannyEdgeDetector.java:325", 78, "Numerical");
        __L79 = new PerturbationLocationImpl("CannyEdgeDetector.java:328", 79, "Numerical");
        __L80 = new PerturbationLocationImpl("CannyEdgeDetector.java:329", 80, "Numerical");
        __L81 = new PerturbationLocationImpl("CannyEdgeDetector.java:334", 81, "Numerical");
        __L82 = new PerturbationLocationImpl("CannyEdgeDetector.java:334", 82, "Numerical");
        __L83 = new PerturbationLocationImpl("CannyEdgeDetector.java:334", 83, "Numerical");
        __L84 = new PerturbationLocationImpl("CannyEdgeDetector.java:334", 84, "Boolean");
        __L85 = new PerturbationLocationImpl("CannyEdgeDetector.java:334", 85, "Numerical");
        __L86 = new PerturbationLocationImpl("CannyEdgeDetector.java:335", 86, "Numerical");
        __L87 = new PerturbationLocationImpl("CannyEdgeDetector.java:335", 87, "Numerical");
        __L88 = new PerturbationLocationImpl("CannyEdgeDetector.java:335", 88, "Numerical");
        __L89 = new PerturbationLocationImpl("CannyEdgeDetector.java:335", 89, "Boolean");
        __L90 = new PerturbationLocationImpl("CannyEdgeDetector.java:335", 90, "Numerical");
        __L91 = new PerturbationLocationImpl("CannyEdgeDetector.java:337", 91, "Numerical");
        __L92 = new PerturbationLocationImpl("CannyEdgeDetector.java:337", 92, "Numerical");
        __L93 = new PerturbationLocationImpl("CannyEdgeDetector.java:337", 93, "Numerical");
        __L94 = new PerturbationLocationImpl("CannyEdgeDetector.java:338", 94, "Numerical");
        __L95 = new PerturbationLocationImpl("CannyEdgeDetector.java:338", 95, "Numerical");
        __L96 = new PerturbationLocationImpl("CannyEdgeDetector.java:338", 96, "Numerical");
        __L97 = new PerturbationLocationImpl("CannyEdgeDetector.java:338", 97, "Boolean");
        __L98 = new PerturbationLocationImpl("CannyEdgeDetector.java:338", 98, "Numerical");
        __L99 = new PerturbationLocationImpl("CannyEdgeDetector.java:339", 99, "Numerical");
        __L100 = new PerturbationLocationImpl("CannyEdgeDetector.java:339", 100, "Numerical");
        __L101 = new PerturbationLocationImpl("CannyEdgeDetector.java:339", 101, "Numerical");
        __L102 = new PerturbationLocationImpl("CannyEdgeDetector.java:339", 102, "Numerical");
        __L103 = new PerturbationLocationImpl("CannyEdgeDetector.java:339", 103, "Numerical");
        __L104 = new PerturbationLocationImpl("CannyEdgeDetector.java:339", 104, "Numerical");
        __L105 = new PerturbationLocationImpl("CannyEdgeDetector.java:339", 105, "Numerical");
        __L106 = new PerturbationLocationImpl("CannyEdgeDetector.java:341", 106, "Numerical");
        __L107 = new PerturbationLocationImpl("CannyEdgeDetector.java:346", 107, "Numerical");
        __L108 = new PerturbationLocationImpl("CannyEdgeDetector.java:346", 108, "Numerical");
        __L109 = new PerturbationLocationImpl("CannyEdgeDetector.java:346", 109, "Numerical");
        __L110 = new PerturbationLocationImpl("CannyEdgeDetector.java:346", 110, "Numerical");
        __L111 = new PerturbationLocationImpl("CannyEdgeDetector.java:346", 111, "Numerical");
        __L112 = new PerturbationLocationImpl("CannyEdgeDetector.java:346", 112, "Boolean");
        __L113 = new PerturbationLocationImpl("CannyEdgeDetector.java:346", 113, "Numerical");
        __L114 = new PerturbationLocationImpl("CannyEdgeDetector.java:347", 114, "Numerical");
        __L115 = new PerturbationLocationImpl("CannyEdgeDetector.java:347", 115, "Numerical");
        __L116 = new PerturbationLocationImpl("CannyEdgeDetector.java:347", 116, "Numerical");
        __L117 = new PerturbationLocationImpl("CannyEdgeDetector.java:347", 117, "Boolean");
        __L118 = new PerturbationLocationImpl("CannyEdgeDetector.java:347", 118, "Numerical");
        __L119 = new PerturbationLocationImpl("CannyEdgeDetector.java:349", 119, "Numerical");
        __L120 = new PerturbationLocationImpl("CannyEdgeDetector.java:349", 120, "Numerical");
        __L121 = new PerturbationLocationImpl("CannyEdgeDetector.java:349", 121, "Numerical");
        __L122 = new PerturbationLocationImpl("CannyEdgeDetector.java:350", 122, "Numerical");
        __L123 = new PerturbationLocationImpl("CannyEdgeDetector.java:351", 123, "Numerical");
        __L124 = new PerturbationLocationImpl("CannyEdgeDetector.java:351", 124, "Numerical");
        __L125 = new PerturbationLocationImpl("CannyEdgeDetector.java:351", 125, "Numerical");
        __L126 = new PerturbationLocationImpl("CannyEdgeDetector.java:351", 126, "Boolean");
        __L127 = new PerturbationLocationImpl("CannyEdgeDetector.java:351", 127, "Numerical");
        __L128 = new PerturbationLocationImpl("CannyEdgeDetector.java:352", 128, "Numerical");
        __L129 = new PerturbationLocationImpl("CannyEdgeDetector.java:352", 129, "Numerical");
        __L130 = new PerturbationLocationImpl("CannyEdgeDetector.java:352", 130, "Numerical");
        __L131 = new PerturbationLocationImpl("CannyEdgeDetector.java:352", 131, "Numerical");
        __L132 = new PerturbationLocationImpl("CannyEdgeDetector.java:352", 132, "Numerical");
        __L133 = new PerturbationLocationImpl("CannyEdgeDetector.java:352", 133, "Numerical");
        __L134 = new PerturbationLocationImpl("CannyEdgeDetector.java:352", 134, "Numerical");
        __L135 = new PerturbationLocationImpl("CannyEdgeDetector.java:353", 135, "Numerical");
        __L136 = new PerturbationLocationImpl("CannyEdgeDetector.java:356", 136, "Numerical");
        __L137 = new PerturbationLocationImpl("CannyEdgeDetector.java:361", 137, "Numerical");
        __L138 = new PerturbationLocationImpl("CannyEdgeDetector.java:362", 138, "Numerical");
        __L139 = new PerturbationLocationImpl("CannyEdgeDetector.java:362", 139, "Numerical");
        __L140 = new PerturbationLocationImpl("CannyEdgeDetector.java:362", 140, "Numerical");
        __L141 = new PerturbationLocationImpl("CannyEdgeDetector.java:363", 141, "Numerical");
        __L142 = new PerturbationLocationImpl("CannyEdgeDetector.java:363", 142, "Numerical");
        __L143 = new PerturbationLocationImpl("CannyEdgeDetector.java:363", 143, "Numerical");
        __L144 = new PerturbationLocationImpl("CannyEdgeDetector.java:364", 144, "Numerical");
        __L145 = new PerturbationLocationImpl("CannyEdgeDetector.java:364", 145, "Numerical");
        __L146 = new PerturbationLocationImpl("CannyEdgeDetector.java:364", 146, "Numerical");
        __L147 = new PerturbationLocationImpl("CannyEdgeDetector.java:364", 147, "Numerical");
        __L148 = new PerturbationLocationImpl("CannyEdgeDetector.java:364", 148, "Numerical");
        __L149 = new PerturbationLocationImpl("CannyEdgeDetector.java:365", 149, "Numerical");
        __L150 = new PerturbationLocationImpl("CannyEdgeDetector.java:365", 150, "Numerical");
        __L151 = new PerturbationLocationImpl("CannyEdgeDetector.java:365", 151, "Numerical");
        __L152 = new PerturbationLocationImpl("CannyEdgeDetector.java:365", 152, "Boolean");
        __L153 = new PerturbationLocationImpl("CannyEdgeDetector.java:365", 153, "Numerical");
        __L154 = new PerturbationLocationImpl("CannyEdgeDetector.java:366", 154, "Numerical");
        __L155 = new PerturbationLocationImpl("CannyEdgeDetector.java:366", 155, "Numerical");
        __L156 = new PerturbationLocationImpl("CannyEdgeDetector.java:366", 156, "Numerical");
        __L157 = new PerturbationLocationImpl("CannyEdgeDetector.java:366", 157, "Boolean");
        __L158 = new PerturbationLocationImpl("CannyEdgeDetector.java:366", 158, "Numerical");
        __L159 = new PerturbationLocationImpl("CannyEdgeDetector.java:367", 159, "Numerical");
        __L160 = new PerturbationLocationImpl("CannyEdgeDetector.java:367", 160, "Numerical");
        __L161 = new PerturbationLocationImpl("CannyEdgeDetector.java:367", 161, "Numerical");
        __L162 = new PerturbationLocationImpl("CannyEdgeDetector.java:368", 162, "Numerical");
        __L163 = new PerturbationLocationImpl("CannyEdgeDetector.java:368", 163, "Numerical");
        __L164 = new PerturbationLocationImpl("CannyEdgeDetector.java:368", 164, "Numerical");
        __L165 = new PerturbationLocationImpl("CannyEdgeDetector.java:369", 165, "Numerical");
        __L166 = new PerturbationLocationImpl("CannyEdgeDetector.java:369", 166, "Numerical");
        __L167 = new PerturbationLocationImpl("CannyEdgeDetector.java:369", 167, "Numerical");
        __L168 = new PerturbationLocationImpl("CannyEdgeDetector.java:370", 168, "Numerical");
        __L169 = new PerturbationLocationImpl("CannyEdgeDetector.java:370", 169, "Numerical");
        __L170 = new PerturbationLocationImpl("CannyEdgeDetector.java:370", 170, "Numerical");
        __L171 = new PerturbationLocationImpl("CannyEdgeDetector.java:371", 171, "Numerical");
        __L172 = new PerturbationLocationImpl("CannyEdgeDetector.java:371", 172, "Numerical");
        __L173 = new PerturbationLocationImpl("CannyEdgeDetector.java:371", 173, "Numerical");
        __L174 = new PerturbationLocationImpl("CannyEdgeDetector.java:372", 174, "Numerical");
        __L175 = new PerturbationLocationImpl("CannyEdgeDetector.java:372", 175, "Numerical");
        __L176 = new PerturbationLocationImpl("CannyEdgeDetector.java:372", 176, "Numerical");
        __L177 = new PerturbationLocationImpl("CannyEdgeDetector.java:373", 177, "Numerical");
        __L178 = new PerturbationLocationImpl("CannyEdgeDetector.java:373", 178, "Numerical");
        __L179 = new PerturbationLocationImpl("CannyEdgeDetector.java:373", 179, "Numerical");
        __L180 = new PerturbationLocationImpl("CannyEdgeDetector.java:374", 180, "Numerical");
        __L181 = new PerturbationLocationImpl("CannyEdgeDetector.java:374", 181, "Numerical");
        __L182 = new PerturbationLocationImpl("CannyEdgeDetector.java:374", 182, "Numerical");
        __L183 = new PerturbationLocationImpl("CannyEdgeDetector.java:375", 183, "Numerical");
        __L184 = new PerturbationLocationImpl("CannyEdgeDetector.java:375", 184, "Numerical");
        __L185 = new PerturbationLocationImpl("CannyEdgeDetector.java:375", 185, "Numerical");
        __L186 = new PerturbationLocationImpl("CannyEdgeDetector.java:377", 186, "Numerical");
        __L187 = new PerturbationLocationImpl("CannyEdgeDetector.java:378", 187, "Numerical");
        __L188 = new PerturbationLocationImpl("CannyEdgeDetector.java:382", 188, "Numerical");
        __L189 = new PerturbationLocationImpl("CannyEdgeDetector.java:382", 189, "Numerical");
        __L190 = new PerturbationLocationImpl("CannyEdgeDetector.java:383", 190, "Numerical");
        __L191 = new PerturbationLocationImpl("CannyEdgeDetector.java:383", 191, "Numerical");
        __L192 = new PerturbationLocationImpl("CannyEdgeDetector.java:384", 192, "Numerical");
        __L193 = new PerturbationLocationImpl("CannyEdgeDetector.java:384", 193, "Numerical");
        __L194 = new PerturbationLocationImpl("CannyEdgeDetector.java:385", 194, "Numerical");
        __L195 = new PerturbationLocationImpl("CannyEdgeDetector.java:385", 195, "Numerical");
        __L196 = new PerturbationLocationImpl("CannyEdgeDetector.java:386", 196, "Numerical");
        __L197 = new PerturbationLocationImpl("CannyEdgeDetector.java:386", 197, "Numerical");
        __L198 = new PerturbationLocationImpl("CannyEdgeDetector.java:387", 198, "Numerical");
        __L199 = new PerturbationLocationImpl("CannyEdgeDetector.java:387", 199, "Numerical");
        __L200 = new PerturbationLocationImpl("CannyEdgeDetector.java:388", 200, "Numerical");
        __L201 = new PerturbationLocationImpl("CannyEdgeDetector.java:388", 201, "Numerical");
        __L202 = new PerturbationLocationImpl("CannyEdgeDetector.java:389", 202, "Numerical");
        __L203 = new PerturbationLocationImpl("CannyEdgeDetector.java:389", 203, "Numerical");
        __L204 = new PerturbationLocationImpl("CannyEdgeDetector.java:419", 204, "Boolean");
        __L205 = new PerturbationLocationImpl("CannyEdgeDetector.java:420", 205, "Boolean");
        __L206 = new PerturbationLocationImpl("CannyEdgeDetector.java:421", 206, "Boolean");
        __L207 = new PerturbationLocationImpl("CannyEdgeDetector.java:422", 207, "Boolean");
        __L208 = new PerturbationLocationImpl("CannyEdgeDetector.java:421", 208, "Boolean");
        __L209 = new PerturbationLocationImpl("CannyEdgeDetector.java:423", 209, "Boolean");
        __L210 = new PerturbationLocationImpl("CannyEdgeDetector.java:424", 210, "Boolean");
        __L211 = new PerturbationLocationImpl("CannyEdgeDetector.java:423", 211, "Boolean");
        __L212 = new PerturbationLocationImpl("CannyEdgeDetector.java:420", 212, "Boolean");
        __L213 = new PerturbationLocationImpl("CannyEdgeDetector.java:425", 213, "Boolean");
        __L214 = new PerturbationLocationImpl("CannyEdgeDetector.java:426", 214, "Boolean");
        __L215 = new PerturbationLocationImpl("CannyEdgeDetector.java:427", 215, "Boolean");
        __L216 = new PerturbationLocationImpl("CannyEdgeDetector.java:426", 216, "Boolean");
        __L217 = new PerturbationLocationImpl("CannyEdgeDetector.java:428", 217, "Boolean");
        __L218 = new PerturbationLocationImpl("CannyEdgeDetector.java:429", 218, "Boolean");
        __L219 = new PerturbationLocationImpl("CannyEdgeDetector.java:428", 219, "Boolean");
        __L220 = new PerturbationLocationImpl("CannyEdgeDetector.java:425", 220, "Boolean");
        __L221 = new PerturbationLocationImpl("CannyEdgeDetector.java:419", 221, "Boolean");
        __L222 = new PerturbationLocationImpl("CannyEdgeDetector.java:431", 222, "Numerical");
        __L223 = new PerturbationLocationImpl("CannyEdgeDetector.java:431", 223, "Boolean");
        __L224 = new PerturbationLocationImpl("CannyEdgeDetector.java:431", 224, "Numerical");
        __L225 = new PerturbationLocationImpl("CannyEdgeDetector.java:431", 225, "Numerical");
        __L226 = new PerturbationLocationImpl("CannyEdgeDetector.java:436", 226, "Numerical");
        __L227 = new PerturbationLocationImpl("CannyEdgeDetector.java:436", 227, "Numerical");
        __L228 = new PerturbationLocationImpl("CannyEdgeDetector.java:472", 228, "Numerical");
        __L229 = new PerturbationLocationImpl("CannyEdgeDetector.java:472", 229, "Numerical");
        __L230 = new PerturbationLocationImpl("CannyEdgeDetector.java:472", 230, "Boolean");
        __L231 = new PerturbationLocationImpl("CannyEdgeDetector.java:472", 231, "Numerical");
        __L232 = new PerturbationLocationImpl("CannyEdgeDetector.java:472", 232, "Numerical");
        __L233 = new PerturbationLocationImpl("CannyEdgeDetector.java:472", 233, "Numerical");
        __L234 = new PerturbationLocationImpl("CannyEdgeDetector.java:472", 234, "Numerical");
        __L235 = new PerturbationLocationImpl("CannyEdgeDetector.java:472", 235, "Numerical");
        __L236 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 236, "Numerical");
        __L237 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 237, "Numerical");
        __L238 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 238, "Numerical");
        __L239 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 239, "Numerical");
        __L240 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 240, "Boolean");
        __L241 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 241, "Numerical");
        __L242 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 242, "Numerical");
        __L243 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 243, "Numerical");
        __L244 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 244, "Numerical");
        __L245 = new PerturbationLocationImpl("CannyEdgeDetector.java:473", 245, "Numerical");
        __L246 = new PerturbationLocationImpl("CannyEdgeDetector.java:474", 246, "Numerical");
        __L247 = new PerturbationLocationImpl("CannyEdgeDetector.java:474", 247, "Numerical");
        __L248 = new PerturbationLocationImpl("CannyEdgeDetector.java:474", 248, "Boolean");
        __L249 = new PerturbationLocationImpl("CannyEdgeDetector.java:474", 249, "Numerical");
        __L250 = new PerturbationLocationImpl("CannyEdgeDetector.java:474", 250, "Numerical");
        __L251 = new PerturbationLocationImpl("CannyEdgeDetector.java:474", 251, "Numerical");
        __L252 = new PerturbationLocationImpl("CannyEdgeDetector.java:474", 252, "Numerical");
        __L253 = new PerturbationLocationImpl("CannyEdgeDetector.java:474", 253, "Numerical");
        __L254 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 254, "Numerical");
        __L255 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 255, "Numerical");
        __L256 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 256, "Numerical");
        __L257 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 257, "Numerical");
        __L258 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 258, "Boolean");
        __L259 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 259, "Numerical");
        __L260 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 260, "Numerical");
        __L261 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 261, "Numerical");
        __L262 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 262, "Numerical");
        __L263 = new PerturbationLocationImpl("CannyEdgeDetector.java:475", 263, "Numerical");
        __L264 = new PerturbationLocationImpl("CannyEdgeDetector.java:477", 264, "Numerical");
        __L265 = new PerturbationLocationImpl("CannyEdgeDetector.java:477", 265, "Numerical");
        __L266 = new PerturbationLocationImpl("CannyEdgeDetector.java:477", 266, "Numerical");
        __L267 = new PerturbationLocationImpl("CannyEdgeDetector.java:478", 267, "Numerical");
        __L268 = new PerturbationLocationImpl("CannyEdgeDetector.java:478", 268, "Numerical");
        __L269 = new PerturbationLocationImpl("CannyEdgeDetector.java:478", 269, "Numerical");
        __L270 = new PerturbationLocationImpl("CannyEdgeDetector.java:478", 270, "Boolean");
        __L271 = new PerturbationLocationImpl("CannyEdgeDetector.java:478", 271, "Numerical");
        __L272 = new PerturbationLocationImpl("CannyEdgeDetector.java:479", 272, "Numerical");
        __L273 = new PerturbationLocationImpl("CannyEdgeDetector.java:479", 273, "Numerical");
        __L274 = new PerturbationLocationImpl("CannyEdgeDetector.java:479", 274, "Numerical");
        __L275 = new PerturbationLocationImpl("CannyEdgeDetector.java:479", 275, "Boolean");
        __L276 = new PerturbationLocationImpl("CannyEdgeDetector.java:479", 276, "Numerical");
        __L277 = new PerturbationLocationImpl("CannyEdgeDetector.java:480", 277, "Numerical");
        __L278 = new PerturbationLocationImpl("CannyEdgeDetector.java:480", 278, "Numerical");
        __L279 = new PerturbationLocationImpl("CannyEdgeDetector.java:480", 279, "Numerical");
        __L280 = new PerturbationLocationImpl("CannyEdgeDetector.java:480", 280, "Numerical");
        __L281 = new PerturbationLocationImpl("CannyEdgeDetector.java:480", 281, "Numerical");
        __L282 = new PerturbationLocationImpl("CannyEdgeDetector.java:481", 282, "Numerical");
        __L283 = new PerturbationLocationImpl("CannyEdgeDetector.java:481", 283, "Numerical");
        __L284 = new PerturbationLocationImpl("CannyEdgeDetector.java:481", 284, "Boolean");
        __L285 = new PerturbationLocationImpl("CannyEdgeDetector.java:481", 285, "Numerical");
        __L286 = new PerturbationLocationImpl("CannyEdgeDetector.java:481", 286, "Numerical");
        __L287 = new PerturbationLocationImpl("CannyEdgeDetector.java:481", 287, "Boolean");
        __L288 = new PerturbationLocationImpl("CannyEdgeDetector.java:481", 288, "Boolean");
        __L289 = new PerturbationLocationImpl("CannyEdgeDetector.java:482", 289, "Numerical");
        __L290 = new PerturbationLocationImpl("CannyEdgeDetector.java:482", 290, "Numerical");
        __L291 = new PerturbationLocationImpl("CannyEdgeDetector.java:482", 291, "Numerical");
        __L292 = new PerturbationLocationImpl("CannyEdgeDetector.java:482", 292, "Boolean");
        __L293 = new PerturbationLocationImpl("CannyEdgeDetector.java:481", 293, "Boolean");
        __L294 = new PerturbationLocationImpl("CannyEdgeDetector.java:483", 294, "Numerical");
        __L295 = new PerturbationLocationImpl("CannyEdgeDetector.java:483", 295, "Numerical");
        __L296 = new PerturbationLocationImpl("CannyEdgeDetector.java:483", 296, "Numerical");
        __L297 = new PerturbationLocationImpl("CannyEdgeDetector.java:483", 297, "Boolean");
        __L298 = new PerturbationLocationImpl("CannyEdgeDetector.java:481", 298, "Boolean");
        __L299 = new PerturbationLocationImpl("CannyEdgeDetector.java:484", 299, "Numerical");
        __L300 = new PerturbationLocationImpl("CannyEdgeDetector.java:484", 300, "Numerical");
        __L301 = new PerturbationLocationImpl("CannyEdgeDetector.java:484", 301, "Numerical");
        __L302 = new PerturbationLocationImpl("CannyEdgeDetector.java:484", 302, "Numerical");
        __L303 = new PerturbationLocationImpl("CannyEdgeDetector.java:271", 303, "Boolean");
        __L304 = new PerturbationLocationImpl("CannyEdgeDetector.java:271", 304, "Numerical");
        __L305 = new PerturbationLocationImpl("CannyEdgeDetector.java:271", 305, "Numerical");
        __L306 = new PerturbationLocationImpl("CannyEdgeDetector.java:271", 306, "Boolean");
        __L307 = new PerturbationLocationImpl("CannyEdgeDetector.java:271", 307, "Boolean");
        __L308 = new PerturbationLocationImpl("CannyEdgeDetector.java:272", 308, "Numerical");
        __L309 = new PerturbationLocationImpl("CannyEdgeDetector.java:273", 309, "Numerical");
        __L310 = new PerturbationLocationImpl("CannyEdgeDetector.java:275", 310, "Numerical");
        __L311 = new PerturbationLocationImpl("CannyEdgeDetector.java:276", 311, "Numerical");
        __L312 = new PerturbationLocationImpl("CannyEdgeDetector.java:277", 312, "Numerical");
        __L313 = new PerturbationLocationImpl("CannyEdgeDetector.java:278", 313, "Numerical");
        __L314 = new PerturbationLocationImpl("CannyEdgeDetector.java:537", 314, "Numerical");
        __L315 = new PerturbationLocationImpl("CannyEdgeDetector.java:538", 315, "Numerical");
        __L316 = new PerturbationLocationImpl("CannyEdgeDetector.java:538", 316, "Numerical");
        __L317 = new PerturbationLocationImpl("CannyEdgeDetector.java:538", 317, "Numerical");
        __L318 = new PerturbationLocationImpl("CannyEdgeDetector.java:538", 318, "Boolean");
        __L319 = new PerturbationLocationImpl("CannyEdgeDetector.java:538", 319, "Numerical");
        __L320 = new PerturbationLocationImpl("CannyEdgeDetector.java:539", 320, "Numerical");
        __L321 = new PerturbationLocationImpl("CannyEdgeDetector.java:539", 321, "Numerical");
        __L322 = new PerturbationLocationImpl("CannyEdgeDetector.java:539", 322, "Numerical");
        __L323 = new PerturbationLocationImpl("CannyEdgeDetector.java:541", 323, "Numerical");
        __L324 = new PerturbationLocationImpl("CannyEdgeDetector.java:542", 324, "Numerical");
        __L325 = new PerturbationLocationImpl("CannyEdgeDetector.java:543", 325, "Numerical");
        __L326 = new PerturbationLocationImpl("CannyEdgeDetector.java:544", 326, "Numerical");
        __L327 = new PerturbationLocationImpl("CannyEdgeDetector.java:544", 327, "Numerical");
        __L328 = new PerturbationLocationImpl("CannyEdgeDetector.java:544", 328, "Numerical");
        __L329 = new PerturbationLocationImpl("CannyEdgeDetector.java:544", 329, "Boolean");
        __L330 = new PerturbationLocationImpl("CannyEdgeDetector.java:544", 330, "Numerical");
        __L331 = new PerturbationLocationImpl("CannyEdgeDetector.java:545", 331, "Numerical");
        __L332 = new PerturbationLocationImpl("CannyEdgeDetector.java:545", 332, "Numerical");
        __L333 = new PerturbationLocationImpl("CannyEdgeDetector.java:546", 333, "Numerical");
        __L334 = new PerturbationLocationImpl("CannyEdgeDetector.java:546", 334, "Numerical");
        __L335 = new PerturbationLocationImpl("CannyEdgeDetector.java:546", 335, "Numerical");
        __L336 = new PerturbationLocationImpl("CannyEdgeDetector.java:546", 336, "Numerical");
        __L337 = new PerturbationLocationImpl("CannyEdgeDetector.java:546", 337, "Numerical");
        __L338 = new PerturbationLocationImpl("CannyEdgeDetector.java:547", 338, "Numerical");
        __L339 = new PerturbationLocationImpl("CannyEdgeDetector.java:547", 339, "Numerical");
        __L340 = new PerturbationLocationImpl("CannyEdgeDetector.java:547", 340, "Numerical");
        __L341 = new PerturbationLocationImpl("CannyEdgeDetector.java:547", 341, "Numerical");
        __L342 = new PerturbationLocationImpl("CannyEdgeDetector.java:547", 342, "Numerical");
        __L343 = new PerturbationLocationImpl("CannyEdgeDetector.java:547", 343, "Boolean");
        __L344 = new PerturbationLocationImpl("CannyEdgeDetector.java:547", 344, "Numerical");
        __L345 = new PerturbationLocationImpl("CannyEdgeDetector.java:548", 345, "Numerical");
        __L346 = new PerturbationLocationImpl("CannyEdgeDetector.java:548", 346, "Numerical");
        __L347 = new PerturbationLocationImpl("CannyEdgeDetector.java:550", 347, "Numerical");
        __L348 = new PerturbationLocationImpl("CannyEdgeDetector.java:553", 348, "Numerical");
        __L349 = new PerturbationLocationImpl("CannyEdgeDetector.java:553", 349, "Numerical");
        __L350 = new PerturbationLocationImpl("CannyEdgeDetector.java:553", 350, "Numerical");
        __L351 = new PerturbationLocationImpl("CannyEdgeDetector.java:553", 351, "Boolean");
        __L352 = new PerturbationLocationImpl("CannyEdgeDetector.java:553", 352, "Numerical");
        __L353 = new PerturbationLocationImpl("CannyEdgeDetector.java:554", 353, "Numerical");
        __L354 = new PerturbationLocationImpl("CannyEdgeDetector.java:554", 354, "Numerical");
        __L355 = new PerturbationLocationImpl("CannyEdgeDetector.java:554", 355, "Numerical");
        __L356 = new PerturbationLocationImpl("CannyEdgeDetector.java:554", 356, "Numerical");
        __L357 = new PerturbationLocationImpl("CannyEdgeDetector.java:458", 357, "Numerical");
        __L358 = new PerturbationLocationImpl("CannyEdgeDetector.java:460", 358, "Numerical");
        __L359 = new PerturbationLocationImpl("CannyEdgeDetector.java:461", 359, "Numerical");
        __L360 = new PerturbationLocationImpl("CannyEdgeDetector.java:461", 360, "Numerical");
        __L361 = new PerturbationLocationImpl("CannyEdgeDetector.java:461", 361, "Numerical");
        __L362 = new PerturbationLocationImpl("CannyEdgeDetector.java:461", 362, "Boolean");
        __L363 = new PerturbationLocationImpl("CannyEdgeDetector.java:461", 363, "Numerical");
        __L364 = new PerturbationLocationImpl("CannyEdgeDetector.java:462", 364, "Numerical");
        __L365 = new PerturbationLocationImpl("CannyEdgeDetector.java:462", 365, "Numerical");
        __L366 = new PerturbationLocationImpl("CannyEdgeDetector.java:462", 366, "Numerical");
        __L367 = new PerturbationLocationImpl("CannyEdgeDetector.java:462", 367, "Boolean");
        __L368 = new PerturbationLocationImpl("CannyEdgeDetector.java:462", 368, "Numerical");
        __L369 = new PerturbationLocationImpl("CannyEdgeDetector.java:463", 369, "Numerical");
        __L370 = new PerturbationLocationImpl("CannyEdgeDetector.java:463", 370, "Numerical");
        __L371 = new PerturbationLocationImpl("CannyEdgeDetector.java:463", 371, "Numerical");
        __L372 = new PerturbationLocationImpl("CannyEdgeDetector.java:463", 372, "Boolean");
        __L373 = new PerturbationLocationImpl("CannyEdgeDetector.java:463", 373, "Numerical");
        __L374 = new PerturbationLocationImpl("CannyEdgeDetector.java:463", 374, "Numerical");
        __L375 = new PerturbationLocationImpl("CannyEdgeDetector.java:463", 375, "Numerical");
        __L376 = new PerturbationLocationImpl("CannyEdgeDetector.java:463", 376, "Boolean");
        __L377 = new PerturbationLocationImpl("CannyEdgeDetector.java:463", 377, "Boolean");
        __L378 = new PerturbationLocationImpl("CannyEdgeDetector.java:464", 378, "Numerical");
        __L379 = new PerturbationLocationImpl("CannyEdgeDetector.java:464", 379, "Numerical");
        __L380 = new PerturbationLocationImpl("CannyEdgeDetector.java:464", 380, "Numerical");
        __L381 = new PerturbationLocationImpl("CannyEdgeDetector.java:464", 381, "Numerical");
        __L382 = new PerturbationLocationImpl("CannyEdgeDetector.java:466", 382, "Numerical");
        __L383 = new PerturbationLocationImpl("CannyEdgeDetector.java:254", 383, "Numerical");
        __L384 = new PerturbationLocationImpl("CannyEdgeDetector.java:255", 384, "Numerical");
        __L385 = new PerturbationLocationImpl("CannyEdgeDetector.java:256", 385, "Numerical");
        __L386 = new PerturbationLocationImpl("CannyEdgeDetector.java:256", 386, "Numerical");
        __L387 = new PerturbationLocationImpl("CannyEdgeDetector.java:256", 387, "Numerical");
        __L388 = new PerturbationLocationImpl("CannyEdgeDetector.java:259", 388, "Boolean");
        __L389 = new PerturbationLocationImpl("CannyEdgeDetector.java:260", 389, "Numerical");
        __L390 = new PerturbationLocationImpl("CannyEdgeDetector.java:261", 390, "Numerical");
        __L391 = new PerturbationLocationImpl("CannyEdgeDetector.java:262", 391, "Numerical");
        __L392 = new PerturbationLocationImpl("CannyEdgeDetector.java:263", 392, "Numerical");
        __L393 = new PerturbationLocationImpl("CannyEdgeDetector.java:263", 393, "Numerical");
        __L394 = new PerturbationLocationImpl("CannyEdgeDetector.java:502", 394, "Numerical");
        __L395 = new PerturbationLocationImpl("CannyEdgeDetector.java:503", 395, "Numerical");
        __L396 = new PerturbationLocationImpl("CannyEdgeDetector.java:503", 396, "Numerical");
        __L397 = new PerturbationLocationImpl("CannyEdgeDetector.java:503", 397, "Boolean");
        __L398 = new PerturbationLocationImpl("CannyEdgeDetector.java:503", 398, "Numerical");
        __L399 = new PerturbationLocationImpl("CannyEdgeDetector.java:503", 399, "Numerical");
        __L400 = new PerturbationLocationImpl("CannyEdgeDetector.java:503", 400, "Boolean");
        __L401 = new PerturbationLocationImpl("CannyEdgeDetector.java:503", 401, "Boolean");
        __L402 = new PerturbationLocationImpl("CannyEdgeDetector.java:504", 402, "Numerical");
        __L403 = new PerturbationLocationImpl("CannyEdgeDetector.java:504", 403, "Numerical");
        __L404 = new PerturbationLocationImpl("CannyEdgeDetector.java:504", 404, "Numerical");
        __L405 = new PerturbationLocationImpl("CannyEdgeDetector.java:504", 405, "Numerical");
        __L406 = new PerturbationLocationImpl("CannyEdgeDetector.java:505", 406, "Numerical");
        __L407 = new PerturbationLocationImpl("CannyEdgeDetector.java:505", 407, "Numerical");
        __L408 = new PerturbationLocationImpl("CannyEdgeDetector.java:505", 408, "Numerical");
        __L409 = new PerturbationLocationImpl("CannyEdgeDetector.java:505", 409, "Boolean");
        __L410 = new PerturbationLocationImpl("CannyEdgeDetector.java:505", 410, "Numerical");
        __L411 = new PerturbationLocationImpl("CannyEdgeDetector.java:506", 411, "Numerical");
        __L412 = new PerturbationLocationImpl("CannyEdgeDetector.java:506", 412, "Numerical");
        __L413 = new PerturbationLocationImpl("CannyEdgeDetector.java:507", 413, "Numerical");
        __L414 = new PerturbationLocationImpl("CannyEdgeDetector.java:507", 414, "Numerical");
        __L415 = new PerturbationLocationImpl("CannyEdgeDetector.java:507", 415, "Numerical");
        __L416 = new PerturbationLocationImpl("CannyEdgeDetector.java:507", 416, "Numerical");
        __L417 = new PerturbationLocationImpl("CannyEdgeDetector.java:507", 417, "Numerical");
        __L418 = new PerturbationLocationImpl("CannyEdgeDetector.java:508", 418, "Numerical");
        __L419 = new PerturbationLocationImpl("CannyEdgeDetector.java:508", 419, "Numerical");
        __L420 = new PerturbationLocationImpl("CannyEdgeDetector.java:508", 420, "Numerical");
        __L421 = new PerturbationLocationImpl("CannyEdgeDetector.java:508", 421, "Numerical");
        __L422 = new PerturbationLocationImpl("CannyEdgeDetector.java:508", 422, "Numerical");
        __L423 = new PerturbationLocationImpl("CannyEdgeDetector.java:509", 423, "Numerical");
        __L424 = new PerturbationLocationImpl("CannyEdgeDetector.java:509", 424, "Numerical");
        __L425 = new PerturbationLocationImpl("CannyEdgeDetector.java:509", 425, "Numerical");
        __L426 = new PerturbationLocationImpl("CannyEdgeDetector.java:510", 426, "Numerical");
        __L427 = new PerturbationLocationImpl("CannyEdgeDetector.java:510", 427, "Numerical");
        __L428 = new PerturbationLocationImpl("CannyEdgeDetector.java:510", 428, "Numerical");
        __L429 = new PerturbationLocationImpl("CannyEdgeDetector.java:510", 429, "Numerical");
        __L430 = new PerturbationLocationImpl("CannyEdgeDetector.java:510", 430, "Numerical");
        __L431 = new PerturbationLocationImpl("CannyEdgeDetector.java:512", 431, "Numerical");
        __L432 = new PerturbationLocationImpl("CannyEdgeDetector.java:512", 432, "Numerical");
        __L433 = new PerturbationLocationImpl("CannyEdgeDetector.java:512", 433, "Boolean");
        __L434 = new PerturbationLocationImpl("CannyEdgeDetector.java:513", 434, "Numerical");
        __L435 = new PerturbationLocationImpl("CannyEdgeDetector.java:513", 435, "Numerical");
        __L436 = new PerturbationLocationImpl("CannyEdgeDetector.java:513", 436, "Numerical");
        __L437 = new PerturbationLocationImpl("CannyEdgeDetector.java:513", 437, "Numerical");
        __L438 = new PerturbationLocationImpl("CannyEdgeDetector.java:514", 438, "Numerical");
        __L439 = new PerturbationLocationImpl("CannyEdgeDetector.java:514", 439, "Numerical");
        __L440 = new PerturbationLocationImpl("CannyEdgeDetector.java:514", 440, "Numerical");
        __L441 = new PerturbationLocationImpl("CannyEdgeDetector.java:514", 441, "Boolean");
        __L442 = new PerturbationLocationImpl("CannyEdgeDetector.java:514", 442, "Numerical");
        __L443 = new PerturbationLocationImpl("CannyEdgeDetector.java:515", 443, "Numerical");
        __L444 = new PerturbationLocationImpl("CannyEdgeDetector.java:515", 444, "Numerical");
        __L445 = new PerturbationLocationImpl("CannyEdgeDetector.java:515", 445, "Numerical");
        __L446 = new PerturbationLocationImpl("CannyEdgeDetector.java:515", 446, "Numerical");
        __L447 = new PerturbationLocationImpl("CannyEdgeDetector.java:515", 447, "Numerical");
        __L448 = new PerturbationLocationImpl("CannyEdgeDetector.java:517", 448, "Numerical");
        __L449 = new PerturbationLocationImpl("CannyEdgeDetector.java:517", 449, "Numerical");
        __L450 = new PerturbationLocationImpl("CannyEdgeDetector.java:517", 450, "Boolean");
        __L451 = new PerturbationLocationImpl("CannyEdgeDetector.java:518", 451, "Numerical");
        __L452 = new PerturbationLocationImpl("CannyEdgeDetector.java:518", 452, "Numerical");
        __L453 = new PerturbationLocationImpl("CannyEdgeDetector.java:518", 453, "Numerical");
        __L454 = new PerturbationLocationImpl("CannyEdgeDetector.java:518", 454, "Numerical");
        __L455 = new PerturbationLocationImpl("CannyEdgeDetector.java:519", 455, "Numerical");
        __L456 = new PerturbationLocationImpl("CannyEdgeDetector.java:519", 456, "Numerical");
        __L457 = new PerturbationLocationImpl("CannyEdgeDetector.java:519", 457, "Numerical");
        __L458 = new PerturbationLocationImpl("CannyEdgeDetector.java:519", 458, "Boolean");
        __L459 = new PerturbationLocationImpl("CannyEdgeDetector.java:519", 459, "Numerical");
        __L460 = new PerturbationLocationImpl("CannyEdgeDetector.java:520", 460, "Numerical");
        __L461 = new PerturbationLocationImpl("CannyEdgeDetector.java:520", 461, "Numerical");
        __L462 = new PerturbationLocationImpl("CannyEdgeDetector.java:520", 462, "Numerical");
        __L463 = new PerturbationLocationImpl("CannyEdgeDetector.java:520", 463, "Numerical");
        __L464 = new PerturbationLocationImpl("CannyEdgeDetector.java:520", 464, "Numerical");
        __L465 = new PerturbationLocationImpl("CannyEdgeDetector.java:520", 465, "Numerical");
        __L466 = new PerturbationLocationImpl("CannyEdgeDetector.java:520", 466, "Numerical");
        __L467 = new PerturbationLocationImpl("CannyEdgeDetector.java:522", 467, "Numerical");
        __L468 = new PerturbationLocationImpl("CannyEdgeDetector.java:522", 468, "Numerical");
        __L469 = new PerturbationLocationImpl("CannyEdgeDetector.java:522", 469, "Boolean");
        __L470 = new PerturbationLocationImpl("CannyEdgeDetector.java:523", 470, "Numerical");
        __L471 = new PerturbationLocationImpl("CannyEdgeDetector.java:523", 471, "Numerical");
        __L472 = new PerturbationLocationImpl("CannyEdgeDetector.java:523", 472, "Numerical");
        __L473 = new PerturbationLocationImpl("CannyEdgeDetector.java:523", 473, "Numerical");
        __L474 = new PerturbationLocationImpl("CannyEdgeDetector.java:524", 474, "Numerical");
        __L475 = new PerturbationLocationImpl("CannyEdgeDetector.java:525", 475, "Numerical");
        __L476 = new PerturbationLocationImpl("CannyEdgeDetector.java:525", 476, "Numerical");
        __L477 = new PerturbationLocationImpl("CannyEdgeDetector.java:525", 477, "Numerical");
        __L478 = new PerturbationLocationImpl("CannyEdgeDetector.java:525", 478, "Boolean");
        __L479 = new PerturbationLocationImpl("CannyEdgeDetector.java:525", 479, "Numerical");
        __L480 = new PerturbationLocationImpl("CannyEdgeDetector.java:526", 480, "Numerical");
        __L481 = new PerturbationLocationImpl("CannyEdgeDetector.java:526", 481, "Numerical");
        __L482 = new PerturbationLocationImpl("CannyEdgeDetector.java:526", 482, "Numerical");
        __L483 = new PerturbationLocationImpl("CannyEdgeDetector.java:526", 483, "Numerical");
        __L484 = new PerturbationLocationImpl("CannyEdgeDetector.java:527", 484, "Numerical");
        __L485 = new PerturbationLocationImpl("CannyEdgeDetector.java:527", 485, "Numerical");
        __L486 = new PerturbationLocationImpl("CannyEdgeDetector.java:527", 486, "Numerical");
        __L487 = new PerturbationLocationImpl("CannyEdgeDetector.java:527", 487, "Numerical");
        __L488 = new PerturbationLocationImpl("CannyEdgeDetector.java:528", 488, "Numerical");
        __L489 = new PerturbationLocationImpl("CannyEdgeDetector.java:528", 489, "Numerical");
        __L490 = new PerturbationLocationImpl("CannyEdgeDetector.java:528", 490, "Numerical");
        __L491 = new PerturbationLocationImpl("CannyEdgeDetector.java:528", 491, "Numerical");
        __L492 = new PerturbationLocationImpl("CannyEdgeDetector.java:529", 492, "Numerical");
        __L493 = new PerturbationLocationImpl("CannyEdgeDetector.java:529", 493, "Numerical");
        __L494 = new PerturbationLocationImpl("CannyEdgeDetector.java:529", 494, "Numerical");
        __L495 = new PerturbationLocationImpl("CannyEdgeDetector.java:529", 495, "Numerical");
        __L496 = new PerturbationLocationImpl("CannyEdgeDetector.java:529", 496, "Numerical");
        __L497 = new PerturbationLocationImpl("CannyEdgeDetector.java:248", 497, "Boolean");
        __L498 = new PerturbationLocationImpl("CannyEdgeDetector.java:225", 498, "Boolean");
        __L499 = new PerturbationLocationImpl("CannyEdgeDetector.java:202", 499, "Numerical");
        __L500 = new PerturbationLocationImpl("CannyEdgeDetector.java:202", 500, "Numerical");
        __L501 = new PerturbationLocationImpl("CannyEdgeDetector.java:202", 501, "Boolean");
        __L502 = new PerturbationLocationImpl("CannyEdgeDetector.java:203", 502, "Numerical");
        __L503 = new PerturbationLocationImpl("CannyEdgeDetector.java:177", 503, "Boolean");
        __L504 = new PerturbationLocationImpl("CannyEdgeDetector.java:153", 504, "Boolean");
        __L505 = new PerturbationLocationImpl("CannyEdgeDetector.java:492", 505, "Numerical");
        __L506 = new PerturbationLocationImpl("CannyEdgeDetector.java:492", 506, "Numerical");
        __L507 = new PerturbationLocationImpl("CannyEdgeDetector.java:492", 507, "Numerical");
        __L508 = new PerturbationLocationImpl("CannyEdgeDetector.java:492", 508, "Boolean");
        __L509 = new PerturbationLocationImpl("CannyEdgeDetector.java:492", 509, "Numerical");
        __L510 = new PerturbationLocationImpl("CannyEdgeDetector.java:493", 510, "Numerical");
        __L511 = new PerturbationLocationImpl("CannyEdgeDetector.java:493", 511, "Numerical");
        __L512 = new PerturbationLocationImpl("CannyEdgeDetector.java:493", 512, "Numerical");
        __L513 = new PerturbationLocationImpl("CannyEdgeDetector.java:493", 513, "Numerical");
        __L514 = new PerturbationLocationImpl("CannyEdgeDetector.java:493", 514, "Boolean");
        __L515 = new PerturbationLocationImpl("CannyEdgeDetector.java:493", 515, "Numerical");
        __L516 = new PerturbationLocationImpl("CannyEdgeDetector.java:493", 516, "Numerical");
        __L517 = new PerturbationLocationImpl("CannyEdgeDetector.java:493", 517, "Numerical");
        __L518 = new PerturbationLocationImpl("CannyEdgeDetector.java:493", 518, "Numerical");
        __L519 = new PerturbationLocationImpl("CannyEdgeDetector.java:562", 519, "Boolean");
        __L520 = new PerturbationLocationImpl("CannyEdgeDetector.java:563", 520, "Numerical");
        __L521 = new PerturbationLocationImpl("CannyEdgeDetector.java:563", 521, "Numerical");
        __L522 = new PerturbationLocationImpl("CannyEdgeDetector.java:563", 522, "Numerical");
        __L523 = new PerturbationLocationImpl("CannyEdgeDetector.java:565", 523, "Numerical");
        __L524 = new PerturbationLocationImpl("CannyEdgeDetector.java:565", 524, "Numerical");
        __L525 = new PerturbationLocationImpl("CannyEdgeDetector.java:565", 525, "Numerical");
        __L526 = new PerturbationLocationImpl("CannyEdgeDetector.java:565", 526, "Numerical");
        __L527 = new PerturbationLocationImpl("CannyEdgeDetector.java:565", 527, "Numerical");
        __L528 = new PerturbationLocationImpl("CannyEdgeDetector.java:565", 528, "Numerical");
    }
}

