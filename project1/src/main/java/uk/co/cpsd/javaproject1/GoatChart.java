package uk.co.cpsd.javaproject1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class GoatChart extends JFrame {

    public GoatChart(List<Integer> goatCounts) {
        setTitle("Goat Population Over Time");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int tick = 0; tick < goatCounts.size(); tick++) {
            dataset.addValue(goatCounts.get(tick), "Goats", String.valueOf(tick));
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
            "Goat Population",
            "Tick",
            "Number of Goats",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(lineChart);
        setContentPane(chartPanel);
    }
}
