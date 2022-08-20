package de.kreth.property2java.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Diese Annotation sollte nicht verwendet werden. Sie sammelt nur
 * {@link GenerateResourceBundleProperty2Java} wenn diese mehrfach verwendet
 * wird.
 *
 * @author Markus
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateResourceBundleProperty2Javas {

    GenerateResourceBundleProperty2Java[] value();
}
