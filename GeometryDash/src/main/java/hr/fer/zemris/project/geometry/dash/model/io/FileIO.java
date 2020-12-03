package hr.fer.zemris.project.geometry.dash.model.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.project.geometry.dash.model.Session;
import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import hr.fer.zemris.project.geometry.dash.threads.DaemonicThreadFactory;

/**
 * Class for IO operations
 * 
 * @author Andi Škrgat
 *
 */
public class FileIO {

	/**
	 * Creates json file
	 * 
	 * @param json json content
	 */
	public static void createJsonFile(String fileName, String json) {
		DaemonicThreadFactory.getInstance().newThread(() -> {
			try {
				OutputStream os = new BufferedOutputStream(new FileOutputStream(fileName));
				InputStream is = new ByteArrayInputStream(json.getBytes());
				byte[] buffer = new byte[GameConstants.bytesPerKB];
				int length;
				while ((length = is.read(buffer)) != -1) {
					os.write(buffer, 0, length);
				}
				is.close();
				os.flush();
				os.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}).start();
	}

	/**
	 * Reads from json file
	 * 
	 * @param fileName file name
	 * @return content
	 */
	public static String readFromJsonFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(fileName));
			int length;
			byte[] buffer = new byte[GameConstants.bytesPerKB];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((length = is.read(buffer)) != -1) {
//				sb.append(new String(buffer, StandardCharsets.UTF_8));
				baos.write(buffer, 0, length);
			}
			sb.append(baos.toString(StandardCharsets.UTF_8));
			baos.close();
			is.close();
		} catch (IOException e) {
			return null;
		}
		return sb.toString();
	}
}
