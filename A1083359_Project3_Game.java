import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class A1083359_Project3_Game
{
    // The randomizer which is used for shuffling the deck.
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    // The game consists of 5 rounds of exploration.
    private static final int ROUND = 5;

    // All explorers participate in the game.
    private final ArrayList<A1083359_Project3_Agent> explorers = new ArrayList<>();
    // The deck of cards to be used for the game.
    private final ArrayList<A1083359_Project3_Card> deck = new ArrayList<>();
    // A tomb-like path for exploration.
    private final ArrayList<A1083359_Project3_Card> path = new ArrayList<>();
    // There are 5 sections (5 rounds) of exploration in the tomb, and one particular artifact is deposited in each section.
    private final ArrayList<A1083359_Project3_Artifact> artifacts = new ArrayList<>();
    // The Hazards that occurred during the game play.
    private final ArrayList<A1083359_Project3_Hazard> occurredHazards = new ArrayList<>();

    public A1083359_Project3_Game(String[] participants)
    {
        if (participants.length < 3 || participants.length > 8)
            throw new IllegalArgumentException("the number of participants is not between 3 and 8");

        try
        {
            for (int i = 0; i < participants.length; i++)
            {
                String participant = participants[i];
                Class<?> clazz = Class.forName(participant);
                Constructor<?> constructor = clazz.getConstructor(int.class);
                this.explorers.add((A1083359_Project3_Agent) constructor.newInstance(i));
            }
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("invalid participant");
        }
        
        this.setUpCards();
    }
    
    public void runGame()
    {
        for (int round = 0; round < ROUND; round++)
        {
            /********************************* TODO (Checkpoint 3) *********************************
             * TODO 6-1 (Past): First, the game data should be initialized at the beginning of each round.
             * Hint 1: All explorers' status should be switched to true.
             * Hint 2: Clear all cards on the path and shuffle them back in the deck.
             * Hint 3: Reset the value of all gemstone cards.
             * Hint 4: You need to remove the artifact for the previous round from the deck even if it was unrevealed or unclaimed,
             *         then put the one for the current round into the deck.
             * Hint 5: For the hazard that occurred in the previous round (if any), should be removed from the deck.
             * Hint 6: Make sure you use shuffleDeck() method to shuffle the deck.
             * Hint 7: You need to print "ROUND X START!" which X represents for the round number (1~5).
             * Notice 1: In this section, you can use doNothing() method as you like to set timeout between any message you would print,
             *           but the format of your output must identically be the same as what the document shows.
             ************************************* End of TODO *************************************/

            /************ START OF YOUR CODE ************/
            for(A1083359_Project3_Agent explorer:this.explorers) {
                explorer.setInExploring(true);
            } 

            for(int k=0;k<path.size();k++){
                if(path.get(k) instanceof A1083359_Project3_Gemstone){                	
                    deck.add(path.get(k));  
                                
                }else if(path.get(k) instanceof A1083359_Project3_Hazard){
                    if(occurredHazards.size()==1){ 
                        if(occurredHazards.get(0).getType()==path.get(k).getType()) {                      
                         occurredHazards.remove(0);
                        } 

                    }else{
                        deck.add(path.get(k));
                        
                    }                    
                }
            }
            path.clear();

            for(int d=0;d<deck.size();d++){
                if(deck.get(d) instanceof A1083359_Project3_Artifact){
                    deck.remove(deck.get(d));
                }else{
                    continue;
                }
            }
            
            deck.add(artifacts.get(round));
            
            for(A1083359_Project3_Card card:deck){
                if(card instanceof A1083359_Project3_Gemstone){
                    ((A1083359_Project3_Gemstone)card).resetValue();
                }
            } 

            
            shuffleDeck();

            System.out.printf("ROUND %d START!%n", round+1); 
            doNothing(100); 

            /************* END OF YOUR CODE *************/

            while (this.isAnyoneStay())
            {
                /********************************* TODO (Checkpoint 3) *********************************
                 * TODO 6-2 (Past): During a round, all explorers explore the path in the ancient tomb and hunt for abundant treasures.
                 * Hint 1: To move forward in our exploration, you need to draw a card from the top of the deck and put it on the end of the path.
                 * Hint 2: When the drawn card is gemstone, use share() method of Gemstone object to share the value of it with all explorers who stay.
                 * Hint 3: When the drawn card is hazard, check whether it is the secondly same type of hazard that has been drawn.
                 *         If this is the case, all explorers attempt to flee and you should add this card into the occurred hazard.
                 * Hint 4: Print out the information of the path.
                 * Hint 5: Print out the information of all explorers in sequence. If the explorer stays in the tomb, print "Explorer X has Y gem(s).",
                 *         otherwise, print "Explorer X left." which X and Y represent for their number and the quantity of collected gems.
                 * Hint 6: After "----- STAY or LEAVE -----" is printed, all explorers who stay have to make their decision about staying or leaving.
                 *         For this purpose, you can use act() method of Agent object.
                 * Hint 7: Print "Everyone keeps exploring." if there is no explorer choose to leave,
                 *         else print "Explorer X wants to leave." for each explorer who chose to leave, which X represents for their number.
                 * Hint 8: On condition that this round had been broken off by a hazard, you should print "X hazard occurs, all explorers attempt to flee!"
                 *         rather than what Hint 7 does, which X represents the name of that occurred hazard.
                 * Hint 9: For those who chose to leave the tomb, should share the value of all the gemstone cards on the path while each one works independently.
                 *         If the path is [<G: 3/11>, <G: 1/9>, <A: Ankh 7>, <G: 5/13>], for instance, there are 3 explorers who chose to leave,
                 *         then the path will become [<G: 0/11>, <G: 1/9>, <A: Ankh 7>, <G: 2/13>] after they leave.
                 * Notice 1: Beware of the mechanism of sharing an artifact.
                 * Notice 2: The act() method of Agent requires an Environment object as its parameter, which indicates that agent can act upon the environment.
                 *           Note that the variable $environment was already declared for you, all you need to do is pass it into the act() method.
                 *           All explorers should act upon the same environment, so do not declare another Environment object or it may cause some error.
                 * Notice 3: In this section, you can use doNothing() method as you like to set timeout between any message you would print,
                 *           but the format of your output must identically be the same as what the document shows.
                 ************************************* End of TODO *************************************/

                /************ START OF YOUR CODE ************/
                ArrayList<A1083359_Project3_Hazard> occurredHazard = new ArrayList<>();
                

                for(int a=0;a<deck.size();a++){
                      if(occurredHazards.size()==1){
                        break;
                    }else if(isAnyoneStay()==false){
                        break;
                    }
                   
                    path.add(deck.remove(deck.size()-1));

                   
                    //如果抽的牌為寶石
                    if(path.get(a) instanceof A1083359_Project3_Gemstone){

                        ((A1083359_Project3_Gemstone)path.get(a)).share(getStayExplorers());

                    //如果抽的牌為災難
                    }else  if(path.get(a) instanceof A1083359_Project3_Hazard){
                        for(int b=0;b<occurredHazard.size();b++){

                            if (occurredHazard.get(b).getType()==path.get(a).getType()) {
                                occurredHazards.add((A1083359_Project3_Hazard) path.get(a));
                                for(A1083359_Project3_Agent explorer:this.getStayExplorers()) {
                                    explorer.flee();
                                }
                            }
                                    
                        }
                    
                        occurredHazard.add((A1083359_Project3_Hazard)path.get(a));
                    }
                    

                
                    doNothing(400);
                    System.out.println();
                    System.out.print(path);
                    

                    System.out.println();
                  doNothing(100); 

                  
                
                    doNothing(400);
                    //輸出持有寶石數或離開
                    for(A1083359_Project3_Agent getGem:this.explorers) {
                         
                        if(getGem.isInExploring()==true) {
                            System.out.printf(getGem+" has "+getGem.getCollectedGems()+" gem(s).%n");
                        }else {
                            System.out.printf(getGem+" left.%n");
                            
                            
                        }
                    }
                doNothing(300); 

                /************* END OF YOUR CODE *************/

                System.out.println("----- STAY or LEAVE -----");

                A1083359_Project3_Environment environment = this.createEnvironment();

                /************ START OF YOUR CODE ************/
                ArrayList<A1083359_Project3_Agent> leave = new ArrayList<>();
                int count=0;
                int leavenum=0;
                
                for(A1083359_Project3_Agent action:getStayExplorers()) {
                    leavenum++;
                    action.act(environment);
                    if (action.isInExploring()==false) {
                        leave.add(action);
                         
                    }else{
                        count++;
                    }
                        doNothing(300);
                }	                    

                //如果抽的牌為災難
                if(path.get(a) instanceof A1083359_Project3_Hazard){
                   
                    if(occurredHazards.size()==1){
                        for(A1083359_Project3_Hazard Hazard:occurredHazards ){
                            System.out.printf(Hazard.name()+" hazard occurs, all explorers attempt to flee!");
                            System.out.println();

                        }           
                    }
                     doNothing(400);
                            
                 //如果抽的牌為神器       
                }else if(path.get(a) instanceof A1083359_Project3_Artifact){                 
                    
                    if(leave.size()==1){
                        
                        ((A1083359_Project3_Artifact)path.get(a)).share(leave);                                            
                    }
                }
                if(leave.size()==1){
       
                    for(int s=0;s<path.size();s++){
                        if(path.get(s) instanceof A1083359_Project3_Artifact){
                                
                            ((A1083359_Project3_Artifact)path.get(s)).share(leave);                             
                        }   
                    }//doNothing(300);
                                                                                        
               }

               for(A1083359_Project3_Agent leavee: leave){
                    System.out.printf(leavee+" wants to leave.%n");
                }
                // doNothing(400);

               if(count==leavenum) {
                   if(occurredHazards.size()==1){
                    break;
                    }
                    System.out.println("Everyone keeps exploring.");
            
                    doNothing(400);
                }else{
                    for(A1083359_Project3_Card gems:path) {
                        if(gems instanceof A1083359_Project3_Gemstone){
                            ((A1083359_Project3_Gemstone)gems).share(leave);          
                        }
                    }              
                }
            }
            
                System.out.println(); 

                /************* END OF YOUR CODE *************/
            }

            /********************************* TODO (Checkpoint 3) *********************************
             * TODO 6-3 (Past): At the end of a round, all explorers finish their exploration and return to the camp with treasure.
             * Hint 1: First, print "ROUND X END!" which X represents for the round number (1~5).
             * Hint 2: To make all explorers store gems they've collected during this round into their tent,
             *         you can use storeGemsIntoTent() method of Agent object.
             * Notice 1: In this section, you can use doNothing() method as you like to set timeout between any message you would print,
             *           but the format of your output must identically be the same as what the document shows.
             ************************************* End of TODO *************************************/

            /************ START OF YOUR CODE ************/
            System.out.printf("ROUND %d END!%n",round+1);
            System.out.println();
                
            for(A1083359_Project3_Agent stone:this.explorers) {
                 stone.storeGemsIntoTent();	
            }  


            /************* END OF YOUR CODE *************/
        }

        System.out.println("GAME OVER!");
        System.out.println();
        System.out.println("----- Final result -----");

        for (A1083359_Project3_Agent explorer : this.explorers)
            System.out.println(explorer + ": " + explorer.totalValue());

        System.out.println();
        System.out.println("Winner: " + this.getWinners());
    }

    private void setUpCards()
    {
        this.deck.add(new A1083359_Project3_Hazard(0));
        this.deck.add(new A1083359_Project3_Hazard(0));
        this.deck.add(new A1083359_Project3_Hazard(0));
        this.deck.add(new A1083359_Project3_Hazard(1));
        this.deck.add(new A1083359_Project3_Hazard(1));
        this.deck.add(new A1083359_Project3_Hazard(1));
        this.deck.add(new A1083359_Project3_Hazard(2));
        this.deck.add(new A1083359_Project3_Hazard(2));
        this.deck.add(new A1083359_Project3_Hazard(2));
        this.deck.add(new A1083359_Project3_Hazard(3));
        this.deck.add(new A1083359_Project3_Hazard(3));
        this.deck.add(new A1083359_Project3_Hazard(3));
        this.deck.add(new A1083359_Project3_Hazard(4));
        this.deck.add(new A1083359_Project3_Hazard(4));
        this.deck.add(new A1083359_Project3_Hazard(4));
        
        this.deck.add(new A1083359_Project3_Gemstone(0, 1));
        this.deck.add(new A1083359_Project3_Gemstone(1, 2));
        this.deck.add(new A1083359_Project3_Gemstone(2, 3));
        this.deck.add(new A1083359_Project3_Gemstone(3, 4));
        this.deck.add(new A1083359_Project3_Gemstone(4, 5));
        this.deck.add(new A1083359_Project3_Gemstone(4, 5));
        this.deck.add(new A1083359_Project3_Gemstone(5, 7));
        this.deck.add(new A1083359_Project3_Gemstone(5, 7));
        this.deck.add(new A1083359_Project3_Gemstone(6, 9));
        this.deck.add(new A1083359_Project3_Gemstone(7, 11));
        this.deck.add(new A1083359_Project3_Gemstone(7, 11));
        this.deck.add(new A1083359_Project3_Gemstone(8, 13));
        this.deck.add(new A1083359_Project3_Gemstone(9, 14));
        this.deck.add(new A1083359_Project3_Gemstone(10, 15));
        this.deck.add(new A1083359_Project3_Gemstone(11, 17));
        
        this.artifacts.add(new A1083359_Project3_Artifact(0, 5));
        this.artifacts.add(new A1083359_Project3_Artifact(1, 7));
        this.artifacts.add(new A1083359_Project3_Artifact(2, 8));
        this.artifacts.add(new A1083359_Project3_Artifact(3, 10));
        this.artifacts.add(new A1083359_Project3_Artifact(4, 12));
    }

    private void shuffleDeck()
    {
        Collections.shuffle(this.deck, RANDOM);
    }

    private ArrayList<A1083359_Project3_Agent> getStayExplorers()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 5-1 (Past): Get all explorers who stay in the tomb.
         * Hint 1: You can check each explorer's status.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/

        ArrayList<A1083359_Project3_Agent> check = new ArrayList<A1083359_Project3_Agent>();
	    	
        for(A1083359_Project3_Agent getStay:this.explorers) {    		
            if (getStay.isInExploring()==true) {
                check.add(getStay); 
            }
        }return check;
        /************* END OF YOUR CODE *************/
    }

    private boolean isAnyoneStay()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 5-2 (Past): Check if there is anyone who stays in the tomb.
         * Hint 1: Return true if at least one explorer was in exploring.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        if (getStayExplorers().size()==0) {
            return false; 			
        } else{
           return true; 
        }	


        /************* END OF YOUR CODE *************/
    }

    private ArrayList<A1083359_Project3_Agent> getWinners()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 5-3 (Past): The winners will be the explorers who hold the highest value of treasure.
         * Hint 1: You can use totalValue() method of Agent object to check the total value that they hold.
         * Notice 1: There might be multiple winners if more than one explorers equivalently hold the highest value.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        ArrayList<Integer> winner = new ArrayList<>();
        ArrayList<Integer> winnerr = new ArrayList<>();
        ArrayList<A1083359_Project3_Agent>  winners=new ArrayList<>();
        
        for(A1083359_Project3_Agent explorer:this.explorers){
            winner.add(explorer.totalValue());
           
        }  
        winnerr.add(Collections.max(winner));

       
   
        for(A1083359_Project3_Agent explorer:this.explorers){
            for(int i=0;i<winnerr.size();i++){
              if(explorer.totalValue()==winnerr.get(i)){
                winners.add(explorer);
                }  
            }                    
        }
        return winners;

        /************* END OF YOUR CODE *************/
    }

    private A1083359_Project3_Environment createEnvironment()
    {
        A1083359_Project3_Environment environment = new A1083359_Project3_Environment();

        environment.setCountOfExplorers(this.explorers.size());
        environment.setCountOfStayExplorers(this.getStayExplorers().size());
        environment.setPath(this.path);
        environment.setOccurredHazards(this.occurredHazards);

        return environment;
    }

    private static void doNothing(long millisecond)
    {
        if (millisecond > 2000)
            throw new IllegalArgumentException("timeout value is over 2000");

        try
        {
            Thread.sleep(millisecond);
        }
        catch (InterruptedException e)
        {
            throw new IllegalStateException("unexpected interruption");
        }
    }
}
