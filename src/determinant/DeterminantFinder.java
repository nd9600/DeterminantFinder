package determinant;

import java.util.Arrays;

public class DeterminantFinder {

	public static void main(String[] args) {

		Double[][] inputMatrix = { { 5.0, 1.0, 0.0, 1.0 }, { 7.0, 0.0, 3.0, 1.0 }, { 2.0, 0.0, 0.0, 0.0 },
				{ 1.0, 2.0, 4.0, 9.0 } };

		Double determinant = getDeterminant(inputMatrix);

		System.out.println("");
		System.out.println("#############################################");

		if (determinant != 0.0) {
			System.out.println("Determinant is: " + determinant);
			System.out.println("Matrix is invertible");
			System.out.println("Rows and columns are both linearly independent");
			System.out.println("Nullspace = {0}");
		} else {
			if (determinant == 0.0) {
				System.out.println("Determinant is 0.0");
			} else {
				System.out.println("Determinant does not exist");
			}
			System.out.println("Matrix is not invertible");
			System.out.println("Columns are linearly dependent");
		}
	}

	private static Object[] getRowOrColumnWithMostZeroes(Double[][] matrix, int sizeOfMatrix) {

		Object[] rowOrColumnWithMostZeroes = { "row", 0, false };
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

			// Loops through every element in row, calculates the number of
			// zeroes in the row
			for (Double element : row) {
				if (element == 0.0) {
					numberOfZerosInRow = numberOfZerosInRow + 1;
				}
			}

			// Loops through every element in column, calculates the number of
			// zeroes in the column
			for (Double element : column) {
				if (element == 0.0) {
					numberOfZerosInColumn = numberOfZerosInColumn + 1;
				}
			}

			// Sets the returned row or column with the most zeroes if this row
			// currently has the most zeroes
			if (numberOfZerosInRow > currentMaxNumberOfZeros) {
				rowOrColumnWithMostZeroes[0] = "row";
				rowOrColumnWithMostZeroes[1] = i;
				currentMaxNumberOfZeros = numberOfZerosInRow;
			}

			// Sets the returned row or column with the most zeroes if this
			// column currently has the most zeroes
			if (numberOfZerosInColumn > currentMaxNumberOfZeros) {
				rowOrColumnWithMostZeroes[0] = "column";
				rowOrColumnWithMostZeroes[1] = i;
				currentMaxNumberOfZeros = numberOfZerosInColumn;
			}

			// If the maximum number of zeros has been reached, return
			if (currentMaxNumberOfZeros == sizeOfMatrix) {
				rowOrColumnWithMostZeroes[2] = true;
				return rowOrColumnWithMostZeroes;
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

	private static Double actuallyGetDeterminant(Double[][] matrix, int sizeOfMatrix, int[] iList, int[] jList,
			Double determinant) {
		// Loops through every element in the wanted rows or columns
		for (int i : iList) {
			for (int j : jList) {

				int actualRowIndex = i + 1;
				int actualColumnIndex = j + 1;

				// Work out the sign and find the relevant element in the row
				int sign = (int) Math.pow(-1.0, actualRowIndex + actualColumnIndex);
				Double relevantElement = matrix[i][j];

				System.out.println("");
				System.out.println("#############################################");
				System.out.println("i, j: " + i + ", " + j);
				System.out.println("sign is: " + sign);
				System.out.println("relevantElement is: " + relevantElement);

				// If the relevant element is zero, the determinant of the
				// resulting matrix will be zero, so this iteration can be
				// skipped
				if (relevantElement == 0.0) {
					System.out.println("relevantElement is zero, skipping this iteration");
					continue;
				}

				// Create the new matrix to find the determinant of by removing
				// the row and column the element is in
				Double[][] newMatrix = new Double[sizeOfMatrix - 1][];

				// Loops through every row
				for (int k = 0; k < sizeOfMatrix; k++) {

					Double[] tempRowMatrix = new Double[sizeOfMatrix - 1];
					// Loops through every column
					for (int l = 0; l < sizeOfMatrix; l++) {

						// Checks if we aren't in the same row or column as the
						// relevant element
						if (!((k == i) || (l == j))) {
							// Adds the new element to the temporary row if not
							Double rowElement = matrix[k][l];

							// Adds the element in the lth position if the
							// current column is left of the deleted column,
							// (l-1)th position if to the right
							if (l < j) {
								tempRowMatrix[l] = rowElement;
							} else if (l > j) {
								tempRowMatrix[l - 1] = rowElement;
							}
						}
					}

					// Adds the temporary row to the new smaller matrix in the
					// kth position if the current row is above the deleted row,
					// (k-1)th position if below
					if (k < i) {
						newMatrix[k] = tempRowMatrix;
					} else if (k > i) {
						newMatrix[(k - 1)] = tempRowMatrix;
					}

				}

				System.out.println("");
				System.out.println("##");
				System.out.println("");

				// Calculate the determinant of the new smaller matrix,
				// multiplies it by the relevant element and the sign, and adds
				// the product to the determinant
				Double newMatrixDeterminant = getDeterminant(newMatrix);

				if (newMatrixDeterminant != 0.0) {
					Double actualNewMatrixDeterminant = sign * relevantElement * newMatrixDeterminant;

					System.out.println("");
					System.out.println("#");
					System.out.println("newMatrixDeterminant is: " + newMatrixDeterminant);
					System.out.println("actualNewMatrixDeterminant is: " + actualNewMatrixDeterminant);
					System.out.println("previousDeterminant is: " + determinant);

					determinant = determinant + actualNewMatrixDeterminant;

					System.out.println("new determinant is: " + determinant);
				}

			}
		}
		return determinant;
	}

	private static Double getDeterminant(Double[][] matrix) {

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

		// Calculates the max number of zeros in any row or column
		Object[] rowOrColumnWithMostZeroes = getRowOrColumnWithMostZeroes(matrix, sizeOfMatrix);
		String rowOrColumnWithMax = (String) rowOrColumnWithMostZeroes[0];
		int rowOrColumnNumber = (int) rowOrColumnWithMostZeroes[1];
		Boolean rowOrColumnAllZeros = (Boolean) rowOrColumnWithMostZeroes[2];

		System.out.println("Getting relevantElements from " + rowOrColumnWithMax + " " + (rowOrColumnNumber + 1));
		System.out.println("rowOrColumnAllZeros: " + rowOrColumnAllZeros);
		// If a row or column has all elements equal to zero, the determinant is
		// zero, so there is no point calculating it
		if (rowOrColumnAllZeros) {
			System.out.println("rowOrColumnAllZeros, returning zero");
			return 0.0;
		}

		// Declare determinant
		Double determinant = 0.0;

		// Calculates the determinant if the input matrix is 2x2
		if (sizeOfMatrix == 2) {
			Double a = matrix[0][0];
			Double b = matrix[0][1];
			Double c = matrix[1][0];
			Double d = matrix[1][1];
			determinant = (a * d - b * c);
			System.out.println("determinant is: " + determinant);

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

		// Chooses which row or column to get the relevantElements from and
		// which rows to columns to loop through, and calculates the determinant
		if (rowOrColumnWithMax.equals("row")) {
			int[] iList = new int[1];
			int[] jList = new int[sizeOfMatrix];

			iList[0] = rowOrColumnNumber;
			for (int k = 0; k < sizeOfMatrix; k++) {
				jList[k] = k;
			}
			determinant = actuallyGetDeterminant(matrix, sizeOfMatrix, iList, jList, determinant);

		} else if (rowOrColumnWithMax.equals("column")) {
			int[] iList = new int[sizeOfMatrix];
			int[] jList = new int[1];

			for (int k = 0; k < sizeOfMatrix; k++) {
				iList[k] = k;
			}
			jList[0] = rowOrColumnNumber;
			determinant = actuallyGetDeterminant(matrix, sizeOfMatrix, iList, jList, determinant);
		}

		return determinant;
	}
}