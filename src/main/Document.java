package main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Document {
    

    public int ID;
    private String words;
    private String category;
    public String [] distinctWords;
    public String [] listOfWords;
    private HashMap<String,String> myHash = new HashMap<String, String>();
    
    private int totalNoOfWords;
    private int totalNoOfDistinctWords;
    
    
    String [] getdistinctWords()
    {
    

     HashMap<String,String> tmpHash = new HashMap<String, String>(); 
     
     for(int i=0;i<listOfWords.length;i++)
                    tmpHash.put(listOfWords[i], "");
     
                    distinctWords = new String[tmpHash.size()];
                    int count=0;
                     Iterator it = tmpHash.entrySet().iterator();
                    while (it.hasNext()) {

                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    distinctWords[count++] =  key;

                    }      
        
        
        return distinctWords;
    }

    public int getTotalNoOfDistinctWords() {
        return totalNoOfDistinctWords;
    }
    


    public int getTotalNoOfWords() {
        return totalNoOfWords;
    }

    public Document(int ID,String words) {
        this.words = words;
        this.ID = ID;
        this.distinctWords = null;
        
        listOfWords = words.split("[\\s+;.,:'!?()-<>'=#__"+'"'+"]");
        totalNoOfWords = listOfWords.length;
    


        for(int i=0;i<listOfWords.length;i++)
        {
            if( myHash.get(listOfWords[i]) == null  )
                myHash.put(listOfWords[i], "1");
            else
            {
                int tmpCount = Integer.parseInt(myHash.get(listOfWords[i]));
                tmpCount++;
                myHash.put(listOfWords[i], tmpCount+"");
            }
        }
        

            totalNoOfDistinctWords = myHash.size();        

        
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
    
    public boolean isPresent(String word)
    {

         
        for(int i=0;i<listOfWords.length ;i++)
        {
            if( word.equalsIgnoreCase(listOfWords[i]))
                return true;
            
        }
        
        
        return false;
    }
}
