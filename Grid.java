/**
 * Created by vincentyu on 4/11/17.
 */

import java.util.Random;

public class Grid {
    private Entity[][] grid;
    public final int GRID_NUM_ROWS; //max x value
    public final int GRID_NUM_COLS; //max y value
    private int clock;

    //Default size is 30x30
    public Grid() {
        grid = new Entity[30][30];
        GRID_NUM_ROWS = grid.length;
        GRID_NUM_COLS = grid[0].length;
        clock = 0;
    }

    public Grid(int a) {
        grid = new Entity[a][a];
        GRID_NUM_ROWS = grid.length;
        GRID_NUM_COLS = grid[0].length;
        clock = 0;
    }

    public void addEntity(Entity a) {
        grid[a.getX()][a.getY()] = a;
    }

    public void removeEntity(int x, int y) {
        grid[x][y] = null;
    }

    public Entity getEntity(int x, int y) {
        return grid[x][y];
    }

    //one cycle has passed
    public void update()
    {
        Random randomNumGenerator = new Random();

        for(int i=0; i<grid.length; i++)
        {
            for(int j=0; j<grid[i].length; j++)
            {
                if (grid[i][j] != null)
                {
                    //If an entity didn't age yet, age it entities by 1
                    if(grid[i][j].getAged() == false)
                    {
                        grid[i][j].setAge(grid[i][j].getAge() + 1);
                        grid[i][j].setAged(true);
                    }

                    //If an animal can give birth, it will give birth
                    //If an animal didn't lose energy yet, deplete its energy by 1
                    if (grid[i][j] instanceof Animal)
                    {
                        Animal a = (Animal)grid[i][j];
                        if(a.canGiveBirth() == true)
                        {
                            a.giveBirth(this);
                            a.setGaveBirth(true);
                        }
                        if(a.getEnergyDepleted() == false)
                        {
                            a.setEnergy(a.getEnergy() - 1);
                            a.setEnergyDepleted(true);
                        }
                    }

                    if (grid[i][j] instanceof Carnivore)
                    {
                        Carnivore c = (Carnivore) grid[i][j];

                        //Remove if carnivore has reached max age in their previous cycle (hence +1)
                        if (c.getAge() == c.MAX_AGE+1)
                        {
                            removeEntity(i, j);
                        }
                        //Remove if carnivore has reached no energy in their previous cycle(hence -1)
                        else if (c.getEnergy() == -1)
                        {
                            removeEntity(i,j);
                        }
                        //Since my animals are dying immediately after the current cycle's aging/energy depletion, I've given them one more cycle
                        //to perform any actions they can during their final cycle

                        //Move carnivore if eligible
                        else
                        {
                            int canMove = randomNumGenerator.nextInt(10);
                            //40% chance this carnivore can move
                            if(canMove < 4) {
                                if (c.getMoved() == false) {
                                    int[][] temp = c.checkCanEat(this);
                                    int[][] possibleFoodLocation = new int[1][2];
                                    possibleFoodLocation[0][0] = temp[0][0];
                                    possibleFoodLocation[0][1] = temp[0][1];
                                    if (possibleFoodLocation[0][0] != -1 && possibleFoodLocation[0][1] != -1)
                                        c.eat(this);
                                    else
                                        c.move(this);
                                }
                            }
                        }
                    }
                    else if (grid[i][j] instanceof Herbivore)
                    {
                        Herbivore h = (Herbivore) grid[i][j];
                        //Remove if herbivore has reached max age in their previous cycle (hence +1)
                        if (h.getAge() == h.MAX_AGE+1)
                        {
                            removeEntity(i, j);
                        }
                        //Remove if herbivore has reached no energy in their previous cycle (hence -1)
                        else if (h.getEnergy() == -1)
                        {
                            removeEntity(i, j);
                        }

                        //Move herbivore if eligible
                        else
                        {
                            int canMove = randomNumGenerator.nextInt(10);
                            //40% chance this herbivore can move
                            if(canMove < 4) {
                                if (h.getMoved() == false) {
                                    int[][] temp = h.checkCanEat(this);
                                    int[][] possibleFoodLocation = new int[1][2];
                                    possibleFoodLocation[0][0] = temp[0][0];
                                    possibleFoodLocation[0][1] = temp[0][1];
                                    if (possibleFoodLocation[0][0] != -1 && possibleFoodLocation[0][1] != -1)
                                        h.eat(this);
                                    else
                                        h.move(this);
                                }
                            }
                        }
                    }
                    else if (grid[i][j] instanceof Plant) {
                        Plant p = (Plant) grid[i][j];
                        if (p.getAge() == p.MAX_AGE) {
                            removeEntity(i, j);
                        }
                    }
                }
            }
        }

        int spawnPlants = randomNumGenerator.nextInt(10);
        //spawn 5% more plants 50% of the time
        if(spawnPlants < 4)
        {
            int spawnX, spawnY;
            int numPlantsToSpawn = (int)(GRID_NUM_ROWS*GRID_NUM_COLS*.04);
            for(int i=0; i<numPlantsToSpawn; i++)
            {
                do {
                    spawnX = randomNumGenerator.nextInt(GRID_NUM_ROWS);
                    spawnY = randomNumGenerator.nextInt(GRID_NUM_COLS);
                } while (this.getEntity(spawnX,spawnY) != null);

                this.addEntity(new Plant(spawnX, spawnY));
            }
        }


        //set moved, aged, energyDepleted, gaveBirth booleans to false for all entities so they can act in the next update
        for(int i=0; i<grid.length; i++)
        {
            for(int j=0; j<grid[i].length; j++)
            {
                if(grid[i][j] != null)
                {
                    grid[i][j].setAged(false);
                }
                if (grid[i][j] instanceof Animal)
                {
                    Animal a = (Animal)grid[i][j];
                    a.setMoved(false);
                    a.setEnergyDepleted(false);
                    a.setGaveBirth(false);
                }
            }
        }

        clock++;
    }

    @Override
    public String toString()
    {
        String s = "";
        for(int i=0; i<grid.length; i++)
        {
            for(int j=0; j<grid[i].length; j++)
            {
                if(grid[i][j] instanceof Carnivore)
                    s += "@ ";
                else if(grid[i][j] instanceof Herbivore)
                    s += "& ";
                else if(grid[i][j] instanceof Plant)
                    s += "* ";
                else
                    s += ". ";
            }
            s += "\n";
        }
        return s;
    }
}
