package com.company;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public final class Main {

    abstract static class Coder {            //обработка

        static final HashMap<String, String> alphabets = new HashMap<String, String>(); //code -> morse
        static final HashMap<String, String> dictionaries = new HashMap<String, String>(); //morse -> code

        static void registerMorse (String alp, String dict) {
            alphabets.put(String.valueOf(alp), dict);
            dictionaries.put(dict, String.valueOf(alp));
        }
        static {
            registerMorse("A", "·-");
            registerMorse("B", "-···");
            registerMorse("W", "·--");
            registerMorse("G", "--·");
            registerMorse("D", "-··");
            registerMorse("E", "·");
            registerMorse("V", "···-");
            registerMorse("Z", "--··");
            registerMorse("I", "··");
            registerMorse("J", "·---");
            registerMorse("K", "-·-");
            registerMorse("L", "·-··");
            registerMorse("M", "--");
            registerMorse("N", "-·");
            registerMorse("O", "---");
            registerMorse("P", "·--·");
            registerMorse("R", "·-·");
            registerMorse("S", "···");
            registerMorse("T", "-");
            registerMorse("U", "··-");
            registerMorse("F", "··-·");
            registerMorse("H", "····");
            registerMorse("C", "-·-·");
            registerMorse("Q", "--·-");
            registerMorse("Y", "-·--");
            registerMorse("X", "-··-");
            registerMorse(".", ".-.-.-");
            registerMorse(",", "--..--");
            registerMorse("?", "..--..");
            registerMorse("!", "-.-.--");
            registerMorse(":", "---...");
            registerMorse(";", "-.-.-.");
            registerMorse("-", "-....-");
            registerMorse(" ", "/");
            registerMorse("(", "-.--.");
            registerMorse(")", "-.--.-");
            registerMorse("", "");
        }
    }


    public static String firstUpperCase(String text){
        char[] myChars = text.toCharArray();
        char[] s = text.toUpperCase().toCharArray();
        for (int i = 0; i < text.length(); i++) {
            if (((text.charAt(i) == '.')||(text.charAt(i) == '!')||(text.charAt(i) == '?'))&&((i+2)<text.length())){
                myChars[i+2] = s[i+2];
                text = String.valueOf(myChars);
            }
            if (((i >= 1))&&(text.charAt(i) == 'i')&&(text.charAt(i - 1) == ' ')&&(text.charAt(i + 1) == ' ')&&((i+2)<text.length())) {
                myChars[i] = s[i];
                text = String.valueOf(myChars);
            }
            if ((text.charAt(0) == 'i')&&(text.charAt(0 + 1) == ' ')) {
                myChars[i] = s[i];
                text = String.valueOf(myChars);
            }
        }
        return text;
    }



        static class En2Morse extends Coder {
            String handle(String text) {
                if (text == null) {
                    throw new IllegalArgumentException("\nText shouldn't be null.\n");
                }
                StringBuilder code = new StringBuilder();
                text = text.toUpperCase();
                for (int i = 0; i < text.length(); i++) {
                    String codeString = text.substring(i, i + 1);
                    String word = alphabets.get(codeString);
                   if (word == null) {
                       code.append("\n");
                   }
                   else{
                       if ((word.equals(".-.-.-"))&&(alphabets.get(text.substring(i+1, i + 2)) == null)) {
                           code.append(word);
                       }else{
                           code.append(word + " ");
                       }
                   }
                }
                return code.toString();
            }
        }

        static class Morse2En extends Coder {

             String handle(String morse) {
                if (morse == null) {
                    throw new IllegalArgumentException("\nMorse shouldn't be null.\n");
                }

                StringBuilder decode = new StringBuilder();
                String decodeString = "";
                for (int i = 0; i < morse.length(); i++) {
                    if (decodeString.equals(".-.-.-")){
                        String word = dictionaries.get(decodeString);
                        decodeString = "";
                        decode.append(word);}
                    if (morse.charAt(i) != '/') {
                       if (morse.charAt(i) != ' ') {
                            decodeString += String.valueOf(morse.charAt(i));
                        } else{
                           String word = dictionaries.get(decodeString);
                           if (word == null){
                               String newString = decodeString.substring(1);
                               word = dictionaries.get(newString);
                               decode.append("\n" + word);
                               decodeString = "";
                           }
                           else{
                               decodeString = "";
                               decode.append(word);
                           }
                       }
                    } else {
                        decode.append(' ');
                   }

                }
                return decode.toString();
            }
        }

        public static String readAllLines(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line + "\n");
           // content.append(System.lineSeparator());
        }
        return content.toString();
    }

        public static void main(String args[]) throws FileNotFoundException {
            System.out.println("What are you want: Code En2M / Decode M2En ? ");
            Scanner keyboard = new Scanner(System.in);
            String act = keyboard.next();
            if (act.equals("code")) {
                System.out.println("Write name of text: ");
                Scanner nameText = new Scanner(System.in);
                String fileName = nameText.next();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    FileWriter writer = new FileWriter("CODE" + fileName);
                    En2Morse E2M = new En2Morse();
                    writer.write(E2M.handle(readAllLines(reader)));
                    reader.close();
                    writer.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Error");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    System.out.println("Write name of text: ");
                    Scanner nameText = new Scanner(System.in);
                    String fileName = nameText.next();
                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    FileWriter writer = new FileWriter(fileName);
                    Morse2En M2E = new Morse2En();
                    writer.write(firstUpperCase(M2E.handle(readAllLines(reader)).toLowerCase()));
                    reader.close();
                    writer.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Error");
                } catch (IOException e) {
                    e.printStackTrace();
                };
            }
        }
    }
