
public class Matrix {
	
	private int[][] matrix;
	private String name;
	
	public Matrix(String name){
		this.matrix = new int[Bankers.MAX_PROCESSES][Bankers.MAX_RESOURCES];
		this.name = name;
		
		initialize();
	}
	
	//This gets the next available index (first row that is null)
	public int nextAvailableIndex(){
		int index = -1;
		for(int i = 0; i < Bankers.MAX_PROCESSES; i++){
			if(matrix[i][0] == -1){ //Only check the rest of the elements if the first one is null
				index = i;
				
				for(int j = 1; j < Bankers.MAX_RESOURCES; j++){
					if(matrix[i][j] != -1){
						index = -1;
						break;
					}
				}
				
				if(index != -1)
					break;
			}
		}
		
		return index;
	}
	
	public boolean isFull(){ return (nextAvailableIndex() == -1); }
	
	//J will represent the appropriate row to be summed
	public int getRowSum(int i){
		int val = 0;
		
		for(int j=0; j<Bankers.MAX_RESOURCES; j++){
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
	
	//Adds this matrix to the other matrix, you better not give it a different sized matrix. You don't want to know what will happen (indexOutOfBoundsException...)
	public Matrix add(Matrix m){
		Matrix result = new Matrix(name + " + " + m.name);
		
		for(int i = 0; i < Bankers.MAX_PROCESSES; i++){
			int[] row = new int[Bankers.MAX_RESOURCES];
			
			for(int j = 0; j < Bankers.MAX_RESOURCES; j++){
				if(matrix[i][j] == -1 || m.matrix[i][j] == -1){ //Maintain pseudo-nullness
					row[j] = -1;
				}else{ //Do the dang thing (add)
					row[j] = matrix[i][j] + m.matrix[i][j];
				}
			}
			
			result.setRow(i, row);
		}
		
		return result;
	}
	
	//Subtracts the matrix m from this matrix. this - m
	public Matrix sub(Matrix m){
		Matrix result = new Matrix(name + " - " + m.name);
		
		for(int i = 0; i < Bankers.MAX_PROCESSES; i++){
			int[] row = new int[Bankers.MAX_RESOURCES];
			
			for(int j = 0; j < Bankers.MAX_RESOURCES; j++){
				if(matrix[i][j] == -1 || m.matrix[i][j] == -1){ //Maintain pseudo-nullness
					row[j] = -1;
				}else{ //subtract
					row[j] = matrix[i][j] - m.matrix[i][j];
				}
			}
			
			result.setRow(i, row);
		}
		
		return result;
	}
	
	//Checks this.row[index] <= m.row[index]
	public boolean rowIsLessThanOrEqualTo(int index, int[] row){
		if(index < 0 || index >= Bankers.MAX_PROCESSES) return false; //Sanity check
		
		boolean result = true;
		
		for(int i = 0; i < Bankers.MAX_RESOURCES; i++){
			result = matrix[index][i] <= row[i];
			if(!result) break; //Break when false incase following values result in true
		}
		
		return result;
		
	}
	
	//this.row[index] + m.row[mindex]
	public Matrix addRows(int index, int[] row){
		Matrix result = this.copy();
		
		int[] thisRow = getRow(index);
		int[] sumRow = new int[thisRow.length];
		
		for(int i = 0; i < thisRow.length; i++){
			sumRow[i] = thisRow[i] + row[i];
		}
		
		result.setRow(index, sumRow);
		return result;
	}
	
	public void deleteRow(int index){
		for(int i = 0; i < Bankers.MAX_RESOURCES; i++){
			matrix[index][i] = -1;
		}
	}
	
	//Copy fxn
	public Matrix copy(){
		int[][] temp = new int[Bankers.MAX_RESOURCES][Bankers.MAX_PROCESSES];
		for(int i=0; i < Bankers.MAX_PROCESSES; i++){
			for(int j=0;j<Bankers.MAX_RESOURCES; j++){
				temp[i][j] = this.matrix[i][j];
			}
		}
		Matrix cpy = new Matrix(name + " - Copy");
		cpy.matrix = temp;
		return cpy;
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
