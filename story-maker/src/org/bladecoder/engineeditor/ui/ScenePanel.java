package org.bladecoder.engineeditor.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;

import org.bladecoder.engineeditor.Ctx;
import org.bladecoder.engineeditor.model.Project;
import org.bladecoder.engineeditor.model.ChapterDocument;
import org.bladecoder.engineeditor.ui.components.HeaderPanel;
import org.w3c.dom.Element;

@SuppressWarnings("serial")
public class ScenePanel extends javax.swing.JPanel {
	
	private HeaderPanel headerPanel;
	private JTabbedPane tabPanel;
	private VerbListPanel verbList;	
	private ActorListPanel actorList;
	private ScenePropsPanel scenePropsPanel;
	
	public ScenePanel() {
		headerPanel = new HeaderPanel("SCENE");		
		tabPanel = new JTabbedPane();
		verbList = new VerbListPanel();
		actorList = new ActorListPanel();
		scenePropsPanel = new ScenePropsPanel();
		
//		tabPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		tabPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(bl);
		
		headerPanel.setContentPane(tabPanel);
		add(headerPanel);
		
		tabPanel.setOpaque(false);
		
		tabPanel.add("Actors", actorList);
		tabPanel.add("Verbs", verbList);
		tabPanel.add("Properties", scenePropsPanel);
		
		Ctx.project.addPropertyChangeListener(Project.NOTIFY_SCENE_SELECTED, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				ChapterDocument doc = Ctx.project.getSelectedChapter();
				Element scn = Ctx.project.getSelectedScene();
				
				if(scn != null) {								
					headerPanel.setTile("SCENE " + doc.getId(scn));
				} else {
					headerPanel.setTile("SCENE");
				}
				
				actorList.addElements(doc, scn, "actor");
				verbList.addElements(doc, scn, "verb");		
				scenePropsPanel.setSceneDocument(doc, scn);
			}
		});	
	}
}
