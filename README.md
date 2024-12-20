# Dominating Set Solver

### Graphe final Result (Capture d’écran 2024-12-15 235939.png)

![Result](./Capture d’écran 2024-12-15 235939.png)

## Description

This project is focused on solving the Minimum Dominating Set problem, a classic NP-hard problem in graph theory. The goal is to find the smallest subset of vertices in a given graph that dominates all other vertices, i.e., every vertex is either in the set or has a neighbor in the set.

The algorithm implemented in this project follows the approach described in the research paper "A PTAS for the Minimum Dominating Set Problem in Unit Disk Graphs" by Tim Nieberg and Johann Hurink.

## Key Features

1. **Polynomial-Time Approximation Scheme (PTAS)**: The algorithm provides a (1+ε)-approximation of the minimum dominating set in polynomial time, where ε is a user-specified parameter.
2. **Geometric Representation Independence**: Unlike previous approaches, this algorithm does not require a geometric representation of the graph (i.e., the positions of the vertices in the plane) as part of the input.
3. **Robustness**: The algorithm is robust in the sense that it either returns a (1+ε)-approximate minimum dominating set or a certificate proving that the input graph is not a unit disk graph.
4. **Extensibility**: The PTAS can be easily adapted to other classes of related geometric intersection graphs.

## Algorithm Overview

The algorithm consists of the following key steps:

1. **Construction of a 2-Separated Collection**: The graph is partitioned into smaller subgraphs by creating a 2-separated collection of vertex subsets. This collection has the property that vertices in different subsets are at least 3 hops apart.
2. **Computation of Local Dominating Sets**: For each subset in the 2-separated collection, a minimum dominating set is computed.
3. **Merging of Local Dominating Sets**: The union of the local dominating sets forms a global (1+ε)-approximate minimum dominating set.

The algorithm is designed to exploit the geometric properties of unit disk graphs to achieve polynomial-time complexity, even without a geometric representation of the input graph.

## Implementation Details

The main components of the implementation are:

1. `DefaultTeam.java`: This class contains the core implementation of the PTAS algorithm, including the construction of the 2-separated collection, the computation of local dominating sets, and the merging of these sets to obtain the global solution.
2. `Evaluation.java`: This class provides utility methods for validating the correctness of the computed dominating set, such as checking if the set is indeed a valid dominating set.

The implementation follows best practices in Java development, including the use of data structures like `HashSet` and `ArrayList` for efficient set operations, and the adherence to principles of object-oriented design.

## Usage

To use the Dominating Set Solver, follow these steps:

1. Ensure you have Java JDK 8+ and Apache Ant installed.
2. Clone the repository and navigate to the project directory:

   ```
   git clone https://github.com/ottodpc/Dominating-Set.git
   cd Dominating-Set
   ```

3. Compile the project using Ant:

   ```
   ant clean
   ant run
   ```

4. The solver will start, and you can interact with it using the following commands:
    - `d`: Calculate the dominating set
    - `g`: Run the provided tests
    - `h`, `j`, `k`, `l`: Move the points in the graph

## Performance and Limitations

The algorithm provides a (1+ε)-approximation of the minimum dominating set in polynomial time. However, as the Minimum Dominating Set problem is NP-hard, the algorithm's running time can still be exponential in the worst case.

Additionally, the algorithm's quality is not guaranteed to be optimal, as it depends on the specific instances of the input graph.

## Future Improvements

Potential areas for improvement include:

1. **Parallelization**: Exploring parallel computation techniques to further improve the algorithm's performance.
2. **Machine Learning Integration**: Investigating the use of machine learning techniques to enhance the algorithm's decision-making process and solution quality.
3. **Multi-objective Optimization**: Extending the algorithm to handle additional constraints or objectives, such as minimizing the number of vertices in the dominating set while also considering other graph properties.

## Author

OTTO Dieu-Puissant Cyprien

## License

This project is licensed under the MIT License.