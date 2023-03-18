package org.jfree.data;

import static org.junit.Assert.*; import org.jfree.data.Range; import org.junit.*;

public class RangeTest {
    private static Range pureNegativeRange; // A range with entirely negative values
    private static Range crossRange; // A range with both negative and positive values
    private static Range purePositiveRange; // A range with entirely positive values
    private static Range nullRange; // A null range
    
    private Range exampleRangeNull;
	private Range exampleRangeSmall;
	private Range exampleRangePosSmall;
	private Range exampleRangeNegSmall;
	private Range exampleRangeLarge;
	private Range exampleRangePosLarge;
	private Range exampleRangeNegLarge;
	private Range exampleRangeDec;
	private Range exampleRangePosDec;
	private Range exampleRangeNegDec;
    
    @BeforeClass 
    // Setup the Ranges to be used during testing
    public static void setUpBeforeClass() throws Exception {
    	pureNegativeRange = new Range(-5, -2);
    	crossRange = new Range(-2, 2);
    	purePositiveRange = new Range(2, 5);
    	nullRange = null;
    }
    
    @Before
	public void setUp() throws Exception {
		exampleRangeNull = null;
		exampleRangeSmall = new Range(-1, 0);
		exampleRangePosSmall = new Range (1,1);
		exampleRangeNegSmall = new Range(-2,-1);
		exampleRangeLarge = new Range(-1000000, 1000000);
		exampleRangePosLarge = new Range(0, 1000000);
		exampleRangeNegLarge = new Range(-1000000, -1);
		exampleRangeDec = new Range(-1.11111,1.111111);
		exampleRangePosDec = new Range(1.11111,14.11200);
		exampleRangeNegDec = new Range(-1.11111,-0.111111);
	}
    
    // Improving coverage
	
	@Test
    //Central value of 0 and 1 should be 0.5
    public void getCentralValueIncludesZero() {
    	Range small = new Range(0,1);
    	assertEquals(0.5, small.getCentralValue(), .000000001d);
    }
    
    @Test (expected = IllegalArgumentException.class)
    // Ensure that the proper exception is thrown when calling the constructor with a greater lower bound
    public void callContructorWithGreaterLowerBound() throws IllegalArgumentException {
    	Range result = new Range(10, 5);
    } 
    
    @Test
    // Ensure that getCentralValue() returns the correct value
    public void getCentralValuePositiveRange(){
    	double expectedCentral = 3.5;
    	
    	double result = purePositiveRange.getCentralValue();
    	
    	assertEquals("Central value was not correct", expectedCentral, result, .000000001d);
    } 
    
    @Test
    // Ensure that contains() functions properly for a value between the bounds
    public void containsValueWithinRange(){
    	boolean expected = true;
    	
    	boolean actual = purePositiveRange.contains(3);
    	
    	assertEquals("Contains result was not correct", expected, actual);
    } 
    
    @Test
    // Ensure that intersects() returns the correct value for b0 < lower bound and b1 > lower bound
    public void intersectsB0LessAndB1GreaterThanLowerBound(){
    	boolean expected = true;
    	
    	boolean actual = purePositiveRange.intersects(-2, 3);
    	
    	assertEquals("Contains result was not correct", expected, actual);
    } 
    
    @Test
    // Ensure that intersects() returns the correct value for b0 < lower bound and b1 < lower bound
    public void intersectsB0LessAndB1LessThanLowerBound(){
    	boolean expected = false;
    	
    	boolean actual = purePositiveRange.intersects(-2, 0);
    	
    	assertEquals("Contains result was not correct", expected, actual);
    } 
    
    @Test
    // Ensure that intersects() returns the correct value for b0 > lower bound and b1 > lower bound
    public void intersectsB0GreaterAndB1GreaterThanLowerBound(){
    	boolean expected = true;
    	
    	boolean actual = purePositiveRange.intersects(3, 6);
    	
    	assertEquals("Contains result was not correct", expected, actual);
    } 
    
    @Test
    // Ensure that intersects() returns the correct value for b0 > lower bound and b1 < b0
    public void intersectsB0LessThanUpperAndB1LessThanB0(){
    	boolean expected = false;
    	
    	boolean actual = purePositiveRange.intersects(3, 2);
    	
    	assertEquals("Contains result was not correct", expected, actual);
    } 
    
    @Test
    // Ensure that intersects() returns the correct value for b0 > upper bound and b1 > b0
    public void intersectsB0GreaterThanUpperAndB1GreaterThanB0(){
    	boolean expected = false;
    	
    	boolean actual = purePositiveRange.intersects(6, 8);
    	
    	assertEquals("Contains result was not correct", expected, actual);
    } 
    
    @Test
    // Ensure that intersects() returns the correct value for a range that does intersect
    public void intersectsRangeThatDoesIntersect(){
    	boolean expected = true;
    	
    	boolean actual = purePositiveRange.intersects(new Range(4, 8));
    	
    	assertEquals("Contains result was not correct", expected, actual);
    } 
    
    @Test
    // Ensure that intersects() returns the correct value for a range that doesn't intersect
    public void intersectsRangeThatDoesNotIntersect(){
    	boolean expected = false;
    	
    	boolean actual = purePositiveRange.intersects(new Range(7, 10));
    	
    	assertEquals("Contains result was not correct", expected, actual);
    } 
    
    @Test
    // Ensure that combine returns range2 when range1 is null
    public void combineRange1Null(){
    	Range expected = purePositiveRange;
    	
    	Range actual = Range.combine(null, purePositiveRange);
    	
    	assertEquals("Combine returned the incorrect Range", expected, actual);
    } 
    
    @Test
    // Ensure that combine returns range1 when range2 is null
    public void combineRange2Null(){
    	Range expected = purePositiveRange;
    	
    	Range actual = Range.combine(purePositiveRange, null);
    	
    	assertEquals("Combine returned the incorrect Range", expected, actual);
    } 
    
    @Test
    // Ensure that combine returns the proper range when both ranges are not null
    public void combineBothRangesNotNull(){
    	double expectedLowerBound = -5;
    	
    	Range result = Range.combine(purePositiveRange, pureNegativeRange);
    	
    	assertEquals("Combine returned the incorrect Range", expectedLowerBound, result.getLowerBound(), .000000001d);
    } 
    
    @Test
    // Ensure that combineIgnoringNaN returns the proper range when range1 is null
    public void combineIgnoringNaNRange1Null(){
    	Range expected = purePositiveRange;
    	
    	Range actual = Range.combineIgnoringNaN(null, purePositiveRange);
    	
    	assertEquals("Combine returned the incorrect Range", expected, actual);
    } 
    
    @Test
    // Ensure that combineIgnoringNaN returns the proper range when range1 is null and range2 is NaN
    public void combineIgnoringNaNRange1NullRange2NaN(){
    	Range expected = null;
    	
    	Range actual = Range.combineIgnoringNaN(null, new Range(Double.NaN, Double.NaN));
    	
    	assertEquals("Combine returned the incorrect Range", expected, actual);
    } 
    
    @Test
    // Ensure that combineIgnoringNaN returns the proper range when both ranges are null
    public void combineIgnoringNaNBothRangesNull(){
    	Range expected = null;
    	
    	Range actual = Range.combineIgnoringNaN(null, null);
    	
    	assertEquals("Combine returned the incorrect Range", expected, actual);
    } 
    
    @Test
    // Ensure that combineIgnoringNaN returns the proper range when range2 is null
    public void combineIgnoringNaNRange2Null(){
    	Range expected = purePositiveRange;
    	
    	Range actual = Range.combineIgnoringNaN(purePositiveRange, null);
    	
    	assertEquals("Combine returned the incorrect Range", expected, actual);
    } 
    
    @Test
    // Ensure that combineIgnoringNaN returns the proper range when range2 is null and range1 is NaN
    public void combineIgnoringNaNRange2NullRange1NaN(){
    	Range expected = null;
    	
    	Range actual = Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), null);
    	
    	assertEquals("Combine returned the incorrect Range", expected, actual);
    } 
    
    @Test
    // Ensure that combine returns the proper range when both ranges are not null and have no NaN bounds
    public void combineIgnoringNaNBothRangesNotNull(){
    	double expectedLowerBound = -5;
    	
    	Range result = Range.combineIgnoringNaN(purePositiveRange, pureNegativeRange);
    	
    	assertEquals("Combine returned the incorrect Range", expectedLowerBound, result.getLowerBound(), .000000001d);
    } 
    
    @Test
    // Ensure that combine returns the proper range when both lower bounds are NaN 
    public void combineIgnoringNaNBothLowerBoundsAreNaN(){
    	double expectedLowerBound = Double.NaN;
    	
    	Range result = Range.combineIgnoringNaN(new Range(Double.NaN, 8), new Range(Double.NaN, 10));
    	
    	assertEquals("Combine returned the incorrect Range", expectedLowerBound, result.getLowerBound(), .000000001d);
    } 
    
    @Test
    // Ensure that combine returns the proper range when both upper bounds are NaN 
    public void combineIgnoringNaNBothUpperBoundsAreNaN(){
    	double expectedUpperBound = Double.NaN;
    	
    	Range result = Range.combineIgnoringNaN(new Range(8, Double.NaN), new Range(10, Double.NaN));
    	
    	assertEquals("Combine returned the incorrect Range", expectedUpperBound, result.getUpperBound(), .000000001d);
    } 
    
    @Test
    // Ensure that combine returns the proper range when one upper bound is NaN 
    public void combineIgnoringNaNOneUpperBoundIsNaN(){
    	double expectedUpperBound = 11;
    	
    	Range result = Range.combineIgnoringNaN(new Range(8, 11), new Range(10, Double.NaN));
    	
    	assertEquals("Combine returned the incorrect Range", expectedUpperBound, result.getUpperBound(), .000000001d);
    } 
    
    @Test
    // Ensure that combine returns the proper range when one lower bound is NaN 
    public void combineIgnoringNaNOneLowerBoundIsNaN(){
    	double expectedLower = 8;
    	
    	Range result = Range.combineIgnoringNaN(new Range(8, 11), new Range(Double.NaN, 10));
    	
    	assertEquals("Combine returned the incorrect Range", expectedLower, result.getLowerBound(), .000000001d);
    } 
    
    @Test
    // Ensure that combine returns the proper range when both ranges are NaN
    public void combineIgnoringNaNBothRangesNaN(){
    	
    	Range result = Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), new Range(Double.NaN, Double.NaN));
    	
    	assertNull("Combine returned the incorrect Range", result);
    } 
    
    @Test
    // Ensure that expandToInclude returns the correct range when the input range is null
    public void expandToIncludeInputRangeNull(){
    	double expected = 10;
    	
    	Range result = Range.expandToInclude(null, 10);
    	
    	assertEquals("expandToInclude returned the incorrect Range", expected, result.getLowerBound(), .000000001d);
    } 
    
    @Test
    // Ensure that expandToInclude returns the correct range when value is less than lower bound
    public void expandToIncludeValueLessThanLowerBound(){
    	double expected = -10;
    	
    	Range result = Range.expandToInclude(purePositiveRange, -10);
    	
    	assertEquals("expandToInclude returned the incorrect Range", expected, result.getLowerBound(), .000000001d);
    } 
    
    @Test
    // Ensure that expandToInclude returns the correct range when value is between the bounds
    public void expandToIncludeValueBetweenBounds(){
    	double expected = 2;
    	
    	Range result = Range.expandToInclude(purePositiveRange, 3);
    	
    	assertEquals("expandToInclude returned the incorrect Range", expected, result.getLowerBound(), .000000001d);
    } 
    
    @Test
    // Ensure that expandToInclude returns the correct range when value is greater than upper bound
    public void expandToIncludeValueGreaterThanUpperBound(){
    	double expected = 10;
    	
    	Range result = Range.expandToInclude(purePositiveRange, 10);
    	
    	assertEquals("expandToInclude returned the incorrect Range", expected, result.getUpperBound(), .000000001d);
    } 
    
    @Test
    // Ensure that shift with zero crossing enabled returns the correct Range
    public void shiftAllowZeroCrossingLowerBound(){
    	double expectedUpperBound = -5;
    	
    	Range result = Range.shift(purePositiveRange, -10, true);
    	
    	assertEquals("Shift returned the incorrect Range", expectedUpperBound, result.getUpperBound(), .000000001d);
    } 
    
    @Test
    // Ensure that shift with zero crossing disabled returns the correct Range when the upper bound is zero
    public void shiftAllowZeroCrossingUpperBoundEqualToZero(){
    	double expectedUpper = 5;
    	
    	Range result = Range.shift(new Range(-2, 0), 5, false);
    	
    	assertEquals("Shift returned the incorrect Range", expectedUpper, result.getUpperBound(), .000000001d);
    } 
    
    @Test (expected = IllegalArgumentException.class)
    // Ensure that scale with a negative factor throws the appropriate exception
    public void scaleWithNegativeFactor() throws IllegalArgumentException{
    	
    	Range result = Range.scale(purePositiveRange, -1);
    } 
    
    @Test
    // Ensure that scale with a positive factor returns the correct Range
    public void scaleWithPositiveFactor(){
    	double expectedUpper = 10;
    	
    	Range result = Range.scale(purePositiveRange, 2);
    	
    	assertEquals("Scale returned the incorrect Range", expectedUpper, result.getUpperBound(), .000000001d);
    } 
    
    @Test
    // Ensure that equals() returns false for an incompatible object
    public void equalsComparingToIncompatibleObject(){
    	boolean expected = false;
    	
    	boolean actual = purePositiveRange.equals(null);
    	
    	assertEquals("Equals returned the incorrect value", expected, actual);
    } 
    
    @Test
    // Ensure that equals() returns false for a range with a different lower bound
    public void equalsDifferentLowerBound(){
    	boolean expected = false;
    	
    	boolean actual = purePositiveRange.equals(new Range(3, 5));
    	
    	assertEquals("Equals returned the incorrect value", expected, actual);
    } 
    
    @Test
    // Ensure that equals() returns false for a range with a different upper bound
    public void equalsDifferentUpperBound(){
    	boolean expected = false;
    	
    	boolean actual = purePositiveRange.equals(new Range(2, 6));
    	
    	assertEquals("Equals returned the incorrect value", expected, actual);
    }  
    
    @Test
    // Ensure that isNaNRange returns the correct value for a range with NaN lower bound
    public void isNanRangeNaNLowerBound(){
    	boolean expected = false;
    	
    	Range nanLower = new Range(Double.NaN, 2);
    	
    	boolean actual = nanLower.isNaNRange();
    	
    	assertEquals("IsNaNRange returned the incorrect value", expected, actual);
    } 
    
    @Test
    // Ensure that hashCode() returns the correct hash code
    public void hashCodeTest(){
    	int expectedHash;
        long temp;
        temp = Double.doubleToLongBits(purePositiveRange.getLowerBound());
        expectedHash = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(purePositiveRange.getUpperBound());
        expectedHash = 29 * expectedHash + (int) (temp ^ (temp >>> 32));
        
        int actualHash = purePositiveRange.hashCode();
    	
    	assertEquals("hashCode returned the incorrect value", expectedHash, actualHash);
    } 
    
    @Test
    // Ensure that toString() returns a properly formatted String
    public void toStringTest(){
    	String expectedString = ("Range[" + purePositiveRange.getLowerBound() + "," + 
    			purePositiveRange.getUpperBound() + "]");
        
        String actualString = purePositiveRange.toString();
    	
    	assertTrue("toString returned the incorrect String", expectedString.equals(actualString));
    } 
    
    // Testing shift()
    
    @Test (expected = Exception.class)
    // Ensure that calling the shift() function with a null base Range throws an exception
    public void shiftNullBaseRange() throws Exception{
    	Range result = Range.shift(nullRange, 1);
    }
    
    @Test
    // Ensure the lower bound of the positively shifted negative Range is as expected
    // Here, Range is a normal value while delta is a minimum value in the positive direction
    public void shiftNegativeRangeByPositiveAmountLowerBound() {
    	int expectedLowerBound = -4;
    	
    	Range result = Range.shift(pureNegativeRange, 1);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the lower bound of the negatively shifted negative Range is as expected
    // Here, Range is a normal value while delta is a minimum value in the negative direction
    public void shiftNegativeRangeByNegativeAmountLowerBound() {
    	int expectedLowerBound = -6;
    	
    	Range result = Range.shift(pureNegativeRange, -1);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the lower bound of the negative Range shifted by zero is as expected
    // Here, Range is a normal value while delta is a boundary value
    public void shiftNegativeRangeByZeroLowerBound() {
    	int expectedLowerBound = -5;
    	
    	Range result = Range.shift(pureNegativeRange, 0);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of the positively shifted negative Range is as expected
    // Here, Range is a normal value while delta is a minimum value in the positive direction
    public void shiftNegativeRangeByPositiveAmountUpperBound() {
    	int expectedUpperBound = -1;
    	
    	Range result = Range.shift(pureNegativeRange, 1);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of the negatively shifted negative Range is as expected
    // Here, Range is a normal value while delta is a minimum value in the negative direction
    public void shiftNegativeRangeByNegativeAmountUpperBound() {
    	int expectedUpperBound = -3;
    	
    	Range result = Range.shift(pureNegativeRange, -1);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of the negative Range shifted by zero is as expected
    // Here, range is a normal value while delta is a boundary value
    public void shiftNegativeRangeByZeroUpperBound() {
    	int expectedUpperBound = -2;
    	
    	Range result = Range.shift(pureNegativeRange, 0);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the lower bound of the positively shifted cross Range is as expected
    // Here, Range is a normal value while delta is a minimum value in the positive direction
    public void shiftCrossRangeByPositiveAmountLowerBound() {
    	int expectedLowerBound = -1;
    	
    	Range result = Range.shift(crossRange, 1);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the lower bound of the negatively shifted cross Range is as expected
    // Here, Range is a normal value while delta is a minimum value in the negative direction
    public void shiftCrossRangeByNegativeAmountLowerBound() {
    	int expectedLowerBound = -3;
    	
    	Range result = Range.shift(crossRange, -1);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the lower bound of the cross Range shifted by zero is as expected
    // Here, Range is a normal value while delta is a boundary value
    public void shiftCrossRangeByZeroLowerBound() {
    	int expectedLowerBound = -2;
    	
    	Range result = Range.shift(crossRange, 0);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of the cross Range shifted by a positive amount is as expected
    // Here, Range is a normal value while delta is a minimum value in the positive direction
    public void shiftCrossRangeByPositiveAmountUpperBound() {
    	int expectedUpperBound = 3;
    	
    	Range result = Range.shift(crossRange, 1);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of the cross Range shifted by a negative amount is as expected
    // Here, Range is a normal value while delta is a minimum value in the negative direction
    public void shiftCrossRangeByNegativeAmountUpperBound() {
    	int expectedUpperBound = 1;
    	
    	Range result = Range.shift(crossRange, -1);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of the cross Range shifted by zero is as expected
    // Here, Range is a normal value while delta is a boundary value
    public void shiftCrossRangeByZeroUpperBound() {
    	int expectedUpperBound = 2;
    	
    	Range result = Range.shift(crossRange, 0);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the lower bound of the positive Range shifted by a positive amount is as expected
    // Here, Range is a normal value while delta is a minimum value in the positive direction
    public void shiftPositiveRangeByPositiveAmountLowerBound() {
    	int expectedLowerBound = 3;
    	
    	Range result = Range.shift(purePositiveRange, 1);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the lower bound of the positive Range shifted by a negative amount is as expected
    // Here, Range is a normal value while delta is a minimum value in the negative direction
    public void shiftPositiveRangeByNegativeAmountLowerBound() {
    	int expectedLowerBound = 1;
    	
    	Range result = Range.shift(purePositiveRange, -1);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the lower bound of the positive Range shifted by zero is as expected
    // Here, Range is a normal value while delta is a boundary value
    public void shiftPositiveRangeByZeroLowerBound() {
    	int expectedLowerBound = 2;
    	
    	Range result = Range.shift(purePositiveRange, 0);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of the positive Range shifted by a positive amount is as expected
    // Here, Range is a normal value while delta is a minimum value in the positive direction
    public void shiftPositiveRangeByPositiveAmountUpperBound() {
    	int expectedUpperBound = 6;
    	
    	Range result = Range.shift(purePositiveRange, 1);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of the positive Range shifted by a negative amount is as expected
    // Here, Range is a normal value while delta is a minimum value in the negative direction
    public void shiftPositiveRangeByNegativeAmountUpperBound() {
    	int expectedUpperBound = 4;
    	
    	Range result = Range.shift(purePositiveRange, -1);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of the positive Range shifted by zero is as expected
    // Here, Range is a normal value while delta is a boundary value
    
    public void shiftPositiveRangeByZeroUpperBound() {
    	int expectedUpperBound = 5;
    	
    	Range result = Range.shift(purePositiveRange, 0);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    // Testing constrain()
    
    @Test
    // Ensure the result of constraining below the lower bound of a negative range is as expected
    // Here the source range is a normal value while the constrain value is a normal value less than lower bound
    public void constrainNegativeRangeInputLessThanLowerBound() {
    	int expectedOutput = -5;
    	
    	double actualOutput = pureNegativeRange.constrain(-10);
    	
    	assertEquals("Constrain returned the wrong value", expectedOutput, actualOutput, .000000001d);
    }
    
    @Test
    // Ensure the result of constraining between the bounds of a negative range is as expected
    // Here the source range is a normal value while the constrain value is a normal value between the two bounds
    public void constrainNegativeRangeInputBetweenBounds() {
    	int expectedOutput = -3;
    	
    	double actualOutput = pureNegativeRange.constrain(-3);
    	
    	assertEquals("Constrain returned the wrong value", expectedOutput, actualOutput, .000000001d);
    }
    
    @Test
    // Ensure the result of constraining above the upper bound of a negative range is as expected
    // Here the source range is a normal value while the constrain value is a normal value greater than the upper bound
    public void constrainNegativeRangeInputGreaterThanUpperBound() {
    	int expectedOutput = -2;
    	
    	double actualOutput = pureNegativeRange.constrain(10);
    	
    	assertEquals("Constrain returned the wrong value", expectedOutput, actualOutput, .000000001d);
    }
    
    @Test
    // Ensure the result of constraining below the lower bound of a cross range is as expected
    // Here the source range is a normal value while the constrain value is a normal value less than lower bound
    public void constrainCrossRangeInputLessThanLowerBound() {
    	int expectedOutput = -2;
    	
    	double actualOutput = crossRange.constrain(-10);
    	
    	assertEquals("Constrain returned the wrong value", expectedOutput, actualOutput, .000000001d);
    }
    
    @Test
    // Ensure the result of constraining between the bounds of a cross range is as expected
    // Here the source range is a normal value while the constrain value is a normal value between the two bounds
    public void constrainCrossRangeInputBetweenBounds() {
    	int expectedOutput = 0;
    	
    	double actualOutput = crossRange.constrain(0);
    	
    	assertEquals("Constrain returned the wrong value", expectedOutput, actualOutput, .000000001d);
    }
    
    @Test
    // Ensure the result of constraining above the upper bound of a cross range is as expected
    // Here the source range is a normal value while the constrain value is a normal value above the upper bound
    public void constrainCrossRangeInputGreaterThanUpperBound() {
    	int expectedOutput = 2;
    	
    	double actualOutput = crossRange.constrain(10);
    	
    	assertEquals("Constrain returned the wrong value", expectedOutput, actualOutput, .000000001d);
    }
    
    @Test
    // Ensure the result of constraining below the lower bound of a positive range is as expected
    // Here the source range is a normal value while the constrain value is a normal value less than lower bound
    public void constrainPositiveRangeInputLessThanLowerBound() {
    	int expectedOutput = 2;
    	
    	double actualOutput = purePositiveRange.constrain(-10);
    	
    	assertEquals("Constrain returned the wrong value", expectedOutput, actualOutput, .000000001d);
    }
    
    @Test
    // Ensure the result of constraining between the bounds of a positive range is as expected
    // Here the source range is a normal value while the constrain value is a normal value between the two bounds
    public void constrainPositiveRangeInputBetweenBounds() {
    	int expectedOutput = 3;
    	
    	double actualOutput = purePositiveRange.constrain(3);
    	
    	assertEquals("Constrain returned the wrong value", expectedOutput, actualOutput, .000000001d);
    }
    
    @Test
    // Ensure the result of constraining above the upper bound of a positive range is as expected
    // Here the source range is a normal value while the constrain value is a normal value greater than the upper bound
    public void constrainPositiveRangeInputGreaterThanUpperBound() {
    	int expectedOutput = 5;
    	
    	double actualOutput = purePositiveRange.constrain(10);
    	
    	assertEquals("Constrain returned the wrong value", expectedOutput, actualOutput, .000000001d);
    }
    
    // Testing expand()
    
    @Test (expected = Exception.class)
    // Ensure that calling the expand() function with a null base Range throws an exception
    public void expandNullBaseRange() throws Exception{
    	Range result = Range.expand(nullRange, 1, 1);
    }
    
    @Test
    // Ensure the lower bound of a negative range expanded by a positive amount is as expected
    // Here range is a normal value while the upper and lower margins are also normal values
    public void expandNegativeRangeByPositiveFractionLowerBound() {
    	double expectedLowerBound = -5 - (0.5)*3;
    	
    	Range result = Range.expand(pureNegativeRange, 0.5, 0.5);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of a negative range expanded by a positive amount is as expected
    // Here range is a normal value while the upper and lower margins are also normal values
    public void expandNegativeRangeByPositiveFractionUpperBound() {
    	double expectedUpperBound = -2 + (0.5)*3;
    	
    	Range result = Range.expand(pureNegativeRange, 0.5, 0.5);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the length of a negative range expanded by a positive amount is as expected
    // Here range is a normal value while the upper and lower margins are also normal values
    public void expandNegativeRangeByPositiveFractionLength() {
    	double expectedLength = 3 + (0.5*3) + (0.5*3);
    	
    	Range result = Range.expand(pureNegativeRange, 0.5, 0.5);
    	double actualLength = result.getLength();
    	
    	assertEquals("Length was not correct", expectedLength, actualLength, .000000001d);
    }
    
    @Test
    // Ensure the lower bound of a cross range expanded by a positive amount is as expected
    // Here range is a normal value while the upper and lower margins are also normal values
    public void expandCrossRangeByPositiveFractionLowerBound() {
    	double expectedLowerBound = -2 - (0.5)*4;
    	
    	Range result = Range.expand(crossRange, 0.5, 0.5);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of a cross range expanded by a positive amount is as expected
    // Here range is a normal value while the upper and lower margins are also normal values
    public void expandCrossRangeByPositiveFractionUpperBound() {
    	double expectedUpperBound = 2 + (0.5)*4;
    	
    	Range result = Range.expand(crossRange, 0.5, 0.5);
    	
    	assertEquals("Lower bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the length of a cross range expanded by a positive amount is as expected
    // Here range is a normal value while the upper and lower margins are also normal values
    public void expandCrossRangeByPositiveFractionLength() {
    	double expectedLength = 4 + (0.5*4) + (0.5*4);
    	
    	Range result = Range.expand(crossRange, 0.5, 0.5);
    	double actualLength = result.getLength();
    	
    	assertEquals("Length was not correct", expectedLength, actualLength, .000000001d);
    }
    
    @Test
    // Ensure the lower bound of a positive range expanded by a positive amount is as expected
    // Here range is a normal value while the upper and lower margins are also normal values
    public void expandPositiveRangeByPositiveFractionLowerBound() {
    	double expectedLowerBound = 2 - (0.5)*3;
    	
    	Range result = Range.expand(purePositiveRange, 0.5, 0.5);
    	
    	assertEquals("Lower bound was not correct", expectedLowerBound, result.getLowerBound(), .000000001d);
    }
    
    @Test
    // Ensure the upper bound of a positive range expanded by a positive amount is as expected
    // Here range is a normal value while the upper and lower margins are also normal values
    public void expandPositiveRangeByPositiveFractionUpperBound() {
    	double expectedUpperBound = 5 + (0.5)*3;
    	
    	Range result = Range.expand(purePositiveRange, 0.5, 0.5);
    	
    	assertEquals("Upper bound was not correct", expectedUpperBound, result.getUpperBound(), .000000001d);
    }
    
    @Test
    // Ensure the length of a positive range expanded by a positive amount is as expected
    // Here range is a normal value while the upper and lower margins are also normal values
    public void expandPositiveRangeByPositiveFractionLength() {
    	double expectedLength = 3 + (0.5*3) + (0.5*3);
    	
    	Range result = Range.expand(purePositiveRange, 0.5, 0.5);
    	double actualLength = result.getLength();
    	
    	assertEquals("Length was not correct", expectedLength, actualLength, .000000001d);
    }
    
    /* 
	 * ==================
	 * Testing UpperBound
	 * ==================
	 */
	
	/* This tests a null range in the Upper Bound method where system should throw
	 * exception.
	 */
	@Test (expected = Exception.class)
	public void testNullUpper() throws Exception{
		double result = exampleRangeNull.getUpperBound();
	}
	
	/* This tests a small negative value and zero value for range in the Upper Bound
	 * method where zero is the upper bound value.
	 */
	@Test
	public void testSmallUpper() {
		assertEquals("Upper Bound of (-1, 0) is 0",
				0, exampleRangeSmall.getUpperBound(), .000000001d);
	}
	
	/* This tests the same two small positive values for range in the Upper Bound
	 * method where the positive number is the upper bound value.
	 */
	@Test
	public void testPosSmallUpper() {
		assertEquals("Upper Bound of (1, 1) is 1",
				1, exampleRangePosSmall.getUpperBound(), .000000001d);
	}
	
	/* This tests a small negative value and another small negative value for 
	 * range in the Upper Bound method where a negative number is the upper bound value.
	 */
	@Test
	public void testNegSmallUpper() {
		assertEquals("Upper Bound of (-2, -1) is -1",
				-1, exampleRangeNegSmall.getUpperBound(), .000000001d);
	}
	
	/* This tests a zero value and a large positive value for range in the Upper Bound
	 * method where a large positive number is the upper bound value.
	 */
	@Test
	public void testLargeUpper() {
		assertEquals("Upper Bound of (-100000, 1000000) is 1000000",
				1000000, exampleRangeLarge.getUpperBound(), .000000001d);
	}
	
	/* This tests a large positive value and another large positive value 
	 * for range in the Upper Bound method where a large positive number
	 *  is the upper bound value.
	 */
	@Test
	public void testPosLargeUpper() {
		assertEquals("Upper Bound of (0, 1000000) is 1000000",
				1000000, exampleRangePosLarge.getUpperBound(), .000000001d);
	}
	
	/* This tests a large negative value and another large negative value
	 *  for range in the Upper Bound method where a large positive number
	 *   is the upper bound value.
	 */
	@Test
	public void testNegLargeUpper() {
		assertEquals("Upper Bound of (-1000000, -1) is -1",
				-1, exampleRangeNegLarge.getUpperBound(), .000000001d);
	}
	
	/* This tests a negative decimal value and a positive decimal value
	 *  for range in the Upper Bound method where a positive decimal
	 *   number is the upper bound value.
	 */
	@Test
	public void testDecUpper() {
		assertEquals("Upper Bound of (-1.11111, 1.11111) is 1.11111",
				1.111111, exampleRangeDec.getUpperBound(), .000000001d);
	}
	
	/* This tests a negative decimal value and a positive decimal value
	 *  for range in the Upper Bound method where a positive decimal
	 *   number is the upper bound value.
	 */
	@Test
	public void testPosDecUpper() {
		assertEquals("Upper Bound of (1.11111, 14.11200) is 14.11200",
				14.11200, exampleRangePosDec.getUpperBound(), .000000001d);
	}
	
	/* This tests a negative decimal value and another negative decimal value
	 *  for range in the Upper Bound method where a negative decimal
	 *   number is the upper bound value.
	 */
	@Test
	public void testNegDecUpper() {
		assertEquals("Upper Bound of (-1.11111, -0.11111) is -0.11111",
				-0.111111, exampleRangeNegDec.getUpperBound(), .000000001d);
	}
	
	/* 
	 * ==============
	 * Testing Length
	 * ==============
	 */
	
	/* This tests a null range in the Length method where system should throw
	 * exception.
	 */
	@Test (expected = Exception.class)
	public void testNullLength() {
		double result = exampleRangeNull.getLength();
	}
	
	/* This tests a small negative value and zero value for range in the Length
	 * method where length is a positive number.
	 */
	@Test
	public void testSmallLength() {
		assertEquals("Length of (-1, 0) is 1",
				1, exampleRangeSmall.getLength(), .000000001d);
	}
	
	/* This tests a small positive value and another small positive value for range
	 * in the Length method where the length is a positive number.
	 */
	@Test
	public void testPosSmallLength() {
		assertEquals("Length of (1, 1) is 0",
				0, exampleRangePosSmall.getLength(), .000000001d);
	}
	
	/* This tests a small negative value and another small negative value for range
	 * in the Length method where the length is a positive number.
	 */
	@Test
	public void testNegSmallLength() {
		assertEquals("Length of (-2, -1) is -1",
				1, exampleRangeNegSmall.getLength(), .000000001d);
	}
	
	/* This tests a large negative value and a large positive value for range
	 * in the Length method where the length is a positive number.
	 */
	@Test
	public void testLargeLength() {
		assertEquals("Length of (-1000000, 1000000) is 2000000",
				2000000, exampleRangeLarge.getLength(), .000000001d);
	}
	
	/* This tests a large positive value and another large positive value
	 *  for range in the Length method where the length is a positive number.
	 */
	@Test
	public void testPosLargeLength() {
		assertEquals("Length of (0, 1000000) is 1000000",
				1000000, exampleRangePosLarge.getLength(), .000000001d);
	}
	
	/* This tests a large negative value and another large negative value
	 *  for range in the Length method where the length is a positive number.
	 */
	@Test
	public void testNegLargeLength() {
		assertEquals("Length of (-1000000, -1) is 99999",
				999999, exampleRangeNegLarge.getLength(), .000000001d);
	}
	
	/* This tests a positive decimal value and another positive decimal value
	 *  for range in the Length method where the length is a positive decimal.
	 */
	@Test
	public void testPosDecLength() {
		assertEquals("Length of (1.11111, 14.11200) is 13.00089",
				13.00089, exampleRangePosDec.getLength(), .000000001d);
	}
	
	/* This tests a negative decimal value and another negative decimal value
	 *  for range in the Length method where the length is a positive number.
	 */
	@Test
	public void testNegDecLength() {
		assertEquals("Length of (-1.11111, -0.11111) is -0.11111",
				1, Math.ceil(exampleRangeNegDec.getLength()), .000000001d);
	}

}