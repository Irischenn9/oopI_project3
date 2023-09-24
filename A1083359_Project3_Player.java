import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;




public class A1083359_Project3_Player extends A1083359_Project3_Agent
{
    public A1083359_Project3_Player(int number)
    {
        super(number);
    }

    @Override
    public boolean decision(String fileName) throws IOException
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 7-1: Here goes your algorithm for the gameplay. In order to get more treasure and reduce the risk,
         *            you have to make your own decision based on the current game state.
         * Hint 1: The parameter $fileName indicates the file path that you need to read.
         *         The file contains information about the current path, removed hazards, count of explorers who stay, etc.
         * Hint 2: This method requires you to return a boolean value. If the return value is true,
         *         it means that you'd like to keep your exploration and stay in the tomb, otherwise, you chose to leave.
         * Hint 3: To recognize the format of the file content, you can just temporarily print it out or see what the document shows.
         *         The file itself will not be preserved after this method finishes.
         * Notice 1: Your explorer's data such as collected gems can be accessed from the parent class,
         *           but due to the orientation of this checkpoint (file I/O), all information must be read from the given file.
         *           That is, do not call any method or variable from the parent class.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/       
            String content3=readPointLine(fileName, 3);//path
            String content4=readPointLine(fileName, 4);//hazard
            String content5=readPointLine(fileName, 5);//explorer
            String content10=readPointLine(fileName, 10);//collect           
            String lastCharacter =content10.substring(content10.length() - 2);//collect的值


            //hazard有path有
            int count=0;
            if(getMem(content4,"Spikes")==1){
                if(getMem(content3,"Spikes")==1){
                   count++;
                }

            }else  if(getMem(content4,"Spiders")==1){
                if(getMem(content3,"Spiders")==1){
                    count++; 
                }

            }else  if(getMem(content4,"Mummy")==1){
                if(getMem(content3,"Mummy")==1){
                    count++;
                }

            }else  if(getMem(content4,"Curse")==1){
                if(getMem(content3,"Curse")==1){
                    count++;
                }

            }else  if(getMem(content4,"Collapse")==1){
                if(getMem(content3,"Collapse")==1){
                    count++; 
                 }
            }


            if(count>=1){
                if(getMem(content3,"H")==4){
                    if(getMem(lastCharacter,"10")==1||getMem(lastCharacter,"11")==1||getMem(lastCharacter,"12")==1||getMem(lastCharacter,"13")==1||
                    getMem(lastCharacter,"14")==1||getMem(lastCharacter,"15")==1||getMem(lastCharacter,"16")==1||getMem(lastCharacter,"17")==1||
                    getMem(lastCharacter,"18")==1||getMem(lastCharacter,"19")==1||getMem(lastCharacter,"20")==1){
                        return false;

                    }else{
                        return true;
                    }                  
                }
            }

            if(count==0){
                if(getMem(content3,"H")==3){
                    return false;
                }
            }

            if(getMem(content3,"H")==3){
                if(getMem(content5,"3 out of 6 explorers stay in the tomb.")==1||getMem(content5,"2 out of 6 explorers stay in the tomb.")==1||
                getMem(content5,"1 out of 6 explorers stay in the tomb.")==1){
                    if(count<=2){
                        if(getMem(content3,"0")>3){  
               
                            return false;  
                        }
                    }   
                }  
            }

            if(getMem(content3,"Meteoric Dagger")==1||getMem(content3,"Ankh")==1||getMem(content3,"Falcon Pectoralr")==1||
            getMem(content3,"Crook and Flail")==1||getMem(content3,"Mask of Tutankhamun")==1||getMem(content3,"Unknown")==1){  
                if(getMem(content5,"2 out of 6 explorers stay in the tomb.")==1||getMem(content5,"1 out of 6 explorers stay in the tomb.")==1){  
                    return false;  
                } 
            }
      
  
        if(getMem(content3,"H")<=3){
            if(getMem(lastCharacter,"13")==1||getMem(lastCharacter,"14")==1||getMem(lastCharacter,"15")==1||getMem(lastCharacter,"16")==1||
            getMem(lastCharacter,"17")==1||getMem(lastCharacter,"18")==1||getMem(lastCharacter,"19")==1||getMem(lastCharacter,"20")==1||
            getMem(lastCharacter,"21")==1||getMem(lastCharacter,"22")==1||getMem(lastCharacter,"23")==1||getMem(lastCharacter,"24")==1||getMem(lastCharacter,"25")==1){  
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }

    
        /************* END OF YOUR CODE *************/
    }

    public static int getMem(String str,String m ){
        int i=0;
        while(str.indexOf(m)!=-1){
        int a=str.indexOf(m);
        str=str.substring(a+1);
        i++;
        }
        return i;
    }
    public static String readPointLine(String fileName,int readLine) throws IOException{
        String line;//讀取每行內容
        try (

            BufferedReader br = Files.newBufferedReader(Paths.get(fileName))){
            int i=0;

            //每次讀取一行，一行一行的讀取 br.readLine()        
            while ((line = br.readLine()) != null) {
                i++;
                if(i==readLine){
                    return line;
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }


        
}
