// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		// imageOut = flippedVertically(tinypic);
		// System.out.println();
		// print(imageOut);
		System.out.println();
		// Tests scaledImage 
		imageOut = scaled(tinypic, 3, 5);
		System.out.println();
		print(imageOut);

		Color a = new Color(100, 40, 100);
		Color b = new Color(200, 20, 40);

		System.out.println(blend(a, b, 0.25));
		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				int red = in.readInt();
				int green = in.readInt();
				int blue = in.readInt();
				image[i][j] = new Color(red,green,blue);
			}
		}
		
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				print(image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {		
		Color [][] flippedHorizonal = new Color[image.length][image[0].length];
		for(int i = 0; i < flippedHorizonal.length; i++) {
			for(int j = 0; j < flippedHorizonal[0].length; j++) {
				flippedHorizonal[i][j] = image[i][flippedHorizonal[0].length - 1 - j];
			}
		}

		return flippedHorizonal;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color [][] flippedVertical = new Color[image.length][image[0].length];
		int ColsLength = flippedVertical.length - 1;
		for(int i = 0; i < flippedVertical.length; i++ ) {
			for(int j = 0; j < flippedVertical[0].length; j++) {
				flippedVertical[i][j] = image[ColsLength - i][j];
			}
		}

		return flippedVertical;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		int green = pixel.getGreen();
		int blue = pixel.getBlue();
		int red = pixel.getRed();
		int lum = (int)((0.299 * red) + (0.587 * green) + (0.114 * blue));
		Color grey = new Color(lum, lum, lum);
		return grey;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color [][] greyImage = new Color[image.length][image[0].length];
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				greyImage[i][j] = luminance(image[i][j]);
			}
		}
		return greyImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color [][] scaledImage = new Color[height][width];
		double h0 = image.length; 
		double w0 = image[0].length;
		double h = height;
		double w = width;
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				scaledImage[i][j] = image[(int)(i * (h0/h))][(int)(j * (w0/w))];
			}
		}
		return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int greenC1 = c1.getGreen();
		int blueC1 = c1.getBlue();
		int redC1 = c1.getRed();
		int greenC2 = c2.getGreen();
		int blueC2 = c2.getBlue();
		int redC2 = c2.getRed();

		int greenBlend = (int)((alpha * greenC1) + ((1 - alpha) * greenC2));
		int blueBlend = (int)((alpha * blueC1) + ((1 - alpha) * blueC2));
		int redBlend = (int)((alpha * redC1) + ((1 - alpha) * redC2));

		Color blend = new Color(redBlend, greenBlend, blueBlend);
		return blend;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color [][] blendImage = new Color[image1.length][image1[0].length];
		for(int i = 0; i < image1.length; i++) {
			for(int j = 0; j < image1[0].length; j++) {
				blendImage[i][j] = blend(image1[i][j],image2[i][j],alpha);
			}
		}
		return blendImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		Color [][] morph = new Color[source.length][source[0].length];
		if(source.length != target.length || source[0].length != target[0].length) {
			target = scaled(target, source[0].length, source.length);
		} 
		for(int i = 0; i <= n; i++) {
			double alpha =(double)(n - i)/n;
			morph = blend(source, target,alpha);
			Runigram.display(morph);
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

