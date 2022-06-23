package jp.jaxa.iss.kibo.rpc.sampleapk;

import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import android.util.Log;
import org.opencv.core.Mat;


// opencv library
import org.opencv.aruco.Aruco;
import org.opencv.aruco.Dictionary;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Rect;
import static org.opencv.android.Utils.matToBitmap;


// java library
import java.util.ArrayList;
import java.util.List;



/**
 * Class meant to handle commands from the Ground Data System and execute them in Astrobee
 */
public class YourService extends KiboRpcService {

    private final String TAG = this.getClass().getSimpleName();


    @Override
    protected void runPlan1(){

        Log.i(TAG,"start mission");
        // the mission starts

        api.startMission();



        // move to a point
        Point point = new Point(10.71000, -7.76000, 4.4200);
        Quaternion quaternion = new Quaternion(0,0.707f, 0, 0.707f);
          api.moveTo(point, quaternion, true);

        // report point1 arrival
        api.reportPoint1Arrival();

        //opencv code
        try {
            Mat source = undistord(api.getMatNavCam());
        }
        catch (Exception ex )
        {

            Log.i(TAG,"podda setu");
        }
        finally {


            Log.i(TAG,"shopno puron hoyeche");
        }



       // Mat src =  api.getMatNavCam();
     //   Mat dst = new Mat();
        //getNavCamIntrinsics();
      // Mat cameraMatrix = Mat(3, 3, api.getNavCamIntrinsics());
       // Mat cameraMatrix = new Mat(3,3,api.getNavCamIntrinsics());

        //Mat distCoeffs = ;
        //Imgproc.undistort(src, dst, cameraMatrix, distCoeffs);

        // get a camera image
        //Mat image = api.getMatNavCam();
        // irradiate the laser
        api.laserControl(true);


        // take target1 snapshots
        api.takeTarget1Snapshot();

        // turn the laser off
        api.laserControl(false);

        //2nd cube
        point = new Point(10.562f, -7.70000f, 5.3528f);
        quaternion = new Quaternion(0,0.707f, 0, 0.707f);

        api.moveTo(point, quaternion, true);

        //3rd cube
        point = new Point(10.547f, -9.9452f, 5.2999f);
        api.moveTo(point, quaternion, true);


        //4th cube 2nd point
        point = new Point (11.2131f, -9.92284, 5.46681);

        quaternion = new Quaternion(0, 0, -0.707f, 0.707f);

        Result result1;


        int count = 0, max_count = 3;
        //check result and loop while moveto api is not succed.

        do
        {
            result1 = api.moveTo(point, quaternion, true);
            count++;
        }
        while (!result1.hasSucceeded() && count < max_count);


        api.laserControl(true);




        api.takeTarget2Snapshot();
        api.laserControl(false);


        //5th cube
        point = new Point(11.345f, -9.9356f, 4.48f);
        api.moveTo(point, quaternion, true);

        //6hth cube
        point = new Point(11.286f, -7.8768f, 4.48f);
        api.moveTo(point, quaternion, true);


        //goal position
        point = new Point(11.27460, -7.89178, 4.96538);
        api.moveTo(point, quaternion, true);






        api.reportMissionCompletion();
    }

    @Override
    protected void runPlan2(){


    }

    @Override
    protected void runPlan3(){
        // write here your plan 3
    }

    public Mat undistord(Mat src)
    {
        Mat dst = new Mat(1280, 960, CvType.CV_8UC1);
        Mat cameraMatrix = new Mat(3, 3, CvType.CV_32FC1);
        Mat distCoeffs = new Mat(1, 5, CvType.CV_32FC1);

        int row = 0, col = 0;

        double cameraMatrix_sim[] =
                {
                        344.173397, 0.000000, 630.793795,
                        0.000000, 344.277922, 487.033834,
                        0.000000, 0.000000, 1.000000
                };
        double distCoeffs_sim[] = {-0.152963, 0.017530, -0.001107, -0.000210, 0.000000};

        double cameraMatrix_orbit[] =
                {
                        692.827528, 0.000000, 571.399891,
                        0.000000, 691.919547, 504.956891,
                        0.000000, 0.000000, 1.000000
                };
        double distCoeffs_orbit[] = {-0.312191, 0.073843, -0.000918, 0.001890, 0.000000};



            cameraMatrix.put(row, col, cameraMatrix_sim);
            distCoeffs.put(row, col, distCoeffs_sim);
            Log.d("Mode[camera]:"," sim");



        cameraMatrix.put(row, col, cameraMatrix_orbit);
        distCoeffs.put(row, col, distCoeffs_orbit);

        Imgproc.undistort(src, dst, cameraMatrix, distCoeffs);
        return dst;
    }

    // You can add your method
    private void moveToWrapper(double pos_x, double pos_y, double pos_z,
                               double qua_x, double qua_y, double qua_z,
                               double qua_w){

        final Point point = new Point(pos_x, pos_y, pos_z);
        final Quaternion quaternion = new Quaternion((float)qua_x, (float)qua_y,
                (float)qua_z, (float)qua_w);

        api.moveTo(point, quaternion, true);
    }

    private void relativeMoveToWrapper(double pos_x, double pos_y, double pos_z,
                                       double qua_x, double qua_y, double qua_z,
                                       double qua_w) {

        final Point point = new Point(pos_x, pos_y, pos_z);
        final Quaternion quaternion = new Quaternion((float) qua_x, (float) qua_y,
                (float) qua_z, (float) qua_w);

        api.relativeMoveTo(point, quaternion, true);
    }

}