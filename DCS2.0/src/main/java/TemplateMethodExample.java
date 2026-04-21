// Abstract class with template method
abstract class Game {
    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();

    public final void play() {
        initialize();
        startPlay();
        endPlay();
    }
}

// Concrete game classes
class Football extends Game {
    @Override
    void initialize() {
        System.out.println("Football game initialized!");
    }

    @Override
    void startPlay() {
        System.out.println("Football game started!");
    }

    @Override
    void endPlay() {
        System.out.println("Football game ended!");
    }
}

class Basketball extends Game {
    @Override
    void initialize() {
        System.out.println("Basketball game initialized!");
    }

    @Override
    void startPlay() {
        System.out.println("Basketball game started!");
    }

    @Override
    void endPlay() {
        System.out.println("Basketball game ended!");
    }
}

public class TemplateMethodExample {
    public static void main(String[] args) {
        // Create game objects
        Game football = new Football();
        Game basketball = new Basketball();

        // Play games
        football.play();
        basketball.play();
    }
}
