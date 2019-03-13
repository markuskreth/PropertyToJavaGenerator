package de.kreth.property2java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.text.WordUtils;

public interface Configuration {

	static final Pattern REGEX = Pattern.compile("_[a-z]{2}(_[A-Z]{2})?\\.");

	/**
	 * Package for generated Java Classes eg. "de.kreth.property2java". If null - no package line is generated.
	 * @return
	 */
	String getPackage();

	/**
	 * Filename to InputReader Entries
	 * @return
	 */
	Map<String, Reader> getInput();

	/**
	 * Path of java source folder.
	 * @return
	 */
	Path getRootPath();

	default Writer outWriter(String fileName) throws IOException {
		return new FileWriter(new File(getRootPath().toFile(), mapFilenameToClassName(fileName)));
	}

	default String mapFilenameToClassName(String fileName) {

		String path = REGEX.matcher(fileName).replaceAll(".").replaceAll("\\.", "_").replaceAll(" ", "_");
		path = WordUtils.capitalize(path, '_');
		return path;
	}

}
