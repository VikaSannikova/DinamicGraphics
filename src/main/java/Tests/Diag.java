package Tests;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Diag extends JFrame {
    public Diag() {
        super("Динамические диаграммы");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel input_data_panel = new JPanel(new GridLayout(2, 2, 0, 5));
        input_data_panel.add(new JLabel("1"));
        final JTextField textField = new JTextField();
        input_data_panel.add(textField);
        JButton draw = new JButton("draw");
        input_data_panel.add(draw);

        JPanel p = new JPanel();
        p.add(input_data_panel, BorderLayout.NORTH);

        final JPanel diag = new JPanel();
        diag.setSize(100, 100);

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(0, "str", "1");

        JFreeChart chart = ChartFactory.createBarChart(
                "Диаграмма",            // chart title
                "Значения",   // domain axis label
                "Высота",       // range axis label
                dataset,                    // data
                PlotOrientation.VERTICAL,   // orientation
                true,                // include legend
                true,               // tooltips
                false                  // urls
        );
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 10);

        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ii = Integer.toString(1);
                System.out.println(dataset.getColumnKey(0));
                double prevData = dataset.getValue("str", ii).doubleValue();
                double currData = Double.parseDouble(textField.getText());
                if (currData > prevData) {
                    for (double i = 0; i <= currData; i += 1) {
                        dataset.addValue(prevData + i, "str", ii);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            }
        });

//        JFreeChart chart = ChartFactory.createBarChart(
//                "Диаграмма",            // chart title
//                "Значения",   // domain axis label
//                "Высота",       // range axis label
//                dataset,                    // data
//                PlotOrientation.VERTICAL,   // orientation
//                true,                // include legend
//                true,               // tooltips
//                false                  // urls
//        );
//        chart.setBackgroundPaint(Color.WHITE);
//        CategoryPlot plot = (CategoryPlot) chart.getPlot();
//        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setRange(0, 10);

        JPanel chartPanel = new JPanel();
        chartPanel.add(new ChartPanel(chart));

        JPanel mainPanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(p, gbc);
        gbc.weightx = 0.9;
        gbc.weighty = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(chartPanel, gbc);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Diag();
            }
        });
    }
}
