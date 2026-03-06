package de.kreth.property2java.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.StringReader;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;


import de.kreth.property2java.Format;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProcessorConfigurationTest {

	@Mock
	private Filer filer;
	@Mock
	private TypeElement element;

	@Test
	void testPackage() throws IOException {
		PackageElement packageElement = mock(PackageElement.class);
		Name name = mock(Name.class);
		String packageName = "de.kreth.pack.name";
		String resourceName = "localization.properties";
		FileObject fileObject = mock(FileObject.class);
		StringReader input = new StringReader("""
				key1=value1
				key2=value2""");

		when(element.getEnclosingElement()).thenReturn(packageElement);
		when(packageElement.getQualifiedName()).thenReturn(name);
		when(name.toString()).thenReturn(packageName);
		when(filer.getResource(StandardLocation.CLASS_PATH, "", resourceName)).thenReturn(fileObject);
		when(fileObject.openReader(false)).thenReturn(input);

		ProcessorConfiguration config = new ProcessorConfiguration(
		ProcessorConfiguration.builder(filer, element)
			.withFormat(Format.WithInnerPropertyLoader)
			.addAll(resourceName));

		assertThat(config.getPackage()).isEqualTo(packageName);
	}
}
