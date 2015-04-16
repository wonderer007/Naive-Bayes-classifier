package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale.Category;
import java.util.Map;

public class Train {

    
    public static HashMap<String,String> stopWords = new HashMap<String, String>();
    public static String [] spam;
    public static String [] ham;
    public static String [] spamTrainData;
    public static String [] hamTrainData;  
    public static Carpus C = new Carpus();
    public static int global = 0;
    public static String path = null;
    public static String stopWordsFile = null;
    public static final int totalSize = 100;
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

//C://Users//Haider//Desktop//carpus    
        path = args[0]; // args[0] contains the path of corpus folder
        stopWordsFile = path+ "//stopWords.txt";    // stopwords.txt contains list of english stopwords and should be place inside corpus folder
        
        if(path == null)
        {
            System.out.println("path is missing in argument");
            return ;            
        }

        
        
        getSpamFiles();
        getHamFiles();
        System.out.println("trainging............");
        trainData();
        BernouliModel();
        MultinomialModel();
        System.out.println("System has been trained");
        
    
    }


    private static void getStopWords() throws FileNotFoundException, IOException {
        
            BufferedReader in = new BufferedReader(new FileReader(stopWordsFile));
            String text ="";
            
            while(in.ready())
            {
                text = in.readLine();
                stopWords.put(text, text);

            }            
    }

    
    
    public static void trainData() throws FileNotFoundException, IOException
    {

// we already have choosen spam and ham files randomly from the folder, now we will use those ham and spam files to trail our system  using naive based probablity distribution algorithm


// first iterate over all spam files one by one
// remove punctuation marks and stop words



        String document = "";
        Document D = null;
        for(int i=0;i<spamTrainData.length  ;i++)
        {   
            BufferedReader in = new BufferedReader(new FileReader(spamTrainData[i])); 
            String text = in.readLine(); 
            document = "";
            
            
            while(in.ready())
            {
                text = in.readLine();
                String [] separateWords = text.split("[\\s+;.,:'!?()-<>'=#__"+'"'+"]");

                for(int j=0;j< separateWords.length ; j++)
                {
                    if(! stopWords.containsKey(separateWords[j]))
                    {
                        document = document + separateWords[j]+" ";
                    }
                        
                }

            }


// against each file we will create a document object
// we will assign a id , text and category ( which we already know in training) to each document object

                global ++;
                D = new Document(global, document);
                D.setCategory("spam");

// add that document to corpus ( group of documents)
// inside Add function , there is lots of magic  
                C.Add(D);
                
            in.close();           
        }
        


// same as above        
        for(int i=0;i<hamTrainData.length  ;i++)
        {   
            BufferedReader in = new BufferedReader(new FileReader(hamTrainData[i])); 
            String text = in.readLine(); 
            document = "";
            
            while(in.ready())
            {
                text = in.readLine();
                String [] separateWords = text.split("[\\s+;.,:'!?()-<>'=#__"+'"'+"]");
                

                for(int j=0;j< separateWords.length ; j++)
                {    
                    if(! stopWords.containsKey(separateWords[j]))
                    {
                        document = document + separateWords[j]+" ";
                    }
                        
                }

            }
                global ++;
                D = new Document(global, document);
                D.setCategory("ham");

                C.Add(D);

            in.close();           
        }
    }
    
        public static void  getSpamFiles()
    {

// function will get totalSize number of files randomly from spam folder  


        System.out.println(path + "/spam");
        File folder = new File(path + "/spam"); // there should be a spam folder inside corpus which contains files having spam data( emails in this case)
        File[] listOfFiles = folder.listFiles();

// initialize array of spam documents        
        spam = new String[listOfFiles.length];
        int count = 0;

// iterate over all files in spam folder
// assign names of spam files in spam array respectivley    
        for (File file : listOfFiles) {
            if (file.isFile()) {
                spam [count++] =  file.getName();
            }
        }


// randomly place all files in tmpRandom array

        int [] tmpRandom = new int[listOfFiles.length];
        count = 0; 
        boolean found = false;
        int mm=0;

        for( int i=0; count < listOfFiles.length; i++)
        {
            found = false;            
            mm = (int) (Math.random() * ( listOfFiles.length  ) + 1);
            
            for(int j=0;j < count && !found ;j++)
            {
                if(mm == tmpRandom[j])
                    found = true;
            }
            if(!found)
                tmpRandom [ count++ ] = mm;
        }
        
        

// out of all randomize files choose totalSize number of files for training purpose

        count=0;
        int count1=0;
        spamTrainData = new String[totalSize];

        for(int i=0;i<listOfFiles.length;i++)
        {
            if( i < totalSize )
                spamTrainData[count++] = path + "//spam//" + spam[ tmpRandom[i]-1 ];
                
        }
    }
    
    public static void getHamFiles()
    {

// function will get totalSize number of files randomly from ham folder  


// there should be a ham folder inside corpus which contains files having ham data( emails in this case)
        File folder = new File(path + "//ham");
        File[] listOfFiles = folder.listFiles();

        ham = new String[listOfFiles.length];
        int count = 0;
 
// iterate over all files in ham folder
// assign names of ham files in ham array respectivley    


        for (File file : listOfFiles) {
            if (file.isFile()) {
                ham [count++] =  file.getName();
            }
        }
        


// randomly place all files in tmpRandom array

        int [] tmpRandom = new int[listOfFiles.length];
        count = 0; 
        boolean found = false;
        int mm=0;

        for( int i=0; count < listOfFiles.length; i++)
        {
            found = false;            
            mm = (int) (Math.random() * ( listOfFiles.length  ) + 1);
            
            for(int j=0;j < count && !found ;j++)
            {
                if(mm == tmpRandom[j])
                    found = true;
            }
            if(!found)
                tmpRandom [ count++ ] = mm;
        }
        

// out of all randomize files choose totalSize number of files for training purpose        
        count=0;
        int count1=0;
        hamTrainData = new String[totalSize];
        for(int i=0;i<listOfFiles.length;i++)
        {
            if(i < totalSize)
                hamTrainData[count++] = path + "//ham//" + ham[ tmpRandom[i]-1 ];            
        }
    }

    private static void BernouliModel() throws IOException {
        
    // From the data we will build a mode which will tell us
    // how many types of categories are there in corpus
    // number of documents in earch category
    // for each word in the whole corpus list how many time i appears against each category
    // you can also see output of the bernouli in Bernouli.out inside corpus folder
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "//Bernouli.out"));
        writer.flush();
                
        String [] Category = C.getAllCategory();        
        Iterator iterator = C.words.entrySet().iterator();
        Iterator iterator1 ;
        
        
        String word = "";
        
        Map.Entry newEntery ;
        String line = "";
        
        line = "";
        for(int i=0;i<Category.length;i++)
        {
                line = line + Category[i] + " ";
        }

        
        writer.write(line);
        writer.newLine();        
        
        
        for(int i=0;i<Category.length;i++)
        {
                line = Category[i]+" "+ C.category_Document.get(Category[i]);
                writer.write(line);
                writer.newLine();
        }
        
                writer.write(C.Doc.length+" ");
                writer.newLine();

            newEntery = (Map.Entry) iterator.next();
            word = (String) newEntery.getKey();
        
        while(iterator.hasNext())
        {
            newEntery = (Map.Entry) iterator.next();
            word = (String) newEntery.getKey();
            line = word + " ";
            if(word == "" || word ==null)
                continue;
            for( int i=0;i<Category.length;i++)
            {
                if (C.words.get(word).containsKey(Category[i]))                   
                    line = line + C.words.get(word).get(Category[i]) + " ";
                else 
                    line = line + "0 ";
                
            }
            
            writer.write(line);
            writer.newLine();
        }
        
        
    }

    private static void MultinomialModel() throws IOException {


    // From the data we will build a mode which will tell us
    // how many types of categories are there in corpus
    // number of documents in earch category
    // how manay unique words are there in each category
    // for each word in the whole corpus list how many time i appears against each category
                
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "//Multinomail.out"));
        writer.flush();
                
        String [] Category = C.getAllCategory();        
        Iterator iterator = C.words1.entrySet().iterator();
        Iterator iterator1 ;
        
        
        String word = "";
        Map.Entry newEntery ;
        String line = "";
        line = "";
        
        for(int i=0;i<Category.length;i++)
        {
                line = line + Category[i]+" ";
        }

        
        writer.write(line);
        writer.newLine();
        
        for(int i=0;i<Category.length;i++)
        {
                line = Category[i]+" "+ C.category_word.get(Category[i])+" "+C.category_Document.get(Category[i]);
                writer.write(line);
                writer.newLine();
        }
        
        
                writer.write(C.getNoOfDistinctWords()+"");
                writer.newLine();
        
        
                writer.write(C.Doc.length+" ");
                writer.newLine();

        
            newEntery = (Map.Entry) iterator.next();
            word = (String) newEntery.getKey();
            
        while(iterator.hasNext())
        {
            newEntery = (Map.Entry) iterator.next();
            word = (String) newEntery.getKey();
            if(word==null || word=="")
                continue;
            line = word + " ";
            
            if(word == "" || word ==null)
                continue;
            
            for( int i=0;i<Category.length;i++)
            {
                
                if (C.words1.get(word).containsKey(Category[i]) )                   
                    line = line + C.words1.get(word).get(Category[i]) + " ";
                else 
                    line = line + "0 ";
                
            }
            
            writer.write(line);
            writer.newLine();
        }
        
    }


    

}
