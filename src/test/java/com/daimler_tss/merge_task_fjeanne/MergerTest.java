package com.daimler_tss.merge_task_fjeanne;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Merger.
 */
public class MergerTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public MergerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MergerTest.class);
	}

	/**
	 * Shouldn't break and return an empty list.
	 */
	public void testEmptyList() {
		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();

		merger.merge(input);

		assertEquals("The merged list should be empty (size == 0)", input.size(), 0);
	}

	/**
	 * Shouldn't break and return <code>null</code>.
	 */
	public void testNull() {
		Merger merger = new Merger();
		assertNull("The merged list should be null", merger.merge(null));
	}

	/**
	 * Two points that shouldn't merge.
	 */
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
		assertEquals(input.get(0), p0);
		assertEquals(input.get(1), p1);
	}

	/**
	 * [0,1] + [1,2] => [0,2]
	 */
	public void testMerge2Points() {
		Merger merger = new Merger();

		ArrayList<Double> input = new ArrayList<Point2D.Double>();
		Point2D.Double pMin = new Point2D.Double(0, 1);
		Point2D.Double pMax = new Point2D.Double(1, 2);

		input.add(pMin);
		input.add(pMax);

		merger.merge(input);

		// Only 1 (merged) element
		assertEquals("The list should 1 merged element", input.size(), 1);

		// Check the merged element
		assertEquals(input.get(0).getX(), pMin.getX());
		assertEquals(input.get(0).getY(), pMax.getY());
	}

}
