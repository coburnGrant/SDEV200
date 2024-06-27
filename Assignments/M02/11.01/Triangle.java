import geometry.GeometricObject;

public class Triangle extends GeometricObject {
    /** Side 1 of the triangle */
    private double side1 = 1.0;

    /** Side 2 of the triangle */
    private double side2 = 1.0;

    /** Side 3 of the triangle */
    private double side3 = 1.0;

    /** Default constructor. All sides default to 1 */
    public Triangle() {
    }

    /** Construct a Triangle with 3 side lengths */
    public Triangle(double side1, double side2, double side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    /** Getter for side 1 */
    public double getSide1() {
        return side1;
    }

    /** Getter for side 2 */
    public double getSide2() {
        return side2;
    }

    /** Getter for side 3 */
    public double getSide3() {
        return side3;
    }

    /** Method to get the area of the triangle */
    @Override
    public double getArea() {
        double s = getPerimeter() / 2;
        double area = Math.sqrt(s * (s - side1) * (s - side2) * ( s - side3));
        return area;
    }

    /** Method to get the perimeter of the triangle */
    @Override
    public double getPerimeter() {
        return (side1 + side2 + side3);
    }

    /** To string method for a description of the triangle's side lengths. */
    @Override
    public String toString() {
        return "Triangle: side1 = " + side1 + " side2 = " + side2 + " side3 = " + side3;
    }
}
