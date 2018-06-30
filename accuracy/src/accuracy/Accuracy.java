/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accuracy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Ryan
 */
public class Accuracy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException,  IOException {
        FileInputStream fstream_test = new FileInputStream("src/accuracy/_result-2.txt");
        int i= 0;
        double TP =0 ,FP = 0, TN=0, FN=0, precision=0, recall=0, f_score=0, count=0, akurasi=0, jumlah_benar=0;
        
         try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream_test))) {
            String strLine_test;
          
            while ((strLine_test = br.readLine())!=null)   {
                String[] words_test = strLine_test.split("\\t");
                count++;
                if(words_test[0].length() > 0){
                    if(words_test[5].equals("O")){
                        if(words_test[5].equals(words_test[4])){
                        FN ++;
                    }
                        else{
                        TN ++;
                    }
                    }
                    else {
                        if(words_test[5].equals(words_test[4])){
                        TP ++;
                    }
                        else{
                        FP ++;
                    }
                    }
                    if(words_test[5].equals(words_test[4])){
                        jumlah_benar ++;
                    }
                    precision = TP/(TP+FP);
                    recall=TP/(TP+FN);  
                    f_score=(2*precision*recall)/(precision+recall);
                    akurasi=jumlah_benar/count;
         }           
    }
             System.out.println("TP: " + (int) TP + " FP: " + (int) FP + " TN: " + (int) TN + " FN: " + (int) FN);
             System.out.println("Precision: " + precision*100+"%");
             System.out.println("Recall: " + recall*100+"%");
             System.out.println("F Score: " + f_score*100+"%");
             System.out.println("Akurasi: " + akurasi*100+"%");
        
               
    }
}
}