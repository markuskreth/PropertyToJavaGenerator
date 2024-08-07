package de.kreth.property2java.processor;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Property2JavaGeneratorTest {

	private Property2JavaGenerator processor;
	@Mock
	private ProcessingEnvironment processingEnv;
	@Mock
	private RoundEnvironment roundEnv;
	private Set<TypeElement> annotations;
	@Mock
	private Messager messanger;

	@BeforeEach
	void initProcesor() {
		annotations = new HashSet<>();

		processor = new Property2JavaGenerator();
		processor.init(processingEnv);
		when(processingEnv.getMessager()).thenReturn(messanger);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGeneratorInitializedCorrectly() {
		when(roundEnv.getElementsAnnotatedWith(ArgumentMatchers.any(Class.class))).thenReturn(annotations);
		processor.process(annotations, roundEnv);
	}
}
