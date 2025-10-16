package de.kreth.property2java;

import static de.kreth.property2java.TestPropertiesSource.testProperties;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GeneratorWithInnerPropertiesTest {

	private final String path = "application.properties";

	@Mock
	private Configuration config;
	private Generator generator;

	@BeforeEach
	void setUp() throws Exception {
		Map<String, Reader> input = new HashMap<>();
		input.put(path, testProperties());

		when(config.getRootPath()).thenReturn(new File(".").toPath());
		when(config.getFormat()).thenReturn(Format.WithInnerPropertyLoader);
		when(config.getInput()).thenReturn(input);
		when(config.mapFilenameToClassName(anyString())).thenCallRealMethod();
		when(config.outputCharset()).thenCallRealMethod();
		when(config.getOptions()).thenReturn(EnumSet.noneOf(GeneratorOptions.class));

		this.generator = new Generator(config);
	}


	@Test
	void testClassDefinition() throws IOException, GeneratorException {

		when(config.getPackage()).thenReturn("de.kreth.property2java");
		when(config.mapFilenameToClassName(anyString())).thenCallRealMethod();

		StringWriter out = new StringWriter();
		when(config.outWriter(anyString())).thenReturn(out);

		generator.start();

		String sourceCode = out.toString().trim();
		StringTokenizer sourceTokenizer = new StringTokenizer(sourceCode, "\n");
		String linePackage = null;
		String lineClass = null;
		int countOpenBaces = 0;
		int countCloseBaces = 0;
		while (sourceTokenizer.hasMoreTokens()) {
			String line = sourceTokenizer.nextToken();
			if (line.trim().startsWith("package")) {
				linePackage = line;
			} else if (line.trim().startsWith("public enum")) {
				lineClass = line;
			}
			if (line.contains("{")) {
				countOpenBaces++;
			}
			if (line.contains("}")) {
				countCloseBaces++;
			}
		}

		assertEquals(countCloseBaces, countOpenBaces,
				"Count of Braces doesn't match. Open = " + countOpenBaces + ", Close = " + countCloseBaces);

		assertNotNull(linePackage);
		assertNotNull(lineClass);

		assertThat(linePackage,
				Matchers.stringContainsInOrder(Arrays.asList("package", "de.kreth.property2java", ";")));

		assertThat(lineClass,
				Matchers.stringContainsInOrder(Arrays.asList("public", "enum", "Application_Properties")));

	}

	@Test
	void testResourceLoad() throws IOException, GeneratorException {

		when(config.getPackage()).thenReturn("de.kreth.property2java");
		when(config.mapFilenameToClassName(anyString())).thenCallRealMethod();

		StringWriter out = new StringWriter();
		when(config.outWriter(anyString())).thenReturn(out);

		generator.start();

		String sourceCode = out.toString().trim();
		StringTokenizer sourceTokenizer = new StringTokenizer(sourceCode, "\n");
		String declaration = null;
		String load = null;
		while (sourceTokenizer.hasMoreTokens()) {
			String line = sourceTokenizer.nextToken();
			if (line.contains("Properties") &&
					!line.contains("import") &&
					!line.contains("enum") &&
					!line.contains("@link") &&
					!line.contains("class")) {
				declaration = line;
			} else if (line.contains(".load")) {
				load = line;
			}
		}
		assertNotNull(declaration);
		assertNotNull(load);

		assertThat(declaration,
				Matchers.stringContainsInOrder(Arrays.asList("private", "static", "Properties", "properties", "=", "new Properties()", ";")));

		assertThat(load,
				Matchers.containsString("properties.load(Application_Properties.class.getResourceAsStream(\"/application.properties\"));"));

	}

	@Test
	void testOneInputGeneratesOneOutput() throws IOException, GeneratorException {

		Writer out = mock(Writer.class);
		Writer nonOut = mock(Writer.class);
		when(config.outWriter(anyString())).thenReturn(out, nonOut);
		generator.start();
		verify(out).close();
		verify(nonOut, never()).close();
		verify(nonOut, never()).flush();
	}

	@Test
	void testKeys() throws IOException, GeneratorException {

		StringWriter out = new StringWriter();
		when(config.outWriter(anyString())).thenReturn(out);
		generator.start();

		List<String> lines = out.toString().lines().filter(line -> line.contains(" (\"")).collect(Collectors.toList());

		assertEquals(22, lines.size());
		assertLineMatch(lines, "label", "label");
		assertLineMatch(lines, "label_addarticle", "label.addarticle");
		assertLineMatch(lines, "label_user_register", "label.user.register");
		assertLineMatch(lines, "message_article_priceerror", "message.article.priceerror");
		assertLineMatch(lines, "message_invoiceitem_startbeforeend", "message.invoiceitem.startbeforeend");
		assertLineMatch(lines, "message_invoiceitem_allfieldsmustbeset", "message.invoiceitem.allfieldsmustbeset");
	}

	private void assertLineMatch(List<String> lines, String key, String expected) {
		Optional<String> found = lines.stream().filter(line -> keyMatches(line, key)).findFirst();

		assertTrue(found.isPresent(), "No line found with key = " + key);
		final String line = found.get().trim();
		int indexEquals = line.indexOf('(');
		String value = line.substring(indexEquals + 1).trim().substring(1);
		value = value.substring(0, value.length() - 3);
		assertEquals(expected, value, "Line \"" + line + "\" don't match expected Value \"" + expected + "\"");

	}

	private boolean keyMatches(String line, String key) {
		line = line.toLowerCase();
		key = key.toLowerCase();
		return line.contains("\t" + key + " ");
	}

}
