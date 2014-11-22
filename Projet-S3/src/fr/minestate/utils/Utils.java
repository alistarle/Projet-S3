package fr.minestate.utils;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

	/**
	 * supprime tout les blanc inutile (avant et apr�s la chaine)
	 * @param str chaine � traiter
	 * @return nouvelle chaine sans espaces inutiles
	 */
	public static String removeUselessSpaces(String str) {
		return str.replaceAll("^[\\s]*", "").replaceAll("[\\s]*$", "");
	}

	public static String readFile(File file) {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			return new String(encoded);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
