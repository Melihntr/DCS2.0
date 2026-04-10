public class SingletonDemo {

    public static void main(String[] args) {

        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("First log");
        logger2.log("Second log");

        System.out.println(logger1 == logger2); // true
    }
}

// Singleton Class
class Logger {

    private static volatile Logger instance;

    private Logger() {
        System.out.println("Logger created!");
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}
