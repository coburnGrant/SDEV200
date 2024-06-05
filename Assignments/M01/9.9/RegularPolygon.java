public class RegularPolygon {
    
    /** Number of sides the polygon has. Defaults to 3 */
    private int n = 3;

    /** Length of the side of the polygon. Defaults to 1 */
    private double side = 1;

    /** x-coordinate of the polygon's center. Defaults to 0  */
    private double x = 0;

    //* y-coordinate of the polygon's center. Defaults to 0 */
    private double y = 0;

    /**
     * Construct a regular polygon with default values. 
     */
    public RegularPolygon() {
    }

    /**
     * Construct a regular polygon with number of sides, and side length.
     */
    public RegularPolygon(int n, double side) {
        this.n = n;
        this.side = side;
    }

    /**
     * Construct a regular polygon with number of sides, side length,
     * x coordinate, and y coordinate.
     */
    public RegularPolygon(int n, double side, double x, double y) {
        this.n = n;
        this.side = side;
        this.x = x;
        this.y = y;
    }

    /** Returns the perimeter of the polygon */
    public double getPerimeter() {
        double perimeter = (side * n);
        return perimeter;
    }

    /** Returns the area of the polygon */
    public double getArea(){
        return (n * Math.pow(side, 2) / (4 * Math.tan(Math.PI / n)));
    }
}
