/*
 * Created on Apr 7, 2007 by wyatt
 */
package org.homeunix.drummer.controller;

import org.homeunix.drummer.model.Category;

public class CategoryListPanelController {

	public static void deleteCategory(boolean notPermanent, Category c){
		//If there are no transactions using this source, ask if user wants to permanently delete source
		if (notPermanent){
			SourceController.deleteSource(c);
		}
		else{
			SourceController.deleteSourcePermanent(c);
			if (c.getParent() != null)
				c.getParent().getChildren().remove(c);
		}
	}
	
	public static void undeleteCategory(Category c){
		SourceController.undeleteSource(c);
	}
}
