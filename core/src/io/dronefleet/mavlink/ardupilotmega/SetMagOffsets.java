package io.dronefleet.mavlink.ardupilotmega;

import io.dronefleet.mavlink.annotations.MavlinkFieldInfo;
import io.dronefleet.mavlink.annotations.MavlinkMessageBuilder;
import io.dronefleet.mavlink.annotations.MavlinkMessageInfo;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

/**
 * Deprecated. Use MAV_CMD_PREFLIGHT_SET_SENSOR_OFFSETS instead. Set the magnetometer 
 * offsets 
 */
@MavlinkMessageInfo(
        id = 151,
        crc = 219,
        description = "Deprecated. Use MAV_CMD_PREFLIGHT_SET_SENSOR_OFFSETS instead. Set the magnetometer offsets"
)
public final class SetMagOffsets {
    private final int targetSystem;

    private final int targetComponent;

    private final int magOfsX;

    private final int magOfsY;

    private final int magOfsZ;

    private SetMagOffsets(int targetSystem, int targetComponent, int magOfsX, int magOfsY,
            int magOfsZ) {
        this.targetSystem = targetSystem;
        this.targetComponent = targetComponent;
        this.magOfsX = magOfsX;
        this.magOfsY = magOfsY;
        this.magOfsZ = magOfsZ;
    }

    /**
     * Returns a builder instance for this message.
     */
    @MavlinkMessageBuilder
    public static Builder builder() {
        return new Builder();
    }

    /**
     * System ID 
     */
    @MavlinkFieldInfo(
            position = 1,
            unitSize = 1,
            description = "System ID"
    )
    public final int targetSystem() {
        return this.targetSystem;
    }

    /**
     * Component ID 
     */
    @MavlinkFieldInfo(
            position = 2,
            unitSize = 1,
            description = "Component ID"
    )
    public final int targetComponent() {
        return this.targetComponent;
    }

    /**
     * magnetometer X offset 
     */
    @MavlinkFieldInfo(
            position = 3,
            unitSize = 2,
            signed = true,
            description = "magnetometer X offset"
    )
    public final int magOfsX() {
        return this.magOfsX;
    }

    /**
     * magnetometer Y offset 
     */
    @MavlinkFieldInfo(
            position = 4,
            unitSize = 2,
            signed = true,
            description = "magnetometer Y offset"
    )
    public final int magOfsY() {
        return this.magOfsY;
    }

    /**
     * magnetometer Z offset 
     */
    @MavlinkFieldInfo(
            position = 5,
            unitSize = 2,
            signed = true,
            description = "magnetometer Z offset"
    )
    public final int magOfsZ() {
        return this.magOfsZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(o.getClass())) return false;
        SetMagOffsets other = (SetMagOffsets)o;
        if (!Objects.deepEquals(targetSystem, other.targetSystem)) return false;
        if (!Objects.deepEquals(targetComponent, other.targetComponent)) return false;
        if (!Objects.deepEquals(magOfsX, other.magOfsX)) return false;
        if (!Objects.deepEquals(magOfsY, other.magOfsY)) return false;
        if (!Objects.deepEquals(magOfsZ, other.magOfsZ)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + Objects.hashCode(targetSystem);
        result = 31 * result + Objects.hashCode(targetComponent);
        result = 31 * result + Objects.hashCode(magOfsX);
        result = 31 * result + Objects.hashCode(magOfsY);
        result = 31 * result + Objects.hashCode(magOfsZ);
        return result;
    }

    @Override
    public String toString() {
        return "SetMagOffsets{targetSystem=" + targetSystem
                 + ", targetComponent=" + targetComponent
                 + ", magOfsX=" + magOfsX
                 + ", magOfsY=" + magOfsY
                 + ", magOfsZ=" + magOfsZ + "}";
    }

    public static final class Builder {
        private int targetSystem;

        private int targetComponent;

        private int magOfsX;

        private int magOfsY;

        private int magOfsZ;

        /**
         * System ID 
         */
        @MavlinkFieldInfo(
                position = 1,
                unitSize = 1,
                description = "System ID"
        )
        public final Builder targetSystem(int targetSystem) {
            this.targetSystem = targetSystem;
            return this;
        }

        /**
         * Component ID 
         */
        @MavlinkFieldInfo(
                position = 2,
                unitSize = 1,
                description = "Component ID"
        )
        public final Builder targetComponent(int targetComponent) {
            this.targetComponent = targetComponent;
            return this;
        }

        /**
         * magnetometer X offset 
         */
        @MavlinkFieldInfo(
                position = 3,
                unitSize = 2,
                signed = true,
                description = "magnetometer X offset"
        )
        public final Builder magOfsX(int magOfsX) {
            this.magOfsX = magOfsX;
            return this;
        }

        /**
         * magnetometer Y offset 
         */
        @MavlinkFieldInfo(
                position = 4,
                unitSize = 2,
                signed = true,
                description = "magnetometer Y offset"
        )
        public final Builder magOfsY(int magOfsY) {
            this.magOfsY = magOfsY;
            return this;
        }

        /**
         * magnetometer Z offset 
         */
        @MavlinkFieldInfo(
                position = 5,
                unitSize = 2,
                signed = true,
                description = "magnetometer Z offset"
        )
        public final Builder magOfsZ(int magOfsZ) {
            this.magOfsZ = magOfsZ;
            return this;
        }

        public final SetMagOffsets build() {
            return new SetMagOffsets(targetSystem, targetComponent, magOfsX, magOfsY, magOfsZ);
        }
    }
}
