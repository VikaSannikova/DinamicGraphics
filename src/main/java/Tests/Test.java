package Tests;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Test {

    private static final String ROW_KEY = "Values";
    private static final Random r = new Random();
    Timer timer;

    private void display() {
        JFrame f = new JFrame("Test");
        JPanel p = new JPanel(new GridLayout(2,1,0,5));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final DefaultCategoryDataset model = new DefaultCategoryDataset();
        model.setValue(1, ROW_KEY, "1");
        model.setValue(2, ROW_KEY, "2");
        model.setValue(3, ROW_KEY, "3");
        JFreeChart chart = ChartFactory.createBarChart("Proxi", "Sensors",
                "Value", model, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 10);
        ChartPanel barPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(320, 240);
            }
        };
        p.add(barPanel);
        final JButton run = new JButton("go");
        run.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        p.add(run);
        f.add(p);
        timer = new Timer(100,new ActionListener(){
            double prevData = model.getValue(ROW_KEY, "2").doubleValue();
            double currData = 8;
            double delta = 0.5;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getValue(ROW_KEY, "2").doubleValue() <= currData){
                    model.setValue(prevData + delta, ROW_KEY, "2");
                    delta += 0.1;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }


                //model.setValue(r.nextDouble() * 3, ROW_KEY, "2");
            }
        });

//        f.add(new JButton(new AbstractAction("Update") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                for (int i =0 ; i < 10; i++)
//                    model.setValue(r.nextDouble() * 3, ROW_KEY, "2");
//
//            }
//        }), BorderLayout.SOUTH);

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Test().display();
            }
        });
    }
}