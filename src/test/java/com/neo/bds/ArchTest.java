package com.neo.bds;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.neo.bds");

        noClasses()
            .that()
                .resideInAnyPackage("com.neo.bds.service..")
            .or()
                .resideInAnyPackage("com.neo.bds.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.neo.bds.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
