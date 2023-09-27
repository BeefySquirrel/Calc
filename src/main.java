import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    String firstLetter;
    int countNum = 0;
    public static void main(String[] args) throws Exception{
        System.out.print("Введите математичекое выражение положительными арабскими или римскими цифрами от 1 до 10 включительно: ");
        Scanner sc = new Scanner(System.in);
        String statement = sc.nextLine();
        System.out.println("Ответ: "+Main.calc(statement));
    }
    public static String calc(String input) throws Exception{
        String[] splittedExpression = new String[3];
        String answer="";
        analysis view =new analysis();
        splittedExpression=view.splitting(input);
        calculation ans = new calculation();
        if (view.isRoman(splittedExpression[0]) && view.isRoman(splittedExpression[2])){
            romanNumTrans romanFunc=new romanNumTrans();
            int tempAns=ans.getAnswer(romanFunc.getUsageSymbol(splittedExpression[0]),splittedExpression[1],romanFunc.getUsageSymbol(splittedExpression[2]));
            if (tempAns<0){
                throw new Exception("//т.к. в римской системе нет отрицательных чисел");
            } else {
                answer = romanFunc.reverseToRoman(tempAns);
            }
        } else if (!view.isRoman(splittedExpression[0]) && !view.isRoman(splittedExpression[2])) {
            try {
                int firstNumb = Integer.parseInt(splittedExpression[0]);
                int secondNumb = Integer.parseInt(splittedExpression[2]);
                if (firstNumb >= 1 && firstNumb <= 10 && secondNumb >= 1 && secondNumb <= 10) {
                    answer = String.valueOf(ans.getAnswer(firstNumb, splittedExpression[1], secondNumb));
                } else {
                    throw new Exception("//т.к. допустимо вводить числа от 1 до 10");
                }
            } catch (Exception e) {
                throw new Exception("//т.к. допустимо вводить только целые числа");
            }
        } else {
            throw new Exception("//т.к. используются одновременно разные системы счисления");
        }
        return answer;
    }
}
class analysis {
    String[] splitting(String statement) throws Exception{
        String tempLetter;
        String[] splittedStatement = {"","",""};
        int countOfOperators=0;
        for (int i = 0; i < statement.length(); i++) {
            tempLetter = String.valueOf(statement.charAt(i));
            if (tempLetter.equals("+") || tempLetter.equals("-") || tempLetter.equals("*") || tempLetter.equals("/")) {
                splittedStatement[1]=tempLetter;
                countOfOperators++;
            } else if (countOfOperators == 0) {
                splittedStatement[0] = splittedStatement[0] + statement.charAt(i);
            } else if (countOfOperators == 1) {
                splittedStatement[2] = splittedStatement[2] + statement.charAt(i);
            }
        }
        if (countOfOperators > 1) {
            throw new Exception("//т.к. формат математической операции не удовлетворяет заданию - " + countOfOperators + " оператора");
        } else if (countOfOperators==0){
            throw new Exception("//т.к. строка не является математической операцией");
        }
        System.out.println(splittedStatement[0]+" "+splittedStatement[1]+" "+splittedStatement[2]+" ");
        return splittedStatement;
    }
    boolean isRoman(String number) throws Exception{
        boolean rome;
        if (Stream.of("I","V","X").anyMatch(number::contains) && Stream.of("0","1","2","3","4","5","6","7","8","9").noneMatch(number::contains)){
            rome=true;
        } else if (Stream.of("I","V","X").noneMatch(number::contains) && Stream.of("0","1","2","3","4","5","6","7","8","9").anyMatch(number::contains)){
            rome=false;
        } else {
            throw new Exception("//т.к. введено число, содержащее символы разных систем счисления (или некорректное выражение)");
        }
        return rome;
    }
}
class romanNumTrans {
    int getUsageSymbol(String input) throws Exception,RuntimeException{
        int firstInt=0;
        try {
            Rome one = Rome.valueOf(input);
            firstInt = one.getNumber();
            if (firstInt>10){
                throw new Exception("//т.к. введено неудовлетворяющее римское число больше X");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("//т.к. введено несуществующее римское число");
        }
        return firstInt;
    }
    String reverseToRoman(int answer) {
        String romanNumb = "";
        for (Rome allNumb : Rome.values()) {
            if (answer == allNumb.getNumber()) {
                romanNumb = allNumb.name();
                break;
            }
        }
        return romanNumb;
    }
}
class calculation {
    int getAnswer(int first, String operator, int second) {
        int answer=1000;
        switch (operator){
            case "+" :
                answer=first+second;
                break;
            case "-" :
                answer=(first-second);
                break;
            case "/" :
                answer=first/second;
                break;
            case "*" :
                answer=first*second;
                break;
        }
        return answer;
    }
}



