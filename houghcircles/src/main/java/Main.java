import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import static org.opencv.core.Core.circle;
import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imwrite;
import static org.opencv.imgproc.Imgproc.*;

public class Main {
    private static void help()
    {
        System.out.println("\nThis program demonstrates circle finding with the Hough transform.\n" +
                "Usage:\n" +
                "./houghcircles <image_name>, Default is pic1.png\n");
    }


    public static void main(String[] args) {

        System.loadLibrary("opencv_java248");

        String filename;
        if (args.length > 0) {
            filename = args[0];
        }
        else {
            filename = "board.jpg";
        }

        Mat img = imread(filename, 0);

        if (img.empty())
        {
            help();
            System.out.println("can not open " + filename);
            return; //-1;
        }

        Mat cimg = new Mat();
        medianBlur(img, cimg, 5);
        //cvtColor(img, cimg, COLOR_BGR2GRAY);

        Point pt;
        Mat circles = new Mat();
        System.out.println(cimg.channels() + "channels, " + cimg.dims());
        HoughCircles(img, circles, CV_HOUGH_GRADIENT, 1,10,100,30,10,100);

        for (int i=0; i< circles.cols(); i++) {
            double[] c = circles.get(0, i);

            pt = new Point(Math.round(c[0]), Math.round(c[1]));
            circle( cimg, pt, (int) Math.round(c[2]), new Scalar(0,0,255), 1);
            circle( cimg, pt, 2, new Scalar(0,255,0), 1);
        }

        imwrite("out.jpg", cimg);

        //return 0;
    }


}