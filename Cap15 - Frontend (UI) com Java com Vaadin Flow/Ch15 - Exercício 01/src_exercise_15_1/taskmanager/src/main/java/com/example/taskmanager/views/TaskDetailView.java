package com.example.taskmanager.views;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.service.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("task-detail")
public class TaskDetailView extends VerticalLayout {

    
    private final TaskService taskService;

    public TaskDetailView(TaskService taskService) {
        this.taskService = taskService;

        TextField taskNameField = new TextField("Nome da Tarefa");
        Button addButton = new Button("Adicionar", click -> {
            String taskName = taskNameField.getValue();
            if (!taskName.isEmpty()) {
                taskService.addTask(new Task((long) (taskService.getTasks().size() + 1), taskName));
                taskNameField.clear();
                getUI().ifPresent(ui -> ui.navigate(MainView.class)); // Redireciona para a MainView
            }
        });

        add(new Paragraph("Adicionar uma nova tarefa:"), taskNameField, addButton);
    }
}
