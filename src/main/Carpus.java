package main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Carpus {
    
    public Document [] Doc;
    public int totalDoc;
    public  int NoOfDistinctWords;  
    public HashMap<String,HashMap<String,Integer>> words = new HashMap<String, HashMap<String, Integer>>();
    public HashMap<String,HashMap<String,Integer>> words1 = new HashMap<String, HashMap<String, Integer>>();
    
    public HashMap<String,Integer> category_Document = new HashMap<String, Integer>();
    public HashMap<String,Integer> category_word = new HashMap<String, Integer>();
    public int C;

    public Carpus() {
        this.Doc = null;
        NoOfDistinctWords=0;
        C=0;
    }
    
    public String [] getAllCategory()
    {
        String [] array = new String[category_Document.size()] ;
        int count = 0;
        
        Map.Entry newEntery ;          
        
        Iterator allCagegorys = category_Document.entrySet().iterator();
        
        while( allCagegorys.hasNext() )
        {
            newEntery = (Map.Entry) allCagegorys.next();
            array[ count++ ] = (String) newEntery.getKey();
            
        }
        return array;
    }

    public int getTotalNumberOfDocument()
    {
        return Doc.length;
        
    }
    
    public void Add(Document D)
    {
        
        if( category_Document.containsKey(D.getCategory()))
        {
            Integer tmp = category_Document.get(D.getCategory());
            tmp++;
            
            category_Document.put(D.getCategory(), tmp);
            
            tmp = category_word.get(D.getCategory());
            tmp = tmp + D.getTotalNoOfWords();
            
            category_word.put(D.getCategory(), tmp);
            
        }
        else
        {
           category_Document.put(D.getCategory(), 1);
           category_word.put(D.getCategory(),(Integer) D.getTotalNoOfWords());
        }
        
        if( Doc == null)
        {
            Doc = new Document[1];
            Doc[0] = D;
            someProcessing(D);

           return;    
        }
        
        Document [] tmp = new Document[Doc.length+1];
        tmp = Doc;
        Doc = new Document[ Doc.length + 1];
        System.arraycopy(tmp, 0, Doc, 0, tmp.length);
        Doc[Doc.length -1 ] = D;
        
        NoOfDistinctWords = temp1();
        someProcessing(D);
        
    }
    
    
    public int getNoOfWords(String Category)
    {
        int count = 0;
        for( int i=0; i<Doc.length; i++)
        {
            if( Doc[i].getCategory() == null ? Category == null : Doc[i].getCategory().equals(Category))
                count +=  Doc[i].getTotalNoOfWords();
        }
    
    return count;
    }
    public int getDocCount()
    {
        return Doc.length;
    }
    
    public int getDocCount(String Category)
    {
        int count = 0;
        for( int i=0; i<Doc.length; i++)
        {
            if( Doc[i].getCategory() == null ? Category == null : Doc[i].getCategory().equals(Category))
                count ++;
        }
    
    return count;    
    }
    
    public int getNoOfWords(String Category,String word)
    {
        int count = 0;
        for( int i=0; i<Doc.length; i++)
        {
            if( Doc[i].getCategory() == null ? Category == null : Doc[i].getCategory().equals(Category) )
            {
                for(int j=0;j<Doc[i].getTotalNoOfWords();j++)
                {
                    if( Doc[i].listOfWords[j].equalsIgnoreCase(word) )
                        count++;
                }
            }
        }
    
    return count;
    }
    
    
    public int getNoOfDistinctWords()
    {

     
    return NoOfDistinctWords;
    }
    

    private int temp1()
    {
        HashMap<String,String> myHash = new HashMap<String, String>();
        
        for( int i=0; i< Doc.length;i++)
        {
            for(int j=0;j<Doc[i].getTotalNoOfWords(); j++)
                myHash.put(Doc[i].listOfWords[j],Doc[i].listOfWords[j] );
            
        }
        
        return myHash.size();
    }

    private void someProcessing(Document D) {
        


            String [] allWords = D.getdistinctWords();
            String word = "";
            
            
            for(int i=0;i<allWords.length;i++)
            {
                word = allWords[i];
                if( this.words.containsKey( word)   )
                {
                    if(this.words.get(word).containsKey(D.getCategory()))
                    {
                       Integer tmp = (Integer) this.words.get(word).get(D.getCategory());
                       tmp++;                                              
                       this.words.get(word).put(D.getCategory(), tmp);
                    }
                    else
                        this.words.get(word).put(D.getCategory(), 1);
                    
                }
                else
                {
                    HashMap<String ,Integer> tmp = new HashMap<String, Integer>();
                    tmp.put( D.getCategory(), 1);
                    this.words.put(word, tmp);
                }
            
            }
            
            allWords = D.listOfWords;
            
            
            
            for(int i=0;i<allWords.length;i++)
            {
                word = allWords[i];
                if( this.words1.containsKey( word)   )
                {
                    if(this.words1.get(word).containsKey(D.getCategory()))
                    {
                       Integer tmp = (Integer) this.words1.get(word).get(D.getCategory());
                       tmp++;                                              
                       this.words1.get(word).put(D.getCategory(), tmp);
                    }
                    else
                        this.words1.get(word).put(D.getCategory(), 1);
                    
                }
                else
                {
                    HashMap<String ,Integer> tmp = new HashMap<String, Integer>();
                    tmp.put( D.getCategory(), 1);
                    this.words1.put(word, tmp);
                }
            
            }           
 }
    
}
