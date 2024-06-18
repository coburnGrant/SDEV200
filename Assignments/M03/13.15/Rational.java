
import java.math.BigDecimal;
import java.math.BigInteger;

public class Rational extends Number implements Comparable<Rational> {
    // Data fields for numerator and denominator
    private BigInteger numerator = BigInteger.ZERO;
    private BigInteger denominator = BigInteger.ONE;

    /** Construct a rational with default properties */
    public Rational() {
    }

    /** Construct a rational with specified numerator and denominator */
    public Rational(BigInteger numerator, BigInteger denominator) {
        BigInteger gcd = gcd(numerator, denominator);
        BigInteger sign = numerator.multiply(denominator).doubleValue() >= 0 ? BigInteger.ONE : BigInteger.valueOf(-1);
        BigInteger n = numerator.divide(gcd);
        BigInteger d = denominator.divide(gcd).abs();

        this.numerator = n.multiply(sign);
        this.denominator = d;
    }

    /** Find GCD of two numbers */
    private static BigInteger gcd(BigInteger n, BigInteger d) {
        return n.abs().gcd(d.abs());
    }

    /** Return numerator */
    public BigInteger getNumerator() {
        return numerator;
    }

    /** Return denominator */
    public BigInteger getDenominator() {
        return denominator;
    }

    /** Add a rational number to this rational */
    public Rational add(Rational secondRational) {
        BigInteger n1 = numerator.multiply(secondRational.getDenominator());
        BigInteger n2 = denominator.multiply(secondRational.getNumerator());

        BigInteger n = n1.add(n2);
        BigInteger d = denominator.multiply(secondRational.getDenominator());

        return new Rational(n, d);
    }

    /** Subtract a rational number from this rational */
    public Rational subtract(Rational secondRational) {
        BigInteger n1 = numerator.multiply(secondRational.getDenominator());
        BigInteger n2 = denominator.multiply(secondRational.getNumerator());

        BigInteger n = n1.subtract(n2);
        BigInteger d = denominator.multiply(secondRational.getDenominator());

        return new Rational(n, d);
    }

    /** Multiply a rational number by this rational */
    public Rational multiply(Rational secondRational) {
        BigInteger n = numerator.multiply(secondRational.getNumerator());
        BigInteger d = denominator.multiply(secondRational.getDenominator());

        return new Rational(n, d);
    }

    /** Divide a rational number by this rational */
    public Rational divide(Rational secondRational) {
        BigInteger n = numerator.multiply(secondRational.getDenominator());
        BigInteger d = denominator.multiply(secondRational.getNumerator());

        return new Rational(n, d);
    }

    @Override
    public String toString() {
        return denominator.equals(BigInteger.ONE) ? (numerator + "") : (numerator + "/" + denominator);
    }

    @Override // Override the equals method in the Object class
    public boolean equals(Object other) {
        Rational difference = this.subtract((Rational) other);

        return difference.getNumerator().equals(BigInteger.ZERO);
    }

    @Override // Implement the abstract intValue method in Number
    public int intValue() {
        return (int) doubleValue();
    }

    @Override // Implement the abstract floatValue method in Number
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override // Implement the doubleValue method in Number
    public double doubleValue() {
        return numerator.divide(denominator).doubleValue();
    }

    @Override // Implement the abstract longValue method in Number
    public long longValue() {
        return (long) doubleValue();
    }

    // BigDecimal value of the rational number
    public BigDecimal bigDecimalValue() {
        return new BigDecimal(numerator.doubleValue() / denominator.doubleValue());
    }

    @Override // Implement the compareTo method in Comparable
    public int compareTo(Rational o) {
        double difference = this.subtract(o).doubleValue();

        if (difference < 0) {
            return -1;
        } else if (difference == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}