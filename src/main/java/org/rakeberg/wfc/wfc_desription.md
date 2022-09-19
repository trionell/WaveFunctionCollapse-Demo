## Wave Function Collapse - Implementation description (V1)
1. Create a 3-dimensional array representing the x-y coordinates of a 2D map and the third 
axis represents the entropy (options) of the cell.
2. Populate the array with full entropy (all options).
3. Pick one random cell and one random option, making the entropy 1 in that cell.
4. Add that cell to a stack/queue.
5. Pop/dequeue the stack/queue.
6. Apply constraints on the neighboring cells (up, down, left, right).
   1. If any of the neighbors get an entropy of 1, add that cell to the stack/queue.
   2. If none of the neighbors reaches an entropy of 1 and there is no cells in the queue, 
      pick one of the neighbors and pick one of the remaining options. Put the cell in the stack/queue.
7. Repeat from 5 until entire map has entropy of 1.