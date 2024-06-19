public class CircleTester {
    public static void main(String[] args) {
        Circle circle1 = new Circle();
        Circle circle2 = new Circle(5);
        Circle circle3 = new Circle(5, "blue", true);

        Circle[] circles = { circle1, circle2, circle3 };

        for(int i = 0; i < circles.length; i++) {
            Circle circle = circles[i];
            
            System.out.println(circle.toString());
            System.out.println("area: " + circle.getArea());
            System.out.println("primeter: " + circle.getPerimeter());
            System.out.println("color: " + circle.getColor());
            System.out.println("is filled: " + circle.isFilled());

            if((i + 1) < circles.length) {
                Circle nextCircle = circles[i + 1];
                System.out.println("circle " + i + " equals circle " + (i + 1) + ": " + circle.equals(nextCircle));
            }
        }
    }
}
