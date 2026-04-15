// Hedef arayüz
interface MediaPlayer {
    void play(String audioType, String fileName);
}

// Uyumlu olmayan sınıf
class LegacyAudioPlayer {
    public void playMP3(String fileName) {
        System.out.println("MP3 dosyası oynatılıyor: " + fileName);
    }
}

// Uyumlayıcı sınıf
class MediaAdapter implements MediaPlayer {
    private LegacyAudioPlayer legacyPlayer;

    public MediaAdapter() {
        this.legacyPlayer = new LegacyAudioPlayer();
    }

    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            legacyPlayer.playMP3(fileName);
        } else {
            System.out.println("Bu format desteklenmiyor.");
        }
    }
}

// Kullanım
public class AdapterPatternDemo {
    public static void main(String[] args) {
        MediaPlayer player = new MediaAdapter();
        player.play("mp3", "song.mp3");
    }
}
