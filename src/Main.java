import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        final ExecutorService threadPool = Executors.newFixedThreadPool(texts.length);
        List<Future<String>> futures = new ArrayList<>(texts.length);

        long startTs = System.currentTimeMillis(); // start time
        for (String text : texts) {
            Callable<String> myCallable = new MyCallable(text);
            final Future<String> future = threadPool.submit(myCallable);
            futures.add(future);
        }

        for (Future<String> future : futures) {
            final String result = future.get();
            String valueFromText = result.substring(result.lastIndexOf("-> ") + 3);
            int valueText = Integer.parseInt(valueFromText);
            if (valueText==MyCallable.valueMax) {
                System.out.println(result);
//                return; // если нужен только один вариант строки с максимальным интервалом значений
            }
        }

//        System.out.println(MyCallable.valueMax); // можно напрямую получить Максимальный интервал значений

        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
        threadPool.shutdown();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}