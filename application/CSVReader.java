package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
//import java.util.Scanner;
import java.util.TreeMap;

public class CSVReader {
	Map<String, ArrayList<Integer>> t = new TreeMap<>();
	private String pathname = "";
	private boolean check;
	private ArrayList<String> UpdateInfo = new ArrayList<>();

	public CSVReader() {
    }

	public CSVReader(String pathname) {
		this.pathname = pathname;
		reader();
	}

//	private String scanner() {
//		Scanner myObj = new Scanner(System.in);
//		System.out.println("Enter path to csv file : ");
//		
//		String path = myObj.nextLine();
//		return path;
//	}
	/**
	 * This method parses a cvs file ands the data into a treemap
	 */
	void reader() {
		String cvsFile = pathname;
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(cvsFile))) {
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(cvsSplitBy);

				if (data[3].contentEquals("US")) {
					String state = data[2];

					if (!t.containsKey(state)) { // if the state is not already
													// in the treemap
						ArrayList<Integer> vals = new ArrayList<>();

						int confirmed = Integer.parseInt(data[7]);
						int deaths = Integer.parseInt(data[8]);
						int recovered = Integer.parseInt(data[9]);

						vals.add(confirmed); // adds the confirmed cases
						vals.add(deaths); // adds the deaths
						vals.add(recovered); // adds the recovered

						t.put(state, vals);

					} else { // the state already exists as a key

						for (Map.Entry<String, ArrayList<Integer>> k : t
								.entrySet()) { // loop through the treemap

							if (data[2].contentEquals(k.getKey())) { // find the
																		// key
																		// that
																		// matches
																		// the
																		// entry

								int confirmed = k.getValue().get(0)
										+ Integer.parseInt(data[7]); // add
																		// previous
																		// and
																		// new
																		// cases
								int deaths = k.getValue().get(1)
										+ Integer.parseInt(data[8]); // add
																		// previous
																		// and
																		// new
																		// deaths
								int recovered = k.getValue().get(2)
										+ Integer.parseInt(data[9]); // add
																		// previous
																		// and
																		// new
																		// recovered

								k.getValue().set(0, confirmed); // update
																// confirmed
																// numbers
								k.getValue().set(1, deaths); // update deaths
								k.getValue().set(2, recovered); // update
																// recovered
							}

						}
					}
				}
			}
			check = true;
			//System.out.println(check);
		} catch (Exception e) {
			//System.out.println("Error! Bad pathname. Unable to parse file.");
		    check = false;
		    //System.out.println(check);
		    
		}
	}
	
	public void addNew(String dataType, String state, int number) {;
	    int type = 0;
	    if(dataType.contains("Recovered")) {
	        type = 2;
	    }else if (dataType.contains("Deaths")) {
	        type = 1;
	    }
	    for (Map.Entry<String, ArrayList<Integer>> k : t.entrySet()) {
            if (k.getKey().equals(state)) {
                int temp = k.getValue().get(type)+number;
                k.getValue().set(type, temp);
                UpdateInfo.add(state + " has " + number + " new " + dataType);
            }
        }
       
	}

	/**
	 * This method creates a new txt file that notes any new information
	 * 
	 * @param newCases - number of new cases to report
	 * @param type     - type of case (i.e., deaths, confirmed, recovered)
	 * @param state    - state where there is a new case
	 * @return a txt file that reports the new cases
	 * @throws IOException
	 */
	public File printFile()throws IOException {
		File file = new File("UpdateLog.txt");
		FileWriter writer = new FileWriter(file);
		if(UpdateInfo != null)
		for(int i = 0; i < UpdateInfo.size(); i++) {
		    writer.write(UpdateInfo.get(i));
		}
		writer.close();
		return file;
	}

	void printContents() {
		for (Map.Entry<String, ArrayList<Integer>> k : t.entrySet()) {
			System.out.println(k.getKey());
			System.out.println("confirmed: " + k.getValue().get(0)
					+ ", deaths: " + k.getValue().get(1) + ", recovered: "
					+ k.getValue().get(2));
		}
	}

	/**
	 * Method to get total confirmed cases 
	 * @return total confirmed cases 
	 */
	public int getTotalCases() {
		int total = 0;
		for (Map.Entry<String, ArrayList<Integer>> k : t.entrySet()) {
			ArrayList<Integer> temp = k.getValue();
			total += temp.get(0);
		}
	
		return total;
	}
	
	/**
	 * Method to get total deaths
	 * @return total deaths
	 */
	public int getTotalDeaths() {
		int total = 0;
		for (Map.Entry<String, ArrayList<Integer>> k : t.entrySet()) {
			ArrayList<Integer> temp = k.getValue();
			total += temp.get(1);
		}
	
		return total;
	}
	
	/**
	 * Method to get total recovered 
	 * @return total recovered 
	 */
	public int getTotalRecovered() {
		int total = 0;
		for (Map.Entry<String, ArrayList<Integer>> k : t.entrySet()) {
			ArrayList<Integer> temp = k.getValue();
			total += temp.get(2);
		}
	
		return total;
	}
	/**
	 * Method to get total confirmed cases in a state 
	 * 
	 * @param state - specified state 
	 * @return total confirmed cases for specific state 
	 */
	public int getTotalCases(String state) {
		int total = 0;
		for (Map.Entry<String, ArrayList<Integer>> k : t.entrySet()) {
			if (k.getKey().equals(state)) {
				ArrayList<Integer> temp = k.getValue();
				total += temp.get(0);
			}
		}
	
		return total;
	}
	
	/**
	 * Method to get total deaths for a specific state 
	 * 
	 * @param state - specified state 
	 * @return total deaths for specific state 
	 */
	public int getTotalDeaths(String state) {
		int total = 0;
		for (Map.Entry<String, ArrayList<Integer>> k : t.entrySet()) {
			if (k.getKey().equals(state)) {
				ArrayList<Integer> temp = k.getValue();
				total += temp.get(1);
			}
		}
	
		return total;
	}

	/**
	 * Method to get total recovered cases for a specific state 
	 * 
	 * @param state - specified state
	 * @return total recovered cases for a specific state 
	 */
	public int getTotalRecovered(String state) {
		int total = 0;
		for (Map.Entry<String, ArrayList<Integer>> k : t.entrySet()) {
			if (k.getKey().equals(state)) {
				ArrayList<Integer> temp = k.getValue();
				total += temp.get(2);
			}
		}
	
		return total;
	}	
	
	/**
     * Method to get check successful parses csv.file 
     * 
     * @param state - specified state
     * @return total recovered cases for a specific state 
     */
	public boolean getCheck() {
	    return check;
	}
	
	
//	public static void main(String[] args) {
//		CSVReader tester = new CSVReader(
//				"/Users/jadelee/Documents/cs400/a3/data.csv");
//		tester.reader();
//		tester.printContents();
//		System.out.println("getTotalCases(): " + tester.getTotalCases());
//		System.out.println("getTotalDeaths(): " + tester.getTotalDeaths());
//		System.out.println("getTotalRecovered(): " + tester.getTotalRecovered());
//		System.out.println("getTotalCases(Wisconsin) " + tester.getTotalCases("Wisconsin"));
//		System.out.println("getTotalCases(Wisconsin) " + tester.getTotalDeaths("Wisconsin"));
//		System.out.println("getTotalCases(Wisconsin) " + tester.getTotalRecovered("Wisconsin"));
//	}
}

