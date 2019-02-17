package com.daimler_tss.merge_task_fjeanne;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author fjeanne
 *
 */
public class Merger {
	public static void main(String[] args) {
	}

	/**
	 * Merge a list of input intervals. Two intervals will be merged if they
	 * overlap, <i>e.g.</i> the result of merging <i>[1,2]</i> and <i>[2,3]</i> will
	 * be <i>[1,3]</i>.<br/>
	 * <br/>
	 * <h1>Some assumptions</h1>
	 * <ul>
	 * <li>All segments are <b>inclusive</b>, <i>i.e.</i> the left and right limits
	 * are within the segment itself</li>
	 * <li>The input list can be modified.</li>
	 * <li>All the <i>[X,Y]</i> points in the input list fulfill the following rule:
	 * <i>X <= Y</i></li>
	 * </ul>
	 * 
	 * @param input The intervals to arrange and merge
	 * @return the same list provided as input but with the intervals ordered and
	 *         merged.
	 */
	public List<Point2D.Double> merge(List<Point2D.Double> input) {

		if (input == null)
			return null;

		// TODO fjeanne: validate the input. The following must be true for every point:
		// X <= Y

		// sort the input list by comparing the X point
		Collections.sort(input, new Comparator<Point2D.Double>() {

			public int compare(Point2D.Double o1, Point2D.Double o2) {
				return (int) (o1.getX() - o2.getX());
			}
		});

		// Now do the merging
		if (input.size() > 1) {

			int i = 0;
			while (i < input.size() - 1) {

				// compare the current element with the next one
				Point2D.Double currElem = input.get(i);
				Point2D.Double nextElem = input.get(i + 1);

				if (currElem.getY() >= nextElem.getX()) {
					// merge the elements and replace in the result list
					Point2D.Double mergedElem = new Point2D.Double(currElem.getX(),
							Math.max(currElem.getY(), nextElem.getY()));

					input.remove(i);
					input.remove(i);

					input.add(i, mergedElem);
				} else {
					// no changes. Go to the next element
					++i;
				}
			}
		}
		return input;
	}
}
