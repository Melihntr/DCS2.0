package SOLID;

/**
 * 2.3: LSP - Liskov'un Yerine Geçme Prensibi
 */
public class LiskovSubstitutionDemo {

    // İHLAL: Ostrich (Devekuşu) bir Bird'dür ama uçamaz. fly() hata fırlatır.
    class Bird { public void fly() { System.out.println("Uçuyor..."); } }
    
    class Ostrich extends Bird {
        @Override
        public void fly() { throw new UnsupportedOperationException("Devekuşları uçamaz!"); }
    }

    // UYGUN: Yetenekler arayüzlerle ayrılır.
    interface IFlyable { void fly(); }
    class Sparrow implements IFlyable { public void fly() { System.out.println("Serçe uçuyor."); } }
    class OstrichClean { /* Sadece kuş özelliklerini alır, fly() zorlanmaz */ }

    public static void main(String[] args) {
        System.out.println("LSP: Kalıtım hiyerarşisi mantıksal hatalardan arındırıldı.");
    }
}
