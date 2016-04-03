package sort;

/**
 * Created by spirals on 01/04/16.
 */
public class Result {

    public int numberOfSuccess = 0;
    public int numberOfFailure = 0;

    public int total() {
        return this.numberOfSuccess + this.numberOfFailure;
    }

    public Result add(Result that) {
        Result result = new Result();
        result.numberOfSuccess = this.numberOfSuccess + that.numberOfSuccess;
        result.numberOfFailure = this.numberOfFailure + that.numberOfFailure;
        return result;
    }

    public String toString() {
        return this.numberOfSuccess + "\t" + this.numberOfFailure;
    }

}
