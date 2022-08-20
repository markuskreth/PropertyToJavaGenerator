package de.kreth.property2java.processor;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import de.kreth.property2java.Configuration;
import de.kreth.property2java.Generator;
import de.kreth.property2java.GeneratorException;

public class ProcessorConfiguration implements Configuration {

    private final Filer filer;
    private final Element element;
    private final Map<String, Reader> input;
    private final Format format;

    ProcessorConfiguration(Builder builder) throws IOException {
	this.filer = builder.filer;
	this.element = builder.element;
	this.format = Objects.requireNonNullElse(builder.format, Format.WithUnaryOperatorParameter);
	this.input = new HashMap<>();
	for (String resource : builder.resourcenames) {
	    FileObject ressource = filer.getResource(StandardLocation.CLASS_PATH, "", resource);
	    input.put(resource, ressource.openReader(false));
	}
    }

    @Override
    public String getPackage() {

	String packageName = "";
	if (element instanceof TypeElement) {
	    TypeElement typeElement = (TypeElement) element;
	    PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
	    packageName = packageElement.getQualifiedName().toString();
	}
	return packageName;
    }

    @Override
    public Format getFormat() {
	return format;
    }

    @Override
    public Map<String, Reader> getInput() {
	return input;
    }

    @Override
    public Path getRootPath() {
	throw new UnsupportedOperationException(
		"For Annotation Processor this is not supported as outWriter is overwritten.");
    }

    @Override
    public Writer outWriter(String fileName) throws IOException {
	String packageName = getPackage();
	if (packageName != null && !packageName.isBlank()) {
	    fileName = packageName + "." + mapFilenameToClassName(fileName);
	}
	return filer.createSourceFile(fileName, element).openWriter();
    }

    static Builder builder(Filer filer, Element element) {
	return new Builder(filer, element);
    }

    static class Builder {
	private final Filer filer;
	private final Element element;
	private final List<String> resourcenames;
	private Format format = Format.WithUnaryOperatorParameter;

	private Builder(Filer filer, Element element) {
	    this.filer = filer;
	    this.element = element;
	    this.resourcenames = new ArrayList<>();
	}

	public Builder withFormat(Format format) {
	    this.format = format;
	    return this;
	}

	public Builder addAll(String[] resourceNames) {
	    this.resourcenames.addAll(Arrays.asList(resourceNames));
	    return this;
	}

	public Builder addAll(List<String> resourceNames) {
	    this.resourcenames.addAll(resourceNames);
	    return this;
	}

	public void startGeneration() throws IOException, GeneratorException {
	    Generator g = new Generator(new ProcessorConfiguration(this));
	    g.start();
	}
    }
}
