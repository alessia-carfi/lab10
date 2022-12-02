package it.unibo.oop.lab.streams;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 */
public final class MusicGroupImpl implements MusicGroup {

    private final Map<String, Integer> albums = new HashMap<>();
    private final Set<Song> songs = new HashSet<>();

    @Override
    public void addAlbum(final String albumName, final int year) {
        this.albums.put(albumName, year);
    }

    @Override
    public void addSong(final String songName, final Optional<String> albumName, final double duration) {
        if (albumName.isPresent() && !this.albums.containsKey(albumName.get())) {
            throw new IllegalArgumentException("invalid album name");
        }
        this.songs.add(new MusicGroupImpl.Song(songName, albumName, duration));
    }

    @Override
    public Stream<String> orderedSongNames() {
        return this.songs.stream()
                .map(song -> song.getSongName())
                .sorted();
    }

    @Override
    public Stream<String> albumNames() {
        return albums.keySet().stream();
    }

    @Override
    public Stream<String> albumInYear(final int year) {
        return albums.keySet()
                .stream()
                .filter(album -> albums.get(album) == year);
    }

    @Override
    public int countSongs(final String albumName) {
        return (int) this.songsInAlbum(albumName).count();
    }

    @Override
    public int countSongsInNoAlbum() {
        return (int) songs.stream()
                .filter(song -> !song.getAlbumName().isPresent())
                .count();
    }

    @Override
    public OptionalDouble averageDurationOfSongs(final String albumName) {
        return OptionalDouble.of(
                this.songsInAlbum(albumName)
                        .map(song -> song.getDuration())
                        .reduce((d1, d2) -> d1 + d2)
                        .get() / countSongs(albumName));
    }

    @Override
    public Optional<String> longestSong() {
        return Optional.of(songs.stream()
                .max((song1, song2) -> Double.compare(song1.getDuration(), song2.getDuration()))
                .get()
                .getSongName());
    }

    @Override
    public Optional<String> longestAlbum() {
        final Map<String, Double> allAlbums = new HashMap<>();
        songs.stream()
                .filter(song -> song.getAlbumName().isPresent())
                .forEach(song -> {
                    final String songName = song.getAlbumName().get();
                    if (allAlbums.containsKey(songName)) {
                        allAlbums.put(songName, allAlbums.get(songName) + song.getDuration());
                    } else {
                        allAlbums.put(songName, song.getDuration());
                    }
                });

        return Optional.of(allAlbums.entrySet()
                .stream()
                .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .get()
                .getKey());
    }

    private Stream<Song> songsInAlbum(final String albumName) {
        return songs.stream()
                .filter(song -> song.getAlbumName().isPresent() && song.getAlbumName().get().equals(albumName));
    }

    private static final class Song {

        private final String songName;
        private final Optional<String> albumName;
        private final double duration;
        private int hash;

        Song(final String name, final Optional<String> album, final double len) {
            super();
            this.songName = name;
            this.albumName = album;
            this.duration = len;
        }

        public String getSongName() {
            return songName;
        }

        public Optional<String> getAlbumName() {
            return albumName;
        }

        public double getDuration() {
            return duration;
        }

        @Override
        public int hashCode() {
            if (hash == 0) {
                hash = songName.hashCode() ^ albumName.hashCode() ^ Double.hashCode(duration);
            }
            return hash;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Song) {
                final Song other = (Song) obj;
                return albumName.equals(other.albumName) && songName.equals(other.songName)
                        && duration == other.duration;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Song [songName=" + songName + ", albumName=" + albumName + ", duration=" + duration + "]";
        }

    }

}
