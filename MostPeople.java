import java.util.*;
import java.lang.*;

public class MostPeople {
    private ArrayList<Person> people;
    boolean DEBUG;
    private int beginYears;
    private int numberYears; 
    private int numberPeople;

    MostPeople() {
	beginYears = 1900;
	numberYears = 10;
	numberPeople = 5;

	DEBUG = true;
	populateData();
	if (DEBUG) {
	    printPeople();	
	}
    }

    private void populateData() {
	people = new ArrayList<Person>();
	// Let's create a few random people
	int birthYear;
	int endYear;
	int i = 0;
	while (i<numberPeople && numberPeople>0) {
	    birthYear = (int)(Math.random()*numberYears+1)+beginYears;
	    endYear = (int)(Math.random()*numberYears+1)+beginYears;
	    if (birthYear<=endYear) {
		people.add(new Person(birthYear,endYear,""+(++i)));
	    }
	}
    }

    private void printPeople() {
	for (Person p: people) {
	    System.out.println(p.toString());
	}
    }

    private void printYears(Hashtable<Integer, Integer> yearPeopleAlive) {
	Enumeration en = yearPeopleAlive.keys();
	while (en.hasMoreElements()) {
	    Integer key = (Integer)en.nextElement();
	    System.out.println("Year: "+key+" Alive: "+yearPeopleAlive.get(key));
	}
    }
    
    private int computeMaxYearN2() {
	// n2 loop over people and years
	Hashtable<Integer, Integer> yearPeopleAlive = 
	    new Hashtable<Integer, Integer>();
	for (Person p: people) {
	    for (int year=p.getBirthYear(); year<=p.getEndYear(); year++) {
		Integer newVal = yearPeopleAlive.containsKey(year) ? 
		    yearPeopleAlive.get(year)+1 : 1;
		yearPeopleAlive.put(year, newVal);	
	    }
	}
	if (DEBUG) {
	    printYears(yearPeopleAlive);
	}
	// determine which year most people are alive
	int maxYear = -1;
	int maxPeople = -1;
	for (Map.Entry<Integer,Integer> entry: yearPeopleAlive.entrySet()) {
	    if (entry.getValue()>maxPeople) {
		maxPeople = entry.getValue();
		maxYear = entry.getKey();
	    }
	}
	return maxYear;
    }

    private int computeMaxYearLinear() {
	// Algorithm: Create an array that adds 1 for a birth of a person
	// and subtracts 1 for a loss. When aggregating the array from
	// left to right, one can determine when most people where alive
	int[] acc = new int[numberYears+1];
	for (int i=0; i<=numberYears; i++) {
	    acc[i] = 0;
	}
	for (Person p: people) {
	    acc[p.getBirthYear()-beginYears]++;
	    acc[p.getEndYear()-beginYears]--;
	}
	// sum up and find max
	int theMax = -1;
	int theLocation = -1;
	int[] max = new int[numberYears+1];
	for (int k=0; k<=numberYears; k++) {
	    if (k==0) {
		max[k] = acc[k];
	    } else {
		max[k] = max[k-1] + acc[k];
	    } 
	    if (max[k]>=theMax) {
		theMax = max[k];
		theLocation = k;
	    }
	}
	return theLocation+beginYears;
    }

    public static void main(String[] args) {
	MostPeople mostPeople = new MostPeople();
	int year = mostPeople.computeMaxYearN2();
	System.out.println("Most people are alive (amongst others) in : "+year);
	year = mostPeople.computeMaxYearLinear();
	System.out.println("Most people are alive (amongst others) in : "+year);

    }
}