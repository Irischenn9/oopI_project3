import java.util.ArrayList;

public class A1083359_Project3_Gemstone extends A1083359_Project3_Treasure
{
    // The current value (gems) remains on the card.
    private int remainValue;
    
    public A1083359_Project3_Gemstone(int type, int value)
    {
        super(type, value);
        this.remainValue = value;
    }

    public int getRemainValue()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 2-1 (Past): Get the variable $remainValue via this accessor method.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        return remainValue;

        /************* END OF YOUR CODE *************/
    }
    
    public void resetValue()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 2-2 (Past): Reset the current value of this card to its original value.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        remainValue=getValue();

        /************* END OF YOUR CODE *************/
    }

    @Override
    public String name()
    {
        return "Gemstone";
    }

    @Override
    public void share(ArrayList<A1083359_Project3_Agent> receivers)
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 2-3 (Past): Evenly share the gems that they find with all receivers, then the leftover remains.
         * Hint 1: If this gemstone card contains 17 gems and there are 3 receivers,
         *         each receiver will obtain 5 gems and the rest of 2 gems will remain on the card.
         * Hint 2: You can use addCollectedGems() method of Agent object to let the receivers get their gems.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        for(A1083359_Project3_Agent receiver:receivers) {
    		receiver.addCollectedGems(remainValue/receivers.size());
            
    	}
   
    	remainValue=remainValue%receivers.size();

        /************* END OF YOUR CODE *************/
    }
    
    @Override
    public String toString()
    {
        return String.format("<G: %d/%d>", this.remainValue, this.getValue());
    }
}
