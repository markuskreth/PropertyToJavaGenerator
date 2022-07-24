package de.kreth.property2java.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import de.kreth.property2java.GeneratorException;

@SupportedAnnotationTypes({ "de.kreth.property2java.processor.GenerateProperty2Java" })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class Property2JavaGenerator extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

	if (!roundEnv.processingOver()) {
	    processingEnv.getMessager().printMessage(Kind.NOTE,
		    "Processing annotation " + GenerateProperty2Java.class);

	    Set<? extends Element> elementsAnnotatedWith = roundEnv
		    .getElementsAnnotatedWith(GenerateProperty2Java.class);

	    processElements(elementsAnnotatedWith);
	} else {
	    processingEnv.getMessager().printMessage(Kind.NOTE,
		    "finished working on annotation " + GenerateProperty2Java.class);
	}
	return true;
    }

    private void processElements(Set<? extends Element> elementsAnnotatedWith) {
	for (Element element : elementsAnnotatedWith) {
	    String[] resources = element.getAnnotation(GenerateProperty2Java.class).resources();
	    processingEnv.getMessager().printMessage(Kind.NOTE,
		    "Generating Java for " + Arrays.asList(resources));
	    try {
		ProcessorConfiguration
			.builder(processingEnv.getFiler(), element)
			.addAll(resources)
			.startGeneration();
	    } catch (IOException | GeneratorException e) {
		StringWriter out = new StringWriter();
		e.printStackTrace(new PrintWriter(out));
		out.flush();
		processingEnv.getMessager().printMessage(Kind.ERROR, "Exception " + e + "\n" + out.toString(),
			element);
	    }
	}
    }
}
