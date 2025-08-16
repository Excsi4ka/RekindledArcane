package excsi.rekindledarcane.api.skill;

public class Point {

    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public static Point of(int x, int y) {
        return new Point(x, y);
    }
}
