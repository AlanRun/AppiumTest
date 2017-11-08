package iOS.utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtils {

	private File logFile;
	private FileWriter fileWriter;
	public LogUtils(String name) {
		logFile = getDataFile(name);
	}
	
//	public static LogUtils getInstance() {
//		if (fileUtils == null) {
//			fileUtils = new LogUtils();
//		}
//		return fileUtils;
//	}
	
	public void saveData(String data) {
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
				fileWriter = new FileWriter(logFile, true);
				fileWriter.append(data);
				fileWriter.append("\n");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fileWriter != null) {
					try {
						fileWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			try {
				fileWriter = new FileWriter(logFile, true);
				fileWriter.append(data);
				fileWriter.append("\n");
				fileWriter.flush();

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (fileWriter != null) {
					try {
						fileWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private File getDataFile(String name) {
		String path = System.getProperty("user.dir") + "/target/surefire-reports/html/logs/";
		if (!(new File(path)).exists()) {
			(new File(path)).mkdirs();
		}
		String file = name + ".txt";
		return new File(path + file);
	}
}
