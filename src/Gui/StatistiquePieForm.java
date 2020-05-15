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
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import java.util.List;



/**
 *
 * @author Imen BenAbderrahmen
 */
public class StatistiquePieForm extends Form {

    
    public StatistiquePieForm() {
        super("Statistiques", new BorderLayout());
        
        createPieChartForm(this);
   
    }

    private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(30);
        renderer.setLegendTextSize(30);
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
            series.add("Hunting" , values[0]);
            series.add("Fishing" , values[1]);
//        }
        return series;
    }

    public void createPieChartForm(Form f) {

        
       List<Integer> li = TrainingService.getInstance().Stati();
       
       
        
        // Generate the values
        double[] values = new double[]{li.get(0).doubleValue()/li.get(2), li.get(1).doubleValue()/li.get(2)};
        System.out.println("values: "+values);
        
        
        
        int[] colors = new int[]{0xf4b342, 0x52b29a};
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(80);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setHighlighted(true);

        // Create the chart ... pass the values and renderer to the chart object.
        
        PieChart chart = new PieChart(buildCategoryDataset("Project budget", values), renderer);

        // Wrap the chart in a Component so we can add it to a form
        ChartComponent c = new ChartComponent(chart);

        // Create a form and show it.
        f.setLayout(new BorderLayout());
        f.addComponent(BorderLayout.CENTER, c);
         Label prc = new Label("Training Statistic by Category");
       
        f.addComponent(BorderLayout.SOUTH, prc);
        
    }
}
