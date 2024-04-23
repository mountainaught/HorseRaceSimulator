package horserace.utils;

public class Decimal {

    public static double validateValue(double value) {
        int decimalRemainder = (int) (value * 100) % 10;
        if (value > 1.0 || value < 0.0) {
            throw new DecimalNumberException("The value must be between 0.0 and 1.0.");
        } else if ( decimalRemainder != 0) {
            throw new DecimalNumberException("The value must only have 1 digit of precision");
        }

        return value;
    }
}
