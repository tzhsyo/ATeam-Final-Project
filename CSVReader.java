package a3;

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
	static String pathname = "";

	public CSVReader(String pathname) {
		this.pathname = pathname;
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

		} catch (IOException e) {
			System.out.println("Error! Bad pathname. Unable to parse file.");
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
	File addNew(String newCases, String type, String state)
			throws IOException {
		File file = new File("new.txt");
		FileWriter writer = new FileWriter(file);

		writer.write(state + " has " + newCases + " new " + type);
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

	public static void main(String[] args) {
		CSVReader tester = new CSVReader(
				"/Users/jadelee/Documents/cs400/a3/data.csv");
		tester.reader();
		tester.printContents();

	}
}

