package jp.jaxa.iss.kibo.rpc.sampleapk;

import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import android.util.Log;
import org.opencv.core.Mat;

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