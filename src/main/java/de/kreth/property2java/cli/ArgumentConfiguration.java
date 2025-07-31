package de.kreth.property2java.cli;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.kreth.property2java.Configuration;

public final class ArgumentConfiguration implements Configuration {

	private final String packageName;

	private final Map<String, Reader> files;

	private final Path rootPath;

	private ArgumentConfiguration(Builder builder) throws IOException {
		this.packageName = builder.packageName;
		rootPath = new File(builder.target).toPath();
		files = new HashMap<>();
		for (String filePath : builder.propFiles) {
			File f = new File(filePath);
			files.put(f.getName(), new FileReader(f));
		}

	}

	@Override
	public String getPackage() {
		return packageName;
	}

	@Override
	public Map<String, Reader> getInput() {
		return files;
	}

	@Override
	public Path getRootPath() {
		return rootPath;
	}

	@Override
	public Writer outWriter(String fileName) throws IOException {
		File dir;
		if (packageName != null && !packageName.isBlank()) {
			dir = new File(rootPath.toFile(), packageName.replace('.', File.separatorChar));
		} else {
			dir = rootPath.toFile();
		}
		return new FileWriter(new File(dir, mapFilenameToClassName(fileName) + ".java"), false);
	}

	public static Configuration parse(String[] args) throws IOException {
		CliConfig cliConfig = new CliConfig();

		Builder builder = new Builder();
		cliConfig.fill(builder, args);
		return builder.build();
	}

	public static class Builder {
		String target;

		List<String> propFiles = new ArrayList<>();

		String packageName;

		public Builder setTarget(String target) {
			this.target = target;
			return this;
		}

		public Builder addPropFile(String propFile) {
			this.propFiles.add(propFile);
			return this;
		}

		public Builder setPackageName(String packageName) {
			this.packageName = packageName;
			return this;
		}

		public Configuration build() throws IOException {
			return new ArgumentConfiguration(this);
		}
	}
}
