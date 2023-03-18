package org.jfree.data;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;

import org.jmock.*;

public class DataUtilitiesTest extends DataUtilities {
	
	Mockery mockingContext;
	
	@Before
	public void setUp() throws Exception {
		mockingContext = new Mockery();
	}
	
	/* ---------------------------------------------------------------
	 * method at test: calculateColumnTotal(Values2D data, int column)
	 * ---------------------------------------------------------------
	 * note: counts nulls as 1s causing test cases to fail!
	 */
	
	/*
	 * this test covers the invalid case of a null being passed instead of a valid Values2D  
	 */
	@Test(expected = Exception.class)
	public void test_calculateColumnTotal_invalidData() {
		DataUtilities.calculateColumnTotal(null, 0);
	} 
	
	/*
	 * this test covers the equivalence class of a column with positive values
	 */
	@Test
	public void test_calculateColumnTotal_positiveValidValues() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(3));
				one(values2D).getColumnCount();
				will(returnValue(1));
				one(values2D).getValue(0,0);
				will(returnValue(1000));
				one(values2D).getValue(1,0);
				will(returnValue(100000));
				one(values2D).getValue(2,0);
				will(returnValue(100000000));
			}
		});
		
		double result = DataUtilities.calculateColumnTotal(values2D,0);
		assertEquals(100101000, result, 0.0000001d);
	}
	
	/*
	 * this test covers the equivalence class of a column with negative values
	 */
	@Test
	public void test_calculateColumnTotal_negativeValidValues() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(3));
				one(values2D).getColumnCount();
				will(returnValue(1));
				one(values2D).getValue(0,0);
				will(returnValue(-1000));
				one(values2D).getValue(1,0);
				will(returnValue(100000));
				one(values2D).getValue(2,0);
				will(returnValue(-100000000));
			}
		});
		
		double result = DataUtilities.calculateColumnTotal(values2D,0);
		assertEquals(-99901000, result, 0.0000001d);
	}
	
	
	/*
	 * this test covers the boundary case of running the method on the max column index
	 * - here we create a 2x2 table and test the method on the 1th column index(pointed below)   
	 *   |
	 * 3 5
	 * 5 3
	 *    = 8
	 */
	@Test
	public void test_calculateColumnTotal_maxColumnIndex() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(2));
				one(values2D).getColumnCount();
				will(returnValue(2));
				one(values2D).getValue(0,0);
				will(returnValue(3));
				one(values2D).getValue(0,1);
				will(returnValue(5));
				one(values2D).getValue(1,0);
				will(returnValue(5));
				one(values2D).getValue(1,1);
				will(returnValue(3));
			}
		});
		
		double result = DataUtilities.calculateColumnTotal(values2D, 1);
		assertEquals(8, result, 0.0000001d);
	}
	
	/*
	 * this test covers the boundary case of all null values in the column
	 */
	@Test
	public void test_calculateColumnTotal_allNullValues() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(3));
				one(values2D).getValue(0,0);
				will(returnValue(null));
				one(values2D).getValue(1,0);
				will(returnValue(null));
				one(values2D).getValue(2,0);
				will(returnValue(null));
			}
		});
		
		double result = DataUtilities.calculateColumnTotal(values2D, 0);
		assertEquals(0, result, 0.0000001d);
	}
	
	/*
	 * this test covers the boundary case of some null values in the column
	 */
	@Test
	public void test_calculateColumnTotal_someNullValues() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(3));
				one(values2D).getValue(0,0);
				will(returnValue(null));
				one(values2D).getValue(1,0);
				will(returnValue(null));
				one(values2D).getValue(2,0);
				will(returnValue(3));
			}
		});
		
		double result = DataUtilities.calculateColumnTotal(values2D, 0);
		assertEquals(3, result, 0.0000001d);
	}
	
	/*
	 * this test covers the invalid case of an empty table
	 */
	@Test
	public void test_calculateColumnTotal_noTable() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(0));
				one(values2D).getColumnCount();
				will(returnValue(0));
			}
		});
		
		double result = DataUtilities.calculateColumnTotal(values2D, 0);
		assertEquals(0, result, 0.0000001d);
	}
	
	/*
	 * this test covers the invalid case of a negative out of bounds column index
	 * - we mock a 2x1 array and try to invoke the method on the -1th column element
	 */
	@Test
	public void test_calculateColumnTotal_negativeOutOfBoundsIndex() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(2));
				one(values2D).getColumnCount();
				will(returnValue(1));
				one(values2D).getValue(0, -1);
				will(returnValue(null));
				one(values2D).getValue(1, -1);
				will(returnValue(null));
			}
		});
		
		double result = DataUtilities.calculateColumnTotal(values2D, -1);
		assertEquals(0, result, 0.0000001d);
	}
	
	/*
	 * this test covers the invalid case of a positive out of bounds column index
	 * - we mock a 2x1 array and try to invoke the method on the 1th column element
	 *   when 0 is the max column index.
	 */
	@Test
	public void test_calculateColumnTotal_positiveOutOfBoundsIndex() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(2));
				one(values2D).getColumnCount();
				will(returnValue(1));
				one(values2D).getValue(0, 1);
				will(returnValue(null));
				one(values2D).getValue(1, 1);
				will(returnValue(null));
			}
		});
		
		double result = DataUtilities.calculateColumnTotal(values2D, 1);
		assertEquals(0, result, 0.0000001d);
	}
	
	/* ---------------------------------------------------------------
	 * method at test: calculateRowTotal(Values2D data, int row)
	 * ---------------------------------------------------------------
	 * note: ignores the very last element that should be counted 
	 * 		 causing tests to fail!
	 */
	
	/*
	 * this test covers the invalid case of a null being passed instead of a valid Values2D 
	 */
	@Test(expected = Exception.class)
	public void test_calculateRowTotal_invalidData() {
		DataUtilities.calculateRowTotal(null, 0);
	}
	
	/*
	 * this test covers the equivalence class of a row with positive values
	 */
	@Test
	public void test_calculateRowTotal_positiveValidValues() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(1));
				one(values2D).getColumnCount();
				will(returnValue(3));
				one(values2D).getValue(0,0);
				will(returnValue(10));
				one(values2D).getValue(0,1);
				will(returnValue(20));
				one(values2D).getValue(0,2);
				will(returnValue(30));
			}
		});
		
		double result = DataUtilities.calculateRowTotal(values2D,0);
		assertEquals(60, result, 0.0000001d);
	}
	
	/*
	 * this test covers the equivalence class of a row with negative values
	 */
	@Test
	public void test_calculateRowTotal_negativeValidValues() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(1));
				one(values2D).getColumnCount();
				will(returnValue(3));
				one(values2D).getValue(0,0);
				will(returnValue(-2));
				one(values2D).getValue(0,1);
				will(returnValue(-10));
				one(values2D).getValue(0,2);
				will(returnValue(-3));
			}
		});
		
		double result = DataUtilities.calculateRowTotal(values2D,0);
		assertEquals(-15, result, 0.0000001d);
	}
	
	/*
	 * this test covers the boundary case of running the method on the max row index
	 * - here we create a 2x2 table and test the method on the 1th row index(pointed below)   
	 *  
	 *    3 5
	 * -> 5 11 = 16
	 */ 
	@Test
	public void test_calculateRowTotal_maxRowIndex() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(2));
				one(values2D).getColumnCount();
				will(returnValue(2));
				one(values2D).getValue(0,0);
				will(returnValue(3));
				one(values2D).getValue(0,1);
				will(returnValue(5));
				one(values2D).getValue(1,0);
				will(returnValue(5));
				one(values2D).getValue(1,1);
				will(returnValue(11));
			}
		});
		
		double result = DataUtilities.calculateRowTotal(values2D, 1);
		assertEquals(16, result, 0.0000001d);
	}
	
	/*
	 * this test covers the boundary case of all null values in the row
	 */
	@Test
	public void test_calculateRowTotal_allNullValues() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getColumnCount();
				will(returnValue(3));
				one(values2D).getValue(0,0);
				will(returnValue(null));
				one(values2D).getValue(0,1);
				will(returnValue(null));
				one(values2D).getValue(0,2);
				will(returnValue(null));
			}
		}); 
		
		double result = DataUtilities.calculateRowTotal(values2D, 0);
		assertEquals(0, result, 0.0000001d);
	}
	
	/*
	 * this test covers the boundary case of some null values in the row
	 */
	@Test
	public void test_calculateRowTotal_someNullValues() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getColumnCount();
				will(returnValue(3));
				one(values2D).getValue(0,0);
				will(returnValue(null));
				one(values2D).getValue(0,1);
				will(returnValue(null));
				one(values2D).getValue(0,2);
				will(returnValue(3));
			}
		});
		
		double result = DataUtilities.calculateRowTotal(values2D, 0);
		assertEquals(3, result, 0.0000001d);
	}
	
	/*
	 * this test covers the invalid case of an empty table
	 */
	@Test
	public void test_calculateRowTotal_noTable() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(0));
				one(values2D).getColumnCount();
				will(returnValue(0));
			}
		});
		
		double result = DataUtilities.calculateRowTotal(values2D, 0);
		assertEquals(0, result, 0.0000001d);
	}
	
	/*
	 * this test covers the invalid case of a negative out of bounds row index
	 * - we mock a 1x2 array and try to invoke the method on the -1th row index
	 * 	 when 0 is the minimum row index.
	 */
	@Test
	public void test_calculateRowTotal_negativeOutOfBoundsIndex() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(1));
				one(values2D).getColumnCount();
				will(returnValue(2));
				one(values2D).getValue(-1, 0);
				will(returnValue(null));
				one(values2D).getValue(-1, 1);
				will(returnValue(null));
			}
		});
		
		double result = DataUtilities.calculateRowTotal(values2D, -1);
		assertEquals(0, result, 0.0000001d);
	}
	
	/*
	 * this test covers the invalid case of a positive out of bounds row index
	 * - we mock a 1x2 array and try to invoke the method on the 1th row index
	 *   when 0 is the max row index.
	 */
	@Test
	public void test_calculateRowTotal_positiveOutOfBoundsIndex() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(1));
				one(values2D).getColumnCount();
				will(returnValue(2));
				one(values2D).getValue(1, 0);
				will(returnValue(null));
				one(values2D).getValue(1, 1);
				will(returnValue(null));
			}
		});
		
		double result = DataUtilities.calculateRowTotal(values2D, 1);
		assertEquals(0, result, 0.0000001d);
	}
	
	/* -------------------------------------------------
	 * method at test: createNumberArray(double[] data)
	 * -------------------------------------------------
	 * note: copies the last element as null
	 */
	
	/*
	 * this test covers the invalid case of null being passed as a double array
	 */
	@Test(expected= IllegalArgumentException.class)
	public void test_createNumberArray_invalidNullData() {
		double[] data = null;
		DataUtilities.createNumberArray(data);
	}
	
	/*
	 * this test covers the equivalence case of a double array with positive values
	 */
	@Test
	public void test_createNumberArray_validPositiveValues() {
		double[] data = new double[] {1.1, 1.2, 5.3, 5.66, 9.22, 77.1};
		Number[] expectedNumberArray = new Number[] {1.1, 1.2, 5.3, 5.66, 9.22, 77.1};
		
		Number[] result = DataUtilities.createNumberArray(data);
		assertArrayEquals(expectedNumberArray, result);
	}
	
	/*
	 * this test covers the equivalence case of a double array with negative values
	 */
	@Test
	public void test_createNumberArray_validNegativeValues() {
		double[] data = new double[] {-1.1, -1.2, -5.3, -5.66, -9.22, -77.1};
		Number[] expectedNumberArray = new Number[] {-1.1, -1.2, -5.3, -5.66, -9.22, -77.1};
		
		Number[] result = DataUtilities.createNumberArray(data);
		assertArrayEquals(expectedNumberArray, result);
	}
	
	/*
	 * this test covers the boundary case of a double array of length 1
	 */
	@Test
	public void test_createNumberArray_arrayLengthOne() {
		double[] data = new double[] {1.1};
		Number[] expectedNumberArray = new Number[] {1.1};
		
		Number[] result = DataUtilities.createNumberArray(data);
		assertArrayEquals(expectedNumberArray, result);
	}
	
	/*
	 * this test covers the boundary case of a double array of length 0
	 */
	@Test
	public void test_createNumberArray_emptyArray() {
		double[] data = new double[] {};
		Number[] expectedNumberArray = new Number[] {};
		
		Number[] result = DataUtilities.createNumberArray(data);
		assertArrayEquals(expectedNumberArray, result);
	}
	
	/* ----------------------------------------------------
	 * method at test: createNumberArray2D(double[][] data)
	 * ----------------------------------------------------
	 * note: copies the last element of the first array as null
	 */
	
	
	/*
	 * this test covers the invalid case of null being passed as a 2D double array
	 */
	@Test(expected= IllegalArgumentException.class)
	public void test_createNumberArray2D_invalidNullData() {
		double[][] data = null;
		DataUtilities.createNumberArray2D(data);
	}
	
	/*
	 * this test covers the equivalence case of a 2D double array with positive & negative values
	 */
	@Test
	public void test_createNumberArray2D_validValues() {
		double[][] data = new double[][] {{1.1, -1.2}, {2.0, 2.1}, {-3.0, 3.1}};
		Number[][] expectedNumberArray = new Number[][] {{1.1, -1.2}, {2.0, 2.1}, {-3.0, 3.1}};
		
		Number[][] result = DataUtilities.createNumberArray2D(data);
		assertArrayEquals(expectedNumberArray, result);
	}
	
	/*
	 * this test covers the boundary case of a 2D double array of length 1
	 */
	@Test
	public void test_createNumberArray2D_arrayLengthOne() {
		double[][] data = new double[][] {{1.1}};
		Number[][] expectedNumberArray = new Number[][] {{1.1}};
		
		Number[][] result = DataUtilities.createNumberArray2D(data);
		assertArrayEquals(expectedNumberArray, result);
	}
	
	/*
	 * this test covers the boundary case of a 2D double array of length 0
	 */
	@Test
	public void test_createNumberArray2D_emptyArray() {
		double[][] data = new double[][] {};
		Number[][] expectedNumberArray = new Number[][] {};
		
		Number[][] result = DataUtilities.createNumberArray2D(data);
		assertArrayEquals(expectedNumberArray, result);
	}
	
	/* ----------------------------------------------------------
	 * method at test: getCumulativePercentages(KeyedValues data)
	 * ----------------------------------------------------------
	 */
	
	/*
	 * this test covers the invalid case of null being passed for KeyedValues data
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test_getCumulativePercentage_nullKeyedValues() {
        KeyedValues data = null; 
        DataUtilities.getCumulativePercentages(data);
	}
	
	/*
	 * this test covers the boundary case of a valid KeyedValues object with valid keys, indexs, & values.
	 */
	@Test
	public void test_getCumulativePercentages_validKeysValues() {
		KeyedValues keyedValues = mockingContext.mock(KeyedValues.class);
		mockingContext.checking(new Expectations() {
			{
				atLeast(3).of(keyedValues).getItemCount();
				will(returnValue(3));
				
				//key at certain index
				atLeast(3).of(keyedValues).getKey(0);
				will(returnValue(0));
				atLeast(3).of(keyedValues).getKey(1);
				will(returnValue(1));
				atLeast(3).of(keyedValues).getKey(2);
				will(returnValue(2));
				
				//value at certain index
				atLeast(3).of(keyedValues).getValue(0);
				will(returnValue(5));
				atLeast(3).of(keyedValues).getValue(1);
				will(returnValue(9));
				atLeast(3).of(keyedValues).getValue(2);
				will(returnValue(2));
			}
		});
		
		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
		
		DefaultKeyedValues defaultKeyedValues = new DefaultKeyedValues();
		defaultKeyedValues.addValue((Integer)0, 0.3125);
		defaultKeyedValues.addValue((Integer)1, 0.875);
		defaultKeyedValues.addValue((Integer)2, 1.0);
		
		assertEquals(defaultKeyedValues.getValue(2), result.getValue(2));
	}
	
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * ASSIGNMENT 3 TESTS BEGIN HERE 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	
	
	/* ----------------------------------------------------------
	 * method at test: equal(double[][] a, double[][] b)
	 * ----------------------------------------------------------
	 */
	
	@Test
	public void testEqual_true() {
		double[][] arrayOne = new double[][] {{1.1, 1.2}, {2.1, 2.2}};
		double[][] arrayTwo = new double[][] {{1.1, 1.2}, {2.1, 2.2}};
		
		boolean result = DataUtilities.equal(arrayOne, arrayTwo);
		assertEquals(true, result);
	}
	
	@Test
	public void testEqual_false() {
		double[][] arrayOne = new double[][] {{1.1, 1.2}, {2.1, 2.2}};
		double[][] arrayTwo = new double[][] {{1.1, 1.12}, {2.1, 2.2}};
		
		boolean result = DataUtilities.equal(arrayOne, arrayTwo);
		assertEquals(false, result);
	}
	
	@Test
	public void testEqual_differentLengths() {
		double[][] arrayOne = new double[][] {{1.1, 1.2}, {2.1, 2.2}, {3.1, 3.2}};
		double[][] arrayTwo = new double[][] {{1.12, 1.2}, {2.1, 2.2}};
		
		boolean result = DataUtilities.equal(arrayOne, arrayTwo);
		assertEquals(false, result);
	}
	
	
	@Test
	public void testEqual_firstNull() {
		double[][] arrayOne = null;
		double[][] arrayTwo = new double[][] {{1.1, 1.2}, {2.1, 2.2}};
		
		boolean result = DataUtilities.equal(arrayOne, arrayTwo);
		assertEquals(false, result);
	}
	
	@Test
	public void testEqual_secondNull() {
		double[][] arrayOne = new double[][] {{1.1, 1.2} , {2.1, 2.2}};
		double[][] arrayTwo = null;
		
		boolean result = DataUtilities.equal(arrayOne, arrayTwo);
		assertEquals(false, result);
	}
	
	@Test
	public void testEqual_bothNull() {
		double[][] arrayOne = null;
		double[][] arrayTwo = null;
		
		boolean result = DataUtilities.equal(arrayOne, arrayTwo);
		assertEquals(true, result);
	}
	
	 
	/* --------------------------------------------
	 * method at test: clone(double[][] source)
	 * --------------------------------------------
	 */
	
	@Test
	public void testClone_noNullElements() {
		double[][] source = new double[][] {{1.1, 1.2}, {2.1, 2.2}};
		double[][] cloneOfSource = DataUtilities.clone(source);
		
		assertArrayEquals(source, cloneOfSource);
	}
	
	@Test
	public void testClone_aNullElement() {
		double[][] source = new double[][] {null, {2.1, 2.2}};
		double[][] cloneOfSource = DataUtilities.clone(source);
		
		assertArrayEquals(source, cloneOfSource);
		
	}
	
	
	/* ---------------------------------------------------------------------------------
	 * method at test: calculateColumnTotal(Values2D data, int column, int[] validRows)
	 * ---------------------------------------------------------------------------------
	 */
	
	@Test
	public void testCalculateColumnTotalArr_fittingValidRows() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(3));
				one(values2D).getColumnCount();
				will(returnValue(1));
				one(values2D).getValue(0,0);
				will(returnValue(1));
				one(values2D).getValue(1,0);
				will(returnValue(2));
				one(values2D).getValue(2,0);
				will(returnValue(3));
			}
		});
		
		int[] validRows = new int[] {0,1, 2};
		double result = DataUtilities.calculateColumnTotal(values2D, 0, validRows);
		assertEquals(6, result, 0.0000001d);
	}
	
	@Test
	public void testCalculateColumnTotalArr_highValidRowsValue() {
		Mockery mockingContextTwo = new Mockery();
		Values2D values2D = mockingContextTwo.mock(Values2D.class);
		mockingContextTwo.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(3));
				one(values2D).getColumnCount();
				will(returnValue(1));
				one(values2D).getValue(0, 0);
				will(returnValue(1));
				one(values2D).getValue(1, 0);
				will(returnValue(2));
				one(values2D).getValue(2, 0);
				will(returnValue(3));
			}
		});
		
		int[] validRows = new int[] {0,1,5};
		double result = DataUtilities.calculateColumnTotal(values2D, 0, validRows);
		assertEquals(3, result, 0.0000001d);
	}
	
	
	@Test
	public void testCalculateColumnTotalArr_nullValue() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations () {
			{
				one(values2D).getRowCount();
				will(returnValue(3));
				one(values2D).getColumnCount();
				will(returnValue(1));
				one(values2D).getValue(0, 0);
				will(returnValue(1));
				one(values2D).getValue(1, 0);
				will(returnValue(null));
				one(values2D).getValue(2, 0);
				will(returnValue(3));	
			}
		});
		
		int[] validRows = new int[] {0,1,2};
		double result = DataUtilities.calculateColumnTotal(values2D, 0, validRows);
		assertEquals(4, result, 0.0000001d);
	}
	
	
	/* ---------------------------------------------------------------------------------
	 * method at test: calculateRowTotal(Values2D data, int row, int[] validCols)
	 * ---------------------------------------------------------------------------------
	 */
	
	@Test
	public void testCalculateRowTotalArr_fittingValidCols() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(1));
				one(values2D).getColumnCount();
				will(returnValue(3));
				one(values2D).getValue(0,0);
				will(returnValue(1));
				one(values2D).getValue(0,1);
				will(returnValue(2));
				one(values2D).getValue(0,2);
				will(returnValue(3));
			}
		});
		
		int[] validCols = new int[] {0,1,2};
		double result = DataUtilities.calculateRowTotal(values2D, 0, validCols);
		assertEquals(6, result, 0.0000001d);
	}
	
	@Test
	public void testCalculateRowTotalArr_highValidColsValue() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(1));
				one(values2D).getColumnCount();
				will(returnValue(3));
				one(values2D).getValue(0,0);
				will(returnValue(1));
				one(values2D).getValue(0,1);
				will(returnValue(2));
				one(values2D).getValue(0,2);
				will(returnValue(3));
			}
		});
		
		int[] validCols = new int[] {0,5,1};
		double result = DataUtilities.calculateRowTotal(values2D, 0, validCols);
		assertEquals(3, result, 0.0000001d);
	}
	
	@Test
	public void testCalculateRowTotalArr_nullValue() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(1));
				one(values2D).getColumnCount();
				will(returnValue(3));
				one(values2D).getValue(0,0);
				will(returnValue(1));
				one(values2D).getValue(0,1);
				will(returnValue(null));
				one(values2D).getValue(0,2);
				will(returnValue(3));
			}
		});
		
		int[] validCols = new int[] {0,1,2};
		double result = DataUtilities.calculateRowTotal(values2D, 0, validCols);
		assertEquals(4, result, 0.0000001d);
	}
	
	@Test
	public void testCalculateRowTotalArr_negativeColumnCount() {
		Values2D values2D = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
			{
				one(values2D).getRowCount();
				will(returnValue(1));
				one(values2D).getColumnCount();
				will(returnValue(-1));
			}
		});
		
		int[] validCols = new int[] {0,1,2};
		double result = DataUtilities.calculateRowTotal(values2D, 0, validCols);
		assertEquals(0, result, 0.0000001d);
	}
	
	/* ----------------------------------------------------------
	 * method at test: getCumulativePercentages(KeyedValues data)
	 * ----------------------------------------------------------
	 */
	
	
	@Test
	public void test_getCumulativePercentages_nullValue() {
		KeyedValues keyedValues = mockingContext.mock(KeyedValues.class);
		mockingContext.checking(new Expectations() {
			{
				atLeast(3).of(keyedValues).getItemCount();
				will(returnValue(3));
				
				//key at certain index
				atLeast(3).of(keyedValues).getKey(0);
				will(returnValue(0));
				atLeast(3).of(keyedValues).getKey(1);
				will(returnValue(1));
				atLeast(3).of(keyedValues).getKey(2);
				will(returnValue(2));
				
				//value at certain index
				atLeast(3).of(keyedValues).getValue(0);
				will(returnValue(null));
				atLeast(3).of(keyedValues).getValue(1);
				will(returnValue(9));
				atLeast(3).of(keyedValues).getValue(2);
				will(returnValue(2));
			}
		});
		
		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);
		
		DefaultKeyedValues defaultKeyedValues = new DefaultKeyedValues();
		defaultKeyedValues.addValue((Integer)0, 0.0);
		defaultKeyedValues.addValue((Integer)1, 0.81818181818);
		defaultKeyedValues.addValue((Integer)2, 1.0);
		
		assertEquals(defaultKeyedValues.getValue(2), result.getValue(2));
	}
	
	@After
	public void tearDown() {
		mockingContext = null;
	}
}
