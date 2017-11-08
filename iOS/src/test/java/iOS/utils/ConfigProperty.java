package iOS.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperty {
	public String fileLocation;
	Properties p = new Properties();
	BufferedReader br;

	public ConfigProperty(String configFileLocation) {
		fileLocation = configFileLocation;
	}

	public String getValue(String key) {
		String value = null;
		try {
			br = new BufferedReader(new FileReader(fileLocation));
			p.load(br);
			value = p.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}
