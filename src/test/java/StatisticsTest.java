import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StatisticsTest {

  @Test
  void statisticsTest() {
    var statistics = new Statistics();

    Assertions.assertEquals(0, statistics.balanceUpdates);
    Assertions.assertEquals(0, statistics.times);
    Assertions.assertEquals(0, statistics.minTime);
    Assertions.assertEquals(0, statistics.maxTime);
    Assertions.assertEquals(0, statistics.getAverageTime());

    statistics.addBalanceUpdate(110L);
    statistics.addBalanceUpdate(155L);
    statistics.addBalanceUpdate(22L);
    statistics.addBalanceUpdate(1100L);

    Assertions.assertEquals(
        "Total balance updates [4] min.time [110] max.time[1100] avg.time [   1.14628]",
        statistics.currentStatistics());

  }

}