# Summer practice: visualization A* algorithm.
## About the application
This application has GUI. The result is the path between two graph vertexes, found using the A* algorithm. 
#### Application features
* Reading the graph from file
* Reading the graph from input window
* Running the A* algorithm
* Execution of the A* algorithm in step-by-step mode
* Editing a graph
  * Double right-click button: add new vertex with default name
  * Right-click button on vertex open a context menu where you can rename or delete vertex
  * You can choose two vertexes by double left-click button. The first selected vertex is the source the second is the destination
  * By selectiong two vertexes you can: run A* algorithm delete edge add edge
  * Clear graph
## Input format
n - number of vertexes, 1 ≤ n ≤ 30.

Next n vertexes and its coordinates: x y, 0 ≤ x ≤ 600, 0 ≤ y ≤ 530.

Then the weight of the edge between the vertexes in the format:

vertex1 vertex2 m

m - weight of the edge between two vertexes, 0 ≤ m ≤ 1.7*10\^308.

*Example:*

3

a 150 150

b 550 250

c 250 350

b a 5

a c 6

c b 10

b c 3

## Output format
To run the algorithm you need to select two vertexes - start and finish. The result is window with path and weight of path, the vertexes and edges from path are colored green. 

*Example:*

Selected "a" and "b":

Path: acb

Weight: 16.0

## Program laun
Load /app.jar/ file on your device. Open the terminal with this file in directory. Input: java -jar app.jar. Version of JRE 11.0.15 and higher.
