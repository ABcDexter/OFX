/**
 * *********************************************************************
 *
 * Copyright (C) 2014 Avaneesh Rastogi (rastogi.avaneesh (at) gmail (dot) com)
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see (http://www.gnu.org/licenses/).
 *
 **********************************************************************
 */
package Utilities;

import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Sound implements playing audio clips for in-game sound effects and handling
 * of Mute.
 *
 * @author Avaneesh Rastogi
 * @version 1.0
 */
public class Sound {

    /**
     * {@code true} if sounds off, otherwise {@code false}
     */
    private static boolean m_mute;

    /**
     * Return {@code true} if sounds are disabled, else {@code false}.
     *
     * @return {@code true} if sounds disabled, otherwise {@code false}
     */
    public static boolean isMute() {
        return m_mute;
    }
//-----------------------------------------------------------------------

    /**
     * Turn off all in-game sound effects.
     */
    public static void setMuteOn() {
        m_mute = true;
    }
//-----------------------------------------------------------------------

    /**
     * Turn on all in-game sound effects.
     */
    public static void setMuteOff() {
        m_mute = false;
    }
//-----------------------------------------------------------------------

    /**
     * Plays sound clip provided as argument via external device.
     * <br> <b>Note:</b> The method uses {@code AudioStream} and
     * {@code AudioPlayer} classes.
     * <br> {@code AudioStream} and {@code AudioPlayer} are internal proprietary
     * API and may be removed in a future release.
     *
     * @param clipPath relative path of sound clip
     */
    public static void playClip(String clipPath) {
        /* Return from method if sounds disabled */
        if (Sound.isMute()) {
            return;
        }

        try {
            ClassLoader CL = Sound.class.getClassLoader();
            InputStream inputStream = CL.getResourceAsStream(clipPath);
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        } catch (IOException e) {
            System.out.println("Class: Sound File: Sound.java -Error "
                    + "encountered in playing sound clip");
        }
    }
//-----------------------------------------------------------------------
}
