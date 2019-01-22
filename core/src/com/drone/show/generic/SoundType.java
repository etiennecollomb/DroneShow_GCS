package com.drone.show.generic;


public enum SoundType {


    BUTTON_CLICK			("sounds/menu_click.mp3", 140),
    TOUCH				("sounds/touch.mp3", 159),

    ;

    private String path;
    private int msLength; /** MilliSeconds */


    /**************************************
     *
     * Constructors
     *
     **************************************/

    private SoundType(final String path, final int msLength) {
        this.path = path;
        this.msLength = msLength;
    }

    /**************************************
     *
     * Property Change Support/Listener
     *
     **************************************/

    /**************************************
     *
     * Methods
     *
     **************************************/

    /**************************************
     *
     * Override
     *
     **************************************/

    /**************************************
     *
     * Getter / Setter
     *
     **************************************/


    public String getPath() {
        return path;
    }

    public int getMsLength() {
        return msLength;
    }


}

