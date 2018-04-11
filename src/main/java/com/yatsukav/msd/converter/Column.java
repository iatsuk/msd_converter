package com.yatsukav.msd.converter;

import ncsa.hdf.object.h5.H5File;

import java.util.Arrays;

public enum Column {
    ARTIST_NAME, ARTIST_HOTNESS, ARTIST_ID, ARTIST_LOCATION, ARTIST_LATITUDE, ARTIST_LONGITUDE, ARTIST_TERMS,
    ARTIST_TAGS, YEAR, RELEASE, TITLE, SONG_HOTNESS, DURATION, END_OF_FADE_IN, LOUDNESS, MODE, MODE_CONFIDENCE,
    START_OF_FADE_OUT, TEMPO, TIME_SIGNATURE, TIME_SIGNATURE_CONFIDENCE;

    public Object getData(H5File h5, int songIdx) {
        try {
            switch (this) {
                case ARTIST_NAME:
                    return Hdf5Getters.get_artist_name(h5, songIdx);
                case ARTIST_HOTNESS:
                    return Hdf5Getters.get_artist_hotttnesss(h5, songIdx);
                case ARTIST_ID:
                    return Hdf5Getters.get_artist_id(h5, songIdx);
                case ARTIST_LOCATION:
                    return Hdf5Getters.get_artist_location(h5, songIdx);
                case ARTIST_LATITUDE:
                    return Hdf5Getters.get_artist_latitude(h5, songIdx);
                case ARTIST_LONGITUDE:
                    return Hdf5Getters.get_artist_longitude(h5, songIdx);
                case ARTIST_TERMS:
                    return "\"" + Arrays.toString(Hdf5Getters.get_artist_terms(h5, songIdx)) + "\"";
                case ARTIST_TAGS:
                    return "\"" + Arrays.toString(Hdf5Getters.get_artist_mbtags(h5, songIdx)) + "\"";
                case YEAR:
                    return Hdf5Getters.get_year(h5, songIdx);
                case RELEASE:
                    return Hdf5Getters.get_release(h5, songIdx);
                case TITLE:
                    return Hdf5Getters.get_title(h5, songIdx);
                case SONG_HOTNESS:
                    return Hdf5Getters.get_song_hotttnesss(h5, songIdx);
                case DURATION:
                    return Hdf5Getters.get_duration(h5, songIdx);
                case END_OF_FADE_IN:
                    return Hdf5Getters.get_end_of_fade_in(h5, songIdx);
                case LOUDNESS:
                    return Hdf5Getters.get_loudness(h5, songIdx);
                case MODE:
                    return Hdf5Getters.get_mode(h5, songIdx) == 0 ? "major" : "minor";
                case MODE_CONFIDENCE:
                    return Hdf5Getters.get_mode_confidence(h5, songIdx);
                case START_OF_FADE_OUT:
                    return Hdf5Getters.get_start_of_fade_out(h5, songIdx);
                case TEMPO:
                    return Hdf5Getters.get_tempo(h5, songIdx);
                case TIME_SIGNATURE:
                    return Hdf5Getters.get_time_signature(h5, songIdx);
                case TIME_SIGNATURE_CONFIDENCE:
                    return Hdf5Getters.get_time_signature_confidence(h5, songIdx);
                default:
                    throw new IllegalStateException("There is no implementation for " + this);
            }
        } catch (Throwable throwable) {
            return null;
        }
    }
}
