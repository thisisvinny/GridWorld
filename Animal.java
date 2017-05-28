/**
 * Created by vincentyu on 4/11/17.
 */

import java.util.Random;

public abstract class Animal extends Entity {
    private int currentEnergy;
    private boolean moved, energyDepleted, gaveBirth;
    public static final int MAX_ENERGY_SO_STOP_EAT = 15;
    public static final int MIN_ENERGY_TO_GIVE_BIRTH = 10;
    public static final int MIN_AGE_TO_GIVE_BIRTH = 7;

    public Animal(int x, int y) {
        super(x, y);
        currentEnergy = 10;
        moved = false;
        energyDepleted = false;
    }

    public int getEnergy() {
        return currentEnergy;
    }

    public void setEnergy(int a) {
        currentEnergy = a;
    }

    public boolean getMoved() {
        return moved;
    }

    public void setMoved(boolean a) {
        moved = a;
    }

    public boolean getEnergyDepleted() {
        return energyDepleted;
    }

    public void setEnergyDepleted(boolean a) {
        energyDepleted = a;
    }

    public boolean gaveBirth() {
        return gaveBirth;
    }

    public void setGaveBirth(boolean a) {
        gaveBirth = a;
    }

    public boolean checkValidMove(Grid g, int newX, int newY) {
        //check out of bounds
        if (newX < 0 || newY < 0 || newX > g.GRID_NUM_ROWS - 1 || newY > g.GRID_NUM_COLS - 1)
            return false;
        if (g.getEntity(newX, newY) != null)
            return false;
        return true;
    }

    //for eating purposes
    public boolean checkValidEatMove(Grid g, int newX, int newY) {
        if (newX < 0 || newY < 0 || newX > g.GRID_NUM_ROWS - 1 || newY > g.GRID_NUM_COLS - 1)
            return false;
        if (g.getEntity(newX, newY) == null)
            return false;
        return true;
    }

    public void move(Grid g) {
        //8 possible moves, an x and y coordinate change for each
        int[][] allPossibleMoves = new int[8][2];
        //move right
        allPossibleMoves[0][0] = this.getX() + 1;
        allPossibleMoves[0][1] = this.getY();
        //move right-down
        allPossibleMoves[1][0] = this.getX() + 1;
        allPossibleMoves[1][1] = this.getY() + 1;
        //move right-up
        allPossibleMoves[2][0] = this.getX() + 1;
        allPossibleMoves[2][1] = this.getY() - 1;
        //move down
        allPossibleMoves[3][0] = this.getX();
        allPossibleMoves[3][1] = this.getY() + 1;
        //move up
        allPossibleMoves[4][0] = this.getX();
        allPossibleMoves[4][1] = this.getY() - 1;
        //move left
        allPossibleMoves[5][0] = this.getX() - 1;
        allPossibleMoves[5][1] = this.getY();
        //move left-down
        allPossibleMoves[6][0] = this.getX() - 1;
        allPossibleMoves[6][1] = this.getY() + 1;
        //move left-up
        allPossibleMoves[7][0] = this.getX() - 1;
        allPossibleMoves[7][1] = this.getY() - 1;

        boolean availableMove = false;
        //make sure there is even a valid move available
        for (int i = 0; i < allPossibleMoves.length; i++) {
            //break out of loop as soon as an available move is found
            if (checkValidMove(g, allPossibleMoves[i][0], allPossibleMoves[i][1]) == true) {
                availableMove = true;
                break;
            }
        }

        //if no move was found, exit move method
        if (availableMove == false)
            return;

        //valid move is available, so move
        int newX, newY;
        //keep generating new random moves until one is valid
        do {
            Random randomNumGenerator = new Random();
            int randomMove = randomNumGenerator.nextInt(8);

            newX = allPossibleMoves[randomMove][0];
            newY = allPossibleMoves[randomMove][1];
        } while (checkValidMove(g, newX, newY) == false);

        //valid move has been found, so perform this move now
        g.removeEntity(this.getX(), this.getY());
        this.setMoved(true);
        this.setX(newX);
        this.setY(newY);
        g.addEntity(this);
    }

    public abstract int[][] checkCanEat(Grid g);

    public void eat(Grid g) {
        int[][] temp = checkCanEat(g);
        int[][] foodLocation = new int[1][2];
        foodLocation[0][0] = temp[0][0];
        foodLocation[0][1] = temp[0][1];

        if (foodLocation[0][0] == -1 && foodLocation[0][1] == -1)
            return;
        else {
            g.removeEntity(this.getX(), this.getY());
            g.removeEntity(foodLocation[0][0], foodLocation[0][1]);
            this.setMoved(true);
            this.setX(foodLocation[0][0]);
            this.setY(foodLocation[0][1]);
            this.setEnergy(this.getEnergy() + 5);
            g.addEntity(this);
        }
    }

    public boolean canGiveBirth() {
        if (this.getAge() >= MIN_AGE_TO_GIVE_BIRTH && this.getEnergy() >= MIN_ENERGY_TO_GIVE_BIRTH)
            return true;
        else
            return false;
    }

    public void giveBirth(Grid g)
    {
        int birthX = 0;
        int birthY = 0;

        //check for possible birth locations
        //8 possible birth locations, an x and y coordinate change for each
        int[][] allBirthLocations = new int[8][2];
        //right
        allBirthLocations[0][0] = this.getX() + 1;
        allBirthLocations[0][1] = this.getY();
        //right-down
        allBirthLocations[1][0] = this.getX() + 1;
        allBirthLocations[1][1] = this.getY() + 1;
        //right-up
        allBirthLocations[2][0] = this.getX() + 1;
        allBirthLocations[2][1] = this.getY() - 1;
        //down
        allBirthLocations[3][0] = this.getX();
        allBirthLocations[3][1] = this.getY() + 1;
        //up
        allBirthLocations[4][0] = this.getX();
        allBirthLocations[4][1] = this.getY() - 1;
        //left
        allBirthLocations[5][0] = this.getX() - 1;
        allBirthLocations[5][1] = this.getY();
        //left-down
        allBirthLocations[6][0] = this.getX() - 1;
        allBirthLocations[6][1] = this.getY() + 1;
        //left-up
        allBirthLocations[7][0] = this.getX() - 1;
        allBirthLocations[7][1] = this.getY() - 1;

        boolean availableBirthLocation = false;
        //make sure there is a valid birth location
        for (int i = 0; i < allBirthLocations.length; i++) {
            //break out of loop at the first instance an available location is found
            if (checkValidMove(g, allBirthLocations[i][0], allBirthLocations[i][1]) == true) {
                birthX = allBirthLocations[i][0];
                birthY = allBirthLocations[i][1];
                availableBirthLocation = true;
                break;
            }
        }

        //if no birth location was found, don't give birth
        if (availableBirthLocation == false)
            return;

        //else give birth
        this.setEnergy(this.getEnergy() - 3);
        if (this instanceof Carnivore) {
            Carnivore baby = new Carnivore(birthX, birthY);
            baby.setMoved(true);
            baby.setEnergyDepleted(true);
            g.addEntity(baby);
        } else if (this instanceof Herbivore) {
            Herbivore baby = new Herbivore(birthX, birthY);
            baby.setMoved(true);
            baby.setEnergyDepleted(true);
            g.addEntity(baby);
        }
    }
}
