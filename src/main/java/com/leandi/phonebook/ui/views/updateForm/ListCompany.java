package com.leandi.phonebook.ui.views.updateForm;


import com.leandi.phonebook.backend.entity.Company;
import com.leandi.phonebook.backend.service.CompanyService;
import com.leandi.phonebook.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
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
        Div content = new Div(gridCompany, formCompany);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
        updateList();

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
