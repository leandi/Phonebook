package com.leandi.phonebook.ui.views.login;

import com.leandi.phonebook.backend.entity.Company;
import com.leandi.phonebook.backend.entity.Contact;
import com.leandi.phonebook.backend.service.CompanyService;
import com.leandi.phonebook.backend.service.ContactService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Route("login")
@PageTitle("Login | Vaadin CRM")
public class LoginView extends HorizontalLayout implements BeforeEnterObserver {

    LoginForm login = new LoginForm();
    Grid<Contact> grid = new Grid<>(Contact.class);
    private ContactService contactService;
    TextField filterText = new TextField();

    public LoginView(ContactService contactService,
                     CompanyService companyService) {


        this.contactService = contactService;

        addClassName("list-view");
        //addClassName("login-view");
        setSizeFull();
        configureGrid();
        updateList();
        //setJustifyContentMode(JustifyContentMode.CENTER);
       //setAlignItems(Alignment.CENTER);

        login.setAction("login");

        add(
            //new H1("Telefonski imenik MOK"),
            grid, login
        );
    }
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "phoneNumber");
//        grid.setColumns();
//        grid.addColumn(Contact::getFirstName).setHeader(new H1("imr"));
//        grid.addColumn(Contact::getLastName).setHeader("Priimek");

        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setSortable(true).setHeader("company");
// set sortable doda možnost sortiranja

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        //grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));
    }
    private void updateList() {
        grid.setItems(contactService.findAll(filterText.getValue()));
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(!beforeEnterEvent.getLocation()
        .getQueryParameters()
        .getParameters()
        .getOrDefault("error", Collections.emptyList())
        .isEmpty()) {
            login.setError(true);
        }
    }
}
