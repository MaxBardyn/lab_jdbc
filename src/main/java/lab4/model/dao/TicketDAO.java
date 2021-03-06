package lab4.model.dao;

import lab4.model.entity.Ticket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements GeneralDAO<Ticket> {


    private static final String GET_ALL = "SELECT * FROM bardyn_lab3.ticket";
    private static final String GET_ONE = "SELECT * FROM bardyn_lab3.ticket WHERE id=?";
    private static final String CREATE = "INSERT bardyn_lab3.ticket "
            + "(price, sector, place_number, event_id, ticket_office_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE bardyn_lab3.ticket"
            + " SET price=?, sector=?, place_number=?, event_id=?, ticket_office_id=? WHERE id=?";
    private static final String DELETE = "DELETE FROM bardyn_lab3.ticket WHERE id=?";


    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnector.getConnection().prepareStatement(GET_ALL)) {
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket(
                        resultSet.getInt("id"),
                        resultSet.getInt("price"),
                        resultSet.getString("sector"),
                        resultSet.getString("place_number"),
                        resultSet.getInt("event_id"),
                        resultSet.getInt("ticket_office_id")
                );
                tickets.add(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public Ticket findOne(Integer id) {
        Ticket ticket = null;
        try (PreparedStatement statement = DatabaseConnector.getConnection().prepareStatement(GET_ONE)) {

            statement.setInt(1, id);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ticket = new Ticket(
                        resultSet.getInt("id"),
                        resultSet.getInt("price"),
                        resultSet.getString("sector"),
                        resultSet.getString("place_number"),
                        resultSet.getInt("event_id"),
                        resultSet.getInt("ticket_office_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public void create(Ticket ticket) {

        try (PreparedStatement statement = DatabaseConnector.getConnection().prepareStatement(CREATE)) {

            statement.setInt(1, ticket.getPrice());
            statement.setString(2, String.valueOf(ticket.getSector()));
            statement.setString(3, String.valueOf(ticket.getPlaceNumber()));
            statement.setInt(4, ticket.getEventId());
            statement.setInt(5, ticket.getTicketOfficeId());

            statement.executeUpdate();
            System.out.println(statement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Integer id, Ticket ticket) {
        try (PreparedStatement statement = DatabaseConnector.getConnection().prepareStatement(UPDATE)) {

            statement.setInt(1, ticket.getPrice());
            statement.setString(2, String.valueOf(ticket.getSector()));
            statement.setString(3, String.valueOf(ticket.getPlaceNumber()));
            statement.setInt(4, ticket.getEventId());
            statement.setInt(5, ticket.getTicketOfficeId());
            statement.setInt(6, id);

            statement.executeUpdate();
            System.out.println(statement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try (PreparedStatement statement = DatabaseConnector.getConnection().prepareStatement(DELETE)) {
            statement.setInt(1, id);
            System.out.println(statement);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
