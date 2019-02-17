# merge-task-fjeanne
## Description
## How to run the app
### For developers
Import the Maven project into your favorite IDE and run it with the following parameters.
```
TO-DO: take input parameters
```
### As a stand-alone Java app
TO-DO: how to run it and parse the input list?
## Complexity of the algorithm
See: [Big-O notation in Wikipedia](https://en.wikipedia.org/wiki/Big_O_notation)

The application does 2 things in order to merge the input intervals:
1. Order the input intervals in ascending order by comparing the left-most (smaller) point of the interval. This part uses Java's `Collections.sort` method so the complexity is **O(n * log(n))**
2. Traverse the ordered list and merge the intervals if necessary. This is a linear traverse so the complexity is **O(n)**

The overall complexity of the program is therefore the [sum](https://en.wikipedia.org/wiki/Big_O_notation#Sum): **O(n * log(n))**