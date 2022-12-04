import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    private String textString;
    public static int valueMax;

    public MyCallable(String text) {
        int maxSize = 0;
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < text.length(); j++) {
                if (i >= j) {
                    continue;
                }
                boolean bFound = false;
                for (int k = i; k < j; k++) {
                    if (text.charAt(k) == 'b') {
                        bFound = true;
                        break;
                    }
                }
                if (!bFound && maxSize < j - i) {
                    maxSize = j - i;
                }
            }
        }
        this.textString = text.substring(0, 100) + " -> " + maxSize;
        if (maxSize>valueMax) {
            valueMax = maxSize;
        }
    }

    @Override
    public String call() throws Exception {
        return textString;
    }
}
