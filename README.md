# private-squid-squid1

12 CS FINAL PROJECT

AUTHORS: THOMAS ROSEBROUGH & IAN STEINER

Our game is inspired by the popular Nitrome flash game, "Roly-Poly". The idea is that the round character is only controled by
gravity, and to complete each level, the player must rotate the entire map to get the character to fall to the exit. Each is
level is a 2D array of tiles, that can be edited and saved in our MapEditor.

The only controls are the mouse, which in the final version will use buttons and a slider in the UI. The current rotating
controls already use the x position of the mouse, which is only a step away from a simple UI element.

The physics is managed by a vector class which has doubles direction and magnitude. The player class has a vector for velocity,
a final vector for gravity, and it constantly is adding gravity to its velocity vector, unless its touching a wall, in which
case it also adds normal vector, creating an illusion of sliding along a surface. Not all of these have been implemented yet,
but the structure for it is in place.

Our classes are in four packages: FinalGame (which holds Game
