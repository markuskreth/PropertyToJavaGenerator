package de.kreth.property2java.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import de.kreth.property2java.Format;
import de.kreth.property2java.GeneratorException;

@SupportedAnnotationTypes({ "de.kreth.property2java.processor.GenerateProperty2Java",
		"de.kreth.property2java.processor.GenerateResourceBundleProperty2Javas",
		"de.kreth.property2java.processor.GenerateResourceBundleProperty2Java" })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class Property2JavaGenerator extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		if (!roundEnv.processingOver()) {
			processGenerateProperty2Java(roundEnv);
			processGenerateResourceBundleProperty2Javas(roundEnv);
		} else {
			processingEnv.getMessager().printMessage(Kind.NOTE, "finished working on annotation " + annotations);
		}
		return true;
	}

	private void processGenerateProperty2Java(RoundEnvironment roundEnv) {
		processingEnv.getMessager().printMessage(Kind.NOTE, "Processing annotation " + GenerateProperty2Java.class);

		Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(GenerateProperty2Java.class);

		for (Element element : elementsAnnotatedWith) {
			GenerateProperty2Java generateAnnotation = element.getAnnotation(GenerateProperty2Java.class);
			String[] resources = generateAnnotation.resources();
			Format format = generateAnnotation.format();
			generateElementProperties(element, Arrays.asList(resources), format);
		}
	}

	private void processGenerateResourceBundleProperty2Javas(RoundEnvironment roundEnv) {

		processingEnv.getMessager().printMessage(Kind.NOTE,
				"Processing annotation " + GenerateResourceBundleProperty2Javas.class);

		Set<? extends Element> elementsAnnotatedWith = roundEnv
				.getElementsAnnotatedWith(GenerateResourceBundleProperty2Javas.class);

		for (Element element : elementsAnnotatedWith) {
			GenerateResourceBundleProperty2Java[] value = element
					.getAnnotation(GenerateResourceBundleProperty2Javas.class).value();
			for (GenerateResourceBundleProperty2Java generateResourceBundleProperty2Java : value) {
				List<String> resources = Arrays.asList(generateResourceBundleProperty2Java.resource());
				generateElementProperties(element, resources, generateResourceBundleProperty2Java.format());
			}
		}
	}

	private void generateElementProperties(Element element, List<String> resources, Format format) {
		processingEnv.getMessager().printMessage(Kind.NOTE, "Generating Java for " + Arrays.asList(resources));
		try {
			ProcessorConfiguration.builder(processingEnv.getFiler(), element).addAll(resources).withFormat(format)
					.startGeneration();
		} catch (IOException | GeneratorException e) {
			StringWriter out = new StringWriter();
			e.printStackTrace(new PrintWriter(out));
			out.flush();
			processingEnv.getMessager().printMessage(Kind.ERROR, "Exception " + e + "\n" + out.toString(), element);
		}
	}

}
