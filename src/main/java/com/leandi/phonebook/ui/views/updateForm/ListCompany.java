package com.leandi.phonebook.ui.views.updateForm;


import com.leandi.phonebook.backend.entity.Company;
import com.leandi.phonebook.backend.service.CompanyService;
import com.leandi.phonebook.ui.MainLayout;
import com.leandi.phonebook.ui.views.list.ContactForm;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "company", layout = MainLayout.class)
@PageTitle("Vnos Uradov ali služb")
public class ListCompany extends VerticalLayout {

    private final CompanyForm formCompany;
    Grid<Company> gridCompany = new Grid<>(Company.class);

    private CompanyService companyService;

    public ListCompany(CompanyService companyService) {
        this.companyService = companyService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        formCompany = new CompanyForm();
        formCompany.addListener(CompanyForm.SaveEvent.class, this::saveCompany);
        formCompany.addListener(CompanyForm.CloseEvent.class, e -> closeEditor());
        formCompany.addListener(CompanyForm.DeleteEvent.class, this::deleteCompany);

        Div content = new Div(gridCompany, formCompany);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void deleteCompany(CompanyForm.DeleteEvent evt) {
        companyService.delete(evt.getCompany());
        updateList();
        closeEditor();
    }

    private  void saveCompany(CompanyForm.SaveEvent evt) {
        companyService.save(evt.getCompany());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addCompanyButton = new Button("Dodaj nov urad ali službo", click -> addCompany());

        HorizontalLayout toolbar = new HorizontalLayout(addCompanyButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void deleteContact(CompanyForm.DeleteEvent evt) {
        companyService.delete(evt.getCompany());
        updateList();
        closeEditor();
    }

    private void addCompany() {
        gridCompany.asSingleSelect().clear();
        editCompany(new Company());
    }


    private void configureGrid() {
        gridCompany.setSizeFull();
        gridCompany.setColumns("name");
        gridCompany.getColumns().forEach(col -> col.setAutoWidth(true));
        gridCompany.asSingleSelect().addValueChangeListener(evt -> editCompany(evt.getValue()));
    }

    private void editCompany(Company company) {
        if (company == null) {
            closeEditor();
        }  else {
            formCompany.setCompany(company);
            formCompany.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        formCompany.setCompany(null);
        formCompany.setVisible(false);
        removeClassName("editing");
    }
    private void updateList() {
        gridCompany.setItems(companyService.findAll());
    }

}
