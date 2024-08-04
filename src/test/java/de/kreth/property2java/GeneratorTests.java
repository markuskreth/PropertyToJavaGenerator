package de.kreth.property2java;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.kreth.property2java.processor.Format;

class GeneratorTests {

	private String path = "application.properties";

	private Configuration config;

	private Generator generator;

	@BeforeEach
	void setUp() throws Exception {
		Map<String, Reader> input = new HashMap<>();
		input.put(path, testProperties());

		config = Mockito.spy(TestImplConfig.class);
		
		when(config.getRootPath()).thenReturn(new File(".").toPath());
		when(config.getFormat()).thenReturn(Format.WithUnaryOperatorParameter);
		when(config.getInput()).thenReturn(input);
		when(config.mapFilenameToClassName(anyString())).thenCallRealMethod();
		when(config.outputCharset()).thenCallRealMethod();

		generator = new Generator(config);
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

		assertEquals(21, lines.size());
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

	private StringReader testProperties() {
		return new StringReader("\r\n" + "label = \r\n" + "\r\n" + "label.addarticle    = Add Article\r\n"
				+ "label.cancel        = Cancel\r\n" + "label.close         = Close\r\n"
				+ "label.delete        = Delete\r\n" + "label.discart       = Discart\r\n"
				+ "label.loggedin      = Logged in:\r\n" + "label.logout        = Logout\r\n"
				+ "label.ok            = OK\r\n" + "label.store         = Store\r\n"
				+ "label.preview       = Preview\r\n" + "label.open          = Open\r\n"
				+ "label.user.register = Register\r\n" + "\r\n"
				+ "message.article.priceerror             = Please set the price.\r\n"
				+ "message.delete.text                    = Delete {0}?\r\n"
				+ "message.delete.title                   = Really delete?\r\n"
				+ "message.invoiceitem.allfieldsmustbeset = Start, end and article must not be \\r\\n"
				+ "                                         empty!\r\n"
				+ "message.invoiceitem.startbeforeend     = End must be later than start.\r\n"
				+ "message.user.create.success            = Thanks {0} created!\r\n"
				+ "message.user.loginfailure              = Login Error! Wrong user or password?\r\n"
				+ "message.user.passwordmissmatch         = Passwords don't match.\r\n" + "");
	}
}
