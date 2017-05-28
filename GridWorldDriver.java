/**
 * Created by vincentyu on 4/11/17.
 */

/*
Create a class Herbivore and a class Carnivore.
Each herbivore eats Plant to survive and each carnivore eats herbivore. Herbivores and carnivores can move to a location around themselves.
The speed of movement of carnivores is faster than herbivores. Animals and plants can live for a certain amount of time and after that they
will die. Animals have level of energy and if the energy of an animal is less than a specific amount it will die. Animals can get birth to
other animals if they are in a certain range of ages and they have enough energy. If energy of an animal is higher than a certain amount it
will not eat anything. Plants grow in random locations at certain times ( e.g. a random number between 3 to 5 clocks).
Create a simulation for above scenario. Inside your simulation you should have a concept of the clock, at each clock certain events happen.
You have to use all the object-oriented concepts that you learned so far, encapsulation and hierarchy are necessary.
Run your simulation in a loop with a certain number of clocks. At each clock print the earth in a good organized format. Use these characters:
Carnivore:      @
Herbivore:      &
Plant:              *
Free Space:   .
Put one space ' ' between each two characters for readability.
I have attached an example of the simulation that uses a grid of 5 x 5 locations. The simulation runs for 4 cycles. You can see all the steps in the that attachment:
Herbivore moves every two cycles, Carnivore moves every cycle. Plant grows every three cycles. The initial energy of all the animals is 3.
Carnivore gets birth after age 4 cycle (It ate a herbivore and got 5 energy points so it has enough energy to get birth to a new animal), the
energy of the carnivore drops by three and the new-born animal gets three initial energy. Herbivore starves at the last cycle.
Your simulation should be larger with more iterations (More clocks) and remember to put the right parameters to keep the ecosystems alive.
Its a very good idea to start with a small simulation and extend it. Also I expect a more realistic simulation from you; for example in each
cycle it should reduce the amount of energy from each creature or the movement of carnivores or herbivores or production of plants should
be more realistic so the simulation becomes more smoother and easier to follow.
An acceptable size for simulation starts from 30X30 and acceptable iteration starts from 30.
*/

/*
a. User decides on the size of simulation and the number of iterations.
b. Use inheritance and polymorphism to make your implementation simpler.
c. Put some randomness in the event (e.g. carnivore most of the time moves with speed of 3 but sometimes it
   should move with speed to 4 or sometimes with speed of 2); the randomness is needed for all variables.
d. Manage to have balanced in the value of variable to keep the environment active.
*/

import java.util.Random;
import java.util.Scanner;

public class GridWorldDriver
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your preferred size for simulation?");
        int size = scanner.nextInt();
        System.out.println("What is the total number of iterations?");
        int numIterations = scanner.nextInt();

        //Initializing the Grid
        Grid grid = new Grid(size);
        Random randomNumGenerator = new Random();

        //15% of grid is carnivores
        //25% of grid is herbivores
        //50% of grid is plants
        int numCarnivores = (int)(size*size*.15);
        int numHerbivores = (int)(size*size*.25);
        int numPlants = (int)(size*size*.5);

        int spawnX, spawnY;

        for(int i=0; i<numCarnivores; i++)
        {
            do {
                spawnX = randomNumGenerator.nextInt(grid.GRID_NUM_ROWS);
                spawnY = randomNumGenerator.nextInt(grid.GRID_NUM_COLS);
            } while(grid.getEntity(spawnX,spawnY) != null);
            grid.addEntity(new Carnivore(spawnX, spawnY));
        }

        for(int i=0; i<numHerbivores; i++)
        {
            do {
                spawnX = randomNumGenerator.nextInt(grid.GRID_NUM_ROWS);
                spawnY = randomNumGenerator.nextInt(grid.GRID_NUM_COLS);
            } while(grid.getEntity(spawnX,spawnY) != null);
            grid.addEntity(new Herbivore(spawnX, spawnY));
        }

        for(int i=0; i<numPlants; i++)
        {
            do {
                spawnX = randomNumGenerator.nextInt(grid.GRID_NUM_ROWS);
                spawnY = randomNumGenerator.nextInt(grid.GRID_NUM_COLS);
            } while(grid.getEntity(spawnX,spawnY) != null);
            grid.addEntity(new Plant(spawnX, spawnY));
        }

        System.out.println("Init Board: ");
        System.out.println(grid);

        //Grid iterations begin
        for(int i=0; i<numIterations; i++) {
            System.out.println("Cycle: " + i);
            grid.update();
            System.out.println(grid);
        }
    }
}
