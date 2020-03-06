package application;
	

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;

public class Main extends Application {

	TextArea area;
	MenuBar m;
	Menu file,edit,help,colors;
	MenuItem new_,open,close,save;
	MenuItem cut,copy,paste,undo,redo,font_size;
	MenuItem backcolor,textcolor,menubarcolor;
    MenuItem help_menu;
	ScrollBar horizontal,vertical;
	final String defaulttitle="Untitled-Anopad";
	Scene scene;
	
	static String bcolor,tcolor,mcolor,fsize;

	
	
	boolean saveanswer;
	@Override
	public void start(Stage primaryStage) {
	
		
		
		bcolor="white";
		tcolor="black";
		mcolor="aquamarine";
		fsize="1";
		
		file=new Menu("File");
		edit=new Menu("Edit");
		help=new Menu("Help");
		colors=new Menu("Color");
		BorderPane root=new BorderPane();
		area=new TextArea();
		area.setText("");
		
		new_=new MenuItem("New");
		open=new MenuItem("Open");
		save=new MenuItem("Save");
		close=new MenuItem("Close");
		
		cut=new MenuItem("Cut");
		copy=new MenuItem("Copy");
		paste=new MenuItem("Paste");
		undo=new MenuItem("Undo");
		redo=new MenuItem("Redo");
		font_size=new MenuItem("Font Size");
		
	
		
	   backcolor=new MenuItem("Background Color");
	   textcolor=new MenuItem("Text Color");
	   menubarcolor=new MenuItem("Menu Bar Color");
	   
	   help_menu=new MenuItem("Help");
		
		
		new_.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent evt)
			{
				
				StringBuilder sb=new StringBuilder();
				sb.append(area.getText());
				if(sb.length()==0)            //Checks if the text area is blank or not
				{
					primaryStage.setTitle(defaulttitle);
					area.setText("");
					
				}
				else // If something is present it should save the file
				{
					Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
					alert.setContentText("Do you want to save the file?");
					alert.setHeaderText("Warning");
					alert.showAndWait();
				if(alert.getResult()==ButtonType.OK) //When ok is pressed in the warning
				{
					FileChooser choose=new FileChooser();
					File savefile=choose.showSaveDialog(primaryStage);
					if(savefile!=null)  // When directory is not corrupt or if anything is selected
					{
						Save.save(savefile, sb);
						System.gc();
						area.setText("");
						primaryStage.setTitle(defaulttitle);
					}
					
				
				}
				}
			}
		});
		
		open.setOnAction(new EventHandler <ActionEvent>() {
		
			@Override
			public void handle(ActionEvent e) {
				FileChooser choose=new FileChooser();
				File openfile=choose.showOpenDialog(primaryStage);
				try {
					
					InputStream readstream=new FileInputStream(openfile);
					BufferedReader br=new BufferedReader(new InputStreamReader(readstream,StandardCharsets.UTF_8));
					String line="";
					while((line=(String)(br.readLine())) != null )
					{
						area.appendText(line+"\n");
					}
				  String newtitle=openfile.getName();
				  primaryStage.setTitle(newtitle);
				    
				    br.close();
				} catch (FileNotFoundException e1) {
					Alert error=new Alert(Alert.AlertType.ERROR);
					error.setContentText("We were unable to save this file.Please try again.\nTo diagnose-:\n1:Please check that file is not too large\n2:Retry to open the file\n3:Please check that the directory exist in readable format\n4:Please check that the file is set for read permission");
					error.setHeaderText("Error");
					error.showAndWait();
				} catch (IOException e1) {
					Alert error=new Alert(Alert.AlertType.ERROR);
					error.setContentText("We were unable to save this file.Please try again.\nTo diagnose-:\n1:Please check that file is not too large\n2:Retry to open the file\n3:Please check that the directory exist in readable format\n4:Please check that the file is set for read permission");
					error.setHeaderText("Error");
					error.showAndWait();
				}
				
			}
		});
		
		
		save.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e)
			{
				StringBuilder content=new StringBuilder();
				content.append(area.getText());
				if(content.length()==0)
				{
					Alert error=new Alert(Alert.AlertType.ERROR);
					error.setContentText("We were unable to save this file.Please try again.\nTo diagnose-:\n1:Please check that file is not too large\n2:Check that file content is not Empty\n3:Please check that the directory exist in readable format");
					error.setHeaderText("Nothing Found");
					error.showAndWait();
				}
				else
				{
					FileChooser choose=new FileChooser();
				    choose.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
					choose.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG File", "*.jpeg"));
					choose.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.file"));
					File path=choose.showSaveDialog(primaryStage);
					if(path!=null)
					{
						Save.save(path, content);
						primaryStage.setTitle(path.getName());
					}
					
				}
			}
		});
		
		close.setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				System.exit(0);
			}
				});
		
		
		file.getItems().add(new_);
		file.getItems().add(open);
		file.getItems().add(save);
		file.getItems().add(close);
		
		
		//Edit Action Listeners
		cut.setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				area.cut();

			}
				});
		
		copy.setOnAction(new EventHandler<ActionEvent>()
		{
	@Override
	public void handle(ActionEvent e)
	{
		area.copy();

	}
		});
		
		paste.setOnAction(new EventHandler<ActionEvent>()
		{
	@Override
	public void handle(ActionEvent e)
	{
		area.paste();

	}
		});
		
		
		undo.setOnAction(new EventHandler<ActionEvent>()
		{
	@Override
	public void handle(ActionEvent e)
	{
		area.undo();

	}
		});
		
		redo.setOnAction(new EventHandler<ActionEvent>()
		{
	@Override
	public void handle(ActionEvent e)
	{
		area.redo();

	}
		});
		
		font_size.setOnAction((ActionEvent evt)->
		{
		
			Stage stage=new Stage();
			FlowPane fpane=new FlowPane();
			Button okbtn=new Button("OK");
			ComboBox box=new ComboBox();
			for(int i=2;i<=50;i+=2) {
				box.getItems().add(i);
			}
			
		fpane.getChildren().add(box);
		fpane.getChildren().add(okbtn);
		fpane.setPadding(new Insets(10));
		fpane.setHgap(20);
		
	     okbtn.setOnAction((ActionEvent event)->{
	    	 fsize=box.getValue().toString();
	    	 area.setStyle(("text-area-background:"+bcolor+";"+"\n"+"-fx-text-fill:"+tcolor+";"+ "\n"+"-fx-font-size:"+fsize+"em;"));
				m.setStyle("-fx-background-color:"+mcolor+";");
	    	 stage.close();
	    	 
	     });
		
			Scene my_scene=new Scene(fpane);
			stage.setScene(my_scene);
			stage.setTitle("Font Size chooser");
			stage.show();
		});
		
		
		edit.getItems().add(cut);
		edit.getItems().add(copy);
		edit.getItems().add(paste);
		edit.getItems().add(undo);
		edit.getItems().add(redo);
		edit.getItems().add(font_size);
		
		
		backcolor.setOnAction((ActionEvent evt)->{
			
			Stage colorstage=new Stage();
			
			Circle c =new Circle(50);
			
			ColorPicker pickme=new ColorPicker();
			FlowPane colorpane=new FlowPane();
			Button ok_btn=new Button();
			ok_btn.setText("OK");
						
			colorpane.setPadding(new Insets(10));
			c.setFill(pickme.getValue());
			colorpane.setHgap(20);
			colorpane.getChildren().add(c);
			colorpane.getChildren().add(pickme);
			colorpane.getChildren().add(ok_btn);
			
			pickme.setOnAction((ActionEvent colorevent)->{
				c.setFill(pickme.getValue());
				bcolor=pickme.getValue().toString().replace("0x", "#");
				System.out.println("Bcolor="+bcolor+"\nMcolor="+mcolor+"\nText color="+tcolor);
								area.setStyle(("text-area-background:"+bcolor+";"+"\n"+"-fx-text-fill:"+tcolor+";"+ "\n"+"-fx-font-size:"+fsize+"em;"));
				m.setStyle("-fx-background-color:"+mcolor+";");
				
				
			});
			
			ok_btn.setOnAction((ActionEvent event)->{
				colorstage.close();
			});
			
			Scene colorscene=new Scene(colorpane);
		    colorstage.setScene(colorscene);
		    colorstage.setTitle("Background color");
		    colorstage.show();
		
			
		});
		
		
		textcolor.setOnAction((ActionEvent evt)->{
			
Stage colorstage=new Stage();
			
			Circle c =new Circle(50);
			
			ColorPicker pickme=new ColorPicker();
			FlowPane colorpane=new FlowPane();
			Button ok_btn=new Button();
			ok_btn.setText("OK");
						
			colorpane.setPadding(new Insets(10));
			c.setFill(pickme.getValue());
			colorpane.setHgap(20);
			colorpane.getChildren().add(c);
			colorpane.getChildren().add(pickme);
			colorpane.getChildren().add(ok_btn);
			
			pickme.setOnAction((ActionEvent colorevent)->{
				c.setFill(pickme.getValue());
				tcolor=pickme.getValue().toString().replace("0x", "#");
				System.out.println("Bcolor="+bcolor+"\nMcolor="+mcolor+"\nText color="+tcolor);
				area.setStyle(("text-area-background:"+bcolor+";"+"\n"+"-fx-text-fill:"+tcolor+";"+ "\n"+"-fx-font-size:"+fsize+"em;"));
				m.setStyle("-fx-background-color:"+mcolor+";");
				
			});
			
			ok_btn.setOnAction((ActionEvent event)->{
				colorstage.close();
			});
			Scene colorscene=new Scene(colorpane);
		    colorstage.setScene(colorscene);
		    colorstage.setTitle("Text color");
		    colorstage.show();
			
		});
		
		
		menubarcolor.setOnAction((ActionEvent evt)->
		{
Stage colorstage=new Stage();
			
			Circle c =new Circle(50);
			
			ColorPicker pickme=new ColorPicker();
			FlowPane colorpane=new FlowPane();
			Button ok_btn=new Button();
			ok_btn.setText("OK");
						
			colorpane.setPadding(new Insets(10));
			c.setFill(pickme.getValue());
			colorpane.setHgap(20);
			colorpane.getChildren().add(c);
			colorpane.getChildren().add(pickme);
			colorpane.getChildren().add(ok_btn);
			
			pickme.setOnAction((ActionEvent colorevent)->{
				c.setFill(pickme.getValue());
				mcolor=pickme.getValue().toString().replace("0x", "#");
				System.out.println("Bcolor="+bcolor+"\nMcolor="+mcolor+"\nText color="+tcolor);
				
				area.setStyle(("text-area-background:"+bcolor+";"+"\n"+"-fx-text-fill:"+tcolor+";"+ "\n"+"-fx-font-size:"+fsize+"em;"));
				m.setStyle("-fx-background-color:"+mcolor+";");
				
			});
			
			ok_btn.setOnAction((ActionEvent event)->{
				colorstage.close();
			});
			
			Scene colorscene=new Scene(colorpane);
		    colorstage.setScene(colorscene);
		    colorstage.setTitle("Text color");
		    colorstage.show();
			
		});
		
		
		colors.getItems().add(backcolor);
		colors.getItems().add(textcolor);
		colors.getItems().add(menubarcolor);
		
		

		
		
		
		//help
		help.getItems().add(help_menu);
		help_menu.setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				System.out.println("Inside help action  listener");
				 Stage stage=new Stage();
				 BorderPane pane=new BorderPane();
				   TextArea area1 =new TextArea();
				   pane.setCenter(area1);
				  
				   String s="This is a Anopad developed in Java.We know that still there are many bugs in the program but we are trying to improve the software continously for your seamless experience.\nIf you encounter with any bugs please report it to us.\nWe are very happy as you being our customer and hope you like this text editor.";
					area1.setText(s);
				   stage.setTitle("Help");
				   area1.setEditable(false);
				   pane.setCenter(area1);
				   Scene temp_scene=new Scene(pane,stage.getMaxWidth()-20,stage.getMaxHeight()-20);
				   stage.setScene(temp_scene);
				   stage.show();
				
			}
				});
		m=new MenuBar();
		m.getMenus().add(file);
		m.getMenus().add(edit);
		m.getMenus().add(colors);
		m.getMenus().add(help);
		area.setScrollLeft(30);
		area.setScrollTop(30);
		area.setLayoutX(0);
		area.setLayoutY(0);	  
		
	    root.setTop(m);
		root.setCenter(area);
		 scene=new Scene(root,primaryStage.getMaxWidth()-20,primaryStage.getMaxHeight()-20);
		 scene.getStylesheets().add("application.css");
		 
	primaryStage.setTitle(defaulttitle);
	primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("mainlogo.png")));
	primaryStage.setScene(scene);
	primaryStage.setMinWidth(500);
	primaryStage.setMinHeight(500);
	primaryStage.show();
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

class Save
{
	public static void save(File path,StringBuilder content) 
	{
		try {
			FileWriter write=new FileWriter(path);
			BufferedWriter writer=new BufferedWriter(write);
			writer.write(content.toString());
			writer.close();
			writer.close();
			
		} catch (Exception e) {
			Alert error=new Alert(Alert.AlertType.ERROR);
			error.setContentText("We were unable to save this file.Please try again.\nTo diagnose-:\n1:Please check the disk space\n2:Retry to save the file");
			error.setHeaderText("Error");
			error.showAndWait();
		}
		
	}
}