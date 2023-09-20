package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.service.UserCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
class UserCalculationServiceImpl implements UserCalculationService {

    private static final BigDecimal NUMERATOR = new BigDecimal("6.0");
    private static final int DENOMINATOR_MULTIPLIER = 2;
    private static final int SCALE = 8;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

    @Override
    public double calculateUserCalculations(int followers, int publicRepos) {
        if (followers == 0) {
            return 0.0;
        }

        BigDecimal denominator = new BigDecimal(followers * (DENOMINATOR_MULTIPLIER + publicRepos));

        BigDecimal result = NUMERATOR.divide(denominator, SCALE, ROUNDING_MODE);

        return result.doubleValue();
    }
}