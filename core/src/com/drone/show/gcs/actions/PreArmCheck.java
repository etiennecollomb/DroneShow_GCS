package com.drone.show.gcs.actions;

import java.beans.PropertyChangeEvent;

import com.drone.show.gcs.MavLinkToolKit;
import com.drone.show.gcs.MavlinkCommunicationModel;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.common.GpsFixType;



public class PreArmCheck extends MavlinkAction {

	
	private static final int GPS_FIX_TYPE_NO_GPS = 0;
	private static final int GPS_FIX_TYPE_NO_FIX = 1;
	private static final int GPS_FIX_TYPE_2D_FIX = 2;
	private static final int GPS_FIX_TYPE_3D_FIX = 3;
	private static final int GPS_FIX_TYPE_DGPS = 4;
	private static final int GPS_FIX_TYPE_RTK_FLOAT = 5;
	private static final int GPS_FIX_TYPE_RTK_FIXED = 6;
	private static final int GPS_FIX_TYPE_STATIC = 7;
	private static final int GPS_FIX_TYPE_PPP = 8;


	/** GPS stuff
	 * Par ordre croissant de precision pour Kalhman
	 */
	//No RTK
	private boolean is3DFixNeeded = true;
	private boolean isDGPSFixNeeded = false;
	//with RTK
	private boolean isRTKFloatNeeded = false;
	private boolean isRTKFixNeeded = false;

	
	public PreArmCheck(MavlinkConnection connection) {
		super(connection);
		
		/** no command specified, on attends que les parametres soient OK */
		this.streamDataID = MavLinkToolKit.MAVLINK_STREAM_DATA_ID_GpsRawInt;
	}



	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
		String propertyName = evt.getPropertyName();

		
		/** GPS STATUS */
		if (propertyName.equals(MavlinkCommunicationModel.GPS_FIX_TYPE)){

			GpsFixType gpsFixType = (GpsFixType) evt.getNewValue();
			
			boolean isAllOK = true;

			int currentGPSFixType = -1;
			if(gpsFixType == GpsFixType.GPS_FIX_TYPE_NO_GPS) currentGPSFixType = GPS_FIX_TYPE_NO_GPS;
			if(gpsFixType == GpsFixType.GPS_FIX_TYPE_NO_FIX) currentGPSFixType = GPS_FIX_TYPE_NO_FIX;
			if(gpsFixType == GpsFixType.GPS_FIX_TYPE_2D_FIX) currentGPSFixType = GPS_FIX_TYPE_2D_FIX;
			if(gpsFixType == GpsFixType.GPS_FIX_TYPE_3D_FIX) currentGPSFixType = GPS_FIX_TYPE_3D_FIX;
			if(gpsFixType == GpsFixType.GPS_FIX_TYPE_DGPS) currentGPSFixType = GPS_FIX_TYPE_DGPS;
			if(gpsFixType == GpsFixType.GPS_FIX_TYPE_RTK_FLOAT) currentGPSFixType = GPS_FIX_TYPE_RTK_FLOAT;
			if(gpsFixType == GpsFixType.GPS_FIX_TYPE_RTK_FIXED) currentGPSFixType = GPS_FIX_TYPE_RTK_FIXED;
			if(gpsFixType == GpsFixType.GPS_FIX_TYPE_STATIC) currentGPSFixType = GPS_FIX_TYPE_STATIC;
			if(gpsFixType == GpsFixType.GPS_FIX_TYPE_PPP) currentGPSFixType = GPS_FIX_TYPE_PPP;


			if(this.is3DFixNeeded && currentGPSFixType < GPS_FIX_TYPE_3D_FIX ) {
				Tools.writeLog("### !!! "+this.getClass().getName() + ": gpsFixType < GPS_FIX_TYPE_3D_FIX (current: " + gpsFixType + ")");
				isAllOK = false;
			}

			if(this.isDGPSFixNeeded && currentGPSFixType < GPS_FIX_TYPE_DGPS ) {
				Tools.writeLog("### !!! "+this.getClass().getName() + ": gpsFixType < GPS_FIX_TYPE_DGPS (current: " + gpsFixType + ")");
				isAllOK = false;
			}

			if(this.isRTKFloatNeeded && currentGPSFixType < GPS_FIX_TYPE_RTK_FLOAT ) {
				Tools.writeLog("### !!! "+this.getClass().getName() + ": gpsFixType < GPS_FIX_TYPE_RTK_FLOAT (current: " + gpsFixType + ")");
				isAllOK = false;
			}

			if(this.isRTKFixNeeded && currentGPSFixType < GPS_FIX_TYPE_RTK_FIXED ) {
				Tools.writeLog("### !!! "+this.getClass().getName() + ": gpsFixType < GPS_FIX_TYPE_RTK_FIXED (current: " + gpsFixType + ")");
				isAllOK = false;
			}


			this.setFinished(isAllOK);
			
		}
		
	}

}
