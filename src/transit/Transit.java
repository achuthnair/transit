package transit;

import java.util.ArrayList;


/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 *
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer


	
	
	public Transit() { trainZero = null; }

	public Transit(TNode tz) { trainZero = tz; }
	

	public TNode getTrainZero () {
		return trainZero;
	}

	

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {

		TNode head = new TNode();
		trainZero = head;
		
		for(int k=0; k<trainStations.length;k++){

		head.setNext(new TNode(trainStations[k]));
		head= head.getNext();
		
		}
	

	
		TNode busZero = new TNode();
		head = new TNode();
		busZero = head;
		for(int j=0; j<busStops.length;j++){


		head.setNext(new TNode(busStops[j]));
		head = head.getNext();
		}
	

		TNode head3 = new TNode();
		TNode walkZero =  new TNode();
		walkZero = head3;
		for(int i=0; i<locations.length;i++){
			head3.setNext(new TNode(locations[i]));
			head3 = head3.getNext();
		}

		TNode tempT;
		tempT = trainZero;
		TNode tempB;
		tempB = busZero;

		for(int i=0; i<=trainStations.length;i++){
			while(tempT.getLocation()!=tempB.getLocation()){
				tempB = tempB.getNext();
			}
			tempT.setDown(tempB);
			tempT = tempT.getNext();

		}

		TNode tempB2;
		tempB2 = busZero;
		TNode tempW;
		tempW = walkZero;


		for(int i=0; i<=busStops.length;i++){
			while(tempB2.getLocation()!=tempW.getLocation()){
				tempW = tempW.getNext();
			}
			tempB2.setDown(tempW);
			tempB2 = tempB2.getNext();

		}
	}







		
	
		













		



	    // UPDATE THIS METHOD
	
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {

		TNode temp = trainZero;

		while(temp.getNext()!=null){
			if(temp.getNext().getLocation()==station && temp.getNext().getNext()==null){
				temp.setNext(null);
			}
			else{
				if(temp.getNext().getLocation()==station){
					temp.setNext(temp.getNext().getNext());
				}
				temp = temp.getNext();
			}
		}


	    // UPDATE THIS METHOD
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {

		TNode busT = trainZero.getDown();
        TNode walkT = busT.getDown();

        while(walkT.getNext()!=null && walkT.getLocation()!=busStop){
            walkT = walkT.getNext();
            if (busT.getNext()!=null && walkT.getLocation()==busT.getNext().getLocation()) busT = busT.getNext();
        }
        if (walkT.getLocation()<busStop || busT.getLocation()==busStop){
            return;
        } 

        TNode newBusStop = new TNode(busStop, busT.getNext(), walkT);

        busT.setNext(newBusStop);
    }




	    // UPDATE THIS METHOD
	
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {

		ArrayList<TNode> path = new ArrayList<>();
		TNode temp1 = trainZero;
		while(temp1!=null && temp1.getLocation() <= destination){
			path.add(temp1);
			temp1 = temp1.getNext();
		}
		ArrayList<TNode> temp = path;
		temp1 = temp.get(temp.size()-1).getDown();
		while(temp1!=null && temp1.getLocation() <= destination){
			path.add(temp1);
			temp1 = temp1.getNext();
		}
		temp = path;
		temp1 = temp.get(temp.size()-1).getDown();
		while(temp1!=null && temp1.getLocation() <= destination){
			path.add(temp1);
			temp1 = temp1.getNext();
		}

		return path;

	    // UPDATE THIS METHOD
	    
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {

		TNode temp1 = new TNode();
        TNode tempt = temp1;
        TNode tempB = trainZero;
        TNode tempB1;
        TNode temp2;

        while(tempB != null)
        {
            temp2 = tempt;
            tempB1 = tempB.getNext();
            while(tempB1 != null)
            {
                temp2.setNext(new TNode(tempB1.getLocation()));
                temp2 = temp2.getNext();
                tempB1 = tempB1.getNext();
            }
            tempB = tempB.getDown();
            if(tempB != null)
            {
                tempt.setDown(new TNode(tempB.getLocation()));
                tempt = tempt.getDown();
            }
        }
        TNode temp3;
        tempt = temp1;
        while(tempt.getDown() != null)
        {
            temp2 = tempt;
            temp3 = tempt.getDown();
            while(temp2 != null)
            {
                while(temp3.getLocation() < temp2.getLocation())
                {
                    temp3 = temp3.getNext();
                }
                temp2.setDown(temp3);
                temp2 = temp2.getNext();
            }
            tempt = tempt.getDown();
        }
        return temp1;
    }




	    // UPDATE THIS METHOD
	

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {

		
		TNode scooterZero;

	    TNode busZero = trainZero.getDown();
		TNode walkZero = trainZero.getDown().getDown();
		TNode head = new TNode();


		scooterZero = head;
	

		for(int i=0;i<scooterStops.length;i++){
			head.setNext(new TNode(scooterStops[i]));
			head = head.getNext();  

		}

		TNode busNull = busZero;
		while(busNull!=null){
			busNull.setDown(null);
			busNull = busNull.getNext();
		}

		busZero.setDown(scooterZero);
		TNode bustemp1 = busZero;
		TNode scootertemp1 = scooterZero;

		while(bustemp1!=null){
			if(bustemp1.getLocation()==scootertemp1.getLocation()){
				bustemp1.setDown(scootertemp1);
				bustemp1 = bustemp1.getNext();
			}
			scootertemp1=scootertemp1.getNext();
		}

		scooterZero.setDown(walkZero);
		TNode scootertemp2 = scooterZero;
		TNode walktemp1 = walkZero;
		while(scootertemp2!=null){
			if(scootertemp2.getLocation()==walktemp1.getLocation()){
				scootertemp2.setDown(walktemp1);
				scootertemp2 = scootertemp2.getNext();
			}
			walktemp1 = walktemp1.getNext();
		}

	    // UPDATE THIS METHOD
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
