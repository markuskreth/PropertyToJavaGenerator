package de.kreth.property2java.generated;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import de.kreth.property2java.Configuration;
import de.kreth.property2java.Format;
import de.kreth.property2java.Generator;
import de.kreth.property2java.GeneratorException;
import de.kreth.property2java.GeneratorOptions;
import de.kreth.property2java.TestPropertiesSource;

public class GenerateTheTest {

	public static final String PROPERTY_LOADER_PROPERTIES = "property_loader.properties";
	public static final String PROPERTY_LOADER_OPTIONS_PROPERTIES = "property_loader_options.properties";
	public static final String UNARY_OPERATOR_PROPERTIES = "unary_operator.properties";
	public static final String RESOURCE_BUNDLE = "resource_bundle.properties";
	
	public static void main(String[] args) throws IOException, GeneratorException {
		Path current = Paths.get(".", "src", "test", "java", "de", "kreth", "property2java", "generated").toAbsolutePath().normalize();
		System.out.println(current);
		
		withUnaryOperatorParameter(current);
		withInnerPropertyLoader(current);
		withResourceBundle(current);
		withInnerPropertyLoaderAndSubstitutors(current);
	}

	private static void withResourceBundle(Path current) throws IOException, GeneratorException {
		Format format = Format.WithInnerPropertyResourceBundle;
		Map<String, Reader> input = new HashMap<>();
		input.put(RESOURCE_BUNDLE, TestPropertiesSource.testProperties());
		System.out.println("Generating: " + RESOURCE_BUNDLE);
		
		Configuration config = new Configuration() {
			
			@Override
			public Path getRootPath() {
				return current;
			}
			
			@Override
			public String getPackage() {
				return "de.kreth.property2java.generated";
			}
			
			@Override
			public Format getFormat() {
				return format;
			}
			
			@Override
			public Map<String, Reader> getInput() {
				return input;
			}
		};
		Generator generator = new Generator(config);
		generator.start();
	}

	private static void withUnaryOperatorParameter(Path current) throws IOException, GeneratorException {
		Format format = Format.WithUnaryOperatorParameter;
		Map<String, Reader> input = new HashMap<>();
		input.put(UNARY_OPERATOR_PROPERTIES, TestPropertiesSource.testProperties());

		System.out.println("Generating: " + UNARY_OPERATOR_PROPERTIES);
		
		Configuration config = new Configuration() {
			
			@Override
			public Path getRootPath() {
				return current;
			}
			
			@Override
			public String getPackage() {
				return "de.kreth.property2java.generated";
			}
			
			@Override
			public Format getFormat() {
				return format;
			}
			
			@Override
			public Map<String, Reader> getInput() {
				return input;
			}
		};
		Generator generator = new Generator(config);
		generator.start();
	}

	private static void withInnerPropertyLoader(Path current) throws IOException, GeneratorException {
		Format format = Format.WithInnerPropertyLoader;
		Map<String, Reader> input = new HashMap<>();
		input.put(PROPERTY_LOADER_PROPERTIES, TestPropertiesSource.testProperties());

		System.out.println("Generating: " + PROPERTY_LOADER_PROPERTIES);
		
		Configuration config = new Configuration() {
			
			@Override
			public Path getRootPath() {
				return current;
			}
			
			@Override
			public String getPackage() {
				return "de.kreth.property2java.generated";
			}
			
			@Override
			public Format getFormat() {
				return format;
			}
			
			@Override
			public Map<String, Reader> getInput() {
				return input;
			}
		};
		Generator generator = new Generator(config);
		generator.start();
	}

	private static void withInnerPropertyLoaderAndSubstitutors(Path current) throws IOException, GeneratorException {
		Format format = Format.WithInnerPropertyLoader;
		Map<String, Reader> input = new HashMap<>();
		input.put(PROPERTY_LOADER_OPTIONS_PROPERTIES, TestPropertiesSource.testProperties());

		System.out.println("Generating: " + PROPERTY_LOADER_OPTIONS_PROPERTIES);
		
		Configuration config = new Configuration() {
			
			@Override
			public Path getRootPath() {
				return current;
			}
			
			@Override
			public String getPackage() {
				return "de.kreth.property2java.generated";
			}
			
			@Override
			public Format getFormat() {
				return format;
			}
			
			@Override
			public EnumSet<GeneratorOptions> getOptions() {
				return EnumSet.allOf(GeneratorOptions.class);
			}
			
			@Override
			public Map<String, Reader> getInput() {
				return input;
			}
		};
		Generator generator = new Generator(config);
		generator.start();
	}

}
