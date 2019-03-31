package de.kreth.property2java;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Map;

import org.apache.commons.text.WordUtils;

import de.kreth.property2java.config.FreemarkerConfigImpl;
import de.kreth.property2java.config.Regex;
import freemarker.template.Template;

public interface Configuration {

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

	default Reader previousGenerated(String fileName) throws IOException {
		File file = new File(getRootPath().toFile(), mapFilenameToClassName(fileName) + ".java");
		if (file.exists()) {
			return new FileReader(file);
		}
		else {
			return new StringReader("");
		}
	}

	default Writer outWriter(String fileName) throws IOException {
		return new FileWriter(new File(getRootPath().toFile(), mapFilenameToClassName(fileName) + ".java"));
	}

	default String mapFilenameToClassName(String fileName) {

		String path = Regex.PATTERN.matcher(fileName).replaceAll(".").replaceAll("\\.", "_").replaceAll(" ", "_");
		path = WordUtils.capitalize(path, '_');
		return path;
	}

	default Template getTemplate() throws IOException {
		return FreemarkerConfigImpl.INSTANCE.getTemplate();
	}

}
