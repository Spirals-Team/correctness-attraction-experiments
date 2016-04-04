package sort;

/**
 * Created by spirals on 01/04/16.
 */
public class Tuple {

    public int valueOne;
    public int valueTwo;

    public Tuple() {
        this.valueOne = 0;
        this.valueTwo = 0;
    }

    public Tuple(int valueOne, int valueTwo) {
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
    }

    public int total() {
        return this.valueOne + this.valueTwo;
    }

    public Tuple add(Tuple that) {
        Tuple result = new Tuple();
        result.valueOne = this.valueOne + that.valueOne;
        result.valueTwo = this.valueTwo + that.valueTwo;
        return result;
    }

    public String toString() {
        return this.valueOne + "\t" + this.valueTwo;
    }

}
