import java.util.ArrayList;
import java.util.Random;


public class Bankers {
	public static final int MAX_RESOURCES = 10;	//Max number of resources in the system
	public static final int MAX_PROCESSES = 10;	//Max number of processes in the system
	public static final int MAX_INSTANCE  = 6;  //Max number of resource instances that are avaiable
	
	Matrix allocation;			//This matrix defines what resources are currently allocated in the system
	Matrix max;					//The total number of resouces the processes need to complete. 
	int[] available;			//The number of resouces available after each process completes Previous row + allocation[process]
	Matrix need;				//The current number of resources each process needs to complete. Max - allocation
	
	Process[] processes;		//All the processes currently in the system.
	
	int lastCompleted;			//The index of the last completed process in the avaiable matrix.
	
	public Process generateProcess(){
		Process p = null;
		int index = allocation.nextAvailableIndex();
		
		if(index != -1){ //Only generate a new process if the system is not full
			Random r = new Random();
			int[] maxResources = new int[MAX_RESOURCES]; //Generate the resources required to complete
			int[] randAvailable = new int[MAX_RESOURCES]; //Generate the resources already acquired
			
			for(int i = 0; i < MAX_RESOURCES; i++){
				maxResources[i] = (int)(r.nextFloat() * MAX_INSTANCE + 1); //Generate a random integer between 1 and MAX_INSTANCE
				randAvailable[i] = (int)(r.nextFloat() * (MAX_INSTANCE/2)); //0 - MAX_INSTANCE/2
			}
			
			p = new Process(index, maxResources);
			
			
			//Check if add is valid (can the system complete from this point?)
			//Copy all matrices
			Matrix allocationCpy = allocation.copy();
			Matrix maxCpy = max.copy();
			int[] availableCpy = available.clone();
			Matrix needCpy = need.copy();
			Process[] processesCpy = processes.clone();
			processesCpy[index] = p;
			allocationCpy.setRow(index, randAvailable);
			
			for(int i = 0; i < processes.length; i++){
				Process completed = null;
				if((completed = stepMatrices(allocationCpy, maxCpy, availableCpy, needCpy, processesCpy)) != null){
					deleteElemFromArray(processesCpy, completed);
				}
			}
			
			if(processArrayIsEmpty(processesCpy)){
				System.out.println("YES MOFO");
			}else{
				System.out.println("NO DAMN :(");
			}
			//Yes
				//Update to allocation matrix
				//update to max matrix
				//Update to need matrix
			//No
				//Skip or retry? i dunno
		}
		
		return p;
	}
	
	public void deleteElemFromArray(Process[] array, Process p){
		for(int i = 0; i < array.length; i++){
			if(array[i] == p){
				array[i] = null;
				return;
			}
		}
	}
	
	public boolean processArrayIsEmpty(Process[] array){
		for(int i = 0; i < array.length; i++){
			if(array[i] != null)
				return false;
		}
		
		return true;
	}
	
	//Steps through all the matrices returning the process that was completed
	public Process stepMatrices(Matrix allocation, Matrix max, int[] available, Matrix need, Process[] processes) {
		Process completed = null;
		//Iterate through each process and complete the first one that can be completed
		for(Process  p : processes){
			if(p == null) continue;
			
			int index = p.getId();
			
			if(need.rowIsLessThanOrEqualTo(index, available)){
				completed = p;
				available = allocation.addRows(index, available).getRow(index);
				allocation.deleteRow(index);
				max.deleteRow(index);
				need.deleteRow(index);
			}
		}
		return completed;
	}
	
	public Bankers(){
		//Initialize variables
		processes 	= new Process[MAX_PROCESSES];
		allocation 	= new Matrix("Allocation");
		max 		= new Matrix("Max");
		available	= new int[MAX_RESOURCES];
		need 		= new Matrix("Need");
		
		lastCompleted = -1;
	}
	
	public void run(){
		/*do{
			//Generate new process
			//Update matrices
			//Simulate allocation
			// ??
			//Profit
		}while(NOT EXIT CONDITION);*/
		generateProcess();
	}
	
	public static void main(String[] args) {
		Bankers b = new Bankers();
		b.run();
	}

}
