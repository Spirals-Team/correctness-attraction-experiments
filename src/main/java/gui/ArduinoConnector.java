package gui;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdanglot on 21/07/16.
 */
public class ArduinoConnector {

	private SerialPort serialPort;
	private BufferedReader serialReader;
	private InputStream serialIn;

	private List<ArduinoListener> listeners = new ArrayList<>();

	public ArduinoConnector() {

		try {

			new Thread(() -> {
				try {
					CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("/dev/ttyUSB0");
					serialPort = (SerialPort) portIdentifier.open(portIdentifier.getName(), 2000);
					serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					serialIn = serialPort.getInputStream();
					serialReader = new BufferedReader(new InputStreamReader(serialIn));
				} catch (Exception e)  {

				}
				while (true) {
					try {
						String line = serialReader.readLine();
						event(Integer.parseInt(line));
					} catch (IOException ex) {
						ex.printStackTrace();
						break;
					}
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (serialPort != null) {
				serialPort.close();
			}
		}
	}

	public void addListener(ArduinoListener a) {
		listeners.add(a);
	}

	public void removeListener(ArduinoListener a) {
		listeners.remove(a);
	}

	private void  event(int data) {
		for (int i = 0; i < listeners.size(); i++) {
			ArduinoListener arduinoListener = listeners.get(i);
			if(data == 0) {
				arduinoListener.decrease();
			} else if (data == 1) {
				arduinoListener.increase();
			}
			arduinoListener.event(data);
		}
	}
}
