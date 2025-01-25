package de.kreth.property2java.processor;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.kreth.property2java.Format;
import de.kreth.property2java.GeneratorOptions;

@Target(TYPE)
@Retention(RetentionPolicy.SOURCE)
/**
 * Für die konfigurierten Resourcen wird jeweils eine Java Klasse erzeugt. Es
 * muss nur die Abhängigkeit eingebunden werden und die Annotation in einer
 * Klasse verwendet werden, in deren Package die neuen Klassen generiert werden.
 *
 * 
 * 
 * Für die Ausgabe der Prozessornachrichten muss folgendes im maven compiler
 * konfiguriert werden:
 *
 * <pre>
&lt;build&gt;
	&lt;plugins&gt;
		&lt;plugin&gt;
			&lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
			&lt;artifactId&gt;maven-compiler-plugin&lt;/artifactId&gt;
			&lt;version&gt;3.8.0&lt;/version&gt;
			&lt;configuration&gt;
				&lt;release&gt;${java.version}&lt;/release&gt;
				<b>&lt;showWarnings&gt;true&lt;/showWarnings&gt;</b>
			&lt;/configuration&gt;
		&lt;/plugin&gt;
	&lt;/plugins&gt;
&lt;/build&gt;
 * </pre>
 *
 * @author Markus
 *
 */
public @interface GenerateProperty2Java {
	String[] resources();

	Format format() default Format.WithUnaryOperatorParameter;

	GeneratorOptions[] options() default {};
}
