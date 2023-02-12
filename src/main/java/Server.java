import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class Server {

  static final Statistics statistics = new Statistics();

  public static void main(String[] args) throws IOException {
     new Server().startServer();
  }

  private void startServer() throws IOException {
    var database = new Database();
    var server = new ServerSocket(Config.serverPort);
    runStatisticsMonitor();
    try (var executor= Executors.newVirtualThreadPerTaskExecutor()) {
      while (true) {
        Socket socket = server.accept();
        executor.execute(() -> new Player(socket, database));
      }
    }
  }

  private void runStatisticsMonitor() {
    var statisticsTimer = new Timer();
    statisticsTimer.schedule( new TimerTask() {
      public void run() {
        System.out.println(statistics.currentStatistics());
      }
    }, Config.statisticsPrintDelay, Config.statisticsPrintDelay);
  }

}
