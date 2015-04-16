/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Haider
 */
public class Test {


    private static Carpus C = new Carpus();
    public static int  global = 0;
    public static String  path = null;
    public static String file = null;
    

    public static void main(String[] args) throws FileNotFoundException, IOException {

// args[0] path to corpus folder
// args[1] path to file to be tested        
        path = args[0];
        file = args[1];
        

        
        getDataForBernouli();
        getDataForMultiNomial();
        Document D = new Document(global++, getData(file));


        System.out.println("Bernouli Results:");
        Bernouli(C, D);
        System.out.println("Document is belong to "+ D.getCategory());        
        System.out.println("Multinomial Results:");
        Mulitinomial(C, D);
        System.out.println("Document is belong to "+ D.getCategory());
        
        

        
        
       
    }
    

    
    
    
    
    public static String getData(String path) throws FileNotFoundException, IOException
    {
        String string = "";
        BufferedReader file = new BufferedReader(new FileReader(path)); 
        String line =null;
        
        
        while( (line = file.readLine() ) !=null)
        {
            string += line;
            string +=" ";
        }
                
                
                
                return string;
                
    }

    private static void getDataForBernouli() throws FileNotFoundException, IOException {
        

//get calculations from bernouli.out file
        BufferedReader bernouli = new BufferedReader(new FileReader(path + "//Bernouli.out"));
        String line = null;
        
        line = bernouli.readLine();
        String [] Category = line.split(" ");
        String [] CategoryInfo = null;
        
        for(int i=0;i<Category.length;i++)
        {
            CategoryInfo = bernouli.readLine().split(" ");
            C.category_Document.put(CategoryInfo[0], Integer.parseInt(CategoryInfo[1]));
        }
        
        C.totalDoc = Integer.parseInt(bernouli.readLine().split(" ")[0]);
        
        line = null;
        String word = null;
        String [] values = null;
        while( (line = bernouli.readLine()) !=null)
        {
                


            values = line.split(" ");
            
            HashMap<String,Integer> tmp = new HashMap<String, Integer>();
            
            for(int i=0;i<Category.length && values.length==3 ;i++)
                tmp.put(Category[i], Integer.parseInt(values[1+i]));
            
            
            word = values[0];
            C.words.put(word, tmp);
                
                line = null;
        }
        

    }
    
    
    
    private static void getDataForMultiNomial() throws FileNotFoundException, IOException
    {

//get calculations from multinomial.out file
        BufferedReader Mutinomail = new BufferedReader(new FileReader(path + "//Multinomail.out"));
        String line = null;
        
        line = Mutinomail.readLine();
        String [] Category = line.split(" ");
        String [] CategoryInfo = null;
        
        for(int i=0;i<Category.length;i++)
        {
            CategoryInfo = Mutinomail.readLine().split(" ");
            C.category_word.put(CategoryInfo[0], Integer.parseInt(CategoryInfo[1]));
            C.category_Document.put(CategoryInfo[0], Integer.parseInt(CategoryInfo[2]));
        }
        
        
        C.NoOfDistinctWords = Integer.parseInt(Mutinomail.readLine());
        C.totalDoc = Integer.parseInt(Mutinomail.readLine().split(" ")[0]);

        
        line = null;
        String word = null;
        String [] values = null;
        Integer integer;
        while( (line = Mutinomail.readLine()) != null)
        {
                           
            values = line.split(" ");            
            HashMap<String,Integer> tmp = new HashMap<String, Integer>();
            
            for(int i=0;i<Category.length && values.length== 3 ;i++)
            {
                integer = Integer.parseInt(values[1+i]);
                tmp.put(Category[i],integer );
            }

            word = values[0];
            C.words1.put(word, tmp);
                
                line = null;
        }
    
    }

    public static void Bernouli(Carpus C, Document D)
    {
        
        double prior = 0.0;
        double pb = 0.0;
        double tmpScore = 0.0;
        double totalScore = 0.0;
        int totalDoc = C.totalDoc;
        int categDoc;
        
        
// calculate probablity of document to be in ham and spam category using bernoulit formula
        
        String [] Category = C.getAllCategory();
        if(Category.length <=0 )
            return ;
        
        HashMap<String,Double> score = new HashMap<String, Double>();
        
        for(int i=0;i<Category.length;i++)
        {
            categDoc = C.category_Document.get(Category[i]);
            prior = (double) ( categDoc * 1.0 / totalDoc);            
            score.put(Category[i], (Double)prior);
        }

        
        String word;
        Map.Entry newEntery ;          
        
        Iterator allWords = C.words.entrySet().iterator();
        boolean present = false;
        
        
        while( allWords.hasNext() )
        {
            
                present = false;
                newEntery = (Map.Entry) allWords.next();
                word = (String) newEntery.getKey();
                
                if( D.isPresent(word))
                    present = true;
                
            
                for(int i=0; i<Category.length;i++)
                {
                     categDoc = C.category_Document.get(Category[i]);
                     
                     if( C.words.get(word).containsKey(Category[i]) )
                           pb = (double) ( C.words.get(word).get(Category[i])  + 1)/( categDoc + Category.length );
                     else
                           pb = (double) ( 0  + 1)/( categDoc + Category.length );
                
                
                    if(!present)
                        pb = 1- pb;
                    
                
                    tmpScore = score.get(Category[i]);
                    tmpScore =  tmpScore  + Math.log(pb);
                    score.put(Category[i], (Double) tmpScore   );
                
                }
        }
        
        
        double  tmp = 0.0;
       
        String category = "";
        double min = 0.0;
        
        category = Category[0];
        min      = score.get(category);
        
        for( int i=0 ; i<Category.length ;i++)
        {
               tmp = score.get(Category[i]);

               if( tmp - (min) >= 0)
               {
                   min = tmp;
                   category = Category[i];
               }
        }
        
        D.setCategory(category);
        
    }
    
    
    public static void Mulitinomial (Carpus C,Document D)
    {
        double prior = 0.0;
        double pb = 0.0;
        double tmpScore = 0.0;
        double totalScore = 0.0;
        int totalDoc = C.totalDoc;
        int totalWords = C.words.size();
        int categDoc;
        int categWords;
        String currentCategory;
        
// calculate probablity of document to be in ham and spam category using multinomial formula        
        
        String [] Category = C.getAllCategory();
        if(Category.length <=0 )
            return ;
        
        HashMap<String,Double> score = new HashMap<String, Double>();
        
        for(int i=0;i<Category.length;i++)
        {
            categDoc = C.category_Document.get(Category[i]);
            prior = (double) ( categDoc * 1.0 / totalDoc);            
            score.put(Category[i], (Double)prior);
        }

        
        double num =0.0;
        double den = 0.0;
        
        String word;
        String [] allWords = D.listOfWords;
        
        for(int i=0;i<allWords.length;i++)
        {
            word = allWords[i];
            
            for(int j=0;j<Category.length;j++)
            {   
                currentCategory = Category[j];
                categWords = C.category_word.get(currentCategory); 
                       
                if(C.words1.containsKey(word) && ( word !=null || word!="") )
                {
                    if(C.words1.get(word).containsKey(currentCategory))
                        num = (double) C.words1.get(word).get(currentCategory) + 1 ;

                    else
                        num = (double) 0 + 1 ;

                    den = (double) categWords + totalWords;
                    pb = (double) num*1.0/den;  
                    tmpScore = score.get(currentCategory);
                    tmpScore = (double) tmpScore + Math.log(pb);
                    score.put(currentCategory, (Double)tmpScore);                
                }
            }
        }
        
        
        double  tmp = 0.0;
       
        String category = "";
        double min = 0.0;
        
        category = Category[0];
        min      = score.get(category);
        
        for( int i=0 ; i<Category.length ;i++)
        {
               tmp = score.get(Category[i]);
               
               if( tmp - (min) >= 0)
               {
                   min = tmp;
                   category = Category[i];
               }
        }
        D.setCategory(category);
        
    }
    
    
    
    
}
