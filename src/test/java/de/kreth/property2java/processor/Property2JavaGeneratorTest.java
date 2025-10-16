package de.kreth.property2java.processor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void testGeneratorInitializedCorrectly() {
		
		GenerateProperty2Java an = mock(GenerateProperty2Java.class);
		when(an.resources()).thenReturn(new String[] {});
		TypeElement element = mock(TypeElement.class);
		when(element.getAnnotation(GenerateProperty2Java.class)).thenReturn(an);
		
		annotations.add(element);
		
		when(roundEnv.getElementsAnnotatedWith(ArgumentMatchers.any(Class.class))).thenReturn(annotations);
		Element annotatedElement = mock(Element.class);
		GenerateResourceBundleProperty2Javas value = new GenerateResourceBundleProperty2Javas() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public GenerateResourceBundleProperty2Java[] value() {
				return new GenerateResourceBundleProperty2Java[0];
			}
		};
		when(annotatedElement.getAnnotation(GenerateResourceBundleProperty2Javas.class)).thenReturn(value );
		Set<Element> elements = new HashSet<>(List.of(annotatedElement));
		
		when(roundEnv.getElementsAnnotatedWith(GenerateResourceBundleProperty2Javas.class))
			.thenReturn((Set)elements);
		
		processor.process(annotations, roundEnv);
		
		
	}

}
