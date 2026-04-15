// Alt sistemler
class TV {
    public void on() { System.out.println("TV açılıyor..."); }
    public void off() { System.out.println("TV kapatılıyor..."); }
}

class SoundSystem {
    public void on() { System.out.println("Ses sistemi açılıyor..."); }
    public void off() { System.out.println("Ses sistemi kapatılıyor..."); }
}

// Facade sınıfı
class HomeTheaterFacade {
    private TV tv;
    private SoundSystem soundSystem;

    public HomeTheaterFacade() {
        this.tv = new TV();
        this.soundSystem = new SoundSystem();
    }

    public void watchMovie() {
        tv.on();
        soundSystem.on();
    }

    public void stopMovie() {
        tv.off();
        soundSystem.off();
    }
}

// Kullanım
public class FacadePatternDemo {
    public static void main(String[] args) {
        HomeTheaterFacade facade = new HomeTheaterFacade();
        facade.watchMovie();
        facade.stopMovie();
    }
}