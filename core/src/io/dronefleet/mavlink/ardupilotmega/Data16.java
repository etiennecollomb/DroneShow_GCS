package io.dronefleet.mavlink.ardupilotmega;

import io.dronefleet.mavlink.annotations.MavlinkFieldInfo;
import io.dronefleet.mavlink.annotations.MavlinkMessageBuilder;
import io.dronefleet.mavlink.annotations.MavlinkMessageInfo;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

/**
 * Data packet, size 16 
 */
@MavlinkMessageInfo(
        id = 169,
        crc = 234,
        description = "Data packet, size 16"
)
public final class Data16 {
    private final int type;

    private final int len;

    private final byte[] data;

    private Data16(int type, int len, byte[] data) {
        this.type = type;
        this.len = len;
        this.data = data;
    }

    /**
     * Returns a builder instance for this message.
     */
    @MavlinkMessageBuilder
    public static Builder builder() {
        return new Builder();
    }

    /**
     * data type 
     */
    @MavlinkFieldInfo(
            position = 1,
            unitSize = 1,
            description = "data type"
    )
    public final int type() {
        return this.type;
    }

    /**
     * data length 
     */
    @MavlinkFieldInfo(
            position = 2,
            unitSize = 1,
            description = "data length"
    )
    public final int len() {
        return this.len;
    }

    /**
     * raw data 
     */
    @MavlinkFieldInfo(
            position = 3,
            unitSize = 1,
            arraySize = 16,
            description = "raw data"
    )
    public final byte[] data() {
        return this.data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(o.getClass())) return false;
        Data16 other = (Data16)o;
        if (!Objects.deepEquals(type, other.type)) return false;
        if (!Objects.deepEquals(len, other.len)) return false;
        if (!Objects.deepEquals(data, other.data)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + Objects.hashCode(type);
        result = 31 * result + Objects.hashCode(len);
        result = 31 * result + Objects.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Data16{type=" + type
                 + ", len=" + len
                 + ", data=" + data + "}";
    }

    public static final class Builder {
        private int type;

        private int len;

        private byte[] data;

        /**
         * data type 
         */
        @MavlinkFieldInfo(
                position = 1,
                unitSize = 1,
                description = "data type"
        )
        public final Builder type(int type) {
            this.type = type;
            return this;
        }

        /**
         * data length 
         */
        @MavlinkFieldInfo(
                position = 2,
                unitSize = 1,
                description = "data length"
        )
        public final Builder len(int len) {
            this.len = len;
            return this;
        }

        /**
         * raw data 
         */
        @MavlinkFieldInfo(
                position = 3,
                unitSize = 1,
                arraySize = 16,
                description = "raw data"
        )
        public final Builder data(byte[] data) {
            this.data = data;
            return this;
        }

        public final Data16 build() {
            return new Data16(type, len, data);
        }
    }
}
