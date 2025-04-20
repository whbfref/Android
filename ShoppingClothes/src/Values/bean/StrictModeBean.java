package Values.bean;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StrictModeBean {
	public static void StrictM(){
		//�����׿4.0�汾�������������
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
							        .detectDiskReads()  
							        .detectDiskWrites()  
							        .detectNetwork()   // or .detectAll() for all detectable problems  
							        .penaltyLog()  
							        .build());  
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
							        .detectLeakedSqlLiteObjects()  
							        .detectLeakedClosableObjects()  
							        .penaltyLog()  
							        .penaltyDeath()  
							        .build());  
	}

}
