/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kz
 */
public class JavaApplication2 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        postag();
        cases();
        punctuation();
    }
    
    public static void postag() throws FileNotFoundException, UnsupportedEncodingException, IOException{
        FileInputStream fstream = new FileInputStream("src/javaapplication2/train_en.txt");
        MaxentTagger tagger =  new MaxentTagger("lib/english-left3words-distsim.tagger");
        PrintWriter writer = new PrintWriter("src/javaapplication2/output_postag_train1.txt", "UTF-8");
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
            String strLine;
            
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                String[] words = strLine.split("\\s+");
                String wrd = "";
                
                String[] input = strLine.split("\\t");
                String tagged = tagger.tagString(input[0]);
                String[] tagged_array = tagged.split("\\s+");
                String pattern = "[+-.,!@#$%^&*();\\/|<>\"']";
                
                for(int i=0; i< tagged_array.length; i++){
                    if(tagged_array[i].length() > 0){
                        String[] aa = tagged_array[i].split("_");
                        Pattern r = Pattern.compile(pattern);
                        Matcher m = r.matcher(aa[0]);
                        String ner = "O";
                        if(m.find()){
                            ner = "O";
                        }
                        else{
                            ner = input[1];
                        }
                        writer.println(aa[0]+"\t"+aa[1]+"\t"+ner);
//                        writer.println(aa[0]+"\t"+aa[1]);
                        System.out.println (aa[0]+"\t"+aa[1]+"\t"+ner);
                        //writer.println(wrd);
                        //System.out.println();
                    }
                    else{
                        writer.println("");
                    }
                }
            }
            //Close the input stream
            writer.close();
         
        }
    }
    
    public static void cases() throws FileNotFoundException, UnsupportedEncodingException, IOException{
        FileInputStream fstream = new FileInputStream("src/javaapplication2/output_postag_train1.txt");
        PrintWriter writer = new PrintWriter("src/javaapplication2/output_case_train.txt", "UTF-8");
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
            String strLine;
            
            //Read File Line By Line
            String patternCase1 = "\\b[A-Z]{1}.*?\\b";
            String patternCase2 = "\\b[A-Z]+\\b";
            String patternCase3 = "\\b[a-z]+\\b";
            String patternCase4 = "\\b.*?[A-Z]*.*?\\b";
            while ((strLine = br.readLine()) != null)   {
                String[] words = strLine.split("\\s+");
                String wrd = words[0];
                //wrd = "awarD";
                //UC : CAPITAL LETTER
                //UP : ALL UPPERCASE
                //UM : MIXED
                
                if(words[0].length() > 0){
                    Pattern r1 = Pattern.compile(patternCase1);
                    Pattern r2 = Pattern.compile(patternCase2);
                    Pattern r3 = Pattern.compile(patternCase3);
                    Matcher m1 = r1.matcher(wrd);
                    Matcher m2 = r2.matcher(wrd);
                    Matcher m3 = r3.matcher(wrd);


                    String cases = "O";
                    if(m1.find()) cases = "UC";
                    else if(m2.find()) cases = "UP";
                    else if(m3.find()) cases = "O";
                    else cases = "UM";

                    //System.out.println (wrd+"\t"+cases+"\t"+words[1]+"\t"+words[2]);

                    writer.println(wrd+"\t"+cases+"\t"+words[1]+"\t"+words[2]);
    //                writer.println(wrd+"\t"+cases+"\t"+words[1]);

                }
                else{
                    writer.println("");
                }
            }
            //Close the input stream
            writer.close();
            
        }
    }
    
    public static void punctuation() throws FileNotFoundException, UnsupportedEncodingException, IOException{
        FileInputStream fstream = new FileInputStream("src/javaapplication2/output_case_train.txt");
        PrintWriter writer = new PrintWriter("src/javaapplication2/output_punc_train3.txt", "UTF-8");
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
            String strLine;
            
            //Read File Line By Line
            String pattern1 = "[.]";
            String pattern2 = "[\\'\\-\\&]";
            while ((strLine = br.readLine()) != null){
                String[] words = strLine.split("\\s+");
                String wrd = words[0];
                //wrd = "awarD";
                //PRD : ENDS / HAS THE INTERNAL PERIOD
                //AHA : INTERNAL APOSTROPHE, HYPEN OR AMPERSAND
                
                if(words[0].length() > 0){
                Pattern r1 = Pattern.compile(pattern1);
                Pattern r2 = Pattern.compile(pattern2);
                Matcher m1 = r1.matcher(wrd);
                Matcher m2 = r2.matcher(wrd);
                

                String puncs = "O";
                if(m1.find()) puncs = "PRD";
                else if(m2.find()) puncs = "AHA";

                //System.out.println (wrd+"\t"+puncs+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]);
//                writer.println(wrd+"\t"+puncs+"\t"+words[1]+"\t"+words[2]);
                writer.println(wrd+"\t"+puncs+"\t"+words[1]+"\t"+words[2]+"\t"+words[3]);
                }
                else{
                    writer.println("");
                }
                
               
            }
            //Close the input stream
            writer.close();
        }
    }
    
}