public class Circle extends GeometricObject implements Comparable<Circle> {
    private double radius;

    public Circle() {
    }

    public Circle(double radius) {
      this.radius = radius;
    }

    public Circle(double radius,
        String color, boolean filled) {
      this.radius = radius;
      setColor(color);
      setFilled(filled);
    }

    /** Return radius */
    public double getRadius() {
      return radius;
    }

    /** Set a new radius */
    public void setRadius(double radius) {
      this.radius = radius;
    }

    /** Return area */
    public double getArea() {
      return radius * radius * Math.PI;
    }

    /** Return diameter */
    public double getDiameter() {
      return 2 * radius;
    }

    /** Return perimeter */
    public double getPerimeter() {
      return 2 * radius * Math.PI;
    }

    /** Print the circle info */
    public void printCircle() {
      System.out.println("The circle is created " + getDateCreated() +
        " and the radius is " + radius);
    }

    public boolean equals(Circle o) {
        return (this.radius == o.radius);
    }

    @Override
    public int compareTo(Circle o) {
        if (this.radius < o.radius) {
            return -1;
        } else if (this.radius > o.radius) {
            return 1;
        }

        return 0;
    }
}