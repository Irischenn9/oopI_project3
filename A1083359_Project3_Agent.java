import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class A1083359_Project3_Agent
{
    // Number of this explorer.
    private final int number;
    // This explorer's status of exploration, which determines whether they stay in the tomb or not.
    private boolean inExploring;
    // The gems that this explorer has collected during a round.
    private int collectedGems;
    // The gems which were stored safely in this explorer's tent.
    private int gemsInsideTent;
    // This explorer's possession of artifacts.
    private final ArrayList<A1083359_Project3_Artifact> ownedArtifacts = new ArrayList<>();
    
    public A1083359_Project3_Agent(int number)
    {
        this.number = number;
        this.inExploring = false;
        this.collectedGems = 0;
        this.gemsInsideTent = 0;
    }

    public int getNumber()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-1 (Past): Get the variable $number via this accessor method.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        return number;

        /************* END OF YOUR CODE *************/
    }

    public boolean isInExploring()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-2 (Past): Get the variable $inExploring via this accessor method.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        if (inExploring==true) {
    		return true;
    	}else {
    		return false;
    	}


        /************* END OF YOUR CODE *************/
    }

    public void setInExploring(boolean inExploring)
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-3 (Past): Set the variable $inExploring via this mutator method.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        this.inExploring=inExploring;

        /************* END OF YOUR CODE *************/
    }

    public int getCollectedGems()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-4 (Past): Get the variable $collectedGems via this accessor method.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        return collectedGems;


        /************* END OF YOUR CODE *************/
    }

    public int getGemsInsideTent()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-5 (Past): Get the variable $gemsInsideTent via this accessor method.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/


        return gemsInsideTent;
        /************* END OF YOUR CODE *************/
    }

    public void addCollectedGems(int additionalGems)
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-6 (Past): Add in additional value of gems to this explorer's collection.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/

        collectedGems=additionalGems+collectedGems;
        /************* END OF YOUR CODE *************/
    }

    public void storeGemsIntoTent()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-7 (Past): Make this explorer's holdings store into their tent.
         * Hint 1: Add in the tent with their collected gems, and remember to reset it to zero.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/

        gemsInsideTent=collectedGems+gemsInsideTent;
    	collectedGems=0;

        /************* END OF YOUR CODE *************/
    }

    public ArrayList<A1083359_Project3_Artifact> getOwnedArtifacts()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-8 (Past): Get the variable $ownedArtifacts via this accessor method.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        return ownedArtifacts;


        /************* END OF YOUR CODE *************/
    }

    public void flee()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-9 (Past): When a hazard occurs, all explorers who still stay in the tomb are forced to flee and leave all treasure behind.
         * Hint 1: Set their collected gems to zero and set their status to false.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        collectedGems=0;
        inExploring=false;

        /************* END OF YOUR CODE *************/
    }

    public int totalValue()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 1-10 (Past): The gems stored in their tent, plus the artifacts they owned, are the value that they totally possessed.
         * Hint 1: To get the value of artifacts, you can use getValue() of Artifact object.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/

        int i=0;
        if(ownedArtifacts.size()!=0){
            i=getGemsInsideTent();
            for(A1083359_Project3_Artifact getStay:ownedArtifacts) {
              
                i+=getStay.getValue();
            }
        }else{

            i=getGemsInsideTent();
        }        
        return i;

        /************* END OF YOUR CODE *************/
    }
    
    @Override
    public String toString()
    {
        return "Explorer " + this.number;
    }

    public final void act(A1083359_Project3_Environment environment)
    {
        if (this.inExploring)
        {
            String fileName = "";

            try
            {
                fileName = environment.generateFile(this);
                this.inExploring = this.decision(fileName);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e.getMessage());
            }
            finally
            {
                File file = new File(fileName);
                file.delete();
            }
        }
    }

    public abstract boolean decision(String fileName) throws IOException;
}
