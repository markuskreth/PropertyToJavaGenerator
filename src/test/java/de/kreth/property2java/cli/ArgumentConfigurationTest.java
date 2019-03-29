package de.kreth.property2java.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.kreth.property2java.Configuration;

class ArgumentConfigurationTest {

	private static File target;

	private static File input;

	private static File input2;

	@BeforeAll
	static void createTargetDir() throws IOException {
		target = new File("testTargetDir");
		target.mkdir();
		input = new File(target, "application.properties");
		input.createNewFile();
		input2 = new File(target, "application2.properties");
		input2.createNewFile();
	}

	@AfterAll
	static void deleteTestfiles() {
		target.delete();
		input.delete();
		input2.delete();
	}

	@Test
	void testArgumentParser() throws IOException {

		String[] args = { "-t", target.getAbsolutePath(),
				"-f",
				input.getAbsolutePath() + "," + input2.getAbsolutePath(),
				"-p", "de.kreth.clubinvoice" };
		Configuration config = ArgumentConfiguration.parse(args);
		assertEquals("de.kreth.clubinvoice", config.getPackage());
		assertEquals(target.getAbsolutePath(), config.getRootPath().toFile().getAbsolutePath());
		Map<String, Reader> inputMap = config.getInput();
		assertEquals(2, inputMap.size());
	}

}
