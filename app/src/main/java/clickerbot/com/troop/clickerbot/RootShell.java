package clickerbot.com.troop.clickerbot;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by troop on 17.12.2016.
 */

public class RootShell
{
    private final String TAG = RootShell.class.getSimpleName();
    private Process process;
    private DataOutputStream os;
    private int id;
    private String cmdDown;
    private String cmdUp;
    private String synevent;
    private int cmdsleep;


    public RootShell(int id, int cmdsleep, int x, int y, String inputmount)
    {
        CmdBuilder builder = new CmdBuilder(inputmount);
        this.id =id;
        this.cmdsleep = cmdsleep;
        Log.d(TAG, "set touch to: " + x+"/"+y);
        //if ((id % 2) == 0 )
            cmdDown = builder.getTouchDown(x,y,id);
        //else
        //   cmdDown = CmdBuilder.getTouchDown(x,y-200,id);
        cmdUp = builder.getTouchUp();
        synevent = builder.getSynEvent();
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"created ID:" + id);

    }

    public void SendCMD()
    {
        try {

            sendTouchDownUp();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Log.d(TAG,"sendCMD ID:" + id);

    }

    private void sendTouchDownUp() throws IOException, InterruptedException {
        os.writeChars(cmdDown);
        os.flush();
        os.writeChars(synevent);
        os.flush();
        Thread.sleep(cmdsleep);
        os.writeChars(cmdUp);
        os.flush();
        os.writeChars(synevent);
        os.flush();
    }

    public void Close()
    {
        try {
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"closed ID:" + id);
    }
}
