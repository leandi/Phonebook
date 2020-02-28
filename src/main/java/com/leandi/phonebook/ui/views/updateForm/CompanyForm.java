package com.leandi.phonebook.ui.views.updateForm;

import com.leandi.phonebook.backend.entity.Company;

import com.leandi.phonebook.ui.views.list.ContactForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class CompanyForm extends FormLayout {
    TextField name = new TextField("Urad ali služba");

    Button save = new Button("Shrani");
    Button delete = new Button("Zbriši");
    Button close = new Button("Prekliči");

    Binder<Company> binder = new BeanValidationBinder<>(Company.class);

    public CompanyForm() {
        addClassName("contact-form");

        binder.bindInstanceFields(this);

        add(name, createButtonsLayout());

    }

    public void setCompany(Company company) {
        binder.setBean(company);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new CompanyForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }


// Events
public static abstract class CompanyFormEvent extends ComponentEvent<CompanyForm> {
    private Company company;

    protected CompanyFormEvent(CompanyForm source, Company company) {
        super(source, false);
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }
}

    public static class SaveEvent extends CompanyFormEvent {
        SaveEvent(CompanyForm source, Company company) {
            super(source, company);
        }
    }

    public static class DeleteEvent extends CompanyFormEvent {
        DeleteEvent(CompanyForm source, Company company) {
            super(source, company);
        }

    }

    public static class CloseEvent extends CompanyFormEvent {
        CloseEvent(CompanyForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
