
public class Matrix {
	
	private int[][] matrix;
	
	public Matrix(){
		this.matrix = new int[Bankers.MAX_PROCESSES][Bankers.MAX_RESOURCES];
		
		initialize();
	}
	
	public Matrix(int x, int y){
		this.matrix = new int[x][y];
		
		initialize();
	}
	
	//J will represent the appropriate row to be summed
	public int getRowSum(int j){
		int val = 0;
		
		for(int i=0; i<Bankers.MAX_RESOURCES; i++){
				val+=matrix[i][j];
		}
		return val;
	}
	
	public int[][] getMatrix(){ return this.matrix;}
	
	public int[] getRow(int index){
		int[] row = new int[Bankers.MAX_PROCESSES];
		for(int i=0; i< Bankers.MAX_PROCESSES; i++){
			row[i] = matrix[i][index];
		}
		return row;
	}
	
	public int[] getCol(int index){
		int col[] = new int[Bankers.MAX_RESOURCES];
		
		for(int i=0; i<Bankers.MAX_RESOURCES; i++){
			col[i] = matrix[index][i];
		}
		return col;
	}
	
	//Copy fxn
	public int[][] Copy(){
		int[][] temp = new int[Bankers.MAX_RESOURCES][Bankers.MAX_PROCESSES];
		for(int i=0; i < Bankers.MAX_PROCESSES; i++){
			for(int j=0;j<Bankers.MAX_RESOURCES; j++){
				temp[j][i] = this.matrix[j][i];
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
		String str = "";
		for(int i=0; i < Bankers.MAX_PROCESSES; i++){
			for(int j = 0; j < Bankers.MAX_RESOURCES; j++){
				if(matrix[i][j] == -1){
					str += "null ";
				}else{
					str += matrix[i][j] + " ";
				}
			}
			
			str += "\n";
		}
		
		return str;
	}
}
