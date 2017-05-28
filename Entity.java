/**
 * Created by vincentyu on 4/11/17.
 */

public abstract class Entity
{
    private int x;
    private int y;
    private int age;
    private boolean aged;

    public Entity(int xCoord, int yCoord)
    {
        x = xCoord;
        y = yCoord;
        age = 0;
        aged = false;
    }

    public void setX(int a)
    {
        x = a;
    }

    public int getX()
    {
        return x;
    }

    public void setY(int a)
    {
        y = a;
    }

    public int getY()
    {
        return y;
    }

    public void setAge(int a)
    {
        age = a;
    }

    public int getAge()
    {
        return age;
    }

    public boolean getAged()
    {
        return aged;
    }

    public void setAged(boolean a)
    {
        aged = a;
    }

}
