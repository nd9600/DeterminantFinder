package determinant;

import java.util.ArrayList;
import java.util.Arrays;

public class DeterminantFinder {

	public static void main(String[] args) {

		Double[][] inputMatrix = { { 5.0, 1.0, 0.0, 1.0 }, { 7.0, 0.0, 3.0, 1.0 }, { 2.0, 0.0, 0.0, 0.0 }, { 1.0, 2.0,4.0, 9.0 } };

		Double determinant = getDeterminant(inputMatrix);

		System.out.println("");
		System.out.println("#############################################");

		if (determinant != 0.0) {
			System.out.println("Determinant is: " + determinant);
			System.out.println("Matrix is invertible");
		} else {
			if (determinant == 0.0) {
				System.out.println("Determinant is 0.0");
			} else {
				System.out.println("Determinant does not exist");
			}
			System.out.println("Matrix is not invertible");
		}
	}

	private static Object[] getRowOrColumnWithMostZeroes(Double[][] matrix, int sizeOfMatrix) {

		Object[] rowOrColumnWithMostZeroes = {"row", 0};
		int currentMaxNumberOfZeros = 0;
		
		// Loops through every row and column in the matrix
		for (int i = 0; i < sizeOfMatrix; i++) {
			
			// Initialises needed variables
			int numberOfZerosInRow = 0;
			int numberOfZerosInColumn = 0;
			
			// Gets the ith row
			Double[] row = matrix[i];
			
			// Gets the ith column
			Double[] column = new Double[sizeOfMatrix];
			for (int j = 0; j < sizeOfMatrix; j++) {
				column[j] = matrix[j][i];
			}
			
			//Loops through every element in row, calculates the number of zeroes in the row
			for (Double element : row){
				if (element == 0.0){
					numberOfZerosInRow = numberOfZerosInRow + 1;
				}
			}
			
			//Loops through every element in column, calculates the number of zeroes in the column
			for (Double element : column){
				if (element == 0.0){
					numberOfZerosInColumn = numberOfZerosInColumn + 1;
				}
			}
			
			//Sets the returned row or column with the most zeroes if this row currently has the most zeroes
			if (numberOfZerosInRow > currentMaxNumberOfZeros){
				rowOrColumnWithMostZeroes[0] = "row";
				rowOrColumnWithMostZeroes[1] = i;
				currentMaxNumberOfZeros = numberOfZerosInRow;
			}
			
			//Sets the returned row or column with the most zeroes if this column currently has the most zeroes
			if (numberOfZerosInColumn > currentMaxNumberOfZeros){
				rowOrColumnWithMostZeroes[0] = "column";
				rowOrColumnWithMostZeroes[1] = i;
				currentMaxNumberOfZeros = numberOfZerosInColumn;
			}
		}

		return rowOrColumnWithMostZeroes;
	}

	private static Boolean checkIfTriangular(Double[][] matrix, int sizeOfMatrix) {

		// Initialises needed booleans
		Boolean isLowerDiagonal = true;
		Boolean isUpperDiagonal = true;

		// Loops through every element in the matrix, i is row position, j is
		// column position less than i
		for (int i = 1; i < sizeOfMatrix; i++) {
			for (int j = 0; j < i; j++) {

				// lower element is a_i_j, upper element is a_j_i
				Double relevantLowerElement = matrix[i][j];
				Double relevantUpperElement = matrix[j][i];

				// If isLowerDiagonal isn't already false and the lower element
				// isn't zero, set isLowerDiagonal to false
				if (isLowerDiagonal && relevantLowerElement != 0) {
					isLowerDiagonal = false;
				}

				// If isUpperDiagonal isn't already false and the upper element
				// isn't zero, set isUpperDiagonal to false
				if (isUpperDiagonal && relevantUpperElement != 0) {
					isUpperDiagonal = false;
				}

				// If isLowerDiagonal and isUpperDiagonal are both false, return
				// false
				if (!(isLowerDiagonal || isUpperDiagonal)) {
					return false;
				}

			}
		}

		// Return true if the matrix is a lower or upper triangular matrix
		return true;
	}

	private static Double getDeterminant(Double[][] matrix) {

		System.out.println("");
		System.out.println("Input matrix is");
		for (Double[] row : matrix)
			System.out.println(Arrays.toString(row));

		int sizeOfMatrix = matrix[0].length;

		// Throws an exception if the matrix isn't square
		try {
			if (sizeOfMatrix != matrix.length) {
				throw new Exception("Matrix not square");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0.0;
		}

		System.out.println("");
		System.out.println("sizeOfMatrix is: " + sizeOfMatrix);

		// Declare determinant
		Double determinant = 0.0;

		// Calculates the determinant if the input matrix is 2x2
		if (sizeOfMatrix == 2) {
			Double a = matrix[0][0];
			Double b = matrix[0][1];
			Double c = matrix[1][0];
			Double d = matrix[1][1];
			determinant = (a * d - b * c);

			return determinant;
		}

		// Else, if not 2x2

		// If the matrix is triangular, compute the determinant
		Boolean matrixIsTriangular = checkIfTriangular(matrix, sizeOfMatrix);

		if (matrixIsTriangular) {
			// Initialise determinant for multiplication
			determinant = 1.0;
			System.out.println("Matrix is triangular");

			// Multiplies the diagonal elements together to get the determinant
			for (int i = 0; i < sizeOfMatrix; i++) {
				Double relevantElement = matrix[i][i];
				determinant = determinant * relevantElement;
			}
			return determinant;
		} else {
			System.out.println("Matrix isn't triangular");
		}

		Object[] rowOrColumnWithMostZeroes = getRowOrColumnWithMostZeroes(matrix, sizeOfMatrix);

		System.out.println("rowOrColumnWithMostZeroes: " + rowOrColumnWithMostZeroes[0] + ", " +  rowOrColumnWithMostZeroes[1]);

		// Loops through every element in the first row (for now)
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < sizeOfMatrix; j++) {

				int actualRowIndex = i + 1;
				int actualColumnIndex = j + 1;

				// Work out the sign and find the relevant element in the row
				int sign = (int) Math.pow(-1.0, actualRowIndex + actualColumnIndex);
				Double relevantElement = matrix[i][j];
				
				System.out.println("");
				System.out.println("#############################################");
				System.out.println("sign is: " + sign);
				System.out.println("relevantElement is: " + relevantElement);
				
				// If the relevant element is zero, the determinant of the resulting matrix will be zero, so this iteration can be skipped
				if (relevantElement == 0.0){
					System.out.println("relevantElement is zero, skipping this iteration");
					System.out.println("");
					continue;
				}

				// Create the new matrix to find the determinant of by removing
				// the row and column the element is in
				ArrayList<ArrayList<Double>> newMatrixArrayList = new ArrayList<ArrayList<Double>>();

				// Loops through every row
				for (int k = 0; k < sizeOfMatrix; k++) {

					ArrayList<Double> tempRowMatrixArrayList = new ArrayList<Double>();
					// Loops through every column
					for (int l = 0; l < sizeOfMatrix; l++) {

						// Checks if we aren't in the same row or column as the
						// relevant element
						if (!((k == i) || (l == j))) {
							// Adds the new element to the temporary row if not
							Double rowElement = matrix[k][l];
							tempRowMatrixArrayList.add(rowElement);
						}
					}

					// Adds the temporary row to the new smaller matrix if it
					// isn't empty
					if (!tempRowMatrixArrayList.isEmpty()) {
						newMatrixArrayList.add(tempRowMatrixArrayList);
					}

				}

				// Converts the ArrayList to an array
				Double[][] newMatrix = new Double[newMatrixArrayList.size()][];
				for (int iterator = 0; iterator < newMatrixArrayList.size(); iterator++) {
					ArrayList<Double> row = newMatrixArrayList.get(iterator);
					newMatrix[iterator] = row.toArray(new Double[row.size()]);
				}

				//System.out.println("relevantElement is: " + relevantElement);
				System.out.println("newMatrix is: ");
				for (Double[] row : newMatrix)
					System.out.println(Arrays.toString(row));
				System.out.println("");
				System.out.println("##");

				// Calculate the determinant of the new smaller matrix,
				// multiplies it by the relevant element and the sign, and adds
				// the product to the determinant
				Double newMatrixDeterminant = getDeterminant(newMatrix);
				Double actualNewMatrixDeterminant = sign * relevantElement * newMatrixDeterminant;

				//System.out.println("");
				//System.out.println("sign is: " + sign);
				//System.out.println("relevantElement is: " + relevantElement);
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