package tictac.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import statistics.PlayerStatistic;
import statistics.StatisticHolder;
import utils.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StatisticsWindow {
    private static final int GAP = 10;

    public StatisticsWindow() {

        // Configuring labels
        Set<PlayerStatistic> stats = new StatisticHolder().getStatistics();
        List<Label[]> labels = new ArrayList<>(stats.size());
        for(PlayerStatistic statsEntry : stats) {
            Label[] labeledStats = new Label[3];
            labeledStats[0] = new Label(statsEntry.getName());
            labeledStats[1] = new Label(String.valueOf(statsEntry.getWins()));
            labeledStats[2] = new Label(String.valueOf(statsEntry.getLosses()));
            labels.add(labeledStats);
        }

        // Configuring layouts
        GridPane statsPane = new GridPane();
        statsPane.setHgap(GAP);
        statsPane.setVgap(GAP);
        int i = 0;
        statsPane.addRow(i++, new Label("Player:"), new Label("Wins:"), new Label("Losses:"));
        for(Label[] labeledStats : labels) {
            statsPane.addRow(i++, labeledStats[0], labeledStats[1], labeledStats[2]);
        }
        BorderPane spacingPane = new BorderPane();
        spacingPane.setCenter(statsPane);
        BorderPane.setMargin(statsPane, new Insets(GAP));

        // Configuring window
        Stage statsStage = new Stage();
        statsStage.getIcons().add(FileManager.readImage("src/main/resources/x.png"));
        statsStage.setTitle("Stats");
        statsStage.setScene(new Scene(spacingPane));
        statsStage.setResizable(false);
        statsStage.show();
    }
}
