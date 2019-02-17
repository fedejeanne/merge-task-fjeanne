package com.daimler_tss.merge_task_fjeanne;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author fjeanne
 *
 */
public class Merger {

	/**
	 * The program takes 1 (and only 1) String parameter:
	 * <ul>
	 * <li>A path to a JSON file with the input intervals (check the <i>input</i>
	 * folder for some examples)</li>
	 * </ul>
	 * 
	 * @param args use only 1 parameter with the input list of intervals.
	 */
	public static void main(String[] args) {

		// TODO: remove all System.out/.err and use Log4J

		// If there are no parameters, show the usage hint
		if (args.length != 1) {
			System.out.println(
					"No input: run the program with exactly 1 parameter: the path to a text file with the input intervals.");
			System.exit(0); // Not an error, just no input.
		}

		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Double> intervals = null;

		// If the first parameter is a file, parse it
		File inputFile = new File(args[0]);
		if (inputFile.isFile()) {
			try {
				intervals = mapper.readValue(inputFile,
						mapper.getTypeFactory().constructCollectionType(List.class, Point2D.Double.class));
			} catch (JsonParseException | JsonMappingException e) {
				System.err.println(
						"Error parsing the intervals from the input file: '" + inputFile.getAbsolutePath() + "'");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Error reading the input file: '" + inputFile.getAbsolutePath() + "'");
				e.printStackTrace();
			}
		}

		if (intervals == null) {
			System.out.println("Couldn't parse the input, exiting.");

			// Error
			System.exit(1);
		}

		// input parsed correctly

		try {
			System.out.println("Input: " + mapper.writeValueAsString(intervals));
		} catch (JsonProcessingException e) {
			System.err.println("Couldn't print out the input in JSON format. Printing in raw (Java) format:");

			System.out.println("Input: " + intervals);
		}
		Merger merger = new Merger();
		merger.merge(intervals);

		try {
			System.out.println("Output: " + mapper.writeValueAsString(intervals));
		} catch (JsonProcessingException e) {
			System.err.println("Couldn't print out the output in JSON format. Printing in raw (Java) format:");

			System.out.println("Output: " + intervals);
		}

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
