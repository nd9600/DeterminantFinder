package determinant;

import java.util.ArrayList;
import java.util.Arrays;

public class DeterminantFinder {
	
	public static void main(String[] args){
		Double[][] inputMatrix = {{1.0 , 2.0, 3.0}, {4.0 , 1.0, 0.0}, {7.0 , 3.0, 9.0}};
		
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
		
		System.out.println("");
		System.out.println("Input matrix is");
		for (Double[] row : matrix)
		    System.out.println(Arrays.toString(row));
		
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
		
		Double determinant = 0.0;
		
		//Loops through every element in the first row (for now)
		for (int i=0; i<1; i++){
			for (int j=0; j<sizeOfMatrix; j++){
				
				int actualRowIndex = i + 1;
				int actualColumnIndex = j + 1;
				
				//Work out the sign and find the relevant element in the row
				int sign = (int) Math.pow(-1.0, actualRowIndex+actualColumnIndex);
				Double relevantElement = matrix[i][j];
				
				System.out.println("");
				System.out.println("#############################################");
				
				//Create the new matrix to find the determinant of by removing the row and column the element is in
				ArrayList<ArrayList<Double>> newMatrixArrayList = new ArrayList<ArrayList<Double>>();
				
				//Loops through every row
				for (int k=0; k<sizeOfMatrix; k++){
					
					ArrayList<Double> tempRowMatrixArrayList = new ArrayList<Double>();
					//Loops through every column
					for (int l=0; l<sizeOfMatrix; l++){
						
						//Checks if we aren't in the same row or column as the relevant element
						if (! ((k == i) || (l == j)) ){	
							//Adds the new element to the temporary row if not
							Double rowElement = matrix[k][l];
							tempRowMatrixArrayList.add(rowElement);
						}
					}
					
					//Adds the temporary row to the new smaller matrix if it isn't empty
					if (! tempRowMatrixArrayList.isEmpty()){
						newMatrixArrayList.add(tempRowMatrixArrayList);
					}					
					
				}
				
				//Converts the ArrayList to an array
				Double[][] newMatrix = new Double[newMatrixArrayList.size()][];
				for (int iterator = 0; iterator < newMatrixArrayList.size(); iterator++) {
				    ArrayList<Double> row = newMatrixArrayList.get(iterator);
				    newMatrix[iterator] = row.toArray(new Double[row.size()]);
				}
				
				System.out.println("newMatrix is: ");
				for (Double[] row : newMatrix)
				    System.out.println(Arrays.toString(row));
				System.out.println("");
				System.out.println("##");
								
				//Calculate the determinant of the new smaller matrix, multiplies it by the relevant element and the sign, and adds the product to the determinant
				Double newMatrixDeterminant =  getDeterminant(newMatrix);
				Double actualNewMatrixDeterminant = sign * relevantElement * newMatrixDeterminant;
				
				System.out.println("");
				System.out.println("sign is: " + sign);
				System.out.println("relevantElement is: " + relevantElement);
				System.out.println("newMatrixDeterminant is: " + newMatrixDeterminant);
				System.out.println("actualNewMatrixDeterminant is: " + actualNewMatrixDeterminant);
				System.out.println("previousDeterminant is: " + determinant);
				
				determinant = determinant + actualNewMatrixDeterminant;
				
				System.out.println("new determinant is: " + determinant);
			}
		}

		return determinant;
	}
}