package hr.fer.zemris.project.geometry.dash.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import hr.fer.zemris.project.geometry.dash.model.settings.GameConstants;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

/**
 * Provides method for writing to zip file and reading from zip file
 * @author Andi Škrgat
 *
 */
public class ZipUtil {

	/**
	 * Saves level to zip file
	 * @param zipFileName zip file
	 * @param json content to save
	 * @param existingFile if null user is asked to input file name, else uses existing file as place to save
	 */
	public static String saveToZipFile(String zipFileName, String json, String existingFile) {
		String enteredFileName = null;
		if(existingFile == null) {
			TextInputDialog dialog = new TextInputDialog("Enter level name");
			dialog.setTitle("SAVE LEVEL");
			dialog.setHeaderText("Please enter level name(without extension)");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
			    enteredFileName = result.get();
			} else {
				return null;
			}
		} else {
			enteredFileName = existingFile;
		}
		OutputStream fos;
		InputStream is = new ByteArrayInputStream(json.getBytes());
		byte[] buffer = new byte[GameConstants.bytesPerKB];
		try {
			fos = new FileOutputStream(zipFileName + "/" + enteredFileName + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			zos.putNextEntry(new ZipEntry(enteredFileName + ".json"));
			int length;
			while((length = is.read(buffer)) != -1) {
				zos.write(buffer, 0, length);
				zos.flush();
			}
			zos.closeEntry();
			zos.close();
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return enteredFileName;
	}
	
	/**
	 * Reads content from zip file
	 * @param filename if it is null then it asks user with the help of {@linkplain FileChooser} which file to load, else loads
	 * requested file - extension must not be entered
	 * @return {@linkplain String} file content
	 */
	public static String openZipFile(String zipFileName, String fileInZip) {
		if(fileInZip == null)  {
			TextInputDialog dialog = new TextInputDialog("Enter name of the level you want to load: ");
			dialog.setTitle("LOAD LEVEL");
			dialog.setHeaderText("Please enter level name(without extension)");
			Optional<String> result = dialog.showAndWait();
			String enteredFileName = null;
			if (result.isPresent()) {
			    enteredFileName = result.get();
			} else {
				return null;
			}
			fileInZip = enteredFileName;
		}
		StringBuilder sb = new StringBuilder();
		 try(ZipFile zipFile = new ZipFile(zipFileName + "/" + fileInZip + ".zip")) {
			 ZipEntry zipEntry = zipFile.getEntry(fileInZip + ".json");
			 InputStream is = zipFile.getInputStream(zipEntry);
			 BufferedReader br = new BufferedReader(new InputStreamReader(is));
			 String line = null;
			 while((line = br.readLine()) != null) {
				 sb = sb.append(line);
			 }
			 is.close();
			 br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
