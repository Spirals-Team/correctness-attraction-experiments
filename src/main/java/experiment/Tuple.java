package experiment;

/**
 * Created by spirals on 05/04/16.
 */
public class Tuple {

    private int[] values;

    public int get(int i) {
        return this.values[i];
    }

    public void set(int i, int value) {
        this.values[i] = value;
    }

    public Tuple(int number) {
        this.values = new int[number];
    }

    public Tuple add(Tuple that) {
        Tuple add = new Tuple(this.values.length);
        for (int i = 0; i < Math.min(this.values.length, that.values.length) ; i++)
            add.values[i] = this.values[i] + that.values[i];
        return add;
    }

    public int total() {
        int total = 0;
        for (int i = 0; i < this.values.length ; i++)
            total += this.values[i];
        return total;
    }

    public int total(int bound) {
        int total = 0;
        for (int i = 0; i < bound && i < this.values.length ; i++)
            total += this.values[i];
        return total;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int value : values)
            ret += value + " ";
        return ret;
    }

}
