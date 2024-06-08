
import java.util.Scanner;

public class TriangleTester {
    /**
     * Write a test program that prompts the user to enter three sides of the triangle, 
     * a color, and a Boolean value to indicate whether the triangle is filled. 
     * The program should create a Triangle object with these sides and set the color and filled properties using the input. 
     * The program should display the area, perimeter, color, and true or false to indicate whether it is filled or not.
     * 
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Let's make a triangle!");

        int[] sides = new int[3];
        for(int i = 0; i < 3; i++) {
            System.out.print("Enter a side length " + (i + 1) + ": ");
            sides[i] = input.nextInt();
        }

        System.out.print("Enter a color: ");
        String color = input.next();

        System.out.print("Is the triangle filled?: ");
        boolean isFilled = input.nextBoolean();

        Triangle triangle = new Triangle(sides[0], sides[1], sides[2]);
        triangle.setColor(color);
        triangle.setFilled(isFilled);

        System.out.println("Created a new Triangle!");
        System.out.println(triangle.toString());
        System.out.println("area: " + triangle.getArea());
        System.out.println("primeter: " + triangle.getPerimeter());
        System.out.println("color: " + triangle.getColor());
        System.out.println("is filled: " + triangle.isFilled());
    }
}
