import java.util.ArrayList;
import java.util.Random;


public class Bankers {
	public static final int MAX_RESOURCES = 10;	//Max number of resources in the system
	public static final int MAX_PROCESSES = 10;	//Max number of processes in the system
	public static final int MAX_INSTANCE  = 6;  //Max number of resource instances that are avaiable
	
	Matrix allocation;			//This matrix defines what resources are currently allocated in the system
	Matrix max;					//The total number of resouces the processes need to complete. 
	Matrix available;			//The number of resouces available after each process completes Previous row + allocation[process]
	Matrix need;				//The current number of resources each process needs to complete. Max - allocation
	
	ArrayList<Process> processes;		//All the processes currently in the system.
	
	public Process generateProcess(){
		Process p = null;
		
		if(processes.size() < MAX_PROCESSES){ //Only generate a new process if the system is not full
			Random r = new Random();
			int[] maxResources = new int[MAX_RESOURCES]; //Generate the new resources
			
			for(int i = 0; i < MAX_RESOURCES; i++){
				maxResources[i] = (int)(r.nextFloat() * MAX_INSTANCE + 1); //Generate a random integer between 1 and MAX_INSTANCE
			}
			
			p = new Process(processes.size(), maxResources);
			processes.add(p);
			
			//Add to allocation matrix
			//Add to max matrix
			//Add to need matrix
			//Check if add is valid
		}
		
		return p;
	}
	
	public Bankers(){
		//Initialize variables
		processes = new ArrayList<Process>();
	}
	
	public static void main(String[] args) {
		Matrix test = new Matrix("test matrix");
		test.setRow(0, new int[]{1, 2, 3, 4});
		test.setRow(1, new int[]{6});
		System.out.println(test.add(test));
	}

}
