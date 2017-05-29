import java.util.Random;
import java.util.Scanner;

class GridThread extends Thread {
	private Thread t;
	private String name;

	GridThread(String name) {
		this.name = name;
	}

	public void run() {
		System.out.println("GridThread Running");
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
        try {
	        for(int i=0; i<numIterations; i++) {
	            System.out.println("Cycle: " + i);
	            grid.update();
	            System.out.println(grid);
	            t.sleep(1000);
	        }
    	} catch(InterruptedException e) {
    		System.out.println("Sleeping for 1000ms");
    	}
        System.out.println("GridThread Finished");
	}

	public void start() {
		if(t == null) {
			t = new Thread(this, name);
			System.out.println("GridThread Started");
			t.start();
		}
	}
}

class InputThread extends Thread {
	private Thread t;
	private String name;

	InputThread(String name) {
		this.name = name;
	}

	public void run() {

		/* REPLACE THIS IMPLEMENTATION WITH KEYBOARD INPUTS */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		/* ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
		Scanner scanner = new Scanner(System.in);
		String input = "";
		
		System.out.println("InputThread Started");
		while(!(input.equals("exit"))) {
			System.out.println("InputThread: ");
			input = scanner.nextLine();
		}
		System.out.println("InputThread Finished");
	}

	public void start() {
		if(t == null){
			t = new Thread(this, name);
			System.out.println("InputThread Started");
			t.start();
		}
	}
}

public class GameThread {
	public static void main(String[] args) {
		GridThread game = new GridThread("GameThread");
		InputThread input = new InputThread("InputThread");
		game.start();
		input.start();	
	}
}