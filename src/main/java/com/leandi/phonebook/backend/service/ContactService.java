package com.leandi.phonebook.backend.service;

import com.leandi.phonebook.backend.entity.Contact;
import com.leandi.phonebook.backend.repository.CompanyRepository;
import com.leandi.phonebook.backend.repository.ContactRepository;
import com.leandi.phonebook.backend.entity.Company;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactService {
    private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());
    private ContactRepository contactRepository;
    private CompanyRepository companyRepository;

    public ContactService(ContactRepository contactRepository,
                          CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public List<Contact> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return contactRepository.findAll();
        } else  {
            return  contactRepository.search(filterText);
        }
    }

    public long count() {
        return contactRepository.count();
    }

    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    public void save(Contact contact) {
        if (contact == null) {
            LOGGER.log(Level.SEVERE,
                "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    @PostConstruct
    public void populateTestData() {
        if (companyRepository.count() == 0) {
            companyRepository.saveAll(
                Stream.of("SLUŽBA ZA DIGITALNI RAZVOJ IN PISARNIŠKO POSLOVANJE", "URAD ZA PROSTORSKI RAZVOJ IN NEPREMIČNINE",
                        "SLUŽBA ZA INŠPEKCIJO, REDARSTVO, ZAŠČITO IN REŠEVANJE")
                    .map(Company::new)
                    .collect(Collectors.toList()));
        }

        if (contactRepository.count() == 0) {
            Random r = new Random(0);
            List<Company> companies = companyRepository.findAll();
            contactRepository.saveAll(
                Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                    "Sandi Kurtin", "Bogdan Stibilj", "Jadran Prodan", "Davor Deranja", "Emanuel Lipovac",
                    "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                    "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                    "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                    "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                    "Jaydan Jackson", "Bernard Nilsen")
                    .map(name -> {
                        String[] split = name.split(" ");
                        Contact contact = new Contact();
                        contact.setFirstName(split[0]);
                        contact.setLastName(split[1]);
                        contact.setCompany(companies.get(r.nextInt(companies.size())));
//                        contact.setStatus(Contact.Status.values()[r.nextInt(Contact.Status.values().length)]);
                        String phoneNumber = "6385";
                        String gsmNumber = "031 600 852";
                        contact.setPhoneNumber(phoneNumber);
                        contact.setGsmNumber(gsmNumber);
                        return contact;
                    }).collect(Collectors.toList()));
        }
    }
}
