package univ.lecture.riotapi;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by tchi on 2017. 3. 19..
 */

public class Calculator {

   String a[];
   String sentence[];
   Stack stack = new Stack();
   int b;
   int c;
   ArrayList<Character> array;

   public double calculate(String exp) {

      char[] chararray = exp.toCharArray();

      sentence = new String[exp.length()];
      array = new ArrayList();

      for (int i3 = 0; i3 < exp.length(); i3++) {
         array.add(chararray[i3]);
      }

      int i2 = 0;
      while (!(array.isEmpty())) {
         StringBuilder sb = new StringBuilder();
         if ("0123456789".indexOf(Character.toString(array.get(0))) >= 0) {
            sb.append(Character.toString(array.remove(0)));
            if (!array.isEmpty()) {
               while (("0123456789".indexOf(Character.toString(array.get(0))) >= 0)) {
                  sb.append(Character.toString(array.remove(0)));
                  if (array.isEmpty()) {
                     break;
                  }
               }
            }
         } else {
            sb.append(Character.toString(array.remove(0)));
         }

         sentence[i2] = sb.toString();
         i2++;

      }

      RPN(infixToPostfix(sentence));

      double result = Double.parseDouble((String) stack.pop());

      return result;
   }

   private boolean isAnOperator(String s) {

      if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s)) {
         return true;
      } else
         return false;
   }

   public double evaluate(double x2, double y2, String op) {
      double z;

      if ("+".equals(op))
         z = x2 + y2;
      else if ("-".equals(op))
         z = x2 - y2;
      else if ("*".equals(op))
         z = x2 * y2;
      else
         z = x2 / y2;

      return z;
   }

   public int precedence(String token) {
      switch (token) {
      case "(":
         b = 0;
         break;
      case "*":
      case "/":
         b = 2;
         break;
      case "+":
      case "-":
         b = 1;
         break;
      default:
         b = -1;
         break;

      }
      return b;
   }

   public String[] infixToPostfix(String[] args) {
      a = new String[args.length];
      Stack s = new Stack();

      for (int i = 0; i < args.length; i++) {

         if (args[i] == null) {
            break;
         }

         if ("(".equals(args[i])) {
            s.push(args[i]);
         } else if (")".equals(args[i])) {
            while (!s.isEmpty() && (precedence((String) s.peek()) != 0)) {
               a[c++] = (String) s.pop();
            }

            if ("(".equals(s.peek())) {
               s.pop();
            }

         } else if (isAnOperator(args[i])) {
            while (!s.isEmpty() && precedence((String) s.peek()) >= precedence(args[i])) {
               a[c++] = (String) s.pop();
            }
            s.push(args[i]);
         }

         else if (!(isAnOperator(args[i]))) {
            a[c++] = args[i];
         }
      }

      while (!s.isEmpty()) {
         a[c++] = (String) s.pop();
      }

      return a;
   }

   public void RPN(String[] args) {

      for (int i = 0; i < args.length; i++) {
         String input = args[i];
         if (input == null)
            break;

         if (isAnOperator(input)) {

            double y2 = Double.parseDouble((String) stack.pop());
            double x2 = Double.parseDouble((String) stack.pop());
            double z2 = evaluate(x2, y2, input);

            stack.push(Double.toString(z2));
         } else
            stack.push(input);
      }
   }

}