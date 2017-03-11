package clickerbot.com.troop.clickerbot;

/**
 * Created by troop on 20.12.2016.
 */

public class CmdBuilder
{
    final int EV_ABS = 3;
    final int EV_SYN = 0;

    final int SYN_REPORT = 0;

    final int ABS_MT_SLOT		=    0x2f;	/* MT slot being modified */
    final int ABS_MT_TOUCH_MAJOR =	0x30;	/* Major axis of touching ellipse */
    //        #define ABS_MT_TOUCH_MINOR	0x31	/* Minor axis (omit if circular) */
    final int ABS_MT_WIDTH_MAJOR =	0x32;	/* Major axis of approaching ellipse */
    final int ABS_MT_WIDTH_MINOR	= 0x33;	/* Minor axis (omit if circular) */
    final int ABS_MT_ORIENTATION	=0x34;	/* Ellipse orientation */
    final int ABS_MT_POSITION_X	=    0x35;	/* Center X touch position */
    final int ABS_MT_POSITION_Y	=    0x36;	/* Center Y touch position */
    //        #define ABS_MT_TOOL_TYPE	    0x37	/* Type of touching device */
//        #define ABS_MT_BLOB_ID		0x38	/* Group a set of packets as a blob */
    final int ABS_MT_TRACKING_ID = 0x39;	/* Unique ID of initiated contact */
    final int ABS_MT_PRESSURE	=	0x3a;	/* Pressure on contact area */
    //        #define ABS_MT_DISTANCE		0x3b	/* Contact hover distance */
//        #define ABS_MT_TOOL_X		    0x3c	/* Center X tool position */
//        #define ABS_MT_TOOL_Y	        0x3d /* Center Y tool position */
    final int maxint = 0xffffffff;

    private final String inputmount;
    private final String sendEvent = "sendevent ";

    public CmdBuilder(String inputmount)
    {
        this.inputmount = inputmount;
    }


    //private final static String sendEvent = "sendevent /dev/input/event0 ";


    private String createSendEvent(int type, int command, int value)
    {
        return sendEvent + inputmount +" " + type + " " + command + " " + value + "\n";
    }

    public String getTouchDown(int x, int y, int id)
    {
        return
                createSendEvent(EV_ABS, ABS_MT_TRACKING_ID,id) + /* touch id*/ //0003 0039 00000000
                createSendEvent(EV_ABS, ABS_MT_POSITION_X,x) + /* x*/ //0003 0035 000003ce
                createSendEvent(EV_ABS, ABS_MT_POSITION_Y,y);/* y*/  //0003 0036 000003c4

    }

    public String getTouchUp()
    {
        return createSendEvent(EV_ABS, ABS_MT_TRACKING_ID,maxint);
    }

    public String getSynEvent() {
        return createSendEvent(EV_SYN, SYN_REPORT, 0);
    }

}
