/*
 * Created on Apr 7, 2007 by wyatt
 */
package org.homeunix.drummer.controller;

import org.homeunix.drummer.model.Account;

public class AccountListPanelController {

	public static void deleteAccount(boolean notPermanent, Account a){
		//If there are no transactions using this source, ask if user wants to permanently delete source
		if (notPermanent){
			SourceController.deleteSource(a);
		}
		else{
			SourceController.deleteSourcePermanent(a);
		}
	}
	
	public static void undeleteAccount(Account a){
		SourceController.undeleteSource(a);
	}
}
