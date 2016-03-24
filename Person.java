public class Person {

    private int birthYear;
    private int endYear;
    private String name;

    Person(int bYear, int eYear, String nameP) {
	birthYear = bYear;
	endYear = eYear;
	name = nameP;
    }

    public int getBirthYear() {
	return birthYear;
    }

    public int getEndYear() {
	return endYear;
    }

    public String toString() {
	return ""+birthYear+", "+endYear+", "+name;
    }
}

