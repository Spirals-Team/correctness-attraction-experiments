package tea;

/**
 * Created by bdanglot on 24/05/16.
 */
public class TEA {

    public static int[] encrypt(int[] v, int[] k) {
        int v0 = v[0], v1 = v[1], acc = 0;
        int delta = 0x9e3779b9;
        int k0 = k[0],k1 = k[1],k2 = k[2],k3 = k[3];
        for (int i = 0; i < 32; i++) {
            acc += delta;
            v0 += ((v1<<4) + k0) ^ (v1 + acc) ^ ((v1>>5) + k1);
            v1 += ((v0<<4) + k2) ^ (v0 + acc) ^ ((v0>>5) + k3);
        }
        v[0]=v0; v[1]=v1;
        return v;
    }

    public static int [] decrypt(int[] v, int[] k) {
        int v0 = v[0], v1 = v[1], acc=0xC6EF3720;
        int delta = 0x9e3779b9;
        int k0 = k[0],k1 = k[1],k2 = k[2],k3 = k[3];
        for (int i = 0; i < 32; i++) {
            v1 -= ((v0<<4) + k2) ^ (v0 + acc) ^ ((v0>>5) + k3);
            v0 -= ((v1<<4) + k0) ^ (v1 + acc) ^ ((v1>>5) + k1);
            acc -= delta;
        }
        v[0]=v0; v[1]=v1;
        return v;
    }

}
