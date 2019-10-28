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
import java.util.ArrayList;

public class DiagComponent extends JComponent {
    JPanel dataInputPanel = new JPanel(new GridLayout(0, 2, 0, 5));
    JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 5));
    JPanel chartPanel = new JPanel();
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    Timer timer;

    public DiagComponent() {

        final ArrayList<JTextField> textFields = new ArrayList<JTextField>();
        for (int i = 0; i < 3; i++) {
            JTextField tf = new JTextField();
            tf.setText("0");
            textFields.add(tf);
        }
        dataInputPanel.add(new JLabel("col1"));
        dataInputPanel.add(textFields.get(0));
        dataInputPanel.add(new JLabel("col2"));
        dataInputPanel.add(textFields.get(1));
        dataInputPanel.add(new JLabel("col3"));
        dataInputPanel.add(textFields.get(2));
        dataInputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton update = new JButton("Update");
        JButton add = new JButton("New Column");
        buttonPanel.add(update);
        buttonPanel.add(add);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
        final ArrayList<BigDecimal> prevData = new ArrayList<BigDecimal>();
        final ArrayList<BigDecimal> currData = new ArrayList<BigDecimal>();

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prevData.clear();
                currData.clear();
                for (Integer i = 0; i < dataInputPanel.getComponentCount()/2; i++) {
                    prevData.add(BigDecimal.valueOf(dataset.getValue("str", i.toString()).doubleValue()));
                    currData.add(BigDecimal.valueOf(Double.parseDouble(textFields.get(i).getText())));
                }
                timer.start();
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s =Integer.toString(dataInputPanel.getComponentCount()/2);
                String st = (Integer.toString(dataInputPanel.getComponentCount()/2 + 1));
                dataInputPanel.add(new JLabel("col" + st));
                JTextField tf = new JTextField();
                tf.setText("0");
                textFields.add(new JTextField("0"));
                dataInputPanel.add(textFields.get(textFields.size()-1));
                dataInputPanel.updateUI();
                dataset.addValue(0, "str", s);
                prevData.clear();
                currData.clear();
                for (Integer i = 0; i < dataInputPanel.getComponentCount()/2; i++) {
                    prevData.add(BigDecimal.valueOf(dataset.getValue("str", i.toString()).doubleValue()));
                    currData.add(BigDecimal.valueOf(Double.parseDouble(textFields.get(i).getText())));
                }
            }
        });


        timer = new Timer(100, new ActionListener() {
            BigDecimal delta = new BigDecimal("0.1");

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Integer i = 0; i < dataInputPanel.getComponentCount()/2; i++) {

                    if (prevData.get(i).compareTo(currData.get(i)) < 0){
                        dataset.setValue((prevData.get(i)).add(delta), "str", i.toString());
                    }
                    if(prevData.get(i).compareTo(currData.get(i))>0){
                        dataset.setValue((prevData.get(i)).add(delta.negate()), "str", i.toString());
                    }
                    prevData.set(i, BigDecimal.valueOf(dataset.getValue("str", i.toString()).doubleValue()));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                if(prevData.equals(currData)){
                    prevData.clear();
                    currData.clear();
                    ((Timer)e.getSource()).stop();
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
//check ssh?