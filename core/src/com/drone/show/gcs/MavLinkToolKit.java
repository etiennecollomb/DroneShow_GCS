package com.drone.show.gcs;

import java.io.IOException;

import com.badlogic.gdx.math.Vector3;
import com.drone.show.generic.Tools;

import io.dronefleet.mavlink.MavlinkConnection;
import io.dronefleet.mavlink.MavlinkMessage;
import io.dronefleet.mavlink.common.CommandLong;
import io.dronefleet.mavlink.common.GpsStatus;
import io.dronefleet.mavlink.common.Heartbeat;
import io.dronefleet.mavlink.common.MavAutopilot;
import io.dronefleet.mavlink.common.MavCmd;
import io.dronefleet.mavlink.common.MavFrame;
import io.dronefleet.mavlink.common.MavMissionType;
import io.dronefleet.mavlink.common.MavMode;
import io.dronefleet.mavlink.common.MavParamType;
import io.dronefleet.mavlink.common.MavState;
import io.dronefleet.mavlink.common.MavType;
import io.dronefleet.mavlink.common.MessageInterval;
import io.dronefleet.mavlink.common.MissionClearAll;
import io.dronefleet.mavlink.common.MissionCount;
import io.dronefleet.mavlink.common.MissionItem;
import io.dronefleet.mavlink.common.MissionItemInt;
import io.dronefleet.mavlink.common.MissionItemReached;
import io.dronefleet.mavlink.common.ParamRequestList;
import io.dronefleet.mavlink.common.ParamRequestRead;
import io.dronefleet.mavlink.common.ParamSet;
import io.dronefleet.mavlink.common.RcChannelsOverride;
import io.dronefleet.mavlink.common.RcChannelsRaw;
import io.dronefleet.mavlink.common.RequestDataStream;
import io.dronefleet.mavlink.common.ServoOutputRaw;
import io.dronefleet.mavlink.common.SetMode;
import io.dronefleet.mavlink.common.SetPositionTargetGlobalInt;
import io.dronefleet.mavlink.common.SetPositionTargetLocalNed;
import io.dronefleet.mavlink.util.reflection.MavlinkReflection;



/**
 * 
 * ToolKit to send mavlink commands 
 * http://ardupilot.org/dev/docs/copter-commands-in-guided-mode.html
 * http://ardupilot.org/copter/docs/common-mavlink-mission-command-messages-mav_cmd.html
 *
 */
public class MavLinkToolKit {
	
	public static int ALL_SYSTEM_ID = 0;
	

	/** https://github.com/ArduPilot/ardupilot/blob/master/ArduCopter/defines.h
	 * 
	 * enum control_mode_t {
	    STABILIZE =     0,  // manual airframe angle with manual throttle
	    ACRO =          1,  // manual body-frame angular rate with manual throttle
	    ALT_HOLD =      2,  // manual airframe angle with automatic throttle
	    AUTO =          3,  // fully automatic waypoint control using mission commands
	    GUIDED =        4,  // fully automatic fly to coordinate or fly at velocity/direction using GCS immediate commands
	    LOITER =        5,  // automatic horizontal acceleration with automatic throttle
	    RTL =           6,  // automatic return to launching point
	    CIRCLE =        7,  // automatic circular flight with automatic throttle
	    LAND =          9,  // automatic landing with horizontal position control
	    DRIFT =        11,  // semi-automous position, yaw and throttle control
	    SPORT =        13,  // manual earth-frame angular rate control with manual throttle
	    FLIP =         14,  // automatically flip the vehicle on the roll axis
	    AUTOTUNE =     15,  // automatically tune the vehicle's roll and pitch gains
	    POSHOLD =      16,  // automatic position hold with manual override, with automatic throttle
	    BRAKE =        17,  // full-brake using inertial/GPS system, no pilot input
	    THROW =        18,  // throw to launch mode using inertial/GPS system, no pilot input
	    AVOID_ADSB =   19,  // automatic avoidance of obstacles in the macro scale - e.g. full-sized aircraft
	    GUIDED_NOGPS = 20,  // guided mode but only accepts attitude and altitude
	    SMART_RTL =    21,  // SMART_RTL returns to home by retracing its steps
	    FLOWHOLD  =    22,  // FLOWHOLD holds position with optical flow without rangefinder
	    FOLLOW    =    23,  // follow attempts to follow another vehicle or ground station
	    ZIGZAG    =    24,  // ZIGZAG mode is able to fly in a zigzag manner with predefined point A and point B
	};
	 */
	public static final int STABILIZE_CUSTOM_MODE = 0;
	public static final int AUTO_CUSTOM_MODE = 3;
	public static final int GUIDED_CUSTOM_MODE = 4;
	public static final int LOITER_CUSTOM_MODE = 5;
	public static final int LAND_CUSTOM_MODE = 9;

	
	
	/** Data Stream ID */
	public static final int MAVLINK_STREAM_DATA_ID_ALL_DATA = 0;
	public static final int MAVLINK_STREAM_DATA_ID_RawImu = 1;
	public static final int MAVLINK_STREAM_DATA_ID_ScaledImu2 = 1;
	public static final int MAVLINK_STREAM_DATA_ID_ScaledPressure = 1;
	public static final int MAVLINK_STREAM_DATA_ID_SysStatus = 2;
	public static final int MAVLINK_STREAM_DATA_ID_PowerStatus = 2;
	public static final int MAVLINK_STREAM_DATA_ID_Meminfo = 2;
	public static final int MAVLINK_STREAM_DATA_ID_MissionCurrent = 2;
	public static final int MAVLINK_STREAM_DATA_ID_GpsRawInt = 2;
	public static final int MAVLINK_STREAM_DATA_ID_NavControllerOutput = 2;
	public static final int MAVLINK_STREAM_DATA_ID_FenceStatus = 2;
	public static final int MAVLINK_STREAM_DATA_ID_RcChannelsRaw = 3;
	public static final int MAVLINK_STREAM_DATA_ID_RcChannels = 3;
	public static final int MAVLINK_STREAM_DATA_ID_ServoOutputRaw = 3;
	public static final int MAVLINK_STREAM_DATA_ID_GlobalPositionInt = 6;
	public static final int MAVLINK_STREAM_DATA_ID_LocalPositionNed = 6;
	public static final int MAVLINK_STREAM_DATA_ID_Attitude = 10;
	public static final int MAVLINK_STREAM_DATA_ID_Ahrs2 = 10;
	public static final int MAVLINK_STREAM_DATA_ID_Ahrs3 = 10;
	public static final int MAVLINK_STREAM_DATA_ID_VfrHud = 11;
	public static final int MAVLINK_STREAM_DATA_ID_Ahrs = 12;
	public static final int MAVLINK_STREAM_DATA_ID_Hwstatus = 12;
	public static final int MAVLINK_STREAM_DATA_ID_SystemTime = 12;
	public static final int MAVLINK_STREAM_DATA_ID_TerrainReport = 12;
	public static final int MAVLINK_STREAM_DATA_ID_EkfStatusReport = 12;
	public static final int MAVLINK_STREAM_DATA_ID_Vibration = 12;


	/** Servo Stuff */
	public static final int DISABLE = 0;
	public static final int RC_PASS_TRU = 1;


	/****************************************************
	 * 
	 * Send a specific command to a MavlinkConnection
	 ****************************************************/

	@SuppressWarnings("rawtypes")
	public synchronized static void sendCommand(MavlinkConnection connection, MavlinkMessage mavlinkMessage) {

		try {

			Tools.writeLog("######## Sending Mavlink to Pixhawk: "+mavlinkMessage.toString());
			connection.send(mavlinkMessage);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	/****************************************************
	 * 
	 * Set of MavlinkMessage
	 ****************************************************/

	/**
	 * Type of Stream data from Ardupilot (8 SATTELITES)
	GpsRawInt{timeUsec=null, fixType=EnumValue{value=3, entry=GPS_FIX_TYPE_3D_FIX}, lat=488440612, lon=24661058, alt=78930, eph=139, epv=189, vel=0, cog=2843, satellitesVisible=8, altEllipsoid=0, hAcc=0, vAcc=0, velAcc=0, hdgAcc=0}
	NavControllerOutput{navRoll=-4.343752, navPitch=1.270647, navBearing=-18, targetBearing=0, wpDist=0, altError=0.24297178, aspdError=0.0, xtrackError=0.0}
	GlobalPositionInt{timeBootMs=163716, lat=488440715, lon=24661186, alt=85690, relativeAlt=4840, vx=11, vy=8, vz=-11, hdg=34125}
	LocalPositionNed{timeBootMs=163741, x=3.7666657, y=3.7484236, z=-4.847028, vx=0.11362605, vy=0.0871584, vz=0.117609866}
	ServoOutputRaw{timeUsec=163741742, port=0, servo1Raw=1000, servo2Raw=1000, servo3Raw=1000, servo4Raw=1000, servo5Raw=0, servo6Raw=0, servo7Raw=0, servo8Raw=0, servo9Raw=0, servo10Raw=0, servo11Raw=0, servo12Raw=0, servo13Raw=0, servo14Raw=0, servo15Raw=0, servo16Raw=0}
	RcChannelsRaw{timeBootMs=163741, port=0, chan1Raw=0, chan2Raw=0, chan3Raw=0, chan4Raw=0, chan5Raw=0, chan6Raw=0, chan7Raw=0, chan8Raw=0, rssi=0}
	RcChannels{timeBootMs=163741, chancount=0, chan1Raw=0, chan2Raw=0, chan3Raw=0, chan4Raw=0, chan5Raw=0, chan6Raw=0, chan7Raw=0, chan8Raw=0, chan9Raw=0, chan10Raw=0, chan11Raw=0, chan12Raw=0, chan13Raw=0, chan14Raw=0, chan15Raw=0, chan16Raw=0, chan17Raw=0, chan18Raw=0, rssi=0}
	Attitude{timeBootMs=163741, roll=-0.07581362, pitch=0.022175549, yaw=-0.32735372, rollspeed=-1.6800934E-4, pitchspeed=1.6473097E-4, yawspeed=-0.0010310058}
	Ahrs2{roll=-0.079649, pitch=0.021759761, yaw=-0.32257536, altitude=0.0, lat=0, lng=0}
	Ahrs3{roll=-0.07581362, pitch=0.022175549, yaw=-0.32735372, altitude=85.689995, lat=488440715, lng=24661186, v1=0.0, v2=0.0, v3=0.0, v4=0.0}
	VfrHud{airspeed=0.008, groundspeed=0.14320429, heading=341, throttle=0, alt=4.8399997, climb=-0.11}
	Ahrs{omegaix=3.682789E-4, omegaiy=-2.154946E-4, omegaiz=-3.3300774E-5, accelWeight=0.0, renormVal=0.0, errorRp=0.0011068854, errorYaw=0.00699771}
	Hwstatus{vcc=4898, i2cerr=0}
	SystemTime{timeUnixUsec=null, timeBootMs=163742}
	TerrainReport{lat=488440715, lon=24661186, spacing=100, terrainHeight=65.07674, currentHeight=4.831142, pending=0, loaded=336}
	BatteryStatus{id=0, batteryFunction=EnumValue{value=0, entry=MAV_BATTERY_FUNCTION_UNKNOWN}, type=EnumValue{value=0, entry=MAV_BATTERY_TYPE_UNKNOWN}, temperature=32767, voltages=null, currentBattery=5, currentConsumed=1, energyConsumed=-1, batteryRemaining=99, timeRemaining=0, chargeState=null}
	EkfStatusReport{flags=EnumValue{value=895, entry=null}, velocityVariance=0.04227184, posHorizVariance=0.041275263, posVertVariance=0.042153634, compassVariance=0.005669823, terrainAltVariance=0.007987975, airspeedVariance=0.0}
	Vibration{timeUsec=null, vibrationX=0.023072952, vibrationY=0.02778883, vibrationZ=0.033979125, clipping0=0, clipping1=0, clipping2=0}
	RawImu{timeUsec=null, xacc=21, yacc=91, zacc=-970, xgyro=0, ygyro=0, zgyro=0, xmag=148, ymag=14, zmag=424}
	ScaledImu2{timeBootMs=164423, xacc=-15, yacc=102, zacc=-995, xgyro=5, ygyro=0, zgyro=0, xmag=92, ymag=142, zmag=497}
	ScaledPressure{timeBootMs=164423, pressAbs=1000.9689, pressDiff=-0.044609375, temperature=4679}
	SysStatus{onboardControlSensorsPresent=EnumValue{value=56687663, entry=null}, onboardControlSensorsEnabled=EnumValue{value=39853103, entry=null}, onboardControlSensorsHealth=EnumValue{value=56687663, entry=null}, load=352, voltageBattery=12100, currentBattery=4, batteryRemaining=99, dropRateComm=0, errorsComm=0, errorsCount1=0, errorsCount2=0, errorsCount3=0, errorsCount4=0}
	PowerStatus{vcc=4896, vservo=4952, flags=EnumValue{value=0, entry=null}}
	Meminfo{brkval=0, freemem=43232, freemem32=0}
	MissionCurrent{seq=0}
	FenceStatus{breachStatus=0, breachCount=1, breachType=EnumValue{value=0, entry=FENCE_BREACH_NONE}, breachTime=2582547}
	 */

	//NOTE : Des messages ne sont pas envoyes si on est en dessou de 8 sattelites captes (LocalPositionNed...etc)
	//Ce sont des parametres a regler concernant le nombre de sattelites
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage requestStreamData(int droneID, int streamID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				RequestDataStream.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.reqStreamId(streamID) //ALL DATA
				.reqMessageRate(5) //Hz
				.startStop(1)  //1 enable , 0 disable
				.build());

	}

	@SuppressWarnings("rawtypes")
	public static MavlinkMessage stopAllStreamData(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				RequestDataStream.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.reqStreamId(0) //ALL DATA
				.reqMessageRate(5) //Hz
				.startStop(0)  //1 enable , 0 disable
				.build());

	}




	/**
	 * Heartbeat received by Ardupilot
	 * Heartbeat{type=EnumValue{value=2, entry=MAV_TYPE_QUADROTOR}, autopilot=EnumValue{value=3, entry=MAV_AUTOPILOT_ARDUPILOTMEGA}, baseMode=EnumValue{value=81, entry=null}, customMode=0, systemStatus=EnumValue{value=4, entry=MAV_STATE_ACTIVE}, mavlinkVersion=3}
	 */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage heartbeat() {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				Heartbeat.builder()
				.type(MavType.MAV_TYPE_GCS)
				.autopilot(MavAutopilot.MAV_AUTOPILOT_INVALID)
				.systemStatus(MavState.MAV_STATE_UNINIT)
				.mavlinkVersion(3)
				.build());

	}


	@SuppressWarnings("rawtypes")
	public static MavlinkMessage arm(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				CommandLong.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.command(MavCmd.MAV_CMD_COMPONENT_ARM_DISARM)
				.confirmation(0)
				.param1(1) //1 enable , 0 disable
				.build());

	}



	@SuppressWarnings("rawtypes")
	/**
	 * param7 : Altitude in meter
	 */
	public static MavlinkMessage takeOff(int droneID, float altitude) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				CommandLong.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.command(MavCmd.MAV_CMD_NAV_TAKEOFF)
				.confirmation(0)
				.param7(altitude)
				.build());

	}


	@SuppressWarnings("rawtypes")
	/**
	 * param2 : Speed m/s
	 */
	public static MavlinkMessage changeSpeed(int droneID, float speed) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				CommandLong.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.command(MavCmd.MAV_CMD_DO_CHANGE_SPEED)
				.confirmation(0)
				.param2(speed)
				.build());

	}


	@SuppressWarnings("rawtypes")
	/**
	 * param5	Lat	Target latitude. If zero, the Copter will land at the current latitude.
	 * param6	Lon	Longitude. If zero, the Copter will land at the current Longitude.
	 */
	public static MavlinkMessage land(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				CommandLong.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.command(MavCmd.MAV_CMD_NAV_LAND)
				.confirmation(0)
				.param5(0)
				.param6(0)
				.build());

	}

	/**
	 * https://discuss.ardupilot.org/t/setting-guided-mode-and-waypoint-by-mavlink/17363/3
	 */
	public static MavlinkMessage stabilizeMode(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				SetMode.builder()
				.targetSystem(droneID)
				.baseMode(MavMode.MAV_MODE_CUSTOM) // @param base_mode The new base mode (Should be 1 to use custom_mode) : Rajout fait dans dronefleet dans MavMode.java
				.customMode(STABILIZE_CUSTOM_MODE) // 0 = STABILIZE Defined in ArduCopter/defines.h
				.build());
	}


	/**
	 * https://discuss.ardupilot.org/t/setting-guided-mode-and-waypoint-by-mavlink/17363/3
	 */
	public static MavlinkMessage guidedMode(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				SetMode.builder() 
				.targetSystem(droneID)
				.baseMode(MavMode.MAV_MODE_CUSTOM) // @param base_mode The new base mode (Should be 1 to use custom_mode) : Rajout fait dans dronefleet dans MavMode.java
				.customMode(GUIDED_CUSTOM_MODE) // 4 = GUIDED Defined in ArduCopter/defines.h
				.build());
	}


	/**
	 * https://discuss.ardupilot.org/t/setting-guided-mode-and-waypoint-by-mavlink/17363/3
	 */
	//Quand on passe de mode guided a mode loiter, le throttle perds de la puisssance :
	//"I found the reason is that RC 3 set to 1100.
	//It can be solved in this way, after takeoff in guided mode, send rc 3 1500 to set throttle to 1500, then change mode to loiter, the altitude will be hold."
	//rc 3 = 1500 -> 50 % Throttle or Hold Current Altitude (depends on flight mode)
	public static MavlinkMessage loiterMode(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				SetMode.builder() 
				.targetSystem(droneID)
				.baseMode(MavMode.MAV_MODE_CUSTOM) // @param base_mode The new base mode (Should be 1 to use custom_mode) : Rajout fait dans dronefleet dans MavMode.java
				.customMode(LOITER_CUSTOM_MODE) // 5 = LOITER  Defined in ArduCopter/defines.h
				.build());
	}


	/**
	 * https://discuss.ardupilot.org/t/setting-guided-mode-and-waypoint-by-mavlink/17363/3
	 */
	public static MavlinkMessage landMode(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				SetMode.builder() 
				.targetSystem(droneID)
				.baseMode(MavMode.MAV_MODE_CUSTOM) // @param base_mode The new base mode (Should be 1 to use custom_mode) : Rajout fait dans dronefleet dans MavMode.java
				.customMode(LAND_CUSTOM_MODE) // 9 = GUIDED Defined in ArduCopter/defines.h
				.build());
	}


	/**
	 * X,Y,Z in meters : Positions are relative to the vehicles home position in the North, East, UP frame. (ie : MAV_FRAME_LOCAL_NED)
	 * example : takeoff 30m , puis setPositionTargetLocalNed(0,0,20) -> le drone descend de 10m
	 */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setPositionTargetLocalNed(int droneID, float x_, float y_, float z_) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				SetPositionTargetLocalNed.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.coordinateFrame(MavFrame.MAV_FRAME_LOCAL_NED)
				.typeMask(4088) // 4088 = 0b0000111111111000, # type_mask (only positions enabled)
				.x(x_).y(y_).z(-z_) //in meter (note: altitude is negative in NED)
				.vx(0).vy(0).vz(0)
				.afx(0).afy(0).afz(0)
				.yaw(0).yawRate(0)
				.timeBootMs(0)
				.build());

	}

	/**
	 * VX,VY,VZ in meters/sec : Velocity directions are in the North, East, Down (NED) frame. (ie : MAV_FRAME_LOCAL_NED)
	 * example : takeoff 30m , puis setVelocityTargetLocalNed(0,0,2) -> le drone monte a la vitesse de 2m/s
	 */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setVelocityTargetLocalNed(int droneID, float vx_, float vy_, float vz_) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				SetPositionTargetLocalNed.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.coordinateFrame(MavFrame.MAV_FRAME_LOCAL_NED)
				.typeMask(4039) // 4039 = 0b0000111111000111, # type_mask (only velocities enabled)
				.x(0).y(0).z(0)
				.vx(vx_).vy(vy_).vz(-vz_) //in m/s
				.afx(0).afy(0).afz(0)
				.yaw(0).yawRate(0)
				.timeBootMs(0)
				.build());

	}




	/**
	 * Sets the home location either as the current location or at the location specified in the command.
	 * param1	Current	Set home location: 1=Set home as current location. 2=Use location specified in message parameters.
	 * param5	Lat	Target home latitude (if ``param1=2``)
	 * param6	Lon	Target home longitude (if ``param1=2``)
	 * param7	Alt	Target home altitude (if ``param1=2``)
	 */
	// Must be in GUIDED MODE (so after Arming....)
	// Copter Mission use MAV_CMD_DO_SET_HOME command to set the �home position� in the global coordinate frame (MAV_FRAME_GLOBAL)
	// WGS84 coordinate system, where altitude is relative to mean sea level.
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setHomeToCurrentLocation(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				CommandLong.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.command(MavCmd.MAV_CMD_DO_SET_HOME)
				.confirmation(0)
				.param1(1) //Set Home to current location
				.param5(0)
				.param6(0)
				.param7(0)
				.build());

	}


	/**
	 * Request the message HOME_POSITION from ardupilot to get home position
	 */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage requestHomePosition(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				CommandLong.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.command(MavCmd.MAV_CMD_GET_HOME_POSITION)
				.confirmation(0)
				.build());

	}


	/** 
	 * https://mavlink.io/en/services/parameter.html
	 * SET CHx_OPT = 0 for nothing
	 */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setCHx_OPT(int droneID, int rcNumber, int optionID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				ParamSet.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.paramId("CH"+rcNumber+"_OPT")
				.paramValue(optionID)
				.paramType(MavParamType.MAV_PARAM_TYPE_INT32)
				.build());

	}


	/** 
	 * https://mavlink.io/en/services/parameter.html
	 * SERVOX_FUNCTION = 1 for passthru
	 */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setSERVOx_FUNCTION(int droneID, int rcNumber, int functionID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				ParamSet.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.paramId("SERVO"+rcNumber+"_FUNCTION")
				.paramValue(functionID) //RCPassThru function
				.paramType(MavParamType.MAV_PARAM_TYPE_INT32)
				.build());
	}

	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setSERVOx_MIN(int droneID, int servoNumber, int value) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				ParamSet.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.paramId("SERVO"+servoNumber+"_MIN")
				.paramValue(value)
				.paramType(MavParamType.MAV_PARAM_TYPE_INT32)
				.build());
	}

	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setSERVOx_MAX(int droneID, int servoNumber, int value) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				ParamSet.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.paramId("SERVO"+servoNumber+"_MAX")
				.paramValue(value)
				.paramType(MavParamType.MAV_PARAM_TYPE_INT32)
				.build());
	}


	/**
	 * Ovveride RC Channel
	 */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage rc3ChannelOverride(int droneID, int rcValue) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				RcChannelsOverride.builder() 
				.targetSystem(droneID)
				.chan3Raw(rcValue)
				.build());
	}

	@SuppressWarnings("rawtypes")
	public static MavlinkMessage rc6ChannelOverride(int droneID, int rcValue) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				RcChannelsOverride.builder() 
				.targetSystem(droneID)
				.chan6Raw(rcValue)
				.build());
	}

	@SuppressWarnings("rawtypes")
	public static MavlinkMessage rc7ChannelOverride(int droneID, int rcValue) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				RcChannelsOverride.builder() 
				.targetSystem(droneID)
				.chan7Raw(rcValue)
				.build());
	}

	@SuppressWarnings("rawtypes")
	public static MavlinkMessage rc8ChannelOverride(int droneID, int rcValue) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				RcChannelsOverride.builder() 
				.targetSystem(droneID)
				.chan8Raw(rcValue)
				.build());
	}


	/**
	//	public void setColorOnDrone() {
	//		
	//		if(System.currentTimeMillis() - this.getSend_COLOR_command_timer() > this.getSend_COLOR_command_interval()) {
	//			this.setSend_COLOR_command_timer( System.currentTimeMillis() );
	//
	//			if(this.getColor() != null) {
	//				//Turn ON
	//				MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setRGB_to_channel((int)(color.r*MAX_PWM), (int)(color.g*MAX_PWM), (int)(color.b*MAX_PWM)));
	//				
	//			}
	//			else {
	//				//Turn OFF
	//				MavLinkToolKit.sendCommand(this.getConnection(), MavLinkToolKit.setRGB_to_channel((int)(1), (int)(1), (int)(1)));
	//			}
	//		}
	//
	//	}
	 **/
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setRGB_to_channel(int droneID, int R, int G, int B) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				RcChannelsOverride.builder() 
				.targetSystem(droneID)
				.chan6Raw(R)
				.chan7Raw(G)
				.chan8Raw(B)
				.build());
	}


	@SuppressWarnings("rawtypes")
	public static MavlinkMessage paramRequestList(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				ParamRequestList.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.build());

	}


	
	/**
	 * 
	 * MISSION
	 * 
	 * **/
	
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage missionCount(int droneID, int count) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				MissionCount.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.count(count)
				.build());

	}

	
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage missionClearAll(int droneID) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				MissionClearAll.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.missionType(MavMissionType.MAV_MISSION_TYPE_ALL)
				.build());

	}
	
	
	/**
	 * MISSION_ITEM sends the lat/long of a waypoint as float E-7 numbers
	 */
	//https://discuss.ardupilot.org/t/planning-missions-via-mavlink/16489/7
	//mavlink_msg_mission_item_pack(255, 1, &msg, 1, 0, 0, MAV_FRAME_GLOBAL, MAV_CMD_NAV_TAKEOFF, 0, 0, 0, 0, 0, 0, 0, 0, 25);
	//mavlink_msg_mission_item_pack(255, 1, &msg, 1, 0, 0, MAV_FRAME_GLOBAL, MAV_CMD_NAV_WAYPOINT, 0, 0, 0, 2, 0, 0, 52.464217, -1.280222, 50);
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage missionItem(int droneID, int sequence, float latitude, float longitude, float altitude) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				MissionItem.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.seq(sequence)
				//All other commands use the MAV_FRAME_GLOBAL_RELATIVE_ALT frame,
				//which uses the same latitude and longitude,
				//but sets altitude as relative to the home position (home altitude = 0).
				.frame(MavFrame.MAV_FRAME_GLOBAL_RELATIVE_ALT)
				//.frame(MavFrame.MAV_FRAME_LOCAL_NED) //A tester : est ce supporte?
				.command(MavCmd.MAV_CMD_NAV_WAYPOINT)
				.x(latitude) //lattitude
				.y(longitude) //longitude
				.z(altitude) //altitude
				.build());

	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////// A TESTER ///////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	




	






	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//MAYBE NOT USEFUL
	/**
	 * Defines the distance from a waypoint, that when crossed indicates the wp has been hit.
	 * Range	Increment	Units
	 * 10 - 1000	1	centimeters
	 * Even the WPNAV_RADIUS is only used when the waypoint has a Delay.
	 * With no delay specified the waypoint will be considered complete when the virtual point that the vehicle is chasing reaches the waypoint.
	 */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setWPNAV_RADIUS(int droneID, int centimeterRadius) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				ParamSet.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.paramId("WPNAV_RADIUS")
				.paramValue(centimeterRadius)
				.paramType(MavParamType.MAV_PARAM_TYPE_INT32)
				.build());

	}
	
	/**
	 * param1	Delay	Hold time at mission waypoint in decimal seconds - MAX 65535 seconds. (Copter/Rover only)
	 * param 5	Latitude
	 * param 6	Longitude
	 * param 7	Altitude
	 */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage waypoint(int droneID, float holdTime, float latitude, float longitude, float altitude) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)

				CommandLong.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.command(MavCmd.MAV_CMD_NAV_WAYPOINT)
				.confirmation(0)
				.param1(holdTime)
				.param5(latitude)
				.param6(longitude)
				.param7(altitude)
				.build());

	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//NOT SUPPORTED

	/** Message Interval
	 * Permet de demander l'envoie d'un message depuis le pixhawk pour avoir des données de telemetry
	 * Request the interval between messages for a particular MAVLink message ID
	 * Param1 : The MAVLink message ID
	 * Param 2 :	The interval between two messages, in microseconds. Set to -1 to disable and 0 to request default rate.
	 * */
	@SuppressWarnings("rawtypes")
	public static MavlinkMessage setMessageInterval(int droneID, int msgID, int interval) {

		return new MavlinkMessage<>(
				255, // our system id
				0, // our component id (0 if we're a ground control system)
				CommandLong.builder()
				.targetSystem(droneID)
				.targetComponent(1)
				.command(MavCmd.MAV_CMD_SET_MESSAGE_INTERVAL)
				.confirmation(0)
				.param1(msgID)
				.param2(interval)
				.build());

	}


}
