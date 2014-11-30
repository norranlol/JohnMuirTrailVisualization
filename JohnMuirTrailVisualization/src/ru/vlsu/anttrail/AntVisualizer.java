package ru.vlsu.anttrail;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import ru.vlsu.anttrail.model.TrailData;

public class AntVisualizer extends JFrame {
	
	private final TrailData trailData;

	public AntVisualizer(){
		this(new TrailData());
	}
	
	public AntVisualizer(TrailData trailData){
      super("AntVisualizer");
      this.trailData = trailData;
      JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
      splitPane.setLeftComponent(new AntPanel(trailData));
      splitPane.setResizeWeight(1.0);
      splitPane.setDividerLocation(650);
      getContentPane().add(splitPane);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(1000, 650);
      setVisible(true);
	}

    public static void main(String [] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AntVisualizer();
            }
        });
    }
	
}
