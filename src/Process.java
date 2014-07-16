
//This represents a process that enters the system
public class Process {
	private final int[] max; 	//The number of resource instances required for the process to complete.
	private final int id;		//This id corresponds to the index in the allocation, max, and need matrices.
	
	public Process(int id, int[] max){
		this.id = id;
		this.max = max;
	}
	 
	 public int getId(){ return id; }
	 public int[] getMax(){ return max; }

}
