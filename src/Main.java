

import java.util.*;

public class Main {
    static int counter=1;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        boolean guessed;
        String code = generateCode();
        System.out.print("Okay, let's start a game!");
        do {
            guessed = game(code);
        } while (!guessed);
        System.out.printf("\nGrade: %d bulls.", code.length());
        System.out.println("Congratulations! You guessed the secret code.");
    }


    static int getInputLength(){
        System.out.println("Please, enter the secret code's length:");
        String input = sc.nextLine();
        if (input.replaceAll("\\D+", "").length() < input.length()) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", input);
            return  0;
        }
        int length = Integer.parseInt(input);
        if (length < 1 || length > 36) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", input);
            return  0;
        }
        return length;
    }

    static int getInputSymbols(int length){
        String input;
        System.out.println("Input the number of possible symbols in the code:");
        input = sc.nextLine();
        if (input.replaceAll("\\D+", "").length() < input.length()) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", input);
            return  0;
        }
        int symbols = Integer.parseInt(input);
        if(symbols>36) {System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z)."); return 0;}
        if(symbols<length) {System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", length, symbols); return 0;}
        return symbols;
    }


    static String generateCode(){
        int length = 0;
        while (length==0){
            length=getInputLength();
        }
        int symbols=0;
        while (symbols==0){
            symbols=getInputSymbols(length);
        }

        String alphanumerics = "0123456789abcdefghijklmnopqrstuvwxyz".substring(0,symbols);
        List<String> characters = Arrays.asList(alphanumerics.split(""));
        Collections.shuffle(characters);
        StringBuilder code= new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(characters.get(i));
        }
        int num1 = symbols>9 ? 9 : symbols-1;
        String output_prompt = " (0-"+num1;
        if(symbols>10){
            output_prompt+=", a-"+alphanumerics.charAt(symbols-1);
        }
        System.out.println("The secret is prepared: "+"*".repeat(length) + output_prompt +").");
        return code.toString();
    }


    static Grading grade(String guess, String code){
        if (guess.equals(code)) return new Grading(0,code.length());
        int cows=0; int bulls=0;
        char[] arr = guess.toCharArray();
        for (int i = 0; i < code.length(); i++) {
            if(code.contains(String.valueOf(arr[i]))) {
                if(code.charAt(i)==arr[i]) bulls++;
                else cows++;
            }
        }
        return new Grading(cows, bulls);
    }

    static boolean game(String code){
        System.out.printf("\nTurn %d:\n",counter++);
        String guess;
        do {
            Scanner sc = new Scanner(System.in);
            guess = sc.nextLine();
            if(guess.length()!=code.length()) System.out.println("Error: Input length must match code length!");
        } while (guess.length()!=code.length());
        Grading gr = grade(guess, code);
        if(gr.bulls==code.length()) return true;
        String cows = gr.cows>1 ? "cows" : "cow";
        String bulls = gr.bulls>1 ? "bulls" : "bull";
        if(gr.cows==0&&gr.bulls==0){
            System.out.print("Grade: None.");
        } else if(gr.cows>0&&gr.bulls==0) {
            System.out.printf("Grade: %d %s.", gr.cows, cows);
        }   else if(gr.bulls>0&&gr.cows==0) {
            System.out.printf("Grade: %d %s.", gr.bulls, bulls);
        } else {
            System.out.printf("Grade: %d %s and %d %s.", gr.bulls,bulls, gr.cows,cows);
        }
        return false;
    }

    static class Grading{
        int cows;
        int bulls;
        public Grading(int cows, int bulls) {
            this.cows = cows;
            this.bulls = bulls;
        }
    }
}
