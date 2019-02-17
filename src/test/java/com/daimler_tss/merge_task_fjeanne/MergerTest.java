package com.daimler_tss.merge_task_fjeanne;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Unit test for simple Merger.
 */
public class MergerTest {//extends TestCase {
	private static Runtime runtime;
	private long usedMemoryBefore;

	@BeforeClass
	public static void setUp() {
		runtime = Runtime.getRuntime();
	}

	@Before
	public void beforeMethod() {
		usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used Memory before: " + usedMemoryBefore);
	}

	@After
	public void afterMethod() {
		// working code here
		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Memory increased:" + (usedMemoryAfter - usedMemoryBefore));
	}

	/**
	 * Shouldn't break and return an empty list.
	 */
	@Test
	public void testEmptyList() {
		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();

		merger.merge(input);

		assertEquals("The merged list should be empty (size == 0)", 0, input.size());
	}

	/**
	 * Shouldn't break and return <code>null</code>.
	 */
	@Test
	public void testNull() {
		Merger merger = new Merger();
		assertNull("The merged list should be null", merger.merge(null));
	}

	/**
	 * Two points that shouldn't merge.
	 */
	@Test
	public void testNoMerge() {
		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();
		Point2D.Double p0 = new Point2D.Double(0, 1);
		Point2D.Double p1 = new Point2D.Double(1.1, 2);

		input.add(p0);
		input.add(p1);

		merger.merge(input);

		// same amount of elements (no merge)
		assertEquals(input.size(), 2);

		// same elements
		assertEquals(p0, input.get(0));
		assertEquals(p1, input.get(1));
	}

	/**
	 * [0,1] + [1,2] => [0,2]
	 */
	@Test
	public void testMerge2Points() {
		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();
		Point2D.Double pMin = new Point2D.Double(0, 1);
		Point2D.Double pMax = new Point2D.Double(1, 2);

		input.add(pMin);
		input.add(pMax);

		merger.merge(input);

		// Only 1 (merged) element
		assertEquals("The list should have 1 merged element", 1, input.size());

		// Check the merged element
		assertEquals(pMin.getX(), input.get(0).getX());
		assertEquals(pMax.getY(), input.get(0).getY());
	}

	/**
	 * Example from the assignment. Input: [25,30] [2,19] [14, 23] [4,8] Output:
	 * [2,23] [25,30]
	 */
	@Test
	public void testMergePoints_1() {

		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();

		input.add(new Point2D.Double(25, 30));
		input.add(new Point2D.Double(2, 19));
		input.add(new Point2D.Double(14, 23));
		input.add(new Point2D.Double(4, 8));

		merger.merge(input);

		// Only 1 (merged) element
		assertEquals(2, input.size());

		// Check the merged elements
		// Note that the elements are type "double"
		assertEquals(2.0, input.get(0).getX());
		assertEquals(23.0, input.get(0).getY());
		assertEquals(25.0, input.get(1).getX());
		assertEquals(30.0, input.get(1).getY());

	}

	/**
	 * Merge 100 overlapping and consecutive intervals into 1:<br>
	 * [0,1], [1,2], ...,[99,100] => [0,100]
	 */
	@Test
	public void testMergeConsecutiveOverlappingIntervalsInto1() {
		System.out.println("MergerTest.testMergeConsecutiveOverlappingIntervalsInto1()");
		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();

		for (int i = 0; i < 100; ++i)
			input.add(new Point2D.Double(i, i + 1));

		merger.merge(input);

		// Only 1 (merged) element
		assertEquals(1, input.size());

		// Check the merged element
		// Note that the elements are type "double"
		assertEquals(0.0, input.get(0).getX());
		assertEquals(100.0, input.get(0).getY());
	}

	/**
	 * Merge 100 overlapping and consecutive intervals into 1:<br>
	 * [0,1], [1,2], ...,[99,100] => [0,100]
	 */
	@Test
	public void testMerge10000ConsecutiveOverlappingIntervalsInto1() {
		System.out.println("MergerTest.testMerge10000ConsecutiveOverlappingIntervalsInto1()");
		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();

		for (int i = 0; i < 10000; ++i)
			input.add(new Point2D.Double(i, i + 1));

		merger.merge(input);

		// Only 1 (merged) element
		assertEquals(1, input.size());

		// Check the merged element
		// Note that the elements are type "double"
		assertEquals(0.0, input.get(0).getX());
		assertEquals(10000.0, input.get(0).getY());
	}

	/**
	 * Create 100 non-overlapping (and consecutive) intervals ([0.1,1], [1.1,2],
	 * ...,[98.1, 99], [99.1,100]) and try to merge them. The output should have the
	 * exact same size as the input (no merges).
	 */
	@Test
	public void testNoMergeNonOverlappingIntervals() {
		System.out.println("MergerTest.testNoMergeNonOverlappingIntervals()");
		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();

		for (int i = 0; i < 100; ++i)
			// the points don't overlap because of the 0.1 difference in the "X" coordinate
			input.add(new Point2D.Double(i + 0.1, i + 1));

		int originalSize = input.size();

		merger.merge(input);

		// Same amount of elements as before doing the merge, ergo no merge.
		assertEquals(originalSize, input.size());
	}

	/**
	 * Create 100 non-overlapping (and consecutive) intervals ([0.1,1], [1.1,2],
	 * ...,[98.1, 99], [99.1,100]) and try to merge them. The output should have the
	 * exact same size as the input (no merges).
	 */
	@Test
	public void testNoMerge10000NonOverlappingIntervals() {
		System.out.println("MergerTest.testNoMerge10000NonOverlappingIntervals()");
		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();

		for (int i = 0; i < 10000; ++i)
			// the points don't overlap because of the 0.1 difference in the "X" coordinate
			input.add(new Point2D.Double(i + 0.1, i + 1));

		int originalSize = input.size();

		merger.merge(input);

		// Same amount of elements as before doing the merge, ergo no merge.
		assertEquals(originalSize, input.size());
	}

}
