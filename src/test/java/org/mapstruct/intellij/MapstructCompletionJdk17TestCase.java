/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.intellij;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecordComponent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.intellij.testutil.TestUtils.createVariable;

/**
 * @author Filip Hrisafov
 */
public class MapstructCompletionJdk17TestCase extends MapstructBaseCompletionTestCase {

    @Override
    protected String getTestDataPath() {
        return "testData/mapping";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        addDirectoryToProject( "dto" );
    }

    @Override
    protected LanguageLevel getLanguageLevel() {
        return LanguageLevel.JDK_17;
    }

    private void assertCarDtoRecordAutoComplete() {
        assertThat( myItems )
            .extracting( LookupElement::getLookupString )
            .containsExactlyInAnyOrder(
                "make",
                "seatCount",
                "manufacturingYear",
                "myDriver",
                "passengers",
                "price",
                "category",
                "available"
            );

        assertThat( myItems )
            .extracting( LookupElementPresentation::renderElement )
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactlyInAnyOrder(
                createVariable( "make", "String" ),
                createVariable( "seatCount", "int" ),
                createVariable( "manufacturingYear", "String" ),
                createVariable( "myDriver", "PersonDto" ),
                createVariable( "passengers", "List<PersonDto>" ),
                createVariable( "price", "Long" ),
                createVariable( "category", "String" ),
                createVariable( "available", "boolean" )
            );
    }

    private void assertCarRecordAutoComplete() {
        assertThat( myItems )
            .extracting( LookupElement::getLookupString )
            .containsExactlyInAnyOrder(
                "make",
                "numberOfSeats",
                "manufacturingDate",
                "driver",
                "passengers",
                "price",
                "category",
                "free"
            );

        assertThat( myItems )
            .extracting( LookupElementPresentation::renderElement )
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactlyInAnyOrder(
                createVariable( "make", "String" ),
                createVariable( "numberOfSeats", "int" ),
                createVariable( "manufacturingDate", "Date" ),
                createVariable( "driver", "Person" ),
                createVariable( "passengers", "List<Person>" ),
                createVariable( "price", "int" ),
                createVariable( "category", "Category" ),
                createVariable( "free", "boolean" )
            );
    }

    public void testCarMapperReturnTargetCarDtoRecord() {
        myFixture.copyFileToProject( "CarDtoRecord.java", "org/example/dto/CarDtoRecord.java" );
        configureByTestName();
        assertCarDtoRecordAutoComplete();
    }

    public void testTargetPropertyReferencesRecordComponent() {
        myFixture.copyFileToProject( "CarDtoRecord.java", "org/example/dto/CarDtoRecord.java" );
        myFixture.configureByFile( "CarMapperReferenceRecordTargetProperty.java" );
        PsiElement reference = myFixture.getElementAtCaret();
        assertThat( reference )
            .isInstanceOfSatisfying( PsiRecordComponent.class, recordComponent -> {
                assertThat( recordComponent.getName() ).isEqualTo( "seatCount" );
                assertThat( recordComponent.getType() ).isNotNull();
                assertThat( recordComponent.getType().getPresentableText() ).isEqualTo( "int" );
            } );
    }

    public void testCarMapperSimpleSingleSourceCarRecord() {
        myFixture.copyFileToProject( "CarRecord.java", "org/example/dto/CarRecord.java" );
        configureByTestName();
        assertCarRecordAutoComplete();
    }

    public void testSourcePropertyReferencesRecordComponent() {
        myFixture.copyFileToProject( "CarRecord.java", "org/example/dto/CarRecord.java" );
        myFixture.configureByFile( "CarMapperReferenceRecordSourceProperty.java" );
        PsiElement reference = myFixture.getElementAtCaret();
        assertThat( reference )
            .isInstanceOfSatisfying( PsiRecordComponent.class, recordComponent -> {
                assertThat( recordComponent.getName() ).isEqualTo( "numberOfSeats" );
                assertThat( recordComponent.getType() ).isNotNull();
                assertThat( recordComponent.getType().getPresentableText() ).isEqualTo( "int" );
            } );
    }
}