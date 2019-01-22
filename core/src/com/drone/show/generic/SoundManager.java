package com.drone.show.generic;


import java.util.HashMap;
import java.util.Map;

import com.drone.show.GlobalManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class SoundManager {

    public static String SOUND_EXTENSION = "mp3";
    /** Volume */
    public static float VOLUME_ON = 0.1f;
    public static float VOLUME_OFF = 0f;


    private boolean isSoundON;

    /** Music background */
    private Music backgroundMusic; //Music if size is > 1Mo but small pause with the loop with Music (not with sound)

    /** Sounds */
    private Map<SoundType, Sound> soundsType;

    /** Volume */
    private float volume;



    /**************************************
     *
     * Constructors
     *
     **************************************/

    public SoundManager(){

    	if (GlobalManager.ISDEBUG) System.out.println("Load SoundManager...");

        this.backgroundMusic = null;

        this.soundsType = new HashMap<SoundType, Sound>();

        this.soundOFF(); /** Turn Off */

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

    public void createAllSounds(){

        for (SoundType soundType : SoundType.values()) {
            Sound sound = GlobalManager.assestsLoader.getAssetManager().get(soundType.getPath(), Sound.class);
            soundsType.put(soundType, sound);
        }

    }


    public void soundON() {

        /** Set ON */
        this.isSoundON = true;
        this.volume = SoundManager.VOLUME_ON;

        if (this.backgroundMusic != null) {
            this.backgroundMusic.play();
            this.backgroundMusic.setVolume(this.volume);
        }
    }


    public void soundOFF(){

        /** Set OFF */
        this.isSoundON = false;
        this.volume = SoundManager.VOLUME_OFF;

        if(this.backgroundMusic != null){
            this.backgroundMusic.pause();
            this.backgroundMusic.setVolume(this.volume);
        }

    }


    public void launchMusicBackground(){
        /** Start Sound Background */
        this.backgroundMusic = GlobalManager.assestsLoader.getAssetManager().get("musics/puzzle-game.mp3", Music.class);
        this.backgroundMusic.setVolume(this.volume);
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.play();
    }

    public void killMusicBackground(){
        /** kill Sound Background */
        if(this.backgroundMusic != null) {
            this.backgroundMusic.stop();
            this.backgroundMusic = null;
        }
    }


    public void playSound(final SoundType soundType){

        if(this.isSoundON) {
            Sound sound = this.soundsType.get(soundType);
            Long id = sound.play(this.volume);
        }

    }


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



}
