# Ants demonstration with AWT graphical user interface

## Launching the demonstration

* From SARL ID:
  1. Create a launch configuration of type "SARL Application"
  2. Set as argument the file containing the TSP in TSPLIB format
  3. As the main class, select `io.sarl.demos.ants.SimulationLauncher`

## Description of the software

ACO is program that aims to implement the ACO algorithm, developed by Dorigo, which simulates the behaviour of ants trying to find the shortest path.

This is acheived by using SARL, an agent oriented programming language.

The structure is as follows : The enviorement acts as the graph containing all the pheromone information, that ants can use to walk on. When an ant has found a path, it depositits pheromone based on how short the tour was. The shorter the path the more pheromones. When ants walk on the graph again, they make a probabilistic decision on what path to take, based on the levels of pheromone present, the more pheromone in an arc, the more likely it is to take said arc.

Ants in the program are also modeled by agents, and are very simplistic.

This software also contains a graphical interface written in AWT, showing in real time all the paths being taken by the ants, as well as the shortest path found.