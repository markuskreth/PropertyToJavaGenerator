package de.kreth.property2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConfigurationTest {

	@Mock
	private Configuration config;

	@Test
	void defaultWriterIsFileWriter() throws IOException {

		when(config.getRootPath()).thenReturn(new File(".").toPath());
		when(config.outWriter(anyString())).thenCallRealMethod();
		when(config.mapFilenameToClassName(anyString())).thenCallRealMethod();
		when(config.outputCharset()).thenCallRealMethod();

		Writer outWriter = config.outWriter("application.properties");
        assertInstanceOf(FileWriter.class, outWriter);
	}

	@Test
	void testPathMapping() {
		when(config.mapFilenameToClassName(anyString())).thenCallRealMethod();
		String className = config.mapFilenameToClassName("application.properties");
		assertEquals("Application_Properties", className);
	}

	@Test
	void testPathMappingLocalized() {
		when(config.mapFilenameToClassName(anyString())).thenCallRealMethod();
		String className = config.mapFilenameToClassName("application_de_DE.properties");
		assertEquals("Application_Properties", className);
		className = config.mapFilenameToClassName("application_en_US.properties");
		assertEquals("Application_Properties", className);
	}
}
