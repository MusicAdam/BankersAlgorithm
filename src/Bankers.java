import java.util.ArrayList;
import java.util.Random;


public class Bankers {
	public static final int MAX_RESOURCES = 3;	//Max number of resources in the system
	public static final int MAX_PROCESSES = 5;	//Max number of processes in the system
	
	Matrix allocation;			//This matrix defines what resources are currently allocated in the system
	Matrix max;					//The total number of resouces the processes need to complete. 
	int[] available;			//The number of resouces available after each process completes Previous row + allocation[process]
	Matrix need;				//The current number of resources each process needs to complete. Max - allocation
	
	boolean[] processes;		//All the processes currently in the system.
	
	int lastCompleted;			//The index of the last completed process in the avaiable matrix.
	
	public int[] subArrays(int[] arr1, int[] arr2){
		int[] result = new int[arr1.length];
		
		for(int i = 0; i < arr1.length; i++){
			result[i] = arr1[i] - arr2[i];
		}
		
		return result;
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
	
	public boolean addProcessArray(Process[] array, Process p){
		for(int i = 0; i < array.length; i++){
			if(array[i] == null){
				array[i] = p;
				return true;
			}
		}
		
		return false;
	}
	
	//Steps through all the matrices returning the process that was completed
	public int stepMatrices() {
		int completed = -1;
		//Iterate through each process and complete the first one that can be completed
		for(int index = 0; index < MAX_PROCESSES; index++){
			if(processes[index] == false){
				if(need.rowIsLessThanOrEqualTo(index, available)){
					available = allocation.addRows(index, available).getRow(index);
					//printAvailable();
					allocation.deleteRow(index);
					max.deleteRow(index);
					need.deleteRow(index);
					processes[index] = true;
					completed = index;
					break;
				}
			}
		}

		return completed;
	}
	
	public void printAvailable(){
		for(int i: this.available){
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
	public Bankers(){
		//Initialize variables
		processes 	= new boolean[MAX_PROCESSES];
		allocation 	= new Matrix("Allocation");
		max 		= new Matrix("Max");
		available	= new int[MAX_RESOURCES];
		need 		= new Matrix("Need");
		
		allocation.setRow(0,  new int[]{0, 1, 0});
		allocation.setRow(1,  new int[]{2, 0, 0});
		allocation.setRow(2,  new int[]{3, 0, 2});
		allocation.setRow(3,  new int[]{2, 1, 1});
		allocation.setRow(4,  new int[]{0, 0, 2});
		
		max.setRow(0, new int[]{7, 5, 3});
		max.setRow(1,  new int[]{3, 2, 2});
		max.setRow(2,  new int[]{9, 0, 2});
		max.setRow(3,  new int[]{2, 2, 2});
		max.setRow(4,  new int[]{4, 3, 3});
		
		need = max.sub(allocation);
		need.name = "Need";
		
		available = new int[]{3, 3, 2};
		
		System.out.println(allocation);
		System.out.println(max);
		System.out.println(need);
		
	}
	
	public void run(){
		System.out.println("---------------------------");
		

		System.out.print("Safe sequence: ");
		
		for(int i=0; i<MAX_PROCESSES; i++){
			int completed = -1;
			//p = generateProcess();
			//if(addProcessArray(processes,p)){
			//	System.out.println("Process Added "+p.getId());
			//}
			
			//System.out.println("--------- START STEP -----------");
			
			if((completed = stepMatrices()) != -1){
				processes[completed] = true;
				System.out.print(completed + " ");
			}
			//System.out.println("--------- END STEP -----------");

		
		}
	}
	
	public static void main(String[] args) {
		Bankers b = new Bankers();
		b.run();
		
	}

}
