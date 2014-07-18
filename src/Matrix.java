
public class Matrix {
	
	private int[][] matrix;
	private String name;
	
	public Matrix(String name){
		this.matrix = new int[Bankers.MAX_PROCESSES][Bankers.MAX_RESOURCES];
		this.name = name;
		
		initialize();
	}
	
	//J will represent the appropriate row to be summed
	public int getRowSum(int j){
		int val = 0;
		
		for(int i=0; i<Bankers.MAX_RESOURCES; i++){
			if(matrix[i][j] != -1)
				val+=matrix[i][j];
		}
		return val;
	}
	
	public int[][] getMatrix(){ return this.matrix;}
	public String getName(){ return this.name;}
	
	//Sets the values of a row by a given index to the values passed in the int array
	public void setRow(int index, int[] values){
		if(index < 0 || index >= Bankers.MAX_PROCESSES) return; //Sanity check
		
		for(int i = 0; i < Bankers.MAX_RESOURCES; i++){
			if(i >= values.length) break; //Sanity check on values index
			matrix[index][i] = values[i];
		}
	}
	
	public int[] getRow(int index){
		int[] row = new int[Bankers.MAX_RESOURCES];
		for(int i=0; i< Bankers.MAX_RESOURCES; i++){
			row[i] = matrix[index][i]; 
		}
		return row;
	}
	
	public int[] getCol(int index){
		int col[] = new int[Bankers.MAX_PROCESSES];
		
		for(int i=0; i<Bankers.MAX_PROCESSES; i++){
			col[i] = matrix[i][index];
		}
		return col;
	}
	
	//Copy fxn
	public int[][] Copy(){
		int[][] temp = new int[Bankers.MAX_RESOURCES][Bankers.MAX_PROCESSES];
		for(int i=0; i < Bankers.MAX_PROCESSES; i++){
			for(int j=0;j<Bankers.MAX_RESOURCES; j++){
				temp[i][j] = this.matrix[i][j];
			}
		}
		return temp;
	}
	
	//Initialize everything to our null valid (-1)
	private void initialize(){
		for(int i=0; i < Bankers.MAX_PROCESSES; i++){
			for(int j=0;j<Bankers.MAX_RESOURCES; j++){
				matrix[i][j] = -1;
			}
		}
	}
	
	@Override
	public String toString(){
		String str = name + ":\n    ";
		for(int x = 0; x < Bankers.MAX_RESOURCES; x++){
			str += "R" + x + " ";
		}
		
		str += "\n";
		
		for(int i=0; i < Bankers.MAX_PROCESSES; i++){
			str += "P" + i + ": ";
			for(int j = 0; j < Bankers.MAX_RESOURCES; j++){
				if(matrix[i][j] < 10 && matrix[i][j] > -1)
					str += " "; //Add this space to make it look correct for single digits (three digits will look bad still)
				
				str += matrix[i][j] + " ";
			}
			
			str += "\n";
		}
		
		return str;
	}
}
