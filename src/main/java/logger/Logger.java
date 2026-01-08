package logger;

import java.time.LocalDateTime;


public final class Logger {

    private static volatile Logger instance;

    private Logger() {}

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

    public void info(String message) {
        System.out.println(format("INFO", message));
    }

    public void error(String message) {
        System.err.println(format("ERROR", message));
    }

    private String format(String level, String message) {
        return "[" + level + "] " + LocalDateTime.now() + " - " + message;
    }
}
