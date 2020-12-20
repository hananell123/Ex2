# README

### introduction
This project is part of an object-oriented programming course in the degree of Computer Science. in the first part of the project we were asked to implement an interface of a directed weighted graph and algorithms such a connectivity check, shortest path ect'.
In the second part we have a game with several senarios, in ech senario we get map (build as a directed weighted graph) and randomalic points which represent points in the game that we must achieve. In addition, each stage has a number of agents (in our implementation it is Timon and Pumbaa). Our task was to create an algorithm that would make agents achieve as many points as possible at a given time (a fixed time for each stage).

### What's in the project
In this project you will find 2 parts. The first part deals with the weighted directed graph structure while the second part deals with the game


## First part
This chapter deals with a data structure directed weighted graph, this structure allows to store information and represent a system that allows to store information with a certain connectivity on the distance / weights 

**DWGraph_DS implementaion**
The graph is implemented with the help of departments:
* NodeData - which represents a vertex in a graph with a unique ID, and a location (geoLocation (x, y, z)), in order to enable relative positioning between vertices - used in the second part
* edge - Represents an arc between two vertices with direction (source and destination) and weight

**Functionality**
You can add and delete a vertex, connect and disconnect between vertices. You can also get up-to-date information such as the number of vertices and arcs and the number of actions performed on the graph



**Algorithems**
In order to allow optimal use of the structure we implemented algorithms that give us a lot of information about the graph * after * built

* IsConnect - check the connectivity of the graph - Are there path **from** each vertex
 **to** each vertex

* Shortest path/Shortest path dis - Checks the shortest route between 2 vertices and returns it as a list (Shortest path) or returns only the distance(Shortest path dis)

* Copy - enable to creat a deep copy of the graph if you want to change it with out delet the original graph

* Save/Load - Allows you to save a given graph or load an old graph (using the Json format).


## second part
* general
In this part of the project we were asked to build an algorithm that manages to handle a game and achieve the best result.
In addition we were asked to build a Frame that would represent the conduct of the game in real time and display it in a window

* Algorithem
The conduct of the game is in move commands in which each agent is given a new target for perception / continues his progress in the current trajectory.
The project has functions that make the game move, but the main part is the functions that optimize the movement of agents according to locations and determines the sleep times of the game thread in order to save move commands (an explanation of the algorithms can be found in thw wiki page).

* Frame
The graphic part shows us in real time the game scenarios, the movements of the agents, the location of the insects.
  Also displays information about the progress of the game such as how many points each agent collected, his speed and timer and how many are left for the end of the game