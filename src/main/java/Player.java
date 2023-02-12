import dto.DtoUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable{

  private static final String CLOSE_CONNECTION = "close_connection";

  private final Socket socket;
  private final Database database;
  private String username;

  public Player(final Socket socket, final Database database) {
    this.socket = socket;
    this.database = database;
  }

  public void run() {
    try (socket) {
      var receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      var sender = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
      while (true) {
        long start = System.currentTimeMillis();
        var command = receiver.readLine();
        if (CLOSE_CONNECTION.equalsIgnoreCase(command)) {
          final var msg = "Closing connection";
          sender.println(msg);
          sender.flush();
          break;
        }
        var playerDTO = DtoUtils.parseRequest(command);
        if (username == null) {
          this.username = playerDTO.getUsername();
        }
        System.out.println(
            String.format(
                "Received from [%s], tr id [%s], BU [%d]", playerDTO.getUsername(), playerDTO.getBalanceChange()
            )
        );
        database.insetOrUpdateBalance(playerDTO);
        sender.println(DtoUtils.createResponse(playerDTO));
        sender.flush();
        System.out.println(
            String.format(
                "Sent to [%s], tr. id [%s], BU [%d] ErrorCode [%s]",
                playerDTO.getUsername(),
                playerDTO.getTransactionId(),
                playerDTO.getBalanceChange(),
                playerDTO.getErrorCode()
            )
        );
        Server.statistics.addBalanceUpdate(System.currentTimeMillis() - start);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      Thread.currentThread().interrupt();
    }
  }

  public String getUsername() {
    return username;
  }

}
