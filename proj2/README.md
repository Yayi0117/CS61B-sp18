# CS61B: Build Your Own Game (BYoG)

## Project Overview

This project is part of the CS61B course (Data Structures) at UC Berkeley. The goal of the project, named "Build Your Own Game" (BYoG), is to apply data structures and algorithms to create a simple game. The project emphasizes object-oriented programming, recursion, and efficient use of data structures.

## Game Description

The game created in this project is a roguelike dungeon crawler. Players navigate through a procedurally generated world, collecting items and avoiding enemies. The game world consists of interconnected rooms and corridors, randomly generated using algorithms covered in the course.

## Features

1. **Procedural Generation**: The game world is generated using a random seed, ensuring a unique experience in every playthrough. The algorithm places rooms and corridors, ensuring they are connected and accessible.
   
2. **Tile-Based Rendering**: The game world is displayed using a tile-based system, where each tile represents a different element (e.g., floor, wall, player, enemy, items).

3. **User Interaction**: Players can navigate the world using keyboard inputs. The controls allow the player to move in four directions, collect items, and interact with the environment.

4. **Data Structures**: The project employs various data structures, including:
   - **Graphs**: Used to represent the game world and ensure all rooms are connected.
   - **Queues**: Implemented in the pathfinding algorithm for enemy movements.
   - **Lists and Arrays**: Utilized for managing game entities and rendering the game world.

5. **Save and Load**: Players can save their progress and load previous game states, allowing for a persistent gaming experience across sessions.

## My Contribution

As part of the CS61B course, I completed the following tasks:

1. **World Generation Algorithm**: Developed a random world generation algorithm that creates a new game world for each playthrough. This involved implementing and optimizing the placement of rooms and corridors.

2. **Tile-Based Rendering**: Implemented the tile-based rendering system, ensuring that the game world is accurately represented on the screen.

3. **Player and Enemy Mechanics**: Designed and coded the player movement mechanics and the basic AI for enemy movements, ensuring they respond to player actions.

4. **Data Structure Implementation**: Integrated various data structures such as graphs, queues, and lists to manage the game state and entities efficiently.

5. **User Interface**: Implemented the basic user interface for player interactions, including the save and load functionality.
