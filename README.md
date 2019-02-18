# merge-task-fjeanne
## Description

This app takes overlapping intervals as input, re-arranges and then merges them into non-overlapping intervals. The input must be provided via a file and in JSON format, like this: 

```
[
  {
    "x": 25,
    "y": 30
  },
  {
    "x": 2,
    "y": 19
  },
  {
    "x": 14,
    "y": 23
  },
  {
    "x": 4,
    "y": 8
  }
]
```

For more examples, take a look at the _input_ folder in this repository.

The output of the program is written in JSON and it presents the list of (ordered) intervals after the merge operation.

## How to run the app
### From you IDE
Import the Maven project into your favorite IDE and run the `main(...)` method within the `com.daimler_tss.merge_task_fjeanne.Merger` class with the following parameters:
* The path to a JSON file holding the list of intervals, like: `input/input1.txt`

### As a stand-alone Java app
In order to run the app from the command line, you need to run the following `maven` command:

```
mvn package exec:java -Dexec.args="input/input1.txt"
```
This will create the necessary JAR file and run the program using the file `input/input1.txt` as input (you can choose another one to try the app).

### Using the Maven wrapper
If you don't have Maven installed in your computer, you can use the Maven wrapper by running:
```
./mvnw package exec:java -Dexec.args="input/input1.txt"
```

## Complexity of the algorithm
See: [Big-O notation in Wikipedia](https://en.wikipedia.org/wiki/Big_O_notation)

The application does 2 things in order to merge the input intervals:
1. Order the input intervals in ascending order by comparing the left-most (smaller) point of the interval. This part uses Java's `Collections.sort` method so the complexity is **O(n * log(n))**
2. Traverse the ordered list and merge the intervals if necessary. This is a linear traverse so the complexity is **O(n)**

The overall complexity of the program is therefore the [sum](https://en.wikipedia.org/wiki/Big_O_notation#Sum): **O(n * log(n))**

## Dedicated time
All in all, for the completion of this task I needed around 6.5 (non-consecutive) hours. I attempted to simulate a team working together and not just 1 programmer pushing everything to the _master_ branch. There's even a "feature request" (under _Issues_ in this repository) to "Dockerize the app": feel free to vote up :) 

* Coding, (JUnit) testing & documenting: 3 hs
* GIT branching, pull requests, merging: 1 hr
* DevOps (Maven, memory usage, CPU usage analysis): 1.5 hs
* GIT documentation: 1 hr

## Running time
For more details, check my other project: [merge-task-fjeanne-benchmark](https://github.com/fedejeanne/merge-task-fjeanne-benchmark#current-results)

These are the current test cases (names, number of input intervals, number of output intervals and throughput):

* `testMergePoints_1`: 4 (input) => 1 (output), **5694714,477 ops/s**
* `testMergeConsecutiveOverlappingIntervalsInto1`: 100 => 1, **130749,545 ops/s**
* `testNoMergeNonOverlappingIntervals`: 100 => 100, **495702,389 ops/s**
* `testMerge10000ConsecutiveOverlappingIntervalsInto1`: 10000 => 1, **55,218 ops/s**
* `testNoMerge10000NonOverlappingIntervals`: 10000 => 10000, **545,255 ops/s**

## Memory usage
This app has been designed to use as little memory as possible, that's why once the input intervals are parsed and uploaded into memory (in the form of a `List<Point2D.Double>`), the same list is used to hold the resulting intervals (_i.e._ there's no `new` operation).
In a nutshell, the memory usage of the applicationn in _Big-O_ notation is **O(2 * n**) where _n_ is the amount of input intervals.

### Explanation
Since every interval in the input list is mapped to a `Point2D.Double` and this class has 2 `double`s called _x_ and _y_, the main factor in the memory usage is determined by the amount of points in the input intervals.
The size of a `double` in Java is 8 bytes, therefore the estimated required memory (_Big-O_ notation) in bytes is: 

_B = 2 * n * 8_

Where _n_ is the amount of input intervals.

## Robustness
According to the previous analysis on memory usage, large inputs should be no problem for this program since the memory consumption is near 16 Bytes (2 `double`s) per input interval, _i.e._ a HEAP of 16 MB would be enough to allocate 1048576 input intervals.

In the real world, one could run this as a ReST service inside a Kubernetes cluster, analyze the real memory consumption and let it scale up/down dynamically.

# Last words
This work is not even near to be production-ready, but I tried to keep it as clean and usable as possible within 1 work-day's effort. Some extra refinements, from the top of my head:

* Refactoring: do the whole "input reading" a bit cleaner in the code
* Sanitizing the input: removing/fixing invalid intervals
* Dockerize the app: to be able to take it to IaaS/PaaS systems like K8s
* Make it a ReST service (with Spring boot): this would make it easier to integrate with other services

To name a few.