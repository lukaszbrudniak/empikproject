package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.service.UserCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserCalculationServiceImplTest {

    @InjectMocks
    private UserCalculationService userCalculationService = new UserCalculationServiceImpl();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({"10, 5, 0.08571428", "0, 5, 0.0", "20, 0, 0.15"})
    public void shouldCalculateUserCalculations(int followers, int publicRepos, String expected) {
        // Given
        double expectedResult = new BigDecimal(expected).doubleValue();

        // When
        double result = userCalculationService.calculateUserCalculations(followers, publicRepos);

        // Then
        assertEquals(expectedResult, result, 0.00000001);
    }
}