package com.example.taskmanager.views;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.service.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {
    private final TaskService taskService;
    private final Grid<Task> taskGrid = new Grid<>(Task.class, false);

    @Autowired
    public MainView(TaskService taskService) {
        this.taskService = taskService;

        // Link para adicionar nova tarefa
        add(new RouterLink("Adicionar Nova Tarefa", TaskDetailView.class));

        // Configuração da grelha
        configureGrid();

        // Atualiza os itens da grelha
        refreshGrid();
        add(taskGrid);
    }

    private void configureGrid() {
        // Coluna para o nome da tarefa
        taskGrid.addColumn(Task::getName).setHeader("Tarefa");

        // Coluna para o estado de conclusão
        taskGrid.addComponentColumn(task -> {
            Button toggleCompleteButton = new Button(task.isCompleted() ? "Desfazer" : "Concluir", click -> {
                taskService.toggleTaskCompletion(task); // Alterna o estado no serviço
                refreshGrid(); // Atualiza a grelha
            });
            return toggleCompleteButton;
        }).setHeader("Estado");

        // Coluna para apagar a tarefa
        taskGrid.addComponentColumn(task -> {
            Button deleteButton = new Button("Apagar", click -> {
                taskService.deleteTask(task); // Remove a tarefa no serviço
                refreshGrid(); // Atualiza a grelha
            });
            return deleteButton;
        }).setHeader("Ações");
    }

    private void refreshGrid() {
        taskGrid.setItems(taskService.getTasks()); // Atualiza os dados da grelha
    }
}
