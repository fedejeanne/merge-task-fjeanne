# merge-task-fjeanne
## Description
## How to run the app
### For developers
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

## TO-DO
* Simulate a bug fix
* Complete this documentation.
  * Answer all questions in the exercise