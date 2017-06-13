package sunspring.swf;

public class SwfGlobal {
	
	//task status
	public final static int APPROVE_TYPE_WORKING=10;
	public final static int APPROVE_TYPE_AGREE=20;
	public final static int APPROVE_TYPE_PASS=21;
	public final static int APPROVE_TYPE_CLOSE=22;
	public final static int APPROVE_TYPE_SYSTEM_CLOSE=23;
	public final static int APPROVE_TYPE_NOT_AGREE=40;
	public final static int APPROVE_TYPE_WAITTING=50;
	public final static int APPROVE_TYPE_APPLY=60;
	
	//process status
	public final static int AVTIVITY_TYPE_REGISTER=10;
	public final static int AVTIVITY_TYPE_AUDIT=20;
	public final static int AVTIVITY_TYPE_AUDIT_RETURN=21;
	public final static int AVTIVITY_TYPE_APPROVE=40;
	public final static int AVTIVITY_TYPE_APPROVE_RETURN=41;
	public final static int AVTIVITY_TYPE_HANDLE=60;
	public final static int AVTIVITY_TYPE_CLOSE=80;
	//process request level
	public final static String REQUEST_LEVEL_NORMAL="R";
	public final static String REQUEST_LEVEL_FAST="E";
	public final static String REQUEST_LEVEL_URGENT="U";
	
}
