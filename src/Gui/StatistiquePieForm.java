/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Services.TrainingService;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import java.util.List;
import javafx.scene.control.ToolBar;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class StatistiquePieForm extends Form {

    public StatistiquePieForm() {
        super("Statistiques", new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        
     
      
       
        
        createPieChartForm(this);

    }

    private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(80);
        renderer.setLegendTextSize(80);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);
        int k = 0;
//        for (double value : values) {
        series.add("Hunting", values[0]);
        series.add("Fishing", values[1]);
//        }
        return series;
    }

    public void createPieChartForm(Form f) {
        f.getAllStyles().setBgColor(0xDDDDD4);
        Label prc = new Label("Training Statistic by Category");

        f.addComponent(BorderLayout.NORTH, BorderLayout.centerCenter(prc));

        List<Integer> li = TrainingService.getInstance().Stati();
        String valeur1 = Double.toString(li.get(0).doubleValue() / li.get(2));
        String role = valeur1.substring(0, 5);
        Double v1 = Double.parseDouble(role);
        String valeur2 = Double.toString(li.get(1).doubleValue() / li.get(2));
        String role2 = valeur2.substring(0, 5);
        Double v2 = Double.parseDouble(role2);

        // Generate the values
        double[] values = new double[]{v1, v2};
        System.out.println("values: " + values);

        int[] colors = new int[]{0xf4b342, 0x52b29a};
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(80);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        renderer.setLabelsColor(0x000000);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setHighlighted(true);

        // Create the chart ... pass the values and renderer to the chart object.
        PieChart chart = new PieChart(buildCategoryDataset("Project budget", values), renderer);

        // Wrap the chart in a Component so we can add it to a form
        ChartComponent c = new ChartComponent(chart);

        // Create a form and show it.
        f.setLayout(new BorderLayout());
        f.addComponent(BorderLayout.CENTER, c);

    }
}
