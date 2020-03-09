package ui;

import javafx.scene.control.Alert;

public  class UI_Helper_Class {

	public static void showMessageBoxInfo(String strMessage)
	{
		new Alert(Alert.AlertType.INFORMATION, strMessage).showAndWait();
	}
	
	public static void showMessageBoxError(String strMessage)
	{
		new Alert(Alert.AlertType.ERROR, strMessage).showAndWait();
	}
	
	public static void showMessageBoxWarning(String strMessage)
	{
		new Alert(Alert.AlertType.WARNING, strMessage).showAndWait();
	}
	
}
