package io.dronefleet.mavlink.ardupilotmega;

import io.dronefleet.mavlink.annotations.MavlinkEntryInfo;
import io.dronefleet.mavlink.annotations.MavlinkEnum;

/**
 *  
 */
@MavlinkEnum
public enum GoproHeartbeatFlags {
    /**
     * GoPro is currently recording 
     */
    @MavlinkEntryInfo(1)
    GOPRO_FLAG_RECORDING
}
