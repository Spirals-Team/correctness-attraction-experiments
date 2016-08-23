package shadow.view;

import com.sun.javafx.charts.Legend;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import shadow.Logger;
import shadow.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by bdanglot on 23/08/16.
 */
public class View extends Application {

	private final static String STRING_PERC_UNPERTURBED = "%Unperturbed";
	private final static String STRING_PERC_PERTURBED = "%Perturbed";
	private final static String STRING_PERC_SUCCESS = "%Success";
	private final static String STRING_PERC_FAILURE = "%Failure";

	private Logger logger;

	private Scene scene;

	@Override
	public void start(Stage stage) {
		this.logger = Main.explorer.getLogger();

		final CategoryAxis xAxisPerturb = new CategoryAxis();
		final NumberAxis yAxisPerturb = new NumberAxis();
		final BarChart<String, Number> bcPerturb = new BarChart<>(xAxisPerturb, yAxisPerturb);
		xAxisPerturb.setLabel("Perturbation Ratio");
		yAxisPerturb.setLabel("Percentage (%)");

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
		xAxis.setLabel("Correctness Ratio");
		yAxis.setLabel("Percentage (%)");

		final XYChart.Series<String, Number> seriesUnperturbed = new XYChart.Series<>();
		seriesUnperturbed.setName(STRING_PERC_UNPERTURBED);
		final XYChart.Data<String, Number> dataPercUnperturbed = new XYChart.Data<>(STRING_PERC_UNPERTURBED, 0);
		seriesUnperturbed.getData().add(dataPercUnperturbed);

		final XYChart.Series<String, Number> seriesPercPerturbed = new XYChart.Series<>();
		seriesPercPerturbed.setName(STRING_PERC_PERTURBED);
		final XYChart.Data<String, Number> dataPercPerturbed = new XYChart.Data<>(STRING_PERC_PERTURBED, 0.0f);
		seriesPercPerturbed.getData().add(dataPercPerturbed);

		final XYChart.Series<String, Number> seriesSuccess = new XYChart.Series<>();
		seriesSuccess.setName(STRING_PERC_SUCCESS);
		final XYChart.Data<String, Number> dataSuccess = new XYChart.Data<>(STRING_PERC_SUCCESS, 0.0f);
		seriesSuccess.getData().add(dataSuccess);

		final XYChart.Series<String, Number> seriesFail = new XYChart.Series<>();
		seriesFail.setName(STRING_PERC_FAILURE);
		final XYChart.Data<String, Number> dataFailure = new XYChart.Data<>(STRING_PERC_FAILURE, 0.0f);
		seriesFail.getData().add(dataFailure);

		Timeline tl = new Timeline();
		tl.getKeyFrames().add(new KeyFrame(Duration.millis(500), actionEvent -> {
			int[] states = this.logger.getState();
			float success = states[1] != 0 ? ((float) states[2] / (float) states[1]) * 100.0f : 0.0f;
			float failure = states[1] != 0 ? ((float) states[3] / (float) states[1]) * 100.0f : 0.0f;
			float percPerturbed = states[0] != 0 ? (float) states[1] / (float) states[0] * 100.0f : 0.0f;
			float percUnperturbed = states[0] != 0 ? (float) (states[0] - states[1]) / (float) states[0] * 100.0f : 0.0f;
			dataPercUnperturbed.setYValue(percUnperturbed);
			dataPercPerturbed.setYValue(percPerturbed);
			dataSuccess.setYValue(success);
			dataFailure.setYValue(failure);
		}));
		tl.setCycleCount(Animation.INDEFINITE);
		tl.play();

		bcPerturb.getData().addAll(seriesUnperturbed, seriesPercPerturbed);
		this.setColorsPerturbation(bcPerturb);
		bc.getData().addAll(seriesSuccess, seriesFail);
		this.setColorsCorrectness(bc);

		scene = new Scene(getHBox(bcPerturb, bc), 800, 600);

		stage.setScene(scene);
		stage.show();
	}

	private void setColorsPerturbation(BarChart<String,Number> bc) {
		for (Node n : bc.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill: orange;");
		}
		for (Node n : bc.lookupAll(".default-color1.chart-bar")) {
			n.setStyle("-fx-bar-fill: blue;");
		}
		Legend legend = (Legend) bc.lookup(".chart-legend");
		Legend.LegendItem li1 = new Legend.LegendItem(STRING_PERC_UNPERTURBED, new Rectangle(10, 10, Color.ORANGE));
		Legend.LegendItem li2 = new Legend.LegendItem(STRING_PERC_PERTURBED, new Rectangle(10, 10, Color.BLUE));
		legend.getItems().setAll(li1, li2);
	}

	private void setColorsCorrectness(final BarChart<String, Number> bc) {

		for (Node n : bc.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill: green;");
		}
		for (Node n : bc.lookupAll(".default-color1.chart-bar")) {
			n.setStyle("-fx-bar-fill: red;");
		}
		Legend legend = (Legend) bc.lookup(".chart-legend");
		Legend.LegendItem li3 = new Legend.LegendItem(STRING_PERC_SUCCESS, new Rectangle(10, 10, Color.GREEN));
		Legend.LegendItem li4 = new Legend.LegendItem(STRING_PERC_FAILURE, new Rectangle(10, 10, Color.RED));
		legend.getItems().setAll(li3, li4);
	}

	@FXML
	public void quit() throws Exception {
		if (scene != null) {
			File outputFile = new File("results/shadow/image.png");
			WritableImage writableImage = new WritableImage((int) scene.getWindow().getWidth(), (int) scene.getWindow().getHeight());
			BufferedImage bImage = SwingFXUtils.fromFXImage(scene.snapshot(writableImage), null);
			try {
				ImageIO.write(bImage, "png", outputFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void stop() throws Exception {
		quit();
	}

	private HBox getHBox(Node... nodes) {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.getChildren().addAll(nodes);
		return hbox;
	}

	public static void run() {
		launch();
	}


}