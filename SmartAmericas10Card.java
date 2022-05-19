/* PLEASE DO NOT MODIFY A SINGLE STATEMENT IN THE TEXT BELOW.
READ THE FOLLOWING CAREFULLY AND FILL IN THE GAPS

I hereby declare that all the work that was required to 
solve the following problem including designing the algorithms
and writing the code below, is solely my own and that I received
no help in creating this solution and I have not discussed my solution 
with anybody. I affirm that I have read and understood
the Senate Policy on Academic honesty at 
https://secretariat-policies.info.yorku.ca/policies/academic-honesty-senate-policy-on/
and I am well aware of the seriousness of the matter and the penalties that I will face as a 
result of committing plagiarism in this assignment.

BY FILLING THE GAPS,YOU ARE SIGNING THE ABOVE STATEMENTS.

Full Name: Arpit Nileshbhai Thakkar
Student Number: 217632340
Course Section: Z
*/

package eecs2030.pe2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents a smart phone card for the Americas. For SmartAmericas10 phone cards, only calls to Canada, the USA, and Latin America are allowed. 
 * For calls to Canada the cost per minute is $0.03, for calls to the USA the cost per minute is $0.05, and for calls to Latin America the cost per minute is $0.10. The initial balance on the card is $10.00. The weekly fees are $0.30.
 * Each card also keeps track of the history of calls charged to the card. The call history is List<Call>. For example, a brand new card would have as its call history the empty list []. 
 * If the card was then used to call +14167362100 in Canada for 2 minutes, its call history would then become [call to number +14167362100 in zone CANADA for 2 minutes]. If the card was then used to call +14152121000 in the USA for 2 minutes, 
 * its call history would then become [call to number +14167362100 in zone CANADA for 2 minutes, call to number +14152121000 in zone USA for 2 minutes]. Every time a call is successfully charged to the card, 
 * it is added to the call history. The relation between SmartAmericas10Card and its call history is composition.
 * @author EECS2030
 *
 */

public class SmartAmericas10Card extends PhoneCard{
	
	
	static double	COST_PER_MIN_TO_CANADA = 0.03;
	static double	COST_PER_MIN_TO_LATINAM = 0.1;
	static double	COST_PER_MIN_TO_USA = 0.05;
	static double	INITIAL_BALANCE = 10;
	static double	WEEKLY_FEES = 0.3;		
	
	List<Call> calledHistory;

	/**
	 * Create a SmartAmericas10Card phone card with the given number and password. 
	 * Sets card's balance to INITIAL_BALANCE and its call history to the empty list.
	 * @param number - the card's number.
	 * @param password - the card's password.
	 * @pre number and password are positive.
	 */
	
	SmartAmericas10Card(long number, int password){
		
		super(number, password, INITIAL_BALANCE);
		this.calledHistory = new ArrayList<Call>();
		
	}
	
	/**
	 * Create a copy of the given SmartAmericas10Card. A deep copy is returned.
	 * @param card - the card to make a copy of.
	 * @pre card is not null.
	 */
	
	SmartAmericas10Card(SmartAmericas10Card card){
		super(card.getNumber(), card.getPassword(), INITIAL_BALANCE);
		this.calledHistory = new ArrayList<Call>();
		for(Call c : card.calledHistory) {
			Call callMe = new Call(c);
			this.calledHistory.add(callMe);
		}
	}
	
	/**
	 * Get the history of calls as a List. The client can modify the returned List without changing the state of the card.
	 * 
	 * @return a List containing the call history of the card.
	 */
	public List<Call> getCallHistory(){
		
		return new ArrayList<Call>(this.calledHistory);
	}

	/**
	 * Get the set of call zones that can be called on this phone card. For SmartAmericas10 phone cards, 
	 * only calls to Canada, the USA, and Latin America are allowed.
	 * @return the set of allowed call zones on the card.
	 */
	
	public Set<CallZone> allowedZones() {
		Set<CallZone> allowedZones = new HashSet<CallZone>();
		allowedZones.add(CallZone.CANADA);
		allowedZones.add(CallZone.USA);
		allowedZones.add(CallZone.LATINAM);
		return allowedZones;
	}
	
	/**
	 * Get the cost per minute of a call to the argument zone on this phone card.
	 * @param zone - the call zone to find the cost for.
	 * @pre zone is not null and a call to zone is allowed for this card.
	 * @return the cost per minute to call the given call zone.
	 */
	
	public double costPerMin(CallZone zone) {
		assert this.isAllowed(zone);
	    if(zone == CallZone.CANADA)
	    {
	    	return SmartAmericas10Card.COST_PER_MIN_TO_CANADA;
	    }
	    else if(zone == CallZone.USA)
	    {
	        return SmartAmericas10Card.COST_PER_MIN_TO_USA;
	    }
	    else if(zone == CallZone.LATINAM)
	    {
	    	return SmartAmericas10Card.COST_PER_MIN_TO_LATINAM;
	    }
	    else return 0.0 ;
	}

	/**
	 * Deduct the appropriate weekly fees from the card's balance. If the balance is insufficient, the balance becomes 0.
	 */
	
	public void deductWeeklyFee() {
		if(this.getBalance() <= WEEKLY_FEES) 
			this.setBalance(0);
		else
			this.setBalance(Math.max(0, this.getBalance() - WEEKLY_FEES));
		
	}
	
	/**
	 * Check whether a call to the argument zone is allowed for this phone card. 
	 * For SmartAmericas10 phone cards, only calls to Canada, the USA, and Latin America are allowed
	 * @param zone - the call zone to check.
	 * @pre zone is not null.
	 * @return true if the card supports the call zone; false otherwise.
	 */
	
	public boolean isAllowed(CallZone zone) {
		if(zone == CallZone.CANADA || zone == CallZone.USA || zone == CallZone.LATINAM)
			return true;
		else return false;
	}

	/**
	 * Charge the given call to this phone card. This method tries to charge the given call to the card. 
	 * If the balance is sufficient to cover it, the call is charged and added to the call history, and the value true is returned. 
	 * If the balance is insufficient, the balance and call history are left unchanged and false is returned. The client can later mutate 
	 * the call without changing the state of the card and its call history.
	 * @param call - the call to charge.
	 * @pre call is not null and its zone is allowed for this card.
	 * @return true if the balance was sufficient to pay for the call, and false otherwise.
	 */
	
	public boolean charge(Call call) {
		if(this.getBalance()<=0)
			return false;
		else {
			calledHistory.add(call);
			this.setBalance(this.getBalance()-(((double)call.getMinutes())*this.costPerMin(call.getZone())));
			return true;
		}
	}
	
	/**
	 * Compares the card with another object for equality. 
	 * Two cards are equal if and only if their PhoneCard sub-objects are equal and their call histories are equal.
	 * @param obj - the object to compare with for equality.
	 * @return true if the card and object are equal; false otherwise.
	 */
	
	public boolean equals(Object obj) {
		
		boolean eq = super.equals(obj);
		if(eq)
		{
			SmartAmericas10Card other = (SmartAmericas10Card) obj;
			if(!this.getCallHistory().equals(other.getCallHistory()))
			{
				eq = false;
			}
		}
		return eq;
	}
		
}