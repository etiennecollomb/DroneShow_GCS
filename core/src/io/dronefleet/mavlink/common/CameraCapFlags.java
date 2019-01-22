package io.dronefleet.mavlink.common;

import io.dronefleet.mavlink.annotations.MavlinkEntryInfo;
import io.dronefleet.mavlink.annotations.MavlinkEnum;

/**
 * Camera capability flags (Bitmap). 
 */
@MavlinkEnum
public enum CameraCapFlags {
    /**
     * Camera is able to record video. 
     */
    @MavlinkEntryInfo(1)
    CAMERA_CAP_FLAGS_CAPTURE_VIDEO,

    /**
     * Camera is able to capture images. 
     */
    @MavlinkEntryInfo(2)
    CAMERA_CAP_FLAGS_CAPTURE_IMAGE,

    /**
     * Camera has separate Video and Image/Photo modes (MAV_CMD_SET_CAMERA_MODE) 
     */
    @MavlinkEntryInfo(4)
    CAMERA_CAP_FLAGS_HAS_MODES,

    /**
     * Camera can capture images while in video mode 
     */
    @MavlinkEntryInfo(8)
    CAMERA_CAP_FLAGS_CAN_CAPTURE_IMAGE_IN_VIDEO_MODE,

    /**
     * Camera can capture videos while in Photo/Image mode 
     */
    @MavlinkEntryInfo(16)
    CAMERA_CAP_FLAGS_CAN_CAPTURE_VIDEO_IN_IMAGE_MODE,

    /**
     * Camera has image survey mode (MAV_CMD_SET_CAMERA_MODE) 
     */
    @MavlinkEntryInfo(32)
    CAMERA_CAP_FLAGS_HAS_IMAGE_SURVEY_MODE
}
