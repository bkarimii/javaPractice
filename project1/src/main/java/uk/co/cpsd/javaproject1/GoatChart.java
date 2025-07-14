package uk.co.cpsd.javaproject1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class GoatChart extends JFrame {

    public GoatChart(List<Integer> goatCounts, List<Integer> grassCounts) {
        setTitle("Population Over Time");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int tick = 0; tick < goatCounts.size(); tick++) {
            dataset.addValue(goatCounts.get(tick), "Goats", String.valueOf(tick));
            dataset.addValue(grassCounts.get(tick), "Grass", String.valueOf(tick));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Goat and Grass Population Over Time",
                "Tick",
                "Count",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
    }
}
