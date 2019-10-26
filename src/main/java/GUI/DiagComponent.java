package GUI;

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
import java.math.BigDecimal;

public class DiagComponent extends JComponent {
    JPanel dataInputPanel = new JPanel(new GridLayout(3, 2, 0, 5));
    JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 0, 5));
    JPanel chartPanel = new JPanel();
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    Timer timer;

    public DiagComponent() {

        final JTextField[] textFields = new JTextField[3];
        for (int i = 0; i < 3; i++) {
            textFields[i] = new JTextField();
            textFields[i].setText("0");
        }
        dataInputPanel.add(new JLabel("col1"));
        dataInputPanel.add(textFields[0]);
        dataInputPanel.add(new JLabel("col2"));
        dataInputPanel.add(textFields[1]);
        dataInputPanel.add(new JLabel("col3"));
        dataInputPanel.add(textFields[2]);

        JButton update = new JButton("Update");
        buttonPanel.add(update);

        dataset.addValue(0, "str", "0");
        dataset.addValue(0, "str", "1");
        dataset.addValue(0, "str", "2");
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
        chartPanel.add(new ChartPanel(chart));
        final BigDecimal[] prevData = new BigDecimal[3];
        final BigDecimal[] currData = new BigDecimal[3];
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Integer i = 0; i < 3; i++) {
                    prevData[i] = BigDecimal.valueOf(dataset.getValue("str", i.toString()).doubleValue());
                    currData[i] = BigDecimal.valueOf(Double.parseDouble(textFields[i].getText()));
                }
                timer.start();
            }
        });

        timer = new Timer(100, new ActionListener() {
            BigDecimal delta = new BigDecimal(0.1);

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Integer i = 0; i < 3; i++) {

                    if (prevData[i].compareTo(currData[i]) < 0){
                        dataset.setValue(prevData[i].add(delta), "str", i.toString());
                    }
                    if(prevData[i].compareTo(currData[i])>0){
                        dataset.setValue(prevData[i].add(delta.negate()), "str", i.toString());
                    }
                    prevData[i] = BigDecimal.valueOf(dataset.getValue("str", i.toString()).doubleValue());
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame(" ");
                frame.setSize(1500, 750);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                DiagComponent diagComponent = new DiagComponent();
                JPanel panel = new JPanel();
                panel.add(diagComponent.dataInputPanel);
                panel.add(diagComponent.buttonPanel);
                panel.add(diagComponent.chartPanel);
                frame.getContentPane().add(panel);
                frame.setVisible(true);
            }
        });
    }
}
