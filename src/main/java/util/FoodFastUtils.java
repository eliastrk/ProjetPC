package util;

public class FoodFastUtils {

    public static String deliveryPlanner(int n){
        if (n%3 == 0 && n%5 == 0){ return "FizzBuzz" ;}
        if (n%3 == 0){ return "Fizz" ;}
        if (n%5 == 0){ return "Buzz" ;}
        return "";
    }

    public static boolean isLeapYear(int year){
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static int sumUpTo(int n){
        int sum = 0;
        for (int i = 1; i <= n; i++){
            sum += i;
        }
        return sum;
    }

    public static String anonymize(String text){
        String str = "";

        for (int i = 0; i < text.length(); i++){
            str = text.charAt(i) + str;
        }
        return str;
    }
}