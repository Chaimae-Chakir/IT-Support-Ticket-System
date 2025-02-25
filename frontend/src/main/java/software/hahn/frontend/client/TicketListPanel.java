package software.hahn.frontend.client;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import software.hahn.frontend.client.model.Ticket;
import com.fasterxml.jackson.core.type.TypeReference;

public class TicketListPanel extends JPanel {
    private JTable ticketTable;
    private String username;
    private String password;

    public TicketListPanel(Frame parent, String username, String password, boolean allTickets) {
        this.username = username;
        this.password = password;
        setLayout(new BorderLayout());

        try {
            String path = allTickets ? "/tickets/all" : "/tickets/my";
            HttpResponse<String> response = HttpUtil.sendGetRequest(path, username, password);
            if (response.statusCode() == 200) {
                List<Ticket> tickets = HttpUtil.getMapper().readValue(response.body(), new TypeReference<>() {});
                String[] columns = {"ID", "Title", "Priority", "Category", "Status", "Creation Date"};
                Object[][] data = new Object[tickets.size()][6];
                for (int i = 0; i < tickets.size(); i++) {
                    Ticket t = tickets.get(i);
                    data[i] = new Object[]{t.getId(), t.getTitle(), t.getPriority(), t.getCategory(), t.getStatus(), t.getCreationDate()};
                }

                ticketTable = new JTable(data, columns);
                ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                ticketTable.getSelectionModel().addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting() && ticketTable.getSelectedRow() >= 0) {
                        // Get the value in the first column (ID), which could be a String
                        Object valueAt = ticketTable.getValueAt(ticketTable.getSelectedRow(), 0);
                        Long id = null;

                        if (valueAt != null) {
                            try {
                                // Convert the value to a Long (ensuring correct parsing)
                                id = Long.parseLong(valueAt.toString());
                                System.out.println("Opening details for ticket ID: " + id + " with Username: " + username);
                                new TicketDetailsDialog(parent, id, username, password, allTickets).setVisible(true);
                            } catch (NumberFormatException ex) {
                                ex.printStackTrace(); // Handle the case where conversion fails
                            }
                        }
                    }
                });

                add(new JScrollPane(ticketTable), BorderLayout.CENTER);
            } else {
                add(new JLabel("Failed to load tickets"), BorderLayout.CENTER);
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}