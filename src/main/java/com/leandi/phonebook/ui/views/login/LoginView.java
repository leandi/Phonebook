package com.leandi.phonebook.ui.views.login;

import com.leandi.phonebook.backend.entity.Company;
import com.leandi.phonebook.backend.entity.Contact;
import com.leandi.phonebook.backend.service.CompanyService;
import com.leandi.phonebook.backend.service.ContactService;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Route("login")
@PageTitle("Login | Vaadin CRM")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    LoginForm login = new LoginForm();
    Grid<Contact> grid = new Grid<>(Contact.class);
    private ContactService contactService;
    TextField filterText = new TextField();
    //TextField filterText2 = new TextField();
    H1 logo = new H1("Telefonski imenik MOK - login!!!");
    public LoginView(ContactService contactService,
                     CompanyService companyService) {


        this.contactService = contactService;

        addClassName("list-view");
        //addClassName("login-view");
        setSizeFull();
        configureGrid();

        //setJustifyContentMode(JustifyContentMode.CENTER);
        //setAlignItems(Alignment.CENTER);

        //login.setAction("login"); to pove springu da je security login in pol tu naprej
        //prevzame spring v roke zadevo.
        login.setAction("login");
        Div content = new Div(grid, login);
        content.addClassName("content");
        content.setSizeFull();
        Image image = new Image("/img/logomok.png", "MOK Grb");
        logo.addClassName("logo");

        //new H1("Telefonski imenik MOK"),
        add(
                image,
                logo,
                getToolBar(),
                content);
        updateList();

    }
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
//        grid.removeColumnByKey("company");
//        grid.setColumns("firstName", "lastName", "phoneNumber");
        grid.setColumns();
        grid.addColumn(Contact::getFirstName).setSortable(true).setHeader("Ime");
        grid.addColumn(Contact::getLastName).setSortable(true).setHeader("Priimek");
        grid.addColumn(Contact::getPhoneNumber).setSortable(true).setHeader("Telefon");
        grid.addColumn(Contact::getGsmNumber).setSortable(true).setHeader("Mobilni telefon");
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setSortable(true).setHeader("Urad ali služba");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        //grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));
    }
    private void updateList() {
        grid.setItems(contactService.findAll(filterText.getValue()));
    }
    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Iskanje po imenu...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
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