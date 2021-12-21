package ru.ws.Homework.crm.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.ws.Homework.crm.model.Address;
import ru.ws.Homework.crm.model.Client;
import ru.ws.Homework.crm.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClientResultSetExtractorClass implements ResultSetExtractor<List<Client>> {

    @Override
    public List<Client> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var clientList = new ArrayList<Client>();
        Long prevClientId = null;
        Long prevPhoneId = null;
        Long clientId = null;
        Client client = null;
        while (rs.next()) {
            clientId = (Long) rs.getObject("client_id");
            if (prevClientId == null || !prevClientId.equals(clientId)) {
                Long addressId = (Long) rs.getObject("address_id");
                client = new Client(clientId,
                        rs.getString("client_name"),
                        new Address(addressId,
                                rs.getString("street")),
                        new HashSet<>());
                clientList.add(client);
                prevClientId = clientId;
            }
            Long phoneId = (Long) rs.getObject("phone_id");
            if (phoneId != null && (prevPhoneId == null || !prevPhoneId.equals(phoneId))) {
                client.getPhones().add(new Phone(phoneId, rs.getString("phone_number")));
                prevPhoneId = phoneId;
            }
        }
        return clientList;
    }
}