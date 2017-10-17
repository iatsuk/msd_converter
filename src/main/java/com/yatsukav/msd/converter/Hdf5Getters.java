package com.yatsukav.msd.converter;

/*
Thierry Bertin-Mahieux (2010) Columbia University
tb2332@columbia.edu


This code contains a set of getters functions to access the fields
from an HDF5 song file (regular file with one song or summary file
with many songs) in Java.
The goal is to reproduce the Python getters behaviour.
Our aim is only to show how to use the HDF5 files with Java, the
code is not optimized at all!

NOTE ON 2D ARRAYS: pitches and timbre are supposed to be #segs x 12
They are stored in 1D array by concatenating rows, e.g. elem 20
should be row 1 elem 8.
To get element of row r and column c from an array a, call a[r*12+c]

To get a faster code, you should load metadata/songs and analysis/songs
only once, see the Matlab code in /MatlabSrc for inspiration.

This is part of the Million Song Dataset project from
LabROSA (Columbia University) and The Echo Nest.


Copyright 2010, Thierry Bertin-Mahieux

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.object.h5.H5CompoundDS;
import ncsa.hdf.object.h5.H5File;
import ncsa.hdf.object.h5.H5ScalarDS;

import java.util.Vector;


/**
 * Interface containing static methods to open a HDF5 song file and access its content.
 */
public interface Hdf5Getters {

    /**
     * Opens an existing HDF5 file with read only access.
     */
    static H5File hdf5_open_readonly(String filename) {
        return new H5File(filename, H5File.READ);
    }

    /**
     * Closes the HDF5 file.
     * Function usefull only because of the try/catch
     */
    static void hdf5_close(H5File h5) {
        try {
            h5.close();
        } catch (HDF5Exception ex) {
            System.out.println("Could not close HDF5 file?");
            ex.printStackTrace();
        }
    }

    static int get_num_songs(H5File h5) {
        H5CompoundDS metadata;
        try {
            metadata = (H5CompoundDS) h5.get("/metadata/songs");
            metadata.init();
            return metadata.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    static double get_artist_familiarity(H5File h5) throws Exception {
        return get_artist_familiarity(h5, 0);
    }

    static double get_artist_familiarity(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/metadata/songs", "artist_familiarity");
    }

    static double get_artist_hotttnesss(H5File h5) throws Exception {
        return get_artist_hotttnesss(h5, 0);
    }

    static double get_artist_hotttnesss(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/metadata/songs", "artist_hotttnesss");
    }

    static String get_artist_id(H5File h5) throws Exception {
        return get_artist_id(h5, 0);
    }

    static String get_artist_id(H5File h5, int songidx) throws Exception {
        return get_member_string(h5, songidx, "/metadata/songs", "artist_id");
    }

    static String get_artist_mbid(H5File h5) throws Exception {
        return get_artist_mbid(h5, 0);
    }

    static String get_artist_mbid(H5File h5, int songidx) throws Exception {
        return get_member_string(h5, songidx, "/metadata/songs", "artist_mbid");
    }

    static int get_artist_playmeid(H5File h5) throws Exception {
        return get_artist_playmeid(h5, 0);
    }

    static int get_artist_playmeid(H5File h5, int songidx) throws Exception {
        return get_member_int(h5, songidx, "/metadata/songs", "artist_playmeid");
    }

    static int get_artist_7digitalid(H5File h5) throws Exception {
        return get_artist_7digitalid(h5, 0);
    }

    static int get_artist_7digitalid(H5File h5, int songidx) throws Exception {
        return get_member_int(h5, songidx, "/metadata/songs", "artist_7digitalid");
    }

    static double get_artist_latitude(H5File h5) throws Exception {
        return get_artist_latitude(h5, 0);
    }

    static double get_artist_latitude(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/metadata/songs", "artist_latitude");
    }

    static double get_artist_longitude(H5File h5) throws Exception {
        return get_artist_longitude(h5, 0);
    }

    static double get_artist_longitude(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/metadata/songs", "artist_longitude");
    }

    static String get_artist_location(H5File h5) throws Exception {
        return get_artist_location(h5, 0);
    }

    static String get_artist_location(H5File h5, int songidx) throws Exception {
        return get_member_string(h5, songidx, "/metadata/songs", "artist_location");
    }

    static String get_artist_name(H5File h5) throws Exception {
        return get_artist_name(h5, 0);
    }

    static String get_artist_name(H5File h5, int songidx) throws Exception {
        return get_member_string(h5, songidx, "/metadata/songs", "artist_name");
    }

    static String get_release(H5File h5) throws Exception {
        return get_release(h5, 0);
    }

    static String get_release(H5File h5, int songidx) throws Exception {
        return get_member_string(h5, songidx, "/metadata/songs", "release");
    }

    static int get_release_7digitalid(H5File h5) throws Exception {
        return get_release_7digitalid(h5, 0);
    }

    static int get_release_7digitalid(H5File h5, int songidx) throws Exception {
        return get_member_int(h5, songidx, "/metadata/songs", "release_7digitalid");
    }

    static String get_song_id(H5File h5) throws Exception {
        return get_song_id(h5, 0);
    }

    static String get_song_id(H5File h5, int songidx) throws Exception {
        return get_member_string(h5, songidx, "/metadata/songs", "song_id");
    }

    static double get_song_hotttnesss(H5File h5) throws Exception {
        return get_song_hotttnesss(h5, 0);
    }

    static double get_song_hotttnesss(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/metadata/songs", "song_hotttnesss");
    }

    static String get_title(H5File h5) throws Exception {
        return get_title(h5, 0);
    }

    static String get_title(H5File h5, int songidx) throws Exception {
        return get_member_string(h5, songidx, "/metadata/songs", "title");
    }

    static int get_track_7digitalid(H5File h5) throws Exception {
        return get_track_7digitalid(h5, 0);
    }

    static int get_track_7digitalid(H5File h5, int songidx) throws Exception {
        return get_member_int(h5, songidx, "/metadata/songs", "track_7digitalid");
    }

    static String[] get_similar_artists(H5File h5) throws Exception {
        return get_similar_artists(h5, 0);
    }

    static String[] get_similar_artists(H5File h5, int songidx) throws Exception {
        return get_array_string(h5, songidx, "metadata", "similar_artists");
    }

    static String[] get_artist_terms(H5File h5) throws Exception {
        return get_artist_terms(h5, 0);
    }

    static String[] get_artist_terms(H5File h5, int songidx) throws Exception {
        return get_array_string(h5, songidx, "metadata", "artist_terms");
    }

    static double[] get_artist_terms_freq(H5File h5) throws Exception {
        return get_artist_terms_freq(h5, 0);
    }

    static double[] get_artist_terms_freq(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "metadata", "artist_terms_freq", 1, "idx_artist_terms");
    }

    static double[] get_artist_terms_weight(H5File h5) throws Exception {
        return get_artist_terms_weight(h5, 0);
    }

    static double[] get_artist_terms_weight(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "metadata", "artist_terms_weight", 1, "idx_artist_terms");
    }

    static double get_analysis_sample_rate(H5File h5) throws Exception {
        return get_analysis_sample_rate(h5, 0);
    }

    static double get_analysis_sample_rate(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "analysis_sample_rate");
    }

    static String get_audio_md5(H5File h5) throws Exception {
        return get_audio_md5(h5, 0);
    }

    static String get_audio_md5(H5File h5, int songidx) throws Exception {
        return get_member_string(h5, songidx, "/analysis/songs", "audio_md5");
    }

    static double get_danceability(H5File h5) throws Exception {
        return get_danceability(h5, 0);
    }

    static double get_danceability(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "danceability");
    }

    static double get_duration(H5File h5) throws Exception {
        return get_duration(h5, 0);
    }

    static double get_duration(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "duration");
    }

    static double get_end_of_fade_in(H5File h5) throws Exception {
        return get_end_of_fade_in(h5, 0);
    }

    static double get_end_of_fade_in(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "end_of_fade_in");
    }

    static double get_energy(H5File h5) throws Exception {
        return get_energy(h5, 0);
    }

    static double get_energy(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "energy");
    }

    static int get_key(H5File h5) throws Exception {
        return get_key(h5, 0);
    }

    static int get_key(H5File h5, int songidx) throws Exception {
        return get_member_int(h5, songidx, "/analysis/songs", "key");
    }

    static double get_key_confidence(H5File h5) throws Exception {
        return get_key_confidence(h5, 0);
    }

    static double get_key_confidence(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "key_confidence");
    }

    static double get_loudness(H5File h5) throws Exception {
        return get_loudness(h5, 0);
    }

    static double get_loudness(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "loudness");
    }

    static int get_mode(H5File h5) throws Exception {
        return get_mode(h5, 0);
    }

    static int get_mode(H5File h5, int songidx) throws Exception {
        return get_member_int(h5, songidx, "/analysis/songs", "mode");
    }

    static double get_mode_confidence(H5File h5) throws Exception {
        return get_mode_confidence(h5, 0);
    }

    static double get_mode_confidence(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "mode_confidence");
    }

    static double get_start_of_fade_out(H5File h5) throws Exception {
        return get_start_of_fade_out(h5, 0);
    }

    static double get_start_of_fade_out(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "start_of_fade_out");
    }

    static double get_tempo(H5File h5) throws Exception {
        return get_tempo(h5, 0);
    }

    static double get_tempo(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "tempo");
    }

    static int get_time_signature(H5File h5) throws Exception {
        return get_time_signature(h5, 0);
    }

    static int get_time_signature(H5File h5, int songidx) throws Exception {
        return get_member_int(h5, songidx, "/analysis/songs", "time_signature");
    }

    static double get_time_signature_confidence(H5File h5) throws Exception {
        return get_time_signature_confidence(h5, 0);
    }

    static double get_time_signature_confidence(H5File h5, int songidx) throws Exception {
        return get_member_double(h5, songidx, "/analysis/songs", "time_signature_confidence");
    }

    static String get_track_id(H5File h5) throws Exception {
        return get_track_id(h5, 0);
    }

    static String get_track_id(H5File h5, int songidx) throws Exception {
        return get_member_string(h5, songidx, "/analysis/songs", "track_id");
    }

    static double[] get_segments_start(H5File h5) throws Exception {
        return get_segments_start(h5, 0);
    }

    static double[] get_segments_start(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "segments_start", 1);
    }

    static double[] get_segments_confidence(H5File h5) throws Exception {
        return get_segments_confidence(h5, 0);
    }

    static double[] get_segments_confidence(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "segments_confidence", 1);
    }

    static double[] get_segments_pitches(H5File h5) throws Exception {
        return get_segments_pitches(h5, 0);
    }

    static double[] get_segments_pitches(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "segments_pitches", 2);
    }

    static double[] get_segments_timbre(H5File h5) throws Exception {
        return get_segments_timbre(h5, 0);
    }

    static double[] get_segments_timbre(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "segments_timbre", 2);
    }

    static double[] get_segments_loudness_max(H5File h5) throws Exception {
        return get_segments_loudness_max(h5, 0);
    }

    static double[] get_segments_loudness_max(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "segments_loudness_max", 1);
    }

    static double[] get_segments_loudness_max_time(H5File h5) throws Exception {
        return get_segments_loudness_max_time(h5, 0);
    }

    static double[] get_segments_loudness_max_time(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "segments_loudness_max_time", 1);
    }

    static double[] get_segments_loudness_start(H5File h5) throws Exception {
        return get_segments_loudness_start(h5, 0);
    }

    static double[] get_segments_loudness_start(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "segments_loudness_start", 1);
    }

    static double[] get_sections_start(H5File h5) throws Exception {
        return get_sections_start(h5, 0);
    }

    static double[] get_sections_start(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "sections_start", 1);
    }

    static double[] get_sections_confidence(H5File h5) throws Exception {
        return get_sections_confidence(h5, 0);
    }

    static double[] get_sections_confidence(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "sections_confidence", 1);
    }

    static double[] get_beats_start(H5File h5) throws Exception {
        return get_beats_start(h5, 0);
    }

    static double[] get_beats_start(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "beats_start", 1);
    }

    static double[] get_beats_confidence(H5File h5) throws Exception {
        return get_beats_confidence(h5, 0);
    }

    static double[] get_beats_confidence(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "beats_confidence", 1);
    }

    static double[] get_bars_start(H5File h5) throws Exception {
        return get_bars_start(h5, 0);
    }

    static double[] get_bars_start(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "bars_start", 1);
    }

    static double[] get_bars_confidence(H5File h5) throws Exception {
        return get_bars_confidence(h5, 0);
    }

    static double[] get_bars_confidence(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "bars_confidence", 1);
    }

    static double[] get_tatums_start(H5File h5) throws Exception {
        return get_tatums_start(h5, 0);
    }

    static double[] get_tatums_start(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "tatums_start", 1);
    }

    static double[] get_tatums_confidence(H5File h5) throws Exception {
        return get_tatums_confidence(h5, 0);
    }

    static double[] get_tatums_confidence(H5File h5, int songidx) throws Exception {
        return get_array_double(h5, songidx, "analysis", "tatums_confidence", 1);
    }

    static int get_year(H5File h5) throws Exception {
        return get_year(h5, 0);
    }

    static int get_year(H5File h5, int songidx) throws Exception {
        return get_member_int(h5, songidx, "/musicbrainz/songs", "year");
    }

    static String[] get_artist_mbtags(H5File h5) throws Exception {
        return get_artist_mbtags(h5, 0);
    }

    static String[] get_artist_mbtags(H5File h5, int songidx) throws Exception {
        return get_array_string(h5, songidx, "musicbrainz", "artist_mbtags");
    }

    static int[] get_artist_mbtags_count(H5File h5) throws Exception {
        return get_artist_mbtags_count(h5, 0);
    }

    static int[] get_artist_mbtags_count(H5File h5, int songidx) throws Exception {
        return get_array_int(h5, songidx, "musicbrainz", "artist_mbtags_count", "idx_artist_mbtags");
    }

    /*                                  UTILITY FUNCTIONS                                    */

    /**
     * Slow utility function.
     */
    static int find(String[] tab, String key) {
        for (int k = 0; k < tab.length; k++)
            if (tab[k].equals(key)) return k;
        return -1;
    }

    static int get_member_int(H5File h5, int songidx, String table, String member) throws Exception {
        H5CompoundDS analysis = (H5CompoundDS) h5.get(table);
        analysis.init();
        int wantedMember = find(analysis.getMemberNames(), member);
        assert (wantedMember >= 0);
        Vector alldata = (Vector) analysis.getData();
        int[] col = (int[]) alldata.get(wantedMember);
        return col[songidx];
    }

    static double get_member_double(H5File h5, int songidx, String table, String member) throws Exception {
        H5CompoundDS analysis = (H5CompoundDS) h5.get(table);
        analysis.init();
        int wantedMember = find(analysis.getMemberNames(), member);
        assert (wantedMember >= 0);
        Vector alldata = (Vector) analysis.getData();
        double[] col = (double[]) alldata.get(wantedMember);
        return col[songidx];
    }

    static String get_member_string(H5File h5, int songidx, String table, String member) throws Exception {
        H5CompoundDS analysis = (H5CompoundDS) h5.get(table);
        analysis.init();
        int wantedMember = find(analysis.getMemberNames(), member);
        assert (wantedMember >= 0);
        Vector alldata = (Vector) analysis.getData();
        String[] col = (String[]) alldata.get(wantedMember);
        return col[songidx];
    }

    static double[] get_array_double(H5File h5, int songidx, String group, String arrayname, int ndims) throws Exception {
        return get_array_double(h5, songidx, group, arrayname, ndims, "");
    }

    static double[] get_array_double(H5File h5, int songidx, String group, String arrayname, int ndims, String idxname) throws Exception {
        // index
        H5CompoundDS analysis = (H5CompoundDS) h5.get(group + "/songs");
        analysis.init();
        if (idxname.equals("")) idxname = "idx_" + arrayname;
        int wantedMember = find(analysis.getMemberNames(), idxname);
        assert (wantedMember >= 0);
        Vector alldata = (Vector) analysis.getData();
        int[] col = (int[]) alldata.get(wantedMember);
        int pos1 = col[songidx];
        // data
        H5ScalarDS array = (H5ScalarDS) h5.get("/" + group + "/" + arrayname);
        if (ndims == 1) {
            double[] data = (double[]) array.getData();
            int pos2 = data.length;
            if (songidx + 1 < col.length) pos2 = col[songidx + 1];
            // copy
            double[] res = new double[pos2 - pos1];
            System.arraycopy(data, pos1, res, 0, res.length);
            return res;
        } else if (ndims == 2) // multiply by 12
        {
            pos1 *= 12;
            double[] data = (double[]) array.getData();
            int pos2 = data.length;
            if (songidx + 1 < col.length) pos2 = col[songidx + 1] * 12;
            // copy
            double[] res = new double[pos2 - pos1];
            System.arraycopy(data, pos1, res, 0, res.length);
            return res;
        }
        // more than 2 dims?
        return null;
    }

    static int[] get_array_int(H5File h5, int songidx, String group, String arrayname) throws Exception {
        return get_array_int(h5, songidx, group, arrayname, "");
    }

    static int[] get_array_int(H5File h5, int songidx, String group, String arrayname, String idxname) throws Exception {
        // index
        H5CompoundDS analysis = (H5CompoundDS) h5.get(group + "/songs");
        analysis.init();
        if (idxname.equals("")) idxname = "idx_" + arrayname;
        int wantedMember = find(analysis.getMemberNames(), idxname);
        assert (wantedMember >= 0);
        Vector alldata = (Vector) analysis.getData();
        int[] col = (int[]) alldata.get(wantedMember);
        int pos1 = col[songidx];
        // data
        H5ScalarDS array = (H5ScalarDS) h5.get("/" + group + "/" + arrayname);
        int[] data = (int[]) array.getData();
        int pos2 = data.length;
        if (songidx + 1 < col.length) pos2 = col[songidx + 1];
        // copy
        int[] res = new int[pos2 - pos1];
        System.arraycopy(data, pos1, res, 0, res.length);
        return res;
    }

    static String[] get_array_string(H5File h5, int songidx, String group, String arrayname) throws Exception {
        // index
        H5CompoundDS analysis = (H5CompoundDS) h5.get(group + "/songs");
        analysis.init();
        int wantedMember = find(analysis.getMemberNames(), "idx_" + arrayname);
        assert (wantedMember >= 0);
        Vector alldata = (Vector) analysis.getData();
        int[] col = (int[]) alldata.get(wantedMember);
        int pos1 = col[songidx];
        // data
        H5ScalarDS array = (H5ScalarDS) h5.get("/" + group + "/" + arrayname);
        String[] data = (String[]) array.getData();
        int pos2 = data.length;
        if (songidx + 1 < col.length) pos2 = col[songidx + 1];
        // copy
        String[] res = new String[pos2 - pos1];
        System.arraycopy(data, pos1, res, 0, res.length);
        return res;
    }


    /****************************************** MAIN *****************************************/


    static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("file 'Hdf5Getters.java'");
            System.out.println("T. Bertin-Mahieux (2010) tb2332@columbia.edu");
            System.out.println("a util static class to read HDF5 song files from");
            System.out.println("the Million Songs Dataset project");
            System.out.println("demo:");
            System.out.println("   see README.txt to compile");
            System.out.println("   java Hdf5Getters <some HDF5 song file>");
            System.exit(0);
        }
        String filename = args[0];
        System.out.println("file: " + filename);
        H5File h5 = hdf5_open_readonly(filename);
        int nSongs = get_num_songs(h5);
        System.out.println("numberof songs: " + nSongs);
        if (nSongs > 1) System.out.println("we'll display infor for song 0");
        try {
            double[] res;
            String[] resS;
            int[] resI;
            // metadata
            System.out.println("artist familiarity: " + get_artist_familiarity(h5));
            System.out.println("artist hotttnesss: " + get_artist_hotttnesss(h5));
            System.out.println("artist id: " + get_artist_id(h5));
            System.out.println("artist mbid: " + get_artist_mbid(h5));
            System.out.println("artist playmeid: " + get_artist_playmeid(h5));
            System.out.println("artist 7digitalid: " + get_artist_7digitalid(h5));
            System.out.println("artist latitude: " + get_artist_latitude(h5));
            System.out.println("artist longitude: " + get_artist_longitude(h5));
            System.out.println("artist location: " + get_artist_location(h5));
            System.out.println("artist name: " + get_artist_name(h5));
            System.out.println("release: " + get_release(h5));
            System.out.println("release 7digitalid: " + get_release_7digitalid(h5));
            System.out.println("song hotttnesss: " + get_song_hotttnesss(h5));
            System.out.println("title: " + get_title(h5));
            System.out.println("track 7digitalid: " + get_track_7digitalid(h5));
            resS = get_similar_artists(h5);
            System.out.println("similar artists, length: " + resS.length + ", elem 2: " + resS[20]);
            resS = get_artist_terms(h5);
            System.out.println("artists terms, length: " + resS.length + ", elem 0: " + resS[0]);
            res = get_artist_terms_freq(h5);
            System.out.println("artists terms freq, length: " + res.length + ", elem 0: " + res[0]);
            res = get_artist_terms_weight(h5);
            System.out.println("artists terms weight, length: " + res.length + ", elem 0: " + res[0]);
            // analysis
            System.out.println("duration: " + get_duration(h5));
            System.out.println("end_of_fade_in: " + get_end_of_fade_in(h5));
            System.out.println("key: " + get_key(h5));
            System.out.println("key confidence: " + get_key_confidence(h5));
            System.out.println("loudness: " + get_loudness(h5));
            System.out.println("mode: " + get_mode(h5));
            System.out.println("mode confidence: " + get_mode_confidence(h5));
            System.out.println("start of fade out: " + get_start_of_fade_out(h5));
            System.out.println("tempo: " + get_tempo(h5));
            System.out.println("time signature: " + get_time_signature(h5));
            System.out.println("time signature confidence: " + get_time_signature_confidence(h5));
            res = get_segments_start(h5);
            System.out.println("segments start, length: " + res.length + ", elem 20: " + res[20]);
            res = get_segments_confidence(h5);
            System.out.println("segments confidence, length: " + res.length + ", elem 20: " + res[20]);
            res = get_segments_pitches(h5);
            System.out.println("segments pitches, length: " + res.length + ", elem 20: " + res[20]);
            res = get_segments_timbre(h5);
            System.out.println("segments timbre, length: " + res.length + ", elem 20: " + res[20]);
            res = get_segments_loudness_max(h5);
            System.out.println("segments loudness max, length: " + res.length + ", elem 20: " + res[20]);
            res = get_segments_loudness_max_time(h5);
            System.out.println("segments loudness max time, length: " + res.length + ", elem 20: " + res[20]);
            res = get_segments_loudness_start(h5);
            System.out.println("segments loudness start, length: " + res.length + ", elem 20: " + res[20]);
            res = get_sections_start(h5);
            System.out.println("sections start, length: " + res.length + ", elem 1: " + res[1]);
            res = get_sections_confidence(h5);
            System.out.println("sections confidence, length: " + res.length + ", elem 1: " + res[1]);
            res = get_beats_start(h5);
            System.out.println("beats start, length: " + res.length + ", elem 1: " + res[1]);
            res = get_beats_confidence(h5);
            System.out.println("beats confidence, length: " + res.length + ", elem 1: " + res[1]);
            res = get_bars_start(h5);
            System.out.println("bars start, length: " + res.length + ", elem 1: " + res[1]);
            res = get_bars_confidence(h5);
            System.out.println("bars confidence, length: " + res.length + ", elem 1: " + res[1]);
            res = get_tatums_start(h5);
            System.out.println("tatums start, length: " + res.length + ", elem 3: " + res[3]);
            res = get_tatums_confidence(h5);
            System.out.println("tatums confidence, length: " + res.length + ", elem 3: " + res[3]);
            // musicbrainz
            System.out.println("year: " + get_year(h5));
            resS = get_artist_mbtags(h5);
            resI = get_artist_mbtags_count(h5);
            if (resS.length > 0) {
                System.out.println("artists mbtags, length: " + resS.length + ", elem 0: " + resS[0]);
                System.out.println("artists mbtags count, length: " + resI.length + ", elem 0: " + resI[0]);
            } else {
                System.out.println("artists mbtags, length: " + resS.length);
                System.out.println("artists mbtags count, length: " + resI.length);
            }
        } catch (Exception e) {
            System.out.println("something went wrong:");
            e.printStackTrace();
        }
        hdf5_close(h5);
        System.out.println("done, file closed.");
    }

}