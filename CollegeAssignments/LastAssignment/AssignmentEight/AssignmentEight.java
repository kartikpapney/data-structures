package CollegeAssignments.LastAssignment.AssignmentEight;
// Please remove the package name while executing the code in some other package


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


/***
 * DAA Assignment
 * Name - Kartik Papney
 * Roll No - CSB19047
 *
 *
 * Time complexity analysis
 * n = number of rows of input matrix
 * m = number of columns of output matrix
 * k = range in which we have to find median of a cell
 *
 * Time complexity = O(n*m*k*k*log(k))
 * Space complexity = O(n*m+k*k)
 *
 *
 * Result = If we increase the size of median matrix the image becomes more blur
 *
 * Just run the code input output is managed in program itself
 * please update the input path and the output path;
 */


class MatrixMedianFinder {

    int medianRange;
    int[][] matrix;
    int[][] result;

    MatrixMedianFinder(int[][] arr, int medianRange) {
        this.medianRange = medianRange;
        this.matrix = arr;
        computeMedianMatrix();
    }

    private int[] sort(int[] arr, int i, int j) {
        if(i + 1 == j) {
            int[] baseCase = new int[1];
            baseCase[0] = arr[i];
            return baseCase;
        }
        int mid = i + (j-i)/2;
        int[] a = sort(arr, i, mid);
        int[] b = sort(arr, mid, j);
        int[] ans = new int[a.length + b.length];
        int ptra = 0;
        int ptrb = 0;
        while(ptra < a.length && ptrb < b.length) {
            if(a[ptra] <= b[ptrb]) ans[ptra+ptrb] = a[ptra++];
            else ans[ptra+ptrb] = b[ptrb++];
        }
        while(ptra < a.length) ans[ptra+ptrb] = a[ptra++];
        while(ptrb < b.length) ans[ptra+ptrb] = b[ptrb++];
        return ans;
    }

    private int findMedian(int row, int col) {
        int a = Math.min(row + this.medianRange/2, this.matrix.length - 1) - Math.max(0, row - this.medianRange/2) + 1;
        int b = Math.min(col + this.medianRange/2, this.matrix[0].length - 1) - Math.max(0, col - this.medianRange/2) + 1;
        int[] arr = new int[a*b];
        int idx = 0;
        int startRow = row - medianRange / 2;
        int startCol = col - medianRange / 2;
        for (int i = Math.max(startRow, 0); i < Math.min(startRow + medianRange, this.matrix.length); i++) {
            for (int j = Math.max(startCol, 0); j < Math.min(startCol + medianRange, this.matrix[0].length); j++) {
                arr[idx++] = this.matrix[i][j];
            }
        }
        arr = sort(arr, 0, arr.length);
        return arr.length%2 != 0?arr[(arr.length) / 2]:(arr[(arr.length)/2 - 1] + arr[(arr.length) / 2])/2;
    }

    private void computeMedianMatrix() {
        this.result = new int[this.matrix.length][this.matrix[0].length];
        for (int i = 0; i < this.result.length; i++) {
            for (int j = 0; j < this.result[0].length; j++) {
                this.result[i][j] = findMedian(i, j);
            }
        }
    }
}

public class AssignmentEight {

    public static void main(String[] args) {
        // n will denote the number of rows and column in the matrix

        System.out.println("Question-a");
        Random r = new Random();
//        Question 8(a)
        int[][] arr = new int[5][5];
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[i].length; j++) {
                arr[i][j] = r.nextInt(10);
            }
        }
        MatrixMedianFinder m = new MatrixMedianFinder(arr, 3);
        System.out.println("Randomly generated matrix");
        System.out.println("---------------------");
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("3X3 Median Matrix");
        System.out.println("---------------------");
        int[][] medianMatrix = m.result;
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[i].length; j++) {
                System.out.print(medianMatrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Question-b");
        System.out.println("---------------------");
//        Question 8(B)
        String inputPath = "D:\\Github\\data-structures\\src\\CollegeAssignments\\LastAssignment\\img.png";
        String outputPath = "D:\\Github\\data-structures\\src\\CollegeAssignments\\LastAssignment";


        int[] medianRange = new int[]{3, 5, 7, 9};
        int width = 960;
        int height = 540;

        for(int v : medianRange) {
            BufferedImage image = null;
            try
            {
                File input_file = new File(inputPath); //image file path

                image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_ARGB);

                image = ImageIO.read(input_file);
                arr = new int[image.getWidth()][image.getHeight()];
                for(int i=0; i<arr.length; i++) {
                    for(int j=0; j<arr[0].length; j++) {
                        arr[i][j] = image.getRGB(i, j);
                    }
                }
                MatrixMedianFinder solver = new MatrixMedianFinder(arr, v);
                for(int i=0; i<arr.length; i++) {
                    for(int j=0; j<arr[0].length; j++) {
                        image.setRGB(i, j, solver.result[i][j]);
                    }
                }
                System.out.println("Read operation performed");
            }
            catch(IOException e)
            {
                System.out.println("Error: "+e);
            }

            try
            {
                File output_file = new File(outputPath + "\\median" + v + "x" + v + ".jpg");
                ImageIO.write(image, "jpg", output_file);
                System.out.println(output_file.getName() + " is written");
            }

            catch(IOException e)
            {
                System.out.println("Error: " + e);
            }
        }
    }
}