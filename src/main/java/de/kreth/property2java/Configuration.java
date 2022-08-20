package de.kreth.property2java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;

import org.apache.commons.text.WordUtils;

import de.kreth.property2java.config.Regex;
import de.kreth.property2java.processor.Format;

public interface Configuration {

    /**
     * Package for generated Java Classes eg. "de.kreth.property2java". If null - no
     * package line is generated.
     *
     * @return
     */
    String getPackage();

    default Format getFormat() {
	return Format.WithUnaryOperatorParameter;
    }

    /**
     * Filename to InputReader Entries
     *
     * @return
     */
    Map<String, Reader> getInput();

    /**
     * Path of java source folder.
     *
     * @return
     */
    Path getRootPath();

    default Writer outWriter(String fileName) throws IOException {
	return new FileWriter(new File(getRootPath().toFile(), mapFilenameToClassName(fileName) + ".java"),
		outputCharset());
    }

    default Charset outputCharset() {
	return Charset.defaultCharset();
    }

    default String mapFilenameToClassName(String fileName) {

	String path = Regex.PATTERN.matcher(fileName)
		.replaceAll(".")
		.replaceAll("\\.", "_")
		.replaceAll(" ", "_")
		.replaceAll("/", "_");
	path = WordUtils.capitalize(path, '_');
	return path;
    }

}
