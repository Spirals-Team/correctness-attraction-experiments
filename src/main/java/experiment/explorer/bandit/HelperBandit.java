package experiment.explorer.bandit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by bdanglot on 06/07/16.
 */
public class HelperBandit {

	static final String CMD_EXEC_BANDIT;

	static {
		URL[] urls = ((URLClassLoader) BanditExplorer.class.getClassLoader()).getURLs();
		String classpath = urls[0].getPath();
		for (int i = 1; i < urls.length; i++)
			classpath += ":" + urls[i].getPath();
		CMD_EXEC_BANDIT = "java -cp " + classpath + " experiment.explorer.bandit.BanditExplorer ";
	}

	/**
	 * return the content of the the given input stream
	 *
	 * @param in
	 * @return
	 */
	static String readInput(InputStream in) {
		String out = "";
		try {
			while (in.available() != 0)
				out += (char)in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	static void readStream(InputStream in) {
		try {
			BufferedReader reader = new BufferedReader (new InputStreamReader(in));
			String line;
			while ((line = reader.readLine ()) != null) {
				System.out.println ("Stdout: " + line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
