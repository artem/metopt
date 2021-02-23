import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SwingWrapper {
    private String windowTitle = "changeme";

    /*public SwingWrapper(T chart, DataMutator ctrl) {
        this.ctrl = ctrl;
        this.charts.add(chart);
    }*/

    /**
     * Display the chart in a Swing JFrame
     */
    public JFrame displayChart() {
        // Create and set up the window.
        final JFrame frame = new JFrame(windowTitle);
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 365 * 3, 365);
        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        try {
            javax.swing.SwingUtilities.invokeAndWait(
                    () -> {
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        /*
                        frame.add(ctrl.getControlPanel(), BorderLayout.WEST);

                        XChartPanel<T> chartPanel = new XChartPanel<T>(charts.get(0));
                        chartPanels.add(chartPanel);
                        rightPanel.add(chartPanel);
                        */
                        JLabel sliderLabel = new JLabel("Days to visualize", JLabel.CENTER);
                        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        rightPanel.add(sliderLabel);
                        rightPanel.add(slider);
                        frame.add(rightPanel);

                        // Display the window.
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return frame;
    }

    public SwingWrapper setTitle(String windowTitle) {
        this.windowTitle = windowTitle;
        return this;
    }
}
