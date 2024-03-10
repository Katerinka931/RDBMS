package org.nosql.postgresql.dto;

import org.nosql.postgresql.entity.Contact;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ContactPojo {
    private long id;
    private String phone;
    private String email;

    public static ContactPojo fromEntity(Contact contact) {
        ContactPojo pojo = new ContactPojo();
        pojo.setId(contact.getId());
        pojo.setPhone(contact.getPhone());
        pojo.setEmail(contact.getEmail());
        return pojo;
    }

    public static Contact toEntity(ContactPojo pojo) {
        Contact contact = new Contact();
        contact.setId(pojo.getId());
        contact.setEmail(pojo.getEmail());
        contact.setPhone(pojo.getPhone());
        return contact;
    }

    public static List<ContactPojo> convertContactsToPojo(List<Contact> contacts) {
        List<ContactPojo> pojos = new ArrayList<>();
        for (Contact contact : contacts) {
            pojos.add(ContactPojo.fromEntity(contact));
        }
        return pojos;
    }
}
