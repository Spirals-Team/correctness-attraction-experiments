package main;

/**
 * Created by bdanglot on 12/08/16.
 */
public class Example {

	static int function(int bound) {
		int acc = 0;
		int mask = 0x2;
		for (int i = bound; i > 0; i--) {
			acc |= i >> mask;
		}
		return acc;
	}

}
