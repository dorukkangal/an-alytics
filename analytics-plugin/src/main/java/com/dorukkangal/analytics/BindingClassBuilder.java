package com.dorukkangal.analytics;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Generates a B class that contains all supported field names in target R file as final values.
 *
 * @author Doruk Kangal
 * @since 2.0.0
 */
public final class BindingClassBuilder {

    private static final String className = "B";
    private static final String[] SUPPORTED_TYPES = {"string"};

    private BindingClassBuilder() {
    }

    static void brewJava(File resourceFile, File outputFile, String packageName)
            throws ParseException, IOException {

        CompilationUnit cu = JavaParser.parse(resourceFile);

        TypeDeclaration resourceClass = cu.getTypes().get(0);

        TypeSpec.Builder result = TypeSpec.classBuilder(className)
                .addModifiers(PUBLIC)
                .addModifiers(FINAL);

        List<String> supportedTypes = Arrays.asList(SUPPORTED_TYPES);

        for (Node node : resourceClass.getChildrenNodes()) {
            if (node instanceof TypeDeclaration) {
                addResourceType(supportedTypes, result, (TypeDeclaration) node);
            }
        }

        JavaFile bFile = JavaFile.builder(packageName, result.build())
                .addFileComment("Generated code from An-alytics. Do not modify!")
                .build();

        bFile.writeTo(outputFile);
    }

    private static void addResourceType(List<String> supportedTypes, TypeSpec.Builder result,
                                        TypeDeclaration node) {

        if (!supportedTypes.contains(node.getName())) {
            return;
        }

        String type = node.getName();
        TypeSpec.Builder resourceType = TypeSpec.classBuilder(type)
                .addModifiers(PUBLIC, STATIC, FINAL);

        for (BodyDeclaration field : node.getMembers()) {
            if (field instanceof FieldDeclaration) {
                addResourceField(resourceType, ((FieldDeclaration) field).getVariables().get(0));
            }
        }

        result.addType(resourceType.build());
    }

    private static void addResourceField(TypeSpec.Builder resourceType, VariableDeclarator variable) {

        String fieldName = variable.getId().getName();
        FieldSpec fieldSpec = FieldSpec.builder(String.class, fieldName)
                .addModifiers(PUBLIC, STATIC, FINAL)
                .initializer("$S", fieldName)
                .build();

        resourceType.addField(fieldSpec);
    }
}
