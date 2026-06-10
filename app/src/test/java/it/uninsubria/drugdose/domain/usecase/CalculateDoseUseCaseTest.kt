package it.uninsubria.drugdose.domain.usecase

import it.uninsubria.drugdose.domain.model.*
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.sqrt

class CalculateDoseUseCaseTest {

    private val useCase = CalculateDoseUseCase()

    @Test
    fun `calculate dose PER_KG returns correct result`() {
        val patient = Patient(weightKg = 70.0)
        val drug = Drug(
            id = "test", name = "Test", indication = "Test",
            formulaType = FormulaType.PER_KG, unitDose = 15.0, unit = "mg"
        )
        
        val result = useCase(patient, drug)
        assertEquals(1050.0, result, 0.001)
    }

    @Test
    fun `calculate dose PER_BSA returns correct result using Mosteller`() {
        // Patient: 170cm, 70kg
        val weight = 70.0
        val height = 170.0
        val unitDose = 50.0
        
        // Manual calculation for expected value
        val expectedBsa = sqrt((height * weight) / 3600.0)
        val expectedDose = expectedBsa * unitDose
        
        val patient = Patient(weightKg = weight, heightCm = height)
        val drug = Drug(
            id = "test", name = "Test", indication = "Test",
            formulaType = FormulaType.PER_BSA, unitDose = unitDose, unit = "mg"
        )
        
        val result = useCase(patient, drug)
        
        // Let's print to see the actual value if it fails again
        println("Expected: $expectedDose, Actual: $result")
        
        assertEquals(expectedDose, result, 0.001)
    }

    @Test
    fun `calculate dose FIXED returns unit dose`() {
        val patient = Patient(weightKg = 100.0)
        val drug = Drug(
            id = "test", name = "Test", indication = "Test",
            formulaType = FormulaType.FIXED, unitDose = 1.0, unit = "compressa"
        )
        
        val result = useCase(patient, drug)
        assertEquals(1.0, result, 0.001)
    }

    @Test
    fun `calculate dose WEIGHT_RANGE returns correct dose for range`() {
        val drug = Drug(
            id = "test", name = "Test", indication = "Test",
            formulaType = FormulaType.WEIGHT_RANGE, unitDose = 0.0, unit = "mg",
            weightRanges = listOf(
                WeightRange(10.0, 20.0, 50.0),
                WeightRange(20.1, 40.0, 100.0)
            )
        )
        
        assertEquals(50.0, useCase(Patient(weightKg = 15.0), drug), 0.001)
        assertEquals(100.0, useCase(Patient(weightKg = 30.0), drug), 0.001)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `calculate dose throws exception when age is below minimum`() {
        val patient = Patient(weightKg = 20.0, ageYears = 4)
        val drug = Drug(
            id = "test", name = "Test", indication = "Test",
            formulaType = FormulaType.FIXED, unitDose = 10.0, unit = "mg",
            minAgeYears = 6
        )
        
        useCase(patient, drug)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `calculate dose throws exception when weight is below minimum`() {
        val patient = Patient(weightKg = 10.0, ageYears = 10)
        val drug = Drug(
            id = "test", name = "Test", indication = "Test",
            formulaType = FormulaType.PER_KG, unitDose = 10.0, unit = "mg",
            minWeightKg = 15.0
        )
        
        useCase(patient, drug)
    }

    @Test
    fun `calculate dose with missing parameters returns zero when no safety constraints`() {
        val drugKg = Drug(
            id = "test", name = "Test", indication = "Test",
            formulaType = FormulaType.PER_KG, unitDose = 10.0, unit = "mg"
        )
        assertEquals(0.0, useCase(Patient(weightKg = null), drugKg), 0.001)
    }
}
