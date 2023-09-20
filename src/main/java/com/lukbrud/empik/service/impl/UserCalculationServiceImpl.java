package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.service.UserCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Service implementation for calculating user-specific values.
 * This service calculates user calculations based on provided data.
 *
 * @author Lukasz Brudniak
 */
@Service
class UserCalculationServiceImpl implements UserCalculationService {

    /**
     * Calculate user-specific calculations.
     *
     * @param followers    The number of followers the user has.
     * @param publicRepos   The number of public repositories the user has.
     * @return The calculated user-specific value.
     */
    @Override
    public double calculateUserCalculations(int followers, int publicRepos) {
        if (followers == 0) {
            return 0.0;
        }

        BigDecimal numerator = new BigDecimal("6.0");
        BigDecimal denominator = new BigDecimal(followers * (2 + publicRepos));

        BigDecimal result = numerator.divide(denominator, 8, RoundingMode.DOWN);

        return result.doubleValue();
    }
}