package de.kreth.property2java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ConfigurationTest {

	private Configuration config;

	@BeforeEach
	void initConfig() {
		config = Mockito.mock(Configuration.class);
	}

	@Test
	void defaultWriterIsFileWriter() throws IOException {

		when(config.outWriter(anyString())).thenCallRealMethod();
		when(config.getRootPath()).thenReturn(new File(".").toPath());
		when(config.mapFilenameToClassName(anyString())).thenCallRealMethod();

		Writer outWriter = config.outWriter("application.properties");
		assertTrue(outWriter instanceof FileWriter);
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
