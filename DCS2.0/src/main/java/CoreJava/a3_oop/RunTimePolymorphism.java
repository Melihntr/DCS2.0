package CoreJava.a3_oop;

class Hayvan {
    public void ses() {
        System.out.println("Hayvan ses ediyor");
    }
}

class Köpek extends Hayvan {
    @Override
    public void ses() {
        System.out.println("Köpek havlıyor");
    }
}

class Kedi extends Hayvan {
    @Override
    public void ses() {
        System.out.println("Kedi miyavlıyor");
    }
}

class Main {
    public static void main(String[] args) {
        Hayvan hayvan = new Köpek();
        hayvan.ses(); // Köpek havlıyor

        hayvan = new Kedi();
        hayvan.ses(); // Kedi miyavlıyor
    }
}

