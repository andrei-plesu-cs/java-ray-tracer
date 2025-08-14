package org.andreip.utils;

import java.util.concurrent.atomic.*;

public class ProgressIndicator extends Thread {
    private final int totalRows;
    private AtomicInteger progress = new AtomicInteger(0);

    public ProgressIndicator(int totalRows) {
        this.totalRows = totalRows;
    }

    public void update(int progress) {
        this.progress.set(progress);
    }

    public void increment() {
        progress.incrementAndGet();
    }

    @Override public void run() {
        do {
            try {
                Thread.sleep(2_000);
                System.out.println("%d/%d rows left to render".formatted(totalRows - progress.get(), totalRows));
            } catch (InterruptedException ununsed) {}
        } while (true);
    }
}