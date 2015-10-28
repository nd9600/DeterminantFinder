package determinant;

public class DeterminantFinder {
	
	public static void main(String[] args){
		Double[][] inputMatrix = {{1.0 , 2.0}, {3.0 , 4.0 }};
		
		Double determinant = getDeterminant(inputMatrix);
		
		System.out.println("");
		System.out.println("Determinant is: " + determinant);
		
		if (determinant != 0.0){
			System.out.println("Matrix is invertible");
		} else {
			System.out.println("Matrix is not invertible");
		}
		
	}
	
	private static Double getDeterminant(Double[][] matrix){
		
		int sizeOfMatrix = matrix[0].length;
		
		//Throws an exception if the matrix isn't square
		try {
			if (sizeOfMatrix != matrix.length){
				throw new Exception("Matrix not square");
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
			return 0.0;
		}
		
		System.out.println("sizeOfMatrix is: " + sizeOfMatrix);
		
		//Calculates the determinant if the input matrix is 2x2
		if (sizeOfMatrix == 2){
			Double a = matrix[0][0];
			Double b = matrix[0][1];
			Double c = matrix[1][0];
			Double d = matrix[1][1];
			Double determinant = (a*d - b*c);
			
			return determinant;
		}
		
		//Else, if not 2x2
		
		Double tempDeterminant = 0.0;
		
		//Loops through every element in the first row
		for (int i=0; i<1; i++){
			for (int j=0; j<sizeOfMatrix; j++){
				System.out.println("tempDeterminant is: " + tempDeterminant);
				
				int actualRowIndex = i + 1;
				int actualColumnIndex = j + 1;
				
				//Work out the sign and find the relevant element in the row
				int sign = (int) Math.pow(-1.0, actualRowIndex+actualColumnIndex);
				Double relevantElement = matrix[i][j];
				
				//Create the new matrix to find the determinant of by removing the row and column the element is in
				Double[][] newMatrix = matrix;
				
				//Calculate the determinant of the new smaller matrix, multiply it by the relevant elemant and the sign, and add the product to the temporary 
				Double newMatrixDeterminant =  getDeterminant(newMatrix);
				Double actualNewMatrixDeterminant = sign * relevantElement * newMatrixDeterminant;
				tempDeterminant = tempDeterminant + actualNewMatrixDeterminant;
				
				System.out.println("");
				System.out.println("###############");
				System.out.println("sign is: " + sign);
				System.out.println("relevantElement is: " + relevantElement);
				System.out.println("newMatrix is: " + newMatrix);
				System.out.println("newMatrixDeterminant is: " + newMatrixDeterminant);
				System.out.println("actualNewMatrixDeterminant is: " + actualNewMatrixDeterminant);
				System.out.println("tempDeterminant is: " + tempDeterminant);
			}
		}
		
		return tempDeterminant;
	}
}