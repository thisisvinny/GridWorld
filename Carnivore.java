/**
 * Created by vincentyu on 4/11/17.
 */

public class Carnivore extends Animal
{
    public static final int MAX_AGE = 15;

    public Carnivore(int x, int y)
    {
        super(x,y);
    }

    public int[][] checkCanEat(Grid g)
    {
        int[][] foodLocation = new int[1][2];

        if(this.getEnergy() < this.MAX_ENERGY_SO_STOP_EAT) {
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

            //check each possible move for food
            for (int i = 0; i < allPossibleMoves.length; i++) {
                if (checkValidEatMove(g, allPossibleMoves[i][0], allPossibleMoves[i][1]) == true) {
                    if (g.getEntity(allPossibleMoves[i][0], allPossibleMoves[i][1]) instanceof Herbivore) {
                        foodLocation[0][0] = allPossibleMoves[i][0];
                        foodLocation[0][1] = allPossibleMoves[i][1];
                        return foodLocation;
                    }
                }
            }
        }

        foodLocation[0][0] = -1;
        foodLocation[0][1] = -1;
        return foodLocation;
    }
}
