import java.util.concurrent.locks.ReentrantLock;

public class Statistics {

   private final static String STATISTICS_FORMAT = "Total balance updates [%d] min.time [%d] max.time[%d] avg.time [%10.5f]";

   private ReentrantLock reentrantLock = new ReentrantLock();

   volatile long balanceUpdates = 0;
   volatile long times = 0;
   volatile long minTime;
   volatile long maxTime;

  public void addBalanceUpdate(long time) {
    reentrantLock.lock();
    try {
      balanceUpdates++;
      times += time;
      minTime = minTime == 0 ? time : minTime;
      maxTime = time > maxTime ? time : maxTime;
    } finally {
      reentrantLock.unlock();
    }
  }

  public double getAverageTime() {
    return times != 0 ? times / (double) (minTime + maxTime) : 0;
  }

  public String currentStatistics() {
      return String.format(STATISTICS_FORMAT, balanceUpdates, minTime, maxTime, getAverageTime());
  }

}
