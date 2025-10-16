package de.kreth.property2java;

import static de.kreth.property2java.TestPropertiesSource.testProperties;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


import org.apache.commons.cli.MissingOptionException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GeneratorTests {

	private final String path = "application.properties";

	@Mock
	private Configuration config;

	@BeforeEach
	void setUp() throws Exception {
		Map<String, Reader> input = new HashMap<>();
		input.put(path, testProperties());

		when(config.getRootPath()).thenReturn(new File(".").toPath());
		when(config.getFormat()).thenReturn(Format.WithUnaryOperatorParameter);
		when(config.getInput()).thenReturn(input);
		when(config.mapFilenameToClassName(anyString())).thenCallRealMethod();
		when(config.outputCharset()).thenCallRealMethod();
		when(config.getOptions()).thenReturn(EnumSet.noneOf(GeneratorOptions.class));

	}

	@Test
	void testAllOptionsConfiguration() throws IOException, GeneratorException, TemplateException {

		Template template = mock(Template.class);
		Generator generator = new Generator(config, template);
		when(config.getOptions()).thenReturn(EnumSet.allOf(GeneratorOptions.class));

		StringWriter out = new StringWriter();
		when(config.outWriter(anyString())).thenReturn(out);

		@SuppressWarnings("unchecked")
		ArgumentCaptor<Map<String, Object>> rootCaptior = ArgumentCaptor.forClass(Map.class);
		generator.start();
		verify(template).process(rootCaptior.capture(), any(Writer.class));
		Map<String, Object> root = rootCaptior.getValue();
		@SuppressWarnings("unchecked")
		EnumSet<GeneratorOptions> options = (EnumSet<GeneratorOptions>) root.get("options");
		assertThat(options).contains(GeneratorOptions.WithMessageFormatter, GeneratorOptions.WithSubstitutors);

		@SuppressWarnings("unchecked")
		List<String> imports = (List<String>) root.get("imports");
		assertThat(imports).contains("java.text.MessageFormat");
	}

	@Test
	void testWithSubstitutorsNoImportsConfiguration() throws IOException, GeneratorException, TemplateException {

		Template template = mock(Template.class);
		Generator generator = new Generator(config, template);
		when(config.getOptions()).thenReturn(EnumSet.of(GeneratorOptions.WithSubstitutors));

		StringWriter out = new StringWriter();
		when(config.outWriter(anyString())).thenReturn(out);

		@SuppressWarnings("unchecked")
		ArgumentCaptor<Map<String, Object>> rootCaptior = ArgumentCaptor.forClass(Map.class);
		generator.start();
		verify(template).process(rootCaptior.capture(), any(Writer.class));
		Map<String, Object> root = rootCaptior.getValue();
		@SuppressWarnings("unchecked")
		EnumSet<GeneratorOptions> options = (EnumSet<GeneratorOptions>) root.get("options");
		assertThat(options).contains(GeneratorOptions.WithSubstitutors);

		assertFalse(root.containsKey("imports"));
	}

	@Test
	void testClassDefinition() throws IOException, GeneratorException {

		Generator generator = new Generator(config);
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
	void testOneInputGeneratesOneOutput() throws IOException, GeneratorException {

		Generator generator = new Generator(config);
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

		Generator generator = new Generator(config);
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

	@Test
	void testTemplateException() throws TemplateException, IOException {
		Template template = mock(Template.class);

		when(config.outWriter(anyString())).thenReturn(mock(Writer.class));
		doThrow(new TemplateException(null)).when(template).process(any(Map.class), any(Writer.class));

		Generator generator = new Generator(config, template);
		GeneratorException ex = assertThrows(GeneratorException.class, generator::start);
		assertThat(ex.getCause()).isInstanceOf(TemplateException.class);
	}

	@Test
	void testMainMethod() throws IOException, GeneratorException {
		Path source = Files.createTempFile(getClass().getSimpleName(), ".properties");
		Generator.main(new String[]{"-t", "target", "-f", source.toString()});
	}

	@Test
	void testMainMethodMissingOption() throws IOException, GeneratorException {
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> Generator.main(new String[]{}));
		assertThat(e.getCause()).isInstanceOf(MissingOptionException.class);
	}

	@Test
	void testGenerateFor() throws IOException, GeneratorException {
		Class<?> locationClass = getClass();
		List<URL> rescources = new ArrayList<>();
		String relativeTargetDir = "target";
		Generator.generateFor(locationClass, rescources, relativeTargetDir);
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
