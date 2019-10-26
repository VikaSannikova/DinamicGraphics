package Tests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

@SuppressWarnings("serial")
public class GUI extends JComponent implements ActionListener {
    DefaultCategoryDataset dataset;
    JFreeChart chart;
    CategoryPlot plot;
    Timer timer;

    public GUI() {
        dataset = new DefaultCategoryDataset();
        dataset.addValue(1, "str", "1");
        chart = ChartFactory.createBarChart(
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
        plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 10);
        timer = new Timer(100, this);
    }
    public void start(){
        timer.start();
    }
    public void stop(){
        timer.stop();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        double prevData = 0;
        double currData = 9;
        //if (prevData < currData) {
        dataset.addValue(prevData + 1, "str", "1");
        //}
        plot.setDataset(dataset);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        double prevData = 0;
        double currData = 9;
        //if (prevData < currData) {
            dataset.addValue(prevData + 1, "str", "1");
        //}
        plot.setDataset(dataset);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        double prevData = 0;
        double currData = 9;
        if (prevData < currData){
            dataset.addValue(prevData + 1, "str", "1");
        }
        plot.setDataset(dataset);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame(" ");
                JPanel panel = new JPanel(/*new GridLayout(1,3,0,5)*/);
                JButton start = new JButton("start");
                JButton stop = new JButton("stop");
                final GUI comp = new GUI();
                panel.add(start);
                panel.add(stop);

                JPanel chartPanel = new JPanel();
                chartPanel.add(new ChartPanel(comp.chart));
                panel.add(chartPanel);
                start.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        comp.start();
                    }
                });
                stop.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        comp.stop();
                    }
                });
                frame.getContentPane().add(panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 550);
                frame.setVisible(true);
            }
        });
    }

}
